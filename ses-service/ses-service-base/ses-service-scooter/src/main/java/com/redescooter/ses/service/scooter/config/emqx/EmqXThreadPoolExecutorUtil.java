package com.redescooter.ses.service.scooter.config.emqx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * EMQ X数据上报所用线程池工具类
 * @author assert
 * @date 2020/12/22 11:19
 */
public class EmqXThreadPoolExecutorUtil {

    /**
     * 核心线程池大小
     */
    private static int corePoolSize = 100;

    /**
     * 最大线程池大小
     */
    private static int maximumPoolSize = 200;

    /**
     * 空闲线程过期时间
     */
    private static long keepAliveTime = 10L;


    private EmqXThreadPoolExecutorUtil() {}

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingDeque<>()
    );

    public static ExecutorService getThreadPool() {
        return threadPoolExecutor;
    }

}
