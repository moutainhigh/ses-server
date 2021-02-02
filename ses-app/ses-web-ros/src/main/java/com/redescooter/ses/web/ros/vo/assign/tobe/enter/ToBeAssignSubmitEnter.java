package com.redescooter.ses.web.ros.vo.assign.tobe.enter;

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
 * @Description 填写完R.SN并点击提交入参
 * @Author Chris
 * @Date 2020/12/28 13:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "填写完R.SN并点击提交入参", description = "填写完R.SN并点击提交入参")
public class ToBeAssignSubmitEnter extends GeneralEnter implements Serializable {

    private static final long serialVersionUID = -9200614796762459345L;

    @ApiModelProperty(value = "车辆信息 给json字符串 格式:id:主键,rsn:R.SN,colorId:颜色id", required = true)
    private String list;

    @ApiModelProperty(value = "客户id", required = true)
    private Long customerId;

}
