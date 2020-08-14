package com.redescooter.ses.web.ros.vo.monday.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:MondayErrorResult
 * @description: MondayErrorResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/31 14:48
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MondayErrorResult {

    //异常信息
    private String message;

    //指异常位置
    private List<MondayLocationResult> locations;

    //指那些字段
    private List<String> fields;
}
