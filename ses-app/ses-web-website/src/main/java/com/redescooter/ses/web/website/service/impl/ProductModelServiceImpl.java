package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.code.MainCode;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteProductModel;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.service.ProductModelService;
import com.redescooter.ses.web.website.service.base.SiteProductModelService;
import com.redescooter.ses.web.website.vo.product.AddProductModelEnter;
import com.redescooter.ses.web.website.vo.product.ModityProductModelEnter;
import com.redescooter.ses.web.website.vo.product.ProductModelDetailsResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private SiteProductModelService siteProductModelService;

    /**
     * 创建产品车型
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult addProductModel(AddProductModelEnter enter) {

        SiteProductModel addProductModelVO = new SiteProductModel();

        addProductModelVO.setId(idAppService.getId(SequenceName.SITE_PRODUCT_MODEL));
        addProductModelVO.setDr(Constant.DR_FALSE);
        addProductModelVO.setStatus(CommonStatusEnums.NORMAL.getValue());
        addProductModelVO.setProductClassId(enter.getProductClassId());
        addProductModelVO.setProductModelName(enter.getProductModelName());
        addProductModelVO.setProductModelCode(new StringBuffer().append("ML_").append(MainCode.generateByShuffle()).toString());

        if (StringUtils.isNotBlank(enter.getRemark())) {
            addProductModelVO.setRemark(enter.getRemark());
        }
        addProductModelVO.setRevision(0);
        addProductModelVO.setSynchronizeFlag(false);
        addProductModelVO.setCreatedBy(enter.getUserId());
        addProductModelVO.setCreatedTime(new Date());
        addProductModelVO.setUpdatedBy(enter.getUserId());

        siteProductModelService.save(addProductModelVO);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 编辑产品车型
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult modityProductModel(ModityProductModelEnter enter) {

        SiteProductModel modityProductModelVO = new SiteProductModel();
        BeanUtils.copyProperties(enter, modityProductModelVO);

        siteProductModelService.updateById(modityProductModelVO);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 移除产品车型
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult removeProductModel(IdEnter enter) {
        siteProductModelService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
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

        if (productModel != null) {
            BeanUtils.copyProperties(productModel, result);
            result.setRequestId(enter.getRequestId());
        }

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
