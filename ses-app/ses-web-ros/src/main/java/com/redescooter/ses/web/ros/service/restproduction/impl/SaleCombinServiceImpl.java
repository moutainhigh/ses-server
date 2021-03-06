package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.JedisConstant;
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
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionProductServiceMapper;
import com.redescooter.ses.web.ros.dao.restproduction.SaleCombinMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleCombin;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSaleCombinService;
import com.redescooter.ses.web.ros.service.restproduction.SaleCombinService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.restproduct.BomNameData;
import com.redescooter.ses.web.ros.vo.restproduct.BomNoEnter;
import com.redescooter.ses.web.ros.vo.restproduct.CombinNameData;
import com.redescooter.ses.web.ros.vo.restproduct.CombinNameEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SaleCombinListResult;
import com.redescooter.ses.web.ros.vo.restproduct.SaleCombinSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SaleListEnter;
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
public class SaleCombinServiceImpl implements SaleCombinService {

    @Autowired
    private OpeSaleCombinService opeSaleCombinService;

    @Autowired
    private SaleCombinMapper saleCombinMapper;

    @Autowired
    private RosProductionProductServiceMapper rosProductionProductServiceMapper;

    @Autowired
    private JedisService jedisService;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private PartsService partsService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult saveSaleCombin(SaleCombinSaveOrUpdateEnter enter) {
        // ?????????
        enter = SesStringUtils.objStringTrim(enter);
        // ???????????????
        check(enter);
        OpeSaleCombin combin = new OpeSaleCombin();
        BeanUtils.copyProperties(enter, combin);
        combin.setCreatedBy(enter.getUserId());
        combin.setCreatedTime(new Date());
        combin.setUpdatedBy(enter.getUserId());
        combin.setUpdatedTime(new Date());
        combin.setId(idAppService.getId(SequenceName.OPE_SALE_COMBIN));
        opeSaleCombinService.saveOrUpdate(combin);
        return new GeneralResult(enter.getRequestId());
    }


    public void check(SaleCombinSaveOrUpdateEnter enter) {
        if (Strings.isNullOrEmpty(enter.getProductName())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_CODE_NOT_NULL.getCode(), ExceptionCodeEnums.PRODUCT_CODE_NOT_NULL.getMessage());
        }
        QueryWrapper<OpeSaleCombin> qw = new QueryWrapper<>();
        qw.eq(OpeSaleCombin.COL_PRODUCT_NAME, enter.getProductName());
        if (StringManaConstant.entityIsNotNull(enter.getId())) {
            qw.ne(OpeSaleCombin.COL_ID, enter.getId());
        }
        int count = opeSaleCombinService.count(qw);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(), ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
        }
    }


    @Override
    public PageResult<SaleCombinListResult> saleCombinList(SaleListEnter enter) {
        // ?????????
        enter = SesStringUtils.objStringTrim(enter);
        int totalNum = saleCombinMapper.saleCombinTotal(enter);
        if (NumberUtil.eqZero(totalNum)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<SaleCombinListResult> resultList = saleCombinMapper.saleCombinList(enter);
        return PageResult.create(enter, totalNum, resultList);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editSaleCombin(SaleCombinSaveOrUpdateEnter enter) {
        // ?????????
        enter = SesStringUtils.objStringTrim(enter);
        // ???????????????
        check(enter);
        OpeSaleCombin combin = opeSaleCombinService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(combin)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        combin.setProductName(enter.getProductName());
        combin.setCombinName(enter.getCombinName());
        combin.setProductionCombinBomId(enter.getProductionCombinBomId());
        combin.setPicture(enter.getPicture());
        combin.setPrice(enter.getPrice());
        combin.setUpdatedBy(enter.getUserId());
        combin.setUpdatedTime(new Date());
        opeSaleCombinService.updateById(combin);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult deleteSaleCombin(IdEnter enter) {
        OpeSaleCombin combin = opeSaleCombinService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(combin)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        opeSaleCombinService.removeById(enter.getId());
        // ros???????????????,??????????????????????????????????????????
        syncDeleteData(combin.getProductCode());
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult editSaleCombinStatus(IdEnter enter) {
        // ?????????????????????????????????????????????  ??????????????????Redis???  ???????????????????????????????????????????????????
        String key = JedisConstant.CHECK_SAFE_CODE_RESULT + enter.getRequestId();
        String checkResut = jedisService.get(key);
        if (!Boolean.valueOf(checkResut)) {
            throw new SesWebRosException(ExceptionCodeEnums.SAFE_CODE_FAILURE.getCode(), ExceptionCodeEnums.SAFE_CODE_FAILURE.getMessage());
        }
        jedisService.delKey(key);
        OpeSaleCombin combin = opeSaleCombinService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(combin)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        Integer saleStatus = combin.getSaleStutas();
        combin.setSaleStutas(saleStatus == 0 ? 1 : 0);
        opeSaleCombinService.updateById(combin);

        // ????????????????????????????????????
        syncData(combin);
        return new GeneralResult(enter.getRequestId());
    }

    void syncData(OpeSaleCombin combin) {
        SyncSalePartsDataEnter model = new SyncSalePartsDataEnter();
        model.setStatus(combin.getSaleStutas() == 1 ? 1 : -1);
        model.setPartsType(5);
        model.setPartsNumber(combin.getProductName());
        model.setEnName(combin.getCombinName());
        model.setEffectiveTime(new Date());
        model.setPicture(combin.getPicture());
        model.setPrice(combin.getPrice());
        model.setRemark(combin.getRemark());
        model.setRevision(0);
        model.setCreatedBy(0L);
        model.setCreatedTime(new Date());
        partsService.syncSalePartsData(model);
    }

    void syncDeleteData(String productCode) {
        partsService.syncDeleteData(productCode);
    }


    @Override
    public List<CombinNameData> combinNameData(CombinNameEnter enter) {
        List<CombinNameData> list = rosProductionProductServiceMapper.combinNameData(enter);
        return list;
    }


    @Override
    public List<BomNameData> bomNoData(BomNoEnter enter) {
        if (Strings.isNullOrEmpty(enter.getCombinName())) {
            throw new SesWebRosException(ExceptionCodeEnums.SELECT_COMBIN_NAME.getCode(), ExceptionCodeEnums.SELECT_COMBIN_NAME.getMessage());
        }
        List<BomNameData> list = rosProductionProductServiceMapper.bomNoData(enter);
        return list;
    }


    @Override
    public List<CombinNameData> combinCnNameData(CombinNameEnter enter) {
        List<CombinNameData> list = rosProductionProductServiceMapper.combinCnNameData(enter);
        return list;
    }

    @Override
    public List<BomNameData> cnBomNoData(BomNoEnter enter) {
        if (Strings.isNullOrEmpty(enter.getCombinName())) {
            throw new SesWebRosException(ExceptionCodeEnums.SELECT_COMBIN_NAME.getCode(), ExceptionCodeEnums.SELECT_COMBIN_NAME.getMessage());
        }
        List<BomNameData> list = rosProductionProductServiceMapper.cnBomNoData(enter);
        return list;
    }


}
