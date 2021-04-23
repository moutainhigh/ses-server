package com.redescooter.ses.api.hub.vo.website;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 同步销售产品的对象类
 * @author: Aleks
 * @create: 2021/03/16 15:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SyncProductionDataEnter extends GeneralEnter {

    @ApiModelProperty(value="状态 up上架，down下架(默认)")
    private Integer status = 1;

    @ApiModelProperty(value="产品类型 如1整车，2组装套件，电池")
    private Integer productType;

    @ApiModelProperty(value="产品编码")
    private String productCode;

    @ApiModelProperty(value="中文名称")
    private String cnName;

    @ApiModelProperty(value="法文名称")
    private String frName;

    @ApiModelProperty(value="英文名称")
    private String enName;

    @ApiModelProperty(value="产品型号ID")
    private Long productModelId;

    @ApiModelProperty(value="图片")
    private String picture;

    @ApiModelProperty(value="最少电池数")
    private Integer minBatteryNum;

    @ApiModelProperty(value="产品参数 存储JSON")
    private String materParameter;

    @ApiModelProperty(value="其他参数 存储JSON")
    private String otherParameter;

    @ApiModelProperty(value="速度")
    private String speed;

    private String power;

    @ApiModelProperty(value="续航里程")
    private String mileage;

    @ApiModelProperty(value="充电周期")
    private String chargeCycle;

    @ApiModelProperty(value="备注")
    private String remark;

    // *********以上是 site_product 表需要的字段************

    @ApiModelProperty(value="颜色名称")
    private String colourName;

    @ApiModelProperty(value="颜色编码")
    private String colourCode;
    // *********以上是 site_colour 表需要的字段************

    @ApiModelProperty(value="产品种类名称(高速低速)")
    private String productClassName;

    @ApiModelProperty(value="产品种类编码")
    private String productClassCode;
    // *********以上是 site_product_class 表需要的字段************

    @ApiModelProperty(value="产品种类名称(高速低速)")
    private String productModelCode;

    @ApiModelProperty(value="产品种类编码")
    private String productModelName;
    // *********以上是 site_product_class 表需要的字段************
}
