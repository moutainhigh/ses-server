package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductType;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeProductTypeMapper extends BaseMapper<OpeProductType> {
    int updateBatch(List<OpeProductType> list);

    int batchInsert(@Param("list") List<OpeProductType> list);

    int insertOrUpdate(OpeProductType record);

    int insertOrUpdateSelective(OpeProductType record);
}