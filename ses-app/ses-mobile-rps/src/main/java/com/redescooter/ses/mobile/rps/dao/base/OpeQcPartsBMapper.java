package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcPartsB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 11:20
 */
public interface OpeQcPartsBMapper extends BaseMapper<OpeQcPartsB> {
    int updateBatch(List<OpeQcPartsB> list);

    int updateBatchSelective(List<OpeQcPartsB> list);

    int batchInsert(@Param("list") List<OpeQcPartsB> list);

    int insertOrUpdate(OpeQcPartsB record);

    int insertOrUpdateSelective(OpeQcPartsB record);
}