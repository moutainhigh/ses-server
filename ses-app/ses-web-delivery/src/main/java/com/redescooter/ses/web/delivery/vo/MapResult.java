package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:DeliveryMapResult
 * @description: DeliveryMapResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/04 14:54
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class MapResult extends GeneralResult {

    private Long tenantId;

    private String tenantLng;

    private String tenantLat;

    private List<DeliveryMapResult> deliveryMapList;

    private List<ScooterMapResult> scooterMapResultList;
}
