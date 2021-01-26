package com.redescooter.ses.admin.dev.service.scooter.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.admin.dev.constant.SequenceName;
import com.redescooter.ses.admin.dev.constant.SpecificDefNameConstant;
import com.redescooter.ses.admin.dev.dao.scooter.AdminScooterMapper;
import com.redescooter.ses.admin.dev.dao.scooter.AdminScooterPartsMapper;
import com.redescooter.ses.admin.dev.dm.AdmScooter;
import com.redescooter.ses.admin.dev.exception.ExceptionCodeEnums;
import com.redescooter.ses.admin.dev.exception.SesAdminDevException;
import com.redescooter.ses.admin.dev.service.base.AdmScooterService;
import com.redescooter.ses.admin.dev.service.scooter.AdminScooterService;
import com.redescooter.ses.admin.dev.vo.scooter.*;
import com.redescooter.ses.api.common.enums.scooter.CommonEvent;
import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.*;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
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
    @Reference
    private ColorService colorService;
    @Reference
    private SpecificService specificService;
    @Reference
    private ScooterService scooterService;
    @Reference
    private ScooterEcuService scooterEcuService;
    @Reference
    private IdAppService idAppService;
    @Reference
    private SysUserService sysUserService;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Reference
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
    public int insertAdminScooter(InsertAdminScooterDTO adminScooterDTO) {
        List<AdminScooterPartsDTO> scooterPartsList = new ArrayList<>();
        Long userId = adminScooterDTO.getUserId();
        try {
            scooterPartsList = JSONArray.parseArray(adminScooterDTO.getScooterPartsList(), AdminScooterPartsDTO.class);
        } catch (Exception e) {
            log.error("【创建车辆信息失败】----参数数据格式有误");
            throw new SesAdminDevException(ExceptionCodeEnums.DATA_FORMAT_ERROR.getCode(),
                    ExceptionCodeEnums.DATA_FORMAT_ERROR.getMessage());
        }

        AdminScooterDTO scooter = adminScooterMapper.getAdminScooterBySn(adminScooterDTO.getSn());
        if (null != scooter) {
            throw new SesAdminDevException(ExceptionCodeEnums.SCOOTER_TABLET_SN_EXISTS.getCode(),
                    ExceptionCodeEnums.SCOOTER_TABLET_SN_EXISTS.getMessage());
        }

        /**
         * 查询车辆颜色、分组信息,用于冗余到车辆表里面去
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
         * 组装车辆、车辆配件数据,需要把对象从dto转到do
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
         * -这里的事务操作分成：1.自身(adm_scooter表新增数据) 2.scooter-service-scooter服务(sco_scooter、sco_scooter_ecu表新增数据)
         * -目前还没有集成分布式事务解决方案,所以这里可能会导致事务不一致的情况出现
         */
        boolean result = transactionTemplate.execute(adminScooterStatus -> {
            boolean flag = true;
            try {
                adminScooterMapper.insertAdminScooter(admScooter);
                adminScooterPartsMapper.batchInsertAdminScooterParts(newList);

                // 需要把车辆数据同步到scooter数据库中去,scooter库中需要同步的表：sco_scooter、sco_scooter_ecu
                String scooterNo = generateScooterNo();
                scooterService.syncScooterData(buildSyncScooterData(id, admScooter.getSn(),admScooter.getScooterController(),
                        userId, scooterNo));
                scooterEcuService.syncScooterEcuData(buildSyncScooterEcuData(admScooter.getMacAddress(), admScooter.getMacName(),
                        userId, scooterNo));
            } catch (Exception e) {
                flag = false;
                log.error("【创建车辆信息失败】----{}", ExceptionUtils.getStackTrace(e));
                adminScooterStatus.setRollbackOnly();
            }
            return flag;
        });

        /**
         * 手动抛出异常
         */
        if (!result) {
            throw new SesAdminDevException(ExceptionCodeEnums.CREATE_SCOOTER_ERROR.getCode(),
                    ExceptionCodeEnums.CREATE_SCOOTER_ERROR.getMessage());
        }

        return 0;
    }

    @Override
    public AdminScooterDTO getAdminScooterDetailById(Long id) {
        AdminScooterDTO adminScooter = adminScooterMapper.getAdminScooterDetailById(id);
        if (null != adminScooter) {
            // 去operation库查询用户的姓名和头像
            SysUserStaffDTO userStaff = sysUserService.getSysUserStaffByUserId(adminScooter.getCreatedBy());
            if (null != userStaff) {
                adminScooter.setCreator(userStaff.getFullName());
            }
        }

        adminScooter.setScooterModel(ScooterModelEnum.getScooterModelByType(adminScooter.getScooterController()));

        return adminScooter;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult setScooterModel(SetScooterModelParamDTO paramDTO) {
        /**
         * 参数校验 -- 检查数据库中是否存在对应数据
         */
        AdminScooterDTO adminScooter = adminScooterMapper.getAdminScooterDetailById(paramDTO.getId());
        if (null == adminScooter) {
            throw new SesAdminDevException(ExceptionCodeEnums.SCOOTER_NOT_EXISTS.getCode(),
                    ExceptionCodeEnums.SCOOTER_NOT_EXISTS.getMessage());
        }

        /**
         * 只允许车辆关闭状态时进行软体设置
         */
        String scooterLockStatus = scooterService.getScooterStatusByTabletSn(adminScooter.getSn());
        if (ScooterLockStatusEnums.UNLOCK.getValue().equals(scooterLockStatus)) {
            throw new SesAdminDevException(ExceptionCodeEnums.SCOOTER_NOT_CLOSED.getCode(),
                    ExceptionCodeEnums.SCOOTER_NOT_CLOSED.getMessage());
        }

        /**
         * type 1时表示当前是设置软体操作, type 2时表示重置车辆软体(也就是设置成默认的E50型号)
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

        /**
         * 如果设置的型号与当前车辆的型号一致则不做操作
         */
        if (null != adminScooter.getScooterController() && adminScooter.getScooterController().equals(scooterModel)) {
            return new GeneralResult(paramDTO.getRequestId());
        }

        /**
         * 更新车辆型号信息,同步车辆型号信息到scooter库sco_scooter表中去
         */
        AdminScooterDTO scooter = new AdminScooterDTO();
        scooter.setId(paramDTO.getId());
        scooter.setScooterController(scooterModel);
        scooter.setGroupId(specificType.getGroupId());
        scooter.setGroupName(specificType.getGroupName());
        scooter.setUpdatedBy(paramDTO.getUserId());
        scooter.setUpdatedTime(new Date());

        adminScooterMapper.updateAdminScooter(scooter);
        scooterService.syncScooterModel(paramDTO.getId(), scooterModel);

        List<SpecificDefGroupPublishDTO> specificDefGroupPublishList = buildSetScooterModelData(specificType.getId());
        if (!CollectionUtils.isEmpty(specificDefGroupPublishList)) {
            /**
             * 发送EMQ消息,通知车辆那边进行升级处理
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
     * 组装同步车辆数据
     * @param scooterId
     * @param tabletSn
     * @param scooterModel
     * @param userId
     * @param scooterNo
     * @return
     */
    private List<SyncScooterDataDTO> buildSyncScooterData(Long scooterId,String tabletSn,Integer scooterModel, Long userId,
                                                    String scooterNo) {
        SyncScooterDataDTO syncScooterData = new SyncScooterDataDTO();
        syncScooterData.setId(scooterId);
        syncScooterData.setScooterNo(scooterNo);
        syncScooterData.setTabletSn(tabletSn);
        syncScooterData.setModel(String.valueOf(scooterModel));
        syncScooterData.setUserId(userId);

        return Arrays.asList(syncScooterData);
    }

    /**
     * 组装同步车辆仪表ECU数据
     * @param bluetoothMacAddress
     * @param bluetoothName
     * @param userId
     * @param scooterNo
     * @return
     */
    private SyncScooterEcuDataDTO buildSyncScooterEcuData(String bluetoothMacAddress, String bluetoothName,
                                                          Long userId, String scooterNo) {
        SyncScooterEcuDataDTO syncScooterEcuData = new SyncScooterEcuDataDTO();
        syncScooterEcuData.setScooterNo(scooterNo);
        syncScooterEcuData.setBluetoothMacAddress(bluetoothMacAddress);
        syncScooterEcuData.setBluetoothName(bluetoothName);
        syncScooterEcuData.setUserId(userId);

        return syncScooterEcuData;
    }

    /**
     * 生成车辆编号
     * @return
     */
    private String generateScooterNo() {
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        int month = cal.get(Calendar.MONTH ) + 1;

        // 查询当前数据库车辆数量
        int count = scooterService.countByScooter();

        // 编号规则：区域 + 产品范围 + 结构类型 + 额定功率 + 生产地点 + 年份 + 月份 + 生产流水号(数量从1开始)
        StringBuilder sb = new StringBuilder();
        sb.append("FR");
        sb.append("ED");
        sb.append("D");
        sb.append("0");
        sb.append("0");
        sb.append(year.substring(2, 4)); // 年份,就这样截取吧,这样也能正常使用差不多八十年了
        sb.append(month);
        sb.append(count + 1);

        return sb.toString();
    }

    /**
     * 组装设置车辆型号数据
     * @param specificTypeId
     * @return
     */
    private List<SpecificDefGroupPublishDTO> buildSetScooterModelData(Long specificTypeId) {
        List<SpecificDefGroupPublishDTO> specificDefGroupPublishList = new ArrayList<>();

        /**
         * 查询当前车辆电池信息,这里主要是为了拿车辆电池的出厂号/流水号信息(用于设置软体时使用)
         * -TODO 先不给电池的出厂号/流水号(车辆那边现在不是强制性需要)
         */

        /**
         * 查询车辆型号自定义项信息
         */
        List<SpecificDefDTO> specificDefList = specificService.getSpecificDefBySpecificId(specificTypeId);
        if (!CollectionUtils.isEmpty(specificDefList)) {
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
                 * 组装自定义项数据 -- 自定义项名称固定值
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
     * 删除车辆
     * @param enter
     * @return
     */
    @Override
    public GeneralResult deleteScooter(IdEnter enter) {
        AdminScooterDTO admScooter = adminScooterMapper.getAdminScooterById(enter.getId());
        if (admScooter != null){
            adminScooterMapper.deleteScooterById(enter.getId());
            // 删除scooter的数据库的sco_scooter_ecu / sco_scooter数据
            scooterService.deleteScooterData(admScooter.getSn());
        }
        return new GeneralResult(enter.getRequestId());
    }
}
