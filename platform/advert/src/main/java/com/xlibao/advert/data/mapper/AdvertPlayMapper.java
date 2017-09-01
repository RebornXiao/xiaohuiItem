package com.xlibao.advert.data.mapper;

import com.xlibao.metadata.advert.AdvertScreenInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/8/29.
 */
public interface AdvertPlayMapper {

    List<AdvertScreenInfo> getAllAdvertInfo(@Param("mac") String mac);

    void updateIsDown(@Param("advertID") int advertID,@Param("screenID") int screenID,@Param("successDownTime") String successDownTime);

    List<AdvertScreenInfo> getAdvertTemplates(@Param("marketId") int marketId, @Param("code") String code,@Param("title") String title,@Param("beginTime") String beginTime,@Param("endTime") String endTime,@Param("isDown") int isDown,@Param("playStatus") int playStatus,@Param("pageSize") int pageSize, @Param("pageStartIndex") int pageStartIndex);

    int addAdvertInfoForScreen(@Param("advertID") int advertID,@Param("screenID") int screenID,@Param("beginTime") String beginTime,@Param("endTime") String endTime,@Param("playOrder") int playOrder,@Param("remark") String remark);

    int updateAdvertInfoFromID(@Param("advertID") int advertID,@Param("screenID") int screenID,@Param("beginTime") String beginTime,@Param("endTime") String endTime,@Param("playOrder") int playOrder,@Param("remark") String remark);

    int deleteAdvertInfoFromID(@Param("advertID") int advertID,@Param("screenID") int screenID);
}
