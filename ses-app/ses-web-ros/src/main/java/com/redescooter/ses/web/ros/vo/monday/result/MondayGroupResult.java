package com.redescooter.ses.web.ros.vo.monday.result;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayGroupResult
 * @description: MondayGroupResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/23 17:41
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MondayGroupResult{

    private String archived;
    private String color;
    private String deleted;
    private String id;
    private String position;
    private String title;
}
