package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocateB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAllocateBMapper extends BaseMapper<OpeAllocateB> {
    int updateBatch(List<OpeAllocateB> list);

    int batchInsert(@Param("list") List<OpeAllocateB> list);

    int insertOrUpdate(OpeAllocateB record);

    int insertOrUpdateSelective(OpeAllocateB record);
}