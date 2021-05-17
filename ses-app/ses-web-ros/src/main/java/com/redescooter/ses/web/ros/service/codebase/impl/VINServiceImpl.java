package com.redescooter.ses.web.ros.service.codebase.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.dao.base.OpeCodebaseVinMapper;
import com.redescooter.ses.web.ros.dm.OpeSpecificatType;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseVinService;
import com.redescooter.ses.web.ros.service.base.OpeSpecificatTypeService;
import com.redescooter.ses.web.ros.service.codebase.VINService;
import com.redescooter.ses.web.ros.vo.codebase.SpecificatResult;
import com.redescooter.ses.web.ros.vo.codebase.VINDetailResult;
import com.redescooter.ses.web.ros.vo.codebase.VINListEnter;
import com.redescooter.ses.web.ros.vo.codebase.VINListResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 9:48
 */
@Service
@Slf4j
public class VINServiceImpl implements VINService {

    @Autowired
    private OpeSpecificatTypeService opeSpecificatTypeService;

    @Autowired
    private OpeCodebaseVinService opeCodebaseVinService;

    @Autowired
    private OpeCodebaseVinMapper opeCodebaseVinMapper;

    /**
     * 车型数据源
     */
    @Override
    public List<SpecificatResult> getSpecificatTypeData(GeneralEnter enter) {
        List<SpecificatResult> resultList = Lists.newArrayList();
        LambdaQueryWrapper<OpeSpecificatType> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeSpecificatType::getDr, Constant.DR_FALSE);
        List<OpeSpecificatType> list = opeSpecificatTypeService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (OpeSpecificatType item : list) {
                SpecificatResult result = new SpecificatResult();
                result.setSpecificatTypeId(item.getId());
                result.setSpecificatTypeName(item.getSpecificatName());
                resultList.add(result);
            }
        }
        return resultList;
    }

    /**
     * 列表
     */
    @Override
    public PageResult<VINListResult> getList(VINListEnter enter) {
        int count = opeCodebaseVinMapper.getVinListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<VINListResult> list = opeCodebaseVinMapper.getVinList(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * 详情
     */
    @Override
    public VINDetailResult getDetail(StringEnter enter) {












        return null;
    }

    /**
     * 导出
     */
    @Override
    public GeneralResult export(VINListEnter enter) {
        return null;
    }

}
