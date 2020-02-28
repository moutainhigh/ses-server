package com.redescooter.ses.web.ros.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redescooter.ses.api.common.enums.bom.BomTypeEnums;
import com.redescooter.ses.api.common.enums.bom.CurrencyUnitEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.StringUtils;
import com.redescooter.ses.web.ros.dao.SupplierChaimRosServiceMapper;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.SupplierChaimRosService;
import com.redescooter.ses.web.ros.vo.bom.ProductPriceDetailResult;
import com.redescooter.ses.web.ros.vo.supplierChaim.EditProductPriceEnter;
import com.redescooter.ses.web.ros.vo.supplierChaim.ProductPriceChartResult;
import com.redescooter.ses.web.ros.vo.supplierChaim.ScProductPriceResult;
import com.redescooter.ses.web.ros.vo.supplierChaim.SupplierChaimListEnter;
import com.redescooter.ses.web.ros.vo.supplierChaim.SupplierChaimListResult;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName:SupplierChaimRosServiceImpl
 * @description: SupplierChaimRosServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/28 10:08
 */
@Service
@Slf4j
public class SupplierChaimRosServiceImpl implements SupplierChaimRosService {

    @Autowired
    private SupplierChaimRosServiceMapper supplierChaimRosServiceMapper;


    /**
     * @param enter
     * @desc: 类型统计
     * @param: enter
     * @retrn: map
     * @auther: alex
     * @date: 2020/2/25 14:41
     * @Version: Ros 1.2
     */
    @Override
    public Map<String, Integer> countByPartType(GeneralEnter enter) {
        List<CountByStatusResult> countByPartType = supplierChaimRosServiceMapper.countByPartType(enter);
        Map<String,Integer> map=new HashMap<>();
        for (CountByStatusResult item : countByPartType) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (BomTypeEnums type : BomTypeEnums.values()) {
            if (!map.containsKey(type.getCode())) {
                map.put(type.getCode(), 0);
            }
        }
        return map;
    }

    /**
     * @param enter
     * @desc: 供应链列表
     * @param: enter
     * @retrn: SupplierChaimListResult
     * @auther: alex
     * @date: 2020/2/25 15:13
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<SupplierChaimListResult> supplierChaimList(SupplierChaimListEnter enter) {
        int count=supplierChaimRosServiceMapper.supplierChaimListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter,count,supplierChaimRosServiceMapper.supplierChaimList(enter));
    }

    /**
     * @param enter
     * @desc: 修改报价
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 15:42
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult editProductPrice(EditProductPriceEnter enter) {
        if (true) {
        // 第一次修改直接保存
            if (StringUtils.isNotBlank(enter.getProductFrUnit())) {
                throw new SesWebRosException(ExceptionCodeEnums.CURRENCY_UNIT_IS_EMPTY.getCode(),ExceptionCodeEnums.CURRENCY_UNIT_IS_EMPTY.getMessage());
            }
            // 货币单位校验
            if (StringUtils.isBlank(CurrencyUnitEnums.checkValue(enter.getProductFrUnit()))){
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
        }
        // 查询产品报价
        return null;
    }

    /**
     * 产品报价详情
     *
     * @param id
     * @return
     */
    @Override
    public ProductPriceDetailResult productPriceDetail(IdEnter id) {
        if (true) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_PRICE_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.PRODUCT_PRICE_IS_NOT_EXIST.getMessage());
        }
        return ProductPriceDetailResult.builder()
//                .id()
//                .productEnPrice()
//                .productEnUnit()
//                .productFrPrice()
//                .productFrUnit()
                .build();
    }

    /**
     * 货币单位
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> currencyUnit(GeneralEnter enter) {
        Map<String, String> result=new HashMap<>();
        for (CurrencyUnitEnums item : CurrencyUnitEnums.values()) {
            result.put(item.getValue(),item.getCode());
        }
        return result;
    }

    /**
     * @param enter
     * @desc: 产品价格列表
     * @paam: enter
     * @retrn: ProductPriceResult
     * @auther: alex
     * @date: 2020/2/25 15:31
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<ScProductPriceResult> productPriceList(IdEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 价格图表
     * @param: enter
     * @retrn: ProductPriceResult
     * @auther: alex
     * @date: 2020/2/25 15:37
     * @Version: Ros 1.2
     */
    @Override
    public ProductPriceChartResult productPriceChart(IdEnter enter) {
        return null;
    }
}
