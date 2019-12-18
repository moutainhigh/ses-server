package com.redescooter.ses.service.scooter.constant;

import java.math.BigDecimal;

/**
 * @ClassName:ScooterDefaultData
 * @description: ScooterDefaultData
 * @author: Alex
 * @Version：1.3
 * @create: 2019/09/11 13:54
 */
public interface ScooterDefaultData {

    Long totalMileage = 0L;

    int battery = 100;

    BigDecimal scooterLongitule=new BigDecimal(2.3522220000);

    BigDecimal scooterLatitude=new BigDecimal(48.8566150000);

    int revision=0;
}
