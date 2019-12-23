package com.redescooter.ses.service.foundation.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;
import com.redescooter.ses.service.foundation.service.base.PlaTenantService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author      Mr.lijiating
 * @Date:       24/12/2019 12:12 上午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaTenantServiceImpl extends ServiceImpl<PlaTenantMapper, PlaTenant> implements PlaTenantService {

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
