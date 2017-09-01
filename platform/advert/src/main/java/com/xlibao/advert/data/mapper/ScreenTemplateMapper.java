package com.xlibao.advert.data.mapper;

import com.xlibao.metadata.advert.ScreenInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/8/31.
 */
public interface ScreenTemplateMapper {

    List<ScreenInfo> getScreenTemplateList(@Param("code") String code,@Param("marketName") String marketName,@Param("size") String size,@Param("pageSize") int pageSize,@Param("pageStartIndex") int pageStartIndex);

    List<ScreenInfo> getScreenInfoFromMAC(@Param("mac") String mac);

    int addScreenInfo(ScreenInfo infos);

    int getMaxScreenID();

    int deleteScreenFromID(@Param("screenID") int screenID);
}
