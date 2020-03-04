package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeProductTypeMapper extends BaseMapper<OpeProductType> {
    int updateBatch(List<OpeProductType> list);

    int batchInsert(@Param("list") List<OpeProductType> list);

    int insertOrUpdate(OpeProductType record);

    int insertOrUpdateSelective(OpeProductType record);
}