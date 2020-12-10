package com.redescooter.ses.service.scooter.service.impl;

import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterEcuDataDTO;
import com.redescooter.ses.api.scooter.service.ScooterEcuService;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.ScooterEcuMapper;
import com.redescooter.ses.service.scooter.dao.ScooterServiceMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterEcu;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author assert
 * @date 2020/11/23 16:34
 */
@Slf4j
@Service
public class ScooterEcuServiceImpl implements ScooterEcuService {

    @Resource
    private ScooterEcuMapper scooterEcuMapper;
    @Resource
    private IdAppService idAppService;
    @Resource
    private ScooterServiceMapper scooterServiceMapper;
    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public int insertScooterEcuByEmqX(ScooterEcuDTO scooterEcu) {
        String scooterNo = scooterServiceMapper.getScooterNoByTabletSn(scooterEcu.getTabletSn());
        if (StringUtils.isBlank(scooterNo)) {
            log.error("【车辆ECU控制器数据上报失败】----车辆不存在");
            return 0;
        }

        /**
         * 检查数据是否存在,如果存在则更新,反之新增
         */
        transactionTemplate.execute(ecuStatus -> {
            ScooterEcuDTO scooterEcuDb = scooterEcuMapper.getScooterEcuBySerialNumber(scooterEcu.getTabletSn());
            if (null != scooterEcuDb) {
                scooterEcu.setId(scooterEcuDb.getId());
                scooterEcu.setUpdatedTime(new Date());
                scooterEcuMapper.updateScooterEcu(scooterEcu);
            } else {
                scooterEcu.setId(idAppService.getId(SequenceName.SCO_SCOOTER_ECU));
                scooterEcu.setScooterNo(scooterNo);
                scooterEcu.setCreatedTime(new Date());
                scooterEcu.setUpdatedTime(new Date());
                scooterEcuMapper.insertScooterEcu(scooterEcu);
            }

            // 同时更新scooter表车辆锁状态
            updateScooterStatusByEcu(scooterEcu.getTabletSn(), scooterEcu.getScooterLock());

            return 1;
        });
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int syncScooterEcuData(SyncScooterEcuDataDTO syncScooterEcuData) {
        ScooterEcuDTO scooterEcu = new ScooterEcuDTO();
        BeanUtils.copyProperties(syncScooterEcuData, scooterEcu);

        scooterEcu.setId(idAppService.getId(SequenceName.SCO_SCOOTER_ECU));
        scooterEcu.setScooterLock(true);
        scooterEcu.setCreatedBy(syncScooterEcuData.getUserId());
        scooterEcu.setCreatedTime(new Date());
        scooterEcu.setUpdatedBy(syncScooterEcuData.getUserId());
        scooterEcu.setUpdatedTime(new Date());

        return scooterEcuMapper.insertScooterEcu(scooterEcu);
    }


    /**
     * 根据ecu上报信息更新车辆锁状态
     * @param tabletSn 平板序列号
     * @param scooterLock 车辆是否锁住 true是 false否
     */
    private void updateScooterStatusByEcu(String tabletSn, boolean scooterLock) {
        String lockStatus = null;
        if (scooterLock) {
            lockStatus = ScooterLockStatusEnums.LOCK.getValue();
        } else {
            lockStatus = ScooterLockStatusEnums.UNLOCK.getValue();
        }

        String lockStatusOld = scooterServiceMapper.getScooterStatusByTabletSn(tabletSn);
        if (!lockStatus.equals(lockStatusOld)) {
            scooterServiceMapper.updateScooterStatusByTabletSn(tabletSn, lockStatus, new Date());
        }

    }

}
