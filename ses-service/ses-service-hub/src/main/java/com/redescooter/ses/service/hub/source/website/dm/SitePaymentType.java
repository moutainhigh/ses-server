package com.redescooter.ses.service.hub.source.website.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chris
 * @since 2021-04-28
 */
@ApiModel(value = "支付方式")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(value = Include.NON_NULL)
public class SitePaymentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主建")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    @TableLogic
    private Integer dr;

    @ApiModelProperty(value = "状态,正常/失效")
    private Integer status;

    @ApiModelProperty(value = "支付方式名称")
    private String paymentName;

    @ApiModelProperty(value = "支付方式编码")
    private String paymentCode;

    @ApiModelProperty(value = "其他参数配置")
    private String otherParam;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否同步")
    private Boolean synchronizeFlag;

    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    @ApiModelProperty(value = "冗余字段")
    private String def1;

    @ApiModelProperty(value = "冗余字段")
    private String def2;

    @ApiModelProperty(value = "冗余字段")
    private String def3;

    @ApiModelProperty(value = "冗余字段")
    private String def5;

    @ApiModelProperty(value = "冗余字段")
    private Double def6;

}