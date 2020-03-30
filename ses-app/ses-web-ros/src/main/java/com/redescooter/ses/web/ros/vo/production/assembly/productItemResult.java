package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName:productItemResult
 * @description: productItemResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 13:57
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class productItemResult extends GeneralResult {

    private Long id;

    private String productN;

    private String enName;

    private String cnName;

    private Integer qty;

    private BigDecimal price;

    private BigDecimal subtotal;

    private Integer stock;

}
