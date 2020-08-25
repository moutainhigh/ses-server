package com.redescooter.ses.web.ros.vo.sellsy.enter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SellsyCreateClientContactEnter
 * @description: SellsyCreateClientContactEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 09:51
 */
@ApiModel(value = "客户创建联系人入参", description = "客户创建联系人入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCreateClientContactEnter {
    
    @ApiModelProperty(value = "名字",required = true)
    private String name;
    
    @ApiModelProperty(value = "联系名字",required = true)
    private String forename;
    
    @ApiModelProperty(value = "电话")
    private String tel;
    
    @ApiModelProperty(value = "传真")
    private String fax;
    
    @ApiModelProperty(value = "手机")
    private String mobile;
    
    @ApiModelProperty(value = "网址")
    private String web;
    
    @ApiModelProperty(value = "职位")
    private String position;
    
    @ApiModelProperty(value = "身份",allowableValues = "man,woman,lady")
    private String civil;
    
    @ApiModelProperty(value = "生日 Timestamp格式")
    private String birthdate;
    
    @ApiModelProperty(value = "启用Enabling SimpleMail  同步",allowableValues = "Y,N")
    private String smoptin;
    
    @ApiModelProperty(value = "Enabling MailChimp 同步",allowableValues = "Y,N")
    private String mcoptin;
    
    @ApiModelProperty(value = "Enabling MailJet 同步",allowableValues = "Y,N")
    private String mjoptin;
    
}
