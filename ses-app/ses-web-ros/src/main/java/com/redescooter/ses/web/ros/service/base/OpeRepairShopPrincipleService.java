package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRepairShopPrinciple;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeRepairShopPrincipleService extends IService<OpeRepairShopPrinciple> {


    int updateBatch(List<OpeRepairShopPrinciple> list);

    int batchInsert(List<OpeRepairShopPrinciple> list);

    int insertOrUpdate(OpeRepairShopPrinciple record);

    int insertOrUpdateSelective(OpeRepairShopPrinciple record);

}
