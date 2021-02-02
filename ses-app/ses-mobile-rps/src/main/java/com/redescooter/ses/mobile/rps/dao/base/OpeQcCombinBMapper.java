package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcCombinB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 11:17
 */
public interface OpeQcCombinBMapper extends BaseMapper<OpeQcCombinB> {
    int updateBatch(List<OpeQcCombinB> list);

    int updateBatchSelective(List<OpeQcCombinB> list);

    int batchInsert(@Param("list") List<OpeQcCombinB> list);

    int insertOrUpdate(OpeQcCombinB record);

    int insertOrUpdateSelective(OpeQcCombinB record);
}