package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpePartsProduct;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePartsProductService extends IService<OpePartsProduct>{


    int updateBatch(List<OpePartsProduct> list);

    int batchInsert(List<OpePartsProduct> list);

    int insertOrUpdate(OpePartsProduct record);

    int insertOrUpdateSelective(OpePartsProduct record);

}
