package com.xlibao.saas.market.manager;

import com.xlibao.common.BasicWebService;
import com.xlibao.common.exception.XlibaoIllegalArgumentException;
import com.xlibao.common.exception.XlibaoRuntimeException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

/**
 * @author chinahuangxc on 2017/7/21.
 */
@Service
@Aspect
public class ModelAndViewAop extends BasicWebService {

    @Around(value = "execution(* com.xlibao.saas.market.manager.controller.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        try {
            getHttpServletResponse().setHeader("content-type", "text/html");
            return point.proceed(args);
        } catch (XlibaoIllegalArgumentException ex) {
            return fail(ex.getMessage());
        } catch (XlibaoRuntimeException ex) {
            return fail(ex.getCode(), ex.getMessage());
        } catch (Throwable cause) {
            return fail("系统错误，请稍后重试！");
        }
    }
}
