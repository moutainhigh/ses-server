package com.redescooter.ses.service.hub.source.website.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.hub.service.website.ProductionService;
import com.redescooter.ses.api.hub.vo.website.SyncProductionDataEnter;
import com.redescooter.ses.service.hub.constant.SequenceName;
import com.redescooter.ses.service.hub.source.website.dm.SiteColour;
import com.redescooter.ses.service.hub.source.website.dm.SiteProduct;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductClass;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductColour;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductModel;
import com.redescooter.ses.service.hub.source.website.service.base.SiteColourService;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductClassService;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductColourService;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductModelService;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductService;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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

    @DubboReference
    private IdAppService idAppService;

    @Override
    @DS("website")
    public boolean syncByProductionCode(String productionCode,Integer saleStatus) {
        // 给个默认值 先默认同步过了
        boolean flag = true;
        QueryWrapper<SiteProductModel> qw = new QueryWrapper<>();
        qw.eq(SiteProductModel.COL_PRODUCT_MODEL_CODE,productionCode);
        qw.last("limit 1");
        SiteProductModel product = siteProductModelService.getOne(qw);
        if (product != null) {
            // 到这里说明同步过数据了  ，这次只要修改一下数据的状态就好了
            product.setStatus(saleStatus==1?1:2);
            product.setUpdatedTime(new Date());
            product.setUpdatedBy(0L);
            siteProductModelService.saveOrUpdate(product);
        }else {
            flag = false;
        }
        return flag;
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @DS("website")
    public void syncProductionData(SyncProductionDataEnter syncProductionDataEnter) {
        log.info("准备同步官网的数据了。。。。。。。。。。。。。。。。");
        // 先创建 site_product_class 信息
        // 先要通过大类的code 判断有没有同步过
        SiteProductClass productClass;
        QueryWrapper<SiteProductClass> productClassQw = new QueryWrapper<>();
        productClassQw.eq(SiteProductClass.COL_PRODUCT_CLASS_CODE,syncProductionDataEnter.getProductClassCode());
        productClassQw.last("limit 1");
        productClass = siteProductClassService.getOne(productClassQw);
        if (productClass == null) {
            // 说明之前没有同步过
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

        // 然后创建 site_product_model 信息
        // 先要根据code判断有没有同步过
        SiteProductModel productModel;
        QueryWrapper<SiteProductModel> productModelQw = new QueryWrapper<>();
        productModelQw.eq(SiteProductModel.COL_PRODUCT_MODEL_NAME,syncProductionDataEnter.getProductModelName());
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
        }

        // 接着创建 site_product 信息
        SiteProduct product;
        QueryWrapper<SiteProduct> qw = new QueryWrapper<>();
        qw.eq(SiteProduct.COL_FR_NAME,syncProductionDataEnter.getFrName());
        qw.last("limit 1");
        product = siteProductService.getOne(qw);
        if (product == null) {
            BeanUtils.copyProperties(syncProductionDataEnter,product);
            product.setId(idAppService.getId(SequenceName.SITE_PRODUCT));
            product.setDr(0);
            product.setCreatedBy(0L);
            product.setCreatedTime(new Date());
            product.setUpdatedBy(0L);
            product.setUpdatedTime(new Date());
            product.setProductModelId(productModel.getId());
        }

        // 再创建 site_colour 信息
        // 先要根据色值判断这个颜色有没有创建过
        SiteColour colour;
        QueryWrapper<SiteColour> colourQw = new QueryWrapper<>();
        colourQw.eq(SiteColour.COL_COLOUR_CODE,syncProductionDataEnter.getColourCode());
        colourQw.last("limit 1");
        colour = siteColourService.getOne(colourQw);
        if (colour == null) {
            // 说明颜色之前没有同步过
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

        // 最后创建 site_product_colour 信息
        // 先根据颜色ID和model的ID判断是否有这样的数据
        SiteProductColour productColour;
        QueryWrapper<SiteProductColour> productColourQw = new QueryWrapper<>();
        productColourQw.eq(SiteProductColour.COL_COLOUR_ID,colour.getId());
        productColourQw.eq(SiteProductColour.COL_PRODUCT_ID, productModel.getId());
        productColourQw.last("limit 1");
        productColour = siteProductColourService.getOne(productColourQw);
        if (productColour == null) {
            productColour = new SiteProductColour();
            productColour.setId(idAppService.getId(SequenceName.SITE_PRODUCT_COLOUR));
            productColour.setColourId(colour.getId());
            productColour.setProductId(productModel.getId());
            siteProductColourService.saveOrUpdate(productColour);
        }
        siteProductService.saveOrUpdate(product);





//        // 先创建 site_product 信息
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
//        // 再创建 site_product_class 信息
//        // 先要通过大类的code 判断有没有同步过
//        SiteProductClass productClass;
//        QueryWrapper<SiteProductClass> productClassQw = new QueryWrapper<>();
//        productClassQw.eq(SiteProductClass.COL_PRODUCT_CLASS_CODE,syncProductionDataEnter.getProductClassCode());
//        productClassQw.last("limit 1");
//        productClass = siteProductClassService.getOne(productClassQw);
//        if (productClass == null) {
//            // 说明之前没有同步过
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
//        // 然后创建 site_product_model 信息
//        // 先要根据code判断有没有同步过
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
//        // model的ID要放到产品表中
//        product.setProductModelId(productModel.getId());
//
//        // 接着创建 site_colour 信息
//        // 先要根据色值判断这个颜色有没有创建过
//        SiteColour colour;
//        QueryWrapper<SiteColour> colourQw = new QueryWrapper<>();
//        colourQw.eq(SiteColour.COL_COLOUR_CODE,syncProductionDataEnter.getColourCode());
//        colourQw.last("limit 1");
//        colour = siteColourService.getOne(colourQw);
//        if (colour == null) {
//            // 说明颜色之前没有同步过
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
//        // 接着创建 site_product_colour 信息
//        // 先根据颜色ID和model的ID判断是否有这样的数据
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
        qw.eq(SiteProductModel.COL_PRODUCT_MODEL_NAME,productionName);
        qw.last("limit 1");
        SiteProductModel product = siteProductModelService.getOne(qw);
        if (product != null) {
            siteProductModelService.removeById(product.getId());
        }
    }
}
