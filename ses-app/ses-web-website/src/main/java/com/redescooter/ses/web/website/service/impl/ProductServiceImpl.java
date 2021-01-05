package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.code.MainCode;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteProduct;
import com.redescooter.ses.web.website.enums.ProductStatusEnums;
import com.redescooter.ses.web.website.enums.ProductTypeEnums;
import com.redescooter.ses.web.website.service.ProductService;
import com.redescooter.ses.web.website.service.base.SiteProductService;
import com.redescooter.ses.web.website.vo.product.AddProductEnter;
import com.redescooter.ses.web.website.vo.product.ModityProductEnter;
import com.redescooter.ses.web.website.vo.product.ProductDetailsResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 1:21 上午
 * @Description 产品服务实现类
 **/

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired(required = true)
    private SiteProductService siteProductService;

    @Reference
    private IdAppService idAppService;

    /**
     * 创建产品
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public Boolean addProduct(AddProductEnter enter) {
        SiteProduct addProductVO = new SiteProduct();
        addProductVO.setId(idAppService.getId(SequenceName.SITE_PRODUCT));
        addProductVO.setDr(Constant.DR_FALSE);
        addProductVO.setStatus(String.valueOf(ProductStatusEnums.UP.getValue()));
        addProductVO.setProductType(ProductTypeEnums.VEHICLE.getValue());
        addProductVO.setProductCode(new StringBuffer().append("PT_").append(MainCode.generateByShuffle()).toString());
        addProductVO.setCnName(enter.getCnName());
        addProductVO.setFrName(enter.getFrName());
        addProductVO.setEnName(enter.getEnName());
        addProductVO.setProductModelId(enter.getProductModelId());
        addProductVO.setMinBatteryNum(enter.getMinBatteryNum());
        addProductVO.setAfterSalesFlag(false);
        addProductVO.setAddedServicesFlag(false);

        if (StringUtils.isNotBlank(enter.getMaterParameter())) {
            addProductVO.setMaterParameter(enter.getMaterParameter());
        }
        if (StringUtils.isNotBlank(enter.getOtherParameter())) {
            addProductVO.setOtherParameter(enter.getOtherParameter());
        }

        addProductVO.setSpeed(enter.getSpeed());
        addProductVO.setPower(enter.getPower());
        addProductVO.setChargeCycle(enter.getChargeCycle());
        addProductVO.setSynchronizeFlag(false);
        addProductVO.setRevision(0);
        addProductVO.setCreatedBy(enter.getUserId());
        addProductVO.setUpdatedBy(enter.getUserId());

        return siteProductService.save(addProductVO);
    }

    /**
     * 编辑产品
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public Boolean modityProduct(ModityProductEnter enter) {
        SiteProduct moditySiteProductVO = new SiteProduct();
        BeanUtils.copyProperties(enter, moditySiteProductVO);
        return siteProductService.update(moditySiteProductVO, new UpdateWrapper<SiteProduct>().eq(SiteProduct.COL_ID, enter.getId()));
    }

    /**
     * 移除产品
     *
     * @param enter
     * @return
     */
    @Override
    public Boolean removeProduct(IdEnter enter) {
        return siteProductService.removeById(enter.getId());
    }

    /**
     * 获取产品详情
     *
     * @param enter
     */
    @Override
    public ProductDetailsResult getProductDetails(IdEnter enter) {
        SiteProduct product = siteProductService.getById(enter.getId());
        ProductDetailsResult result = new ProductDetailsResult();

        if (product != null) {
            BeanUtils.copyProperties(product, result);
            result.setRequestId(enter.getRequestId());
        }

        return result;
    }

    /**
     * 获取产品列表
     *
     * @param enter
     */
    @Override
    public List<ProductDetailsResult> getProductList(GeneralEnter enter) {

        List<ProductDetailsResult> resultList = new ArrayList<>();
        List<SiteProduct> list = siteProductService.list(new QueryWrapper<SiteProduct>().eq(SiteProduct.COL_DR, 0));

        if (list.size() > 0) {
            list.forEach(pc -> {
                ProductDetailsResult result = new ProductDetailsResult();
                BeanUtils.copyProperties(pc, result);
                resultList.add(result);
            });
        }
        return resultList;
    }
}
