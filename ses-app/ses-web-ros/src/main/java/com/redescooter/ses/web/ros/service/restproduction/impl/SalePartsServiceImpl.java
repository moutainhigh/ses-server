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
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionPartsServiceMapper;
import com.redescooter.ses.web.ros.dao.restproduction.SalePartsMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleParts;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSalePartsService;
import com.redescooter.ses.web.ros.service.restproduction.SalePartsService;
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
import org.springframework.scheduling.annotation.Async;
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
    private JedisService jedisService;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private PartsService partsService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult saveSaleParts(SalePartsSaveOrUpdateEnter enter) {
        // 去空格
        enter = SesStringUtils.objStringTrim(enter);
        // 新增的校验
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
        if (enter.getId() != null) {
            qw.ne(OpeSaleParts.COL_ID, enter.getId());
        }
        int count = opeSalePartsService.count(qw);
        if (count > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(), ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
        }
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editSaleParts(SalePartsSaveOrUpdateEnter enter) {
        // 去空格
        enter = SesStringUtils.objStringTrim(enter);
        // 新增的校验
        check(enter);
        OpeSaleParts saleParts = opeSalePartsService.getById(enter.getId());
        if (saleParts == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        saleParts.setProductName(enter.getProductName());
        saleParts.setProductCode(enter.getProductCode());
        saleParts.setPartsName(enter.getPartsName());
        saleParts.setPartsId(enter.getPartsId());
        saleParts.setUpdatedBy(enter.getUserId());
        saleParts.setUpdatedTime(new Date());
        opeSalePartsService.updateById(saleParts);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteSaleParts(IdEnter enter) {
        OpeSaleParts parts = opeSalePartsService.getById(enter.getId());
        if (null == parts) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        opeSalePartsService.removeById(enter.getId());
        // ros删除配件后,需将官网已同步的配件同样删除
        syncDeleteData(parts.getProductCode());
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editSalePartsStatus(IdEnter enter) {
        // 编辑这玩意之前有个安全码的校验  并把结果放在Redis中  这里再次验证一下安全码校验是否通过
        String key = JedisConstant.CHECK_SAFE_CODE_RESULT + enter.getRequestId();
        String checkResut = jedisService.get(key);
        if (!Boolean.valueOf(checkResut)) {
            throw new SesWebRosException(ExceptionCodeEnums.SAFE_CODE_FAILURE.getCode(), ExceptionCodeEnums.SAFE_CODE_FAILURE.getMessage());
        }
        jedisService.delKey(key);
        OpeSaleParts saleParts = opeSalePartsService.getById(enter.getId());
        if (saleParts == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        Integer saleStatus = saleParts.getSaleStutas();
        saleParts.setSaleStutas(saleStatus == 0 ? 1 : 0);
        opeSalePartsService.updateById(saleParts);

        // 数据同步到官网的销售配件
        OpeSaleParts opeSaleParts = opeSalePartsService.getById(enter.getId());
        syncData(opeSaleParts);
        return new GeneralResult(enter.getRequestId());
    }

    @Async
    void syncData(OpeSaleParts saleParts) {
        SyncSalePartsDataEnter model = new SyncSalePartsDataEnter();
        model.setStatus(saleParts.getSaleStutas() == 1 ? 1 : 2);
        model.setPartsType(1);
        model.setPartsNumber(saleParts.getProductCode());
        model.setEnName(saleParts.getPartsName());
        model.setEffectiveTime(new Date());
        model.setRemark(saleParts.getRemark());
        model.setRevision(0);
        model.setCreatedBy(0L);
        model.setCreatedTime(new Date());
        partsService.syncSalePartsData(model);
    }

    @Async
    void syncDeleteData(String productCode) {
        partsService.syncDeleteData(productCode);
    }

    @Override
    public PageResult<SalePartsListResult> salePartsList(SaleListEnter enter) {
        // 去空格
        enter = SesStringUtils.objStringTrim(enter);
        int totalNum = salePartsMapper.salePartsTotal(enter);
        if (totalNum == 0) {
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
