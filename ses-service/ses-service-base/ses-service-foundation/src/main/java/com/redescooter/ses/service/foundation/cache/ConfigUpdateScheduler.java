package com.redescooter.ses.service.foundation.cache;


import com.redescooter.ses.api.foundation.vo.common.SysConfigVO;
import com.redescooter.ses.service.foundation.dao.SysConfigVOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ConfigUpdateScheduler {

    public static final long UPDATE_INTERVAL = 1000;
    @Autowired
    private SysConfigVOMapper sysConfigVODao;
    @Autowired
    private ConfigCacheService configCacheService;

    @Scheduled(initialDelay = 3000, fixedDelay = UPDATE_INTERVAL)
    public void scheduleUpdate() {
        long start = System.currentTimeMillis();
        List<SysConfigVO> cs = sysConfigVODao.getAll();
        long dbTime = System.currentTimeMillis();
        log.debug("config update from db success. use time: {}", dbTime - start);
        configCacheService.updateCacheConfigs(cs);

        // 必须把数据库中所有的配置全部更新到本地缓存中后才可以调用此函数
        configCacheService.deleteUnexistConfigs();
        log.debug("config update local cache success. use time: {}", System.currentTimeMillis() - dbTime);
    }
}
