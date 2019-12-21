package com.redescooter.ses.service.foundation.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPassword;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaUserPasswordMapper;
import com.redescooter.ses.service.foundation.service.impl.base.PlaUserPasswordService;
/**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaUserPasswordServiceImpl extends ServiceImpl<PlaUserPasswordMapper, PlaUserPassword> implements PlaUserPasswordService{

    @Override
    public int updateBatch(List<PlaUserPassword> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaUserPassword> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaUserPassword record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaUserPassword record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
