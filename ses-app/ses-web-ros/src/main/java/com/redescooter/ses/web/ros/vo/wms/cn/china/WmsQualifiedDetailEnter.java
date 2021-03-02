package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author Chris
 * @Date 2021/1/26 10:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class WmsQualifiedDetailEnter extends PageEnter implements Serializable {

    private static final long serialVersionUID = -1156487184894853820L;

    @ApiModelProperty(value = "列表的id")
    private Long id;

    @ApiModelProperty(value = "产品类型 1车辆 2组装件 3部件")
    private Integer type;

}
