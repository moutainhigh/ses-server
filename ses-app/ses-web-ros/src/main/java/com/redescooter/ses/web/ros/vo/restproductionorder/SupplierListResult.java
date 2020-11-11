package com.redescooter.ses.web.ros.vo.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:SupplierListResult
 * @description: SupplierListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 15:12 
 */
@ApiModel(value = "供应商列表", description = "供应商列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SupplierListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "负责人列表")
    private List<SupplierPrincipaleResult> principaleList;
}
