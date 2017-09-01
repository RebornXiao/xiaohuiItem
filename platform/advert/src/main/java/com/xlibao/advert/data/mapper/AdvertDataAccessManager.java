package com.xlibao.advert.data.mapper;

import com.xlibao.metadata.advert.AdvertInfo;
import com.xlibao.metadata.advert.AdvertScreenInfo;
import com.xlibao.metadata.advert.ScreenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2017/8/28.
 */
@Component
public class AdvertDataAccessManager {

    @Autowired
    private AdvertTemplateMapper advertTemplateMapper;

    @Autowired
    private AdvertPlayMapper advertPlayMapper;

    @Autowired
    private ScreenTemplateMapper screenTemplateMapper;


    public List<AdvertInfo> getAdvertTemplateList(String title,int timeType,int isUsed,int pageSize,int pageStartIndex){
        return advertTemplateMapper.searchAdvertTemplates(title,timeType,isUsed,pageSize,pageStartIndex);
    }


    public List<AdvertScreenInfo> getAllAdvertInfo(String mac){
        return advertPlayMapper.getAllAdvertInfo(mac);
    }

    public void updateIsDown(int advertID,int screenID,String successDownTime){
        advertPlayMapper.updateIsDown(advertID,screenID,successDownTime);
    }

    public int uploadAdvertInfo(AdvertInfo infos){
        return advertTemplateMapper.uploadAdvertInfo(infos);
    }

    public int getMaxAdvertID(){
        return advertTemplateMapper.getMaxAdvertID();
    }

    public List<AdvertInfo> getAdvertFromID(int advertID){
        return advertTemplateMapper.searchAdvertFromID(advertID);
    }

    public void updateAdvertInfo(String title,int timeSize,String remark,int advertID){advertTemplateMapper.updateAdvertInfo(title,timeSize,remark,advertID);}

    public void deleteAdvertFromID(int advertID){advertTemplateMapper.deleteAdvertFromID(advertID);}

    public List<ScreenInfo> getScreenInfoFromMAC(String mac){return screenTemplateMapper.getScreenInfoFromMAC(mac);}

    public List<ScreenInfo> getScreenTemplateList(String code,String marketId,String size,int pageSize,int pageStartIndex){return screenTemplateMapper.getScreenTemplateList(code,marketId,size,pageSize,pageStartIndex);}

    public int getMaxScreenID(){return screenTemplateMapper.getMaxScreenID();}

    public int addScreenInfo(ScreenInfo infos){return screenTemplateMapper.addScreenInfo(infos);}

    public int deleteScreenFromID(int screenID){return screenTemplateMapper.deleteScreenFromID(screenID);}

    public List<AdvertScreenInfo> getAdvertTemplates(int marketId,String code,String title,String beginTime,String endTime,int isDown,int playStatus,int pageSize,int pageStartIndex){return advertPlayMapper.getAdvertTemplates(marketId,code,title,beginTime,endTime,isDown,playStatus,pageSize,pageStartIndex);}

    public int addAdvertInfoForScreen(int advertID,int screenID,String beginTime,String endTime,int playOrder,String remark){return advertPlayMapper.addAdvertInfoForScreen(advertID,screenID,beginTime,endTime,playOrder,remark);}

    public int updateAdvertInfoFromID(int advertID,int screenID,String beginTime,String endTime,int playOrder,String remark){return advertPlayMapper.updateAdvertInfoFromID(advertID,screenID,beginTime,endTime,playOrder,remark);}

    public int deleteAdvertInfoFromID(int advertID,int screenID){return advertPlayMapper.deleteAdvertInfoFromID(advertID,screenID);};
}

