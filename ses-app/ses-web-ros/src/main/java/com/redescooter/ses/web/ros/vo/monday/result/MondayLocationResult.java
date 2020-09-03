package com.redescooter.ses.web.ros.vo.monday.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayLocationResult
 * @description: MondayLocationResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/31 14:47
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MondayLocationResult {

    private int line;

    private int column;
}
