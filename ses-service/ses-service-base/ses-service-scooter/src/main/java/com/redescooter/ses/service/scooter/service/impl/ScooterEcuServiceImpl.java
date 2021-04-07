package com.redescooter.ses.service.scooter.service.impl;

import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterEcuDataDTO;
import com.redescooter.ses.api.scooter.service.ScooterEcuService;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.ScooterEcuMapper;
import com.redescooter.ses.service.scooter.dao.ScooterServiceMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterStatus;
import com.redescooter.ses.service.scooter.service.base.ScoScooterStatusService;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import com.redescooter.ses.tool.utils.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author assert
 * @date 2020/11/23 16:34
 */
@Slf4j
@DubboService
public class ScooterEcuServiceImpl implements ScooterEcuService {

    @DubboReference
    private IdAppService idAppService;

    @Resource
    private ScooterEcuMapper scooterEcuMapper;

    @Resource
    private ScooterServiceMapper scooterServiceMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Autowired
    private ScoScooterStatusService scoScooterStatusService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public int insertScooterEcuByEmqX(ScooterEcuDTO scooterEcu) {
        try {
            String scooterNo = scooterServiceMapper.getScooterNoByTabletSn(scooterEcu.getTabletSn());
            if (StringUtils.isBlank(scooterNo)) {
                log.error("【车辆ECU控制器数据上报失败】----车辆不存在");
                return 0;
            }

            /**
             * 检查数据是否存在,如果存在则更新,反之新增
             */
            try {
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

                // 同时更新scooter表车辆锁状态和车辆行驶总里程
                updateScooterStatusAndTotalMilesByEcu(scooterEcu.getTabletSn(), scooterEcu.getScooterLock(),
                        scooterEcu.getTotalMiles());

                // 新增scooter实时信息
                ScoScooterStatus model = new ScoScooterStatus();
                model.setId(idAppService.getId(SequenceName.SCO_SCOOTER));
                model.setDr(0);
                model.setScooterEcuId(0L);
                model.setScooterId(scooterEcu.getId());
                model.setLockStatus(ScooterLockStatusEnums.LOCK.getValue());
                model.setTopBoxStatus(ScooterLockStatusEnums.LOCK.getValue());
                model.setLongitule(scooterEcu.getLongitude());
                model.setLatitude(scooterEcu.getLatitude());
                model.setGeohash(MapUtil.geoHash(scooterEcu.getLongitude().toString(), scooterEcu.getLatitude().toString()));
                model.setBattery(scooterEcu.getBattery());
                model.setCumulativeMileage("0");
                model.setRevision(0);
                model.setCreatedBy(scooterEcu.getCreatedBy());
                model.setCreatedTime(new Date());
                scoScooterStatusService.save(model);
            } catch (Exception e) {
                log.error("【车辆ECU仪表信息数据上报失败】----{}", ExceptionUtils.getStackTrace(e));
            }
        } catch (Exception e) {
            // 这里不要抛出异常,这里会往上抛到emq那边导致连接中断
            log.error("【车辆ECU仪表信息数据上报失败】----{}", ExceptionUtils.getStackTrace(e));
        }
        return 1;
    }

    @GlobalTransactional(rollbackFor = Exception.class)
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

    @Override
    public List<ScooterEcuDTO> getScooterEcuByTabletSnList(List<String> tabletSnList) {
        return scooterEcuMapper.getScooterEcuByTabletSnList(tabletSnList);
    }


    /**
     * 根据ecu上报信息更新车辆锁状态、行驶总里程
     *
     * @param tabletSn    平板序列号
     * @param scooterLock 车辆是否锁住 true是 false否
     * @param totalMiles  行驶总里程(单位/km)
     */
    private void updateScooterStatusAndTotalMilesByEcu(String tabletSn, boolean scooterLock, Integer totalMiles) {
        String lockStatus = null;
        if (scooterLock) {
            lockStatus = ScooterLockStatusEnums.LOCK.getValue();
        } else {
            lockStatus = ScooterLockStatusEnums.UNLOCK.getValue();
        }

        String lockStatusOld = scooterServiceMapper.getScooterStatusByTabletSn(tabletSn);
        if (!lockStatus.equals(lockStatusOld)) {
            scooterServiceMapper.updateScooterStatusAndTotalMilesByEcu(tabletSn, lockStatus, totalMiles, new Date());
        }

    }

}
