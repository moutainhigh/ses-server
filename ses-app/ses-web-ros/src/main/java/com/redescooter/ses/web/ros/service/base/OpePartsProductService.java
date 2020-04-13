package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpePartsProductService extends IService<OpePartsProduct> {


    int updateBatch(List<OpePartsProduct> list);

    int batchInsert(List<OpePartsProduct> list);

    int insertOrUpdate(OpePartsProduct record);

    int insertOrUpdateSelective(OpePartsProduct record);

}



