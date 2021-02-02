package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeQcPartsBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcPartsB;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeQcPartsBService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/26 15:38
 */
@Service
public class OpeQcPartsBServiceImpl extends ServiceImpl<OpeQcPartsBMapper, OpeQcPartsB>
        implements OpeQcPartsBService {

    @Override
    public int updateBatch(List<OpeQcPartsB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeQcPartsB> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeQcPartsB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeQcPartsB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeQcPartsB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


