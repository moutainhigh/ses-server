package com.redescooter.ses.api.common.enums.scooter;

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

	START("START", "开始导航","1"),

	END("END","结束导航","2")
	;

	private String code;

	private String message;

	private String value;

	public static NavigationStatus getErrorCodeByCode(String code) {
		for (NavigationStatus item : NavigationStatus.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}
