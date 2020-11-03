package com.redescooter.ses.mobile.rps.service.restproductionorder.consign;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.consign.*;

import java.util.List;
import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:26
 *  @version：V ROS 1.8.3
 *  @Description:
 */
public interface ConsignOrderService {

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 12:04 下午
     * @Param: enter
     * @Return: Map
     * @desc: countByProductType
     */
    Map<Integer, Integer> countByProductType(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 6:06 下午
     * @Param: enter
     * @Return: ConsignOrderListResult
     * @desc: 委托单列表
     */
    PageResult<ConsignOrderListResult> list(ConsignOrderListEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/3 7:24 下午
    * @Param:  enter
    * @Return: ConsignDetailResult
    * @desc: 单据详情
    */
    ConsignDetailResult detail(IdEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/3 7:25 下午
    * @Param:  enter
    * @Return: ConsignOrderListResult
    * @desc: 产品列表
    */
    List<ConsignDetailProductResult> detailProductList(IdEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/3 7:56 下午
    * @Param:  enter
    * @Return: BooleanResult
    * @desc: 序列号校验
    */
    BooleanResult checkSerial(StringEnter enter);

    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/3 7:34 下午
    * @Param:  alex
    * @Return: GeneralResult
    * @desc: 出库
    */
    GeneralResult ship(ConsignShipEnter enter);


}
