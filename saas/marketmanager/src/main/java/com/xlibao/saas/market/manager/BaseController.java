package com.xlibao.saas.market.manager;

import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.manager.config.LogicConfig;
import org.springframework.ui.ModelMap;

/**
 * Created by zhumg on 8/10.
 */
public class BaseController extends BasicWebService {

    /**
     * 跳转页面
     * @param map
     * @param targetPage    目标页面，ftl文件夹里面的文件名
     * @param menuTab       选中的主菜单项
     * @param menuTabChild  选中的主菜单下的子菜单项
     * @return
     */
    public String jumpPage(ModelMap map, String targetPage, String menuTab, String menuTabChild) {
        map.put(LogicConfig.RIGHT_FTL_PAGE_URL, targetPage);
        map.put(LogicConfig.TAB_NAME, menuTab);
        map.put(LogicConfig.TAB_CHILD_NAME, menuTabChild);
        return LogicConfig.FTL_PAGE;
    }
}