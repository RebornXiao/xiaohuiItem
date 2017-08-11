package com.xlibao.saas.market.manager;

import com.xlibao.common.BasicWebService;
import com.xlibao.common.exception.XlibaoIllegalArgumentException;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.saas.market.manager.config.LogicConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/7/21.
 */
@Service
@Aspect
public class ModelAndViewAop extends BaseController {

    @Around(value = "execution(* com.xlibao.saas.market.manager.controller.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        try {
            getHttpServletResponse().setHeader("content-type", "text/html");
            return point.proceed(args);
        } catch (XlibaoIllegalArgumentException ex) {
            ex.printStackTrace();
            return createView(ex.getMessage());
        } catch (XlibaoRuntimeException ex) {
            ex.printStackTrace();
            return createView(ex.getMessage());
        } catch (Throwable cause) {
            cause.printStackTrace();
            return createView("系统错误，请稍后重试！");
        }
    }

    ModelAndView createView(String error) {
        Map map = new HashMap<>();
        map.put("error", error);
        return new ModelAndView(LogicConfig.FTL_ERROR, map);
    }
}
