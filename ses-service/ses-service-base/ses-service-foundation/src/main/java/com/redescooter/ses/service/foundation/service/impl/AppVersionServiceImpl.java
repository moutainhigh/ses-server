package com.redescooter.ses.service.foundation.service.impl;

import com.redescooter.ses.api.foundation.service.AppVersionService;
import com.redescooter.ses.api.foundation.vo.app.AppVersionDTO;
import com.redescooter.ses.service.foundation.dao.AppVersionMapper;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author assert
 * @date 2020/11/30 11:54
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Resource
    private AppVersionMapper appVersionMapper;

    @Override
    public AppVersionDTO getNewAppVersionById(Long id) {
        return appVersionMapper.getNewAppVersionById(id);
    }

    @Override
    public List<AppVersionDTO> queryAppVersion() {
        return null;
    }

}
