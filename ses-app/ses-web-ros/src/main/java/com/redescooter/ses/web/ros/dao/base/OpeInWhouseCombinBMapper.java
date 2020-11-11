package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeInWhouseCombinB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeInWhouseCombinBMapper extends BaseMapper<OpeInWhouseCombinB> {
    int updateBatch(List<OpeInWhouseCombinB> list);

    int batchInsert(@Param("list") List<OpeInWhouseCombinB> list);

    int insertOrUpdate(OpeInWhouseCombinB record);

    int insertOrUpdateSelective(OpeInWhouseCombinB record);
}