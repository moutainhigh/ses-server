package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.hub.service.website.PartsService;
import com.redescooter.ses.api.hub.vo.website.SyncSalePartsDataEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionPartsServiceMapper;
import com.redescooter.ses.web.ros.dao.restproduction.SalePartsMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import com.redescooter.ses.web.ros.dm.OpeSaleParts;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsService;
import com.redescooter.ses.web.ros.service.base.OpeSalePartsService;
import com.redescooter.ses.web.ros.service.restproduction.SalePartsService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.restproduct.PartsNameData;
import com.redescooter.ses.web.ros.vo.restproduct.PartsNoData;
import com.redescooter.ses.web.ros.vo.restproduct.PartsNoEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SaleListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SalePartsListResult;
import com.redescooter.ses.web.ros.vo.restproduct.SalePartsSaveOrUpdateEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author Aleks
 * @Description
 * @Date 2020/10/20 10:28
 * @Param
 * @return
 **/
@Service
@Slf4j
public class SalePartsServiceImpl implements SalePartsService {

    @Autowired
    private OpeSalePartsService opeSalePartsService;

    @Autowired
    private SalePartsMapper salePartsMapper;

    @Autowired
    private RosProductionPartsServiceMapper rosProductionPartsServiceMapper;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private JedisService jedisService;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private PartsService partsService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult saveSaleParts(SalePartsSaveOrUpdateEnter enter) {
        // ?????????
        enter = SesStringUtils.objStringTrim(enter);
        // ???????????????
        check(enter);
        OpeSaleParts saleParts = new OpeSaleParts();
        BeanUtils.copyProperties(enter, saleParts);
        saleParts.setCreatedBy(enter.getUserId());
        saleParts.setCreatedTime(new Date());
        saleParts.setUpdatedBy(enter.getUserId());
        saleParts.setUpdatedTime(new Date());
        saleParts.setId(idAppService.getId(SequenceName.OPE_SALE_PARTS));
        opeSalePartsService.saveOrUpdate(saleParts);
        return new GeneralResult(enter.getRequestId());
    }

    public void check(SalePartsSaveOrUpdateEnter enter) {
        if (Strings.isNullOrEmpty(enter.getProductName())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_NAME_NOT_NULL.getCode(), ExceptionCodeEnums.PRODUCT_NAME_NOT_NULL.getMessage());
        }
        QueryWrapper<OpeSaleParts> qw = new QueryWrapper<>();
        qw.eq(OpeSaleParts.COL_PRODUCT_NAME, enter.getProductName());
        if (StringManaConstant.entityIsNotNull(enter.getId())) {
            qw.ne(OpeSaleParts.COL_ID, enter.getId());
        }
        int count = opeSalePartsService.count(qw);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(), ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
        }
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editSaleParts(SalePartsSaveOrUpdateEnter enter) {
        // ?????????
        enter = SesStringUtils.objStringTrim(enter);
        // ???????????????
        check(enter);
        OpeSaleParts saleParts = opeSalePartsService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(saleParts)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        saleParts.setProductName(enter.getProductName());
        saleParts.setProductCode(enter.getProductCode());
        saleParts.setPartsName(enter.getPartsName());
        saleParts.setPartsId(enter.getPartsId());
        saleParts.setPicture(enter.getPicture());
        saleParts.setPrice(enter.getPrice());
        saleParts.setUpdatedBy(enter.getUserId());
        saleParts.setUpdatedTime(new Date());
        opeSalePartsService.updateById(saleParts);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteSaleParts(IdEnter enter) {
        OpeSaleParts parts = opeSalePartsService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(parts)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        opeSalePartsService.removeById(enter.getId());
        // ros???????????????,??????????????????????????????????????????
        syncDeleteData(parts.getProductCode());
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editSalePartsStatus(IdEnter enter) {
        // ?????????????????????????????????????????????  ??????????????????Redis???  ???????????????????????????????????????????????????
        String key = JedisConstant.CHECK_SAFE_CODE_RESULT + enter.getRequestId();
        String checkResut = jedisService.get(key);
        if (!Boolean.valueOf(checkResut)) {
            throw new SesWebRosException(ExceptionCodeEnums.SAFE_CODE_FAILURE.getCode(), ExceptionCodeEnums.SAFE_CODE_FAILURE.getMessage());
        }
        jedisService.delKey(key);
        OpeSaleParts saleParts = opeSalePartsService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(saleParts)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        Integer saleStatus = saleParts.getSaleStutas();
        saleParts.setSaleStutas(saleStatus == 0 ? 1 : 0);
        opeSalePartsService.updateById(saleParts);

        // ????????????????????????????????????
        syncData(saleParts);
        return new GeneralResult(enter.getRequestId());
    }

    void syncData(OpeSaleParts saleParts) {
        Long partsId = saleParts.getPartsId();
        OpeProductionParts parts = opeProductionPartsService.getById(partsId);
        if (StringManaConstant.entityIsNotNull(parts)) {
            SyncSalePartsDataEnter model = new SyncSalePartsDataEnter();
            model.setStatus(saleParts.getSaleStutas() == 1 ? 1 : -1);
            model.setPartsType(parts.getPartsType());
            model.setPartsNumber(saleParts.getProductName());
            model.setEnName(saleParts.getPartsName());
            model.setEffectiveTime(new Date());
            model.setPicture(saleParts.getPicture());
            model.setPrice(saleParts.getPrice());
            model.setRemark(saleParts.getRemark());
            model.setRevision(0);
            model.setCreatedBy(0L);
            model.setCreatedTime(new Date());
            partsService.syncSalePartsData(model);
        }
    }

    void syncDeleteData(String productCode) {
        partsService.syncDeleteData(productCode);
    }

    @Override
    public PageResult<SalePartsListResult> salePartsList(SaleListEnter enter) {
        // ?????????
        enter = SesStringUtils.objStringTrim(enter);
        int totalNum = salePartsMapper.salePartsTotal(enter);
        if (NumberUtil.eqZero(totalNum)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<SalePartsListResult> resultList = salePartsMapper.salePartsList(enter);
        return PageResult.create(enter, totalNum, resultList);
    }

    @Override
    public List<PartsNameData> partsNameData(GeneralEnter enter) {
        List<PartsNameData> list = rosProductionPartsServiceMapper.partsNameData();
        return list;
    }

    @Override
    public List<PartsNoData> partsNoData(PartsNoEnter enter) {
        List<PartsNoData> list = rosProductionPartsServiceMapper.partsNoData(enter);
        return list;
    }
}
