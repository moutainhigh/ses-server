package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeProductionPartPriceSheet;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeProductionPartPriceSheetMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartPriceSheetService;

@Service
public class OpeProductionPartPriceSheetServiceImpl
    extends ServiceImpl<OpeProductionPartPriceSheetMapper, OpeProductionPartPriceSheet>
    implements OpeProductionPartPriceSheetService {

    @Override
    public int updateBatch(List<OpeProductionPartPriceSheet> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionPartPriceSheet> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionPartPriceSheet record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionPartPriceSheet record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
