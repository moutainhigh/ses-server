package com.redescooter.ses.web.ros.service.setting.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.setting.GroupSettingService;
import com.redescooter.ses.api.foundation.vo.setting.GroupListEnter;
import com.redescooter.ses.api.foundation.vo.setting.GroupResult;
import com.redescooter.ses.api.foundation.vo.setting.SaveGroupEnter;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.setting.RosGroupService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RosGroupServiceImpl implements RosGroupService {

    @Reference
    private GroupSettingService groupSettingService;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    /**
     * 分组列表
     * @param enter
     * @return
     */
    @Override
    public PageResult<GroupResult> list(GroupListEnter enter) {
        PageResult<GroupResult> list = groupSettingService.list(enter);
        if (CollectionUtils.isEmpty(list.getList())) {
            return list;
        }
        //查询创建人 更新人信息
        List<OpeSysUserProfile> createUserProfileList = opeSysUserProfileService.list(new LambdaQueryWrapper<OpeSysUserProfile>().in(OpeSysUserProfile::getSysUserId,
                list.getList().stream().map(GroupResult::getCreatedById).collect(Collectors.toList())));

        List<OpeSysUserProfile> updateUserProfileList = opeSysUserProfileService.list(new LambdaQueryWrapper<OpeSysUserProfile>().in(OpeSysUserProfile::getSysUserId,
                list.getList().stream().map(GroupResult::getUpdatedById).collect(Collectors.toList())));

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
     * @param enter
     * @return
     */
    @Override
    public GeneralResult export(GeneralEnter enter) {
        return groupSettingService.export(enter);
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
