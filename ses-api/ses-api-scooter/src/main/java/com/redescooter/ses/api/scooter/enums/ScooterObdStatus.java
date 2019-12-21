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
public enum ScooterObdStatus {

	NORMAL("NORMAL", "正常"),
	PROCESSING("PROCESSING", "处理"),
	COMPLETED("COMPLETED","完成");

	private String code;

	private String message;

	public static ScooterObdStatus getErrorCodeByCode(String code) {
		for (ScooterObdStatus item : ScooterObdStatus.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}
