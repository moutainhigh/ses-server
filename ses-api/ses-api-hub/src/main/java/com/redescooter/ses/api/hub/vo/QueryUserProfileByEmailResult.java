package com.redescooter.ses.api.hub.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameQueryUserProfileByEmailResult
 * @Description
 * @Author Joan
 * @Date2020/8/19 10:50
 * @Version V1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryUserProfileByEmailResult extends GeneralResult {
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value="照片")
    private String picture;
}
