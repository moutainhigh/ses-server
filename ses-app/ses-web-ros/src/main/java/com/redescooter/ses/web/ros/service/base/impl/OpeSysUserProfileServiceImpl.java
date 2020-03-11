package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserProfileMapper;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 3:28 上午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@Service
public class OpeSysUserProfileServiceImpl extends ServiceImpl<OpeSysUserProfileMapper, OpeSysUserProfile> implements OpeSysUserProfileService {

    @Override
    public int updateBatch(List<OpeSysUserProfile> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSysUserProfile> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSysUserProfile record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSysUserProfile record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


