package com.redescooter.ses.web.ros.vo.monday;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:MondayDataResult
 * @description: MondayDataResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/23 18:26
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MondayDataResult{

    //板子列表
    private List<MondayBoardResult> boards;

    //标签列表
    private List<MondayTagResult> tags;

    //创建成功后返回
    private MondayCreateResult create_item;
}
