package com.redescooter.ses.service.foundation.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaMessage;
import com.redescooter.ses.service.foundation.dao.base.PlaMessageMapper;
import com.redescooter.ses.service.foundation.service.impl.base.PlaMessageService;
/**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaMessageServiceImpl extends ServiceImpl<PlaMessageMapper, PlaMessage> implements PlaMessageService{

    @Override
    public int updateBatch(List<PlaMessage> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaMessage> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaMessage record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaMessage record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
