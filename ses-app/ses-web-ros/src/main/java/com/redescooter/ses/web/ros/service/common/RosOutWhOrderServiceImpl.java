package com.redescooter.ses.web.ros.service.common;

import com.redescooter.ses.api.common.service.RosOutWhOrderService;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.service.restproductionorder.outbound.OutboundOrderService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author assert
 * @date 2021-01-10
 */
@Service
public class RosOutWhOrderServiceImpl implements RosOutWhOrderService {

	@Resource
	private OutboundOrderService outboundOrderService;


	@Override
	public GeneralResult outWarehouse(IdEnter enter) {
		/**
		 * 调用Aleks提交出库后的状态流转方法
		 */
		return outboundOrderService.endQc(enter);
	}

}
