package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerAccessoriesMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomerAccessories;
import com.redescooter.ses.web.ros.service.base.OpeCustomerAccessoriesService;
@Service
public class OpeCustomerAccessoriesServiceImpl extends ServiceImpl<OpeCustomerAccessoriesMapper, OpeCustomerAccessories> implements OpeCustomerAccessoriesService{

    @Override
    public int updateBatch(List<OpeCustomerAccessories> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeCustomerAccessories> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeCustomerAccessories record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeCustomerAccessories record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
