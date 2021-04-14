package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteProductParts;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.website.exception.SesWebsiteException;
import com.redescooter.ses.web.website.service.ProductPartsService;
import com.redescooter.ses.web.website.service.base.SiteProductPartsService;
import com.redescooter.ses.web.website.vo.product.AddProductPartsEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult addProductParts(AddProductPartsEnter enter) {
        log.info("impl>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        SiteProductParts addProductPartsVO = new SiteProductParts();
        List<String> list = Arrays.asList(enter.getPartsId().split(","));

        list.stream().forEach(item->{
           QueryWrapper<SiteProductParts> wrapper = new QueryWrapper<SiteProductParts>().eq("parts_id",item).eq("product_id",enter.getProductId());
            List<SiteProductParts> list1 = siteProductPartsService.list(wrapper);
            if(list1.size()>0){
                throw new SesWebsiteException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            addProductPartsVO.setId(idAppService.getId(SequenceName.SITE_PRODUCT_PARTS));
            addProductPartsVO.setDr(Constant.DR_FALSE);
            addProductPartsVO.setStatus(CommonStatusEnums.NORMAL.getValue());
            addProductPartsVO.setPartsId(Long.parseLong(item));
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
        });
        return new GeneralResult(enter.getRequestId());
    }

}
