package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRepairOrderScooter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeRepairOrderScooterService extends IService<OpeRepairOrderScooter> {


    int updateBatch(List<OpeRepairOrderScooter> list);

    int batchInsert(List<OpeRepairOrderScooter> list);

    int insertOrUpdate(OpeRepairOrderScooter record);

    int insertOrUpdateSelective(OpeRepairOrderScooter record);

}
