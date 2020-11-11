package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeInWhousePartsB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeInWhousePartsBMapper extends BaseMapper<OpeInWhousePartsB> {
    int updateBatch(List<OpeInWhousePartsB> list);

    int batchInsert(@Param("list") List<OpeInWhousePartsB> list);

    int insertOrUpdate(OpeInWhousePartsB record);

    int insertOrUpdateSelective(OpeInWhousePartsB record);
}