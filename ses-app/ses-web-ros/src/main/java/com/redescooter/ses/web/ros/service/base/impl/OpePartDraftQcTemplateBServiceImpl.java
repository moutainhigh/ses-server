package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePartDraftQcTemplateBMapper;
import com.redescooter.ses.web.ros.dm.OpePartDraftQcTemplateB;
import com.redescooter.ses.web.ros.service.base.OpePartDraftQcTemplateBService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpePartDraftQcTemplateBServiceImpl extends ServiceImpl<OpePartDraftQcTemplateBMapper, OpePartDraftQcTemplateB> implements OpePartDraftQcTemplateBService {

    @Override
    public int updateBatch(List<OpePartDraftQcTemplateB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePartDraftQcTemplateB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePartDraftQcTemplateB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePartDraftQcTemplateB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}





