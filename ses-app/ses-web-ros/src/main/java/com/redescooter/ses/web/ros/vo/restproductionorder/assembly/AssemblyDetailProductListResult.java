package com.redescooter.ses.web.ros.vo.restproductionorder.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

/**
 * @ClassName:AssemblyDetailProductListResult
 * @description: AssemblyDetailProductListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 14:27 
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class AssemblyDetailProductListResult extends GeneralResult {

    private Long id;

    private Long productId;

    private Integer productType;

    private String productName;

//    private
//
//    private String orderNo;

    private Integer orderType;

    private Integer qty;
}
