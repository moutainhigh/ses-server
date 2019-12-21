package com.redescooter.ses.service.foundation.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaJpushUserMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaJpushUser;
import com.redescooter.ses.service.foundation.service.impl.base.PlaJpushUserService;
/**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaJpushUserServiceImpl extends ServiceImpl<PlaJpushUserMapper, PlaJpushUser> implements PlaJpushUserService{

    @Override
    public int updateBatch(List<PlaJpushUser> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaJpushUser> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaJpushUser record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaJpushUser record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
