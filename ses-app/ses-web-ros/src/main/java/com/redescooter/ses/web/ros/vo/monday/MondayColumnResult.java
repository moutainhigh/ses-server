package com.redescooter.ses.web.ros.vo.monday;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayColumnResult
 * @description: MondayColumnResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/23 17:42
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MondayColumnResult extends MondayGeneralResult {

    private boolean archived;
    private String pos;
    private String settings_str;
    private String id;
    private String title;
    private String type;
    private int width;
}
