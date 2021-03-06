package com.redescooter.ses.service.foundation.service.impl.setting;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.base.SystemTypeEnums;
import com.redescooter.ses.api.common.vo.base.BooleanEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.setting.ParameterSettingService;
import com.redescooter.ses.api.foundation.vo.setting.ParameterGroupResultList;
import com.redescooter.ses.api.foundation.vo.setting.ParameterListEnter;
import com.redescooter.ses.api.foundation.vo.setting.ParameterListResult;
import com.redescooter.ses.api.foundation.vo.setting.ParameterResult;
import com.redescooter.ses.api.foundation.vo.setting.SaveParamentEnter;
import com.redescooter.ses.api.foundation.vo.setting.SaveParameterBatchEnter;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.ParameterSettingServiceMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaSysGroupSetting;
import com.redescooter.ses.service.foundation.dm.base.PlaSysParamSetting;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaSysGroupSettingService;
import com.redescooter.ses.service.foundation.service.base.PlaSysParamSettingService;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: alex
 * @Date: 2020/9/16 18:52
 * @version???V ROS 1.7.1
 * @Description:
 */
@DubboService
public class ParameterSettingServiceImpl implements ParameterSettingService {

    @Autowired
    private PlaSysParamSettingService plaSysParamSettingService;

    @Autowired
    private PlaSysGroupSettingService plaSysGroupSettingService;

    @Autowired
    private ParameterSettingServiceMapper parameterSettingServiceMapper;

    @DubboReference
    private IdAppService idAppService;


    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<ParameterResult> list(ParameterListEnter enter) {

        QueryWrapper<PlaSysParamSetting> paramQueryWrapper = new QueryWrapper();
        if (null != enter.getGroupId() && 0 != enter.getGroupId()) {
            paramQueryWrapper.eq(PlaSysParamSetting.COL_GROUP_ID, enter.getGroupId());
        }
        if (StringUtils.isNotBlank(enter.getKeyword())) {
            paramQueryWrapper.like(PlaSysParamSetting.COL_PARAMETER_NAME, enter.getKeyword());
        }
        int count = plaSysParamSettingService.count(paramQueryWrapper);
        if (0 == count) {
            PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, parameterSettingServiceMapper.paramList(enter));
    }

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    @Override
    public ParameterResult detail(IdEnter enter) {
        return parameterSettingServiceMapper.detail(enter);
    }

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult delete(IdEnter enter) {
        PlaSysParamSetting plaSysParamSetting = plaSysParamSettingService.getById(enter.getId());
        if (null == plaSysParamSetting) {
            throw new FoundationException(ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getMessage());
        }
        if (plaSysParamSetting.getEnable()) {
            throw new FoundationException(ExceptionCodeEnums.ENABLE_NOT_DELETE.getCode(), ExceptionCodeEnums.ENABLE_NOT_DELETE.getMessage());
        }
        plaSysParamSettingService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????????
     *
     * @param ids
     * @return
     */
    @Override
    public List<ParameterResult> export(List<Long> ids, String systemType) {
        return parameterSettingServiceMapper.export(ids, systemType);
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult importParament(GeneralEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(SaveParamentEnter enter) {
        PlaSysGroupSetting plaSysGroupSetting = plaSysGroupSettingService.getById(enter.getGroupId());
        if (null == plaSysGroupSetting) {
            throw new FoundationException(ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getMessage());
        }

        PlaSysParamSetting plaSysParamSetting = null;
        if (null == enter.getId() || 0 == enter.getId()) {
            //??????
            plaSysParamSetting = buildParament(enter);
            plaSysParamSetting.setId(idAppService.getId(SequenceName.PLA_SYS_PARAM_SETTING));
            plaSysParamSetting.setCreatedBy(enter.getUserId());
            plaSysParamSetting.setCreatedTime(new Date());
        } else {
            plaSysParamSetting = buildParament(enter);
            plaSysParamSetting.setId(enter.getId());
        }

        if (null != plaSysParamSetting) {
            plaSysParamSettingService.saveOrUpdate(plaSysParamSetting);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public StringResult downloadExcel(GeneralEnter enter) {
        return new StringResult(Constant.PARAMETER_DOWNLOAD_URL);
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<ParameterGroupResultList> groupList(BooleanEnter enter) {
        List<ParameterGroupResultList> result = new ArrayList<>();
        SystemTypeEnums systemTypeEnumsByCode = SystemTypeEnums.getSystemTypeEnumsByCode(enter.getSystemId());
        if (null == systemTypeEnumsByCode) {
            return result;
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(PlaSysGroupSetting.COL_SYSTEM_TYPE, systemTypeEnumsByCode.getValue());
        if (enter.getDisable()) {
            queryWrapper.eq(PlaSysGroupSetting.COL_ENABLE, Boolean.TRUE);
        }
        List<PlaSysGroupSetting> list = plaSysGroupSettingService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return result;
        }
        list.forEach(item -> {
            result.add(ParameterGroupResultList.builder().id(item.getId()).name(item.getGroupName()).build());
        });
        return result;
    }

    /**
     * ???????????????
     *
     * @param parameterNames
     * @param systemType
     * @return
     */
    @Override
    public List<ParameterResult> checkParameterName(List<String> parameterNames, String systemType) {
        return parameterSettingServiceMapper.checkParameterName(parameterNames, systemType);
    }

    /**
     * ????????????????????????
     *
     * @param saveParameterBatchEnterList
     * @param systemType
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void saveParameterBatchByImport(List<SaveParameterBatchEnter> saveParameterBatchEnterList, String systemType) {

        List<PlaSysParamSetting> saveList = new ArrayList<>();
        saveParameterBatchEnterList.forEach(item -> {
            PlaSysParamSetting sysParamSetting = PlaSysParamSetting.builder()
                    .id(idAppService.getId(SequenceName.PLA_SYS_PARAM_SETTING))
                    .dr(Constant.DR_FALSE)
                    .systemType(systemType)
                    .groupId(item.getGroupId())
                    .parameterName(item.getParameterName())
                    .paramKey(item.getKey())
                    .paramValue(item.getValue())
                    .enable(item.getEnable())
                    .desc(null)
                    .createdBy(item.getUserId())
                    .createdTime(new Date())
                    .updatedBy(item.getUserId())
                    .updatedTime(new Date())
                    .build();
            saveList.add(sysParamSetting);
        });
        if (CollectionUtils.isNotEmpty(saveList)) {
            plaSysParamSettingService.saveBatch(saveList);
        }

    }

    /**
     * ???????????????????????????????????????????????????
     */
    @Override
    public List<ParameterListResult> getAllParamByGroup(IdEnter enter) {
        PlaSysGroupSetting group = plaSysGroupSettingService.getById(enter.getId());
        if (null == group) {
            throw new FoundationException(ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getMessage());
        }
        if (!group.getEnable()) {
            throw new FoundationException(ExceptionCodeEnums.GROUP_NOT_ENABLE.getCode(), ExceptionCodeEnums.GROUP_NOT_ENABLE.getMessage());
        }

        List<ParameterListResult> result = Lists.newArrayList();
        LambdaQueryWrapper<PlaSysParamSetting> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PlaSysParamSetting::getDr, Constant.DR_FALSE);
        wrapper.eq(PlaSysParamSetting::getGroupId, enter.getId());
        wrapper.eq(PlaSysParamSetting::getEnable, Boolean.TRUE);
        List<PlaSysParamSetting> list = plaSysParamSettingService.list(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            for (PlaSysParamSetting item : list) {
                ParameterListResult model = new ParameterListResult();
                BeanUtils.copyProperties(item, model);
                result.add(model);
            }
        }
        return result;
    }

    /**
     * ?????????????????????????????????
     */
    @Override
    public Map<String, Map<String, String>> getAllGroupParam(GeneralEnter enter) {
        // ????????????
        Map<String, Map<String, String>> result = Maps.newHashMap();

        LambdaQueryWrapper<PlaSysGroupSetting> qw = new LambdaQueryWrapper<>();
        qw.eq(PlaSysGroupSetting::getDr, Constant.DR_FALSE);
        qw.eq(PlaSysGroupSetting::getEnable, Boolean.TRUE);
        List<PlaSysGroupSetting> groupList = plaSysGroupSettingService.list(qw);
        if (CollectionUtils.isNotEmpty(groupList)) {
            for (PlaSysGroupSetting group : groupList) {
                LambdaQueryWrapper<PlaSysParamSetting> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(PlaSysParamSetting::getDr, Constant.DR_FALSE);
                wrapper.eq(PlaSysParamSetting::getEnable, Boolean.TRUE);
                wrapper.eq(PlaSysParamSetting::getGroupId, group.getId());
                List<PlaSysParamSetting> list = plaSysParamSettingService.list(wrapper);
                // ??????groupId?????????????????????
                Map<Long, List<PlaSysParamSetting>> collect = list.stream().collect(Collectors.groupingBy(o -> o.getGroupId()));

                for (Map.Entry<Long, List<PlaSysParamSetting>> map : collect.entrySet()) {
                    Long key = map.getKey();
                    List<PlaSysParamSetting> value = map.getValue();

                    Map<String, String> valueMap = Maps.newHashMap();
                    if (CollectionUtils.isNotEmpty(value)) {
                        for (PlaSysParamSetting item : value) {
                            valueMap.put(item.getParamKey(), item.getParamValue());
                        }
                    }
                    String groupName = plaSysGroupSettingService.getById(key).getGroupName();
                    result.put(groupName, valueMap);
                }
            }
        }
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
