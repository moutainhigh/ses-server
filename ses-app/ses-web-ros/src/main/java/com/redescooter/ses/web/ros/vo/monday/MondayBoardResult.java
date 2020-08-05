package com.redescooter.ses.web.ros.vo.monday;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:MondayBoardResult
 * @description: MondayBoardResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/23 17:34
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MondayBoardResult{

    private String id;

    private String name;

    private int board_folder_id;

    private String board_kind;

    private String communication;

    private String description;

    //分组名称
    private List<MondayGroupResult> groups;

    //列名集合
    private List<MondayColumnResult> columns;
}
