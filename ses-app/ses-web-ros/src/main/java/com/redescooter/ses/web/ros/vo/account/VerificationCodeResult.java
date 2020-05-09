package com.redescooter.ses.web.ros.vo.account;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:VerificationCodeReslt
 * @description: VerificationCodeReslt
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/24 14:15
 */
@ApiModel(value = "验证码出参", description = "验证码出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class VerificationCodeResult extends GeneralResult {

    @ApiModelProperty(value = "")
    private String base64Img;
}
