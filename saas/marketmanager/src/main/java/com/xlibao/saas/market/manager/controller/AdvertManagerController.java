package com.xlibao.saas.market.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.manager.BaseController;
import com.xlibao.saas.market.manager.config.LogicConfig;
import com.xlibao.saas.market.manager.service.advermanager.AdverManagerService;
import com.xlibao.saas.market.manager.utils.QiniuFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 2017/8/25.
 */
@Controller
@RequestMapping(value = "marketmanager/advert")
public class AdvertManagerController extends BaseController {

    @Autowired
    private AdverManagerService adverManagerService;
    @Autowired
    private QiniuFileUtil qiniuFileUtil;
    /**
     *获取视频列表
     * @param map
     * @return
     */
    @RequestMapping("/adverts")
    public String getAdvertList(ModelMap map){
        JSONObject adverJson =  adverManagerService.searchAdvertTemplatesPage();
        JSONObject response = adverJson.getJSONObject("response");
        JSONArray adverts = response.getJSONArray("data");

        int pageIndex = getIntParameter("pageIndex", 1);
        map.put("title", getUTF("title",""));
        map.put("timeType", getIntParameter("timeType",0));
        map.put("isUsed", getIntParameter("isUsed",1));
        map.put("pageIndex", pageIndex);
        map.put("advertList", adverts);
        return jumpPage(map, LogicConfig.FTL_ADVERT_EDIT, LogicConfig.TAB_ADVERT, LogicConfig.TAB_ADVERT_LIST);
    }

    /**
     * 添加视频附带视频文件去”七牛“
     * @param file
     * @param map
     * @return
     */
    @RequestMapping(value = "/addAdvert",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public JSONObject addAdvert(@RequestParam("file") MultipartFile file,ModelMap map){
        JSONObject jsonObject = qiniuFileUtil.uploadToQiniu(file);
        String path = jsonObject.getString("path");
        return  adverManagerService.addAdvert(path);
    }

    /**
     * 查看视频详情
     * @param map
     * @return
     */
    @RequestMapping("/detail")
    public String advertDetail(ModelMap map){
        JSONObject json =  adverManagerService.getAdvertByID();
        JSONObject  adverJson= JSONObject.parseObject(json.getJSONObject("response").getString("data"));
        map.put("adver",adverJson);
        return jumpPage(map, LogicConfig.FTL_ADVERT_MANAGET_DETAIL, LogicConfig.TAB_ADVERT, LogicConfig.TAB_ADVERT_LIST);
    }

    /**
     * 更新视频文件
     * @param map
     * @return
     */
    @RequestMapping("/updateAdvert")
    public JSONObject updateAdvert(ModelMap map){
        return adverManagerService.updateAdvert();
    }

    /**
     * 删除视频
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delAdvert", method = RequestMethod.POST)
    public JSONObject delAdvert(ModelMap map){
        return adverManagerService.delAdvertByID();
    }
    /**
     *获取屏幕列表
     * @param map
     * @return
     */
    @RequestMapping("/screens")
    public String getScreenList(ModelMap map){
        JSONObject adverJson =  adverManagerService.searchScreenTemplatePage();
        JSONObject response = adverJson.getJSONObject("response");
        JSONArray screens = response.getJSONArray("data");

        int pageIndex = getIntParameter("pageIndex", 1);
        map.put("code", getUTF("code",""));
        map.put("marketID", getIntParameter("marketID",0));
        map.put("size", getUTF("size",""));
        map.put("pageIndex", pageIndex);
        map.put("screens", screens);
        return jumpPage(map, LogicConfig.FTL_ADVERT_EDIT, LogicConfig.TAB_ADVERT, LogicConfig.TAB_ADVERT_LIST);
    }

    /**
     * 添加屏幕
     * @param map
     * @return
     */
    @RequestMapping("/addScreen")
    public JSONObject addScreen(ModelMap map){
        return adverManagerService.addScreen();
    }


    /**
     * 删除屏幕
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delScreen", method = RequestMethod.POST)
    public JSONObject delScreen(ModelMap map){
        return adverManagerService.delScreenByID();
    }
    /**
     *获取播放列表
     * @param map
     * @return
     */
    @RequestMapping("/advertScreens")
    public String getadvertScreenList(ModelMap map){
        /** JSONObject adverJson =  adverManagerService.searchScreenAdvertTemplatePage();
         JSONObject response = adverJson.getJSONObject("response");
         JSONArray advertScreens = response.getJSONArray("data");

         int pageIndex = getIntParameter("pageIndex", 1);
         map.put("marketID", getIntParameter("marketID",0));
         map.put("code", getUTF("code",""));
         map.put("title", getUTF("title",""));
         map.put("beginTime", getUTF("beginTime",""));
         map.put("endTime", getUTF("endTime",""));
         map.put("isDown", getIntParameter("isDown",0));
         map.put("playStatus", getIntParameter("playStatus",0));
         map.put("pageIndex", pageIndex);
         map.put("advertScreens", advertScreens);*/
        return jumpPage(map, LogicConfig.FTL_ADVERT_MANAGER, LogicConfig.TAB_ADVERT, LogicConfig.TAB_ADVERT_LIST);
    }
    /**
     * 添加播放视频
     * @param map
     * @return
     */
    @RequestMapping("/addScreenAdvert")
    public JSONObject addScreenAdvert(ModelMap map){
        return adverManagerService.addScreenAdvert();
    }
    /**
     * 更新播放视频
     * @param map
     * @return
     */
    @RequestMapping("/updateScreenAdvert")
    public JSONObject updateScreenAdvert(ModelMap map){
        return adverManagerService.updateScreenAdvert();
    }

    /**
     * 删除播放视频
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delScreenAdvert", method = RequestMethod.POST)
    public JSONObject delScreenAdvert(ModelMap map){
        return adverManagerService.delAdvertScreenByID();
    }
}
