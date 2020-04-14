package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpePartsProductB;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePartsProductBService extends IService<OpePartsProductB> {


    int updateBatch(List<OpePartsProductB> list);

    int batchInsert(List<OpePartsProductB> list);

    int insertOrUpdate(OpePartsProductB record);

    int insertOrUpdateSelective(OpePartsProductB record);

}

