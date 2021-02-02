package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhCombinB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 9:40
 */
public interface OpeOutWhCombinBMapper extends BaseMapper<OpeOutWhCombinB> {
    int updateBatch(List<OpeOutWhCombinB> list);

    int updateBatchSelective(List<OpeOutWhCombinB> list);

    int batchInsert(@Param("list") List<OpeOutWhCombinB> list);

    int insertOrUpdate(OpeOutWhCombinB record);

    int insertOrUpdateSelective(OpeOutWhCombinB record);
}