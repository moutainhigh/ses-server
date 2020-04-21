package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpePartsProduct;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpePartsProductMapper;
import com.redescooter.ses.mobile.rps.service.base.OpePartsProductService;

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





