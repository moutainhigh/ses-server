package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPassword;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaUserPasswordMapper;
import com.redescooter.ses.service.foundation.service.base.PlaUserPasswordService;
@Service
public class PlaUserPasswordServiceImpl extends ServiceImpl<PlaUserPasswordMapper, PlaUserPassword> implements PlaUserPasswordService{

    @Override
    public int updateBatch(List<PlaUserPassword> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaUserPassword> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaUserPassword record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaUserPassword record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
