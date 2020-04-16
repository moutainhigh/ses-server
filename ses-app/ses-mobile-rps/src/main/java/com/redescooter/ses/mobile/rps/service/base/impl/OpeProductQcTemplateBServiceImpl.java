package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductQcTemplateBMapper;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeProductQcTemplateB;
import com.redescooter.ses.mobile.rps.service.base.impl.OpeProductQcTemplateBService;

@Service
public class OpeProductQcTemplateBServiceImpl extends ServiceImpl<OpeProductQcTemplateBMapper, OpeProductQcTemplateB> implements OpeProductQcTemplateBService {

    @Override
    public int updateBatch(List<OpeProductQcTemplateB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductQcTemplateB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductQcTemplateB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductQcTemplateB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

