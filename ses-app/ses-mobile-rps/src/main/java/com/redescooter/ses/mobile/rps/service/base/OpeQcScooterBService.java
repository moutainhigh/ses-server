package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeQcScooterB;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/26 15:38
 */
public interface OpeQcScooterBService extends IService<OpeQcScooterB> {

    int updateBatch(List<OpeQcScooterB> list);

    int updateBatchSelective(List<OpeQcScooterB> list);

    int batchInsert(List<OpeQcScooterB> list);

    int insertOrUpdate(OpeQcScooterB record);

    int insertOrUpdateSelective(OpeQcScooterB record);
}


