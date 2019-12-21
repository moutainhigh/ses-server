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
public enum NavigationEvent {

	START("START", "锁定"),
	END("END", "解锁");

	private String code;

	private String message;

	public static NavigationEvent getErrorCodeByCode(String code) {
		for (NavigationEvent item : NavigationEvent.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}
