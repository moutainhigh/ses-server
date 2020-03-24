package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartsProductB;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpePartsProductBService extends IService<OpePartsProductB> {


    int updateBatch(List<OpePartsProductB> list);

    int batchInsert(List<OpePartsProductB> list);

    int insertOrUpdate(OpePartsProductB record);

    int insertOrUpdateSelective(OpePartsProductB record);

}




