package com.redescooter.ses.web.ros.service.setting.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.base.SystemTypeEnums;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.setting.ParameterSettingService;
import com.redescooter.ses.api.foundation.vo.setting.ParameterGroupResultList;
import com.redescooter.ses.api.foundation.vo.setting.ParameterListEnter;
import com.redescooter.ses.api.foundation.vo.setting.ParameterResult;
import com.redescooter.ses.api.foundation.vo.setting.SaveParamentEnter;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.setting.ParameterService;
import com.redescooter.ses.web.ros.vo.setting.RosParameterListEnter;
import com.redescooter.ses.web.ros.vo.setting.RosSaveParamentEnter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParameterServiceImpl implements ParameterService {

    @Reference
    private ParameterSettingService parameterSettingService;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    /**
     * 参数名称
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
            //查询创建人 更新人信息
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
     * 详情
     * @param enter
     * @return
     */
    @Override
    public ParameterResult detail(IdEnter enter) {
        ParameterResult detail = parameterSettingService.detail(enter);

        OpeSysUserProfile createUserProfile = opeSysUserProfileService.getById(detail.getCreatedById());
        OpeSysUserProfile updateUserProfile = opeSysUserProfileService.getById(detail.getUpadtedById());
        if (createUserProfile != null) {
            detail.setCreatedByFirtName(createUserProfile.getFirstName());
            detail.setCreatedByLastName(createUserProfile.getLastName());
        }
        if (updateUserProfile != null) {
            detail.setUpadtedByFirtName(updateUserProfile.getFirstName());
            detail.setUpadtedByLastName(updateUserProfile.getLastName());
        }
        return detail;
    }

    /**
     * 删除
     * @param enter
     * @return
     */
    @Override
    public GeneralResult delete(IdEnter enter) {
        return parameterSettingService.delete(enter);
    }

    /**
     * 导出参数列表
     * @param enter
     * @return
     */
    @Override
    public GeneralResult export(GeneralEnter enter) {
        return parameterSettingService.export(enter);
    }

    /**
     * 导出参数列表
     * @param enter
     * @return
     */
    @Override
    public GeneralResult importParament(GeneralEnter enter) {
        return parameterSettingService.importParament(enter);
    }

    /**
     * 参数保存编辑
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
     * 下载模版
     * @param enter
     * @return
     */
    @Override
    public StringResult downloadExcel(GeneralEnter enter) {
        return parameterSettingService.downloadExcel(enter);
    }

    /**
     * 分组列表
     * @param enter
     * @return
     */
    @Override
    public List<ParameterGroupResultList> groupList(GeneralEnter enter) {
        return parameterSettingService.groupList(new StringEnter(SystemTypeEnums.REDE_ROS.getValue()));
    }
}
