package com.redescooter.ses.service.scooter.service.impl;

import com.redescooter.ses.api.scooter.service.ScooterBbiService;
import com.redescooter.ses.api.scooter.vo.emqx.BatteryWareDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterBbiReportedDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterBmsReportedDTO;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.ScooterBbiBatteryWareMapper;
import com.redescooter.ses.service.scooter.dao.ScooterBbiMapper;
import com.redescooter.ses.service.scooter.dao.ScooterBmsMapper;
import com.redescooter.ses.service.scooter.dao.ScooterServiceMapper;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2020/11/23 16:34
 */
@Slf4j
@Service
public class ScooterBbiServiceImpl implements ScooterBbiService {

    @Resource
    private ScooterBbiMapper scooterBbiMapper;
    @Resource
    private ScooterBbiBatteryWareMapper bbiBatteryWareMapper;
    @Resource
    private ScooterBmsMapper scooterBmsMapper;
    @Resource
    private ScooterServiceMapper scooterServiceMapper;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private IdAppService idAppService;


    @Override
    public int insertScooterBbiByEmqX(ScooterBbiReportedDTO scooterReportedBbi) {
        try {
            String scooterNo = scooterServiceMapper.getScooterNoByTabletSn(scooterReportedBbi.getTabletSn());
            if (StringUtils.isBlank(scooterNo)) {
                log.error("【车辆电池相关信息数据上报失败】----车辆不存在");
                return 0;
            }

            /**
             * 检查数据库中是否存在车辆电池相关信息,有则更新 无则新增
             */
            ScooterBbiReportedDTO scooterBbi = scooterBbiMapper.getScooterBbiByScooterNoAndBatchNo(scooterNo,
                    scooterReportedBbi.getBatchNo());

            transactionTemplate.execute(status -> {
                try {
                    /**
                     * BMS电池信息
                     */
                    List<ScooterBmsReportedDTO> scooterBmsNew = new ArrayList<>();
                    scooterReportedBbi.getBatteryWares().forEach(battery -> {
                        if (null != battery.getBms()) {
                            scooterBmsNew.add(battery.getBms());
                        }
                    });

                    if (null != scooterBbi) {
                        /**
                         * 更新BBI(电池管理系统)数据
                         */
                        scooterReportedBbi.setId(scooterBbi.getId());
                        scooterReportedBbi.setUpdatedTime(new Date());
                        scooterBbiMapper.updateScooterBbiById(scooterReportedBbi);

                        /**
                         * 更新电池仓位信息(电池卡槽,每辆车有四个)
                         */
                        bbiBatteryWareMapper.batchUpdateBatteryWare(buildBatteryWareData(scooterReportedBbi.getBatteryWares(),
                                scooterNo, 2));

                        /**
                         * 更新电池信息 -- 根据scooterNo、wareNo和batchNo来确定电池
                         * by-数据库中已存在的电池进行更新,未存在进行新增
                         */
                        if (!CollectionUtils.isEmpty(scooterBmsNew)) {
                            List<Integer> bmsWareNos = scooterBmsMapper.getScooterBmsWareNoByScooterNo(scooterNo);

                            /**
                             * 使用stream.filter筛选出存在和未存在的电池信息
                             */
                            List<ScooterBmsReportedDTO> updateScooterBmsList = scooterBmsNew.stream().filter(
                                    bms -> bmsWareNos.contains(bms.getWareNo())
                            ).collect(Collectors.toList());

                            List<ScooterBmsReportedDTO> insertScooterBmsList = scooterBmsNew.stream().filter(
                                    bms -> !bmsWareNos.contains(bms.getWareNo())
                            ).collect(Collectors.toList());

                            // 目前电池只会有四个,所以这里会做限制
                            if (!CollectionUtils.isEmpty(insertScooterBmsList) && bmsWareNos.size() < 4) {
                                insertScooterBmsList.forEach(bms -> {
                                    bms.setId(idAppService.getId(SequenceName.SCO_SCOOTER_BMS));
                                    bms.setScooterNo(scooterNo);
                                    bms.setCreatedTime(new Date());
                                    bms.setUpdatedTime(new Date());
                                });

                                int subLength = 4 - bmsWareNos.size();
                                scooterBmsMapper.batchInsertScooterBms(insertScooterBmsList.subList(0, subLength));
                            }

                            // set UpdatedTime or update
                            if (!CollectionUtils.isEmpty(updateScooterBmsList)) {
                                updateScooterBmsList.forEach(bms -> {
                                    bms.setUpdatedTime(new Date());
                                });
                                scooterBmsMapper.batchUpdateScooterBms(updateScooterBmsList, scooterNo);
                            }
                        }
                    } else {
                        // bbi -> batteryWare -> bms
                        scooterReportedBbi.setId(idAppService.getId(SequenceName.SCO_SCOOTER_BBI));
                        scooterReportedBbi.setScooterNo(scooterNo);
                        scooterReportedBbi.setCreatedTime(new Date());
                        scooterReportedBbi.setUpdatedTime(new Date());
                        scooterBbiMapper.insertScooterBbi(scooterReportedBbi);

                        bbiBatteryWareMapper.batchInsertBatteryWare(buildBatteryWareData(scooterReportedBbi.getBatteryWares(),
                                scooterNo, 1));

                        if (!CollectionUtils.isEmpty(scooterBmsNew)) {
                            scooterBmsNew.forEach(bms -> {
                                bms.setId(idAppService.getId(SequenceName.SCO_SCOOTER_BMS));
                                bms.setScooterNo(scooterNo);
                                bms.setCreatedTime(new Date());
                                bms.setUpdatedTime(new Date());
                            });
                            scooterBmsMapper.batchInsertScooterBms(scooterBmsNew.size() > 4 ? scooterBmsNew.subList(0, 4) : scooterBmsNew);
                        }
                    }
                } catch (Exception e) {
                    log.error("【车辆电池相关信息数据上报失败】----{}", ExceptionUtils.getStackTrace(e));
                    status.setRollbackOnly();
                }
                return 1;
            });
        } catch (Exception e) {
            log.error("【车辆电池相关信息数据上报失败】----{}", ExceptionUtils.getStackTrace(e));
        }

        return 1;
    }

    /**
     * 组装电池仓位信息(BatteryWare)
     * @param batteryWares
     * @param scooterNo
     * @param type 1新增 2修改
     * @return
     */
    private List<BatteryWareDTO> buildBatteryWareData(List<BatteryWareDTO> batteryWares, String scooterNo, int type) {
        // 电池仓位号,平板端那边传过来的电池仓位信息集合是有序的,所以我直接遍历累加就行了
        int wareNo = 1;

        for (int i = 0; i < batteryWares.size(); i++) {
            if (1 == type) {
                batteryWares.get(i).setCreatedTime(new Date());
                batteryWares.get(i).setId(idAppService.getId(SequenceName.SCO_SCOOTER_BBI_BATTERY_WARE));
            }
            batteryWares.get(i).setUpdatedTime(new Date());
            batteryWares.get(i).setWareNo(wareNo++);
            batteryWares.get(i).setScooterNo(scooterNo);
        }

        // 现在车辆电池仓位只有4个,所以只取4个
        return batteryWares.size() > 4 ? batteryWares.subList(0, 4) : batteryWares;
    }

}
