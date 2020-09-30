package com.redescooter.ses.web.ros.vo.specificat;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSpecificatGroupDataResult
 * @Description
 * @Author Aleks
 * @Date2020/9/29 10:52
 * @Version V1.0
 **/
@Data
public class SpecificatGroupDataResult  {

    @ApiModelProperty("分组id")
    private Long groupId;

    @ApiModelProperty(value="分组名称")
    private String groupName;

    /**
     * 产品类型，1：整车，2：组装
     */
    @ApiModelProperty(value="产品类型，1：整车，2：组装")
    private Integer productionType;


}
