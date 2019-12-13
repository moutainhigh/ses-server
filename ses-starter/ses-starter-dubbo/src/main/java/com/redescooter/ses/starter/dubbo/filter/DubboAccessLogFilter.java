package com.redescooter.ses.starter.dubbo.filter;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.starter.dubbo.InterfaceLog;
import com.redescooter.ses.starter.dubbo.annotation.RecordDubboLog;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DubboAccessLogFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(DubboAccessLogFilter.class);
    public static final String LINE_SEPARATOR = System.getProperty("line.separator", "\r\n");

    private static final List<String> IGNORE_RESULT_CACHE = new ArrayList<>();

    private static final int RESULT_MAXIMUM_LENGTH = 512;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        StopWatch sw = new StopWatch();
        Throwable exception = null;
        Result result = null;
        try {
            sw.start();
            result = invoker.invoke(invocation);
            if (result != null) {
                exception = result.getException();
            }
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            sw.stop();
            boolean printInArgs = true;
            boolean printOutArgs = true;
            if (invoker.getInterface().isAnnotationPresent(RecordDubboLog.class)) {
                try {
                    printInArgs = invoker.getInterface().getAnnotation(RecordDubboLog.class).printInArgs();
                    printOutArgs = invoker.getInterface().getAnnotation(RecordDubboLog.class).printOutArgs();

                    //只需要公开的方法并且接口中声明的，否则暴露不出去
                    Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                    if (method.isAnnotationPresent(RecordDubboLog.class)) {
                        printInArgs = method.getAnnotation(RecordDubboLog.class).printInArgs();
                        printOutArgs = method.getAnnotation(RecordDubboLog.class).printOutArgs();
                    }
                } catch (Exception e) {
                    exception = e;
                }
            }
            InterfaceLog interfaceLog = buildBaseInterfaceLog(invoker, invocation);
            interfaceLog.setCostTime(sw.getLastTaskTimeMillis());

            if (printInArgs) {
                Class[] types = invocation.getParameterTypes();
                if (types != null && types.length > 0) {
                    interfaceLog.setParamTypes(Arrays.stream(types).map(Class::getName).collect(Collectors.toList()).toArray(new String[types.length]));
                }

                interfaceLog.setParamValues(
                        Arrays.stream(invocation.getArguments()).map(arg -> {
                            try {
                                return JSON.toJSONString(arg);
                            } catch (Exception e) {
                                return "paramValue can't to json, " + arg;
                            }
                        }).collect(Collectors.toList()).toArray());
            }
            if (!IGNORE_RESULT_CACHE.contains(interfaceLog.getMethodName())) {
                if (printOutArgs && result != null) {
                    String resultJson = "";
                    try {
                        resultJson = JSON.toJSONString(result.getValue());
                        if (resultJson.length() > RESULT_MAXIMUM_LENGTH) {
                            IGNORE_RESULT_CACHE.add(interfaceLog.getMethodName());
                        }
                    } catch (Exception e) {
                        interfaceLog.setResultValue("result value can't to json, " + result);
                    }
                    interfaceLog.setResultValue(resultJson);
                }
            }

            if (exception != null) {
                StringBuilder sb = new StringBuilder();
                Arrays.stream(exception.getStackTrace()).forEach(ste -> sb.append(
                        ste.getClassName()).append(".")
                        .append(ste.getMethodName()).append(":")
                        .append(ste.getLineNumber())
                        .append(LINE_SEPARATOR));
                interfaceLog.setStackTraces(sb.toString());
                interfaceLog.setThrowableClass(exception.getClass().getName());
                interfaceLog.setExceptionMsg(exception.getMessage());

                log.error(interfaceLog.toString(), exception);

            } else {
                log.info(interfaceLog.toString());
            }
        }
        return result;
    }

    private InterfaceLog buildBaseInterfaceLog(Invoker<?> invoker, Invocation invocation) {
        InterfaceLog interfaceLog = new InterfaceLog();
        interfaceLog.setMethodName(invoker.getInterface().getName() + "." + invocation.getMethodName());
        interfaceLog.setReceiverName(invoker.getUrl().getParameter("application"));
        interfaceLog.setSrvGroup(invoker.getUrl().getParameter("group"));
        interfaceLog.setVersion(invoker.getUrl().getParameter("version"));

        interfaceLog.setSenderName(RpcContext.getContext().getAttachment("SENDER_APP_NAME"));
        interfaceLog.setRequestId(RpcContext.getContext().getAttachment("requestId"));

        interfaceLog.setSenderHost(RpcContext.getContext().getRemoteHost() + ":" + RpcContext.getContext().getRemotePort());
        interfaceLog.setReceiverHost(RpcContext.getContext().getLocalHost() + ":" + RpcContext.getContext().getLocalPort());
        return interfaceLog;
    }
}