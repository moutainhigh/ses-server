package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrder;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeInWhouseOrderMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhouseOrderService;
/**
 *@author assert
 *@date 2021/1/18 10:46
 */
@Service
public class OpeInWhouseOrderServiceImpl implements OpeInWhouseOrderService{

    @Resource
    private OpeInWhouseOrderMapper opeInWhouseOrderMapper;

    @Override
    public int updateBatch(List<OpeInWhouseOrder> list) {
        return opeInWhouseOrderMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInWhouseOrder> list) {
        return opeInWhouseOrderMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInWhouseOrder record) {
        return opeInWhouseOrderMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInWhouseOrder record) {
        return opeInWhouseOrderMapper.insertOrUpdateSelective(record);
    }

}
