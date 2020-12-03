package com.redescooter.ses.admin.dev.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.admin.dev.dm.AdmWorkOrderLog;

import java.util.List;
public interface AdmWorkOrderLogService extends IService<AdmWorkOrderLog> {


    int updateBatch(List<AdmWorkOrderLog> list);

    int updateBatchSelective(List<AdmWorkOrderLog> list);

    int batchInsert(List<AdmWorkOrderLog> list);

    int insertOrUpdate(AdmWorkOrderLog record);

    int insertOrUpdateSelective(AdmWorkOrderLog record);

}
