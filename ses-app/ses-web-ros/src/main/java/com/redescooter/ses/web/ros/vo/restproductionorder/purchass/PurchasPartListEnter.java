package com.redescooter.ses.web.ros.vo.restproductionorder.purchass;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:PurchasPartListEnter
 * @description: PurchasPartListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/12 10:29 
 */
@ApiModel(value = "可采购的部件列表入参", description = "可采购的部件列表入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class PurchasPartListEnter extends PageEnter {
    @ApiModelProperty(value = "部件类型")
    private Integer productType;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
