package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrderSerialBind;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 10:24
 */
public interface OpeInWhouseOrderSerialBindMapper extends BaseMapper<OpeInWhouseOrderSerialBind> {
    int updateBatch(List<OpeInWhouseOrderSerialBind> list);

    int updateBatchSelective(List<OpeInWhouseOrderSerialBind> list);

    int batchInsert(@Param("list") List<OpeInWhouseOrderSerialBind> list);

    int insertOrUpdate(OpeInWhouseOrderSerialBind record);

    int insertOrUpdateSelective(OpeInWhouseOrderSerialBind record);
}