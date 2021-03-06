package com.redescooter.ses.service.scooter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterEcuDataDTO;
import com.redescooter.ses.api.scooter.service.ScooterEcuService;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.ScooterEcuMapper;
import com.redescooter.ses.service.scooter.dao.ScooterServiceMapper;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooter;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterStatus;
import com.redescooter.ses.service.scooter.service.base.ScoScooterStatusService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.tool.utils.map.MapUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.time.chrono.JapaneseDate;
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
    @Autowired
    private ScoScooterStatusService scoScooterStatusService;
    @Autowired
    private ScoScooterMapper scoScooterMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public int insertScooterEcuByEmqX(ScooterEcuDTO scooterEcu) {
        try {
            ScoScooter scooter = scoScooterMapper.selectOne(
                    new QueryWrapper<ScoScooter>()
                            .eq(ScoScooter.COL_TABLET_SN, scooterEcu.getTabletSn())
                            .eq(ScoScooter.COL_DR, 0)
                            .last("limit 1"));
            if (scooter == null || StringUtils.isBlank(scooter.getScooterNo())) {
                log.error("?????????ECU??????????????????????????????----{}???????????????,??????????????????", scooterEcu.getTabletSn());
                return 0;
            }

            /**
             * ????????????????????????,?????????????????????,????????????
             */
            try {
                ScooterEcuDTO scooterEcuDb = scooterEcuMapper.getScooterEcuBySerialNumber(scooterEcu.getTabletSn());

                log.info("?????????{}:::ECU??????????????????????????????----{}", scooterEcu.getTabletSn(), DateUtil.format(new Date(), DateUtil.DEFAULT_DATETIME_FORMAT));

                if (scooterEcuDb == null) {
                    scooterEcu.setId(idAppService.getId(SequenceName.SCO_SCOOTER_ECU));
                    scooterEcu.setScooterNo(scooter.getScooterNo());
                    if (StringUtils.isAnyBlank(scooterEcu.getBluetoothMacAddress())) {
                        scooterEcu.setBluetoothMacAddress(scooter.getBluetoothMacAddress());
                    }
                    scooterEcu.setCreatedTime(new Date());
                    scooterEcu.setCreatedBy(0L);
                    scooterEcu.setUpdatedTime(new Date());
                    scooterEcu.setUpdatedBy(0L);
                    scooterEcuMapper.insertScooterEcu(scooterEcu);
                } else {
                    scooterEcu.setId(scooterEcuDb.getId());
                    scooterEcu.setUpdatedTime(new Date());
                    scooterEcu.setUpdatedBy(0L);
                    log.info("ECU>>>>>update>>>>>{}", scooterEcu.toString());
                    scooterEcuMapper.updateScooterEcu(scooterEcu);
                }

                log.info("????????????  ?????????????????????sim iccid??????  start");
                jedisCluster.set(scooterEcu.getTabletSn(), scooterEcu.getIccid());
                log.info("????????????  ?????????????????????sim iccid??????  end");

                // ????????????scooter??????????????????????????????????????????
                updateScooterStatusAndTotalMilesByEcu(scooterEcu.getTabletSn(), scooterEcu.getScooterLock(), scooterEcu.getTotalMiles());
                // ??????scooter????????????
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
                model.setDef1(scooterEcu.getTabletSn());
                model.setDef2(scooterEcu.getBluetoothMacAddress());
                scoScooterStatusService.save(model);
                log.info("?????????{}:::ECU??????????????????????????????----{}", scooterEcu.getTabletSn(), DateUtil.format(new Date(), DateUtil.DEFAULT_DATETIME_FORMAT));

            } catch (Exception e) {
                log.error("?????????ECU?????????????????????----{}", ExceptionUtils.getStackTrace(e));
            }
        } catch (Exception e) {
            // ????????????????????????,?????????????????????emq????????????????????????
            log.error("?????????ECU?????????????????????----{}", ExceptionUtils.getStackTrace(e));
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
     * ??????ecu???????????????????????????????????????????????????
     *
     * @param tabletSn    ???????????????
     * @param scooterLock ?????????????????? true??? false???
     * @param totalMiles  ???????????????(??????/km)
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
