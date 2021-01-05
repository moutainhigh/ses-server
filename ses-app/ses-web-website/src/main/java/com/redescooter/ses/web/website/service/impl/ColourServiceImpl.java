package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.code.MainCode;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteColour;
import com.redescooter.ses.web.website.enums.ColourRangeEnums;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.service.ColourService;
import com.redescooter.ses.web.website.service.base.SiteColourService;
import com.redescooter.ses.web.website.vo.product.AddColourEnter;
import com.redescooter.ses.web.website.vo.product.ModityColourEnter;
import com.redescooter.ses.web.website.vo.product.ColourDetailsResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/5 10:17 下午
 * @Description 产品颜色服务实现类
 **/
@Slf4j
@Service
public class ColourServiceImpl implements ColourService {

    @Reference
    private IdAppService idAppService;

    @Autowired(required = true)
    private SiteColourService siteColourService;


    /**
     * 创建产品颜色
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public Boolean addColour(AddColourEnter enter) {
        SiteColour addSiteColourVO = new SiteColour();
        addSiteColourVO.setId(idAppService.getId(SequenceName.SITE_COLOUR));
        addSiteColourVO.setDr(Constant.DR_FALSE);
        addSiteColourVO.setStatus(String.valueOf(CommonStatusEnums.NORMAL.getValue()));
        addSiteColourVO.setColourName(enter.getColourName());
        addSiteColourVO.setColourCode(new StringBuffer().append("CE_").append(MainCode.generateByShuffle()).toString());
        addSiteColourVO.setColour16(enter.getColour16());
        addSiteColourVO.setColourRgb(enter.getColourRgb());
        addSiteColourVO.setColourRange(String.valueOf(ColourRangeEnums.VEHICLE.getValue()));

        if (StringUtils.isNotBlank(enter.getRemark())) {
            addSiteColourVO.setRemark(enter.getRemark());
        }
        addSiteColourVO.setRevision(0);
        addSiteColourVO.setSynchronizeFlag(false);
        addSiteColourVO.setCreatedBy(enter.getUserId());
        addSiteColourVO.setCreatedTime(new Date());
        addSiteColourVO.setUpdatedBy(enter.getUserId());
        return siteColourService.save(addSiteColourVO);
    }

    /**
     * 编辑产品颜色
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public Boolean modityColour(ModityColourEnter enter) {

        SiteColour moditySiteColourVO = new SiteColour();
        BeanUtils.copyProperties(enter, moditySiteColourVO);

        return siteColourService.update(moditySiteColourVO, new UpdateWrapper<SiteColour>().eq(SiteColour.COL_ID, enter.getId()));
    }

    /**
     * 移除产品颜色
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public Boolean removeColour(IdEnter enter) {
        return siteColourService.removeById(enter.getId());
    }

    /**
     * 获取产品颜色详情
     *
     * @param enter
     */
    @Override
    public ColourDetailsResult getColourDetails(IdEnter enter) {
        SiteColour Colour = siteColourService.getById(enter.getId());
        ColourDetailsResult result = new ColourDetailsResult();
        if (Colour != null) {
            BeanUtils.copyProperties(Colour, result);
            result.setRequestId(enter.getRequestId());
        }
        return result;
    }

    /**
     * 获取产品颜色列表
     *
     * @param enter
     */
    @Override
    public List<ColourDetailsResult> getColourList(GeneralEnter enter) {
        List<ColourDetailsResult> resultList = new ArrayList<>();
        List<SiteColour> list = siteColourService.list(new QueryWrapper<SiteColour>().eq(SiteColour.COL_DR, 0));

        if (list.size() > 0) {
            list.forEach(cr -> {
                ColourDetailsResult result = new ColourDetailsResult();
                BeanUtils.copyProperties(cr, result);
                resultList.add(result);
            });
        }
        return resultList;
    }
}
