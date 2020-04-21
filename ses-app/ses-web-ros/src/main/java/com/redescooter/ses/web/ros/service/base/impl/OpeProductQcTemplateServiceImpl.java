package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeProductQcTemplate;
import com.redescooter.ses.web.ros.dao.base.OpeProductQcTemplateMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductQcTemplateService;

@Service
public class OpeProductQcTemplateServiceImpl extends ServiceImpl<OpeProductQcTemplateMapper, OpeProductQcTemplate> implements OpeProductQcTemplateService {

    @Override
    public int updateBatch(List<OpeProductQcTemplate> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductQcTemplate> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductQcTemplate record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductQcTemplate record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



