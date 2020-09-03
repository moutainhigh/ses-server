package com.redescooter.ses.web.ros.vo.monday.result;

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

    //存放正常执行后的数据
    private MondayDataResult data;

    //存放错误信息
    private MondayErrorResult errors;

    private String error_message;

    private int status_code;


    //是指那个账户执行的账户Id
    private long account_id;
}
