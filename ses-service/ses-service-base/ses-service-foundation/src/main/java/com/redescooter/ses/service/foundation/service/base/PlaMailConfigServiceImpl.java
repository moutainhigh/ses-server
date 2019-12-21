package com.redescooter.ses.service.foundation.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaMailConfigMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMailConfig;
import com.redescooter.ses.service.foundation.service.impl.base.PlaMailConfigService;
/**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaMailConfigServiceImpl extends ServiceImpl<PlaMailConfigMapper, PlaMailConfig> implements PlaMailConfigService{

    @Override
    public int updateBatch(List<PlaMailConfig> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaMailConfig> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaMailConfig record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaMailConfig record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
