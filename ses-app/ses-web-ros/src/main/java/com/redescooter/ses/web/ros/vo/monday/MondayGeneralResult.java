package com.redescooter.ses.web.ros.vo.monday;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.Data;

/**
 * @ClassName:ModdayGeneralResult
 * @description: ModdayGeneralResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/09 16:53
 */
@Data
public class MondayGeneralResult extends GeneralResult {

    private String data;
    private long account_id;
}
