package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePartDraftQcTemplate;
import com.redescooter.ses.web.ros.dao.base.OpePartDraftQcTemplateMapper;
import com.redescooter.ses.web.ros.service.base.OpePartDraftQcTemplateService;

@Service
public class OpePartDraftQcTemplateServiceImpl extends ServiceImpl<OpePartDraftQcTemplateMapper, OpePartDraftQcTemplate> implements OpePartDraftQcTemplateService {

    @Override
    public int updateBatch(List<OpePartDraftQcTemplate> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePartDraftQcTemplate> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePartDraftQcTemplate record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePartDraftQcTemplate record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


