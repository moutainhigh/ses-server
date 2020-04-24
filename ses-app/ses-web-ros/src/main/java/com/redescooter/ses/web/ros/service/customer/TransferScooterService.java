package com.redescooter.ses.web.ros.service.customer;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.customer.ChooseScooterIdEnter;
import com.redescooter.ses.web.ros.vo.customer.ChooseScooterResult;

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
    PageResult<ChooseScooterResult> chooseScooterList(ChooseScooterIdEnter enter);



}
