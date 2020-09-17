package com.redescooter.ses.service.foundation.service.impl.setting;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.setting.ParameterSettingService;
import com.redescooter.ses.api.foundation.vo.setting.ParameterListEnter;
import com.redescooter.ses.api.foundation.vo.setting.ParameterResult;
import com.redescooter.ses.api.foundation.vo.setting.SaveParamentEnter;
import com.redescooter.ses.service.foundation.dao.ParameterSettingServiceMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaSysGroupSetting;
import com.redescooter.ses.service.foundation.dm.base.PlaSysParamSetting;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaSysGroupSettingService;
import com.redescooter.ses.service.foundation.service.base.PlaSysParamSettingService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 *  @author: alex
 *  @Date: 2020/9/16 18:52
 *  @version：V ROS 1.7.1
 *  @Description:
 */

@Service
public class ParameterSettingServiceImpl implements ParameterSettingService {

    @Autowired
    private PlaSysParamSettingService plaSysParamSettingService;


    @Autowired
    private PlaSysGroupSettingService plaSysGroupSettingService;

    @Autowired
    private ParameterSettingServiceMapper parameterSettingServiceMapper;


    /**
     * 参数名称
     * @param enter
     * @return
     */
    @Override
    public PageResult<ParameterResult> list(ParameterListEnter enter) {

        QueryWrapper<PlaSysParamSetting> paramQueryWrapper = new QueryWrapper();
        paramQueryWrapper.eq(PlaSysParamSetting.COL_GROUP_ID, enter.getGroupId());
        if (StringUtils.isNotBlank(enter.getKeyword())) {
            paramQueryWrapper.eq(PlaSysParamSetting.COL_PARAMETER_NAME, enter.getKeyword());
        }
        int count = plaSysParamSettingService.count(paramQueryWrapper);
        if (count == 0) {
            PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, parameterSettingServiceMapper.paramList(enter));
    }

    /**
     * 详情
     * @param enter
     * @return
     */
    @Override
    public ParameterResult detail(IdEnter enter) {
        return parameterSettingServiceMapper.detail(enter);
    }

    /**
     * 删除
     * @param enter
     * @return
     */
    @Override
    public GeneralResult delete(IdEnter enter) {
        PlaSysParamSetting plaSysParamSetting = plaSysParamSettingService.getById(enter.getId());
        if (plaSysParamSetting == null) {
            throw new FoundationException(ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getMessage());
        }
        plaSysParamSettingService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 导出参数列表
     * @param enter
     * @return
     */
    @Override
    public GeneralResult export(GeneralEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 导出参数列表
     * @param enter
     * @return
     */
    @Override
    public GeneralResult importParament(GeneralEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 参数保存编辑
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(SaveParamentEnter enter) {
        PlaSysGroupSetting plaSysGroupSetting = plaSysGroupSettingService.getById(enter.getGroupId());
        if (plaSysGroupSetting == null) {
            throw new FoundationException(ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getMessage());
        }

        if (enter.getId() == null || enter.getId() == 0) {
            //编辑
        } else {
                    .createdBy(enter.getUserId())
                    .createdTime(new Date())
        }

        PlaSysParamSetting plaSysParamSetting = PlaSysParamSetting.builder()
                .systemType(enter.getSystemType().getValue())
                .groupId(enter.getGroupId())
                .parameterName(enter.getParameterName())
                .paramKey(enter.getKey())
                .paramValue(enter.getValue())
                .enable(enter.getEnable())
                .desc(enter.getDesc())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();


        return new GeneralResult(enter.getRequestId());
    }
}
