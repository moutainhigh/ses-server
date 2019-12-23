package com.redescooter.ses.service.hub.source.consumer.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.consumer.dao.ConUserProfileMapper;
import com.redescooter.ses.service.hub.source.consumer.dm.ConUserProfile;
import com.redescooter.ses.service.hub.source.consumer.service.base.ConUserProfileService;
/**
 * @author      Mr.lijiating
 * @Date:       23/12/2019 11:21 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */
@DS("consumer")
@Service
public class ConUserProfileServiceImpl extends ServiceImpl<ConUserProfileMapper, ConUserProfile> implements ConUserProfileService{

    @Override
    public int updateBatch(List<ConUserProfile> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<ConUserProfile> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(ConUserProfile record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(ConUserProfile record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
