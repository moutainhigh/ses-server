package com.redescooter.ses.web.ros.vo.website.payment;

import lombok.*;
import lombok.Data;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class Address {

    private String city;
    private String country;
    private String line1;
    private String line2;
    private String postalCode;
    private String state;

}