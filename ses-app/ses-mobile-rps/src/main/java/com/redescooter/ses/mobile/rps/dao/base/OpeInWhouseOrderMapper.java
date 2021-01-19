package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/1/19 10:59
 */
public interface OpeInWhouseOrderMapper extends BaseMapper {
    int updateBatch(List<OpeInWhouseOrder> list);

    int batchInsert(@Param("list") List<OpeInWhouseOrder> list);

    int insertOrUpdate(OpeInWhouseOrder record);

    int insertOrUpdateSelective(OpeInWhouseOrder record);
}