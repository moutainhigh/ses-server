package com.redescooter.ses.service.scooter.service.impl;

import com.redescooter.ses.admin.client.TerminalServiceFactory;
import com.redescooter.ses.api.scooter.service.IotAdminService;
import com.redescooter.ses.iot.enums.InvokeResult;
import com.redescooter.ses.iot.enums.Lock;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName:IotAdminServiceImpl
 * @description: IotAdminServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/20 18:17
 */
public class IotAdminServiceImpl implements IotAdminService {

    /**
     * 主锁 上锁
     *
     * @param sn
     * @param lock
     * @return
     */
    @Transactional
    @Override
    public InvokeResult masterLock(String sn, Lock lock) {
        return TerminalServiceFactory.getInstance().lock(sn, Lock.MASTER);
    }

    /**
     * 后备箱锁 上锁
     *
     * @param sn
     * @param lock
     * @return
     */
    @Transactional
    @Override
    public InvokeResult boxLock(String sn, Lock lock) {
        return TerminalServiceFactory.getInstance().lock(sn, Lock.BOX);
    }

    /**
     * 电池锁 上锁
     *
     * @param sn
     * @param lock
     * @return
     */
    @Transactional
    @Override
    public InvokeResult batteryLock(String sn, Lock lock) {
        return TerminalServiceFactory.getInstance().lock(sn, Lock.BATTERY);
    }

    /**
     * 主锁解锁
     *
     * @param sn
     * @param lock
     * @return
     */
    @Transactional
    @Override
    public InvokeResult unMasterLocklock(String sn, Lock lock) {
        return TerminalServiceFactory.getInstance().unlock(sn, Lock.MASTER);
    }

    /**
     * 后备箱锁解锁
     *
     * @param sn
     * @param lock
     * @return
     */
    @Transactional
    @Override
    public InvokeResult unBoxLock(String sn, Lock lock) {
        return TerminalServiceFactory.getInstance().unlock(sn, Lock.BOX);
    }

    /**
     * 电池锁解锁
     *
     * @param sn
     * @param lock
     * @return
     */
    @Transactional
    @Override
    public InvokeResult unBatteryLock(String sn, Lock lock) {
        return TerminalServiceFactory.getInstance().unlock(sn, Lock.BATTERY);
    }

    /**
     * 导航 开启
     *
     * @param sn
     * @param longitude
     * @param latitude
     * @return
     */
    @Transactional
    @Override
    public InvokeResult navigation(String sn, String longitude, String latitude) {
        return TerminalServiceFactory.getInstance().navigation(sn, longitude, latitude);
    }

    /**
     * 导航结束
     *
     * @param sn
     * @return
     */
    @Transactional
    @Override
    public InvokeResult finishNavigation(String sn) {
        return TerminalServiceFactory.getInstance().finishNavigation(sn);
    }
}
