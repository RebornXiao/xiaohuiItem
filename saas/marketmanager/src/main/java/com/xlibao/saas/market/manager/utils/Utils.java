package com.xlibao.saas.market.manager.utils;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.sun.tools.javac.util.Constants.format;

/**
 * Created by Administrator on 2017/8/11 0011.
 */
public class Utils {

    /**
     * 从json里删除指定价格的key，并将key转为元重新存进去
     */
    public static void changePrice(JSONObject json, String key) {
        Object obj = json.remove(key);
        if (obj != null) {
            long price = Long.parseLong(obj.toString());
            double price2 = (double) price / (double) 100;
            //取2位
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
            String v = df.format(price2);
            json.put(key, v);
        } else {
            json.put(key, "0");
        }
    }

    /**
     * 从json里删除指定日期毫秒值，并将key转为中文日期值
     */
    public static void changeData(JSONObject json, String key) {
        Object obj = json.remove(key);
        String str = obj != null ? obj.toString() : null;
        if (obj != null && str.length() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
            String date = sdf.format(new Date(Long.parseLong(str)));
            json.put(key, date);
        } else {
            json.put(key, "0");
        }
    }

    public static void appendStr(StringBuilder sb, String key, String value) {
        if (value != null && value.length() > 0) {
            sb.append("&").append(key).append("=").append(value);
        }
    }
    public static void appendInt(StringBuilder sb, String key, int value) {
        if (value != -1) {
            sb.append("&").append(key).append("=").append(value);
        }
    }
    public static void appendLong(StringBuilder sb, String key, long value) {
        if (value != -1) {
            sb.append("&").append(key).append("=").append(value);
        }
    }
}
