package com.redescooter.ses.web.ros.vo.monday.enter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayMutationColumnEnter
 * @description: MondayMutationColumnEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/31 17:03
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MondayMutationColumnEnter {

    private int boardId;

    private String title;

    private String columnType;

    private String defaults;
}
