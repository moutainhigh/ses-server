package com.redescooter.ses.api.scooter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: ScooterAction
 * @author: Darren
 * @create: 2019/02/20 10:21
 */
@Getter
@AllArgsConstructor
public enum ScooterAction {

	LOCK("LOCKED", "锁车"),
	UNLOCK("UNLOCK", "开锁"),
	START_ENGINE("START_ENGINE", "启动引擎"),
	STOP_ENGINE("STOP_ENGINE", "关闭引擎"),
	OPEN_BATTERY_COMPARTMENT("OPEN_BATTERY_COMPARTMENT", "打开电池仓"),
	CLOSE_BATTERY_COMPARTMENT("CLOSE_BATTERY_COMPARTMENT", "关闭电池仓"),
	OPEN_BOX("OPEN_BOX", "打开后备箱"),
	CLOSE_BOX("CLOSE_BOX", "关闭后备箱"),
	STARTNAVIGATION("START_NAVIGATION","开始导航"),
	ENDNAVIGATION("END_NAVIGATION","结束导航"),
	CONTINUENAVIGATION("CONTINUE_NAVIGATION","继续导航"),
	SCOOTEROBD_NORMAL("NORMAL","正常");


	private String code;

	private String message;

	public static ScooterAction getErrorCodeByCode(String code) {
		for (ScooterAction item : ScooterAction.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}
