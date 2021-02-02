package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseCombinB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 10:17
 */
public interface OpeInWhouseCombinBMapper extends BaseMapper<OpeInWhouseCombinB> {
    int updateBatch(List<OpeInWhouseCombinB> list);

    int updateBatchSelective(List<OpeInWhouseCombinB> list);

    int batchInsert(@Param("list") List<OpeInWhouseCombinB> list);

    int insertOrUpdate(OpeInWhouseCombinB record);

    int insertOrUpdateSelective(OpeInWhouseCombinB record);
}