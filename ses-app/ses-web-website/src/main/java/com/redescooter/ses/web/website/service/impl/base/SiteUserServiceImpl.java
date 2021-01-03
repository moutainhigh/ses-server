package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.website.dao.base.SiteUserMapper;
import com.redescooter.ses.web.website.dm.SiteUser;
import com.redescooter.ses.web.website.service.base.SiteUserService;
@Service
public class SiteUserServiceImpl extends ServiceImpl<SiteUserMapper, SiteUser> implements SiteUserService{

    @Override
    public int updateBatch(List<SiteUser> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SiteUser> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SiteUser> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SiteUser record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SiteUser record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
