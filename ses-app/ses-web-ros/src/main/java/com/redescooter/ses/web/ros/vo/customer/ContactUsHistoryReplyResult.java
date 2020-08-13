package com.redescooter.ses.web.ros.vo.customer;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @ClassNameContactUsHistoryReplyResult
 * @Description
 * @Author Joan
 * @Date2020/8/13 16:54
 * @Version V1.0
 **/
@ApiModel(value = "联系我们历史返回参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class ContactUsHistoryReplyResult extends GeneralResult {

    private Long id;

    private String reply;

    private Date replyCreatedTime;
}
