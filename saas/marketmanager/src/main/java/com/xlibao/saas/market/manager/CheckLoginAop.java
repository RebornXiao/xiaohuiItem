package com.xlibao.saas.market.manager;

import com.xlibao.common.BasicWebService;
import com.xlibao.common.exception.XlibaoIllegalArgumentException;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.support.PassportRemoteService;
import com.xlibao.saas.market.manager.config.LogicConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/7/21.
 */
@Service
@Aspect
public class CheckLoginAop extends BaseController {

    @Around(value = "execution(* com.xlibao.saas.market.manager.controller.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        getHttpServletResponse().setHeader("content-type", "text/html");
        try {

//            long passportId = getLongParameter("passportId", 0);
//
//            if(passportId == 0) {
//                //需要登录
//                //fali("请重新登录");
//                return LogicConfig.FTL_LOGIN;
//            }
//
//            String accessToken = getUTF("accessToken");
//
//            //检测 accessToken 有效期
//            try {
//                accessToken = PassportRemoteService.changeAccessToken(passportId, accessToken);
//            } catch (Exception ex) {
//                fali("登录有效期已过，请重新登录");
//                return LogicConfig.FTL_LOGIN;
//            }
//
//            setAccessToken(accessToken);

            return point.proceed(args);

        } catch (XlibaoIllegalArgumentException ex) {
            ex.printStackTrace();
            fali(ex.getMessage());
            return LogicConfig.FTL_ERROR;
        } catch (XlibaoRuntimeException ex) {
            ex.printStackTrace();
            fali(ex.getMessage());
            return LogicConfig.FTL_ERROR;
        } catch (Throwable cause) {
            cause.printStackTrace();
            fali("系统错误，请稍后重试！");
            return LogicConfig.FTL_ERROR;
        }
    }

    void fali(String error) {
        HttpServletRequest request = getHttpServletRequest();
        request.setAttribute("error", error);
    }
}
