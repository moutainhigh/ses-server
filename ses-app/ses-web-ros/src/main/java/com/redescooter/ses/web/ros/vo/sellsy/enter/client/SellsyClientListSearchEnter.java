package com.redescooter.ses.web.ros.vo.sellsy.enter.client;

import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel(value = "客户列表", description = "客户列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyClientListSearchEnter {
//    //客户类型
//private SellsyClientTypeEnums type;
//
//    //	search in the name/forename/email
//    private String contains;
//
//    //电话
//    private String containstel;
//
//    //名字
private String name;
//
//    //
//    private Integer ident;
//
//    //邮箱
//    private String email;
//
//    //网址
//    private String web;
//
//    //标签
//    private String tags;
//
//    //创建开始时间
//    private Timestamp periodecreated_start;
//
//    //创建结束时间
//    private Timestamp periodecreated_end;
//
//    private String containssiret;
//
//    //包含
//    private String containssiren;
//
//    //
//    private SellsyBooleanEnums actif;
}
