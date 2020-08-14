package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeContactUsMapper;
import com.redescooter.ses.web.ros.dm.OpeContactUs;
import com.redescooter.ses.web.ros.service.base.OpeContactUsService;
@Service
public class OpeContactUsServiceImpl extends ServiceImpl<OpeContactUsMapper, OpeContactUs> implements OpeContactUsService{

    @Override
    public int updateBatch(List<OpeContactUs> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeContactUs> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeContactUs record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeContactUs record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
