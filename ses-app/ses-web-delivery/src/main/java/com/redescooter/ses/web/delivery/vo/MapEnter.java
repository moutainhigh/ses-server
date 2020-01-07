package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:MapEnter
 * @description: MapEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/07 09:55
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class MapEnter extends GeneralEnter {

    private List<String> statusList;

    private List<Long> scooterIdList;

}
