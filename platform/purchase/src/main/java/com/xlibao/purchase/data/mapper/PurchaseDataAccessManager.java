package com.xlibao.purchase.data.mapper;
import com.xlibao.purchase.data.model.*;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;


/**
 * @author chinahuangxc on 2017/01/26
 */
@Component
public class PurchaseDataAccessManager {

    @Autowired
    private PurchaseCommodityMapper commodityMapper;

    @Autowired
    private PurchaseCommodityStoresMapper commodityStoresMapper;

    @Autowired
    private  PurchaseEntryMapper entryMapper;

    @Autowired
    private  PurchaseSupplierMapper supplierMapper;

    @Autowired
    private  PurchaseWarehouseMapper warehouseMapper;

    @Autowired
    private  PurchaseWarehouseUserMapper warehouseUserMapper;


    public List<PurchaseSupplier> searchSupplierPage(String supplierName,int supplierType, int status,int pageSize, int pageStartIndex){
        return supplierMapper.searchSupplierPage(supplierName,supplierType,status,pageSize,pageStartIndex);
    }

    public int searchSupplierPageCount(String supplierName,int supplierType, int status){
        return supplierMapper.searchSupplierPageCount(supplierName,supplierType,status);
    }

    public List<PurchaseSupplier> getAllSupplier(){
        return supplierMapper.getAllSupplier();
    }

    public int saveSupplier(PurchaseSupplier supplier){
        return supplierMapper.insertSelective(supplier);
    }

    public int updateSupplier(PurchaseSupplier supplier){
        return supplierMapper.updateByPrimaryKeySelective(supplier);
    }

    public  PurchaseSupplier getSupplier(long id){
        return supplierMapper.selectByPrimaryKey(id);
    }
    /**仓库**/
    public List<ResultMap> searchWarehousePage(String warehouseName, int status, int pageSize, int pageStartIndex){
        return warehouseMapper.searchWarehousePage(warehouseName,status,pageSize,pageStartIndex);
    }

    public int searchWarehousePageCount(String warehouseName, int status){
        return warehouseMapper.searchWarehousePageCount(warehouseName,status);
    }

    public List<PurchaseWarehouse> getAllWarehouse(){
        return warehouseMapper.getAllWarehouse();
    }
    public int saveWarehouse(PurchaseWarehouse warehouse){
        return warehouseMapper.insertSelective(warehouse);
    }

    public int updateWarehouse(PurchaseWarehouse warehouse){
        return warehouseMapper.updateByPrimaryKeySelective(warehouse);
    }

    public  PurchaseWarehouse getWarehouse(long id){
        return warehouseMapper.selectByPrimaryKey(id);
    }

    /****仓库管理员***/
    public int saveWarehouseUser(PurchaseWarehouseUser warehouseUser){
        return warehouseUserMapper.insertSelective(warehouseUser);
    }

    public List<PurchaseWarehouseUser> getAllWarehouseUser(long warehouseId){
        return warehouseUserMapper.getAllWarehouseUser(warehouseId);
    }

    public int updateWarehouseUser(PurchaseWarehouseUser warehouseUser){
        return warehouseUserMapper.updateByPrimaryKeySelective(warehouseUser);
    }
    /**采购单**/
    public List<ResultMap> searchPurchasePage(String supplierName,int warehouseID, int status, int pageSize, int pageStartIndex){
        return entryMapper.searchPurchasePage(supplierName,warehouseID,status,pageSize,pageStartIndex);
    }

    public int searchPurchasePageCount(String supplierName,int warehouseID, int status){
        return entryMapper.searchPurchasePageCount(supplierName,warehouseID,status);
    }
    public HashMap getPurchase(Long id){
        return entryMapper.getPurchase(id);
    }

    public List<PurchaseCommodity> getPurchaseCommodityS(Long purchaseID,Long supplierID){
       return  commodityMapper.getPurchaseCommodityS(purchaseID,supplierID);
    }

    public int updatePurchase(PurchaseEntry purchaseEntry){
        return entryMapper.updateByPrimaryKeySelective(purchaseEntry);
    }
    public int delPurchaseCommodity(PurchaseCommodity purchaseCommodity){
        return commodityMapper.updatePurchaseCommodity(purchaseCommodity);
    }
    public int updatePurchaseCommodity(PurchaseCommodity purchaseCommodity){
        return commodityMapper.updateByPrimaryKeySelective(purchaseCommodity);
    }

    public int savePurchase(PurchaseEntry purchaseEntry){
        return entryMapper.insertSelective(purchaseEntry);
    }

    public int savePurchaseCommodity(PurchaseCommodity purchaseCommodity){
        return commodityMapper.insertSelective(purchaseCommodity);
    }

    public int delPurchaseCommodity(Long purchaseID){
       return commodityMapper.deleteByPrimaryKey(purchaseID);
    }

}