package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ScooterLicensePlateEnter
 * @description: ScooterLicensePlateEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/07 18:30
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ScooterLicensePlateEnter extends GeneralEnter {
    private String licensePlate;
}
