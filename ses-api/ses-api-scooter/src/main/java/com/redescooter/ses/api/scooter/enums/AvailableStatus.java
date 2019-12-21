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
public enum AvailableStatus {

	AVAILABLE("AVAILABLE", "可用"),
	CHARGING("CHARGING", "充电"),
	REPAIR("REPAIR", "维修"),
	FAULT("FAULT", "失败"),
	USING("USING", "使用");

	private String code;

	private String message;

	public static AvailableStatus getErrorCodeByCode(String code) {
		for (AvailableStatus item : AvailableStatus.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}
