package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhScooterB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 9:38
 */
public interface OpeOutWhScooterBMapper extends BaseMapper<OpeOutWhScooterB> {
    int updateBatch(List<OpeOutWhScooterB> list);

    int updateBatchSelective(List<OpeOutWhScooterB> list);

    int batchInsert(@Param("list") List<OpeOutWhScooterB> list);

    int insertOrUpdate(OpeOutWhScooterB record);

    int insertOrUpdateSelective(OpeOutWhScooterB record);
}