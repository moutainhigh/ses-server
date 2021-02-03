package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteProductParts;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.service.ProductPartsService;
import com.redescooter.ses.web.website.service.base.SiteProductPartsService;
import com.redescooter.ses.web.website.vo.product.AddProductPartsEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author jerry
 * @Date 2021/1/6 4:33 上午
 * @Description 产品配件服务
 **/
@Slf4j
@Service
public class ProductPartsServiceImpl implements ProductPartsService {

    @Autowired
    private SiteProductPartsService siteProductPartsService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 创建产品配件
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult addProductParts(AddProductPartsEnter enter) {

        SiteProductParts addProductPartsVO = new SiteProductParts();
        addProductPartsVO.setId(idAppService.getId(SequenceName.SITE_PRODUCT_PARTS));
        addProductPartsVO.setDr(Constant.DR_FALSE);
        addProductPartsVO.setStatus(CommonStatusEnums.NORMAL.getValue());
        addProductPartsVO.setPartsId(enter.getPartsId());
        addProductPartsVO.setProductId(enter.getProductId());
        addProductPartsVO.setQty(enter.getQty());
        addProductPartsVO.setParameter(enter.getParameter());
        addProductPartsVO.setSynchronizeFlag(false);
        addProductPartsVO.setRevision(0);
        addProductPartsVO.setCreatedBy(0L);
        addProductPartsVO.setCreatedTime(new Date());
        addProductPartsVO.setUpdatedBy(0L);
        addProductPartsVO.setUpdatedTime(new Date());
        siteProductPartsService.save(addProductPartsVO);
        return new GeneralResult(enter.getRequestId());
    }

}
