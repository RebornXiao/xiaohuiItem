package com.xlibao.purchase.service.purchase.impl;


import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.GlobalConstantConfig;
import com.xlibao.purchase.data.mapper.PurchaseDataAccessManager;
import com.xlibao.purchase.data.model.PurchaseSupplier;
import com.xlibao.purchase.service.purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Created by admin on 2017/8/28.
 */
@Transactional
@Service("advertService")
public class PurchaseServiceImpl extends BasicWebService implements PurchaseService {

    @Autowired
    private PurchaseDataAccessManager purchaseDataAccessManager;

    @Override
    public JSONObject searchSupplierPage() {

        String supplierName = getUTF("supplierName", null);
        int supplierType = getIntParameter("supplierType", -1);
        int status = getIntParameter("status", -1);
        int pageSize = getPageSize();
        int pageStartIndex = getPageStartIndex("pageIndex", pageSize);

        List<PurchaseSupplier> purchaseSuppliers = purchaseDataAccessManager.searchSupplierPage(supplierName,supplierType,status,pageSize,pageStartIndex);
        int count = purchaseDataAccessManager.searchSupplierPageCount(supplierName,supplierType,status,pageSize,pageStartIndex);

        JSONObject response = new JSONObject();
        response.put("data", purchaseSuppliers);
        response.put("count", count);
        response.put("pageIndex", getIntParameter("pageIndex", 1) - 1);
        return success(response);

    }
    @Override
    public JSONObject getAllSupplier(){
        List<PurchaseSupplier> uppliers = purchaseDataAccessManager.getAllSupplier();
        if (CommonUtils.isEmpty(uppliers)) {
            return fail("系统不存在供应商记录，请联系管理员！");
        }
        return success(uppliers);
    }
    @Override
    public JSONObject saveSupplier(){
        String supplierName = getUTF("supplierName",null);
        String address = getUTF("address",null);
        int supplierType = getIntParameter("supplierType",-1);
        String deliverPeriod = getUTF("deliverPeriod",null);
        String salesmanName = getUTF("salesmanName",null);
        String phone = getUTF("phone",null);
        if(supplierName == null || supplierName.isEmpty()){
            return fail("缺少供应商名称");
        }else if(address == null || address.isEmpty()){
            return fail("缺少供应商地址");
        }else if(deliverPeriod == null || deliverPeriod.isEmpty()){
            return fail("缺少供货周期");
        }else if(salesmanName == null || salesmanName.isEmpty()){
            return fail("缺少供业务员");
        }else if(phone == null || phone.isEmpty()){
            return fail("缺少业务员联系电话");
        }else if(supplierType==-1){
            return fail("缺少供应商级别");
        }
        PurchaseSupplier supplier = new PurchaseSupplier();
        supplier.setSupplierName(supplierName);
        supplier.setAddress(address);
        supplier.setDeliverPeriod(deliverPeriod);
        supplier.setSalesmanName(salesmanName);
        supplier.setPhone(phone);
        supplier.setSupplierType(supplierType);
        if (purchaseDataAccessManager.saveSupplier(supplier) > 0) {
            return success("添加成功");
        }
        return fail("添加失败");
    }
    @Override
    public JSONObject updateSupplier(){
        long id = getLongParameter("id",-1);
        String supplierName = getUTF("supplierName",null);
        String address = getUTF("address",null);
        int supplierType = getIntParameter("supplierType",-1);
        String deliverPeriod = getUTF("deliverPeriod",null);
        String salesmanName = getUTF("salesmanName",null);
        String phone = getUTF("phone",null);
        if(id==-1){
            return fail("缺少供应商ID");
        }else if(supplierName == null || supplierName.isEmpty()){
            return fail("缺少供应商名称");
        }else if(address == null || address.isEmpty()){
            return fail("缺少供应商地址");
        }else if(deliverPeriod == null || deliverPeriod.isEmpty()){
            return fail("缺少供货周期");
        }else if(salesmanName == null || salesmanName.isEmpty()){
            return fail("缺少供业务员");
        }else if(phone == null || phone.isEmpty()){
            return fail("缺少业务员联系电话");
        }else if(supplierType==-1){
            return fail("缺少供应商级别");
        }
        PurchaseSupplier supplier = new PurchaseSupplier();
        supplier.setId(id);
        supplier.setSupplierName(supplierName);
        supplier.setAddress(address);
        supplier.setDeliverPeriod(deliverPeriod);
        supplier.setSalesmanName(salesmanName);
        supplier.setPhone(phone);
        supplier.setSupplierType(supplierType);
        supplier.setUpdateTime(new Date());
        if (purchaseDataAccessManager.updateSupplier(supplier) > 0) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    public JSONObject updateSupplierStatus(){
        long id = getLongParameter("id",-1);

        int status = getIntParameter("status",-1);

        if(id==-1){
            return fail("缺少供应商ID");
        }else if(status==-1){
            return fail("缺少状态参数");
        }
        PurchaseSupplier supplier = new PurchaseSupplier();
        supplier.setId(id);
        supplier.setStatus(status);
        supplier.setUpdateTime(new Date());
        if (purchaseDataAccessManager.updateSupplier(supplier) > 0) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    public JSONObject getSupplier(){
        long id = getLongParameter("id",-1);
        if(id==-1){
            return fail("缺少供应商ID");
        }
        PurchaseSupplier supplier =purchaseDataAccessManager.getSupplier(id);
        if (supplier == null) {
            return fail("找不到供应商信息");
        }
        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(supplier));
        return success(response);
    }
}
