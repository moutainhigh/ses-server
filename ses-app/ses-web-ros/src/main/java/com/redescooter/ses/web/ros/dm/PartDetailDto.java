package com.redescooter.ses.web.ros.dm;

import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName:PartDetailDao
 * @description: PartDetailDao
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/24 15:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PartDetailDto extends SerializableSerializer {

    private Long partId;

    private BigDecimal price;

    private String unit;

    private Long supplierId;
}
