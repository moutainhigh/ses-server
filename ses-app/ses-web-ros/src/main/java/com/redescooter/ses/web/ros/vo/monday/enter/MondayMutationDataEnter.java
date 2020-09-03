package com.redescooter.ses.web.ros.vo.monday.enter;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaveContactUsDateEnter
 * @description: SaveContactUsDateEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/23 19:34
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MondayMutationDataEnter extends GeneralEnter {

    private String boardId;

    private String groupId;

    private String itemName;

    private String columnValues;
}
