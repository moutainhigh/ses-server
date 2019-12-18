package com.redescooter.ses.service.foundation.cache;

import com.redescooter.ses.api.foundation.vo.common.SysConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
public class ConfigCacheService {

    public static final String GROUP_KEY_ALL = "*";

    private final ConcurrentMap<String, ConcurrentMap<String, SysConfigVO>> CONFIG_CACHES = new ConcurrentHashMap<>();

    public void updateCacheConfigs(List<SysConfigVO> dbConfigs) {
        for (SysConfigVO c : dbConfigs) {
            c.setDeleted(false);
            updateCacheConfig(c);
        }
    }

    public List<SysConfigVO> get() {
        List<SysConfigVO> ret = new ArrayList<>();
        Iterator<ConcurrentMap<String, SysConfigVO>> iterator = CONFIG_CACHES.values().iterator();
        while (iterator.hasNext()) {
            ConcurrentMap<String, SysConfigVO> next = iterator.next();
            ret.addAll(next.values());
        }
        return ret;
    }

    public List<SysConfigVO> getByGroup(String group) {
        ConcurrentMap<String, SysConfigVO> groupCs = CONFIG_CACHES.get(group);
        List<SysConfigVO> ret = new ArrayList<>(groupCs.values().size());
        ret.addAll(groupCs.values());
        return ret;
    }

    public SysConfigVO getConfig(String group, String key) {
        ConcurrentMap<String, SysConfigVO> concurrentMap = CONFIG_CACHES.get(group);
        if (concurrentMap == null) {
            return null;
        }
        return concurrentMap.get(key);
    }

    /**
     * 必须把数据库中所有的配置全部更新到本地缓存中后才可以调用此函数
     */
    public void deleteUnexistConfigs() {
        for (ConcurrentMap<String, SysConfigVO> groupConfigs : CONFIG_CACHES.values()) {
            Iterator<SysConfigVO> it = groupConfigs.values().iterator();
            while (it.hasNext()) {
                SysConfigVO c = it.next();
                if (c.isDeleted()) {
                    it.remove();
                    log.info("config deleted: {}", c);
                } else {
                    // 重置缓冲中所有的配置项默认都为删除状态，等待下次从数据库更新时，再重置为false
                    c.setDeleted(true);
                }
            }

        }
    }

    private String[] getKeyAndServiceFlag(String keyAndServiceFlag) {
        String[] ret = new String[2];
        int split = keyAndServiceFlag.lastIndexOf("_");
        ret[0] = keyAndServiceFlag.substring(0, split);
        ret[1] = keyAndServiceFlag.substring(split + 1);
        return ret;
    }


    public void updateCacheConfig(SysConfigVO c) {
        String g = c.getGroup();
        String k = c.getKey();
        ConcurrentMap<String, SysConfigVO> groupCache = CONFIG_CACHES.get(g);
        if (groupCache == null) {
            groupCache = new ConcurrentHashMap<>();
            if (CONFIG_CACHES.putIfAbsent(g, groupCache) != null) {
                groupCache = CONFIG_CACHES.get(g);
            }
        }

        SysConfigVO cInCache = groupCache.get(k);
        if (cInCache == null) {
            cInCache = c;
            if (groupCache.putIfAbsent(k, cInCache) == null) {
                log.info("config added: {}", cInCache);
            } else {
                cInCache = groupCache.get(k);
            }
        }
        if (c.getUpdateTime().getTime() > cInCache.getUpdateTime().getTime()) {
            cInCache = c;
            // 高精度下会出现并发问题，出现不能把最新的数据更新到缓存里。
            SysConfigVO old = groupCache.put(k, cInCache);
            log.info("config updated: \nconfig old: {}\nconfig new: {}", old, cInCache);
        }

        // important 这里必须重置删除位
        cInCache.setDeleted(false);
    }


}
