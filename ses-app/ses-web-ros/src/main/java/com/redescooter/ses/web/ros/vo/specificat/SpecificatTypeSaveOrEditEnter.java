package com.redescooter.ses.web.ros.vo.specificat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassNameSpecificatTypeSaveOrEditEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/28 17:30
 * @Version V1.0
 **/
@Data
public class SpecificatTypeSaveOrEditEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键")
    private Long id;

    @TableField(value = "specificat_name")
    @ApiModelProperty(value = "规格名称")
    private String specificatName;

    /**
     * 规格类型编码
     */
    @TableField(value = "specificat_code")
    @ApiModelProperty(value = "规格类型编码")
    private String specificatCode;

    /**
     * 所属分组
     */
    @TableField(value = "group_id")
    @ApiModelProperty(value = "所属分组")
    private Long groupId;

    @ApiModelProperty("自定义项")
    private String st;


}
