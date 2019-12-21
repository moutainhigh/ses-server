package com.redescooter.ses.service.foundation.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaUserMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.redescooter.ses.service.foundation.service.impl.base.PlaUserService;
/**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaUserServiceImpl extends ServiceImpl<PlaUserMapper, PlaUser> implements PlaUserService{

    @Override
    public int updateBatch(List<PlaUser> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaUser> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaUser record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaUser record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
