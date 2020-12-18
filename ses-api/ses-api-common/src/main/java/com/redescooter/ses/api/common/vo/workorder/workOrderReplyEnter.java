package com.redescooter.ses.api.common.vo.workorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassNameworkOrderReplyEnter
 * @Description
 * @Author Aleks
 * @Date2020/12/4 17:24
 * @Version V1.0
 **/
@Data
public class workOrderReplyEnter extends GeneralEnter {

    @ApiModelProperty("工单id")
    private Long id;

    @ApiModelProperty("回复的内容")
    private String remark;

    @ApiModelProperty("消息类型 1：留言， 2：回复")
    private  Integer messageType;

    @ApiModelProperty("图片地址，用','隔开")
    private String urls;

    @ApiModelProperty("是否是图片,发送图片")
    private Boolean isPhotos = false;
}
