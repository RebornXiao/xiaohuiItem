package com.xlibao.saas.market.controller.openapi;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.service.market.MarketService;
import com.xlibao.saas.market.service.market.ShelvesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chinahuangxc on 2017/7/15.
 */
@Controller
@RequestMapping(value = "/market/openapi")
public class MarketOpenApiController {

    @Autowired
    private MarketService marketService;
    @Autowired
    private ShelvesService shelvesService;

    /***
     * <pre>
     *     <b>找到商店</b>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "findMarket")
    public JSONObject findMarket() {
        return marketService.findMarket();
    }

    /**
     * <pre>
     *     <b>展示可用的商店</b>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "showMarkets")
    public JSONObject showMarkets() {
        return marketService.showMarkets();
    }

    /**
     * <pre>
     *     <b>过滤商店(寻找商店)</b>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "filterMarket")
    public JSONObject filterMarket() {
        return marketService.filterMarket();
    }

    /**
     * <pre>
     *     <b>获取组(走道)、货架(单元)、层(楼层)的值</b>
     *
     *     <b>访问地址：</b>http://domainName/market/open/getShelvesMarks.do
     *     <b>访问方式：</b>GET/POST 推荐使用POST
     *
     *     <b>参数：</b>
     *          <b>marketId</b> - long 商店ID，必填参数。
     *          <b>groupCode</b> - String 组编码，当 <b>shelvesType</b>为{@link com.xlibao.saas.market.service.market.ShelvesTypeEnum#GROUP}或{@link com.xlibao.saas.market.service.market.ShelvesTypeEnum#UNIT}时必填
     *          <b>unitCode</b> - String 单元编码，当 <b>shelvesType</b>为{@link com.xlibao.saas.market.service.market.ShelvesTypeEnum#UNIT}时必填
     *          <b>shelvesType</b> - int 获取的货架类型，具体参考：{@link com.xlibao.saas.market.service.market.ShelvesTypeEnum}
     *
     *     <b>返回：</b>
     *          <b>datas</b> - JSONArray 编码组，每个元素为String，即对应请求的数据集合
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "getShelvesMarks")
    public JSONObject getShelvesMarks() {
        return shelvesService.getShelvesMarks();
    }

    /**
     * <pre>
     *     <b>加载弹夹的数据</b>
     *
     *     <b>访问地址：</b>http://domainName/market/open/loaderClipDatas.do
     *     <b>访问方式：</b>GET/POST 推荐使用POST
     *
     *     <b>参数：</b>
     *          <b>marketId</b> - long 商店ID，必填参数。
     *          <b>groupCode</b> - String 组编码，必填参数。
     *          <b>unitCode</b> - String 单元编码，必填参数。
     *          <b>floorCode</b> - String 楼层编码，必填参数。
     *          <b>pageIndex</b> - int 页码，非必填参数；默认值为：{@link com.xlibao.common.GlobalConstantConfig#DEFAULT_PAGE_INDEX}
     *          <b>pageSize</b> - int 单页数量，非必填参数；默认值为：{@link com.xlibao.common.GlobalConstantConfig#DEFAULT_PAGE_SIZE}
     *
     *     <b>返回：</b>
     *          <b>datas</b> - JSONArray 弹夹数据集合，每个元素为JSONObject结构
     *              <b>locationCode</b> - String 弹夹的完整编码，如：01010101
     *              <b>itemTemplateId</b> - long 商品模版ID
     *              <b>itemName</b> - String 当前存放的商品名称
     *              <b>itemQuantity</b> - int 当前的商品库存
     *              <b>barcode</b> - String 当前存放的商品条码
     *              <b>unitName</b> - String 当前存放的商品单位
     *              <b>taskId</b> - long 任务ID；当没有存在预操作任务时为0，否则为对应的任务ID
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "loaderClipDatas")
    public JSONObject loaderClipDatas() {
        return shelvesService.loaderClipDatas();
    }

    /**
     * <pre>
     *     <b>获取原来存在任务数据</b>
     *
     *     <b>访问地址：</b>http://domainName/market/open/checkPrepareActionTask.do
     *     <b>访问方式：</b>GET/POST 推荐使用POST
     *
     *     <b>参数：</b>
     *          <b>taskId</b> - long 任务ID，必填参数；该参数必定大于0
     *
     *     <b>返回：</b>
     *          <b>taskId</b> - long 任务ID
     *          <b>locationCode</b> - String 弹夹的完整编码，如：01010101
     *          <b>itemTemplateId</b> - long 商品模版ID
     *          <b>itemName</b> - String 商品名
     *          <b>itemQuantity</b> - int 商品数量
     *          <b>barcode</b> - String 商品的条码
     *          <b>unitName</b> - String 当前存放的商品单位
     *          <b>status</b> - int 任务状态，参考：{@link com.xlibao.saas.market.service.item.PrepareActionStatusEnum}
     *          <b>hopeExecutorDate</b> - String 期望执行的日期
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "checkPrepareActionTask")
    public JSONObject checkPrepareActionTask() {
        return shelvesService.checkPrepareActionTask();
    }

    /**
     * <pre>
     *     <b>新增预操作行为</b>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "prepareAction")
    public JSONObject prepareAction() {
        return shelvesService.prepareAction();
    }

    /**
     * <pre>
     *     <b>扫描商品获取上架任务</b>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "scanShelvesTask")
    public JSONObject scanShelvesTask() {
        return shelvesService.scanShelvesTask();
    }
}