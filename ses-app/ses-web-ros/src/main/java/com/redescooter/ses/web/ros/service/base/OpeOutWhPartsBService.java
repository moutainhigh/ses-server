package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeOutWhPartsB;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeOutWhPartsBService extends IService<OpeOutWhPartsB> {


    int updateBatch(List<OpeOutWhPartsB> list);

    int batchInsert(List<OpeOutWhPartsB> list);

    int insertOrUpdate(OpeOutWhPartsB record);

    int insertOrUpdateSelective(OpeOutWhPartsB record);

}

