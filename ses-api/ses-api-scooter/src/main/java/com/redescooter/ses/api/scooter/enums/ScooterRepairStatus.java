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
public enum ScooterRepairStatus {

	PLANNED("PLANNED", "预约"),
	REPAIRING("REPAIRING", "维修中"),
	WAITPAYMENT("WAITPAYMENT","等待维修"),
	FINSH("FINSH","维修结束");

	private String code;

	private String message;

	public static ScooterRepairStatus getErrorCodeByCode(String code) {
		for (ScooterRepairStatus item : ScooterRepairStatus.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}
