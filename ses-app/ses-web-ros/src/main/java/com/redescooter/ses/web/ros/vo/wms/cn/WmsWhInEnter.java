package com.redescooter.ses.web.ros.vo.wms.cn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassNameWmsWhInEnter
 * @Description
 * @Author Joan
 * @Date2020/7/17 16:03
 * @Version V1.0
 **/
@ApiModel(value = "仓储待生产出参", description = "仓储待生产出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WmsWhInEnter extends PageEnter {

  @ApiModelProperty(value = "开始时间", required = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
  private Date startTime;

  @ApiModelProperty(value = "结束时间", required = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
  private Date endTime;

  @ApiModelProperty(value = "关键字,调拨编号、组装单号、委托人")
  private  String keyword;
}
