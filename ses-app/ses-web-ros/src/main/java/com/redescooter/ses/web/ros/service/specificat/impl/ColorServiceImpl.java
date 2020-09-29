package com.redescooter.ses.web.ros.service.specificat.impl;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.specificat.ColorServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeColor;
import com.redescooter.ses.web.ros.service.base.OpeColorService;
import com.redescooter.ses.web.ros.service.specificat.ColorService;
import com.redescooter.ses.web.ros.vo.specificat.ColorDataResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorListResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorSaveOrEditEnter;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Reference
    private IdAppService idAppService;

    @Autowired
    private ColorServiceMapper colorServiceMapper;

    @Override
    public GeneralResult colorSave(ColorSaveOrEditEnter enter) {
        // 去空格
        SesStringUtils.objStringTrim(enter);
        OpeColor  color = new OpeColor();
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


    @Override
    public PageResult<ColorListResult> colorList(PageEnter enter) {
        int num = colorServiceMapper.listNum();
        if (num == 0){
            return PageResult.createZeroRowResult(enter);
        }
        List<ColorListResult>  list = colorServiceMapper.colorList(enter);
        return PageResult.create(enter, num, list);
    }


    @Override
    public GeneralResult colorDelete(IdEnter enter) {
        // todo 后期加校验  判断颜色是否被使用
        opeColorService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public List<ColorDataResult> colorData(GeneralEnter enter) {
        List<ColorDataResult> results = colorServiceMapper.colorData();
        return results;
    }
}
