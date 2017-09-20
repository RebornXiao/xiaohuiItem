package com.xlibao.saas.market.manager.service.purchase.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.support.BasicRemoteService;
import com.xlibao.saas.market.manager.config.ConfigFactory;
import com.xlibao.saas.market.manager.service.purchase.SupplierManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/9/20.
 */
@Transactional
@Service("passportManagerService")
public class SupplierManagerServiceImpl extends BasicRemoteService implements SupplierManagerService {


    @Override
    public JSONObject searchSupplierPage() {

        String supplierName = getUTF("supplierName", null);
        int supplierType = getIntParameter("supplierType", -1);
        int status = getIntParameter("status", -1);
        int pageSize = getPageSize();
        int pageStartIndex = getPageStartIndex("pageIndex", pageSize);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("supplierName", supplierName);
        parameters.put("supplierType", String.valueOf(supplierType));
        parameters.put("status", String.valueOf(status));
        parameters.put("pageSize", String.valueOf(pageSize));
        parameters.put("pageStartIndex", String.valueOf(pageStartIndex));

        String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/searchSupplierPage.do";
        JSONObject response = executor(url, parameters);

        return response;
    }
    @Override
    public JSONObject getAllSupplier(){
        Map<String, String> parameters = new HashMap<>();
        String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/getAllSupplier.do";
        JSONObject response = executor(url,parameters);

        return response;
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

        Map<String, String> parameters = new HashMap<>();
        parameters.put("supplierName",supplierName);
        parameters.put("address",address);
        parameters.put("deliverPeriod",deliverPeriod);
        parameters.put("salesmanName",salesmanName);
        parameters.put("phone",phone);
        parameters.put("supplierType",String.valueOf(supplierType));

        String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/saveSupplier.do";
        JSONObject response = executor(url,parameters);
        return response;
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
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id",String.valueOf(id));
        parameters.put("supplierName",supplierName);
        parameters.put("address",address);
        parameters.put("deliverPeriod",deliverPeriod);
        parameters.put("salesmanName",salesmanName);
        parameters.put("phone",phone);
        parameters.put("supplierType",String.valueOf(supplierType));

        String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/updateSupplier.do";
        JSONObject response = executor(url,parameters);
        return response;
    }

    public JSONObject updateSupplierStatus(){
        long id = getLongParameter("id",-1);

        int status = getIntParameter("status",-1);

        if(id==-1){
            return fail("缺少供应商ID");
        }else if(status==-1){
            return fail("缺少状态参数");
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id",String.valueOf(id));
        parameters.put("status",String.valueOf(status));
        String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/updateSupplierStatus.do";
        JSONObject response = executor(url,parameters);
        return response;
    }

    public JSONObject getSupplier(){
        long id = getLongParameter("id",-1);
        if(id==-1){
            return fail("缺少供应商ID");
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id",String.valueOf(id));
        String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/getSupplier.do";
        JSONObject response = executor(url,parameters);
        return response;
    }
}
