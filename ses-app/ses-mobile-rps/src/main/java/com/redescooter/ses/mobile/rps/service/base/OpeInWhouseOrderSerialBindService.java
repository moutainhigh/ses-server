package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrderSerialBind;import java.util.List;

/**
 * @author assert
 * @date 2021/1/22 17:47
 */
public interface OpeInWhouseOrderSerialBindService extends IService<OpeInWhouseOrderSerialBind> {

    int updateBatch(List<OpeInWhouseOrderSerialBind> list);

    int updateBatchSelective(List<OpeInWhouseOrderSerialBind> list);

    int batchInsert(List<OpeInWhouseOrderSerialBind> list);

    int insertOrUpdate(OpeInWhouseOrderSerialBind record);

    int insertOrUpdateSelective(OpeInWhouseOrderSerialBind record);
}




