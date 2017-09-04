package com.xlibao.saas.market.manager.service.advermanager;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by user on 2017/8/31.
 */
public interface AdverManagerService {

    /**
     * 获取广告列表
     * @return
     */
    JSONObject searchAdvertTemplatesPage();

    /**
     * 添加广告
     * @return
     */
    JSONObject addAdvert(String path);

    /**
     * 编辑广告
     * @return
     */
    JSONObject updateAdvert();

    /**
     * 根据ID获取广告
     * @return
     */
    JSONObject getAdvertByID();

    /**
     * 删除广告
     * @return
     */
    JSONObject delAdvertByID();

    /**
     * 获取屏幕数据列表
     * @return
     */
    JSONObject searchScreenTemplatePage();

    /**
     * 添加屏幕
     * @return
     */
    JSONObject addScreen();

    /**
     * 删除屏幕
     * @return
     */
    JSONObject delScreenByID();

    /**
     * 获取广告播放列表
     */
    JSONObject searchScreenAdvertTemplatePage();

    /**
     * 为屏幕添加播放广告
     * @return
     */
    JSONObject addScreenAdvert();

    /**
     * 修改播放广告信息
     * @return
     */
    JSONObject updateScreenAdvert();

    /**
     * 删除屏幕广告
     * @return
     */
    JSONObject delAdvertScreenByID();
}