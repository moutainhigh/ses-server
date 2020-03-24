package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeRepairOrderScooter;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeRepairOrderScooterService extends IService<OpeRepairOrderScooter> {


    int updateBatch(List<OpeRepairOrderScooter> list);

    int batchInsert(List<OpeRepairOrderScooter> list);

    int insertOrUpdate(OpeRepairOrderScooter record);

    int insertOrUpdateSelective(OpeRepairOrderScooter record);

}
