package com.redescooter.ses.web.ros.vo.bom.parts;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName PartsTypeResult
 * @Author Jerry
 * @date 2020/02/27 15:50
 * @Description:
 */

@ApiModel(value = "部品类型结果", description = "部品类型结果")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class PartsTypeResult extends GeneralResult {

    /**
     * 主键 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 部品类型名称 部品类型名称
     */
    @ApiModelProperty(value = "部品类型名称")
    private String name;

    /**
     * 部品类型编码 部品类型编码
     */
    @ApiModelProperty(value = "部品类型编码")
    private String code;

    /**
     * 部品类型对应值
     */
    @ApiModelProperty(value = "部品类型编码")
    private int value;
}
