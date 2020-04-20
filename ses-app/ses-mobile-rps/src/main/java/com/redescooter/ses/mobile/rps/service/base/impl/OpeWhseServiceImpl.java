package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeWhseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWhse;
import com.redescooter.ses.mobile.rps.service.base.OpeWhseService;

@Service
public class OpeWhseServiceImpl extends ServiceImpl<OpeWhseMapper, OpeWhse> implements OpeWhseService {

    @Override
    public int batchInsert(List<OpeWhse> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeWhse record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeWhse record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}




