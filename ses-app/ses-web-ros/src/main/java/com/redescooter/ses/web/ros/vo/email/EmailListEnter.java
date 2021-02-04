package com.redescooter.ses.web.ros.vo.email;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
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
 * @Date 2021/2/4 14:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class EmailListEnter extends GeneralEnter implements Serializable {

    private static final long serialVersionUID = -3691079465510708860L;

    @ApiModelProperty(value = "值")
    private String keyword;

    @ApiModelProperty(value = "状态")
    private String status;

}
