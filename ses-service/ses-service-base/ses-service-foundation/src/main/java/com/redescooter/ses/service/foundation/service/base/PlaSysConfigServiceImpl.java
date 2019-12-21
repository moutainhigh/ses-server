package com.redescooter.ses.service.foundation.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaSysConfig;
import com.redescooter.ses.service.foundation.dao.base.PlaSysConfigMapper;
import com.redescooter.ses.service.foundation.service.impl.base.PlaSysConfigService;
/**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaSysConfigServiceImpl extends ServiceImpl<PlaSysConfigMapper, PlaSysConfig> implements PlaSysConfigService{

    @Override
    public int updateBatch(List<PlaSysConfig> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaSysConfig> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaSysConfig record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaSysConfig record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
