package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairShopUser;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeRepairShopUserService extends IService<OpeRepairShopUser> {


    int updateBatch(List<OpeRepairShopUser> list);

    int batchInsert(List<OpeRepairShopUser> list);

    int insertOrUpdate(OpeRepairShopUser record);

    int insertOrUpdateSelective(OpeRepairShopUser record);

}
