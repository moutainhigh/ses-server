package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeOutwhOrderB;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeOutwhOrderBService extends IService<OpeOutwhOrderB> {


    int updateBatch(List<OpeOutwhOrderB> list);

    int batchInsert(List<OpeOutwhOrderB> list);

    int insertOrUpdate(OpeOutwhOrderB record);

    int insertOrUpdateSelective(OpeOutwhOrderB record);

}




