package com.redescooter.ses.api.foundation.service;

import com.redescooter.ses.api.foundation.vo.app.AppVersionUpdateLogDTO;
import com.redescooter.ses.api.foundation.vo.app.AppVersionUpdateLogDetailDTO;

import java.util.List;

/**
 * 应用版本升级记录业务接口
 * @author assert
 * @date 2020/12/27 17:25
 */
public interface AppVersionUpdateLogService {

    /**
     * 批量新增应用版本升级日志记录
     * @param appVersionUpdateLogList
     * @return int
     * @author assert
     * @date 2020/12/27
     */
    int batchInsertAppVersionUpdateLog(List<AppVersionUpdateLogDTO> appVersionUpdateLogList);

    /**
     * 批量修改车载平板升级状态为 “已成功”
     * @param tabletSnList
     * @return int
     * @author assert
     * @date 2020/12/27
    */
    int batchUpdateAppVersionUpdateLogStatus(List<String> tabletSnList);

    /**
     * 查询车载平板更新失败记录
     * @return java.util.List<com.redescooter.ses.api.foundation.vo.app.AppVersionUpdateLogDetailDTO>
     * @author assert
     * @date 2020/12/27
    */
    List<AppVersionUpdateLogDetailDTO> getAppVersionUpdateFailLog();

}
