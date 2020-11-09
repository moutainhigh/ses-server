package com.redescooter.ses.web.ros.service.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.CancelOrderEnter;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

/**
 * @ClassNameAllocateOrderService
 * @Description
 * @Author Aleks
 * @Date2020/10/23 11:33
 * @Version V1.0
 **/
public interface AllocateOrderService {

    /**
     * @Author Aleks
     * @Description  调拨单列表
     * @Date  2020/10/23 12:05
     * @Param [enter]
     * @return
     **/
    PageResult<AllocateOrderListResult> allocateList(AllocateOrderListEnter enter);


    /**
     * @Author Aleks
     * @Description 调拨单新增
     * @Date  2020/10/23 14:38
     * @Param [enter]
     * @return
     **/
    GeneralResult allocateSave(AllocateOrderOrUpdateSaveEnter enter);


    /**
     * @Author Aleks
     * @Description
     * @Date  2020/10/23 16:01
     * @Param [enter]
     * @return
     **/
    GeneralResult allocateEdit(AllocateOrderOrUpdateSaveEnter enter);


    /**
     * @Author Aleks
     * @Description  调拨单详情
     * @Date  2020/10/23 16:31
     * @Param [enter]
     * @return
     **/
    AllocateOrderDetailResult allocateDetail(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  调拨单立即下单
     * @Date  2020/10/23 17:48
     * @Param [enter]
     * @return
     **/
    GeneralResult allocateConfirmOrder(IdEnter enter);


    /**
     * @Author Aleks
     * @Description 取消订单
     * @Date  2020/10/23 17:50
     * @Param [enter]
     * @return
     **/
    GeneralResult allocateCancelOrder(CancelOrderEnter enter);


    /**
     * @Author Aleks
     * @Description  调拨单删除
     * @Date  2020/10/27 20:37
     * @Param [enter]
     * @return
     **/
    GeneralResult allocateDelete(IdEnter enter);


    /**
     * @Author Aleks
     * @Description 人员下拉数据
     * @Date  2020/10/23 17:50
     * @Param [enter]
     * @return
     **/
    List<UserDataResult> userData(UserDataEnter enter);


    /**
     * @Author Aleks
     * @Description  列表统计
     * @Date  2020/10/26 10:16
     * @Param [enter]
     * @return
     **/
    Map<String,Integer>  listCount(GeneralEnter enter);


    /**
     * @Author Aleks
     * @Description  仓库下拉接口数据源
     * @Date  2020/10/26 19:04
     * @Param [enter]
     * @return
     **/
    List<WhDataResult> whData(WhDataEnter enter);


    /**
     * @Author Aleks
     * @Description  调拨单状态变采购中
     * @Date  2020/10/30 11:14
     * @Param
     * @return
     **/
     void allocatePurchaseing(Long allocateId,Long userId);


    /**
     * @Author Aleks
     * @Description  调拨单状态变待发货
     * @Date  2020/10/30 10:30
     * @Param 调拨单id  操作人id
     * @return
     **/
    void allocateWaitDeliver(Long allocateId,Long userId);


    /**
     * @Author Aleks
     * @Description  调拨单签收
     * @Date  2020/10/30 10:30
     * @Param 调拨单id 采购单id  操作人id
     * @return
     **/
    void allocateSign(Long allocateId,Long purchaseId,Long userId);


    /**
     * @Author Aleks
     * @Description  调拨单的状态变为待签收
     * @Date  2020/10/30 19:26
     * @Param [allocateId, userId]
     * @return
     **/
    void allocateWaitSign(Long allocateId,Long userId);


    /**
     * @Author Aleks
     * @Description  调拨单状态变已完成
     * @Date  2020/10/30 10:30
     * @Param 调拨单id 采购单id
     * @return
     **/
    void allocateFinish(Long allocateId,Long purchaseId,Long userId);


    /**
     * @Author Aleks
     * @Description  调拨单的产品列表返参
     * @Date  2020/11/9 15:15
     * @Param [enter]
     * @return
     **/
    AllocateProductListResult allocateProductData(IdEnter enter);
}
