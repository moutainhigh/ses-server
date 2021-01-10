package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteParts;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.enums.PartsProcurementSourceEnums;
import com.redescooter.ses.web.website.enums.PartsTypeEnums;
import com.redescooter.ses.web.website.service.PartsService;
import com.redescooter.ses.web.website.service.base.SitePartsService;
import com.redescooter.ses.web.website.vo.parts.AddPartsEnter;
import com.redescooter.ses.web.website.vo.parts.ModityPartsEnter;
import com.redescooter.ses.web.website.vo.parts.PartsDetailsResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 3:55 下午
 * @Description 配件服务接口实现类
 **/

@Slf4j
@Service
public class PartsServiceImpl implements PartsService {

    @Autowired(required = true)
    private SitePartsService sitePartsService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 创建配件
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult addParts(AddPartsEnter enter) {
        SiteParts addSitePartsVO = new SiteParts();
        addSitePartsVO.setId(idAppService.getId(SequenceName.SITE_PARTS));
        addSitePartsVO.setDr(Constant.DR_FALSE);
        addSitePartsVO.setStatus(String.valueOf(CommonStatusEnums.NORMAL.getValue()));
        if (enter.getPartsType().equals(String.valueOf(PartsTypeEnums.BATTERY.getValue()))) {
            addSitePartsVO.setPartsType(String.valueOf(PartsTypeEnums.BATTERY.getValue()));
        } else {
            addSitePartsVO.setPartsType(String.valueOf(PartsTypeEnums.ACCESSORY.getValue()));
        }
        addSitePartsVO.setPartsNumber(enter.getPartsNumber());
        addSitePartsVO.setCnName(enter.getCnName());
        addSitePartsVO.setFrName(enter.getFrName());
        addSitePartsVO.setEnName(enter.getEnName());
        addSitePartsVO.setPrice(enter.getPrice());
        addSitePartsVO.setCurrencyUnit("€");
        addSitePartsVO.setSources(String.valueOf(PartsProcurementSourceEnums.CN.getValue()));
        addSitePartsVO.setRevision(0);
        addSitePartsVO.setSynchronizeFlag(false);
        addSitePartsVO.setCreatedBy(enter.getUserId());
        addSitePartsVO.setCreatedTime(new Date());
        addSitePartsVO.setUpdatedBy(enter.getUserId());

        sitePartsService.save(addSitePartsVO);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 编辑配件
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult modityParts(ModityPartsEnter enter) {

        SiteParts modityPartsClassVO = new SiteParts();
        BeanUtils.copyProperties(enter, modityPartsClassVO);

        sitePartsService.update(modityPartsClassVO, new UpdateWrapper<SiteParts>().eq(SiteParts.COL_ID, enter.getId()));
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 移除配件
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult removeParts(IdEnter enter) {
        sitePartsService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 配件详情
     *
     * @param enter
     * @return
     */
    @Override
    public PartsDetailsResult partsDetails(IdEnter enter) {
        SiteParts parts = sitePartsService.getById(enter.getId());
        PartsDetailsResult result = new PartsDetailsResult();

        if (parts != null) {
            BeanUtils.copyProperties(parts, result);
            result.setRequestId(enter.getRequestId());
        }
        return result;
    }

    /**
     * 配件列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<PartsDetailsResult> getPartsList(GeneralEnter enter) {

        List<PartsDetailsResult> resultList = new ArrayList<>();
        List<SiteParts> list = sitePartsService.list(new QueryWrapper<SiteParts>().eq(SiteParts.COL_DR, 0));

        if (list.size() > 0) {
            list.forEach(pc -> {
                PartsDetailsResult result = new PartsDetailsResult();
                BeanUtils.copyProperties(pc, result);
                resultList.add(result);
            });
        }
        return resultList;
    }
}
