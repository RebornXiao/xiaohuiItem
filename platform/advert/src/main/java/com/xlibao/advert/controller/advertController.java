package com.xlibao.advert.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.advert.service.item.advertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 2017/8/29.
 */
@Controller
@RequestMapping("/advert")
public class advertController {

    @Autowired
    private advertService advertService;

    @ResponseBody
    @RequestMapping("getAdvertTemplateList")
    public JSONObject getAdvertTemplateList(){
        return advertService.getAdvertTemplateList();
    }

    @ResponseBody
    @RequestMapping("getAllAdvertInfo")
    public JSONObject getAllAdvertInfo(){return advertService.getAllAdvertInfo();}

    @ResponseBody
    @RequestMapping("updateIsDown")
    public JSONObject updateIsDown(){return advertService.updateIsDown();}

    @ResponseBody
    @RequestMapping("uploadAdvertInfo")
    public JSONObject uploadAdvertInfo(){return advertService.uploadAdvertInfo();}

    @ResponseBody
    @RequestMapping("getAdvertFromID")
    public JSONObject getAdvertFromID(){return advertService.getAdvertFromID();}

    @ResponseBody
    @RequestMapping("updateAdvertInfo")
    public JSONObject updateAdvertInfo(){return advertService.updateAdvertInfo();}

    @ResponseBody
    @RequestMapping("deleteAdvertFromID")
    public JSONObject deleteAdvertFromID(){return advertService.deleteAdvertFromID();}

    @ResponseBody
    @RequestMapping("getScreenInfoFromMAC")
    public JSONObject getScreenInfoFromMAC(){return advertService.getScreenInfoFromMAC();}

    @ResponseBody
    @RequestMapping("getScreenTemplateList")
    public JSONObject getScreenTemplateList(){return advertService.getScreenTemplateList();}

    @ResponseBody
    @RequestMapping("addScreenInfo")
    public JSONObject addScreenInfo(){return advertService.addScreenInfo();}

    @ResponseBody
    @RequestMapping("deleteScreenFromID")
    public JSONObject deleteScreenFromID(){return advertService.deleteScreenFromID();}

    @ResponseBody
    @RequestMapping("getAdvertTemplates")
    public JSONObject getAdvertTemplates(){return advertService.getAdvertTemplates();}

    @ResponseBody
    @RequestMapping("addAdvertInfoForScreen")
    public JSONObject addAdvertInfoForScreen(){return advertService.addAdvertInfoForScreen();}

    @ResponseBody
    @RequestMapping("updateAdvertInfoFromID")
    public JSONObject updateAdvertInfoFromID(){return advertService.updateAdvertInfoFromID();}

    @ResponseBody
    @RequestMapping("deleteAdvertInfoFromID")
    public JSONObject deleteAdvertInfoFromID(){return advertService.deleteAdvertInfoFromID();};
}
