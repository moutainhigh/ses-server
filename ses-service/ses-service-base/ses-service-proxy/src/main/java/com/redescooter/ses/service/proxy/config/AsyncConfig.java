package com.redescooter.ses.service.proxy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * description: AsyncConfig
 *
 * @描述 通过使用ThreadPoolTaskExecutor创建了一个线程池，同时设置了以下这些参数：
 * 核心线程数10：线程池创建时候初始化的线程数
 * 最大线程数20：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
 * 缓冲队列200：用来缓冲执行任务的队列
 * 允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
 * 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
 * 线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
 * author: jerry.li
 * create: 2019-05-17 10:35
 */

@Slf4j
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Bean("proxy-getAsyncExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(20);//当前线程数
        threadPool.setMaxPoolSize(100);// 最大线程数
        threadPool.setQueueCapacity(20);//线程池所使用的缓冲队列
        threadPool.setWaitForTasksToCompleteOnShutdown(true);//等待任务在关机时完成--表明等待所有线程执行完
        threadPool.setAwaitTerminationSeconds(60 * 15);// 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPool.setThreadNamePrefix("RedEExecutor-");//  线程名称前缀
        threadPool.initialize(); // 初始化
        log.info("--------------------------》》》开启异常线程池");
        return threadPool;
    }

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数10：线程池创建时候初始化的线程数
        executor.setCorePoolSize(4000);
        // 最大线程数20：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(20000);
        // 缓冲队列200：用来缓冲执行任务的队列
        executor.setQueueCapacity(2000);
        // 允许线程的空闲时间60秒：当超过了核心线程数之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(30);
        // 线程池名的前缀：设置好了之后可以方便定位处理任务所在的线程池
        executor.setThreadNamePrefix("taskExecutor-");
        /*
        线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，
        当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；
        如果执行程序已关闭，则会丢弃该任务
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        executor.setAwaitTerminationSeconds(600);
        return executor;
    }

//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return new MyAsyncExceptionHandler();
//    }
//
//    /**
//     * 自定义异常处理类
//     *
//     * @author jerry
//     */
//    class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
//
//        //手动处理捕获的异常
//        @Override
//        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
//            System.out.println("-------------》》》捕获线程异常信息");
//            log.info("Exception message - " + throwable.getMessage());
//            log.info("Method value - " + method.getName());
//            for (Object param : obj) {
//                log.info("Parameter value - " + param);
//            }
//        }
//
//    }

}