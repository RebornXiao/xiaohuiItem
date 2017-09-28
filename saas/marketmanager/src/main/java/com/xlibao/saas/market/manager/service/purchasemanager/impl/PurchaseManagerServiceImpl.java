package com.xlibao.saas.market.manager.service.purchasemanager.impl;


import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.exception.XlibaoIllegalArgumentException;
import com.xlibao.common.support.BasicRemoteService;
import com.xlibao.saas.market.manager.config.ConfigFactory;
import com.xlibao.saas.market.manager.service.purchasemanager.PurchaseManagerService;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by user on 2017/9/20.
 */
@Transactional
@Service("purchaseManagerService")
public class PurchaseManagerServiceImpl extends BasicRemoteService implements PurchaseManagerService {


    @Override
    public JSONObject searchPurchasePage() {
        String supplierName = getUTF("supplierName", "");
        String warehouseCode = getUTF("warehouseCode", "");
        int status = getIntParameter("status", -1);
        int pageSize = getPageSize();
        int pageStartIndex = getPageStartIndex("pageIndex", pageSize);


        Map<String, String> parameters = new HashMap<>();
        parameters.put("supplierName", supplierName);
        parameters.put("warehouseCode", warehouseCode);
        parameters.put("status", String.valueOf(status));
        parameters.put("pageSize", String.valueOf(pageSize));
        parameters.put("pageStartIndex", String.valueOf(pageStartIndex));

        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/searchPurchasePage.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoIllegalArgumentException ex){
            throw new XlibaoIllegalArgumentException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject getPurchase() {
        long id = getLongParameter("id",-1);
        if(id==-1){
            return fail("缺少采购单ID");
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id",String.valueOf(id));
        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/getPurchase.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoIllegalArgumentException ex){
            throw new XlibaoIllegalArgumentException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject getPurchaseCommodityS() {
        long purchaseID = getLongParameter("purchaseID",-1);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("purchaseID", String.valueOf(purchaseID));
        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/getPurchaseCommodityS.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoIllegalArgumentException ex){
            throw new XlibaoIllegalArgumentException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject getSupplierCommodityS() {
        long supplierID = getLongParameter("id",-1);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("supplierID", String.valueOf(supplierID));
        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/getSupplierCommodityS.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoIllegalArgumentException ex){
            throw new XlibaoIllegalArgumentException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject deletePurchase() {
        long id = getLongParameter("id",-1);

        if(id==-1){
            return fail("缺少采购单ID");
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id",String.valueOf(id));
        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/deletePurchase.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoIllegalArgumentException ex){
            throw new XlibaoIllegalArgumentException("远程接口通信异常");
        }
    }

    @Override
    public JSONObject savePurchase() {
        String warehouseCode = getUTF("warehouseCode","");
        long supplierID = getLongParameter("supplierID",-1);
        int status = getIntParameter("status", -1);
        //String [] purchaseID= getHttpServletRequest().getParameterValues("purchaseID");
        String  itemIDs= getUTF("itemIDs","");
        String  itemTypeIDs= getUTF("itemTypeIDs","");
        String  itemNames= getUTF("itemNames","");
        String  itemTypeTitles= getUTF("itemTypeTitles","");
        String  purchaseTimes= getUTF("purchaseTimes","");
        String  purchaseNumbers= getUTF("purchaseNumbers","");

        if(warehouseCode == ""){
            return fail("缺少仓库编码");
        }else if(supplierID == -1){
            return fail("缺少供应商ID");
        }else if(status==-1){
            return fail("缺少状态");
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("supplierID", String.valueOf(supplierID));
        parameters.put("warehouseCode", warehouseCode);
        parameters.put("status", String.valueOf(status));

        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/savePurchase.do";
            JSONObject response = executor(url, parameters);
            //添加采购单成功再添加采购物品
            if (response.getIntValue("code")==0) {
                JSONObject purchaseResp = response.getJSONObject("response");
                long purchaseID = purchaseResp.getLongValue("purchaseID");
                String []  itemIDList= itemIDs.split(CommonUtils.SPLIT_COMMA);
                String [] itemTypeIDList= itemTypeIDs.split(CommonUtils.SPLIT_COMMA);
                String [] itemNameList= itemNames.split(CommonUtils.SPLIT_COMMA);
                String [] itemTypeTitleList= itemTypeTitles.split(CommonUtils.SPLIT_COMMA);
                String [] purchaseTimeList= purchaseTimes.split(CommonUtils.SPLIT_COMMA);
                String [] purchaseNumberList=purchaseNumbers.split(CommonUtils.SPLIT_COMMA);
                for (int i = 0; i < itemIDList.length; i++) {
                    Map<String, String> parameters2 = new HashMap<>();
                    parameters2.put("purchaseID", String.valueOf(purchaseID));
                    parameters2.put("itemID", itemIDList[i]);
                    parameters2.put("itemTypeID", itemTypeIDList[i]);
                    parameters2.put("itemName", itemNameList[i]);
                    parameters2.put("itemTypeTitle", itemTypeTitleList[i]);
                    parameters2.put("purchaseTime", purchaseTimeList[i]);
                    parameters2.put("purchaseNumber", purchaseNumberList[i]);
                    //调用添加接口
                    savePurchaseCommodity(parameters2);
                }
            }
        }catch (XlibaoIllegalArgumentException ex){
            throw new XlibaoIllegalArgumentException("远程接口通信异常");
        }
        return success("添加成功");
    }
    @Override
    public JSONObject savePurchaseCommodity(Map<String, String> parameters) {
//        long purchaseID = getLongParameter("purchaseID",-1);
//        long itemID = getLongParameter("itemID",-1);
//        long itemTypeID = getLongParameter("itemTypeID",-1);
//        String itemName = getUTF("itemName",null);
//        String itemTypeTitle = getUTF("itemTypeTitle",null);
//        String purchaseTime = getUTF("purchaseTime",null);
//        int purchaseNumber = getIntParameter("purchaseNumber",-1);
//
//        if(purchaseID == -1){
//            return fail("缺少采购单ID");
//        }else if(itemID == -1){
//            return fail("缺少商品ID");
//        }else if(itemTypeID==-1){
//            return fail("缺少商品类型ID");
//        }else if(purchaseNumber==-1){
//            return fail("缺少采购数量");
//        }else if(itemName == null || itemName.isEmpty()){
//            return fail("缺少商品名称");
//        }else if(itemTypeTitle == null || itemTypeTitle.isEmpty()){
//            return fail("缺少商品类型");
//        }else if(purchaseTime == null || purchaseTime.isEmpty()){
//            return fail("缺少采购日期");
//        }
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("purchaseID", String.valueOf(purchaseID));
//        parameters.put("itemID", String.valueOf(itemID));
//        parameters.put("itemTypeID", String.valueOf(itemTypeID));
//        parameters.put("purchaseNumber", String.valueOf(purchaseNumber));
//        parameters.put("itemName", itemName);
//        parameters.put("itemTypeTitle", itemTypeTitle);
//        parameters.put("purchaseTime", purchaseTime);

        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/savePurchaseCommodity.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoIllegalArgumentException ex){
            throw new XlibaoIllegalArgumentException("远程接口通信异常");
        }

    }

    @Override
    public JSONObject updatePurchase() {
        long id = getLongParameter("id",-1);
        String warehouseCode = getUTF("warehouseCode","");
        long supplierID = getLongParameter("supplierID",-1);
        int status = getIntParameter("status", -1);
        //String [] purchaseID= getHttpServletRequest().getParameterValues("purchaseID");
        String  itemIDs= getUTF("itemIDs","");
        String  itemTypeIDs= getUTF("itemTypeIDs","");
        String  itemNames= getUTF("itemNames","");
        String  itemTypeTitles= getUTF("itemTypeTitles","");
        String  purchaseTimes= getUTF("purchaseTimes","");
        String  purchaseNumbers= getUTF("purchaseNumbers","");


        if(id == -1){
            return fail("缺少采购单ID");
        }else if(warehouseCode == ""){
            return fail("缺少仓库编码");
        }else if(supplierID == -1){
            return fail("缺少供应商ID");
        }else if(status==-1){
            return fail("缺少状态");
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", String.valueOf(id));
        parameters.put("supplierID", String.valueOf(supplierID));
        parameters.put("warehouseCode", warehouseCode);
        parameters.put("status", String.valueOf(status));

        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/updatePurchase.do";
            JSONObject response = executor(url, parameters);
            //删除采购单下的物品
            delPurchaseCommodity(id);
            //添加采购单成功再添加采购物品
            if (response.getIntValue("code")==0) {
                String []  itemIDList= itemIDs.split(CommonUtils.SPLIT_COMMA);
                String [] itemTypeIDList= itemTypeIDs.split(CommonUtils.SPLIT_COMMA);
                String [] itemNameList= itemNames.split(CommonUtils.SPLIT_COMMA);
                String [] itemTypeTitleList= itemTypeTitles.split(CommonUtils.SPLIT_COMMA);
                String [] purchaseTimeList= purchaseTimes.split(CommonUtils.SPLIT_COMMA);
                String [] purchaseNumberList=purchaseNumbers.split(CommonUtils.SPLIT_COMMA);
                for (int i = 0; i < itemIDList.length; i++) {
                    Map<String, String> parameters2 = new HashMap<>();
                    parameters2.put("purchaseID", String.valueOf(id));
                    parameters2.put("itemID", itemIDList[i]);
                    parameters2.put("itemTypeID", itemTypeIDList[i]);
                    parameters2.put("itemName", itemNameList[i]);
                    parameters2.put("itemTypeTitle", itemTypeTitleList[i]);
                    parameters2.put("purchaseTime", purchaseTimeList[i]);
                    parameters2.put("purchaseNumber", purchaseNumberList[i]);
                    //调用添加接口
                    savePurchaseCommodity(parameters2);
                }
            }
        }catch (XlibaoIllegalArgumentException ex){
            throw new XlibaoIllegalArgumentException("远程接口通信异常");
        }
        return success("添加成功");
    }

    @Override
    public JSONObject delPurchaseCommodity(long purchaseID) {
        if(purchaseID == -1){
            return fail("缺少采购单ID");
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("purchaseID", String.valueOf(purchaseID));
        try {
            String url = ConfigFactory.getDomainNameConfig().purchaseRemoteURL + "purchase/delPurchaseCommodity.do";
            JSONObject response = executor(url, parameters);
            return response;
        }catch (XlibaoIllegalArgumentException ex){
            throw new XlibaoIllegalArgumentException("远程接口通信异常");
        }

    }

}
