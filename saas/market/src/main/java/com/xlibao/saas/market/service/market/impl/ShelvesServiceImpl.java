package com.xlibao.saas.market.service.market.impl;

import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.market.data.model.MarketEntry;
import com.xlibao.market.data.model.MarketShelvesManager;
import com.xlibao.market.protocol.HardwareMessageType;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.service.market.MarketStatusEnum;
import com.xlibao.saas.market.service.market.ShelvesService;
import com.xlibao.saas.market.service.support.remote.MarketShopRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author chinahuangxc on 2017/8/16.
 */
@Transactional
@Service("shelvesService")
public class ShelvesServiceImpl extends BasicWebService implements ShelvesService {

    @Autowired
    private DataAccessFactory dataAccessFactory;
    @Autowired
    private MarketShopRemoteService marketShopRemoteService;

    @Override
    public void builderShelvesData(MarketEntry marketEntry, String content) {
        if (marketEntry.getStatus() != MarketStatusEnum.INITIALIZATION.getKey()) {
            // TODO 不处于初始化过程
        }
        String[] contentArray = content.split("\r\n");
        for (String c : contentArray) {
            String dataType = c.substring(0, 4);
            String data = c.substring(4).replaceAll("\\[", "").replaceAll("]", "");
            if ("0000".equals(dataType)) {
                groupAdapter(marketEntry, data);
                continue;
            }
            unitAdapter(marketEntry, dataType, data);
        }
    }

    private void groupAdapter(MarketEntry marketEntry, String content) {
        String[] groups = content.split(CommonUtils.SPLIT_COMMA);
        for (int i = 0; i < groups.length; i++) {
            String groupCode = CommonUtils.numberToString(String.valueOf(i + 1), 2, "0"); // 当前组的编码

            int unitQuantity = Integer.parseInt(groups[i]); // 当前组的单元数量
            for (int j = 0; j < unitQuantity; j++) {
                String unitCode = CommonUtils.numberToString(String.valueOf(j + 1), 2, "0"); // 当前单元的编码

                createShelves(marketEntry.getId(), marketEntry.getPassportId(), groupCode, unitCode, "01", "01");
            }
            // 刷新单元信息
            refreshUnitDatas(marketEntry, groupCode);
        }
    }

    private void unitAdapter(MarketEntry marketEntry, String dataType, String content) {
        String[] floors = content.split(CommonUtils.SPLIT_COMMA);
        for (int i = 0; i < floors.length; i++) {
            String floorCode = CommonUtils.numberToString(String.valueOf(i + 1), 2, "0"); // 当前楼层的编码

            int clipQuantity = Integer.parseInt(floors[i]); // 当前楼层的弹夹个数
            for (int j = 0; j < clipQuantity; j++) {
                String clipCode = CommonUtils.numberToString(String.valueOf(j + 1), 2, "0");

                if ("01".equals(floorCode) && "01".equals(clipCode)) { // 已初始化
                    continue;
                }
                createShelves(marketEntry.getId(), marketEntry.getPassportId(), dataType.substring(0, 2), dataType.substring(2), floorCode, clipCode);
            }
        }
    }

    private void refreshUnitDatas(MarketEntry marketEntry, String groupCode) {
        String content = HardwareMessageType.SHELVES + groupCode;

        marketShopRemoteService.shelvesMessage(marketEntry.getPassportId(), content);
    }

    private void createShelves(long marketId, long passportId, String groupCode, String unitCode, String floorCode, String clipCode) {
        MarketShelvesManager shelvesManager = new MarketShelvesManager();
        shelvesManager.setMarketId(marketId);
        shelvesManager.setGroupCode(groupCode);
        shelvesManager.setUnitCode(unitCode);
        shelvesManager.setFloorCode(floorCode);
        shelvesManager.setClipCode(clipCode);
        shelvesManager.setCreateTime(new Date());
        shelvesManager.setModifyPassportId(passportId);
        shelvesManager.setLastModifyTime(new Date());
        dataAccessFactory.getMarketDataAccessManager().createShelves(shelvesManager);
    }
}