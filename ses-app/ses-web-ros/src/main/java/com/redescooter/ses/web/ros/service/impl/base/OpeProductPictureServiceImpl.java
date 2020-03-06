package com.redescooter.ses.web.ros.service.impl.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeProductPictureMapper;
import com.redescooter.ses.web.ros.dm.OpeProductPicture;
import com.redescooter.ses.web.ros.service.base.OpeProductPictureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeProductPictureServiceImpl extends ServiceImpl<OpeProductPictureMapper, OpeProductPicture> implements OpeProductPictureService {

    @Override
    public int updateBatch(List<OpeProductPicture> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductPicture> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductPicture record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductPicture record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



