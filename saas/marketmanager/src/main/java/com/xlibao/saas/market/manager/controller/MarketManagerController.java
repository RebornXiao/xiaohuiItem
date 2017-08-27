package com.xlibao.saas.market.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.http.HttpRequest;
import com.xlibao.market.data.model.MarketEntry;
import com.xlibao.metadata.passport.PassportArea;
import com.xlibao.metadata.passport.PassportCity;
import com.xlibao.metadata.passport.PassportProvince;
import com.xlibao.metadata.passport.PassportStreet;
import com.xlibao.saas.market.manager.BaseController;
import com.xlibao.saas.market.manager.config.ConfigFactory;
import com.xlibao.saas.market.manager.config.LogicConfig;
import com.xlibao.saas.market.manager.service.itemmanager.ItemManagerService;
import com.xlibao.saas.market.manager.service.marketmanager.MarketManagerService;
import com.xlibao.saas.market.manager.service.passportmanager.PassportManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Controller
@RequestMapping(value = "/marketmanager/market")
public class MarketManagerController extends BaseController {

    @Autowired
    private MarketManagerService marketManagerService;

    @Autowired
    private PassportManagerService passportManagerService;

    @Autowired
    private ItemManagerService itemManagerService;

    //商店 列表 页面
    @RequestMapping("/markets")
    public String markets(ModelMap map) {

        String province = getUTF("province", "");
        String city = getUTF("city", "");
        String district = getUTF("district", "");

        long provinceId = getLongParameter("provinceId", 0);
        long cityId = getLongParameter("city", 0);
        long districtId = getLongParameter("district", 0);

        String street = getUTF("street", "");
        long streetId = getLongParameter("streetId", -1);

        int type = getIntParameter("type", -1);
        int status = getIntParameter("status", -1);
        int deliveryMode = getIntParameter("deliveryMode", -1);

        MarketEntry entry = new MarketEntry();
        entry.setProvince(province);
        entry.setCity(city);
        entry.setDistrict(district);
        entry.setStreet(street);
        entry.setStreetId(streetId);
        entry.setType(type);
        entry.setStatus(status);
        entry.setDeliveryMode(deliveryMode);

        int pageSize = getPageSize();
        int pageIndex = getIntParameter("pageIndex", 1);

        JSONObject marketJson = marketManagerService.searchMarkets(entry, pageSize, pageIndex);

        if (marketJson.getIntValue("code") != 0) {
            return remoteFail(map, marketJson, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_LIST);
        }

        JSONObject response = marketJson.getJSONObject("response");

        map.put("markets", response.getJSONArray("data"));
        map.put("count", response.getIntValue("count"));

        //////////////////
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("districtId", districtId);
        map.put("streetId", streetId);

        JSONObject streetJson = null;

        if (districtId != 0) {
            streetJson = passportManagerService.getStreets(districtId);
            if (streetJson.getInteger("code") == 0) {
                map.put("streets", streetJson.getJSONObject("response").getJSONArray("datas"));
            }
        }

        map.put("type", type);
        map.put("status", status);
        map.put("deliveryMode", deliveryMode);

        map.put("pageSize", pageSize);
        map.put("pageIndex", pageIndex);

        return jumpPage(map, LogicConfig.FTL_MARKET_LIST, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_LIST);
    }

    //商店 编辑 页面
    @RequestMapping("/merketEdit")
    public String merketEdit(ModelMap map) {
        long id = getLongParameter("id", 0);
        if (id != 0) {
            JSONObject itemJson = marketManagerService.getMarket(id);
            if (itemJson.getIntValue("code") != 0) {
                return remoteFail(map, itemJson, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_TEMPLATE);
            }
            MarketEntry entry = parseObject(itemJson.getJSONObject("response").getString("data"), MarketEntry.class);
            map.put("market", entry);
            //拿省
            String provinceName = entry.getProvince();
            if (CommonUtils.isNotNullString(provinceName)) {
                PassportProvince province = passportManagerService.searchProvinceByName(provinceName);
                if (province != null) {
                    map.put("provinceId", province.getId());
                }
            }
            //拿市
            String cityName = entry.getCity();
            if (CommonUtils.isNotNullString(cityName)) {
                PassportCity city = passportManagerService.searchCityByName(cityName);
                if (city != null) {
                    map.put("cityId", city.getId());
                }
            }
            //拿区
            String areaName = entry.getDistrict();
            if (CommonUtils.isNotNullString(areaName)) {
                PassportArea area = passportManagerService.searchAreaByName(areaName);
                if (area != null) {
                    map.put("areaId", area.getId());
                }
            }
            //拿街道
            if (entry.getStreetId() != 0) {
                //拿到这个街道
                JSONObject streetJson = passportManagerService.getStreetJson(entry.getStreetId());
                if (streetJson.getIntValue("code") == 0) {
                    PassportStreet street = parseObject(streetJson.getJSONObject("response").getString("data"), PassportStreet.class);
                    //拿所有数据
                    JSONObject streetsJson = passportManagerService.getStreets(street.getAreaId());
                    if (streetsJson.getIntValue("code") == 0) {
                        JSONArray array = streetsJson.getJSONObject("response").getJSONArray("datas");
                        map.put("streets", array);
                        map.put("streetId", street.getId());
                    }
                }
            }
        }
        return jumpPage(map, LogicConfig.FTL_MARKET_EDIT, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_LIST);
    }

    //提供给页面，拿到 街道列表 JSONObject 信息
    @ResponseBody
    @RequestMapping("/getStreets")
    public JSONObject getStreets() {
        JSONObject json = passportManagerService.getStreets(getLongParameter("districtId"));
        if (json.getIntValue("code") == 0) {
            Map map = new HashMap<>();
            JSONArray array = json.getJSONObject("response").getJSONArray("datas");
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = array.getJSONObject(i);
                map.put(obj.getInteger("id"), obj.getString("name"));
            }
            JSONObject result = new JSONObject();
            result.put("code", 0);
            result.put("datas", map);
            return result;
        } else {
            JSONObject result = new JSONObject();
            result.put("code", 1);
            result.put("msg", json.getString("msg"));
            return result;
        }
    }

    //街道页面
    @RequestMapping("/streets")
    public String streets(ModelMap map) {

        //如果没有选区域，则默认为0
        long provinceId = getLongParameter("provinceId", 10018);//默认广东省
        long cityId = getLongParameter("cityId", 10212);//默认广州市
        long areaId = getLongParameter("areaId", 11919);//默认天河区

        if (areaId != 0) {
            JSONObject streets = passportManagerService.getStreets(areaId);
            if (streets.getIntValue("code") == 0) {
                map.put("streets", streets.getJSONObject("response").getJSONArray("datas"));
            }
        }

        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("areaId", areaId);

        return jumpPage(map, LogicConfig.FTL_MARKET_STREET_LIST, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_STREET_LIST);
    }

    //街道编辑页面
    @RequestMapping("/streetEdit")
    public String streetEdit(ModelMap map) {
        //如果没有选区域，则默认为0
        long provinceId = getLongParameter("provinceId", 10018);//默认广东省
        long cityId = getLongParameter("cityId", 10212);//默认广州市
        long areaId = getLongParameter("areaId", 11919);//默认天河区
        long id = getLongParameter("id", 0);

        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("areaId", areaId);

        if (id != 0) {
            PassportStreet street = passportManagerService.getStreet(id);
            if (street == null) {
                return jumpPage(map, LogicConfig.FTL_MARKET_STREET_EDIT, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_STREET_LIST);
            }
            map.put("street", street);
        }

        return jumpPage(map, LogicConfig.FTL_MARKET_STREET_EDIT, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_STREET_LIST);
    }

    //街道编辑保存
    @ResponseBody
    @RequestMapping("/streetEditSave")
    public JSONObject streetEditSave() {
        String title = getUTF("title");
        long areaId = getLongParameter("areaId");
        long id = getLongParameter("id", 0);
        return passportManagerService.streetEditSave(id, areaId, title);
    }

    //商店 货架 商品
    @RequestMapping("/marketShelves")
    public String marketShelves(ModelMap map) {
        long marketId = getLongParameter("id", 0);

        //拿到所有店铺
        JSONObject response = marketManagerService.getAllMarkets();
        if (response.getIntValue("code") == 0) {
            List<MarketEntry> entrys = JSONObject.parseArray(response.getJSONObject("response").getString("datas"), MarketEntry.class);
            if(entrys.size() > 0) {
                if(marketId == 0) {
                    marketId = entrys.get(0).getId();
                }
                map.put("markets", entrys);
            }
            if(marketId != 0) {
                //拿走道
                String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().marketRemoteURL + "/marketmanager/getShelvesMarks.do?marketId="+ marketId);
                JSONObject jsonObject = JSONObject.parseObject(json);
                if(jsonObject.getIntValue("code") == 0) {
                    List<String> groups = JSONObject.parseArray(jsonObject.getJSONObject("response").getString("datas"), String.class);
                    map.put("groups", groups);
                }
            }
        }

        //所有类型
        map.put("itemTypes", itemManagerService.getSelectItemTypes());

        map.put("marketId", marketId);

        return jumpPage(map, LogicConfig.FTL_MARKET_SHELVES_LIST, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_SHELVES_LIST);
    }

    //拿商店的一些 走道，层 等数据
    @ResponseBody
    @RequestMapping("/getShelvesMarks")
    public JSONObject getShelvesMarks() {
        long marketId = getLongParameter("marketId");
        String groupCode = getUTF("groupCode", "");
        String unitCode = getUTF("unitCode", "");
        int shelvesType = getIntParameter("shelvesType", 0);

        String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().marketRemoteURL + "/marketmanager/getShelvesMarks.do?marketId=" + marketId + "&groupCode=" + groupCode + "&unitCode=" + unitCode + "&shelvesType=" + shelvesType);
        return parseObject(json);
    }

    //获得弹夹数据
    @ResponseBody
    @RequestMapping("/loaderClipDatas")
    public JSONObject loaderClipDatas() {

        long marketId = getLongParameter("marketId");
        String groupCode = getUTF("groupCode");
        String unitCode = getUTF("unitCode");
        String floorCode = getUTF("floorCode");
        int pageSize = getPageSize();
        int pageStartIndex = getPageStartIndex(pageSize);

        String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().marketRemoteURL + "/marketmanager/loaderClipDatas.do?marketId=" + marketId + "&groupCode=" + groupCode + "&unitCode=" + unitCode + "&floorCode=" + floorCode + "&pageSize=" + 1000 + "&pageStartIndex=" + pageStartIndex);
        return parseObject(json);
    }

    //检测并返回商品上架任务详情
    @ResponseBody
    @RequestMapping("/checkPrepareActionTask")
    public JSONObject checkPrepareActionTask() {
        long taskId = getLongParameter("taskId");

        String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().marketRemoteURL + "/marketmanager/checkPrepareActionTask.do?taskId=" + taskId);
        return parseObject(json);
    }

    //取消商品上架任务
    @ResponseBody
    @RequestMapping("/cancelPrepareActionTask")
    public JSONObject cancelPrepareActionTask() {
        long taskId = getLongParameter("taskId");

        String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().marketRemoteURL + "/marketmanager/cancelPrepareActionTask.do?taskId=" + taskId);
        return parseObject(json);
    }
}