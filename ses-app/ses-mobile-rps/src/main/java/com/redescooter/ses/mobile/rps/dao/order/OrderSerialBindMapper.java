package com.redescooter.ses.mobile.rps.dao.order;

import com.redescooter.ses.mobile.rps.dm.OpeOrderSerialBind;

/**
 * 订单序列号绑定信息 Mapper接口
 * @author assert
 * @date 2021-01-10
 */
public interface OrderSerialBindMapper {
	
	/**
	 * 根据serialNum查询订单序列号绑定信息
	 * @param serialNum
	 * @return com.redescooter.ses.mobile.rps.dm.OpeOrderSerialBind
	 * @author assert
	 * @date 2021-01-10
	 */
	OpeOrderSerialBind getOrderSerialBindBySerialNum(String serialNum);

}
