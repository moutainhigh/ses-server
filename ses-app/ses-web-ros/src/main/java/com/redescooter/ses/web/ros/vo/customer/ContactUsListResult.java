package com.redescooter.ses.web.ros.vo.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @ClassNameContactUsListResult
 * @Description
 * @Author Aleks
 * @Date2020/8/4 20:44
 * @Version V1.0
 **/
@ApiModel(value = "联系我们列表返回参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class ContactUsListResult extends GeneralResult {

    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 客户邮箱
     */
    @ApiModelProperty(value="客户邮箱")
    private String email;

    /**
     * 名
     */
    @ApiModelProperty(value="名")
    private String firstName;

    /**
     * 姓
     */
    @ApiModelProperty(value="姓")
    private String lastName;

    /**
     * 全名
     */
    @ApiModelProperty(value="全名")
    private String fullName;

    /**
     * 电话
     */
    @ApiModelProperty(value="电话")
    private String telephone;

    /**
     * 国家名称
     */
    @TableField(value = "country_name")
    @ApiModelProperty(value="国家名称")
    private String countryName;

    /**
     * 城市名称
     */
    @TableField(value = "city_name")
    @ApiModelProperty(value="城市名称")
    private String cityName;

    /**
     * 区域编码
     */
    @TableField(value = "district_name")
    @ApiModelProperty(value="区域编码")
    private String districtName;

    /**
     * 地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value="地址")
    private String address;

    /**
     * 联系次数
     */
    @TableField(value = "frequency")
    @ApiModelProperty(value="联系次数")
    private Integer frequency;

    @ApiModelProperty(value = "创建开始时间")
    private Date createTime;

}
