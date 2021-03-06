package com.redescooter.ses.web.ros.service.setting.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.SystemTypeEnums;
import com.redescooter.ses.api.common.vo.base.BooleanEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.api.foundation.service.setting.ParameterSettingService;
import com.redescooter.ses.api.foundation.vo.setting.ParameterGroupResultList;
import com.redescooter.ses.api.foundation.vo.setting.ParameterListEnter;
import com.redescooter.ses.api.foundation.vo.setting.ParameterListResult;
import com.redescooter.ses.api.foundation.vo.setting.ParameterResult;
import com.redescooter.ses.api.foundation.vo.setting.SaveParamentEnter;
import com.redescooter.ses.api.foundation.vo.setting.SaveParameterBatchEnter;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.service.setting.RosParameterService;
import com.redescooter.ses.web.ros.vo.setting.ImportParameterEnter;
import com.redescooter.ses.web.ros.vo.setting.ImportParameterExcleData;
import com.redescooter.ses.web.ros.vo.setting.RosParameterExportResult;
import com.redescooter.ses.web.ros.vo.setting.RosParameterListEnter;
import com.redescooter.ses.web.ros.vo.setting.RosSaveParamentEnter;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RosRosParameterServiceImpl implements RosParameterService {

    @DubboReference
    private ParameterSettingService parameterSettingService;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    @Autowired
    private ExcelService excelService;

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<ParameterResult> list(RosParameterListEnter enter) {
        ParameterListEnter parameterListEnter = new ParameterListEnter();
        BeanUtils.copyProperties(enter, parameterListEnter);
        parameterListEnter.setSystemType(SystemTypeEnums.REDE_ROS.getValue());
        PageResult<ParameterResult> list = parameterSettingService.list(parameterListEnter);


        List<Long> createIdList = list.getList().stream().map(ParameterResult::getCreatedById).collect(Collectors.toList());
        List<OpeSysUserProfile> createUserProfileList = null;
        if (CollectionUtils.isNotEmpty(createIdList)) {
            //??????????????? ???????????????
            createUserProfileList = opeSysUserProfileService.list(new LambdaQueryWrapper<OpeSysUserProfile>().in(OpeSysUserProfile::getSysUserId,
                    createIdList));
        }

        List<Long> updateIdList = list.getList().stream().map(ParameterResult::getUpadtedById).collect(Collectors.toList());
        List<OpeSysUserProfile> updateUserProfileList = null;
        if (CollectionUtils.isNotEmpty(updateIdList)) {
            updateUserProfileList = opeSysUserProfileService.list(new LambdaQueryWrapper<OpeSysUserProfile>().in(OpeSysUserProfile::getSysUserId,
                    updateIdList));
        }


        for (ParameterResult item : list.getList()) {
            if (CollectionUtils.isNotEmpty(createUserProfileList)) {
                for (OpeSysUserProfile create : createUserProfileList) {
                    if (item.getCreatedById().equals(create.getSysUserId())) {
                        item.setCreatedByFirtName(create.getFirstName());
                        item.setCreatedByLastName(create.getLastName());
                        continue;
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(updateUserProfileList)) {
                for (OpeSysUserProfile update : updateUserProfileList) {
                    if (item.getUpadtedById().equals(update.getSysUserId())) {
                        item.setUpadtedByFirtName(update.getFirstName());
                        item.setUpadtedByLastName(update.getLastName());
                        continue;
                    }
                }
            }
        }
        return list;
    }

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    @Override
    public ParameterResult detail(IdEnter enter) {
        ParameterResult detail = parameterSettingService.detail(enter);

        OpeSysUserProfile createUserProfile = opeSysUserProfileService.getById(detail.getCreatedById());
        OpeSysUserProfile updateUserProfile = opeSysUserProfileService.getById(detail.getUpadtedById());
        if (StringManaConstant.entityIsNotNull(createUserProfile)) {
            detail.setCreatedByFirtName(createUserProfile.getFirstName());
            detail.setCreatedByLastName(createUserProfile.getLastName());
        }
        if (StringManaConstant.entityIsNotNull(updateUserProfile)) {
            detail.setUpadtedByFirtName(updateUserProfile.getFirstName());
            detail.setUpadtedByLastName(updateUserProfile.getLastName());
        }
        return detail;
    }

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult delete(IdEnter enter) {
        return parameterSettingService.delete(enter);
    }

    /**
     * ??????????????????
     *
     * @param ids
     * @param response
     * @return
     */
    @Override
    public GeneralResult export(String ids, HttpServletResponse response) {
        List<ParameterResult> parameterResultList = parameterSettingService.export(Arrays.stream(ids.split(",")).map(id -> Long.parseLong(id)).collect(Collectors.toList()),
                SystemTypeEnums.REDE_ROS.getValue());
        //??????????????????
        exportStaffUserName(parameterResultList);

        List<RosParameterExportResult> exportList = new ArrayList<>();
        parameterResultList.forEach(item -> {
            RosParameterExportResult rosParameterExportResult = new RosParameterExportResult();
            rosParameterExportResult.setParameterName(item.getParameterName());
            rosParameterExportResult.setGroupName(item.getGroupName());
            rosParameterExportResult.setEnable(item.getEnable() == 0 ? "NO" : "YES");
            rosParameterExportResult.setKey(item.getKey());
            rosParameterExportResult.setValue(item.getValue());
            rosParameterExportResult.setCreatedTime(DateUtil.getDateTime(item.getCreatedTime(), DateUtil.YMDHMSS));
            rosParameterExportResult.setFounder(StringUtils.isEmpty(item.getCreatedByFirtName() + item.getCreatedByLastName()) ? "--" : item.getCreatedByFirtName() + item.getCreatedByLastName());
            rosParameterExportResult.setUpdatedTime(DateUtil.getDateTime(item.getUpdatedTime(), DateUtil.YMDHMSS));
            rosParameterExportResult.setUpdatedTime(StringUtils.isEmpty(item.getUpadtedByFirtName() + item.getUpadtedByLastName()) ? "--" : item.getUpadtedByFirtName() + item.getUpadtedByLastName());
            exportList.add(rosParameterExportResult);
        });
        try {
            // ??????????????????????????????
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // ???????????????????????????
            response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
            // =========easypoi??????
            ExportParams exportParams = new ExportParams();
            exportParams.setSheetName("parameterExport");
            // exportParams.setDataHanlder(null);//?????????????????????????????????handler?????????????????????
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, RosParameterExportResult.class, exportList);
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            System.out.println("+++++++++++++++++++");
        }
        return new GeneralResult();
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult importParament(ImportParameterEnter enter) {
        return excelService.readExcelDataByParameter(enter);
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(RosSaveParamentEnter enter) {
        SaveParamentEnter saveParamentEnter = new SaveParamentEnter();
        BeanUtils.copyProperties(enter, saveParamentEnter);
        saveParamentEnter.setSystemType(SystemTypeEnums.REDE_ROS);
        return parameterSettingService.save(saveParamentEnter);
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public StringResult downloadExcel(GeneralEnter enter) {
        return parameterSettingService.downloadExcel(enter);
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<ParameterGroupResultList> groupList(BooleanEnter enter) {
        enter.setSystemId(SystemTypeEnums.REDE_ROS.getCode());
        return parameterSettingService.groupList(enter);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveParameterBatch(ImportParameterEnter enter, List<ImportParameterExcleData> successList) {
        //????????????
        BooleanEnter booleanEnter = new BooleanEnter(Boolean.FALSE);
        booleanEnter.setSystemId(SystemTypeEnums.REDE_ROS.getCode());
        List<ParameterGroupResultList> parameterGroupResultList = parameterSettingService.groupList(booleanEnter);

        if (CollectionUtils.isEmpty(parameterGroupResultList)) {
            throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
        }


        List<SaveParameterBatchEnter> saveParameterBatchEnterList = new ArrayList<>();
        successList.forEach(item -> {
            Long groupId = parameterGroupResultList.stream().filter(group -> StringUtils.equals(item.getGroupName(), group.getName())).findFirst().orElse(null).getId();
            SaveParameterBatchEnter saveParameterBatchEnter = new SaveParameterBatchEnter(item.getParameterName(), item.getGroupName(), groupId, item.getKey(), item.getValue(),
                    item.getEnable());
            saveParameterBatchEnter.setUserId(enter.getUserId());
            saveParameterBatchEnterList.add(saveParameterBatchEnter);
        });

        parameterSettingService.saveParameterBatchByImport(saveParameterBatchEnterList, SystemTypeEnums.REDE_ROS.getValue());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ???????????????????????????????????????????????????
     */
    @Override
    public List<ParameterListResult> getAllParamByGroup(IdEnter enter) {
        return parameterSettingService.getAllParamByGroup(enter);
    }

    /**
     * ?????????????????????????????????
     */
    @Override
    public Map<String, Map<String, String>> getAllGroupParam(GeneralEnter enter) {
        return parameterSettingService.getAllGroupParam(enter);
    }

    /**
     * ???????????? ??????????????????
     *
     * @param parameterResultList
     */
    private void exportStaffUserName(List<ParameterResult> parameterResultList) {
        if (CollectionUtils.isNotEmpty(parameterResultList)) {
            //??????????????????
            QueryWrapper<OpeSysStaff> qwCreat = new QueryWrapper<>();
            qwCreat.in(OpeSysStaff.COL_ID, parameterResultList.stream().map(ParameterResult::getCreatedById).collect(Collectors.toList()));
            List<OpeSysStaff> creatUserList = opeSysStaffService.list(qwCreat);

            if (CollectionUtils.isNotEmpty(creatUserList)) {
                // ?????????????????????
                for (ParameterResult result : parameterResultList) {
                    for (OpeSysStaff staff : creatUserList) {
                        if (result.getCreatedById().equals(staff.getId())) {
                            result.setCreatedByFirtName(staff.getFirstName());
                            result.setCreatedByLastName(staff.getLastName());
                        }
                    }
                }
            }

            QueryWrapper<OpeSysStaff> qwUpdate = new QueryWrapper<>();
            qwUpdate.in(OpeSysStaff.COL_ID, parameterResultList.stream().map(ParameterResult::getUpadtedById).collect(Collectors.toList()));
            List<OpeSysStaff> updateUserList = opeSysStaffService.list(qwUpdate);
            if (CollectionUtils.isNotEmpty(updateUserList)) {
                // ?????????????????????
                for (ParameterResult result : parameterResultList) {
                    for (OpeSysStaff staff : updateUserList) {
                        if (result.getCreatedById().equals(staff.getId())) {
                            result.setCreatedByFirtName(staff.getFirstName());
                            result.setCreatedByLastName(staff.getLastName());
                        }
                    }
                }
            }
        }
    }
}
