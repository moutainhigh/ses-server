package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproduction.SaleScooterMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleScooter;
import com.redescooter.ses.web.ros.dm.OpeSpecificatType;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSaleCombinService;
import com.redescooter.ses.web.ros.service.base.OpeSalePartsService;
import com.redescooter.ses.web.ros.service.base.OpeSaleScooterService;
import com.redescooter.ses.web.ros.service.base.OpeSpecificatTypeService;
import com.redescooter.ses.web.ros.service.restproduction.SaleScooterService;
import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterListResult;
import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterSaveOrUpdateEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public GeneralResult saveSaleScooter(SaleScooterSaveOrUpdateEnter enter) {
        // 去空格
        enter = SesStringUtils.objStringTrim(enter);
        // 新增的校验
        check(enter);
        OpeSaleScooter saleScooter = new OpeSaleScooter();
        BeanUtils.copyProperties(enter,saleScooter);
        saleScooter.setGroupId(findGroupId(enter.getSpecificatId()));
        saleScooter.setCreatedBy(enter.getUserId());
        saleScooter.setCreatedTime(new Date());
        saleScooter.setUpdatedBy(enter.getUserId());
        saleScooter.setUpdatedTime(new Date());
        saleScooter.setId(idAppService.getId(SequenceName.OPE_SALE_SCOOTER));
        opeSaleScooterService.saveOrUpdate(saleScooter);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult editSaleScooter(SaleScooterSaveOrUpdateEnter enter) {
        // 去空格
        enter = SesStringUtils.objStringTrim(enter);
        // 新增的校验
        check(enter);
        OpeSaleScooter saleScooter = opeSaleScooterService.getById(enter.getId());
        if (saleScooter == null){
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        saleScooter.setProductName(enter.getProductName());
        saleScooter.setProductCode(enter.getProductCode());
        saleScooter.setSpecificatId(enter.getSpecificatId());
        saleScooter.setGroupId(findGroupId(enter.getSpecificatId()));
        saleScooter.setColorId(enter.getColorId());
        opeSaleScooterService.saveOrUpdate(saleScooter);
        return new GeneralResult(enter.getRequestId());
    }


    public void check(SaleScooterSaveOrUpdateEnter enter){
        if (Strings.isNullOrEmpty(enter.getProductCode())){
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_CODE_NOT_NULL.getCode(), ExceptionCodeEnums.PRODUCT_CODE_NOT_NULL.getMessage());
        }
        QueryWrapper<OpeSaleScooter> qw = new QueryWrapper<>();
        qw.eq(OpeSaleScooter.COL_PRODUCT_CODE,enter.getProductCode());
        if(enter.getId() != null){
            qw.ne(OpeSaleScooter.COL_ID,enter.getId());
        }
        int count = opeSaleScooterService.count(qw);
        if(count > 0){
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(), ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
        }
    }


    // 通过过个类型的id  找到规格分组的id
    public Long findGroupId(Long specificatId) {
        OpeSpecificatType specificatType = specificatTypeService.getById(specificatId);
        if (specificatType == null){
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
        }
        return specificatType.getGroupId();
    }


    @Override
    public GeneralResult deleteSaleScooter(IdEnter enter) {
        opeSaleScooterService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public GeneralResult editSaleScooterStatus(IdEnter enter) {
        // 编辑这玩意之前有个安全码的校验  并把结果放在Redis中  这里再次验证一下安全码校验是否通过
        String key = JedisConstant.CHECK_SAFE_CODE_RESULT + enter.getRequestId();
        String checkResut = jedisService.get(key);
        if (!Boolean.valueOf(checkResut)) {
            throw new SesWebRosException(ExceptionCodeEnums.SAFE_CODE_FAILURE.getCode(),ExceptionCodeEnums.SAFE_CODE_FAILURE.getMessage());
        }
        jedisService.delKey(key);
        OpeSaleScooter saleScooter = opeSaleScooterService.getById(enter.getId());
        if (saleScooter == null){
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        Integer saleStutas = saleScooter.getSaleStutas();
        saleScooter.setSaleStutas(saleStutas==0?1:0);
        opeSaleScooterService.updateById(saleScooter);
        return new GeneralResult(enter.getRequestId());
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
        if (totalNum == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<SaleScooterListResult> resultList = saleScooterMapper.saleScooterList(enter);
        return PageResult.create(enter, totalNum, resultList);
    }

    @Override
    public Map<String, Integer> listCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        // 1 2 3分别对应整车、组装件、部件
        map.put("1",opeSaleScooterService.count());
        map.put("2",opeSaleCombinService.count());
        map.put("3",opeSalePartsService.count());
        return map;
    }
}
