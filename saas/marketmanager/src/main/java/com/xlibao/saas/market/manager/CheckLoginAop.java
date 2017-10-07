package com.xlibao.saas.market.manager;

import com.xlibao.common.exception.XlibaoIllegalArgumentException;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.support.PassportRemoteService;
import com.xlibao.saas.market.manager.config.LogicConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

/**
 * @author chinahuangxc on 2017/7/21.
 */
@Service
@Aspect
public class CheckLoginAop extends BaseController {

    @Around(value = "execution(* com.xlibao.saas.market.manager.controller.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        getHttpServletResponse().setHeader("content-type", "text/html");
        try {
            fillCookieAttributes();
            long pid = getLongParameter("passportId", 0);

            Object passportId = getHttpServletRequest().getSession().getAttribute("passportId");
            if (passportId != null) {
                pid = Long.parseLong((String) passportId);
            }
            if (pid == 0) {
                // 需要登录
                return LogicConfig.FTL_LOGIN;
            }

            // 检测 accessToken 有效期
            Object accessToken = getHttpServletRequest().getSession().getAttribute("accessToken");
            if (accessToken == null) {
                // 需要登录
                return LogicConfig.FTL_LOGIN;
            }
            String token = (String) accessToken;
            try {
                token = PassportRemoteService.changeAccessToken(pid, token);
            } catch (Exception ex) {
                ex.printStackTrace();
                failError(ex.getMessage());
                return LogicConfig.FTL_LOGIN;
            }
            setAccessToken(token);
            return point.proceed(point.getArgs());
        } catch (XlibaoIllegalArgumentException ex) {
            ex.printStackTrace();
            failError(ex.getMessage());
            return LogicConfig.FTL_ERROR;
        } catch (XlibaoRuntimeException ex) {
            ex.printStackTrace();
            failError(ex.getMessage());
            return LogicConfig.FTL_ERROR;
        } catch (Throwable cause) {
            cause.printStackTrace();
            failError("系统错误，请稍后重试！");
            return LogicConfig.FTL_ERROR;
        }
    }

    private void failError(String error) {
        getHttpServletRequest().setAttribute("error", error);
    }

    private void fillCookieAttributes() {
        // 获取浏览器访问访问服务器时传递过来的cookie数组
        Cookie[] cookies = getHttpServletRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    getHttpServletRequest().getSession().setAttribute("accessToken", cookie.getValue());
                }
                if ("passportId".equals(cookie.getName())) {
                    getHttpServletRequest().getSession().setAttribute("passportId", cookie.getValue());
                }
            }
        }
    }
}