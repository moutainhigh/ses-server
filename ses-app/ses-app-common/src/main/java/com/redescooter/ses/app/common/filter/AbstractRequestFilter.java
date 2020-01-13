package com.redescooter.ses.app.common.filter;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.CountryEnums;
import com.redescooter.ses.api.common.vo.base.ReqHeader;
import com.redescooter.ses.app.common.exception.HttpHeaderException;
import com.redescooter.ses.tool.utils.IpUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractRequestFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) {

        log.info("服务启动,调用过滤器Filter初始化方法init()..........");

    }

    protected abstract AppIDEnums getAppId();

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HashMap parameterMap = new HashMap(request.getParameterMap());
        parameterMap.put("requestId", request.getAttribute("requestId"));
        //String clientIp = getClientIp(request, true, "x-forwarded-for");
        parameterMap.put("clientIp", IpUtils.getIpAddr(request));
        parameterMap.put("timestamp", request.getAttribute("timestamp"));
        parameterMap.put("appId", getAppId().getValue());
        parameterMap.put("systemId", getAppId().getSystemId());
        commonHeaderProcess(request, parameterMap);
        ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(request, parameterMap);
        chain.doFilter(wrapRequest, response);
    }

    private void commonHeaderProcess(HttpServletRequest request, HashMap parameterMap) {
        if (StringUtils.isNotBlank(request.getHeader("CHP"))) {
            String chp = "";
            try {
                chp = URLDecoder.decode(request.getHeader("CHP"), "UTF-8");
                if (StringUtils.isNotBlank(chp)) {
                    ReqHeader reqHeader = JSON.parseObject(chp, ReqHeader.class);
                    if (StringUtils.isNotBlank(reqHeader.getRequestId())) {
                        parameterMap.put("requestId", reqHeader.getRequestId());
                    }
                    parameterMap.put("token", reqHeader.getToken());
                    processCountry(parameterMap, reqHeader);
                    parameterMap.put("timeZone", reqHeader.getTimeZone());
                    parameterMap.put("clientType", reqHeader.getClientType() == null ? "PC" : reqHeader.getClientType());
                    parameterMap.put("version", reqHeader.getVersion());
                    if ((!parameterMap.containsKey("lat")) ||
                            (parameterMap.get("lat") == null) ||
                            (StringUtils.isNotEmpty(parameterMap.get("lat").toString())) ||
                            ((!parameterMap.containsKey("lng"))) ||
                            ((parameterMap.get("lng") == null)) ||
                            (StringUtils.isNotEmpty(parameterMap.get("lng").toString()))) {
                        parameterMap.put("lat", reqHeader.getLat());
                        parameterMap.put("lng", reqHeader.getLng());
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                log.error("header CHP invalid:" + chp, e);
                throw new HttpHeaderException("parse CHP header failure:", e);
            }
        }
    }

    private void processCountry(HashMap parameterMap, ReqHeader reqHeader) {
        CountryEnums country = CountryEnums.findCountryByLanguage(reqHeader.getLanguage());
        if (country == null) {
            throw new HttpHeaderException("language illegal");
        }
        parameterMap.put("country", country.getCountry());
        parameterMap.put("language", country.getLanguage());
    }


    @Override
    public void destroy() {

    }

    /**
     * 获取客户端ip
     */
    public static String getClientIp(HttpServletRequest request, boolean reverseProxy, String proxyHeader) {
        if (!reverseProxy) {
            return request.getRemoteAddr();
        }
        String ipHeader = null;
        if (StringUtils.isNotEmpty(proxyHeader)) {
            ipHeader = request.getHeader(proxyHeader);
        }
        if (!isIpHeader(ipHeader)) {
            String[] commonReverseProxyHeaderNanes = {"x-forwarded-for", "x-real-ip", "X-Forwarded-For", "Proxy-Client-IP",
                    "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR",};
            int count = 0;
            while (!isIpHeader(ipHeader) && count < commonReverseProxyHeaderNanes.length) {
                ipHeader = request.getParameter(commonReverseProxyHeaderNanes[count++]);
            }
        }
        if (!isIpHeader(ipHeader)) {
            return "0.0.0.0";
        } else {
            return ipHeader;
        }
    }

    private static boolean isIpHeader(String ipHeader) {
        return StringUtils.isNotEmpty(ipHeader) && !"unknown".equalsIgnoreCase(ipHeader);
    }

}
