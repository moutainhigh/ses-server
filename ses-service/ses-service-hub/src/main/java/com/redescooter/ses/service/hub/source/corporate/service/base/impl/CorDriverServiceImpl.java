package com.redescooter.ses.service.hub.source.corporate.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.corporate.dao.CorDriverMapper;
import com.redescooter.ses.service.hub.source.corporate.dm.CorDriver;
import com.redescooter.ses.service.hub.source.corporate.service.base.CorDriverService;
/**
 * @author      Mr.lijiating
 * @Date:       23/12/2019 11:20 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */
@DS("corporate")
@Service
public class CorDriverServiceImpl extends ServiceImpl<CorDriverMapper, CorDriver> implements CorDriverService{

    @Override
    public int updateBatch(List<CorDriver> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<CorDriver> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(CorDriver record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(CorDriver record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
