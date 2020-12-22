package com.redescooter.ses.service.scooter.service.impl;

import com.redescooter.ses.api.scooter.service.ScooterMcuService;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterMcuControllerInfoDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterMcuReportedDTO;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.ScooterMcuControllerInfoMapper;
import com.redescooter.ses.service.scooter.dao.ScooterMcuMapper;
import com.redescooter.ses.service.scooter.dao.ScooterServiceMapper;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author assert
 * @date 2020/11/23 16:35
 */
@Slf4j
@Service
public class ScooterMcuServiceImpl implements ScooterMcuService {

    @Reference
    private IdAppService idAppService;
    @Resource
    private ScooterMcuMapper scooterMcuMapper;
    @Resource
    private ScooterMcuControllerInfoMapper mcuControllerInfoMapper;
    @Resource
    private ScooterServiceMapper scooterServiceMapper;
    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public int insertScooterMcuByEmqX(ScooterMcuReportedDTO scooterReportedMcu) {
        try {
            String scooterNo = scooterServiceMapper.getScooterNoByTabletSn(scooterReportedMcu.getTabletSn());
            if (StringUtils.isBlank(scooterNo)) {
                log.error("【车辆MCU控制器数据上报失败】----车辆不存在");
                return 0;
            }

            /**
             * 检查数据是否存在,存在则修改,反之新增
             */
            ScooterMcuReportedDTO scooterMcu = scooterMcuMapper.getScooterMcuByScooterNoAndBatchNo(scooterNo,
                    scooterReportedMcu.getBatchNo());

            transactionTemplate.execute(scooterMcuTransaction -> {
                try {
                    if (null != scooterMcu) {
                        /**
                         * 修改MCU控制器信息
                         */
                        scooterReportedMcu.setId(scooterMcu.getId());
                        scooterReportedMcu.setScooterNo(scooterNo);
                        scooterReportedMcu.setUpdatedTime(new Date());
                        scooterMcuMapper.updateScooterMcuById(scooterReportedMcu);

                        /**
                         * 修改MCU控制器状态信息
                         */
                        ScooterMcuControllerInfoDTO mcuControllerInfo = scooterReportedMcu.getControlInfo();
                        if (null != mcuControllerInfo) {
                            mcuControllerInfo.setMcuId(scooterMcu.getId());
                            mcuControllerInfo.setUpdatedTime(new Date());
                            mcuControllerInfoMapper.updateMcuControllerByMcuId(mcuControllerInfo);
                        }
                    } else {
                        Long mcuId = idAppService.getId(SequenceName.SCO_SCOOTER_MCU);
                        scooterReportedMcu.setId(mcuId);
                        scooterReportedMcu.setScooterNo(scooterNo);
                        scooterReportedMcu.setCreatedTime(new Date());
                        scooterReportedMcu.setUpdatedTime(new Date());
                        scooterMcuMapper.insertScooterMcu(scooterReportedMcu);

                        ScooterMcuControllerInfoDTO mcuControllerInfo = scooterReportedMcu.getControlInfo();
                        if (null != mcuControllerInfo) {
                            mcuControllerInfo.setId(idAppService.getId(SequenceName.SCO_SCOOTER_MCU_CONTROLLER_INFO));
                            mcuControllerInfo.setMcuId(mcuId);
                            mcuControllerInfo.setCreatedTime(new Date());
                            mcuControllerInfo.setUpdatedTime(new Date());
                            mcuControllerInfoMapper.insertMcuController(mcuControllerInfo);
                        }
                    }
                } catch (Exception e) {
                    log.error("【车辆MCU控制器数据上报失败】----{}", ExceptionUtils.getStackTrace(e));
                    scooterMcuTransaction.setRollbackOnly();
                }
                return 1;
            });
        } catch (Exception e) {
            log.error("【车辆MCU控制器数据上报失败】----{}", ExceptionUtils.getStackTrace(e));
        }

        return 1;
    }

}
