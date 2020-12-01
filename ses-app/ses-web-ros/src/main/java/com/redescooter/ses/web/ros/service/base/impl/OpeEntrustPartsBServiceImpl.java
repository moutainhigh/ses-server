package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeEntrustPartsBMapper;
import com.redescooter.ses.web.ros.dm.OpeEntrustPartsB;
import com.redescooter.ses.web.ros.service.base.OpeEntrustPartsBService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OpeEntrustPartsBServiceImpl extends ServiceImpl<OpeEntrustPartsBMapper, OpeEntrustPartsB> implements OpeEntrustPartsBService{

    @Override
    public int updateBatch(List<OpeEntrustPartsB> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeEntrustPartsB> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeEntrustPartsB record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeEntrustPartsB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
