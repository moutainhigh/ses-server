package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.code.MainCode;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteProductClass;
import com.redescooter.ses.web.website.dm.SiteProductModel;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.service.ProductModelService;
import com.redescooter.ses.web.website.service.base.SiteProductModelService;
import com.redescooter.ses.web.website.vo.product.ProductClassDetailsResult;
import com.redescooter.ses.web.website.vo.product.ProductModelDetailsResult;
import com.redescooter.ses.web.website.vo.product.addProductModelEnter;
import com.redescooter.ses.web.website.vo.product.modityProductModelEnter;
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
 * @Date 2021/1/5 6:01 下午
 * @Description 产品车型服务实现类
 **/
@Slf4j
@Service
public class ProductModelServiceImpl implements ProductModelService {

    @Reference
    private IdAppService idAppService;

    @Autowired(required = true)
    private SiteProductModelService siteProductModelService;

    /**
     * 创建产品车型
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public Boolean addProductModel(addProductModelEnter enter) {

        SiteProductModel addProductModelVO = new SiteProductModel();

        addProductModelVO.setId(idAppService.getId(SequenceName.SITE_PRODUCT_MODEL));
        addProductModelVO.setDr(Constant.DR_FALSE);
        addProductModelVO.setStatus(String.valueOf(CommonStatusEnums.NORMAL.getValue()));
        addProductModelVO.setProductClassId(enter.getProductClassId());
        addProductModelVO.setProductModelName(enter.getProductModelName());
        addProductModelVO.setProductModelCode(new StringBuffer().append("ML_").append(MainCode.generateByShuffle()).toString());
        addProductModelVO.setCnName(enter.getCnName());
        addProductModelVO.setFrName(enter.getFrName());
        addProductModelVO.setEnName(enter.getEnName());

        if (StringUtils.isNotBlank(enter.getRemark())) {
            addProductModelVO.setRemark(enter.getRemark());
        }
        addProductModelVO.setRevision(0);
        addProductModelVO.setSynchronizeFlag(false);
        addProductModelVO.setCreatedBy(enter.getUserId());
        addProductModelVO.setCreatedTime(new Date());
        addProductModelVO.setUpdatedBy(enter.getUserId());

        return siteProductModelService.save(addProductModelVO);
    }

    /**
     * 编辑产品车型
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public Boolean modityProductModel(modityProductModelEnter enter) {

        SiteProductModel modityProductModelVO = new SiteProductModel();
        BeanUtils.copyProperties(enter, modityProductModelVO);

        return siteProductModelService.updateById(modityProductModelVO);
    }

    /**
     * 移除产品车型
     *
     * @param enter
     * @return
     */
    @Override
    public Boolean removeProductModel(IdEnter enter) {
        return siteProductModelService.removeById(enter.getId());
    }

    /**
     * 获取产品车型详情
     *
     * @param enter
     */
    @Override
    public ProductModelDetailsResult getProductModelDetails(IdEnter enter) {
        SiteProductModel productModel = siteProductModelService.getById(enter.getId());
        ProductModelDetailsResult result = new ProductModelDetailsResult();

        BeanUtils.copyProperties(productModel, result);
        result.setRequestId(enter.getRequestId());

        return result;
    }

    /**
     * 获取产品车型列表
     *
     * @param enter
     */
    @Override
    public List<ProductModelDetailsResult> getProductModelList(GeneralEnter enter) {

        List<ProductModelDetailsResult> resultList = new ArrayList<>();
        List<SiteProductModel> list = siteProductModelService.list(new QueryWrapper<SiteProductModel>().eq(SiteProductModel.COL_DR, 0));

        if (list.size() > 0) {
            list.forEach(pc -> {
                ProductModelDetailsResult result = new ProductModelDetailsResult();
                BeanUtils.copyProperties(pc, result);
                resultList.add(result);
            });
        }

        return resultList;
    }
}
