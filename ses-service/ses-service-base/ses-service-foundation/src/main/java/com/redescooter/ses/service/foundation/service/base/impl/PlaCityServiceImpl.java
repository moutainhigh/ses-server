package com.redescooter.ses.service.foundation.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dao.base.PlaCityMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaCity;
import com.redescooter.ses.service.foundation.service.base.PlaCityService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author      Mr.lijiating
 * @Date:       24/12/2019 12:11 上午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaCityServiceImpl extends ServiceImpl<PlaCityMapper, PlaCity> implements PlaCityService {

    @Override
    public int updateBatch(List<PlaCity> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaCity> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaCity record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaCity record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
