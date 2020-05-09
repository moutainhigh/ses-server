package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRepairShop;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeRepairShopService extends IService<OpeRepairShop> {


    int updateBatch(List<OpeRepairShop> list);

    int batchInsert(List<OpeRepairShop> list);

    int insertOrUpdate(OpeRepairShop record);

    int insertOrUpdateSelective(OpeRepairShop record);

}
