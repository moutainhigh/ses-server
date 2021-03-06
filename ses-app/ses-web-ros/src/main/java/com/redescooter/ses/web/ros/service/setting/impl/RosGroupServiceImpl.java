package com.redescooter.ses.web.ros.service.setting.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.enums.base.SystemTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.setting.GroupSettingService;
import com.redescooter.ses.api.foundation.vo.setting.GroupListEnter;
import com.redescooter.ses.api.foundation.vo.setting.GroupResult;
import com.redescooter.ses.api.foundation.vo.setting.SaveGroupEnter;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.setting.RosGroupService;
import com.redescooter.ses.web.ros.vo.setting.RosGroupExportResult;
import com.redescooter.ses.web.ros.vo.setting.RosGroupListEnter;
import com.redescooter.ses.web.ros.vo.setting.RosSaveGroupEnter;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RosGroupServiceImpl implements RosGroupService {

    @DubboReference
    private GroupSettingService groupSettingService;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<GroupResult> list(RosGroupListEnter enter) {
        GroupListEnter groupListEnter = new GroupListEnter();

        BeanUtils.copyProperties(enter, groupListEnter);
        groupListEnter.setSystemType(SystemTypeEnums.REDE_ROS.getValue());
        PageResult<GroupResult> list = groupSettingService.list(groupListEnter);
        if (CollectionUtils.isEmpty(list.getList())) {
            return list;
        }
        List<OpeSysUserProfile> createUserProfileList = null;
        List<Long> createIdList = list.getList().stream().map(GroupResult::getCreatedById).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(createIdList)) {
            createUserProfileList = opeSysUserProfileService.list(new LambdaQueryWrapper<OpeSysUserProfile>().in(OpeSysUserProfile::getSysUserId,
                    createIdList));
        }
        //??????????????? ???????????????

        List<OpeSysUserProfile> updateUserProfileList = null;
        List<Long> updateIdList = list.getList().stream().map(GroupResult::getUpdatedById).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(updateIdList)) {
            updateUserProfileList = opeSysUserProfileService.list(new LambdaQueryWrapper<OpeSysUserProfile>().in(OpeSysUserProfile::getSysUserId,
                    updateIdList));
        }

        for (GroupResult item : list.getList()) {
            if (CollectionUtils.isNotEmpty(createUserProfileList)) {
                for (OpeSysUserProfile create : createUserProfileList) {
                    if (item.getCreatedById().equals(create.getSysUserId())) {
                        item.setCreatedByFirstName(create.getFirstName());
                        item.setCreatedByLastName(create.getLastName());
                        continue;
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(updateUserProfileList)) {
                for (OpeSysUserProfile update : updateUserProfileList) {
                    if (item.getUpdatedById().equals(update.getSysUserId())) {
                        item.setUpdatedByFirstName(update.getFirstName());
                        item.setUpdatedByLastName(update.getLastName());
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
    public GroupResult detail(IdEnter enter) {
        GroupResult detail = groupSettingService.detail(enter);
        OpeSysUserProfile createUserProfile = opeSysUserProfileService.getById(detail.getCreatedById());
        OpeSysUserProfile updateUserProfile = opeSysUserProfileService.getById(detail.getUpdatedById());

        if (StringManaConstant.entityIsNotNull(createUserProfile)) {
            detail.setCreatedByFirstName(createUserProfile.getFirstName());
            detail.setCreatedByLastName(createUserProfile.getLastName());
        }
        if (StringManaConstant.entityIsNotNull(updateUserProfile)) {
            detail.setUpdatedByFirstName(updateUserProfile.getFirstName());
            detail.setUpdatedByLastName(updateUserProfile.getLastName());
        }
        return detail;
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult save(RosSaveGroupEnter enter) {
        SaveGroupEnter saveGroupEnter = new SaveGroupEnter();
        BeanUtils.copyProperties(enter, saveGroupEnter);
        saveGroupEnter.setSystemType(SystemTypeEnums.REDE_ROS);
        return groupSettingService.save(saveGroupEnter);
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult delete(IdEnter enter) {
        return groupSettingService.delete(enter);
    }

    /**
     * ??????
     *
     * @param
     * @return
     */
    @Override
    public GeneralResult export(String id, HttpServletResponse response) {
        List<GroupResult> groupList = groupSettingService.export(id);
        if (CollectionUtils.isNotEmpty(groupList)) {
            // ?????????????????????????????????????????????
            getUserName(groupList);
            // ???????????????????????????????????????
            List<RosGroupExportResult> exportList = new ArrayList<>();
            for (GroupResult group : groupList) {
                RosGroupExportResult export = new RosGroupExportResult();
                export.setGroupName(group.getGroupName());
                export.setDescription(group.getDesc());
                export.setEnable(group.getEnable() == 1 ? "Yes" : "No");
                export.setFounder(group.getCreatedByFirstName() + " " + group.getCreatedByLastName());
                if (Strings.isNullOrEmpty(export.getFounder())) {
                    export.setFounder("--");
                }
                export.setCreatedTime(group.getCreatedTime() == null ? "--" : DateUtil.format(DateUtil.dateAddHour(group.getCreatedTime(), 8), ""));
                export.setUpdater(group.getUpdatedByFirstName() + " " + group.getUpdatedByLastName());
                if (Strings.isNullOrEmpty(export.getUpdater())) {
                    export.setFounder("--");
                }
                export.setUpdatedTime(group.getUpdatedTime() == null ? "--" : DateUtil.format(DateUtil.dateAddHour(group.getUpdatedTime(), 8), ""));
                exportList.add(export);
            }
            try {
                // ??????????????????????????????
                response.setHeader("content-Type", "application/vnd.ms-excel");
                // ???????????????????????????
                response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
                // =========easypoi??????
                ExportParams exportParams = new ExportParams();
                exportParams.setSheetName("group");
                // exportParams.setDataHanlder(null);//?????????????????????????????????handler?????????????????????
                Workbook workbook = ExcelExportUtil.exportExcel(exportParams, RosGroupExportResult.class, exportList);
                workbook.write(response.getOutputStream());
            } catch (Exception e) {
                System.out.println("+++++++++++++++++++");
            }
        }
        return new GeneralResult();
    }

    private void getUserName(List<GroupResult> groupList) {
        List<Long> createIds = groupList.stream().map(GroupResult::getCreatedById).collect(Collectors.toList());
        QueryWrapper<OpeSysStaff> qwCreat = new QueryWrapper<>();
        qwCreat.in(OpeSysStaff.COL_ID, createIds);
        List<OpeSysStaff> creatUserList = opeSysStaffService.list(qwCreat);
        if (CollectionUtils.isNotEmpty(creatUserList)) {
            // ?????????????????????
            for (GroupResult result : groupList) {
                for (OpeSysStaff staff : creatUserList) {
                    if (result.getCreatedById().equals(staff.getId())) {
                        result.setCreatedByFirstName(staff.getFirstName());
                        result.setCreatedByLastName(staff.getLastName());
                    }
                }
            }
        }

        List<Long> updateIds = groupList.stream().map(GroupResult::getUpdatedById).collect(Collectors.toList());
        QueryWrapper<OpeSysStaff> qwUpdate = new QueryWrapper<>();
        qwUpdate.in(OpeSysStaff.COL_ID, updateIds);
        List<OpeSysStaff> updateUserList = opeSysStaffService.list(qwUpdate);
        if (CollectionUtils.isNotEmpty(updateUserList)) {
            // ?????????????????????
            for (GroupResult result : groupList) {
                for (OpeSysStaff staff : updateUserList) {
                    if (result.getUpdatedById().equals(staff.getId())) {
                        result.setUpdatedByFirstName(staff.getFirstName());
                        result.setUpdatedByLastName(staff.getLastName());
                    }
                }
            }
        }
    }
}
