package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeProductionPurchasePartsB;
import com.redescooter.ses.web.ros.dao.base.OpeProductionPurchasePartsBMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductionPurchasePartsBService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class OpeProductionPurchasePartsBServiceImpl extends ServiceImpl<OpeProductionPurchasePartsBMapper, OpeProductionPurchasePartsB> implements OpeProductionPurchasePartsBService {

    @Override
    public int updateBatch(List<OpeProductionPurchasePartsB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionPurchasePartsB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionPurchasePartsB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionPurchasePartsB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

