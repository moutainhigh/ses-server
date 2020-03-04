package com.redescooter.ses.web.ros.service.impl.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePartsProductMapper;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpePartsProductServiceImpl extends ServiceImpl<OpePartsProductMapper, OpePartsProduct> implements OpePartsProductService {

    @Override
    public int updateBatch(List<OpePartsProduct> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePartsProduct> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePartsProduct record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePartsProduct record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

