package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeInWhouseCombinB;
import com.redescooter.ses.web.ros.dao.base.OpeInWhouseCombinBMapper;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseCombinBService;

@Service
public class OpeInWhouseCombinBServiceImpl extends ServiceImpl<OpeInWhouseCombinBMapper, OpeInWhouseCombinB> implements OpeInWhouseCombinBService {

    @Override
    public int updateBatch(List<OpeInWhouseCombinB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInWhouseCombinB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInWhouseCombinB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInWhouseCombinB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

