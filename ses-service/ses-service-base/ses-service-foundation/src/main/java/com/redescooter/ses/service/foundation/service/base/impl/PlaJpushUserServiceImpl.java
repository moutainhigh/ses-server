package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaJpushUserMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaJpushUser;
import com.redescooter.ses.service.foundation.service.base.PlaJpushUserService;

@Service
public class PlaJpushUserServiceImpl extends ServiceImpl<PlaJpushUserMapper, PlaJpushUser> implements PlaJpushUserService {

    @Override
    public int updateBatch(List<PlaJpushUser> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<PlaJpushUser> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(PlaJpushUser record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(PlaJpushUser record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int updateBatchSelective(List<PlaJpushUser> list) {
        return baseMapper.updateBatchSelective(list);
    }
}

