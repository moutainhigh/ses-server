package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseScooterB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/1/13 16:10
 */
public interface OpeInWhouseScooterBMapper extends BaseMapper<OpeInWhouseScooterB> {
    int updateBatch(List<OpeInWhouseScooterB> list);

    int batchInsert(@Param("list") List<OpeInWhouseScooterB> list);

    int insertOrUpdate(OpeInWhouseScooterB record);

    int insertOrUpdateSelective(OpeInWhouseScooterB record);
}