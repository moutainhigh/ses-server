package com.redescooter.ses.web.ros.service.specificat.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.specificat.SpecificatGroupServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleScooter;
import com.redescooter.ses.web.ros.dm.OpeSpecificatGroup;
import com.redescooter.ses.web.ros.dm.OpeSpecificatType;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSaleScooterService;
import com.redescooter.ses.web.ros.service.base.OpeSpecificatGroupService;
import com.redescooter.ses.web.ros.service.base.OpeSpecificatTypeService;
import com.redescooter.ses.web.ros.service.specificat.SpecificatGroupService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupDataResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupListEnter;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupListResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupSaveOrEditEnter;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassNameSpecificatGroupServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:43
 * @Version V1.0
 **/
@Service
public class SpecificatGroupServiceImpl implements SpecificatGroupService {

    @Autowired
    private OpeSpecificatGroupService opeSpecificatGroupService;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private SpecificatGroupServiceMapper specificatGroupServiceMapper;

    @Autowired
    private OpeSpecificatTypeService opeSpecificatTypeService;

    @Autowired
    private OpeSaleScooterService opeSaleScooterService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult specificatGroupSave(SpecificatGroupSaveOrEditEnter enter) {
        // ?????????
        SesStringUtils.objStringTrim(enter);
        OpeSpecificatGroup specificatGroup = new OpeSpecificatGroup();
        specificatGroup.setGroupName(enter.getGroupName());
        specificatGroup.setProductionType(enter.getProductionType());
        specificatGroup.setCreatedBy(enter.getUserId());
        specificatGroup.setCreatedTime(new Date());
        specificatGroup.setUpdatedBy(enter.getUserId());
        specificatGroup.setUpdatedTime(new Date());
        specificatGroup.setDr(0);
        specificatGroup.setId(idAppService.getId(SequenceName.OPE_SPECIFICAT_GROUP));
        opeSpecificatGroupService.saveOrUpdate(specificatGroup);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult specificatGroupEdit(SpecificatGroupSaveOrEditEnter enter) {
        // ?????????
        SesStringUtils.objStringTrim(enter);
        OpeSpecificatGroup group = opeSpecificatGroupService.getById(enter.getId());
        if(StringManaConstant.entityIsNull(group)){
            throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
        }
        group.setGroupName(enter.getGroupName());
        group.setProductionType(enter.getProductionType());
        group.setUpdatedBy(enter.getUserId());
        group.setUpdatedTime(new Date());
        opeSpecificatGroupService.updateById(group);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public PageResult<SpecificatGroupListResult> specificatGroupList(SpecificatGroupListEnter enter) {
        // ?????????
        SesStringUtils.objStringTrim(enter);
        int num = specificatGroupServiceMapper.listNum(enter);
        if(NumberUtil.eqZero(num)){
            return PageResult.createZeroRowResult(enter);
        }
        List<SpecificatGroupListResult> list = specificatGroupServiceMapper.groupList(enter);
        return PageResult.create(enter, num, list);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult specificatGroupDelete(IdEnter enter) {
        // ???????????? ?????????????????????????????????????????????
        QueryWrapper<OpeSpecificatType> qw = new QueryWrapper<>();
        qw.eq(OpeSpecificatType.COL_GROUP_ID,enter.getId());
        int count = opeSpecificatTypeService.count(qw);
        if(0 < count){
            throw new SesWebRosException(ExceptionCodeEnums.GROUP_BE_USED.getCode(), ExceptionCodeEnums.GROUP_BE_USED.getMessage());
        }
        // 2020 10 14 ?????? ?????????????????????????????????
        QueryWrapper<OpeSaleScooter> scooterQueryWrapper = new QueryWrapper<>();
        scooterQueryWrapper.eq(OpeSaleScooter.COL_COLOR_ID,enter.getId());
        int count1 = opeSaleScooterService.count(scooterQueryWrapper);
        if(0 < count1){
            throw new SesWebRosException(ExceptionCodeEnums.GROUP_BE_USED.getCode(), ExceptionCodeEnums.GROUP_BE_USED.getMessage());
        }
        opeSpecificatGroupService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public List<SpecificatGroupDataResult> specificatGroupData(GeneralEnter enter) {
        List<SpecificatGroupDataResult> result = new ArrayList<>();
        result = specificatGroupServiceMapper.specificatGroupData();
        return result;
    }


}
