package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeInWhousePartsB;
import com.redescooter.ses.web.ros.dao.base.OpeInWhousePartsBMapper;
import com.redescooter.ses.web.ros.service.base.OpeInWhousePartsBService;

@Service
public class OpeInWhousePartsBServiceImpl extends ServiceImpl<OpeInWhousePartsBMapper, OpeInWhousePartsB> implements OpeInWhousePartsBService {

    @Override
    public int updateBatch(List<OpeInWhousePartsB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInWhousePartsB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInWhousePartsB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInWhousePartsB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

