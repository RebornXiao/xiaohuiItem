package com.xlibao.advert.data.mapper;

import com.xlibao.metadata.advert.AdvertInfo;
import com.xlibao.metadata.advert.ScreenInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/8/29.
 */
public interface AdvertTemplateMapper {

    List<AdvertInfo> searchAdvertTemplates(@Param("title") String title, @Param("timeType") int timeType, @Param("isUsed") int isUsed, @Param("pageSize") int pageSize, @Param("pageStartIndex") int pageStartIndex);

    int getMaxAdvertID();

    int uploadAdvertInfo(AdvertInfo advertInfo);

    List<AdvertInfo> searchAdvertFromID(@Param("advertID") int advertID);

    void updateAdvertInfo(@Param("title") String title,@Param("timeSize") int timeSize,@Param("remark") String remark,@Param("advertID") int advertID);

    void deleteAdvertFromID(@Param("advertID") int advertID);

}
