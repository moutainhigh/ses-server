package com.redescooter.ses.web.ros.service.impl.base;

import com.redescooter.ses.web.ros.dm.OpeCustomer;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.redescooter.ses.web.ros.dao.base.OpeCustomerMapper;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 8:50 上午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@Service
public class OpeCustomerServiceImpl extends ServiceImpl<OpeCustomerMapper, OpeCustomer> implements OpeCustomerService {

    @Override
    public int updateBatch(List<OpeCustomer> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeCustomer> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeCustomer record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeCustomer record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}





