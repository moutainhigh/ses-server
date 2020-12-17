package com.redescooter.ses.web.ros.vo.distributor.enter;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 经销商门店名称入参
 * @Author Chris
 * @Date 2020/12/17 17:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "经销商门店名称入参", description = "经销商门店名称入参")
public class DistributorNameEnter extends GeneralEnter implements Serializable {

    private static final long serialVersionUID = 9023169191500563517L;

    @ApiModelProperty(value = "新增时,传递-1,查询所有门店名称;修改时,传递-1之外的其他id,查询除了此门店外的其他所有门店名称", required = true)
    private String key;

}
