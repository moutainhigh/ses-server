package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpePayMchNotifyMapper;
import com.redescooter.ses.web.ros.dm.OpePayMchNotify;
import com.redescooter.ses.web.ros.service.base.OpePayMchNotifyService;

@Service
public class OpePayMchNotifyServiceImpl extends ServiceImpl<OpePayMchNotifyMapper, OpePayMchNotify>
    implements OpePayMchNotifyService {

    @Override
    public int updateBatch(List<OpePayMchNotify> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePayMchNotify> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePayMchNotify record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePayMchNotify record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
