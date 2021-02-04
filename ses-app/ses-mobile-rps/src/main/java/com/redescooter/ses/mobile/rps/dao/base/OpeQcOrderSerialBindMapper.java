package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcOrderSerialBind;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 11:23
 */
public interface OpeQcOrderSerialBindMapper extends BaseMapper<OpeQcOrderSerialBind> {
    int updateBatch(List<OpeQcOrderSerialBind> list);

    int updateBatchSelective(List<OpeQcOrderSerialBind> list);

    int batchInsert(@Param("list") List<OpeQcOrderSerialBind> list);

    int insertOrUpdate(OpeQcOrderSerialBind record);

    int insertOrUpdateSelective(OpeQcOrderSerialBind record);
}