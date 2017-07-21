package com.xlibao.datacache.location;

import com.xlibao.common.thread.AsyncScheduledService;
import com.xlibao.datacache.DataCacheApplicationContextLoaderNotify;
import com.xlibao.metadata.passport.PassportArea;
import com.xlibao.metadata.passport.PassportCity;
import com.xlibao.metadata.passport.PassportProvince;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chinahuangxc on 2017/7/21.
 */
public class LocationDataCacheService {

    private static final Logger logger = LoggerFactory.getLogger(LocationDataCacheService.class);

    // 省份信息专用读写锁
    private static final ReentrantReadWriteLock PROVINCE_READ_WRITE_LOCK = new ReentrantReadWriteLock();
    // 省份信息专用读锁
    private static final ReentrantReadWriteLock.ReadLock PROVINCE_READ_LOCK = PROVINCE_READ_WRITE_LOCK.readLock();
    // 省份信息专用写锁
    private static final ReentrantReadWriteLock.WriteLock PROVINCE_WRITE_LOCK = PROVINCE_READ_WRITE_LOCK.writeLock();
    // 所有省份信息映射关系 key -- 省份ID value -- 省份实体对象
    private static final Map<Long, PassportProvince> provinceMap = new ConcurrentHashMap<>();

    // 城市信息专用读写锁
    private static final ReentrantReadWriteLock CITY_READ_WRITE_LOCK = new ReentrantReadWriteLock();
    // 城市信息专用读锁
    private static final ReentrantReadWriteLock.ReadLock CITY_READ_LOCK = CITY_READ_WRITE_LOCK.readLock();
    // 城市信息专用写锁
    private static final ReentrantReadWriteLock.WriteLock CITY_WRITE_LOCK = CITY_READ_WRITE_LOCK.writeLock();
    // 所有城市信息映射关系 key -- 城市ID value -- 城市实体对象
    private static final Map<Long, PassportCity> cityMap = new ConcurrentHashMap<>();

    // 区域信息专用读写锁
    private static final ReentrantReadWriteLock DISTRICT_READ_WRITE_LOCK = new ReentrantReadWriteLock();
    // 区域信息专用读锁
    private static final ReentrantReadWriteLock.ReadLock DISTRICT_READ_LOCK = DISTRICT_READ_WRITE_LOCK.readLock();
    // 区域信息专用写锁
    private static final ReentrantReadWriteLock.WriteLock DISTRICT_WRITE_LOCK = DISTRICT_READ_WRITE_LOCK.writeLock();
    // 所有区域信息映射关系 key -- 区域ID value -- 区域实体对象
    private static final Map<Long, PassportArea> districtMap = new ConcurrentHashMap<>();

    public static void initLocationCache() {
        Callable<Boolean> loaderProvinceCallable = new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                try {
                    logger.info("系统正在加载省份位置数据到缓存中......");
                    loaderProvinces();
                    logger.info("本次加载省份位置数据的数量为：" + provinceMap.size());
                } catch (Throwable cause) {
                    cause.printStackTrace();
                    return false;
                }
                AsyncScheduledService.submitDelayCacheTask(this, DataCacheApplicationContextLoaderNotify.DELAY, DataCacheApplicationContextLoaderNotify.TIME_UNIT);
                return true;
            }
        };
        boolean result = AsyncScheduledService.waitFutureCacheTask(loaderProvinceCallable);
        if (!result) {
            // 一般不能让服务器正常启动
            System.exit(0);
        }

        Callable<Boolean> loaderCityCallable = new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                try {
                    logger.info("系统正在加载城市位置数据到缓存中......");
                    loaderCitys();
                    logger.info("本次加载城市位置数据的数量为：" + cityMap.size());
                } catch (Throwable cause) {
                    cause.printStackTrace();
                    return false;
                }
                AsyncScheduledService.submitDelayCacheTask(this, DataCacheApplicationContextLoaderNotify.DELAY, DataCacheApplicationContextLoaderNotify.TIME_UNIT);
                return true;
            }
        };
        result = AsyncScheduledService.waitFutureCacheTask(loaderCityCallable);
        if (!result) {
            // 一般不能让服务器正常启动
            System.exit(0);
        }

        Callable<Boolean> loaderDistrictCallable = new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                try {
                    logger.info("系统正在加载区域位置数据到缓存中......");
                    loaderAreas();
                    logger.info("本次加载区域位置数据的数量为：" + provinceMap.size());
                } catch (Throwable cause) {
                    cause.printStackTrace();
                    return false;
                }
                AsyncScheduledService.submitDelayCacheTask(this, DataCacheApplicationContextLoaderNotify.DELAY, DataCacheApplicationContextLoaderNotify.TIME_UNIT);
                return true;
            }
        };
        result = AsyncScheduledService.waitFutureCacheTask(loaderDistrictCallable);
        if (!result) {
            // 一般不能让服务器正常启动
            System.exit(0);
        }
    }

    private static void loaderProvinces() {
        List<PassportProvince> provinces = LocationRemoteService.loaderProvinces();

        Map<Long, PassportProvince> tmpProvinceMap = new ConcurrentHashMap<>();
        for (PassportProvince province : provinces) {
            tmpProvinceMap.put(province.getId(), province);
        }
        try {
            if (PROVINCE_WRITE_LOCK.tryLock(DataCacheApplicationContextLoaderNotify.WAIT_LOCK_TIME_OUT, DataCacheApplicationContextLoaderNotify.WAIT_LOCK_TIME_UNIT)) {
                try {
                    provinceMap.clear();
                    provinceMap.putAll(tmpProvinceMap);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    PROVINCE_WRITE_LOCK.unlock();
                }
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
    }

    private static void loaderCitys() {
        List<PassportCity> citys = LocationRemoteService.loaderCitys();

        Map<Long, PassportCity> tmpCityMap = new ConcurrentHashMap<>();
        for (PassportCity city : citys) {
            tmpCityMap.put(city.getId(), city);
        }
        try {
            if (CITY_WRITE_LOCK.tryLock(DataCacheApplicationContextLoaderNotify.WAIT_LOCK_TIME_OUT, DataCacheApplicationContextLoaderNotify.WAIT_LOCK_TIME_UNIT)) {
                try {
                    cityMap.clear();
                    cityMap.putAll(tmpCityMap);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    CITY_WRITE_LOCK.unlock();
                }
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
    }

    private static void loaderAreas() {
        List<PassportArea> districts = LocationRemoteService.loaderDistricts();

        Map<Long, PassportArea> tmpDistrictMap = new ConcurrentHashMap<>();
        for (PassportArea area : districts) {
            tmpDistrictMap.put(area.getId(), area);
        }
        try {
            if (DISTRICT_WRITE_LOCK.tryLock(DataCacheApplicationContextLoaderNotify.WAIT_LOCK_TIME_OUT, DataCacheApplicationContextLoaderNotify.WAIT_LOCK_TIME_UNIT)) {
                try {
                    districtMap.clear();
                    districtMap.putAll(tmpDistrictMap);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    DISTRICT_WRITE_LOCK.unlock();
                }
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
    }

    public static List<PassportProvince> getProvinces() {
        try {
            if (!PROVINCE_READ_LOCK.tryLock(DataCacheApplicationContextLoaderNotify.WAIT_LOCK_TIME_OUT, DataCacheApplicationContextLoaderNotify.WAIT_LOCK_TIME_UNIT)) {
                return LocationRemoteService.loaderProvinces();
            }
            try {

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                PROVINCE_READ_LOCK.unlock();
            }
        } catch (Throwable cause) {

        }
        return LocationRemoteService.loaderProvinces();
    }
}