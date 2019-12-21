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
public enum LockScooterBoxEvent {

	LOCK("LOCK", "锁定"),
	UNLOCK("UNLOCK", "解锁");

	private String code;

	private String message;

	public static LockScooterBoxEvent getErrorCodeByCode(String code) {
		for (LockScooterBoxEvent item : LockScooterBoxEvent.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}
