package com.redescooter.ses.mobile.rps.service.restproductionorder.consign.impl;

import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.dao.restproductionorder.consign.ConsignOrderServiceMapper;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.restproductionorder.consign.ConsignOrderService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.consign.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:26
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Slf4j
@Service
public class ConsignOrderServiceImpl implements ConsignOrderService {

    @Autowired
    private ConsignOrderServiceMapper consignOrderServiceMapper;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 12:04 下午
     * @Param: enter
     * @Return: Map
     * @desc: countByProductType
     * @param enter
     */
    @Override
    public Map<Integer, Integer> countByProductType(GeneralEnter enter) {
        Map<Integer, Integer> map = new HashMap<>();
        List<CountByStatusResult>  countByStatusResultList=consignOrderServiceMapper.countByProductType(enter);
        map=countByStatusResultList.stream().collect(Collectors.toMap(item->{return Integer.valueOf(item.getStatus());},CountByStatusResult::getTotalCount));

        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (!map.containsKey(item.getValue())){
                map.put(item.getValue(),0);
            }
        }
        return map;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 6:06 下午
     * @Param: enter
     * @Return: ConsignOrderListResult
     * @desc: 委托单列表
     * @param enter
     */
    @Override
    public PageResult<ConsignOrderListResult> list(ConsignOrderListEnter enter) {
        int count=consignOrderServiceMapper.listCount(enter);
        if (count==0){
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter,count,consignOrderServiceMapper.list(enter));
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 7:24 下午
     * @Param: enter
     * @Return: ConsignDetailResult
     * @desc: 单据详情
     * @param enter
     */
    @Override
    public ConsignDetailResult detail(IdEnter enter) {
        ConsignDetailResult detail=consignOrderServiceMapper.detail(enter);
        if (detail==null){
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }
        detail.setConsignDetailProductResultList(this.detailProductList(enter));
        return detail;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 7:25 下午
     * @Param: enter
     * @Return: ConsignOrderListResult
     * @desc: 产品列表
     * @param enter
     */
    @Override
    public List<ConsignDetailProductResult> detailProductList(IdEnter enter) {
        List<ConsignDetailProductResult> result = new ArrayList<>();
//        result=consignOrderServiceMapper.detailProductList(enter);
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 7:56 下午
     * @Param: enter
     * @Return: BooleanResult
     * @desc: 序列号校验
     * @param enter
     */
    @Override
    public BooleanResult checkSerial(StringEnter enter) {
        return null;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 7:34 下午
     * @Param: alex
     * @Return: GeneralResult
     * @desc: 出库
     * @param enter
     */
    @Override
    public GeneralResult ship(ConsignShipEnter enter) {
        List<>
        try {

        }catch (Exception e) {

        }
        return null;
    }
}
