package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.code.MainCode;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteParts;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.service.PartsService;
import com.redescooter.ses.web.website.service.base.SitePartsService;
import com.redescooter.ses.web.website.vo.product.AddPartsEnter;
import com.redescooter.ses.web.website.vo.product.ModityPartsEnter;
import com.redescooter.ses.web.website.vo.product.PartsDetailsResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Reference
    private IdAppService idAppService;

    /**
     * 创建配件
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public Boolean addParts(AddPartsEnter enter) {
        SiteParts addSitePartsVO = new SiteParts();
        addSitePartsVO.setId(idAppService.getId(SequenceName.SITE_PARTS));
        addSitePartsVO.setDr(Constant.DR_FALSE);
        addSitePartsVO.setStatus(String.valueOf(CommonStatusEnums.NORMAL.getValue()));
        addSitePartsVO.setPartsType(enter.getPartsType());
        addSitePartsVO.setPartsNumber(enter.getPartsNumber());
        addSitePartsVO.setCnName(enter.getCnName());
        addSitePartsVO.setFrName(enter.getFrName());
        addSitePartsVO.setEnName(enter.getEnName());
        addSitePartsVO.setPrice(enter.getPrice());
        addSitePartsVO.setCurrencyUnit(enter.getCurrencyUnit());
        addSitePartsVO.setSources(enter.getSources());

        if (StringUtils.isNotBlank(enter.getRemark())) {
            addSitePartsVO.setRemark(enter.getRemark());
        }
        addSitePartsVO.setRevision(0);
        addSitePartsVO.setSynchronizeFlag(false);
        addSitePartsVO.setCreatedBy(enter.getUserId());
        addSitePartsVO.setCreatedTime(new Date());
        addSitePartsVO.setUpdatedBy(enter.getUserId());

        return sitePartsService.save(addSitePartsVO);
    }

    /**
     * 编辑配件
     *
     * @param enter
     * @return
     */
    @Override
    public Boolean modityParts(ModityPartsEnter enter) {
        return null;
    }

    /**
     * 移除配件
     *
     * @param enter
     * @return
     */
    @Override
    public Boolean removeParts(IdEnter enter) {
        return null;
    }

    /**
     * 获取配件详情
     *
     * @param enter
     */
    @Override
    public PartsDetailsResult getPartsDetails(IdEnter enter) {
        return null;
    }

    /**
     * 获取配件列表
     *
     * @param enter
     */
    @Override
    public List<PartsDetailsResult> getPartsList(GeneralEnter enter) {
        return null;
    }
}
