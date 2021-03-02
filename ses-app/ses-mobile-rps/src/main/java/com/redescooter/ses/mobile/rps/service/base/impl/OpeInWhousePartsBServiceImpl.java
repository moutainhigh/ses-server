package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeInWhousePartsBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB;import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhousePartsBService;import java.util.List;

/**
 * @author assert
 * @date 2021/1/13 16:13
 */
@Service
public class OpeInWhousePartsBServiceImpl extends ServiceImpl<OpeInWhousePartsBMapper, OpeInWhousePartsB> implements OpeInWhousePartsBService {

    @Override
    public int updateBatch(List<OpeInWhousePartsB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeInWhousePartsB> list) {
        return baseMapper.updateBatchSelective(list);
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






