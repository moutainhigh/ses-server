package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartsType;

@Transactional
public interface OpePartsTypeService extends IService<OpePartsType> {


    int updateBatch(List<OpePartsType> list);

    int batchInsert(List<OpePartsType> list);

    int insertOrUpdate(OpePartsType record);

    int insertOrUpdateSelective(OpePartsType record);

}







