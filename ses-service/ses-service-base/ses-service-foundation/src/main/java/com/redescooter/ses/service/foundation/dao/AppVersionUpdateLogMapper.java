package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.foundation.vo.app.AppVersionUpdateLogDTO;
import com.redescooter.ses.api.foundation.vo.app.AppVersionUpdateLogDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 应用版本升级记录 Mapper接口
 * @author assert
 * @date 2020/12/27 15:47
 */
public interface AppVersionUpdateLogMapper {

    /**
     * 批量新增应用版本升级日志记录
     * @param appVersionUpdateLogList
     * @return int
     * @author assert
     * @date 2020/12/27
     */
    int batchInsertAppVersionUpdateLog(@Param("appVersionUpdateLogList") List<AppVersionUpdateLogDTO> appVersionUpdateLogList);

    /**
     * 批量修改车载平板升级状态为 “已成功”
     * @param tabletSnList
     * @param currentTime
     * @return int
     * @author assert
     * @date 2020/12/27
    */
    int batchUpdateAppVersionUpdateLogStatus(@Param("tabletSnList") List<String> tabletSnList, @Param("currentTime") Date currentTime);

    /**
     * 查询车载平板更新失败记录
     * @return java.util.List<com.redescooter.ses.api.foundation.vo.app.AppVersionUpdateLogDetailDTO>
     * @author assert
     * @date 2020/12/27
     */
    List<AppVersionUpdateLogDetailDTO> getAppVersionUpdateFailLog();

}
