package com.redescooter.ses.admin.dev.service.scooter.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.admin.dev.constant.SequenceName;
import com.redescooter.ses.admin.dev.dao.scooter.AdminScooterMapper;
import com.redescooter.ses.admin.dev.dao.scooter.AdminScooterPartsMapper;
import com.redescooter.ses.admin.dev.dm.AdmScooter;
import com.redescooter.ses.admin.dev.exception.ExceptionCodeEnums;
import com.redescooter.ses.admin.dev.exception.SesAdminDevException;
import com.redescooter.ses.admin.dev.service.base.AdmScooterService;
import com.redescooter.ses.admin.dev.service.scooter.AdminScooterService;
import com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.AdminScooterPartsDTO;
import com.redescooter.ses.admin.dev.vo.scooter.InsertAdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.QueryAdminScooterParamDTO;
import com.redescooter.ses.admin.dev.vo.scooter.SetScooterModelParamDTO;
import com.redescooter.ses.api.common.constant.SpecificDefNameConstant;
import com.redescooter.ses.api.common.enums.date.DayCodeEnum;
import com.redescooter.ses.api.common.enums.date.MonthCodeEnum;
import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.ColorDTO;
import com.redescooter.ses.api.common.vo.scooter.SpecificGroupDTO;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterEcuDataDTO;
import com.redescooter.ses.api.common.vo.specification.SpecificDefDTO;
import com.redescooter.ses.api.hub.service.operation.ColorService;
import com.redescooter.ses.api.hub.service.operation.SpecificService;
import com.redescooter.ses.api.hub.service.operation.SysUserService;
import com.redescooter.ses.api.hub.vo.SysUserStaffDTO;
import com.redescooter.ses.api.hub.vo.operation.SpecificTypeDTO;
import com.redescooter.ses.api.scooter.service.ScooterEcuService;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.emqx.SetScooterModelPublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SpecificDefGroupPublishDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2020/12/8 18:45
 */
@Slf4j
@Service
public class AdminScooterServiceImpl implements AdminScooterService {

    @Resource
    private AdminScooterMapper adminScooterMapper;

    @Resource
    private AdminScooterPartsMapper adminScooterPartsMapper;

    @DubboReference
    private ColorService colorService;

    @DubboReference
    private SpecificService specificService;

    @DubboReference
    private ScooterService scooterService;

    @DubboReference
    private ScooterEcuService scooterEcuService;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private SysUserService sysUserService;

    @DubboReference
    private ScooterEmqXService scooterEmqXService;

    @Autowired
    private AdmScooterService admScooterService;

    @Override
    public PageResult<AdminScooterDTO> queryAdminScooter(QueryAdminScooterParamDTO paramDTO) {
        PageEnter enter = new PageEnter();
        enter.setPageNo(paramDTO.getPageNo());
        enter.setPageSize(paramDTO.getPageSize());
        int count = adminScooterMapper.countByAdminScooter(paramDTO);
        return PageResult.create(enter, count, adminScooterMapper.queryAdminScooter(paramDTO));
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public int insertAdminScooter(InsertAdminScooterDTO adminScooterDTO) {
        List<AdminScooterPartsDTO> scooterPartsList;
        Long userId = adminScooterDTO.getUserId();
        try {
            scooterPartsList = JSONArray.parseArray(adminScooterDTO.getScooterPartsList(), AdminScooterPartsDTO.class);
        } catch (Exception e) {
            log.error("??????????????????????????????----????????????????????????");
            throw new SesAdminDevException(ExceptionCodeEnums.DATA_FORMAT_ERROR.getCode(),
                    ExceptionCodeEnums.DATA_FORMAT_ERROR.getMessage());
        }

        AdminScooterDTO scooter = adminScooterMapper.getAdminScooterBySn(adminScooterDTO.getSn());
        if (null != scooter) {
            throw new SesAdminDevException(ExceptionCodeEnums.SCOOTER_TABLET_SN_EXISTS.getCode(),
                    ExceptionCodeEnums.SCOOTER_TABLET_SN_EXISTS.getMessage());
        }

        /**
         * ?????????????????????????????????,?????????????????????????????????
         */
        SpecificGroupDTO specificGroup = specificService.getSpecificGroupById(adminScooterDTO.getGroupId());
        ColorDTO color = colorService.getColorInfoById(adminScooterDTO.getColorId());
        if (null == specificGroup) {
            throw new SesAdminDevException(ExceptionCodeEnums.GROUP_NOT_EXISTS.getCode(),
                    ExceptionCodeEnums.GROUP_NOT_EXISTS.getMessage());
        }
        if (null == color) {
            throw new SesAdminDevException(ExceptionCodeEnums.COLOR_NOT_EXISTS.getCode(),
                    ExceptionCodeEnums.COLOR_NOT_EXISTS.getMessage());
        }

        /**
         * ?????????????????????????????????,??????????????????dto??????do
         */
        AdmScooter admScooter = new AdmScooter();
        BeanUtils.copyProperties(adminScooterDTO, admScooter);

        Long id = idAppService.getId(SequenceName.ADM_SCOOTER);
        admScooter.setId(id);
        admScooter.setColorName(color.getColorName());
        admScooter.setColorValue(color.getColorValue());
        admScooter.setGroupName(specificGroup.getGroupName());
        admScooter.setScooterController(ScooterModelEnum.SCOOTER_E50.getType());
        admScooter.setCreatedBy(userId);
        admScooter.setCreatedTime(new Date());
        admScooter.setUpdatedBy(userId);
        admScooter.setUpdatedTime(new Date());

        scooterPartsList.forEach(parts -> {
            parts.setId(idAppService.getId(SequenceName.ADM_SCOOTER_PARTS));
            parts.setScooterId(id);
            parts.setCreatedBy(userId);
            parts.setCreatedTime(new Date());
            parts.setUpdatedBy(userId);
            parts.setUpdatedTime(new Date());
        });

        List<AdminScooterPartsDTO> newList = scooterPartsList;

        /**
         * -??????????????????????????????1.??????(adm_scooter???????????????) 2.scooter-service-scooter??????(sco_scooter???sco_scooter_ecu???????????????)
         * -????????????????????????????????????????????????,?????????????????????????????????????????????????????????
         */
        try {
            adminScooterMapper.insertAdminScooter(admScooter);
            adminScooterPartsMapper.batchInsertAdminScooterParts(newList);

            // ??????????????????????????????scooter???????????????,scooter???????????????????????????sco_scooter(??????sn??????,????????????????????????)???sco_scooter_ecu(?????????,???????????????)
            String sn = admScooter.getSn();
            // ?????????????????????(sn)?????????sco_scooter??????????????? ???????????????true ????????????false
            Boolean flag = scooterService.getSnIsExist(sn);
            if (flag) {
                String scooterNo = generateScooterNo();
                scooterService.syncScooterData(buildSyncScooterData(id, sn, admScooter.getScooterController(), userId, scooterNo));
                /*scooterEcuService.syncScooterEcuData(buildSyncScooterEcuData(admScooter.getMacAddress(), admScooter.getMacName(), userId, scooterNo));*/
            }
        } catch (Exception e) {
            log.error("??????????????????????????????----{}", ExceptionUtils.getStackTrace(e));
            throw new SesAdminDevException(ExceptionCodeEnums.CREATE_SCOOTER_ERROR.getCode(), ExceptionCodeEnums.CREATE_SCOOTER_ERROR.getMessage());
        }
        return 0;
    }

    @Override
    public AdminScooterDTO getAdminScooterDetailById(Long id) {
        AdminScooterDTO adminScooter = adminScooterMapper.getAdminScooterDetailById(id);
        if (null != adminScooter) {
            // ???operation?????????????????????????????????
            SysUserStaffDTO userStaff = sysUserService.getSysUserStaffByUserId(adminScooter.getCreatedBy());
            if (null != userStaff) {
                adminScooter.setCreator(userStaff.getFullName());
            }
        }
        adminScooter.setScooterModel(ScooterModelEnum.getScooterModelByType(adminScooter.getScooterController()));
        return adminScooter;
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult setScooterModel(SetScooterModelParamDTO paramDTO) {
        /**
         * ???????????? -- ??????????????????????????????????????????
         */
        AdminScooterDTO adminScooter = adminScooterMapper.getAdminScooterDetailById(paramDTO.getId());
        if (null == adminScooter) {
            throw new SesAdminDevException(ExceptionCodeEnums.SCOOTER_NOT_EXISTS.getCode(),
                    ExceptionCodeEnums.SCOOTER_NOT_EXISTS.getMessage());
        }

        /**
         * ????????????????????????????????????????????????
         */
        String scooterLockStatus = scooterService.getScooterStatusByTabletSn(adminScooter.getSn());
        if (ScooterLockStatusEnums.UNLOCK.getValue().equals(scooterLockStatus)) {
            throw new SesAdminDevException(ExceptionCodeEnums.SCOOTER_NOT_CLOSED.getCode(),
                    ExceptionCodeEnums.SCOOTER_NOT_CLOSED.getMessage());
        }

        /**
         * type 1????????????????????????????????????, type 2???????????????????????????(???????????????????????????E50??????)
         */
        SpecificTypeDTO specificType = null;
        if (1 == paramDTO.getType()) {
            specificType = specificService.getSpecificTypeById(paramDTO.getModelId());
        } else {
            specificType = specificService.getSpecificTypeByName(ScooterModelEnum.SCOOTER_E50.getModel());
        }

        if (null == specificType) {
            throw new SesAdminDevException(ExceptionCodeEnums.SPECIFIC_TYPE_NOT_EXISTS.getCode(),
                    ExceptionCodeEnums.SPECIFIC_TYPE_NOT_EXISTS.getMessage());
        }

        Integer scooterModel = ScooterModelEnum.getScooterModelType(specificType.getSpecificatName());
        if (0 == scooterModel) {
            throw new SesAdminDevException(ExceptionCodeEnums.SELECT_SCOOTER_MODEL_ERROR.getCode(),
                    ExceptionCodeEnums.SELECT_SCOOTER_MODEL_ERROR.getMessage());
        }

        /**
         * ??????????????????????????????????????????????????????????????????
         */
        if (null != adminScooter.getScooterController() && adminScooter.getScooterController().equals(scooterModel)) {
            return new GeneralResult(paramDTO.getRequestId());
        }

        /**
         * ????????????????????????,???????????????????????????scooter???sco_scooter?????????
         */
        AdminScooterDTO scooter = new AdminScooterDTO();
        scooter.setId(paramDTO.getId());
        scooter.setScooterController(scooterModel);
        scooter.setGroupId(specificType.getGroupId());
        scooter.setGroupName(specificType.getGroupName());
        scooter.setUpdatedBy(paramDTO.getUserId());
        scooter.setUpdatedTime(new Date());

        adminScooterMapper.updateAdminScooter(scooter);
        scooterService.syncScooterModel(adminScooter.getSn(), scooterModel);

        List<SpecificDefGroupPublishDTO> specificDefGroupPublishList = buildSetScooterModelData(specificType.getId());
        if (!CollectionUtils.isEmpty(specificDefGroupPublishList)) {
            /**
             * ??????EMQ??????,????????????????????????????????????
             */
            SetScooterModelPublishDTO publishDTO = new SetScooterModelPublishDTO();
            publishDTO.setTabletSn(adminScooter.getSn());
            publishDTO.setScooterModel(scooterModel);
            publishDTO.setSpecificDefGroupList(specificDefGroupPublishList);
            scooterEmqXService.setScooterModel(publishDTO);
        }
        return new GeneralResult(paramDTO.getRequestId());
    }


    /**
     * ????????????????????????
     *
     * @param scooterId
     * @param tabletSn
     * @param scooterModel
     * @param userId
     * @param scooterNo
     * @return
     */
    private List<SyncScooterDataDTO> buildSyncScooterData(Long scooterId, String tabletSn, Integer scooterModel, Long userId, String scooterNo) {
        SyncScooterDataDTO syncScooterData = new SyncScooterDataDTO();
        syncScooterData.setId(scooterId);
        syncScooterData.setScooterNo(scooterNo);
        syncScooterData.setTabletSn(tabletSn);
        syncScooterData.setModel(String.valueOf(scooterModel));
        syncScooterData.setUserId(userId);
        return Arrays.asList(syncScooterData);
    }

    /**
     * ????????????????????????ECU??????
     *
     * @param bluetoothMacAddress
     * @param bluetoothName
     * @param userId
     * @param scooterNo
     * @return
     */
    private SyncScooterEcuDataDTO buildSyncScooterEcuData(String bluetoothMacAddress, String bluetoothName, Long userId, String scooterNo) {
        SyncScooterEcuDataDTO syncScooterEcuData = new SyncScooterEcuDataDTO();
        syncScooterEcuData.setScooterNo(scooterNo);
        syncScooterEcuData.setBluetoothMacAddress(bluetoothMacAddress);
        syncScooterEcuData.setBluetoothName(bluetoothName);
        syncScooterEcuData.setUserId(userId);
        return syncScooterEcuData;
    }

    /**
     * ??????????????????
     *
     * @return
     */
    private String generateScooterNo() {
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // ????????????????????? + ???????????? + ???????????? + ???????????? + ???????????? + ?????? + ?????? + ???????????????(?????????1??????)
        StringBuilder sb = new StringBuilder();
        sb.append("FR");
        sb.append("ED");
        sb.append("D");
        sb.append("0");
        sb.append(year.substring(2, 4));
        sb.append(MonthCodeEnum.getMonthCodeByMonth(month));
        // ?????????????????????,???????????????6????????????????????????
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sub = timeStamp.substring(timeStamp.length() - 6);
        String number = String.format("%s%s%s", DayCodeEnum.getDayCodeByDay(day), "1", sub);
        sb.append(number);
        return sb.toString();
    }

    /**
     * ??????????????????????????????
     *
     * @param specificTypeId
     * @return
     */
    private List<SpecificDefGroupPublishDTO> buildSetScooterModelData(Long specificTypeId) {
        List<SpecificDefGroupPublishDTO> specificDefGroupPublishList = new ArrayList<>();

        /**
         * ??????????????????????????????,????????????????????????????????????????????????/???????????????(???????????????????????????)
         * -TODO ???????????????????????????/?????????(???????????????????????????????????????)
         */

        /**
         * ????????????????????????????????????
         */
        List<SpecificDefDTO> specificDefList = specificService.getSpecificDefBySpecificId(specificTypeId);
        if (!CollectionUtils.isEmpty(specificDefList)) {
            // ?????????ope_specificat_def?????????def_group_id??????????????????,???????????????stream?????????????????????
            specificDefList.forEach(def -> {
                if (null == def.getSpecificDefGroupId()) {
                    throw new SesAdminDevException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
            });

            /**
             * {specificDefGroupId, List<SpecificDefDTO>}
             */
            Map<Long, List<SpecificDefDTO>> specificDefGroupMap = specificDefList.stream().collect(
                    Collectors.groupingBy(SpecificDefDTO::getSpecificDefGroupId)
            );

            for (Map.Entry<Long, List<SpecificDefDTO>> map : specificDefGroupMap.entrySet()) {
                /**
                 * {defName, defValue}
                 */
                Map<String, String> specificDefMap = map.getValue().stream().collect(
                        Collectors.toMap(SpecificDefDTO::getDefName, SpecificDefDTO::getDefValue)
                );

                /**
                 * ???????????????????????? -- ???????????????????????????
                 */
                SpecificDefGroupPublishDTO defGroupPublish = SpecificDefGroupPublishDTO.builder()
                        .wheelDiameter(specificDefMap.get(SpecificDefNameConstant.WHEEL_DIAMETER))
                        .speedRatio(specificDefMap.get(SpecificDefNameConstant.SPEED_RATIO))
                        .limitSpeedBos(specificDefMap.get(SpecificDefNameConstant.LIMIT_SPEED_BOS))
                        .limiting(specificDefMap.get(SpecificDefNameConstant.LIMITING))
                        .speedLimit(specificDefMap.get(SpecificDefNameConstant.SPEED_LIMIT))
                        .socRedWarning(specificDefMap.get(SpecificDefNameConstant.SOC_RED_WARNING))
                        .orangeWarning(specificDefMap.get(SpecificDefNameConstant.ORANGE_WARNING))
                        .stallSOC(specificDefMap.get(SpecificDefNameConstant.STALL_SOC))
                        .setSOCTo0AtStallUndervoltage(specificDefMap.get(SpecificDefNameConstant.SET_SOC_TO_0_AT_STALL_UNDER_VOLTAGE))
                        .stallVoltageUndervoltage(specificDefMap.get(SpecificDefNameConstant.STALL_VOLTAGE_UNDER_VOLTAGE))
                        .voltageLegalRecognitionMin(specificDefMap.get(SpecificDefNameConstant.VOLTAGE_LEGAL_RECOGNITION_MAX))
                        .voltageLegalRecognitionMax(specificDefMap.get(SpecificDefNameConstant.VOLTAGE_LEGAL_RECOGNITION_MIN))
                        .controllerUndervoltage(specificDefMap.get(SpecificDefNameConstant.CONTROLLER_UNDER_VOLTAGE))
                        .controllerUndervoltageRecovery(specificDefMap.get(SpecificDefNameConstant.CONTROLLER_UNDER_VOLTAGE_RECOVERY))
                        .build();

                specificDefGroupPublishList.add(defGroupPublish);
            }
        }
        return specificDefGroupPublishList;
    }


    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteScooter(IdEnter enter) {
        AdminScooterDTO admScooter = adminScooterMapper.getAdminScooterById(enter.getId());
        if (admScooter != null) {
            adminScooterMapper.deleteScooterById(enter.getId());
            // ??????scooter???????????????sco_scooter_ecu / sco_scooter??????
            scooterService.deleteScooterData(admScooter.getSn());
        }
        return new GeneralResult(enter.getRequestId());
    }
}
