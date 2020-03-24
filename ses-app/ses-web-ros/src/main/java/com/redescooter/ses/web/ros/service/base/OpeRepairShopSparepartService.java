package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairShopSparepart;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeRepairShopSparepartService extends IService<OpeRepairShopSparepart> {


    int updateBatch(List<OpeRepairShopSparepart> list);

    int batchInsert(List<OpeRepairShopSparepart> list);

    int insertOrUpdate(OpeRepairShopSparepart record);

    int insertOrUpdateSelective(OpeRepairShopSparepart record);

}
