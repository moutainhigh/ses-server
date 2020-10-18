package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeWithdrawalSite;

import java.util.List;

public interface OpeWithdrawalSiteService extends IService<OpeWithdrawalSite> {


    int updateBatch(List<OpeWithdrawalSite> list);

    int updateBatchSelective(List<OpeWithdrawalSite> list);

    int batchInsert(List<OpeWithdrawalSite> list);

    int insertOrUpdate(OpeWithdrawalSite record);

    int insertOrUpdateSelective(OpeWithdrawalSite record);

}
