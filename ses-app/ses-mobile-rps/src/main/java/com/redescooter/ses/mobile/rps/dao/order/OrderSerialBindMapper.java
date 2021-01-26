package com.redescooter.ses.mobile.rps.dao.order;

import com.redescooter.ses.mobile.rps.dm.OpeOrderSerialBind;
import org.apache.ibatis.annotations.Param;

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

	/**
	 * 新增订单序列号绑定信息
	 * @param opeOrderSerialBind
	 * @return int
	 * @author assert
	 * @date 2021/1/15
	*/
	int insertOrderSerialBind(OpeOrderSerialBind opeOrderSerialBind);

	/**
	 * 根据orderBId、orderType查询订单序列号绑定信息
	 * @param orderBId, orderType
	 * @return com.redescooter.ses.mobile.rps.dm.OpeOrderSerialBind
	 * @author assert
	 * @date 2021/1/19
	*/
	OpeOrderSerialBind getOrderSerialBindByOrderBIdAndOrderType(@Param("orderBId") Long orderBId,
																@Param("orderType") Integer orderType);

}
