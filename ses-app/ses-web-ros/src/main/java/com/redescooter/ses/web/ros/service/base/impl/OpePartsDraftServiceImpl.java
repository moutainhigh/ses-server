package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePartsDraftMapper;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePartsDraft;
import com.redescooter.ses.web.ros.service.base.OpePartsDraftService;

@Service
public class OpePartsDraftServiceImpl extends ServiceImpl<OpePartsDraftMapper, OpePartsDraft> implements OpePartsDraftService {

    @Override
    public int updateBatch(List<OpePartsDraft> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePartsDraft> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePartsDraft record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePartsDraft record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

