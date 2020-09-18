package com.redescooter.ses.web.ros.service.setting.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.enums.base.SystemTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.setting.GroupSettingService;
import com.redescooter.ses.api.foundation.vo.setting.GroupListEnter;
import com.redescooter.ses.api.foundation.vo.setting.GroupResult;
import com.redescooter.ses.api.foundation.vo.setting.SaveGroupEnter;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.setting.RosGroupService;
import com.redescooter.ses.web.ros.vo.setting.GroupExportResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RosGroupServiceImpl implements RosGroupService {

    @Reference
    private GroupSettingService groupSettingService;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    /**
     * 分组列表
     * @param enter
     * @return
     */
    @Override
    public PageResult<GroupResult> list(GroupListEnter enter) {
        enter.setSystemType(SystemTypeEnums.REDE_ROS);
        PageResult<GroupResult> list = groupSettingService.list(enter);
        if (CollectionUtils.isEmpty(list.getList())) {
            return list;
        }
        List<OpeSysUserProfile> createUserProfileList = null;
        List<Long> createIdList = list.getList().stream().map(GroupResult::getCreatedById).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(createIdList)) {
            createUserProfileList = opeSysUserProfileService.list(new LambdaQueryWrapper<OpeSysUserProfile>().in(OpeSysUserProfile::getSysUserId,
                    createIdList));
        }
        //查询创建人 更新人信息

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
     * 详情
     * @param enter
     * @return
     */
    @Override
    public GroupResult detail(IdEnter enter) {
        GroupResult detail = groupSettingService.detail(enter);
        OpeSysUserProfile createUserProfile = opeSysUserProfileService.getById(detail.getCreatedById());
        OpeSysUserProfile updateUserProfile = opeSysUserProfileService.getById(detail.getUpdatedById());

        if (createUserProfile != null) {
            detail.setCreatedByFirstName(createUserProfile.getFirstName());
            detail.setCreatedByLastName(createUserProfile.getLastName());
        }
        if (updateUserProfile != null) {
            detail.setUpdatedByFirstName(updateUserProfile.getFirstName());
            detail.setUpdatedByLastName(updateUserProfile.getLastName());
        }
        return detail;
    }

    /**
     * 保存分组
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(SaveGroupEnter enter) {
        return groupSettingService.save(enter);
    }

    /**
     * 删除分组
     * @param enter
     * @return
     */
    @Override
    public GeneralResult delete(IdEnter enter) {
        return groupSettingService.delete(enter);
    }

    /**
     * 导出
     * @param
     * @return
     */
    @Override
    public GeneralResult export(String id, HttpServletResponse response) {
        List<GroupResult> groupList = groupSettingService.export(id);
        if(CollectionUtils.isNotEmpty(groupList)){
            // 找到创建人的名字和修改人的名字
            getUserName(groupList);
            // 现在创建人和修改人都补全了
            List<GroupExportResult> exportList = new ArrayList<>();
            for (GroupResult group : groupList) {
                GroupExportResult export = new GroupExportResult();
                export.setGroupName(group.getGroupName());
                export.setDescription(group.getDesc());
                export.setEnable(group.getEnable()==1?"Yes":"No");
                export.setFounder(group.getCreatedByFirstName()+" "+group.getCreatedByLastName());
                if(Strings.isNullOrEmpty(export.getFounder())){
                    export.setFounder("--");
                }
                export.setCreatedTime(group.getCreatedTime()==null?"--": DateUtil.format(group.getCreatedTime(),""));
                export.setUpdater(group.getUpdatedByFirstName()+" "+group.getUpdatedByLastName());
                if(Strings.isNullOrEmpty(export.getUpdater())){
                    export.setFounder("--");
                }
                export.setUpdatedTime(group.getUpdatedTime()==null?"--": DateUtil.format(group.getUpdatedTime(),""));
                exportList.add(export);
            }
            try {
                // 设置响应输出的头类型
                response.setHeader("content-Type", "application/vnd.ms-excel");
                // 下载文件的默认名称
                response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
                // =========easypoi部分
                ExportParams exportParams = new ExportParams();
                exportParams.setSheetName("group");
                // exportParams.setDataHanlder(null);//和导入一样可以设置一个handler来处理特殊数据
                Workbook workbook = ExcelExportUtil.exportExcel(exportParams, GroupExportResult.class, exportList);
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
        qwCreat.in(OpeSysStaff.COL_ID,createIds);
        List<OpeSysStaff> creatUserList = opeSysStaffService.list(qwCreat);
        if(CollectionUtils.isNotEmpty(creatUserList)){
            // 补全创建人信息
            for (GroupResult result : groupList) {
                for (OpeSysStaff staff : creatUserList) {
                    if(result.getCreatedById() == staff.getId()){
                        result.setCreatedByFirstName(staff.getFirstName());
                        result.setCreatedByLastName(staff.getLastName());
                    }
                }
            }
        }

        List<Long> updateIds = groupList.stream().map(GroupResult::getUpdatedById).collect(Collectors.toList());
        QueryWrapper<OpeSysStaff> qwUpdate = new QueryWrapper<>();
        qwUpdate.in(OpeSysStaff.COL_ID,updateIds);
        List<OpeSysStaff> updateUserList = opeSysStaffService.list(qwUpdate);
        if(CollectionUtils.isNotEmpty(updateUserList)){
            // 补全修改人信息
            for (GroupResult result : groupList) {
                for (OpeSysStaff staff : updateUserList) {
                    if(result.getUpdatedById() == staff.getId()){
                        result.setUpdatedByFirstName(staff.getFirstName());
                        result.setUpdatedByLastName(staff.getLastName());
                    }
                }
            }
        }
    }

    /**
     * 导入
     * @param enter
     */
    @Override
    public GeneralResult importGroup(GeneralEnter enter) {
        return groupSettingService.importGroup(enter);
    }
}
