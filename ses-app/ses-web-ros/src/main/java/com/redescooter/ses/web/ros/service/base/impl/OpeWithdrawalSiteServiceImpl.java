package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeWithdrawalSiteMapper;
import com.redescooter.ses.web.ros.dm.OpeWithdrawalSite;
import com.redescooter.ses.web.ros.service.base.OpeWithdrawalSiteService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OpeWithdrawalSiteServiceImpl extends ServiceImpl<OpeWithdrawalSiteMapper, OpeWithdrawalSite> implements OpeWithdrawalSiteService {

    @Override
    public int updateBatch(List<OpeWithdrawalSite> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<OpeWithdrawalSite> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<OpeWithdrawalSite> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeWithdrawalSite record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeWithdrawalSite record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
