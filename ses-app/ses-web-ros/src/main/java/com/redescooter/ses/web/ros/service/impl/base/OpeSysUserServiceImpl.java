package com.redescooter.ses.web.ros.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserMapper;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
/**
 * @author      Mr.lijiating
 * @Date:       19/12/2019 3:29 上午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class OpeSysUserServiceImpl extends ServiceImpl<OpeSysUserMapper, OpeSysUser> implements OpeSysUserService{

    @Override
    public int updateBatch(List<OpeSysUser> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeSysUser> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeSysUser record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeSysUser record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
