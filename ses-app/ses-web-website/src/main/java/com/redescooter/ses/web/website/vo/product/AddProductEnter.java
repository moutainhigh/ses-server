package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 1:21 上午
 * @Description 新增产品服务入参
 **/
@ApiModel(value = "新增产品服务入参", description = "新增产品服务入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddProductEnter extends GeneralEnter {

    /**
     * 中文名称
     */
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    /**
     * 法文名称
     */
    @ApiModelProperty(value = "法文名称")
    private String frName;

    /**
     * 英文名称
     */
    @ApiModelProperty(value = "英文名称")
    private String enName;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String picture;

    /**
     * 产品型号ID
     */
    @ApiModelProperty(value = "产品型号ID")
    private Long productModelId;

    /**
     * 最少电池数
     */
    @ApiModelProperty(value = "最少电池数")
    private Integer minBatteryNum;

    /**
     * 产品参数 存储JSON
     */
    @ApiModelProperty(value = "产品参数 存储JSON")
    private String materParameter;

    /**
     * 其他参数 存储JSON
     */
    @ApiModelProperty(value = "其他参数 存储JSON")
    private String otherParameter;

    /**
     * 速度
     */
    @ApiModelProperty(value = "速度")
    private String speed;

    /**
     * 功率
     */
    @ApiModelProperty(value = "功率")
    private String power;

    /**
     * 续航里程
     */
    @ApiModelProperty(value = "续航里程")
    private String mileage;

    /**
     * 充电周期
     */
    @ApiModelProperty(value = "充电周期")
    private String chargeCycle;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
