package com.xlibao.common.constant.order;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/2/9.
 */
public enum OrderStatusEnum {

    /** 1、默认--下单 */
    ORDER_STATUS_DEFAULT(1, "待支付"),
    /** 2、取消 */
    ORDER_STATUS_CANCEL(2, "取消"),
    /** 4、失效 */
    ORDER_STATUS_INVALID(4, "失效"),
    /** 8、已支付 */
    ORDER_STATUS_PAYMENT(8, "已支付"),
    /** 16、已接单 */
    ORDER_STATUS_ACCEPT(16, "已接单"),
    /** 32、发货中 */
    ORDER_STATUS_DELIVER(32, "发货中"),
    /** 64、配送中 */
    ORDER_STATUS_DISTRIBUTION(64, "配送中"),
    /** 128、送达 */
    ORDER_STATUS_ARRIVE(128, "送达"),
    /** 256、已完成 */
    ORDER_STATUS_CONFIRM(256, "已完成"),
    /** 512、已收款 */
    ORDER_STATUS_RECEIVABLES(512, "已收款"),
    /** 1024、分批配送 */
    ORDER_STATUS_BATCH(1024, "分批配送"),
    /** 2048、退款 */
    ORDER_STATUS_REFUND(2048, "退款"),
    ;

    private int key;
    private String value;

    OrderStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    private static final Map<Integer, OrderStatusEnum> orderStatusEnums = new HashMap<>();

    static {
        OrderStatusEnum[] orderStatusEnumArray = OrderStatusEnum.values();
        for (OrderStatusEnum orderStatusEnum : orderStatusEnumArray) {
            orderStatusEnums.put(orderStatusEnum.getKey(), orderStatusEnum);
        }
    }

    public static OrderStatusEnum getOrderStatusEnum(int key) {
        return orderStatusEnums.get(key);
    }
}
