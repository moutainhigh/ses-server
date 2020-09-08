package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.SellsyExceptionMapper;
import com.redescooter.ses.web.ros.dm.SellsyException;
import com.redescooter.ses.web.ros.service.base.SellsyExceptionService;

@Service
public class SellsyExceptionServiceImpl extends ServiceImpl<SellsyExceptionMapper, SellsyException>
    implements SellsyExceptionService {

    @Override
    public int updateBatch(List<SellsyException> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<SellsyException> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SellsyException record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SellsyException record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
