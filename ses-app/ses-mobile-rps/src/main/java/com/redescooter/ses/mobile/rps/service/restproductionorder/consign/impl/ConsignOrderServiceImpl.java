package com.redescooter.ses.mobile.rps.service.restproductionorder.consign.impl;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.service.restproductionorder.consign.ConsignOrderService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.consign.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:26
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Slf4j
@Service
public class ConsignOrderServiceImpl implements ConsignOrderService {

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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
    }
}
