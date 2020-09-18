package com.redescooter.ses.web.ros.service.admin;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.annotation.LogAnnotation;
import com.redescooter.ses.web.ros.dao.sys.StaffServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysLog;
import com.redescooter.ses.web.ros.service.base.OpeSysLogService;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * @ClassNameAdvice
 * @Description 统一捕获系统日志（除了登陆日志）
 * @Author Aleks
 * @Date2020/9/15 17:27
 * @Version V1.0
 **/
@Aspect
@Component
@Slf4j
public class Advice {

    @Autowired
    private OpeSysLogService opeSysLogService;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private StaffServiceMapper staffServiceMapper;


    @Around(value = "@annotation(com.redescooter.ses.api.common.annotation.LogAnnotation)")
    public Object testLog(ProceedingJoinPoint joinPoint) throws Throwable {

        // 日志开始的时间
        LocalDateTime startTime = LocalDateTime.now();
        log.info("开始时间：" + startTime);
        OpeSysLog sysLog = new OpeSysLog();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Integer code = null;
        sysLog.setOpMethod(methodSignature.getDeclaringTypeName() + "." + methodSignature.getName());
        // 请求的参数信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        sysLog.setRequestParam(JSON.toJSONString(request.getParameterMap()));
        sysLog.setLoginIp(request.getRemoteAddr());
        sysLog.setRequestAddress(request.getRequestURI());
        sysLog.setRequestType(request.getMethod());
        // 获取操作用户的信息（只能从缓存里拿）
        String token = request.getParameter("token");
        if(!Strings.isNullOrEmpty(token)){
            Map<String, String> map = jedisCluster.hgetAll(token);
            if(map != null && map.size() > 0){
                Long userId = Long.parseLong(map.get("userId"));
                StaffResult userMsg = staffServiceMapper.staffDetail(userId);
                if(userMsg != null){
                    sysLog.setOpUserName(userMsg.getFullName());
                    sysLog.setOpUserCode(userMsg.getCode());
                    sysLog.setOpUserDeptName(userMsg.getDeptName());
                    sysLog.setCreatedBy(userMsg.getId());
                    sysLog.setUpdatedBy(userMsg.getId());
                    sysLog.setLogType(2);
                }
            }else {
                // 这种情况就是登陆了
                sysLog.setLogType(1);
            }
        }else {
            // 这种情况就是登陆了
            sysLog.setLogType(1);
        }

        LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
        String module = logAnnotation.module();
        if (StringUtils.isEmpty(module)) {
            ApiOperation apiOperation = methodSignature.getMethod().getDeclaredAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                module = apiOperation.value();
                sysLog.setOpModul(module);
                code = apiOperation.code();
            }
        }
        if (StringUtils.isEmpty(module)) {
            throw new RuntimeException("没有指定日志module");
        }
        try {
            if (code != null && !code.equals(200)) {
                String id = null;
                Object object = joinPoint.getArgs()[0];

                if (code % 2 == 0) {
                    id = (String) (object);

                } else if (code % 2 == 1) {

                }

            }
            Object object = joinPoint.proceed();
            if(sysLog.getLogType() == 1){
                // 如果是登陆，成功之后补全操作人的信息
                Map<String,String> map = (Map<String, String>) JSON.toJSON(object);
                Map<String,String> result = (Map<String, String>) JSON.toJSON(map.get("result"));
                String resToken = result.get("token");
                Map<String, String> resMap = jedisCluster.hgetAll(resToken);
                if(resMap != null && resMap.size() > 0){
                    Long userId = Long.parseLong(resMap.get("userId"));
                    StaffResult userMsg = staffServiceMapper.staffDetail(userId);
                    if(userMsg != null){
                        sysLog.setOpUserName(userMsg.getFullName());
                        sysLog.setOpUserCode(userMsg.getCode());
                        sysLog.setOpUserDeptName(userMsg.getDeptName());
                        sysLog.setCreatedBy(userMsg.getId());
                        sysLog.setUpdatedBy(userMsg.getId());
                    }
                }
            }
            sysLog.setResponseParam(JSON.toJSONString(object));
            sysLog.setIfSuccess(1);
            return object;
        } catch (Exception e) {
            // 返回的异常信息
            sysLog.setExpMsg(e.getMessage());
            sysLog.setResponseParam(JSON.toJSONString(e));
            sysLog.setIfSuccess(0);
            throw e;
        } finally {
            log.info("不管这次请求成功还是失败，都要走这里");
            // 日志开始的时间
            LocalDateTime endTime = LocalDateTime.now();
            log.info("开始时间：" + endTime);
            Duration duration = Duration.between(startTime, endTime);
            sysLog.setTimeConsum(duration.toMillis());
            sysLog.setCreatedTime(new Date());
            sysLog.setUpdatedTime(new Date());
            sysLog.setId(System.currentTimeMillis());
            opeSysLogService.save(sysLog);
        }

    }


}
