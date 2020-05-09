package com.redescooter.ses.api.common.enums.jiguang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * description: PushTypeEnum 推送目标
 * 推送设备对象，表示一条推送可以被推送到哪些设备列表。确认推送设备对象，
 * JPush 提供了多种方式，比如：别名、标签、注册 ID、分群、广播等。
 * author: jerry.li
 * create: 2019-05-21 10:01
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PushTypeEnum {

    /**
     * 用标签来进行大规模的设备属性、用户属性分群。 一次推送最多 20 个。
     * 有效的 tag 组成：字母（区分大小写）、数字、下划线、汉字、特殊字符@!#$&*+=.|￥。
     * 限制：每一个 tag 的长度限制为 40 字节。（判断长度需采用 UTF-8 编码）
     */
    TAG(1, "TAG", "标签"),
    /**
     * 数组。多个标签之间是 AND 关系，即取交集。
     * 注意与 tag 区分。一次推送最多 20 个。
     */
    TAG_AND(2, "TAG_AND", "标签交集"),

    /**
     * 数组。多个标签之间，先取多标签的并集，再对该结果取补集。
     * 一次推送最多 20 个。
     */
    TAG_NOT(3, "TAG_NOT", "标签补集"),

    /**
     * 用别名来标识一个用户。一个设备只能绑定一个别名，但多个设备可以绑定同一个别名。一次推送最多 1000 个。
     * 有效的 alias 组成：字母（区分大小写）、数字、下划线、汉字、特殊字符@!#$&*+=.|￥。
     * 限制：每一个 alias 的长度限制为 40 字节。（判断长度需采用 UTF-8 编码）
     */

    ALIAS(4, "ALIAS", "别名"),

    /**
     * 设备标识。一次推送最多 1000 个。客户端集成 SDK 后可获取到该值。
     */
    REGISTRATION_ID(5, "REGISTRATION_ID", "设备标识"),

    /**
     * 在页面创建的用户分群的 ID。定义为数组，但目前限制一次只能推送一个。
     * 目前限制是一次只能推送一个。
     */
    SEGMENT(6, "SEGMENT", "分组ID"),

    /**
     * 在页面创建的 A/B 测试的 ID。定义为数组，但目前限制是一次只能推送一个。
     */
    ABTEST(7, "ABTEST", "页面ID"),
    ;

    private int value;

    private String code;

    private String name;


}
