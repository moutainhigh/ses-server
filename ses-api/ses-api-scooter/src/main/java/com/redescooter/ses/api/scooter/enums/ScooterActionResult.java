package com.redescooter.ses.api.scooter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: ScooterActionResultResult
 * @author: Alex
 * @create: 2019/02/25 14:05
 */
@Getter
@AllArgsConstructor
public enum ScooterActionResult {
	SUCCESS("SUCCESS", "成功"),
	FAILURE("FAILURE", "失败");


	private String code;

	private String message;

	public static ScooterActionResult getErrorCodeByCode(String code) {
		for (ScooterActionResult item : ScooterActionResult.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}
