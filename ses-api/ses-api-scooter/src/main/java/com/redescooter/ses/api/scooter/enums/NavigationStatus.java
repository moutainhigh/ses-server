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
public enum NavigationStatus {

	START("START", "开始导航"),
	CONTINUE("CONTINUE", "继续导航"),
	END("END","结束导航");

	private String code;

	private String message;

	public static NavigationStatus getErrorCodeByCode(String code) {
		for (NavigationStatus item : NavigationStatus.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}
