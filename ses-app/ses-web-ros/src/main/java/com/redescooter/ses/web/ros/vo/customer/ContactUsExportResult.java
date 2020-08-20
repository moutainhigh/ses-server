package com.redescooter.ses.web.ros.vo.customer;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameContactUsExportResult
 * @Description
 * @Author Aleks
 * @Date2020/8/11 13:26
 * @Version V1.0
 **/
@Data
public class ContactUsExportResult {

    /**
     * 客户邮箱
     */
    @Excel(name = "客户邮箱")
    private String email;

    /**
     * 名
     */
    @Excel(name = "名")
    private String firstName;

    /**
     * 姓
     */
    @Excel(name = "姓")
    private String lastName;

    /**
     * 全名
     */
    @Excel(name = "全名")
    private String fullName;

    /**
     * 电话
     */
    @ApiModelProperty(value="电话")
    @Excel(name = "合同编号")
    private String telephone;

    /**
     * 国家名称
     */
    @Excel(name = "国家名称")
    private String countryName;

    /**
     * 城市名称
     */
    @Excel(name = "城市名称")
    private String cityName;

    /**
     * 区域编码
     */
    @Excel(name = "区域编码")
    private String districtName;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String address;

    /**
     * 联系次数
     */
    @Excel(name = "联系次数")
    private Integer frequency;

    @Excel(name = "创建时间")
    private Date createTime;

    @Excel(name = "是否邮件回复")
    private String isReply;

}
