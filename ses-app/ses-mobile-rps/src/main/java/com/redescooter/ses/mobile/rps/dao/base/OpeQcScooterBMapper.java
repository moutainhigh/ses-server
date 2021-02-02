package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcScooterB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 11:15
 */
public interface OpeQcScooterBMapper extends BaseMapper<OpeQcScooterB> {
    int updateBatch(List<OpeQcScooterB> list);

    int updateBatchSelective(List<OpeQcScooterB> list);

    int batchInsert(@Param("list") List<OpeQcScooterB> list);

    int insertOrUpdate(OpeQcScooterB record);

    int insertOrUpdateSelective(OpeQcScooterB record);
}