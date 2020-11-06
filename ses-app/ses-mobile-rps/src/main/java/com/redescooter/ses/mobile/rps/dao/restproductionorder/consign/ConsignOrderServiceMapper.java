package com.redescooter.ses.mobile.rps.dao.restproductionorder.consign;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.consign.*;
import lombok.*;

import java.util.List;

/**
 * @ClassName:ConsignOrderService
 * @description: ConsignOrderService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/04 10:01 
 */

public interface ConsignOrderServiceMapper{
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/4 10:14 上午
    * @Param:  enter
    * @Return: CountByStatusResult
    * @desc: 产品类型统计
    */
    List<CountByStatusResult> countByProductType(GeneralEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/4 10:17 上午
    * @Param:  enter
    * @Return: ConsignOrderListResult
    * @desc: 出库单统计
    */
    int listCount(ConsignOrderListEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/4 10:19 上午
    * @Param:  enter
    * @Return: ConsignOrderListResult
    * @desc: 列表
    */
    List<ConsignOrderListResult> list(ConsignOrderListEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/4 10:21 上午
    * @Param:  enter
    * @Return: ConsignDetailResult
    * @desc: 委托单详情
    */
    ConsignDetailResult detail(IdEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/5 2:39 下午
    * @Param:  enter
    * @Return: ConsignProductSerialResult
    * @desc: 委托单车辆列表
    */
    List<ConsignProductSerialResult> detailProductScooterList(Long id);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/5 2:40 下午
    * @Param:  enter
    * @Return: ConsignProductSerialResult
    * @desc: 委托单组合件列表
    */
    List<ConsignProductSerialResult> detailProductCombinList(Long id);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/5 2:40 下午
    * @Param:  enter
    * @Return: ConsignProductSerialResult
    * @desc: 委托单部件列表
    */
    List<ConsignProductSerialResult> detailProductPartList(Long id);
}
