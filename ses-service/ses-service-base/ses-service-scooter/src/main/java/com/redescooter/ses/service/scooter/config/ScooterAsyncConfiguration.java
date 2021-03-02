package com.redescooter.ses.service.scooter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 车辆业务异步线程池
 * @author assert
 * @date 2020/11/20 14:33
 */
@Configuration
public class ScooterAsyncConfiguration {

    private static int CORE_POOL_SIZE = 50;

    private static int MAX_POOL_SIZE = 200;

    private static int QUEUE_CAPACITY = 100;

    private static int KEEP_ALIVE_SECONDS = 300;

    @Bean("scooterReportedSyncExecutor")
    public TaskExecutor emqSyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(CORE_POOL_SIZE);
        // 设置最大线程数
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        // 设置队列容量
        executor.setQueueCapacity(QUEUE_CAPACITY);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        // 设置默认线程名称
        executor.setThreadNamePrefix("async-job-thread-");
        // 设置拒绝策略rejection-policy：当pool已经达到max size的时候，如何处理新任务 CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

}
