package com.redescooter.ses.api.hub.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassNameUserProfileEnter
 * @Description
 * @Author Joan
 * @Date2020/8/18 19:24
 * @Version V1.0
 **/
@ApiModel(value = "个人信息", description = "个人信息")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryUserProfileByEmailEnter extends GeneralEnter {

    @ApiModelProperty(value = "email")
    List<String> email;
}
