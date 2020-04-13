package com.redescooter.ses.service.scooter.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterNavigationMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterNavigation;
import com.redescooter.ses.service.scooter.service.base.ScoScooterNavigationService;
@Service
public class ScoScooterNavigationServiceImpl extends ServiceImpl<ScoScooterNavigationMapper, ScoScooterNavigation> implements ScoScooterNavigationService{

    @Override
    public int updateBatch(List<ScoScooterNavigation> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<ScoScooterNavigation> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(ScoScooterNavigation record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(ScoScooterNavigation record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
