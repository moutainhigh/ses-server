package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustProductSerialNum;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeEntrustProductSerialNumService extends IService<OpeEntrustProductSerialNum> {


    int updateBatch(List<OpeEntrustProductSerialNum> list);

    int batchInsert(List<OpeEntrustProductSerialNum> list);

    int insertOrUpdate(OpeEntrustProductSerialNum record);

    int insertOrUpdateSelective(OpeEntrustProductSerialNum record);

}








