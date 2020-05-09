package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductItem;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeProductItemMapper extends BaseMapper<OpeProductItem> {
    int updateBatch(List<OpeProductItem> list);

    int batchInsert(@Param("list") List<OpeProductItem> list);

    int insertOrUpdate(OpeProductItem record);

    int insertOrUpdateSelective(OpeProductItem record);
}