package com.redescooter.ses.api.foundation.vo.tenant;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassNameQueryAccountBy
 * @Description
 * @Author Joan
 * @Date2020/8/7 15:41
 * @Version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryAccountCountStatusEnter extends GeneralEnter {
  @ApiModelProperty(value = "emailList")
  private List<String> emailList;
}
