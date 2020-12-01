package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeProductPriceMapper;
import com.redescooter.ses.web.ros.dm.OpeProductPrice;
import java.util.List;
import com.redescooter.ses.web.ros.service.base.OpeProductPriceService;

@Service
public class OpeProductPriceServiceImpl extends ServiceImpl<OpeProductPriceMapper, OpeProductPrice>
        implements OpeProductPriceService {

    @Override
    public int updateBatch(List<OpeProductPrice> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductPrice> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductPrice record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductPrice record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

