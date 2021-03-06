package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 10:22
 */
public interface OpeInWhousePartsBMapper extends BaseMapper<OpeInWhousePartsB> {
    int updateBatch(List<OpeInWhousePartsB> list);

    int updateBatchSelective(List<OpeInWhousePartsB> list);

    int batchInsert(@Param("list") List<OpeInWhousePartsB> list);

    int insertOrUpdate(OpeInWhousePartsB record);

    int insertOrUpdateSelective(OpeInWhousePartsB record);
}