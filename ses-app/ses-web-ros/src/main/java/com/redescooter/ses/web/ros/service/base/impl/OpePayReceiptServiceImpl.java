package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpePayReceiptMapper;
import com.redescooter.ses.web.ros.dm.OpePayReceipt;
import com.redescooter.ses.web.ros.service.base.OpePayReceiptService;

@Service
public class OpePayReceiptServiceImpl extends ServiceImpl<OpePayReceiptMapper, OpePayReceipt>
    implements OpePayReceiptService {

    @Override
    public int updateBatch(List<OpePayReceipt> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePayReceipt> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePayReceipt record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePayReceipt record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
