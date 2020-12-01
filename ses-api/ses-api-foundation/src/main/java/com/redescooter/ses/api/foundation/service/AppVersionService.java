package com.redescooter.ses.api.foundation.service;

import com.redescooter.ses.api.foundation.vo.app.AppVersionDTO;

/**
 * 应用版本管理业务接口
 * @author assert
 * @date 2020/11/30 11:53
 */
public interface AppVersionService {

    /**
     * 根据id查询新应用版本信息
     * @param id
     * @return com.redescooter.ses.api.foundation.vo.app.AppVersionDTO
     * @author assert
     * @date 2020/11/30
    */
    AppVersionDTO getNewAppVersionById(Long id);

}
