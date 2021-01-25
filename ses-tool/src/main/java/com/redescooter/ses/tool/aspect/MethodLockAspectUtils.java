package com.redescooter.ses.tool.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName:MethodLockAspectUtils
 * @description: MethodLockAspectUtils
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/01 14:43
 */
public class MethodLockAspectUtils {
    /**
     * 互斥锁 参数默认false，不公平锁
     */
    private static Lock lock = new ReentrantLock(false);


    public static Object methodLock(ProceedingJoinPoint joinPoint){

        lock.lock();
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return obj;
    }
}
