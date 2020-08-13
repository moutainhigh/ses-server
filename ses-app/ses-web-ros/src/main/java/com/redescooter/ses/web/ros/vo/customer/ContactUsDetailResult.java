package com.redescooter.ses.web.ros.vo.customer;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @ClassNameContactUsDetailResult
 * @Description
 * @Author Joan
 * @Date2020/8/5 10:47
 * @Version V1.0
 **/
@ApiModel(value = "联系我们详情返回参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class ContactUsDetailResult extends GeneralResult {
  @ApiModelProperty(value="主键")
  private Long id;

  @ApiModelProperty(value = "创建开始时间")
  private Date createTime;

  @ApiModelProperty(value = "备注")
  private String remark;

  @ApiModelProperty(value = "留言回复类型:1.留言 2.回复")
  private String messageType;
}
