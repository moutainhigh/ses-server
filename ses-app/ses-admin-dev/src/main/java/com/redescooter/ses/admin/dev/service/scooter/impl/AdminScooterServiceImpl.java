package com.redescooter.ses.admin.dev.service.scooter.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.admin.dev.constant.SequenceName;
import com.redescooter.ses.admin.dev.convert.scooter.AdminScooterConvert;
import com.redescooter.ses.admin.dev.dao.scooter.AdminScooterMapper;
import com.redescooter.ses.admin.dev.dao.scooter.AdminScooterPartsMapper;
import com.redescooter.ses.admin.dev.dm.AdmScooter;
import com.redescooter.ses.admin.dev.exception.ExceptionCodeEnums;
import com.redescooter.ses.admin.dev.exception.SesAdminDevException;
import com.redescooter.ses.admin.dev.service.scooter.AdminScooterService;
import com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.AdminScooterPartsDTO;
import com.redescooter.ses.admin.dev.vo.scooter.InsertAdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.QueryAdminScooterParamDTO;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.ColorDTO;
import com.redescooter.ses.api.common.vo.scooter.SpecificGroupDTO;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterEcuDataDTO;
import com.redescooter.ses.api.hub.service.operation.ColorService;
import com.redescooter.ses.api.hub.service.operation.SpecificService;
import com.redescooter.ses.api.scooter.service.ScooterEcuService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public PageResult<AdminScooterDTO> queryAdminScooter(QueryAdminScooterParamDTO paramDTO) {
        PageEnter enter = new PageEnter();
        enter.setPageNo(paramDTO.getPageNo());
        enter.setPageSize(paramDTO.getPageSize());

        int count = adminScooterMapper.countByAdminScooter();
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
        admScooter.setScooterController(ScooterModelEnum.getScooterModelType(specificGroup.getGroupName()));
        admScooter.setColorName(color.getColorName());
        admScooter.setColorValue(color.getColorValue());
        admScooter.setGroupName(specificGroup.getGroupName());
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
        transactionTemplate.execute(adminScooterStatus -> {
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
                log.error("【创建车辆信息失败】----{}", ExceptionUtils.getStackTrace(e));
                adminScooterStatus.setRollbackOnly();
            }
            return 1;
        });

        return 0;
    }

    @Override
    public AdminScooterDTO getAdminScooterDetailById(Long id) {

        return adminScooterMapper.getAdminScooterDetailById(id);
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
    private SyncScooterDataDTO buildSyncScooterData(Long scooterId,String tabletSn,Integer scooterModel, Long userId,
                                                    String scooterNo) {
        SyncScooterDataDTO syncScooterData = new SyncScooterDataDTO();
        syncScooterData.setId(scooterId);
        syncScooterData.setScooterNo(scooterNo);
        syncScooterData.setTabletSn(tabletSn);
        syncScooterData.setModel(String.valueOf(scooterModel));
        syncScooterData.setUserId(userId);

        return syncScooterData;
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
        int count = adminScooterMapper.countByAdminScooter();

        // 编号规则：区域 + 产品范围 + 结构类型 + 额定功率 + 生产地点 + 年份 + 月份 + 生产流水号(数量从1开始)
        StringBuilder sb = new StringBuilder();
        sb.append("FR");
        sb.append("ED");
        sb.append("D");
        sb.append("0");
        sb.append("0");
        sb.append(year.substring(2, 4)); // 年份,就这样截取吧,这样也能正常使用差不多八百年了
        sb.append(month);
        sb.append(count);

        return sb.toString();
    }

}
