package com.redescooter.ses.web.delivery.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.delivery.dao.base.CorDriverScooterMapper;
import com.redescooter.ses.web.delivery.dm.CorDriverScooter;
import com.redescooter.ses.web.delivery.service.base.CorDriverScooterService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 11:22 上午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@Service
public class CorDriverScooterServiceImpl extends ServiceImpl<CorDriverScooterMapper, CorDriverScooter> implements CorDriverScooterService {

    @Override
    public int updateBatch(List<CorDriverScooter> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<CorDriverScooter> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorDriverScooter record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorDriverScooter record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
