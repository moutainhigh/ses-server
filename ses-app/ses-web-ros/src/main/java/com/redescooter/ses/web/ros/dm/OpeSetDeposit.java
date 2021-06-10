package com.redescooter.ses.web.ros.dm;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Courtney
 * @since 2021-06-10
 */
@ApiModel(value = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(value = Include.NON_NULL)
public class OpeSetDeposit {

  private static final long serialVersionUID = 1L;

   @ApiModelProperty(value = "id")
   @TableId(value = "id", type = IdType.AUTO)
  private Long id;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer dr;

    @ApiModelProperty(value = "定金")
   private BigDecimal deposit;

}