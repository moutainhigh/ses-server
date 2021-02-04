package com.redescooter.ses.web.ros.service.common;

import com.redescooter.ses.api.common.service.RosEntrustOrderService;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.service.restproductionorder.consign.ConsignOrderService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author assert
 * @date 2021/1/7 17:07
 */
@Service
public class RosEntrustOrderServiceImpl implements RosEntrustOrderService {

    @Resource
    private ConsignOrderService consignOrderService;

    @Override
    public GeneralResult entrustOrderDeliver(IdEnter enter) {
        /**
         * 调用Aleks委托单发货后状态流转的逻辑
         */
        return consignOrderService.waitSign(enter);
    }

}
