package com.xlibao.advert.service.item.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.advert.data.mapper.AdvertDataAccessManager;
import com.xlibao.advert.service.item.advertService;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.GlobalConstantConfig;
import com.xlibao.metadata.advert.AdvertInfo;
import com.xlibao.metadata.advert.AdvertScreenInfo;
import com.xlibao.metadata.advert.ScreenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/8/28.
 */
@Transactional
@Service("advertService")
public class advertServiceImpl extends BasicWebService implements advertService {

    @Autowired
    private AdvertDataAccessManager advertDataAccessManager;

    /**
     *
     * @return
     */

    @Override
    public JSONObject getAdvertTemplateList() {
        String title = getUTF("title",null);
        int timeType = getIntParameter("timeType",-1);
        int isUsed = getIntParameter("isUsed",-1);
        int pageSize = getIntParameter("pageSize", GlobalConstantConfig.DEFAULT_PAGE_SIZE);
        int pageStartIndex = getPageStartIndex("pageIndex", pageSize);


        List<AdvertInfo> infos = advertDataAccessManager.getAdvertTemplateList(title,timeType,isUsed,pageSize,pageStartIndex);

        JSONObject response = new JSONObject();
        response.put("data", JSONObject.parseArray(JSONObject.toJSONString(infos)));

        return success(response);

    }

    public JSONObject getAllAdvertInfo(){
        String mac = getUTF("mac",null);
        List<AdvertScreenInfo> infos = advertDataAccessManager.getAllAdvertInfo(mac);
        JSONObject response = new JSONObject();
        response.put("data", JSONObject.parseArray(JSONObject.toJSONString(infos)));

        return success(response);
    }

    @Override
    public JSONObject updateIsDown() {
        int advertID = getIntParameter("advertID",0);
        int screenID = getIntParameter("screenID",0);

        if(advertID == 0 ||screenID == 0){
            return fail("参数不允许为空");
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:m:ss");
        advertDataAccessManager.updateIsDown(advertID,screenID,df.format(new Date()));

        return success("更新状态成功");
    }

    @Override
    public JSONObject uploadAdvertInfo() {
        String title = getUTF("title",null);
        String timeSize = getUTF("timeSize",null);
        String remark = getUTF("remark",null);
        String url = getUTF("url",null);
        String videoName = getUTF("videoName",null);
        int time = 0;
        int timeType = 0;


        if(title==null||url==null||videoName==null){
            return fail("传入参数不能为空");
        }
        try{
            time = Integer.parseInt(timeSize);
            if(time<0){
                return fail("广告时长不能小于零");
            }
            timeType = getTimeType(time);
        }catch (NumberFormatException e){
            return fail("广告时长参数错误");
        }

        int advertID = advertDataAccessManager.getMaxAdvertID()+1;

        AdvertInfo infos = new AdvertInfo();

        infos.setAdvertID(advertID);
        infos.setRemark(remark);
        infos.setTimeSize(time);
        infos.setTimeType(timeType);
        infos.setTitle(title);
        infos.setUrl(url);
        infos.setVideoName(videoName);

        advertDataAccessManager.uploadAdvertInfo(infos);


        return success("上传成功");
    }

    public JSONObject getAdvertFromID(){
        int advertID = getIntParameter("advertID",0);
        if(advertID==0){
            return fail("传入参数不能为空");
        }

        List<AdvertInfo> infos = advertDataAccessManager.getAdvertFromID(advertID);

        JSONObject response = new JSONObject();
        response.put("data", JSONObject.parseArray(JSONObject.toJSONString(infos)));

        return success(response);
    }

    public JSONObject updateAdvertInfo(){
        String title = getUTF("title",null);
        String timeSize = getUTF("timeSize",null);
        String remark = getUTF("remark",null);
        int advertID = getIntParameter("advertID",0);

        int time = 0;

        if(title==null || timeSize==null || advertID == 0){
            return fail("传入参数不能为空");
        }

        try{
            time = Integer.parseInt(timeSize);
            if(time<0){
                return fail("广告时长不能小于零");
            }
        }catch (NumberFormatException e){
            return fail("广告时长参数错误");
        }

        advertDataAccessManager.updateAdvertInfo(title,time,remark,advertID);

        return success("更新成功");
    }

    public JSONObject deleteAdvertFromID(){
        int advertID = getIntParameter("advertID",0);

        if(advertID==0){
            return fail("传入参数不能为空");
        }

        advertDataAccessManager.deleteAdvertFromID(advertID);

        return success("操作成功");
    }

    public JSONObject getScreenInfoFromMAC(){
        String mac = getUTF("mac",null);
        if(mac==null){
            return fail("参数出错");
        }

        List<ScreenInfo> infos = advertDataAccessManager.getScreenInfoFromMAC(mac);

        JSONObject response = new JSONObject();
        response.put("data", JSONObject.parseArray(JSONObject.toJSONString(infos)));

        return success(response);
    }

    public JSONObject getScreenTemplateList(){
        String code = getUTF("code",null);
        String marketId = getUTF("marketID",null);
        String size = getUTF("size",null);
        int pageSize = getIntParameter("pageSize", GlobalConstantConfig.DEFAULT_PAGE_SIZE);
        int pageStartIndex = getPageStartIndex("pageIndex", pageSize);

        List<ScreenInfo> infos = advertDataAccessManager.getScreenTemplateList(code,marketId,size,pageSize,pageStartIndex);

        JSONObject response = new JSONObject();
        response.put("data", JSONObject.parseArray(JSONObject.toJSONString(infos)));

        return success(response);
    }


    public JSONObject addScreenInfo(){
        int ScreenID = advertDataAccessManager.getMaxScreenID() + 1;
        int marketId = getIntParameter("marketId",0);
        String marketName = getUTF("marketName",null);
        String requireTime = getUTF("requireTime",null);
        String size = getUTF("size",null);
        String code = getUTF("code",null);
        String mac = getUTF("mac",null);
        int advertCount = getIntParameter("advertCount",5);
        int used = getIntParameter("used",1);
        String screenRemark = getUTF("screenRemark",null);

        if(marketName==null || size ==null || code ==null || mac ==null){
            return fail("参数错误");
        }

        ScreenInfo screenInfo = new ScreenInfo();
        screenInfo.setScreenID(ScreenID);
        screenInfo.setMarketId(marketId);
        screenInfo.setMarketName(marketName);
        screenInfo.setRequireTime(requireTime);
        screenInfo.setSize(size);
        screenInfo.setCode(code);
        screenInfo.setMac(mac);
        screenInfo.setAdvertCount(advertCount);
        screenInfo.setUsed(used);
        screenInfo.setScreenRemark(screenRemark);

        advertDataAccessManager.addScreenInfo(screenInfo);

        return success("添加成功");
    }

    public JSONObject deleteScreenFromID(){
        int screenID = getIntParameter("screenID",0);
        if(screenID ==0){
            fail("参数错误");
        }

        advertDataAccessManager.deleteScreenFromID(screenID);

        return success("删除成功");
    }

    public JSONObject getAdvertTemplates(){
        int marketId = getIntParameter("marketID",-1);
        String code = getUTF("code",null);
        String title = getUTF("title",null);
        String beginTime = getUTF("beginTime",null);
        String endTime = getUTF("endTime",null);
        int isDown = getIntParameter("isDown",-1);
        int playStatus = getIntParameter("playStatus",-1);
        int pageSize = getIntParameter("pageSize", GlobalConstantConfig.DEFAULT_PAGE_SIZE);
        int pageStartIndex = getPageStartIndex("pageIndex", pageSize);


        List<AdvertScreenInfo> infos = advertDataAccessManager.getAdvertTemplates(marketId,code,title,beginTime,endTime,isDown,playStatus,pageSize,pageStartIndex);

        JSONObject response = new JSONObject();
        response.put("data", JSONObject.parseArray(JSONObject.toJSONString(infos)));

        return success(response);
    }


    public JSONObject addAdvertInfoForScreen(){
        int screenID = getIntParameter("screenID");
        int advertID = getIntParameter("advertID");
        String beginTime = getUTF("beginTime",null);
        String endTime = getUTF("endTime",null);
        int playOrder = getIntParameter("playOrder",-1);
        String remark = getUTF("remark",null);

        if(beginTime == null || endTime==null || playOrder==-1){
            return fail("参数错误");
        }

        advertDataAccessManager.addAdvertInfoForScreen(advertID,screenID,beginTime,endTime,playOrder,remark);

        return success("添加成功");
    }

    public JSONObject updateAdvertInfoFromID(){
        int screenID = getIntParameter("screenID");
        int advertID = getIntParameter("advertID");
        String beginTime = getUTF("beginTime",null);
        String endTime = getUTF("endTime",null);
        int playOrder = getIntParameter("playOrder",-1);
        String remark = getUTF("remark",null);

        if(beginTime == null || endTime==null || playOrder==-1){
            return fail("参数错误");
        }

        advertDataAccessManager.updateAdvertInfoFromID(advertID,screenID,beginTime,endTime,playOrder,remark);
        return success("更新成功");
    }

    public JSONObject deleteAdvertInfoFromID(){
        int screenID = getIntParameter("screenID");
        int advertID = getIntParameter("advertID");

        advertDataAccessManager.deleteAdvertInfoFromID(advertID,screenID);

        return success("删除成功");
    }

    public int getTimeType(int timeSize){

        int timeType = 0;

        if(timeSize<=15){timeType=0;}else if (timeSize<=30 && timeSize>15){timeType=1;}
        else if(timeSize>30 && timeSize<=60){timeType=3;}
        else if(timeSize>60 && timeSize<=90){timeType=4;}
        else if(timeSize>90 && timeSize<=120){timeType=5;}
        else {timeType=6;}

        return timeType;
    }


}
