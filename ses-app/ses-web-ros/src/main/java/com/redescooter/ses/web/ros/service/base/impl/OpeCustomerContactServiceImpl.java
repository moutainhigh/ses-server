package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeCustomerContact;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerContactMapper;
import com.redescooter.ses.web.ros.service.base.OpeCustomerContactService;
@Service
public class OpeCustomerContactServiceImpl extends ServiceImpl<OpeCustomerContactMapper, OpeCustomerContact> implements OpeCustomerContactService{

    @Override
    public int updateBatch(List<OpeCustomerContact> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeCustomerContact> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeCustomerContact record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeCustomerContact record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
