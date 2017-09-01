package com.xlibao.saas.market.manager.controller;

import com.xlibao.saas.market.manager.BaseController;
import com.xlibao.saas.market.manager.config.LogicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by admin on 2017/8/25.
 */
@Controller
@RequestMapping(value = "marketmanager/advert")
public class AdvertManagerController extends BaseController {

    @RequestMapping("/adverts")
    public String advertInfo(ModelMap map){

        return jumpPage(map,LogicConfig.FTL_ADVERT_EDIT,LogicConfig.TAB_ADVERT,LogicConfig.TAB_ADVERT_LIST);
    }

    @RequestMapping("/admanager")
    public String advertManager(ModelMap map){
        return jumpPage(map,LogicConfig.FTL_ADVERT_MANAGER,LogicConfig.TAB_ADVERT,LogicConfig.TAB_ADVERT_MANAGER);
    }

    @RequestMapping("/adverts/detail")
    public String advertDetail(ModelMap map){
        return jumpPage(map,LogicConfig.FTL_ADVERT_MANAGET_DETAIL,LogicConfig.TAB_ADVERT,LogicConfig.TAB_ADVERT_LIST);
    }

    @RequestMapping("/adverts/screen")
    public String advertScreen(ModelMap map){
        return jumpPage(map,LogicConfig.FTL_ADVERT_MANAGET_SCREEN,LogicConfig.TAB_ADVERT,LogicConfig.TAB_ADVERT_LIST);
    }

    @RequestMapping("/adverts/screenConfig")
    public String advertScreenConfig(ModelMap map){
        return jumpPage(map,LogicConfig.FTL_ADVERT_MANAGET_SCREENCONFIG,LogicConfig.TAB_ADVERT,LogicConfig.TAB_ADVERT_LIST);
    }

    @RequestMapping(value = "/adverts/playDetail")
    public String advertPlayDetail(ModelMap map){
        return jumpPage(map,LogicConfig.FTL_ADVERT_MANAGET_PLAYDETAIL,LogicConfig.TAB_ADVERT,LogicConfig.TAB_ADVERT_LIST);
    }

    @RequestMapping(value = "/adverts/addPlay")
    public String advertAddPlay(ModelMap map){
        return jumpPage(map,LogicConfig.FTL_ADVERT_MANAGET_ADDPLAY,LogicConfig.TAB_ADVERT,LogicConfig.TAB_ADVERT_LIST);
    }
}
