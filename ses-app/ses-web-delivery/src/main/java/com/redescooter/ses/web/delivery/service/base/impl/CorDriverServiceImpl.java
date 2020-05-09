package com.redescooter.ses.web.delivery.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.delivery.dao.base.CorDriverMapper;
import com.redescooter.ses.web.delivery.dm.CorDriver;
import com.redescooter.ses.web.delivery.service.base.CorDriverService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 31/12/2019 8:31 上午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@Service
public class CorDriverServiceImpl extends ServiceImpl<CorDriverMapper, CorDriver> implements CorDriverService {

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
