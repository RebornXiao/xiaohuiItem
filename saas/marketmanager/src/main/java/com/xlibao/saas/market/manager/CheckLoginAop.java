package com.xlibao.saas.market.manager;

import com.xlibao.common.exception.XlibaoIllegalArgumentException;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.support.PassportRemoteService;
import com.xlibao.saas.market.manager.config.LogicConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

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
            long pid = getLongParameter("passportId", 0);

            Object passportId = getSessionAttribute("passportId");
            if (passportId != null) {
                pid = Long.parseLong((String) passportId);
            }
            if (pid == 0) {
                // 需要登录
                return LogicConfig.FTL_LOGIN;
            }

            // 检测 accessToken 有效期
            Object accessToken = getSessionAttribute("accessToken");
            if (accessToken == null) {
                // 需要登录
                return LogicConfig.FTL_LOGIN;
            }
            String token = (String) accessToken;
            try {
                token = PassportRemoteService.changeAccessToken(pid, token);
            } catch (Exception ex) {
                fail(ex.getMessage());
                return LogicConfig.FTL_LOGIN;
            }
            setAccessToken(token);
            return point.proceed(args);
        } catch (XlibaoIllegalArgumentException ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
            return LogicConfig.FTL_ERROR;
        } catch (XlibaoRuntimeException ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
            return LogicConfig.FTL_ERROR;
        } catch (Throwable cause) {
            cause.printStackTrace();
            fail("系统错误，请稍后重试！");
            return LogicConfig.FTL_ERROR;
        }
    }
}