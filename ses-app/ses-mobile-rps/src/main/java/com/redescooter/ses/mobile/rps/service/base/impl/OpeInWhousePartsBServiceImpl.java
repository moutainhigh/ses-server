package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeInWhousePartsBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhousePartsBService;
/**
 *@author assert
 *@date 2021/1/13 16:13
 */
@Service
public class OpeInWhousePartsBServiceImpl implements OpeInWhousePartsBService{

    @Resource
    private OpeInWhousePartsBMapper opeInWhousePartsBMapper;

    @Override
    public int updateBatch(List<OpeInWhousePartsB> list) {
        return opeInWhousePartsBMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInWhousePartsB> list) {
        return opeInWhousePartsBMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInWhousePartsB record) {
        return opeInWhousePartsBMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInWhousePartsB record) {
        return opeInWhousePartsBMapper.insertOrUpdateSelective(record);
    }

}
