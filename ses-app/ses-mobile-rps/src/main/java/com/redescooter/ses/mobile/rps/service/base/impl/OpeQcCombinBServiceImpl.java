package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeQcCombinBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcCombinB;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeQcCombinBService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/26 15:38
 */
@Service
public class OpeQcCombinBServiceImpl extends ServiceImpl<OpeQcCombinBMapper, OpeQcCombinB>
        implements OpeQcCombinBService {

    @Override
    public int updateBatch(List<OpeQcCombinB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeQcCombinB> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeQcCombinB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeQcCombinB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeQcCombinB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


