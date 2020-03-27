package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRepairOrderSparepart;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeRepairOrderSparepartService extends IService<OpeRepairOrderSparepart> {


    int updateBatch(List<OpeRepairOrderSparepart> list);

    int batchInsert(List<OpeRepairOrderSparepart> list);

    int insertOrUpdate(OpeRepairOrderSparepart record);

    int insertOrUpdateSelective(OpeRepairOrderSparepart record);

}
