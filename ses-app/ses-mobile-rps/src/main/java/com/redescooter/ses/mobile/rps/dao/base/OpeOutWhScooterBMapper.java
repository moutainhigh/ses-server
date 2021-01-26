package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhScooterB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/1/19 15:28
 */
public interface OpeOutWhScooterBMapper extends BaseMapper<OpeOutWhScooterB> {
    int updateBatch(List<OpeOutWhScooterB> list);

    int batchInsert(@Param("list") List<OpeOutWhScooterB> list);

    int insertOrUpdate(OpeOutWhScooterB record);

    int insertOrUpdateSelective(OpeOutWhScooterB record);
}