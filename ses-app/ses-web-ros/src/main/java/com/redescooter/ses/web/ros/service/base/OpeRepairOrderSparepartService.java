package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairOrderSparepart;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeRepairOrderSparepartService extends IService<OpeRepairOrderSparepart> {


    int updateBatch(List<OpeRepairOrderSparepart> list);

    int batchInsert(List<OpeRepairOrderSparepart> list);

    int insertOrUpdate(OpeRepairOrderSparepart record);

    int insertOrUpdateSelective(OpeRepairOrderSparepart record);

}
