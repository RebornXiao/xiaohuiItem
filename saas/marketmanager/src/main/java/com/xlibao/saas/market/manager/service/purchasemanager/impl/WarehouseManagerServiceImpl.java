package com.xlibao.saas.market.manager.service.purchasemanager.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.support.BasicRemoteService;
import com.xlibao.saas.market.manager.config.ConfigFactory;
import com.xlibao.saas.market.manager.service.purchasemanager.SupplierManagerService;
import com.xlibao.saas.market.manager.service.purchasemanager.WarehouseManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/9/20.
 */
@Transactional
@Service("warehouseManagerService")
public class WarehouseManagerServiceImpl extends BasicRemoteService implements WarehouseManagerService {

    @Override
    public JSONObject searchWarehousePage() {
        String warehouseName = getUTF("warehouseName", null);
        int status = getIntParameter("status", -1);
        int pageSize = getPageSize();
        int pageStartIndex = getPageStartIndex("pageIndex", pageSize);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("warehouseName", warehouseName);
        parameters.put("status", String.valueOf(status));
        parameters.put("pageSize", String.valueOf(pageSize));
        parameters.put("pageStartIndex", String.valueOf(pageStartIndex));

        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/searchWarehousePage.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoRuntimeException ex){
            throw new XlibaoRuntimeException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject saveWarehouse() {
        String warehouseCode = getUTF("warehouseCode",null);
        String warehouseName = getUTF("warehouseName",null);
        String address = getUTF("address",null);
        String ramark = getUTF("ramark",null);

        if(warehouseCode == null || warehouseCode.isEmpty()){
            return fail("缺少仓库编码");
        }else if(warehouseName == null || warehouseName.isEmpty()){
            return fail("缺少仓库名称");
        }else if(address == null || address.isEmpty()){
            return fail("缺少仓库地址");
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("warehouseCode", warehouseCode);
        parameters.put("warehouseName", warehouseName);
        parameters.put("address", address);
        parameters.put("ramark", ramark);

        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/saveWarehouse.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoRuntimeException ex){
            throw new XlibaoRuntimeException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject getAllWarehouse() {
        Map<String, String> parameters = new HashMap<>();
        try{
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/getAllWarehouse.do";
            JSONObject response = executor(url,parameters);
            return response;
        }catch (XlibaoRuntimeException ex){
            throw new XlibaoRuntimeException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject updateWarehouse() {
        long id = getLongParameter("id",-1);
        String warehouseCode = getUTF("warehouseCode",null);
        String warehouseName = getUTF("warehouseName",null);
        String address = getUTF("address",null);
        String ramark = getUTF("ramark",null);
        if(id==-1){
            return fail("缺少仓库ID");
        }else if(warehouseCode == null || warehouseCode.isEmpty()){
            return fail("缺少仓库编码");
        }else if(warehouseName == null || warehouseName.isEmpty()){
            return fail("缺少仓库名称");
        }else if(address == null || address.isEmpty()){
            return fail("缺少仓库地址");
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", String.valueOf(id));
        parameters.put("warehouseCode", warehouseCode);
        parameters.put("warehouseName", warehouseName);
        parameters.put("address", address);
        parameters.put("ramark", ramark);

        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/updateWarehouse.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoRuntimeException ex){
            throw new XlibaoRuntimeException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject updateWarehouseStatus() {
        long id = getLongParameter("id",-1);

        int status = getIntParameter("status",-1);
        String  stopRemark = getUTF("stopRemark",null);

        if(id==-1){
            return fail("缺少仓库ID");
        }else if(status==-1){
            return fail("缺少状态参数");
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", String.valueOf(id));
        parameters.put("status",  String.valueOf(status));
        parameters.put("stopRemark", stopRemark);

        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/updateWarehouseStatus.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoRuntimeException ex){
            throw new XlibaoRuntimeException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject getWarehouse() {
        long id = getLongParameter("id",-1);
        if(id==-1){
            return fail("缺少仓库ID");
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", String.valueOf(id));

        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/getWarehouse.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoRuntimeException ex){
            throw new XlibaoRuntimeException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject saveWarehouseUser() {
        long warehouseID = getLongParameter("warehouseID",-1);
        String username = getUTF("username",null);
        String password = getUTF("password",null);
        String ramark = getUTF("ramark",null);
        if(warehouseID==-1){
            return fail("缺少仓库ID");
        }else if(username == null || username.isEmpty()){
            return fail("缺少仓库管理员用户名");
        }else if(password == null || password.isEmpty()){
            return fail("缺少管理员密码");
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("warehouseID", String.valueOf(warehouseID));
        parameters.put("username", username);
        parameters.put("password", password);
        parameters.put("ramark", ramark);

        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/saveWarehouseUser.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoRuntimeException ex){
            throw new XlibaoRuntimeException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject getWarehouseUsers() {
        long warehouseID = getLongParameter("id",-1);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("warehouseID",String.valueOf(warehouseID));
        try{
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/getWarehouseUsers.do";
            JSONObject response = executor(url,parameters);
            return response;
        }catch (XlibaoRuntimeException ex){
            throw new XlibaoRuntimeException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject updateWarehouseUserPassword() {
        long id = getLongParameter("id",-1);
        String password = getUTF("password",null);

        if(id==-1){
            return fail("缺少仓库管理员ID");
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", String.valueOf(id));
        parameters.put("password", "xiaohui2017");

        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/updateWarehouseUserPwd.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoRuntimeException ex){
            throw new XlibaoRuntimeException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject deleteWarehouseUser() {
        long id = getLongParameter("id",-1);

        if(id==-1){
            return fail("缺少仓库管理员ID");
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", String.valueOf(id));

        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/deleteWarehouseUser.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoRuntimeException ex){
            throw new XlibaoRuntimeException("远程接口通信异常");
        }
    }
}
