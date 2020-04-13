package com.redescooter.ses.web.delivery.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.delivery.dao.base.CorDriverScooterHistoryMapper;
import com.redescooter.ses.web.delivery.dm.CorDriverScooterHistory;
import com.redescooter.ses.web.delivery.service.base.CorDriverScooterHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 4/1/2020 8:56 下午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@Service
public class CorDriverScooterHistoryServiceImpl extends ServiceImpl<CorDriverScooterHistoryMapper, CorDriverScooterHistory> implements CorDriverScooterHistoryService {

    @Override
    public int updateBatch(List<CorDriverScooterHistory> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<CorDriverScooterHistory> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorDriverScooterHistory record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorDriverScooterHistory record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
