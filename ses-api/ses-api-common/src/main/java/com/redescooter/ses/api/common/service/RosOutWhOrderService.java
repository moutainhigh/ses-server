package com.redescooter.ses.api.common.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;

/**
 * ROS项目出库单业务接口
 * @author assert
 * @date 2021-01-10
 */
public interface RosOutWhOrderService {

	/**
	 * 提交出库
	 * @param enter
	 * @return com.redescooter.ses.api.common.vo.base.GeneralResult
	 * @author assert
	 * @date 2021-01-10
	 */
	GeneralResult outWarehouse(IdEnter enter);

	/**
	 * 生成出库质检单
	 * @param enter
	 * @return com.redescooter.ses.api.common.vo.base.GeneralResult
	 * @author assert
	 * @date 2021/1/26
	*/
	GeneralResult generatorQcOrderByOutBound(IdEnter enter);

}
