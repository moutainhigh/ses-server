package com.redescooter.ses.web.ros.vo.wms.cn.fr;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/28 15:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FrInWhOrderCheckEnter extends GeneralEnter {

    @ApiModelProperty(value = "车辆序列号集合", required = true)
    private List<String> rsnList;

}
