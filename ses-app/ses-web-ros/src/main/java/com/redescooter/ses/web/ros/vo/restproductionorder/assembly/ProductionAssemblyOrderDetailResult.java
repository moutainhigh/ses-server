package com.redescooter.ses.web.ros.vo.restproductionorder.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.Date;

/**
 * @ClassName:ProductionAssemblyOrderDetailResult
 * @description: ProductionAssemblyOrderDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 14:05 
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductionAssemblyOrderDetailResult extends GeneralResult {

    private Long id;

    private Integer combinType;

    private Integer classType;

    private String combinNo;

    private Integer combinStatus;

    private Date combinStartDate;

    private Date combinEndDate;

    private Long principalId;

    private String principalName;

    private String countryCode;

    private String telephone;

    private String remark;


}
