package com.redescooter.ses.web.ros.service.specificat.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.specificat.ColorServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeColor;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBomDraft;
import com.redescooter.ses.web.ros.dm.OpeSaleScooter;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeColorService;
import com.redescooter.ses.web.ros.service.base.OpeProductionScooterBomDraftService;
import com.redescooter.ses.web.ros.service.base.OpeProductionScooterBomService;
import com.redescooter.ses.web.ros.service.base.OpeSaleScooterService;
import com.redescooter.ses.web.ros.service.specificat.ColorService;
import com.redescooter.ses.web.ros.vo.specificat.ColorDataResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorListResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorSaveOrEditEnter;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassNameColorServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:37
 * @Version V1.0
 **/
@Service
public class ColorServiceImpl implements ColorService {

    @Autowired
    private OpeColorService opeColorService;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private ColorServiceMapper colorServiceMapper;

    @Autowired
    private OpeSaleScooterService opeSaleScooterService;

    @Autowired
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Autowired
    private OpeProductionScooterBomDraftService opeProductionScooterBomDraftService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResult colorSave(ColorSaveOrEditEnter enter) {
        // 去空格
        SesStringUtils.objStringTrim(enter);
        // 校验字段长度
        checkFiledLength(enter);
        // 校验色值唯一
        checkColorValue(enter);
        OpeColor color = new OpeColor();
        color.setColorName(enter.getColorName());
        color.setColorValue(enter.getColorValue());
        color.setCreatedBy(enter.getUserId());
        color.setCreatedTime(new Date());
        color.setUpdatedBy(enter.getUserId());
        color.setUpdatedTime(new Date());
        color.setDr(0);
        color.setId(idAppService.getId(SequenceName.OPE_COLOR));
        opeColorService.insertOrUpdate(color);
        return new GeneralResult(enter.getRequestId());
    }


    public void checkColorValue(ColorSaveOrEditEnter enter) {
        QueryWrapper<OpeColor> qw = new QueryWrapper<>();
        qw.eq(OpeColor.COL_COLOR_VALUE, enter.getColorValue());
        if (enter.getId() != null) {
            qw.ne(OpeColor.COL_ID, enter.getId());
        }
        Integer count = opeColorService.count(qw);
        if (count > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_VALUE_EXIST.getCode(), ExceptionCodeEnums.COLOR_VALUE_EXIST.getMessage());
        }
    }


    /**
     * @return
     * @Author Aleks
     * @Description 校验字段长度是否正确（暂时只要求校验色值）
     * @Date 2020/9/29 11:07
     * @Param [enter]
     **/
    public void checkFiledLength(ColorSaveOrEditEnter enter) {
        if (Strings.isNullOrEmpty(enter.getColorValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_VALUE_NOT_NULL.getCode(), ExceptionCodeEnums.COLOR_VALUE_NOT_NULL.getMessage());
        }
        if (enter.getColorValue().length() != 7) {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_VALUE_LENGTH_ERROR.getCode(), ExceptionCodeEnums.COLOR_VALUE_LENGTH_ERROR.getMessage());
        }
    }


    @Override
    public PageResult<ColorListResult> colorList(PageEnter enter) {
        int num = colorServiceMapper.listNum();
        if (num == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<ColorListResult> list = colorServiceMapper.colorList(enter);
        return PageResult.create(enter, num, list);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResult colorDelete(IdEnter enter) {
        // todo 后期加校验  判断颜色是否被使用
        // 2020 10 14追加校验 判断颜色是否被使用（销售整车）
        QueryWrapper<OpeSaleScooter> qw = new QueryWrapper<>();
        qw.eq(OpeSaleScooter.COL_COLOR_ID, enter.getId());
        int count = opeSaleScooterService.count(qw);
        if (count > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_IS_USED.getCode(), ExceptionCodeEnums.COLOR_IS_USED.getMessage());
        }
        // 2020 10 14追加校验 判断颜色是否被使用（整车）
        QueryWrapper<OpeProductionScooterBom> scooterBomQueryWrapper = new QueryWrapper<>();
        scooterBomQueryWrapper.eq(OpeProductionScooterBom.COL_COLOR_ID, enter.getId());
        int count1 = opeProductionScooterBomService.count(scooterBomQueryWrapper);
        if (count1 > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_IS_USED.getCode(), ExceptionCodeEnums.COLOR_IS_USED.getMessage());
        }
        // 2020 10 14追加校验 判断颜色是否被使用（整车草稿）
        QueryWrapper<OpeProductionScooterBomDraft> scooterBomDraftQueryWrapper = new QueryWrapper<>();
        scooterBomDraftQueryWrapper.eq(OpeProductionScooterBomDraft.COL_COLOR_ID, enter.getId());
        int count2 = opeProductionScooterBomDraftService.count(scooterBomDraftQueryWrapper);
        if (count2 > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_IS_USED.getCode(), ExceptionCodeEnums.COLOR_IS_USED.getMessage());
        }
        opeColorService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public List<ColorDataResult> colorData(GeneralEnter enter) {
        List<ColorDataResult> results = colorServiceMapper.colorData();
        return results;
    }
}
