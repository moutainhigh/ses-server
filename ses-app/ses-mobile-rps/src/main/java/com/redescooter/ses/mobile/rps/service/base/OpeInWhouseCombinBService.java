package com.redescooter.ses.mobile.rps.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseCombinB;

import java.util.List;

/**
 * @author assert
 * @date 2021/1/13 16:13
 */
public interface OpeInWhouseCombinBService extends IService<OpeInWhouseCombinB> {

    int updateBatch(List<OpeInWhouseCombinB> list);

    int updateBatchSelective(List<OpeInWhouseCombinB> list);

    int batchInsert(List<OpeInWhouseCombinB> list);

    int insertOrUpdate(OpeInWhouseCombinB record);

    int insertOrUpdateSelective(OpeInWhouseCombinB record);
}






