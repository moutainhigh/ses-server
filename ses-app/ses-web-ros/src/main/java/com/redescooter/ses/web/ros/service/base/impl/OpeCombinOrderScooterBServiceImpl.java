package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeCombinOrderScooterB;
import com.redescooter.ses.web.ros.dao.base.OpeCombinOrderScooterBMapper;
import java.util.List;
import com.redescooter.ses.web.ros.service.base.OpeCombinOrderScooterBService;

@Service
public class OpeCombinOrderScooterBServiceImpl extends ServiceImpl<OpeCombinOrderScooterBMapper, OpeCombinOrderScooterB> implements OpeCombinOrderScooterBService {

    @Override
    public int updateBatch(List<OpeCombinOrderScooterB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeCombinOrderScooterB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeCombinOrderScooterB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeCombinOrderScooterB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


