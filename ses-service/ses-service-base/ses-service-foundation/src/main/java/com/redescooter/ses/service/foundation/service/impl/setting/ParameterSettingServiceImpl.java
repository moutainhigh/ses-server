package com.redescooter.ses.service.foundation.service.impl.setting;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.setting.ParameterSettingService;
import com.redescooter.ses.api.foundation.vo.setting.ParameterGroupResultList;
import com.redescooter.ses.api.foundation.vo.setting.ParameterListEnter;
import com.redescooter.ses.api.foundation.vo.setting.ParameterResult;
import com.redescooter.ses.api.foundation.vo.setting.SaveParamentEnter;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.ParameterSettingServiceMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaSysGroupSetting;
import com.redescooter.ses.service.foundation.dm.base.PlaSysParamSetting;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaSysGroupSettingService;
import com.redescooter.ses.service.foundation.service.base.PlaSysParamSettingService;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private IdAppService idAppService;


    /**
     * 参数名称
     * @param enter
     * @return
     */
    @Override
    public PageResult<ParameterResult> list(ParameterListEnter enter) {

        QueryWrapper<PlaSysParamSetting> paramQueryWrapper = new QueryWrapper();
        if (enter.getGroupId() != null && enter.getGroupId() != 0) {
            paramQueryWrapper.eq(PlaSysParamSetting.COL_GROUP_ID, enter.getGroupId());
        }
        if (StringUtils.isNotBlank(enter.getKeyword())) {
            paramQueryWrapper.like(PlaSysParamSetting.COL_PARAMETER_NAME, enter.getKeyword());
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
    @Transactional
    @Override
    public GeneralResult delete(IdEnter enter) {
        PlaSysParamSetting plaSysParamSetting = plaSysParamSettingService.getById(enter.getId());
        if (plaSysParamSetting == null) {
            throw new FoundationException(ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getMessage());
        }
        if(plaSysParamSetting.getEnable()){
            throw new FoundationException(ExceptionCodeEnums.ENABLE_NOT_DELETE.getCode(), ExceptionCodeEnums.ENABLE_NOT_DELETE.getMessage());
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
    @Transactional
    @Override
    public GeneralResult save(SaveParamentEnter enter) {
        PlaSysGroupSetting plaSysGroupSetting = plaSysGroupSettingService.getById(enter.getGroupId());
        if (plaSysGroupSetting == null) {
            throw new FoundationException(ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getMessage());
        }

        PlaSysParamSetting plaSysParamSetting = null;
        if (enter.getId() == null || enter.getId() == 0) {
            //编辑
            plaSysParamSetting = buildParament(enter);
            plaSysParamSetting.setId(idAppService.getId(SequenceName.PLA_SYS_PARAM_SETTING));
            plaSysParamSetting.setCreatedBy(enter.getUserId());
            plaSysParamSetting.setCreatedTime(new Date());
        } else {
            plaSysParamSetting = buildParament(enter);
            plaSysParamSetting.setId(enter.getId());
        }

        if (plaSysParamSetting != null) {
            plaSysParamSettingService.saveOrUpdate(plaSysParamSetting);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 下载模版
     * @param enter
     * @return
     */
    @Override
    public StringResult downloadExcel(GeneralEnter enter) {
        return new StringResult(Constant.PARAMETER_DOWNLOAD_URL);
    }

    /**
     * 分组列表
     * @param enter
     * @return
     */
    @Override
    public List<ParameterGroupResultList> groupList(StringEnter enter) {
        List<ParameterGroupResultList> result = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(PlaSysGroupSetting.COL_SYSTEM_TYPE, enter.getSt());
        queryWrapper.eq(PlaSysGroupSetting.COL_ENABLE, Boolean.TRUE);
        List<PlaSysGroupSetting> list = plaSysGroupSettingService.list();
        if (CollectionUtils.isEmpty(list)) {
            return result;
        }
        list.forEach(item -> {
            result.add(ParameterGroupResultList.builder().id(item.getId()).name(item.getGroupName()).build());
        });
        return result;
    }

    private PlaSysParamSetting buildParament(SaveParamentEnter enter) {
        return PlaSysParamSetting.builder()
                .systemType(enter.getSystemType().getValue())
                .groupId(enter.getGroupId())
                .parameterName(enter.getParameterName())
                .paramKey(enter.getKey())
                .paramValue(enter.getValue())
                .enable(enter.getEnable() == 1 ? Boolean.TRUE : Boolean.FALSE)
                .desc(enter.getDesc())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }
}
