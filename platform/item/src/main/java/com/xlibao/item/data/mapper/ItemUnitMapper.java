package com.xlibao.item.data.mapper;

import com.xlibao.metadata.item.ItemUnit;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface ItemUnitMapper {

    List<ItemUnit> loaderItemUnits(@Param("status") int status);

    ItemUnit getItemUnit(@Param("itemUnitId") long itemUnitId, @Param("status") int status);

    List<ItemUnit> getItemUnitList(@Param("title") String title, @Param("status") int status, @Param("pageSize") int pageSize, @Param("pageStartIndex") int pageStartIndex);
    int getItemUnitListCount(@Param("title") String title, @Param("status") int status);

    @Insert("insert into item_unit (title, status) values (#{unit.title}, #{unit.status})")
    @SelectKey(before=false,keyProperty="unit.id",resultType=Long.class,statementType= StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    Long add(@Param("unit") ItemUnit unit);

    @Update(" update item_unit SET title = #{unit.title}, status = #{unit.status} where id = #{unit.id}")
    void update(@Param("unit") ItemUnit unit);

}