package com.redescooter.ses.web.ros.vo.specificat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameSpecificatTypeResultEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/28 18:47
 * @Version V1.0
 **/
@Data
public class SpecificatTypeListResult extends GeneralResult {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "规格名称")
    private String specificatName;

    /**
     * 规格类型编码
     */
    @ApiModelProperty(value = "规格类型编码")
    private String specificatCode;

    private Long groupId;


    @ApiModelProperty(value = "所属分组名称")
    private String groupName;


    @ApiModelProperty(value = "创建人名称")
    private String createdName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private String updatedName;

    @ApiModelProperty(value = "修改时间")
    private Date updatedTime;



}
