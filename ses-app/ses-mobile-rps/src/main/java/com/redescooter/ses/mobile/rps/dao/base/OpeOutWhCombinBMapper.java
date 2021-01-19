package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhCombinB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/1/19 15:28
 */
public interface OpeOutWhCombinBMapper extends BaseMapper {
    int updateBatch(List<OpeOutWhCombinB> list);

    int batchInsert(@Param("list") List<OpeOutWhCombinB> list);

    int insertOrUpdate(OpeOutWhCombinB record);

    int insertOrUpdateSelective(OpeOutWhCombinB record);
}