package com.redescooter.ses.api.mobile.b.service.express;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.mobile.b.vo.CompleteEnter;
import com.redescooter.ses.api.mobile.b.vo.CompleteResult;
import com.redescooter.ses.api.mobile.b.vo.StartEnter;
import com.redescooter.ses.api.mobile.b.vo.express.EdRfuseEnter;
import com.redescooter.ses.api.mobile.b.vo.express.OrderResult;

import java.util.List;

/**
 *  @author: alex
 *  @Date: 2020/2/6 15:02
 *  @version：V 1.2
 *  @Description: 订单业务
 */
public interface EdOrderService {
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/6 15:18
    * @Param:  enter
    * @Return: PageResult<OrderResult>
    * @desc:  订单列表
    */
    List<OrderResult> orderList(GeneralEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/6 15:30
    * @Param:  enter
    * @Return: OrderResult
    * @desc: 订单详情
    */
    OrderResult  orderDetail(IdEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
     *
    * @Date:   2020/2/6 15:31
    * @Param:  enter
    * @Return: GeneralResult
    * @desc: 开始订单
    */
    GeneralResult start(StartEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/6 15:32
    * @Param:  enter
    * @Return: GeneralResult
    * @desc:  拒绝订单
    */
    GeneralResult refuse(EdRfuseEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/6 15:41
    * @Param:  enter
    * @Return: CompleteResult
    * @desc: 完成订单
    */
    CompleteResult complete(CompleteEnter enter);

}
