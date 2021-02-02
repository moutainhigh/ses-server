package com.redescooter.ses.mobile.rps.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/13 16:13
 */
public interface OpeInWhousePartsBService extends IService<OpeInWhousePartsB> {

    int updateBatch(List<OpeInWhousePartsB> list);

    int updateBatchSelective(List<OpeInWhousePartsB> list);

    int batchInsert(List<OpeInWhousePartsB> list);

    int insertOrUpdate(OpeInWhousePartsB record);

    int insertOrUpdateSelective(OpeInWhousePartsB record);
}






