package com.redescooter.ses.service.foundation.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaI18nConfigMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaI18nConfig;
import com.redescooter.ses.service.foundation.service.impl.base.PlaI18nConfigService;
/**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaI18nConfigServiceImpl extends ServiceImpl<PlaI18nConfigMapper, PlaI18nConfig> implements PlaI18nConfigService{

    @Override
    public int updateBatch(List<PlaI18nConfig> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaI18nConfig> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaI18nConfig record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaI18nConfig record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
