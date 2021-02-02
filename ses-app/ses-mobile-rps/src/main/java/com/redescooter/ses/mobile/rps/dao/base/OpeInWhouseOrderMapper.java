package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 10:04
 */
public interface OpeInWhouseOrderMapper extends BaseMapper<OpeInWhouseOrder> {
    int updateBatch(List<OpeInWhouseOrder> list);

    int updateBatchSelective(List<OpeInWhouseOrder> list);

    int batchInsert(@Param("list") List<OpeInWhouseOrder> list);

    int insertOrUpdate(OpeInWhouseOrder record);

    int insertOrUpdateSelective(OpeInWhouseOrder record);
}