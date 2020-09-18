package com.redescooter.ses.service.foundation.service.impl.setting;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.SystemTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.setting.GroupSettingService;
import com.redescooter.ses.api.foundation.vo.setting.GroupListEnter;
import com.redescooter.ses.api.foundation.vo.setting.GroupResult;
import com.redescooter.ses.api.foundation.vo.setting.SaveGroupEnter;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.GroupSettingServiceMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaSysGroupSetting;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaSysGroupSettingService;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  @author: alex
 *  @Date: 2020/9/16 18:45
 *  @version：V ROS 1.7.1
 *  @Description:
 */
@Service
public class GroupSettingServiceImpl implements GroupSettingService {


    @Autowired
    private GroupSettingServiceMapper groupSettingServiceMapper;

    @Autowired
    private PlaSysGroupSettingService plaSysGroupSettingService;

    @Autowired
    private IdAppService idAppService;

    /**
     * 分组列表
     * @param enter
     * @return
     */
    @Override
    public PageResult<GroupResult> list(GroupListEnter enter) {
        QueryWrapper<PlaSysGroupSetting> groupQueryWrapper = new QueryWrapper<>();
        groupQueryWrapper.eq(PlaSysGroupSetting.COL_SYSTEM_TYPE, enter.getSystemType().getValue());
        if (StringUtils.isNotEmpty(enter.getKeyword())) {
            groupQueryWrapper.like(PlaSysGroupSetting.COL_GROUP_NAME, enter.getKeyword());
        }
        int count =
                plaSysGroupSettingService.count(groupQueryWrapper);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, groupSettingServiceMapper.groupList(enter));
    }

    /**
     * 详情
     * @param enter
     * @return
     */
    @Override
    public GroupResult detail(IdEnter enter) {
        PlaSysGroupSetting plaSysGroupSetting = plaSysGroupSettingService.getById(enter.getId());
        if (plaSysGroupSetting == null) {
            throw new FoundationException(ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getMessage());
        }
        return GroupResult.builder()
                .id(plaSysGroupSetting.getId())
                .groupName(plaSysGroupSetting.getGroupName())
                .desc(plaSysGroupSetting.getDesc())
                .enable(plaSysGroupSetting.getEnable() ? 1 : 0)
                .createdById(plaSysGroupSetting.getCreatedBy())
                .updatedById(plaSysGroupSetting.getUpdatedBy())
                .build();
    }

    /**
     * 保存分组
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(SaveGroupEnter enter) {
        PlaSysGroupSetting plaSysGroupSetting = null;
        if (enter.getId() == null || enter.getId() == 0) {
            //保存
            plaSysGroupSetting = buildGroup(enter);
            plaSysGroupSetting.setId(idAppService.getId(SequenceName.PLA_SYS_GROUP_SETTING));
            plaSysGroupSetting.setCreatedBy(enter.getUserId());
            plaSysGroupSetting.setCreatedTime(new Date());
        } else {
            //编辑
            plaSysGroupSetting = buildGroup(enter);
            plaSysGroupSetting.setId(enter.getId());
        }
        if (plaSysGroupSetting != null) {
            plaSysGroupSettingService.saveOrUpdate(plaSysGroupSetting);
        }
        return new GeneralResult(enter.getRequestId());
    }

    private PlaSysGroupSetting buildGroup(SaveGroupEnter enter) {
        return PlaSysGroupSetting.builder()
                .dr(0)
                .groupName(enter.getGroupName())
                .systemType(SystemTypeEnums.REDE_ROS.getValue())
                .desc(enter.getDesc())
                .enable(StringUtils.equals(enter.getEnable(), "0") ? Boolean.FALSE : Boolean.TRUE)
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    /**
     * 删除分组
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult delete(IdEnter enter) {
        PlaSysGroupSetting plaSysGroupSetting = plaSysGroupSettingService.getById(enter.getId());
        if (plaSysGroupSetting == null) {
            throw new FoundationException(ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getMessage());
        }
        plaSysGroupSettingService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 导出
     * @param id
     * @return
     */
    @Override
    public List<GroupResult> export(String id) {
        List<GroupResult> results = new ArrayList<>();
        // id可能是多个  用，隔开的
        QueryWrapper<PlaSysGroupSetting> qw = new QueryWrapper<>();
        qw.in(PlaSysGroupSetting.COL_ID,id.split(","));
        List<PlaSysGroupSetting> groupSettings = plaSysGroupSettingService.list(qw);
        if(CollectionUtils.isNotEmpty(groupSettings)){
            for (PlaSysGroupSetting setting : groupSettings) {
                GroupResult result = new GroupResult();
                result.setId(setting.getId());
                result.setGroupName(setting.getGroupName());
                result.setDesc(setting.getDesc());
                result.setEnable(setting.getEnable()==null?0:(setting.getEnable()?1:0));
                result.setCreatedTime(setting.getCreatedTime());
                result.setCreatedById(setting.getCreatedBy());
                result.setUpdatedById(setting.getUpdatedBy());
                result.setUpdatedTime(setting.getUpdatedTime());
                results.add(result);
            }
        }
        return results;
    }

    /**
     * 导入
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult importGroup(GeneralEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }
}
