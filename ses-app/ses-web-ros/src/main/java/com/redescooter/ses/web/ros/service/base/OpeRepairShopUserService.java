package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRepairShopUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeRepairShopUserService extends IService<OpeRepairShopUser> {


    int updateBatch(List<OpeRepairShopUser> list);

    int batchInsert(List<OpeRepairShopUser> list);

    int insertOrUpdate(OpeRepairShopUser record);

    int insertOrUpdateSelective(OpeRepairShopUser record);

}
