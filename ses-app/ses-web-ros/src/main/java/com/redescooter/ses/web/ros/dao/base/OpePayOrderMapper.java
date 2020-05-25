package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePayOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePayOrderMapper extends BaseMapper<OpePayOrder> {
    int updateBatch(List<OpePayOrder> list);

    int batchInsert(@Param("list") List<OpePayOrder> list);

    int insertOrUpdate(OpePayOrder record);

    int insertOrUpdateSelective(OpePayOrder record);
}