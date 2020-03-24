package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairShopPrinciple;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeRepairShopPrincipleService extends IService<OpeRepairShopPrinciple> {


    int updateBatch(List<OpeRepairShopPrinciple> list);

    int batchInsert(List<OpeRepairShopPrinciple> list);

    int insertOrUpdate(OpeRepairShopPrinciple record);

    int insertOrUpdateSelective(OpeRepairShopPrinciple record);

}
