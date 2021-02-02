package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 10:28
 */
public interface OpeQcOrderMapper extends BaseMapper<OpeQcOrder> {
    int updateBatch(List<OpeQcOrder> list);

    int updateBatchSelective(List<OpeQcOrder> list);

    int batchInsert(@Param("list") List<OpeQcOrder> list);

    int insertOrUpdate(OpeQcOrder record);

    int insertOrUpdateSelective(OpeQcOrder record);
}