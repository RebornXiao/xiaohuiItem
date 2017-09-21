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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

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

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/getAdvertTemplateList.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject addAdvert(String path,String videoName,MultipartHttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String title = multipartRequest.getParameter("title");
        String timeSize = multipartRequest.getParameter("timeSize");
        String remark = multipartRequest.getParameter("remark");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("title",title);
        parameters.put("timeSize", timeSize);
        parameters.put("remark",remark);
        parameters.put("url",path+videoName);
        parameters.put("videoName",videoName);


        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/uploadAdvertInfo.do";
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

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/updateAdvertInfo.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject getAdvertByID() {

        int advertID =  getIntParameter("advertID",0);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("advertID", String.valueOf(advertID));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/getAdvertFromID.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject delAdvertByID() {
        int advertID =  getIntParameter("advertID",0);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("advertID", String.valueOf(advertID));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/deleteAdvertFromID.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject searchScreenTemplatePage(){
        String code = getUTF("code",null);
        String marketID = getUTF("marketID","-1");
        String size = getUTF("size",null);
        int pageSize = getPageSize();
        int pageIndex = getIntParameter("pageIndex", 1);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("code", code);
        parameters.put("marketID", marketID);
        parameters.put("screenSize", size);
        parameters.put("pageSize", String.valueOf(pageSize));
        parameters.put("pageIndex", String.valueOf(pageIndex));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/getScreenTemplateList.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject addScreen() {

        String marketId =  getUTF("marketID","");
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
        parameters.put("screenSize",size);
        parameters.put("code",code);
        parameters.put("mac",mac);
        parameters.put("screenRemark",screenRemark);

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/addScreenInfo.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject getScreenByMac() {
        String  mac =  getUTF("mac","");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("mac", mac);

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/getScreenInfoFromMAC.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject delScreenByID() {
        int screenID =  getIntParameter("screenID",0);
        int marketID =  getIntParameter("marketID",0);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("screenID", String.valueOf(screenID));
        parameters.put("marketID", String.valueOf(marketID));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/deleteScreenFromID.do";
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

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/getAdvertTemplates.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject addScreenAdvert() {
         JSONObject rest = new JSONObject();
         int marketID =  getIntParameter("marketID",0);
         int screenID =  getIntParameter("screenID",0);
         String [] advertIDs= getHttpServletRequest().getParameterValues("advertID");
         String [] beginTimes= getHttpServletRequest().getParameterValues("beginTime");
         String [] endTimes= getHttpServletRequest().getParameterValues("endTime");
         String [] playOrders= getHttpServletRequest().getParameterValues("playOrder");
         String [] remarks= getHttpServletRequest().getParameterValues("remark");
        String resStr ="";
        for (int i=0;i<advertIDs.length;i++){
            Map<String, String> parameters = new HashMap<>();
            parameters.put("marketID",String.valueOf(marketID));
            parameters.put("screenID",String.valueOf(screenID));
            parameters.put("advertID",advertIDs[i]);
            parameters.put("beginTime",beginTimes[i]);
            parameters.put("endTime",endTimes[i]);
            parameters.put("playOrder",playOrders[i]);
            parameters.put("remark",remarks[i]);

            String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/addAdvertInfoForScreen.do";
            rest = executor(url, parameters);
        }
        return rest;
    }

    @Override
    public JSONObject updateScreenAdvert() {
        int marketID =  getIntParameter("marketID",0);
        int screenID =  getIntParameter("screenID",0);
        int advertID =  getIntParameter("advertID",0);
        String beginTime =  getUTF("beginTime","");
        String endTime =  getUTF("endTime","");
        int playOrder =  getIntParameter("playOrder",-1);
        String remark =  getUTF("remark","");
        String sCode = getUTF("sCode","");


        Map<String, String> parameters = new HashMap<>();
        parameters.put("marketID",String.valueOf(marketID));
        parameters.put("screenID",String.valueOf(screenID));
        parameters.put("advertID",String.valueOf(advertID));
        parameters.put("sCode",sCode);
        parameters.put("beginTime",beginTime);
        parameters.put("endTime",endTime);
        parameters.put("playOrder",String.valueOf(playOrder));
        parameters.put("remark",remark);

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/updateAdvertInfoFromID.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject delAdvertScreenByID() {
        int screenID =  getIntParameter("screenID",0);
        int advertID =  getIntParameter("advertID",0);
        int marketID =  getIntParameter("marketID",0);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("screenID", String.valueOf(screenID));
        parameters.put("advertID", String.valueOf(advertID));
        parameters.put("marketID", String.valueOf(marketID));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/deleteAdvertInfoFromID.do";
        JSONObject response = executor(url, parameters);

        return response;
    }
    @Override
    public JSONObject getScreenListBy(){
        int marketId =  getIntParameter("marketId",-1);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("marketId", String.valueOf(marketId));

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/getAllScreenInfo.do";
        JSONObject response = executor(url, parameters);

        return response;
    }


    @Override
    public JSONObject getAdvertScreenByID(){
        String screenID =  getUTF("screenID","-1");
        String advertID =  getUTF("advertID","-1");
        String marketID = getUTF("marketID","-1");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("screenID", screenID);
        parameters.put("advertID", advertID);
        parameters.put("marketID", marketID);

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/getAdvertInfoFromID.do";
        JSONObject response = executor(url, parameters);

        return response;
    }


    @Override
    public JSONObject getAdvertByscreenID(String screenID){

        Map<String, String> parameters = new HashMap<>();
        parameters.put("screenID", screenID);

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/getAdvertInfoFromScreenID.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject getScreensByAdvertID(){

        String advertID =  getUTF("advertID","0");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("advertID", advertID);

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/getScreenInfoFromAdvertID.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

    @Override
    public JSONObject updateAdvertScreenIsdown(){
        String screenID =  getUTF("screenID","-1");
        String advertID =  getUTF("advertID","-1");
        String marketID = getUTF("marketID","-1");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("screenID", screenID);
        parameters.put("advertID", advertID);
        parameters.put("marketID", marketID);
        parameters.put("isDown", "0");

        String url = ConfigFactory.getDomainNameConfig().adverRemoteURL + "advert/advert/updateIsDown.do";
        JSONObject response = executor(url, parameters);

        return response;
    }

}
