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
import com.redescooter.ses.tool.utils.date.DateUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
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
@DubboService
public class ScooterBbiServiceImpl implements ScooterBbiService {

    @DubboReference
    private IdAppService idAppService;

    @Resource
    private ScooterBbiMapper scooterBbiMapper;

    @Resource
    private ScooterBbiBatteryWareMapper bbiBatteryWareMapper;

    @Resource
    private ScooterBmsMapper scooterBmsMapper;

    @Resource
    private ScooterServiceMapper scooterServiceMapper;


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public int insertScooterBbiByEmqX(ScooterBbiReportedDTO scooterReportedBbi) {
        try {
            String scooterNo = scooterServiceMapper.getScooterNoByTabletSn(scooterReportedBbi.getTabletSn());
            if (StringUtils.isBlank(scooterNo)) {
                log.error("????????????????????????????????????????????????----{}???????????????", scooterReportedBbi.getTabletSn());
                return 0;
            }

            /**
             * ??????????????????????????????????????????????????????,???????????? ????????????
             */
            ScooterBbiReportedDTO scooterBbi = scooterBbiMapper.getScooterBbiByScooterNoAndBatchNo(scooterNo,
                    scooterReportedBbi.getBatchNo());
            log.info("?????????{}:::ECU??????????????????????????????----{}", scooterReportedBbi.getTabletSn(), DateUtil.format(new Date(), DateUtil.DEFAULT_DATETIME_FORMAT));

            try {
                /**
                 * BMS????????????
                 */
                List<ScooterBmsReportedDTO> scooterBmsNew = new ArrayList<>();
                scooterReportedBbi.getBatteryWares().forEach(battery -> {
                    if (null != battery.getBms()) {
                        scooterBmsNew.add(battery.getBms());
                    }
                });

                if (null != scooterBbi) {
                    /**
                     * ??????BBI(??????????????????)??????
                     */
                    scooterReportedBbi.setId(scooterBbi.getId());
                    scooterReportedBbi.setUpdatedTime(new Date());
                    scooterBbiMapper.updateScooterBbiById(scooterReportedBbi);

                    /**
                     * ????????????????????????(????????????,??????????????????)
                     */
                    bbiBatteryWareMapper.batchUpdateBatteryWare(buildBatteryWareData(scooterReportedBbi.getBatteryWares(),
                            scooterNo, 2));

                    /**
                     * ?????????????????? -- ??????scooterNo???wareNo???batchNo???????????????
                     * by-??????????????????????????????????????????,?????????????????????
                     */
                    if (!CollectionUtils.isEmpty(scooterBmsNew)) {
                        List<Integer> bmsWareNos = scooterBmsMapper.getScooterBmsWareNoByScooterNo(scooterNo);

                        /**
                         * ??????stream.filter??????????????????????????????????????????
                         */
                        List<ScooterBmsReportedDTO> updateScooterBmsList = scooterBmsNew.stream().filter(
                                bms -> bmsWareNos.contains(bms.getWareNo())
                        ).collect(Collectors.toList());

                        List<ScooterBmsReportedDTO> insertScooterBmsList = scooterBmsNew.stream().filter(
                                bms -> !bmsWareNos.contains(bms.getWareNo())
                        ).collect(Collectors.toList());

                        // ???????????????????????????,????????????????????????
                        if (!CollectionUtils.isEmpty(insertScooterBmsList) && bmsWareNos.size() < 4) {
                            insertScooterBmsList.forEach(bms -> {
                                bms.setId(idAppService.getId(SequenceName.SCO_SCOOTER_BMS));
                                bms.setScooterNo(scooterNo);
                                bms.setCreatedTime(new Date());
                                bms.setUpdatedTime(new Date());
                            });

                            int subLength = 4 - bmsWareNos.size();

                            if (insertScooterBmsList.size() < subLength) {
                                scooterBmsMapper.batchInsertScooterBms(insertScooterBmsList);
                            } else {
                                scooterBmsMapper.batchInsertScooterBms(insertScooterBmsList.subList(0, subLength));
                            }
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
                log.info("?????????{}:::ECU??????????????????????????????----{}", scooterReportedBbi.getTabletSn(), DateUtil.format(new Date(), DateUtil.DEFAULT_DATETIME_FORMAT));

            } catch (Exception e) {
                log.error("????????????????????????????????????????????????----{}", ExceptionUtils.getStackTrace(e));
            }
        } catch (Exception e) {
            log.error("????????????????????????????????????????????????----{}", ExceptionUtils.getStackTrace(e));
        }

        return 1;
    }

    /**
     * ????????????????????????(BatteryWare)
     *
     * @param batteryWares
     * @param scooterNo
     * @param type         1?????? 2??????
     * @return
     */
    private List<BatteryWareDTO> buildBatteryWareData(List<BatteryWareDTO> batteryWares, String scooterNo, int type) {
        // ???????????????,???????????????????????????????????????????????????????????????,????????????????????????????????????
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

        // ??????????????????????????????4???,????????????4???
        return batteryWares.size() > 4 ? batteryWares.subList(0, 4) : batteryWares;
    }

}
