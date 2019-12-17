package com.redescooter.ses.app.common.filter;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.exception.BaseException;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.app.common.exception.HttpHeaderException;
import com.redescooter.ses.tool.utils.AccessLogUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 公共请求拦截器
 */
@Slf4j
public class LogAndExceptionFilter implements Filter {

    private static final Charset UTF8 = StandardCharsets.UTF_8;

    // 不需要做日志记录的uri
    private Set<String> blackUris = new HashSet<>();

    @Override
    public void init(FilterConfig arg0) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        httpServletRequest.setAttribute("timestamp", (new Date()).getTime());
        httpServletRequest.setAttribute("requestId", UUID.randomUUID().toString().replaceAll("-", ""));
        try {
            if (!uriNeedLog(httpServletRequest.getRequestURI())) {
                // 移交下一个filter处理
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            filterChain.doFilter(servletRequest, servletResponse);
            // 成功访问日志
            log.info(AccessLogUtils.getSuccessLog(httpServletRequest));
        } catch (HttpHeaderException hhe) {
            //AbstractRequestFilter 会对HTTP 头进行解析并填充body，有可能出现解析异常，故在此处理。
            log.error(AccessLogUtils.getErrorLog(httpServletRequest), hhe);
            returnExceptionReponseToClient(httpServletRequest, (HttpServletResponse) servletResponse, getErrorResponse(BaseException.DEFAULE_ERRORCODE, BaseException.DEFAULT_ERRORMSG));
        } catch (Exception e) {
            BaseException be = getBaseException(e);
            if (be != null) {
                // 可控性业务异常
                log.error(AccessLogUtils.getFailureLog(httpServletRequest), be);
                returnExceptionReponseToClient(httpServletRequest, (HttpServletResponse) servletResponse, getErrorResponse(be.getErrorCode(), be.getErrorMessage()));
            } else {
                // 非可控性异常
                log.error(AccessLogUtils.getErrorLog(httpServletRequest), e);
                returnExceptionReponseToClient(httpServletRequest, (HttpServletResponse) servletResponse, getErrorResponse(BaseException.DEFAULE_ERRORCODE, BaseException.DEFAULT_ERRORMSG));
            }
        }
    }

    private String getErrorResponse(String errorCode, String errorMessage) {
        Response<String> response = new Response<>();
        response.setErrorCode(errorCode);
        response.setErrorMsg(errorMessage);
        return JSON.toJSONString(response);
    }

    private BaseException getBaseException(Exception e) {
        if (e instanceof BaseException) {
            return (BaseException) e;
        } else {
            for (Throwable cause = e.getCause(); cause != null; cause = cause.getCause()) {
                if (cause instanceof BaseException) {
                    return (BaseException) cause;
                }
            }
        }
        return null;
    }

    private void returnExceptionReponseToClient(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String response) {
        httpServletResponse.setContentType("application/json;charset=" + UTF8);
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
            out.print(response);
            httpServletResponse.flushBuffer();
        } catch (Exception e) {
            log.error("unexpected Exception while returnExceptionReponseToClient to uri({}): {}", httpServletRequest.getRequestURI(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private boolean uriNeedLog(String requestURI) {
        return !blackUris.contains(requestURI);
    }

    private void initBlackUris(Properties properties) {
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            if (name.startsWith("/")) {
                blackUris.add(name);
            }
        }
    }

    @Override
    public void destroy() {
    }

}
