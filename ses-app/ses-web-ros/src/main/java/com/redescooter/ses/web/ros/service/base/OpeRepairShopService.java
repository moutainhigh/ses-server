package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairShop;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeRepairShopService extends IService<OpeRepairShop> {


    int updateBatch(List<OpeRepairShop> list);

    int batchInsert(List<OpeRepairShop> list);

    int insertOrUpdate(OpeRepairShop record);

    int insertOrUpdateSelective(OpeRepairShop record);

}
