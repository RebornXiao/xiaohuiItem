package com.xlibao.saas.market.manager.service.advermanager.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.support.BasicRemoteService;
import com.xlibao.saas.market.manager.config.ConfigFactory;
import com.xlibao.saas.market.manager.service.advermanager.AdverManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/8/31.
 */
@Transactional
@Service("adverManagerService")
public class AdverManagerServiceImpl extends BasicRemoteService implements AdverManagerService {

    private static final Logger logger = LoggerFactory.getLogger(AdverManagerServiceImpl.class);
    @Override
    public JSONObject searchAdvertTemplatesPage() {
        String title = getUTF("title","");
        int timeType = getIntParameter("timeType",-1);
        int isUsed = getIntParameter("isUsed",-1);
        int pageSize = getPageSize();
        int pageIndex = getIntParameter("pageIndex", 1);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("title", title);
        parameters.put("timeType", String.valueOf(timeType));
        parameters.put("isUsed", String.valueOf(isUsed));
        parameters.put("pageSize", String.valueOf(pageSize));
        parameters.put("pageIndex", String.valueOf(pageIndex));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/getAdvertTemplateList.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject addAdvert(String path,String title,String timeSize,String remark,String videoName) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("title",title);
        parameters.put("timeSize", timeSize);
        parameters.put("remark",remark);
        parameters.put("url",path);
        parameters.put("videoName",videoName);

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/uploadAdvertInfo.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject updateAdvert() {
        String title =  getUTF("title","");
        int timeSize =  getIntParameter("timeSize",0);
        String remark = getUTF("remark","");
        int advertID =  getIntParameter("advertID",0);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("title",title);
        parameters.put("timeSize",String.valueOf(timeSize));
        parameters.put("remark",remark);
        parameters.put("advertID", String.valueOf(advertID));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/updateAdvertInfo.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject getAdvertByID() {

        int advertID =  getIntParameter("advertID",0);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("advertID", String.valueOf(advertID));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/getAdvertFromID.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject delAdvertByID() {
        int advertID =  getIntParameter("advertID",0);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("advertID", String.valueOf(advertID));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/deleteAdvertFromID.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject searchScreenTemplatePage(){
        String code = getUTF("code","");
        int marketID = getIntParameter("marketID",-1);
        String size = getUTF("size","");
        int pageSize = getPageSize();
        int pageIndex = getIntParameter("pageIndex", 1);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("code", code);
        parameters.put("marketID", String.valueOf(marketID));
        parameters.put("size", size);
        parameters.put("pageSize", String.valueOf(pageSize));
        parameters.put("pageIndex", String.valueOf(pageIndex));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/getScreenTemplateList.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject addScreen() {

        int marketId =  getIntParameter("marketId",0);
        String marketName =  getUTF("marketName","");
        String requireTime =  getUTF("requireTime","");
        String size =  getUTF("size","");
        String code =  getUTF("code","");
        String mac =  getUTF("mac","");
        String screenRemark =  getUTF("screenRemark","");


        Map<String, String> parameters = new HashMap<>();
        parameters.put("marketId",String.valueOf(marketId));
        parameters.put("marketName",marketName);
        parameters.put("requireTime",requireTime);
        parameters.put("size",size);
        parameters.put("code",code);
        parameters.put("mac",mac);
        parameters.put("screenRemark",screenRemark);

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/addScreenInfo.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject delScreenByID() {
        int screenID =  getIntParameter("screenID",0);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("screenID", String.valueOf(screenID));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/deleteScreenFromID.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject searchScreenAdvertTemplatePage(){

        int marketID = getIntParameter("marketID",-1);
        String title = getUTF("title","");
        String code = getUTF("code","");
        String beginTime = getUTF("beginTime","");
        String endTime = getUTF("endTime","");
        int isDown = getIntParameter("isDown",-1);
        int playStatus = getIntParameter("playStatus",-1);
        int pageSize = getPageSize();
        int pageIndex = getIntParameter("pageIndex", 1);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("title", title);
        parameters.put("beginTime", beginTime);
        parameters.put("endTime", endTime);
        parameters.put("code", code);
        parameters.put("marketID", String.valueOf(marketID));
        parameters.put("isDown", String.valueOf(isDown));
        parameters.put("playStatus", String.valueOf(playStatus));

        parameters.put("pageSize", String.valueOf(pageSize));
        parameters.put("pageIndex", String.valueOf(pageIndex));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/getAdvertTemplates.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject addScreenAdvert() {
         JSONObject rest = new JSONObject();
         int screenID =  getIntParameter("screenID",0);
         String [] titles = getHttpServletRequest().getParameterValues("title");
         String [] advertIDs= getHttpServletRequest().getParameterValues("advertID");
         String [] beginTimes= getHttpServletRequest().getParameterValues("beginTime");
         String [] endTimes= getHttpServletRequest().getParameterValues("endTime");
         String [] playOrders= getHttpServletRequest().getParameterValues("playOrder");
         String [] remarks= getHttpServletRequest().getParameterValues("remark");
        String resStr ="";
        for (int i=0;i<advertIDs.length;i++){
            Map<String, String> parameters = new HashMap<>();
            parameters.put("screenID",String.valueOf(screenID));
            parameters.put("advertID",advertIDs[i]);
            parameters.put("beginTime",beginTimes[i]);
            parameters.put("endTime",endTimes[i]);
            parameters.put("playOrder",playOrders[i]);
            parameters.put("remark",remarks[i]);

            String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/addAdvertInfoForScreen.do";
            JSONObject response = executor(url, parameters);
            if(response.getInteger("code")!=0){
                resStr += "广告："+titles[i]+"。添加失败!";
            }
        }
        rest.put("msg",resStr);
        return rest;
    }

    @Override
    public JSONObject updateScreenAdvert() {
        int screenID =  getIntParameter("screenID",0);
        int advertID =  getIntParameter("advertID",0);
        String beginTime =  getUTF("beginTime","");
        String endTime =  getUTF("endTime","");
        int playOrder =  getIntParameter("playOrder",-1);
        String remark =  getUTF("remark","");


        Map<String, String> parameters = new HashMap<>();
        parameters.put("screenID",String.valueOf(screenID));
        parameters.put("advertID",String.valueOf(advertID));
        parameters.put("beginTime",beginTime);
        parameters.put("endTime",endTime);
        parameters.put("playOrder",String.valueOf(playOrder));
        parameters.put("remark",remark);

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/updateAdvertInfoFromID.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject delAdvertScreenByID() {
        int screenID =  getIntParameter("screenID",0);
        int advertID =  getIntParameter("advertID",0);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("screenID", String.valueOf(screenID));
        parameters.put("advertID", String.valueOf(advertID));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/deleteAdvertInfoFromID.do";
        JSONObject response = executor(url, parameters);

        return response;
    }
}
