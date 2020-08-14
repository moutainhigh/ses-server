package com.redescooter.ses.web.ros.vo.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassNameContactUsListEnter
 * @Description
 * @Author Aleks
 * @Date2020/8/4 20:38
 * @Version V1.0
 **/
@ApiModel(value = "联系我们列表查询传参")
@Data
@EqualsAndHashCode(callSuper = false)
public class ContactUsListEnter extends PageEnter {

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("区域编码")
    private String districtName;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "创建开始时间")
    private Date createStartDateTime;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "创建结束时间")
    private Date createEndDateTime;

    @ApiModelProperty("关键字查询")
    private String keyWord;
}
