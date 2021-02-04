package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeOutWhCombinB;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeOutWhCombinBService extends IService<OpeOutWhCombinB> {


    int updateBatch(List<OpeOutWhCombinB> list);

    int batchInsert(List<OpeOutWhCombinB> list);

    int insertOrUpdate(OpeOutWhCombinB record);

    int insertOrUpdateSelective(OpeOutWhCombinB record);

}


