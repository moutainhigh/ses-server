package com.redescooter.ses.service.scooter.dm.base;

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
 * @since 2021-05-26
 */
@ApiModel(value = "平板升级更新记录表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(value = Include.NON_NULL)
public class ScoScooterUpdateRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.UUID)
    private Long id;

    @ApiModelProperty(value = "是否删除 0正常 1删除")
    @TableLogic
    private Integer dr;

    @ApiModelProperty(value = "平板序列号")
    private String tabletSn;

    @ApiModelProperty(value = "更新包下载地址")
    private String downloadLink;

    @ApiModelProperty(value = "版本号")
    private String versionCode;

    @ApiModelProperty(value = "表示这次升级推送的唯一性 用uuid")
    private String updateCode;

    @ApiModelProperty(value = "标识此uuid是否使用 1未使用 2已使用")
    private Integer flag;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @ApiModelProperty(value = "修改人")
    private Long updatedBy;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    @ApiModelProperty(value = "冗余字段1")
    private String def1;

    @ApiModelProperty(value = "冗余字段2")
    private String def2;

    @ApiModelProperty(value = "冗余字段3")
    private String def3;

    @ApiModelProperty(value = "冗余字段4")
    private String def4;

    @ApiModelProperty(value = "冗余字段5")
    private String def5;

}