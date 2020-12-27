package com.redescooter.ses.web.ros.vo.distributor.result;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 门店类型选择销售,可售卖产品的数据来源出参
 * @Author Chris
 * @Date 2020/12/17 15:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "门店类型选择销售,可售卖产品的数据来源出参", description = "门店类型选择销售,可售卖产品的数据来源出参")
public class DistributorSaleProductResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = -7093587624742376702L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "规格名称")
    private String specificationName;

}
