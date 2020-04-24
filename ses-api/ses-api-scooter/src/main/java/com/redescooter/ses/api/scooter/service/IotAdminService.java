/*
package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.iot.enums.InvokeResult;
import com.redescooter.ses.iot.enums.Lock;

*/
/**
 * @ClassName:IotAdminService
 * @description: IotAdminService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/08/27 10:19
 *//*

public interface    IotAdminService {
    */
/**
     * 主锁 上锁
     *
     * @param sn
     * @param lock
     * @return
     *//*

    InvokeResult masterLock(String sn, Lock lock);

    */
/**
     * 后备箱锁 上锁
     *
     * @param sn
     * @param lock
     * @return
     *//*


    InvokeResult boxLock(String sn, Lock lock);

    */
/**
     * 电池锁 上锁
     *
     * @param sn
     * @param lock
     * @return
     *//*

    InvokeResult batteryLock(String sn, Lock lock);

    */
/**
     * 主锁解锁
     *
     * @param sn
     * @param lock
     * @return
     *//*

    InvokeResult unMasterLocklock(String sn, Lock lock);

    */
/**
     * 后备箱锁解锁
     *
     * @param sn
     * @param lock
     * @return
     *//*

    InvokeResult unBoxLock(String sn, Lock lock);

    */
/**
     * 电池锁解锁
     *
     * @param sn
     * @param lock
     * @return
     *//*

    InvokeResult unBatteryLock(String sn, Lock lock);

    */
/**
     * 导航 开启
     *
     * @param sn
     * @param longitude
     * @param latitude
     * @return
     *//*

    InvokeResult navigation(String sn, String longitude, String latitude);

    */
/**
     * 导航结束
     *
     * @param sn
     * @return
     *//*

    InvokeResult finishNavigation(String sn);

}
*/
