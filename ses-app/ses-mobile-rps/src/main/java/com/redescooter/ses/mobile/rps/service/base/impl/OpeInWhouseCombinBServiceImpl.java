package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeInWhouseCombinBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseCombinB;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhouseCombinBService;import java.util.List;

/**
 * @author assert
 * @date 2021/1/13 16:13
 */
@Service
public class OpeInWhouseCombinBServiceImpl extends ServiceImpl<OpeInWhouseCombinBMapper, OpeInWhouseCombinB>
        implements OpeInWhouseCombinBService {

    @Override
    public int updateBatch(List<OpeInWhouseCombinB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeInWhouseCombinB> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeInWhouseCombinB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInWhouseCombinB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInWhouseCombinB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}






