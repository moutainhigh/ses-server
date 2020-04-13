package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRepairShopBusinessHours;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeRepairShopBusinessHoursService extends IService<OpeRepairShopBusinessHours> {


    int updateBatch(List<OpeRepairShopBusinessHours> list);

    int batchInsert(List<OpeRepairShopBusinessHours> list);

    int insertOrUpdate(OpeRepairShopBusinessHours record);

    int insertOrUpdateSelective(OpeRepairShopBusinessHours record);

}
