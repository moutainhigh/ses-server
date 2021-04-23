package com.redescooter.ses.service.foundation.service.impl;

import com.redescooter.ses.api.foundation.service.AppVersionUpdateLogService;
import com.redescooter.ses.api.foundation.vo.app.AppVersionUpdateLogDTO;
import com.redescooter.ses.api.foundation.vo.app.AppVersionUpdateLogDetailDTO;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.AppVersionUpdateLogMapper;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author assert
 * @date 2020/12/27 17:27
 */
@DubboService
public class AppVersionUpdateLogServiceImpl implements AppVersionUpdateLogService {

    @Resource
    private AppVersionUpdateLogMapper appVersionUpdateLogMapper;

    @DubboReference
    private IdAppService idAppService;

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int batchInsertAppVersionUpdateLog(List<AppVersionUpdateLogDTO> appVersionUpdateLogList) {
        // 设置id、createdTime、updatedTime
        appVersionUpdateLogList.forEach(appVersionUpdateLog -> {
            appVersionUpdateLog.setId(idAppService.getId(SequenceName.PLA_APP_VERSION_UPDATE_LOG));
            appVersionUpdateLog.setCreatedTime(new Date());
            appVersionUpdateLog.setUpdatedTime(new Date());
        });

        return appVersionUpdateLogMapper.batchInsertAppVersionUpdateLog(appVersionUpdateLogList);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateAppVersionUpdateLogStatus(List<String> tabletSnList) {
        return appVersionUpdateLogMapper.batchUpdateAppVersionUpdateLogStatus(tabletSnList, new Date());
    }

    @Override
    public List<AppVersionUpdateLogDetailDTO> getAppVersionUpdateFailLog() {
        return appVersionUpdateLogMapper.getAppVersionUpdateFailLog();
    }

}
