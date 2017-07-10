package com.xlibao.item.data.mapper;

import com.xlibao.metadata.item.ItemType;
import com.xlibao.metadata.item.ItemUnit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface ItemTypeMapper {

    List<ItemType> loaderItemTypes(@Param("status") int status);

    List<ItemType> relationItemTypes(@Param("parentItemTypeId") long parentItemTypeId, @Param("status") int status);

    ItemType getItemType(@Param("itemTypeId") long itemTypeId, @Param("status") int status);

    @Insert("insert into item_type (title, parent_id, status, sort, top, icon, image) values (#{type.title}, #{type.parentId}, #{type.status}, #{type.sort}, #{type.top}, #{type.icon}, #{type.image})")
    @SelectKey(before=false,keyProperty="type.id",resultType=Long.class,statementType= StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    Long add(@Param("type") ItemType type);

    @Update("update item_type SET title = #{type.title}, parent_id = #{type.parentId}, status = #{type.status}, sort = #{type.sort}, top = #{type.top}, icon = #{type.icon}, image = #{type.image}  where id = #{type.id}")
    void update(@Param("type") ItemType type);
}