package com.redescooter.ses.web.ros.vo.codebase;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 11:34
 */
@Data
@ApiModel(value = "VIN详情出参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VINDetailResult extends GeneralResult {
}
