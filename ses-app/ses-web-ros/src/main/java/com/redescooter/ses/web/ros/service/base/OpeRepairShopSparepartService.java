package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRepairShopSparepart;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeRepairShopSparepartService extends IService<OpeRepairShopSparepart> {


    int updateBatch(List<OpeRepairShopSparepart> list);

    int batchInsert(List<OpeRepairShopSparepart> list);

    int insertOrUpdate(OpeRepairShopSparepart record);

    int insertOrUpdateSelective(OpeRepairShopSparepart record);

}
