package com.redescooter.ses.web.ros.service.customer;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.customer.ChooseScooterResult;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.web.ros.vo.customer.ScooterCustomerResult;
import com.redescooter.ses.web.ros.vo.customer.TransferScooterEnter;
//import com.redescooter.ses.web.ros.vo.customer.TransferScooterEnter;


/**
 * @ClassName:TransferScooterService
 * @description: TransferScooterService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 16:32
 */



public interface TransferScooterService {

    /**
     * @Author kyle
     * @Description //查询分配整车列表
     * @Date  2020/4/24 17:01
     * @Param [enter]
     * @return
     **/
    PageResult<ChooseScooterResult> chooseScooterList(PageEnter enter);


    /**
     * 车辆分配
     *
     * @param enter
     * @return
     */
    GeneralResult transferScooter(TransferScooterEnter enter);



    /**
     * 车辆用户分配信息
     *
     * @param enter
     */
    PageResult<ScooterCustomerResult> scooterCustomerResult (PageEnter enter);

}
