package com.xlibao.advert.service.item;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by admin on 2017/8/28.
 */
public interface advertService {

    /*JSONObject adverPlayList();

    JSONObject getAdvertList();*/

    JSONObject getAdvertTemplateList();

    JSONObject getAllAdvertInfo();

    JSONObject updateIsDown();

    JSONObject uploadAdvertInfo();

    JSONObject getAdvertFromID();

    JSONObject updateAdvertInfo();

    JSONObject deleteAdvertFromID();

    JSONObject getScreenInfoFromMAC();

    JSONObject getScreenTemplateList();

    JSONObject addScreenInfo();

    JSONObject deleteScreenFromID();

    JSONObject getAdvertTemplates();

    JSONObject addAdvertInfoForScreen();

    JSONObject updateAdvertInfoFromID();

    JSONObject deleteAdvertInfoFromID();
}
