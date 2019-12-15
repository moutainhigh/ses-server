package com.redescooter.ses.app.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class AccessLogUtils {

    public static String getErrorLog(HttpServletRequest request) {
        return buildLog(request, "ERROR");
    }

    public static String getSuccessLog(HttpServletRequest request) {
        return buildLog(request, "SUCCESS");
    }

    public static String getFailureLog(HttpServletRequest request) {
        return buildLog(request, "FAILURE");
    }

    public static void printException(String requestURI, String errorMsg, Throwable e) {
        log.error(requestURI + " throw Exception: " + errorMsg, e);
    }

    private static String buildHeaderLog(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headerMap.put(headerName, headerValue);
        }
        String headerLog = JSON.toJSONString(headerMap);
        return headerLog;
    }

    private static StringBuilder buildBodyLog(HttpServletRequest request) {
        Map<String, ?> parameterMap = request.getParameterMap();
        StringBuilder logBuffer = new StringBuilder();
        List<String> keys = new ArrayList<String>(parameterMap.keySet());
        for (int i = 0; i < keys.size(); i++) {
            try {
                String key = keys.get(i);
                logBuffer.append(key);
                logBuffer.append("=");
                logBuffer.append(buildValueLog(parameterMap, key));
                logBuffer.append((i == keys.size() - 1) ? "" : "&");
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return logBuffer;
    }

    private static StringBuilder buildValueLog(Map<String, ?> parameterMap, String key) {
        StringBuilder valueBuffer = new StringBuilder();
        Object obj = parameterMap.get(key);
        if (obj instanceof String) {
            valueBuffer.append(String.valueOf(obj));
        } else if (obj instanceof String[]) {
            String[] values = (String[]) parameterMap.get(key);
            if (values.length > 1) {
                valueBuffer.append("[");
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    valueBuffer.append(value);
                    valueBuffer.append((i == values.length - 1) ? "" : ",");
                }
                valueBuffer.append("]");
            } else {
                String value = values[0];
                valueBuffer.append(value);
            }
        }
        return valueBuffer;
    }

    private static String buildLog(HttpServletRequest request, String status) {
        try {
            long startTime = (long) request.getAttribute("timestamp");
            String uri = request.getRequestURI();
            String method = request.getMethod();
            String headerLog = buildHeaderLog(request);
            StringBuilder bodyLog = buildBodyLog(request);
            return formatLog(status, method, uri, startTime, headerLog,
                    bodyLog);
        } catch (Exception e) {
            log.error("unexpected exception while build log for uri({}): {}", request.getRequestURI(), e);
            return null;
        }
    }


    private static String formatLog(String result, String method, String uri, long startTime, String headerLog, StringBuilder bodyLog) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String requestTime = df.format(new Date(startTime));
        long executeTime = (new Date()).getTime() - startTime;
        StringBuilder logBuffer = new StringBuilder();
        logBuffer.append(result).append("||");
        logBuffer.append(method).append("||");
        logBuffer.append(uri).append("||");
        logBuffer.append(requestTime).append("||");
        logBuffer.append(executeTime).append("ms").append("||");
        logBuffer.append("header:").append(headerLog).append("||");
        logBuffer.append("body:").append(bodyLog);
        return logBuffer.toString();
    }

    public static void printException(String requestURI, Exception e) {
        printException(requestURI, null, e);
    }
}
