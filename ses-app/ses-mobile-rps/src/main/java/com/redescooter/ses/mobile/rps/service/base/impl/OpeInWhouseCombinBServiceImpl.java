package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseCombinB;
import com.redescooter.ses.mobile.rps.dao.base.OpeInWhouseCombinBMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhouseCombinBService;

/**
 * @author assert
 * @date 2021/1/13 16:13
 */
@Service
public class OpeInWhouseCombinBServiceImpl implements OpeInWhouseCombinBService {

    @Resource
    private OpeInWhouseCombinBMapper opeInWhouseCombinBMapper;

    @Override
    public int updateBatch(List<OpeInWhouseCombinB> list) {
        return opeInWhouseCombinBMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInWhouseCombinB> list) {
        return opeInWhouseCombinBMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInWhouseCombinB record) {
        return opeInWhouseCombinBMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInWhouseCombinB record) {
        return opeInWhouseCombinBMapper.insertOrUpdateSelective(record);
    }

}


