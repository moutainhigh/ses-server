package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.hub.service.website.ProductionService;
import com.redescooter.ses.api.hub.vo.website.SyncProductionDataEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.restproduction.SaleScooterMapper;
import com.redescooter.ses.web.ros.dm.OpeColor;
import com.redescooter.ses.web.ros.dm.OpeSaleScooter;
import com.redescooter.ses.web.ros.dm.OpeSaleScooterBatteryRelation;
import com.redescooter.ses.web.ros.dm.OpeSpecificatGroup;
import com.redescooter.ses.web.ros.dm.OpeSpecificatType;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeColorService;
import com.redescooter.ses.web.ros.service.base.OpeSaleCombinService;
import com.redescooter.ses.web.ros.service.base.OpeSalePartsService;
import com.redescooter.ses.web.ros.service.base.OpeSaleScooterBatteryRelationService;
import com.redescooter.ses.web.ros.service.base.OpeSaleScooterService;
import com.redescooter.ses.web.ros.service.base.OpeSpecificatGroupService;
import com.redescooter.ses.web.ros.service.base.OpeSpecificatTypeService;
import com.redescooter.ses.web.ros.service.restproduction.SaleScooterService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.restproduct.SaleProductionParaEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterListResult;
import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.specificat.ColorDataResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spark.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassNameSaleScooterServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/10/12 17:03
 * @Version V1.0
 **/
@Service
@Slf4j
public class SaleScooterServiceImpl implements SaleScooterService {

    @Autowired
    private SaleScooterMapper saleScooterMapper;

    @Autowired
    private OpeSaleScooterService opeSaleScooterService;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private OpeSpecificatTypeService specificatTypeService;

    @Autowired
    private JedisService jedisService;

    @Autowired
    private OpeSaleCombinService opeSaleCombinService;

    @Autowired
    private OpeSalePartsService opeSalePartsService;

    @DubboReference
    private ProductionService productionService;

    @Autowired
    private OpeColorService opeColorService;

    @Autowired
    private OpeSpecificatTypeService opeSpecificatTypeService;

    @Autowired
    private OpeSpecificatGroupService opeSpecificatGroupService;

    @Autowired
    private OpeSaleScooterBatteryRelationService opeSaleScooterBatteryRelationService;


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult saveSaleScooter(SaleScooterSaveOrUpdateEnter enter) {
        // ?????????
        enter = SesStringUtils.objStringTrim(enter);
        // ???????????????
        check(enter);
        OpeSaleScooter saleScooter = new OpeSaleScooter();
        BeanUtils.copyProperties(enter, saleScooter);
        saleScooter.setGroupId(findGroupId(enter.getSpecificatId()));
        saleScooter.setCreatedBy(enter.getUserId());
        saleScooter.setCreatedTime(new Date());
        saleScooter.setUpdatedBy(enter.getUserId());
        saleScooter.setUpdatedTime(new Date());
        saleScooter.setId(idAppService.getId(SequenceName.OPE_SALE_SCOOTER));
        opeSaleScooterService.saveOrUpdate(saleScooter);

        // ???????????????????????????
        insertSaleScooterBatteryRelation(saleScooter, enter);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult editSaleScooter(SaleScooterSaveOrUpdateEnter enter) {
        // ?????????
        enter = SesStringUtils.objStringTrim(enter);
        // ???????????????
        check(enter);
        OpeSaleScooter saleScooter = opeSaleScooterService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(saleScooter)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        saleScooter.setProductName(enter.getProductName());
        saleScooter.setProductCode(enter.getProductCode());
        saleScooter.setSpecificatId(enter.getSpecificatId());
        saleScooter.setGroupId(findGroupId(enter.getSpecificatId()));
        saleScooter.setColorId(enter.getColorId());
        saleScooter.setMinBatteryNum(enter.getMinBatteryNum());
        saleScooter.setOtherParam(enter.getOtherParam());
        saleScooter.setProductionParam(enter.getProductionParam());
        saleScooter.setPicture(enter.getPicture());
        opeSaleScooterService.saveOrUpdate(saleScooter);

        // ??????????????????????????????
        deleteSaleScooterBatteryRelation(saleScooter);

        // ??????????????????????????????
        insertSaleScooterBatteryRelation(saleScooter, enter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????ope_sale_scooter_battery_relation???,??????????????????
     */
    public void insertSaleScooterBatteryRelation(OpeSaleScooter saleScooter, SaleScooterSaveOrUpdateEnter enter) {
        for (int j = 0; j < 2; j++) {
            String language;
            String msg;
            if (j == 0) {
                language = "en";
                msg = "Batterie";
            } else {
                language = "fr";
                msg = "Batterie";
            }
            List<OpeSaleScooterBatteryRelation> list = Lists.newArrayList();
            Integer maxBatteryNum = 4;
            // ??????????????????  ??????:E50??????4??? E100??????3??? E125??????1???
            Integer minBatteryNum = saleScooter.getMinBatteryNum();
            for (int i = minBatteryNum; i <= maxBatteryNum; i++) {
                OpeSaleScooterBatteryRelation relation = new OpeSaleScooterBatteryRelation();
                relation.setId(idAppService.getId(SequenceName.OPE_SALE_SCOOTER_BATTERY_RELATION));
                relation.setDr(Constant.DR_FALSE);
                relation.setSaleScooterId(saleScooter.getId());
                relation.setContent(saleScooter.getProductName() + "+" + i + msg);
                relation.setLanguage(language);
                relation.setCreatedBy(enter.getUserId());
                relation.setCreatedTime(new Date());
                relation.setUpdatedBy(enter.getUserId());
                relation.setUpdatedTime(new Date());
                list.add(relation);
            }
            opeSaleScooterBatteryRelationService.saveBatch(list);
        }
    }

    /**
     * ??????ope_sale_scooter_battery_relation???
     */
    public void deleteSaleScooterBatteryRelation(OpeSaleScooter saleScooter) {
        LambdaQueryWrapper<OpeSaleScooterBatteryRelation> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeSaleScooterBatteryRelation::getDr, Constant.DR_FALSE);
        qw.eq(OpeSaleScooterBatteryRelation::getSaleScooterId, saleScooter.getId());
        List<OpeSaleScooterBatteryRelation> relationList = opeSaleScooterBatteryRelationService.list(qw);
        List<Long> ids = relationList.stream().map(o -> o.getId()).collect(Collectors.toList());
        opeSaleScooterBatteryRelationService.removeByIds(ids);
    }

    public void check(SaleScooterSaveOrUpdateEnter enter) {
        if (Strings.isNullOrEmpty(enter.getProductCode())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_CODE_NOT_NULL.getCode(), ExceptionCodeEnums.PRODUCT_CODE_NOT_NULL.getMessage());
        }
        QueryWrapper<OpeSaleScooter> qw = new QueryWrapper<>();
        qw.eq(OpeSaleScooter.COL_PRODUCT_CODE, enter.getProductCode());
        if (StringManaConstant.entityIsNotNull(enter.getId())) {
            qw.ne(OpeSaleScooter.COL_ID, enter.getId());
        }
        int count = opeSaleScooterService.count(qw);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(), ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
        }
    }


    // ?????????????????????id  ?????????????????????id
    public Long findGroupId(Long specificatId) {
        OpeSpecificatType specificatType = specificatTypeService.getById(specificatId);
        if (StringManaConstant.entityIsNull(specificatType)) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
        }
        return specificatType.getGroupId();
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteSaleScooter(IdEnter enter) {
        OpeSaleScooter saleScooter = opeSaleScooterService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(saleScooter)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        opeSaleScooterService.removeById(enter.getId());
        // ???????????????????????????  ????????????????????????????????????
        syncDeleteData(saleScooter.getProductName());

        // ???????????????????????????
        deleteSaleScooterBatteryRelation(saleScooter);
        return new GeneralResult(enter.getRequestId());
    }


    void syncDeleteData(String productionName){
        productionService.syncDeleteData(productionName);
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editSaleScooterStatus(IdEnter enter) {
        // ?????????????????????????????????????????????  ??????????????????Redis???  ???????????????????????????????????????????????????
        String key = JedisConstant.CHECK_SAFE_CODE_RESULT + enter.getRequestId();
        String checkResut = jedisService.get(key);
        if (!Boolean.parseBoolean(checkResut)) {
            throw new SesWebRosException(ExceptionCodeEnums.SAFE_CODE_FAILURE.getCode(), ExceptionCodeEnums.SAFE_CODE_FAILURE.getMessage());
        }
        jedisService.delKey(key);
        OpeSaleScooter saleScooter = opeSaleScooterService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(saleScooter)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        Integer saleStutas = saleScooter.getSaleStutas();
        saleScooter.setSaleStutas(saleStutas == 0 ? 1 : 0);
        opeSaleScooterService.updateById(saleScooter);

        // ???????????????????????????  ??????????????????????????????????????????????????????????????????????????????????????????
        dataSyncToWebsite(saleScooter);
        return new GeneralResult(enter.getRequestId());
    }

    // ??????????????????????????????
    void dataSyncToWebsite(OpeSaleScooter saleScooter){
        try {
            if (0 == saleScooter.getSaleStutas()){
                // ??????????????????
                productionService.syncByProductionCode(saleScooter.getProductName(), saleScooter.getSaleStutas());
            } else {
                // ??????????????????
                // ???????????????  ????????????????????????????????????  ????????????5??????
                log.info("????????????????????????");
                SyncProductionDataEnter syncProductionDataEnter = new SyncProductionDataEnter();
                // ??????????????????????????????????????????
                // ?????????????????????
                syncProductionDataEnter.setProductCode(saleScooter.getProductCode());
                syncProductionDataEnter.setProductType(1);
                syncProductionDataEnter.setStatus(1);
                syncProductionDataEnter.setPicture(saleScooter.getPicture());
                syncProductionDataEnter.setOtherParameter(saleScooter.getOtherParam());
                syncProductionDataEnter.setMaterParameter(saleScooter.getProductionParam());
                syncProductionDataEnter.setMinBatteryNum(saleScooter.getMinBatteryNum());
                syncProductionDataEnter.setProductModelId(saleScooter.getSpecificatId());
                syncProductionDataEnter.setRemark(saleScooter.getRemark());
                syncProductionDataEnter.setProductModelName(saleScooter.getProductName());
                syncProductionDataEnter.setProductModelCode(saleScooter.getProductCode());
                syncProductionDataEnter.setCnName(saleScooter.getProductName());
                syncProductionDataEnter.setEnName(saleScooter.getProductName());
                syncProductionDataEnter.setFrName(saleScooter.getProductName());
                if (!Strings.isNullOrEmpty(saleScooter.getProductionParam())){
                    List<SaleProductionParaEnter> params;
                    try {
                        params = JSONArray.parseArray(saleScooter.getProductionParam(), SaleProductionParaEnter.class);
                    }catch (Exception e) {
                        throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                    }
                    // 1:speed???2???power???3???mileage???4???charge_cycle
                    for (SaleProductionParaEnter param : params) {
                        if (Objects.equals(param.getDefName(), "1")){
                            syncProductionDataEnter.setSpeed(param.getDefValue());
                        }else if (Objects.equals(param.getDefName(), "2")){
                            syncProductionDataEnter.setPower(param.getDefValue());
                        }else if (Objects.equals(param.getDefName(), "3")){
                            syncProductionDataEnter.setMileage(param.getDefValue());
                        }else if (Objects.equals(param.getDefName(), "4")){
                            syncProductionDataEnter.setChargeCycle(param.getDefValue());
                        }
                    }
                }

                // ?????????????????????
                OpeColor color = opeColorService.getById(saleScooter.getColorId());
                if (StringManaConstant.entityIsNull(color)) {
                    throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
                }
                syncProductionDataEnter.setColourCode(color.getColorValue());
                syncProductionDataEnter.setColourName(color.getColorName());

                // ??????????????????????????????????????????
                OpeSpecificatType specificatType = opeSpecificatTypeService.getById(saleScooter.getSpecificatId());
                if (StringManaConstant.entityIsNull(specificatType)) {
                    throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
                }
                OpeSpecificatGroup specificatGroup = opeSpecificatGroupService.getById(specificatType.getGroupId());
                if (StringManaConstant.entityIsNull(specificatGroup)) {
                    throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
                }
                syncProductionDataEnter.setProductClassName(specificatGroup.getGroupName());
                // ?????????ros?????? ????????????/??????  ???????????????code ????????????????????????????????????code
                syncProductionDataEnter.setProductClassCode(specificatGroup.getGroupName());
                // ????????????????????? ??????????????????api??????
                log.info("?????????????????????????????????????????????*******************");
                productionService.syncProductionData(syncProductionDataEnter);
            }
        }catch (Exception ignored){}
    }



//    @SneakyThrows
//    public static void main(String[] args) {
//        long timeMillis = System.currentTimeMillis();
//        System.out.println("111111111");
//        System.out.println("222222222");
//        System.out.println("333333333");
//        System.out.println(timeMillis);
////        Thread.sleep(10);
//        System.out.println(System.currentTimeMillis());
//    }

    @Override
    public PageResult<SaleScooterListResult> saleScooterList(SaleScooterListEnter enter) {
        int totalNum = saleScooterMapper.saleScooterTotal(enter);
        if (NumberUtil.eqZero(totalNum)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<SaleScooterListResult> resultList = saleScooterMapper.saleScooterList(enter);
        return PageResult.create(enter, totalNum, resultList);
    }

    @Override
    public Map<String, Integer> listCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        // 1 2 3???????????????????????????????????????
        map.put("1", opeSaleScooterService.count());
        map.put("2", opeSaleCombinService.count());
        map.put("3", opeSalePartsService.count());
        return map;
    }

    @Override
    public List<SpecificatTypeResult> specificatTypeDataList(GeneralEnter enter) {
       List<OpeSaleScooter> list = opeSaleScooterService.list(new QueryWrapper<OpeSaleScooter>().eq(OpeSaleScooter.COL_SALE_STUTAS,1));
        List<SpecificatTypeResult> specificatTypeResults  = new ArrayList<>();;
        if (CollectionUtils.isNotEmpty(list)){
            QueryWrapper<OpeSpecificatType> wrapper = new QueryWrapper<OpeSpecificatType>()
                    .in(OpeSpecificatType.COL_ID,list.stream().map(OpeSaleScooter::getSpecificatId).collect(Collectors.toList()));
            List<OpeSpecificatType> opeSpecificatTypes = opeSpecificatTypeService.list(wrapper);
            if (CollectionUtils.isNotEmpty(opeSpecificatTypes)){
                for (OpeSpecificatType ope : opeSpecificatTypes){
                    SpecificatTypeResult specificatTypeResult = new SpecificatTypeResult();
                    specificatTypeResult.setSpecificatId(ope.getId());
                    specificatTypeResult.setSpecificatName(ope.getSpecificatName());
                    specificatTypeResults.add(specificatTypeResult);
                }
            }
        }
        return specificatTypeResults;
    }

    @Override
    public List<ColorDataResult> SpecificationsColorLinkage(Long specificatId) {
        QueryWrapper<OpeSaleScooter> wrapper = new QueryWrapper<OpeSaleScooter>()
                .eq(OpeSaleScooter.COL_SPECIFICAT_ID,specificatId).eq(OpeSaleScooter.COL_SALE_STUTAS,1);
        List<OpeSaleScooter> saleScooterList = opeSaleScooterService.list(wrapper);
        List<ColorDataResult> resultList  = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(saleScooterList)){
             QueryWrapper<OpeColor> qw = new QueryWrapper<>();
            qw.in(OpeColor.COL_ID,saleScooterList.stream().map(OpeSaleScooter::getColorId).collect(Collectors.toList()));
            List<OpeColor> colorList = opeColorService.list(qw);
            if (CollectionUtils.isNotEmpty(colorList)){
                for (OpeColor opeColor : colorList) {
                    ColorDataResult result = new ColorDataResult();
                    result.setColorId(opeColor.getId());
                    result.setColorName(opeColor.getColorName());
                    result.setColorValue(opeColor.getColorValue());
                    resultList.add(result);
                }
            }
        }
        return resultList;
    }
}
