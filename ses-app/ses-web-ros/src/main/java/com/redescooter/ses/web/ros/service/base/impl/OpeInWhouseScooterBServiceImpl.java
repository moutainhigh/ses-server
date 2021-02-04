package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeInWhouseScooterB;
import com.redescooter.ses.web.ros.dao.base.OpeInWhouseScooterBMapper;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseScooterBService;

@Service
public class OpeInWhouseScooterBServiceImpl extends ServiceImpl<OpeInWhouseScooterBMapper, OpeInWhouseScooterB> implements OpeInWhouseScooterBService {

    @Override
    public int updateBatch(List<OpeInWhouseScooterB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInWhouseScooterB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInWhouseScooterB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInWhouseScooterB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

