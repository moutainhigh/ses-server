package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
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
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private SiteProductService siteProductService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 创建产品
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult addProduct(AddProductEnter enter) {
        SiteProduct addProductVO = new SiteProduct();
        addProductVO.setId(idAppService.getId(SequenceName.SITE_PRODUCT));
        addProductVO.setDr(Constant.DR_FALSE);
        addProductVO.setStatus(ProductStatusEnums.UP.getValue());
        addProductVO.setProductType(ProductTypeEnums.VEHICLE.getValue());
        addProductVO.setProductCode(new StringBuffer().append("PT_").append(MainCode.generateByShuffle()).toString());
        addProductVO.setCnName(enter.getCnName());
        addProductVO.setFrName(enter.getFrName());
        addProductVO.setEnName(enter.getEnName());
        addProductVO.setPicture(enter.getPicture());
        addProductVO.setProductModelId(enter.getProductModelId());
        addProductVO.setMinBatteryNum(enter.getMinBatteryNum());

        addProductVO.setSpeed(enter.getSpeed());
        addProductVO.setPower(enter.getPower());
        addProductVO.setMileage(enter.getMileage());
        addProductVO.setChargeCycle(enter.getChargeCycle());

        if (StringUtils.isNotBlank(enter.getMaterParameter())) {
            addProductVO.setMaterParameter(enter.getMaterParameter());
        }
        if (StringUtils.isNotBlank(enter.getOtherParameter())) {
            addProductVO.setOtherParameter(enter.getOtherParameter());
        }

        addProductVO.setAfterSalesFlag(false);
        addProductVO.setAddedServicesFlag(false);
        addProductVO.setSynchronizeFlag(false);
        addProductVO.setRevision(0);
        addProductVO.setCreatedBy(enter.getUserId());
        addProductVO.setUpdatedBy(enter.getUserId());

        siteProductService.save(addProductVO);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 编辑产品
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult modityProduct(ModityProductEnter enter) {
        SiteProduct moditySiteProductVO = new SiteProduct();
        BeanUtils.copyProperties(enter, moditySiteProductVO);
        siteProductService.update(moditySiteProductVO, new UpdateWrapper<SiteProduct>().eq(SiteProduct.COL_ID, enter.getId()));
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 移除产品
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult removeProduct(IdEnter enter) {
        siteProductService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
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
