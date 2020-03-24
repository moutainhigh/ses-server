package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairShopBusinessHours;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeRepairShopBusinessHoursService extends IService<OpeRepairShopBusinessHours> {


    int updateBatch(List<OpeRepairShopBusinessHours> list);

    int batchInsert(List<OpeRepairShopBusinessHours> list);

    int insertOrUpdate(OpeRepairShopBusinessHours record);

    int insertOrUpdateSelective(OpeRepairShopBusinessHours record);

}
