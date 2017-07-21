package com.xlibao.saas.market.service.market;

import com.xlibao.common.CommonUtils;
import com.xlibao.common.lbs.SimpleLocationUtils;
import com.xlibao.common.thread.AsyncScheduledService;
import com.xlibao.saas.market.data.mapper.DataAccessFactory;
import com.xlibao.saas.market.data.model.MarketEntry;
import com.xlibao.saas.market.service.XMarketTimeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chinahuangxc on 2016/10/14.
 */
@Component
public class MarketDataCacheService {

    private static final Logger logger = LoggerFactory.getLogger(MarketDataCacheService.class);

    @Autowired
    private DataAccessFactory dataAccessFactory;

    // 商店数据专用读写锁
    private static final ReentrantReadWriteLock MARKET_READ_WRITE_LOCK = new ReentrantReadWriteLock();
    // 商店数据专用读锁
    private static final ReentrantReadWriteLock.ReadLock MARKET_READ_LOCK = MARKET_READ_WRITE_LOCK.readLock();
    // 商店数据专用写锁
    private static final ReentrantReadWriteLock.WriteLock MARKET_WRITE_LOCK = MARKET_READ_WRITE_LOCK.writeLock();

    private static final Map<Long, MarketEntry> markets = new ConcurrentHashMap<>();

    private static final Map<Long, Long> passportCache = new ConcurrentHashMap<>();

    void initMarketCache() {
        Callable<Boolean> loaderMarketCallable = new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                try {
                    logger.info("系统正在加载所有商店数据到缓存中......");
                    loaderMarket();
                } catch (Throwable cause) {
                    cause.printStackTrace();
                    return false;
                }
                AsyncScheduledService.submitDelayCacheTask(this, XMarketTimeConfig.DELAY, XMarketTimeConfig.TIME_UNIT);
                return true;
            }
        };
        boolean result = AsyncScheduledService.waitFutureCacheTask(loaderMarketCallable);
        if (!result) {
            // 一般不能让服务器正常启动
            System.exit(0);
        }
    }

    private void loaderMarket() {
        List<MarketEntry> marketEntries = dataAccessFactory.getMarketDataAccessManager().loaderMarkets();
        logger.info("当前系统商店数量：" + (marketEntries == null ? 0 : marketEntries.size()));

        Map<Long, MarketEntry> marketEntryMap = new ConcurrentHashMap<>();
        Map<Long, Long> passportCacheMap = new ConcurrentHashMap<>();
        if (!CommonUtils.isEmpty(marketEntries)) {
            for (MarketEntry marketEntry : marketEntries) {
                marketEntryMap.put(marketEntry.getId(), marketEntry);
                passportCacheMap.put(marketEntry.getPassportId(), marketEntry.getId());
            }
        }
        try {
            if (MARKET_WRITE_LOCK.tryLock(XMarketTimeConfig.WAIT_LOCK_TIME_OUT, XMarketTimeConfig.WAIT_LOCK_TIME_UNIT)) {
                try {
                    markets.clear();
                    passportCache.clear();

                    markets.putAll(marketEntryMap);
                    passportCache.putAll(passportCacheMap);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    MARKET_WRITE_LOCK.unlock();
                }
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
    }

    public MarketEntry getMarket(long marketId) {
        try {
            if (!MARKET_READ_LOCK.tryLock(XMarketTimeConfig.WAIT_LOCK_TIME_OUT, XMarketTimeConfig.WAIT_LOCK_TIME_UNIT)) {
                return dataAccessFactory.getMarketDataAccessManager().getMarket(marketId);
            }
            try {
                return markets.get(marketId);
            } finally {
                MARKET_READ_LOCK.unlock();
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
        return dataAccessFactory.getMarketDataAccessManager().getMarket(marketId);
    }

    public MarketEntry getMarketForPassport(long passportId) {
        try {
            if (!MARKET_READ_LOCK.tryLock(XMarketTimeConfig.WAIT_LOCK_TIME_OUT, XMarketTimeConfig.WAIT_LOCK_TIME_UNIT)) {
                return dataAccessFactory.getMarketDataAccessManager().getMarketForPassport(passportId);
            }
            try {
                Long marketId = passportCache.get(passportId);
                if (marketId == null) {
                    return dataAccessFactory.getMarketDataAccessManager().getMarketForPassport(passportId);
                }
                MarketEntry marketEntry = markets.get(marketId);
                if (marketEntry == null) {
                    return dataAccessFactory.getMarketDataAccessManager().getMarketForPassport(passportId);
                }
                return marketEntry;
            } finally {
                MARKET_READ_LOCK.unlock();
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
        return dataAccessFactory.getMarketDataAccessManager().getMarketForPassport(passportId);
    }

    public MarketEntry findRecentMarket(double longitude, double latitude) {
        try {
            if (!MARKET_READ_LOCK.tryLock(XMarketTimeConfig.WAIT_LOCK_TIME_OUT, XMarketTimeConfig.WAIT_LOCK_TIME_UNIT)) {
                return null;
            }
            MarketEntry market = null;
            try {
                int lastDistance = 0;
                for (MarketEntry marketEntry : markets.values()) {
                    if (marketEntry.getStatus() == MarketStatusEnum.CLOSE.getKey()) {
                        // 关闭状态
                        continue;
                    }
                    if (marketEntry.getStatus() == MarketStatusEnum.MAINTAIN.getKey()) {
                        // 不对外开放
                        continue;
                    }
                    int distance = SimpleLocationUtils.simpleDistance(latitude, longitude, Double.parseDouble(marketEntry.getLocation().split(CommonUtils.SPLIT_COMMA)[1]), Double.parseDouble(marketEntry.getLocation().split(CommonUtils.SPLIT_COMMA)[0]));
                    if (distance > marketEntry.getCoveringDistance()) { // 超出配送距离
                        continue;
                    }
                    if (market == null || distance < lastDistance) {
                        lastDistance = distance;
                        market = marketEntry;
                    }
                }
                return market;
            } finally {
                MARKET_READ_LOCK.unlock();
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
        return null;
    }

    /**
     * <pre>
     *     <b>将给定的商店ID组合为字符集合</b>格式为：1,2,3
     * </pre>
     *
     * @param marketEntries 给定的商店集合
     * @return ID集合 格式为：1,2,3
     */
    public String assemMarketSet(List<MarketEntry> marketEntries) {
        StringBuilder marketSet = new StringBuilder();
        for (MarketEntry marketEntry : marketEntries) {
            marketSet.append(marketEntry.getId()).append(CommonUtils.SPLIT_COMMA);
        }
        // 仓库集合
        marketSet = marketSet.deleteCharAt(marketSet.length() - 1);
        return marketSet.toString();
    }
}