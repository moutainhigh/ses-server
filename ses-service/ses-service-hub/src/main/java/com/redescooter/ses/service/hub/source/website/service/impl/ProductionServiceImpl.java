package com.redescooter.ses.service.hub.source.website.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.website.ProductionService;
import com.redescooter.ses.api.hub.vo.website.SyncProductionDataEnter;
import com.redescooter.ses.service.hub.constant.SequenceName;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.operation.service.impl.SalePriceServiceImpl;
import com.redescooter.ses.service.hub.source.website.dm.SiteColour;
import com.redescooter.ses.service.hub.source.website.dm.SiteProduct;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductClass;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductColour;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductModel;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductPrice;
import com.redescooter.ses.service.hub.source.website.service.base.SiteColourService;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductClassService;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductColourService;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductModelService;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductPriceService;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductService;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.common.util.CollectionUtils;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/03/16 10:42
 */
@DubboService
@Slf4j
public class ProductionServiceImpl implements ProductionService {

    @Autowired
    private SiteColourService siteColourService;

    @Autowired
    private SiteProductService siteProductService;

    @Autowired
    private SiteProductClassService siteProductClassService;

    @Autowired
    private SiteProductColourService siteProductColourService;

    @Autowired
    private SiteProductModelService siteProductModelService;

    @Autowired
    private SiteProductPriceService siteProductPriceService;

    @Autowired
    private SalePriceServiceImpl salePriceService;

    @DubboReference
    private IdAppService idAppService;

    @Override
    @DS("website")
    public void syncByProductionCode(String productionName, Integer saleStatus) {
        // ???????????????????????? ?????????????????????????????????????????????
        QueryWrapper<SiteProductModel> qw = new QueryWrapper<>();
        qw.eq(SiteProductModel.COL_PRODUCT_MODEL_NAME, productionName);
        qw.last("limit 1");
        SiteProductModel product = siteProductModelService.getOne(qw);
        if (product != null) {
            // ??????????????????????????????????????? ??????????????????????????????
            // ??????site_product_model
            //siteProductModelService.removeById(product.getId());
            product.setStatus(-1);
            siteProductModelService.updateById(product);

            // ??????site_product
            QueryWrapper<SiteProduct> productQueryWrapper = new QueryWrapper<>();
            productQueryWrapper.eq(SiteProduct.COL_PRODUCT_MODEL_ID, product.getId());
            List<SiteProduct> productList = siteProductService.list(productQueryWrapper);
            if (CollectionUtils.isNotEmpty(productList)) {
                for (SiteProduct item : productList) {
                    item.setStatus(-1);
                    siteProductService.updateById(item);
                }
                //siteProductService.removeByIds(productList.stream().map(SiteProduct::getId).collect(Collectors.toList()));
            }

            // ??????site_product_colour
            QueryWrapper<SiteProductColour> colourQueryWrapper = new QueryWrapper<>();
            colourQueryWrapper.eq(SiteProductColour.COL_PRODUCT_ID, product.getId());
            List<SiteProductColour> colourList = siteProductColourService.list(colourQueryWrapper);
            if (CollectionUtils.isNotEmpty(colourList)) {
                siteProductColourService.removeByIds(colourList.stream().map(SiteProductColour::getId).collect(Collectors.toList()));
            }

            // ??????site_product_price
            LambdaQueryWrapper<SiteProductPrice> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SiteProductPrice::getProductModelId, product.getId());
            List<SiteProductPrice> list = siteProductPriceService.list(wrapper);
            if (CollectionUtils.isNotEmpty(list)) {
                for (SiteProductPrice item : list) {
                    item.setStatus(-1);
                    siteProductPriceService.updateById(item);
                }
                /*List<Long> ids = list.stream().map(o -> o.getId()).collect(Collectors.toList());
                siteProductPriceService.removeByIds(ids);*/
            }

            //salePriceService.deleteSalePrice(product.getProductModelName());

//            product.setStatus(saleStatus == 1 ? 1 : -1);
//            product.setUpdatedTime(new Date());
//            product.setUpdatedBy(0L);
//            siteProductModelService.saveOrUpdate(product);
//
//            // ??????site_product?????????
//            Long productModelId = product.getId();
//            LambdaQueryWrapper<SiteProduct> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(SiteProduct::getDr, Constant.DR_FALSE);
//            wrapper.eq(SiteProduct::getProductModelId, productModelId);
//            wrapper.last("limit 1");
//            SiteProduct siteProduct = siteProductService.getOne(wrapper);
//            if (null != siteProduct) {
//                siteProduct.setStatus(saleStatus == 1 ? 1 : 2);
//                siteProduct.setUpdatedTime(new Date());
//                siteProduct.setUpdatedBy(0L);
//                siteProductService.saveOrUpdate(siteProduct);
//            }
//        } else {
//            flag = false;
        }
//        return flag;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @DS("website")
    public void syncProductionData(SyncProductionDataEnter syncProductionDataEnter) {
        // ????????? site_product_class ??????
        // ?????????????????????code ????????????????????????
        SiteProductClass productClass;
        QueryWrapper<SiteProductClass> productClassQw = new QueryWrapper<>();
        productClassQw.eq(SiteProductClass.COL_PRODUCT_CLASS_CODE, syncProductionDataEnter.getProductClassCode());
        productClassQw.last("limit 1");
        productClass = siteProductClassService.getOne(productClassQw);
        if (productClass == null) {
            // ???????????????????????????
            productClass = new SiteProductClass();
            productClass.setId(idAppService.getId(SequenceName.SITE_PRODUCT_CLASS));
            productClass.setDr(0);
            productClass.setStatus(1);
            productClass.setProductClassName(syncProductionDataEnter.getProductClassName());
            productClass.setProductClassCode(syncProductionDataEnter.getProductClassCode());
            productClass.setCreatedBy(0L);
            productClass.setCreatedTime(new Date());
            productClass.setUpdatedBy(0L);
            productClass.setUpdatedTime(new Date());
            siteProductClassService.saveOrUpdate(productClass);
        }

        // ???????????? site_product_model ??????
        // ????????????code????????????????????????
        SiteProductModel productModel;
        QueryWrapper<SiteProductModel> productModelQw = new QueryWrapper<>();
        productModelQw.eq(SiteProductModel.COL_PRODUCT_MODEL_NAME, syncProductionDataEnter.getProductModelName());
        productModelQw.last("limit 1");
        productModel = siteProductModelService.getOne(productModelQw);
        if (productModel == null) {
            productModel = new SiteProductModel();
            productModel.setId(idAppService.getId(SequenceName.SITE_PRODUCT_MODEL));
            productModel.setDr(0);
            productModel.setProductClassId(productClass.getId());
            productModel.setProductModelName(syncProductionDataEnter.getProductModelName());
            productModel.setProductModelCode(syncProductionDataEnter.getProductModelCode());
            productModel.setStatus(1);
            productModel.setCreatedBy(0L);
            productModel.setCreatedTime(new Date());
            productModel.setUpdatedBy(0L);
            productModel.setUpdatedTime(new Date());
            siteProductModelService.saveOrUpdate(productModel);
        } else {
            log.info("productModel?????????");

            LambdaQueryWrapper<SiteProductPrice> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SiteProductPrice::getProductModelId, productModel.getId());
            wrapper.eq(SiteProductPrice::getStatus, -1);
            List<SiteProductPrice> list = siteProductPriceService.list(wrapper);
            if (CollectionUtils.isNotEmpty(list)) {
                for (SiteProductPrice item : list) {
                    item.setStatus(1);
                    siteProductPriceService.updateById(item);
                }
            }

            productModel.setStatus(1);
            siteProductModelService.updateById(productModel);
        }

        // ???????????? site_product ??????
        SiteProduct product;
        QueryWrapper<SiteProduct> qw = new QueryWrapper<>();
        qw.eq(SiteProduct.COL_FR_NAME, syncProductionDataEnter.getFrName());
        qw.last("limit 1");
        product = siteProductService.getOne(qw);
        if (product == null) {
            product = new SiteProduct();
            BeanUtils.copyProperties(syncProductionDataEnter, product);
            product.setId(idAppService.getId(SequenceName.SITE_PRODUCT));
            product.setDr(0);
            product.setCreatedBy(0L);
            product.setCreatedTime(new Date());
            product.setUpdatedBy(0L);
            product.setUpdatedTime(new Date());
            product.setProductModelId(productModel.getId());
            siteProductService.saveOrUpdate(product);
        } else {
            log.info("product?????????");
            product.setStatus(1);
            siteProductService.updateById(product);
        }

        // ????????? site_colour ??????
        // ??????????????????????????????????????????????????????
        SiteColour colour;
        QueryWrapper<SiteColour> colourQw = new QueryWrapper<>();
        colourQw.eq(SiteColour.COL_COLOUR_CODE, syncProductionDataEnter.getColourCode());
        colourQw.last("limit 1");
        colour = siteColourService.getOne(colourQw);
        if (colour == null) {
            // ?????????????????????????????????
            colour = new SiteColour();
            colour.setId(idAppService.getId(SequenceName.SITE_COLOUR));
            colour.setDr(0);
            colour.setColourRange("1");
            colour.setColourName(syncProductionDataEnter.getColourName());
            colour.setColourCode(syncProductionDataEnter.getColourCode());
            colour.setStatus(1);
            colour.setCreatedBy(0L);
            colour.setCreatedTime(new Date());
            colour.setUpdatedBy(0L);
            colour.setUpdatedTime(new Date());
            siteColourService.saveOrUpdate(colour);
        }

        // ???????????? site_product_colour ??????
        // ???????????????ID???model???ID??????????????????????????????
        SiteProductColour productColour;
        QueryWrapper<SiteProductColour> productColourQw = new QueryWrapper<>();
        productColourQw.eq(SiteProductColour.COL_COLOUR_ID, colour.getId());
        productColourQw.eq(SiteProductColour.COL_PRODUCT_ID, productModel.getId());
        productColourQw.last("limit 1");
        productColour = siteProductColourService.getOne(productColourQw);
        if (productColour == null) {
            productColour = new SiteProductColour();
            productColour.setId(idAppService.getId(SequenceName.SITE_PRODUCT_COLOUR));
            productColour.setColourId(colour.getId());
            productColour.setProductId(productModel.getId());
            productColour.setPicture(syncProductionDataEnter.getPicture());
            siteProductColourService.saveOrUpdate(productColour);
        }
        //siteProductService.saveOrUpdate(product);


//        // ????????? site_product ??????
//        SiteProduct product;
//        QueryWrapper<SiteProduct> qw = new QueryWrapper<>();
//        qw.eq(SiteProduct.COL_FR_NAME,syncProductionDataEnter.getFrName());
//        qw.last("limit 1");
//        product = siteProductService.getOne(qw);
//        if (product == null) {
//            BeanUtils.copyProperties(syncProductionDataEnter,product);
//            product.setId(idAppService.getId(SequenceName.SITE_PRODUCT));
//            product.setDr(0);
//            product.setCreatedBy(0L);
//            product.setCreatedTime(new Date());
//            product.setUpdatedBy(0L);
//            product.setUpdatedTime(new Date());
//        }
//
//        // ????????? site_product_class ??????
//        // ?????????????????????code ????????????????????????
//        SiteProductClass productClass;
//        QueryWrapper<SiteProductClass> productClassQw = new QueryWrapper<>();
//        productClassQw.eq(SiteProductClass.COL_PRODUCT_CLASS_CODE,syncProductionDataEnter.getProductClassCode());
//        productClassQw.last("limit 1");
//        productClass = siteProductClassService.getOne(productClassQw);
//        if (productClass == null) {
//            // ???????????????????????????
//            productClass = new SiteProductClass();
//            productClass.setId(idAppService.getId(SequenceName.SITE_PRODUCT_CLASS));
//            productClass.setDr(0);
//            productClass.setStatus(1);
//            productClass.setProductClassName(syncProductionDataEnter.getProductClassName());
//            productClass.setProductClassCode(syncProductionDataEnter.getProductClassCode());
//            productClass.setCreatedBy(0L);
//            productClass.setCreatedTime(new Date());
//            productClass.setUpdatedBy(0L);
//            productClass.setUpdatedTime(new Date());
//            siteProductClassService.saveOrUpdate(productClass);
//        }
//
//        // ???????????? site_product_model ??????
//        // ????????????code????????????????????????
//        SiteProductModel productModel;
//        QueryWrapper<SiteProductModel> productModelQw = new QueryWrapper<>();
//        productModelQw.eq(SiteProductModel.COL_PRODUCT_MODEL_NAME,syncProductionDataEnter.getProductModelName());
//        productModelQw.last("limit 1");
//        productModel = siteProductModelService.getOne(productModelQw);
//        if (productModel == null) {
//            productModel = new SiteProductModel();
//            productModel.setId(idAppService.getId(SequenceName.SITE_PRODUCT_MODEL));
//            productModel.setDr(0);
//            productModel.setProductClassId(productClass.getId());
//            productModel.setProductModelName(syncProductionDataEnter.getProductModelName());
//            productModel.setProductModelCode(syncProductionDataEnter.getProductModelCode());
//            productModel.setStatus(1);
//            productModel.setCreatedBy(0L);
//            productModel.setCreatedTime(new Date());
//            productModel.setUpdatedBy(0L);
//            productModel.setUpdatedTime(new Date());
//            siteProductModelService.saveOrUpdate(productModel);
//        }
//        // model???ID?????????????????????
//        product.setProductModelId(productModel.getId());
//
//        // ???????????? site_colour ??????
//        // ??????????????????????????????????????????????????????
//        SiteColour colour;
//        QueryWrapper<SiteColour> colourQw = new QueryWrapper<>();
//        colourQw.eq(SiteColour.COL_COLOUR_CODE,syncProductionDataEnter.getColourCode());
//        colourQw.last("limit 1");
//        colour = siteColourService.getOne(colourQw);
//        if (colour == null) {
//            // ?????????????????????????????????
//            colour = new SiteColour();
//            colour.setId(idAppService.getId(SequenceName.SITE_COLOUR));
//            colour.setDr(0);
//            colour.setColourRange("1");
//            colour.setStatus(1);
//            colour.setColourName(syncProductionDataEnter.getColourName());
//            colour.setColourCode(syncProductionDataEnter.getColourCode());
//            colour.setStatus(1);
//            colour.setCreatedBy(0L);
//            colour.setCreatedTime(new Date());
//            colour.setUpdatedBy(0L);
//            colour.setUpdatedTime(new Date());
//            siteColourService.saveOrUpdate(colour);
//        }
//
//        // ???????????? site_product_colour ??????
//        // ???????????????ID???model???ID??????????????????????????????
//        SiteProductColour productColour;
//        QueryWrapper<SiteProductColour> productColourQw = new QueryWrapper<>();
//        productColourQw.eq(SiteProductColour.COL_COLOUR_ID,colour.getId());
//        productColourQw.eq(SiteProductColour.COL_PRODUCT_ID, productModel.getId());
//        productColourQw.last("limit 1");
//        productColour = siteProductColourService.getOne(productColourQw);
//        if (productColour == null) {
//            productColour = new SiteProductColour();
//            productColour.setId(idAppService.getId(SequenceName.SITE_PRODUCT_COLOUR));
//            productColour.setColourId(colour.getId());
//            productColour.setProductId(productModel.getId());
//            siteProductColourService.saveOrUpdate(productColour);
//        }
//        siteProductService.saveOrUpdate(product);
    }


    @Override
    @DS("website")
    public void syncDeleteData(String productionName) {
        QueryWrapper<SiteProductModel> qw = new QueryWrapper<>();
        qw.eq(SiteProductModel.COL_PRODUCT_MODEL_NAME, productionName);
        qw.last("limit 1");
        SiteProductModel product = siteProductModelService.getOne(qw);
        if (product != null) {
            // ??????site_product_model
            siteProductModelService.removeById(product.getId());

            // ??????site_product
            QueryWrapper<SiteProduct> productQueryWrapper = new QueryWrapper<>();
            productQueryWrapper.eq(SiteProduct.COL_PRODUCT_MODEL_ID, product.getId());
            List<SiteProduct> productList = siteProductService.list(productQueryWrapper);
            if (CollectionUtils.isNotEmpty(productList)) {
                siteProductService.removeByIds(productList.stream().map(SiteProduct::getId).collect(Collectors.toList()));
            }

            // ??????site_product_colour
            QueryWrapper<SiteProductColour> colourQueryWrapper = new QueryWrapper<>();
            colourQueryWrapper.eq(SiteProductColour.COL_PRODUCT_ID, product.getId());
            List<SiteProductColour> colourList = siteProductColourService.list(colourQueryWrapper);
            if (CollectionUtils.isNotEmpty(colourList)) {
                siteProductColourService.removeByIds(colourList.stream().map(SiteProductColour::getId).collect(Collectors.toList()));
            }

            // ??????site_product_price
            LambdaQueryWrapper<SiteProductPrice> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SiteProductPrice::getProductModelId, product.getId());
            List<SiteProductPrice> list = siteProductPriceService.list(wrapper);
            if (CollectionUtils.isNotEmpty(list)) {
                List<Long> ids = list.stream().map(o -> o.getId()).collect(Collectors.toList());
                siteProductPriceService.removeByIds(ids);
            }

            salePriceService.deleteSalePrice(product.getProductModelName());
        }
    }

    /**
     * ??????site_product_model????????????????????????,??????,??????
     */
    @Override
    @DS("website")
    public Map<String, String> getProductInfoByModelId(Long id) {
        Map<String, String> result = Maps.newHashMapWithExpectedSize(3);

        // ????????????
        LambdaQueryWrapper<SiteProductColour> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SiteProductColour::getProductId, id);
        wrapper.last("limit 1");
        SiteProductColour productColour = siteProductColourService.getOne(wrapper);
        if (null == productColour) {
            throw new SeSHubException(ExceptionCodeEnums.PRODUCTION_COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCTION_COLOR_NOT_EXIST.getMessage());
        }
        Long colourId = productColour.getColourId();
        SiteColour colour = siteColourService.getById(colourId);
        if (null == colour) {
            throw new SeSHubException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
        }
        String colorCode = colour.getColourCode();

        // ?????????????????????
        SiteProductModel model = siteProductModelService.getById(id);
        if (null == model) {
            throw new SeSHubException(ExceptionCodeEnums.PRODUCTION_MODEL_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCTION_MODEL_NOT_EXIST.getMessage());
        }
        String code = model.getProductModelCode();
        String name = model.getProductModelName();
        result.put("colorCode", colorCode);
        result.put("code", code);
        result.put("name", name);
        return result;
    }

}
