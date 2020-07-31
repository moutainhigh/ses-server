package com.redescooter.ses.web.ros.enums.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayColumnGeneralEnums
 * @description: 这个文件 主要存放 类型只有一个字段的数据类型
 * 详情参考 https://monday.com/developers/v2#column-values-section-check
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/31 14:11
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayColumnGeneralEnums {

    IDS("ids","多个Id"),
    LABELS("labels","多标签"),
    TEXT("text","文本"),
    ID("id","id"),
    RATING("rating","评级范围"),
    TAG_IDS("tag_ids","标签ids"),
    TEAM_ID("team_id","团队Id"),
    TIMEZONE("timezone","时区"),
    ITEM_IDS("item_ids","条目Id"),
    CLEAR_ALL("clear_all","是否清除全部"),
    ;

    private String code;

    private String message;

}
