package com.xlibao.saas.market.manager;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zhumg on 8/9.
 */
public class BaseFreeMarkerView extends FreeMarkerView {

    private static final String CONTEXT_PATH = "base";

    @Override
    protected void exposeHelpers(Map<String, Object> model,
                                 HttpServletRequest request) throws Exception
    {
        //如果有登录过，则设置用户名，用户ID
        Object obj = request.getSession().getAttribute("loginUserName");
        if(obj != null) {
            model.put("userName", (String)obj);
        } else {
            model.put("userName", "未登录");
        }
        obj = request.getSession().getAttribute("loginUserId");
        if(obj != null) {
            model.put("userId", (String)obj);
        } else {
            model.put("userId", "0");
        }

        //设置base
        model.put(CONTEXT_PATH, request.getContextPath());
        super.exposeHelpers(model, request);
    }
}