package com.redescooter.ses.service.foundation.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantMapper;
import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;
import com.redescooter.ses.service.foundation.service.base.PlaTenantService;
@Service
public class PlaTenantServiceImpl extends ServiceImpl<PlaTenantMapper, PlaTenant> implements PlaTenantService{

    @Override
    public int updateBatch(List<PlaTenant> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaTenant> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaTenant record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaTenant record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
