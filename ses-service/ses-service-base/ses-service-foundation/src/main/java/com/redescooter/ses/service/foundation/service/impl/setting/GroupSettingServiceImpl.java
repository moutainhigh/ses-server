package com.redescooter.ses.service.foundation.service.impl.setting;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
        int count =
                plaSysGroupSettingService.count(new LambdaQueryWrapper<PlaSysGroupSetting>().eq(PlaSysGroupSetting::getSystemType, enter.getSystemType().getMessage()).like(PlaSysGroupSetting::getGroupName,
                        enter.getKeyword()));
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, 1, groupSettingServiceMapper.groupList(enter));
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
                .enable(plaSysGroupSetting.getEnable())
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
        }
        if (plaSysGroupSetting != null) {
            plaSysGroupSettingService.saveOrUpdate(plaSysGroupSetting);
        }
        return new GeneralResult(enter.getRequestId());
    }

    private PlaSysGroupSetting buildGroup(SaveGroupEnter enter) {
        return PlaSysGroupSetting.builder()
                .dr(0)
                .systemType(SystemTypeEnums.REDE_ROS.getValue())
                .desc(enter.getDesc())
                .enable(enter.getEnable())
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
     * @param enter
     * @return
     */
    @Override
    public GeneralResult export(GeneralEnter enter) {
        return new GeneralResult(enter.getRequestId());
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
