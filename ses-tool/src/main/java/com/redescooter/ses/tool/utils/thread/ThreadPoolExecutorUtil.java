package com.redescooter.ses.tool.utils.thread;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor线程池工具类
 * @author assert
 * @date 2020/11/24 16:38
 */
public class ThreadPoolExecutorUtil {

    /**
     * 核心线程池大小
     */
    private static int corePoolSize = 50;

    /**
     * 最大线程池大小
     */
    private static int maximumPoolSize = 200;

    /**
     * 空闲线程过期时间
     */
    private static long keepAliveTime = 10L;


    private ThreadPoolExecutorUtil() {}

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingDeque<>()
    );

    public static ExecutorService getThreadPool() {
        return threadPoolExecutor;
    }

}
