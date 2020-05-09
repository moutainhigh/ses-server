package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartsSec;

@Transactional
public interface OpePartsSecService extends IService<OpePartsSec> {


    int updateBatch(List<OpePartsSec> list);

    int batchInsert(List<OpePartsSec> list);

    int insertOrUpdate(OpePartsSec record);

    int insertOrUpdateSelective(OpePartsSec record);

}


