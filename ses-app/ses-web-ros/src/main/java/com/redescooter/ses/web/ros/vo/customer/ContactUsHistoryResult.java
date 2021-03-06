package com.redescooter.ses.web.ros.vo.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @ClassNameContactUshistoryResult
 * @Description
 * @Author Joan
 * @Date2020/8/5 10:58
 * @Version V1.0
 **/
@ApiModel(value = "联系我们历史返回参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class ContactUsHistoryResult extends GeneralResult {

  @ApiModelProperty(value="主键")
  private Long id;

  @ApiModelProperty(value="客户邮箱")
  private String email;

  @ApiModelProperty(value="名")
  private String firstName;

  @ApiModelProperty(value="姓")
  private String lastName;

  @ApiModelProperty(value="全名")
  private String fullName;


  @ApiModelProperty(value="电话")
  private String telephone;


  @ApiModelProperty(value="国家名称")
  private String countryName;


  @ApiModelProperty(value="城市名称")
  private String cityName;


  @ApiModelProperty(value="区域编码")
  private String districtName;

  @ApiModelProperty(value="地址")
  private String address;

  @ApiModelProperty(value = "留言")
  private String remark;

  @ApiModelProperty(value = "回复集合")
  private List<ContactUsHistoryReplyResult> replyList;

  @ApiModelProperty(value = "留言创建开始时间")
  private Date createdTime;

}
