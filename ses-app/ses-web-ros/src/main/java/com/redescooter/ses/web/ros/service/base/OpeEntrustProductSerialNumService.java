package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeEntrustProductSerialNum;

import java.util.List;
public interface OpeEntrustProductSerialNumService extends IService<OpeEntrustProductSerialNum> {


    int updateBatch(List<OpeEntrustProductSerialNum> list);

    int batchInsert(List<OpeEntrustProductSerialNum> list);

    int insertOrUpdate(OpeEntrustProductSerialNum record);

    int insertOrUpdateSelective(OpeEntrustProductSerialNum record);

}
