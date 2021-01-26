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
 * @Date 2021/1/26 10:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class MaterialDetailEnter extends PageEnter implements Serializable {

    private static final long serialVersionUID = -8504271904422799318L;

    @ApiModelProperty(value = "列表的id")
    private Long id;

    @ApiModelProperty(value = "库存状态 1可用 2不可用")
    private Integer stockStatus;

    @ApiModelProperty(value = "关键字")
    private String keyword;

}
