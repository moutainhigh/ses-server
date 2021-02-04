package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlaMailTemplateMapper extends BaseMapper<PlaMailTemplate> {
    int batchInsert(@Param("list") List<PlaMailTemplate> list);

    int insertOrUpdate(PlaMailTemplate record);

    int insertOrUpdateSelective(PlaMailTemplate record);

    int insertOrUpdateWithBLOBs(PlaMailTemplate record);

    /**
     * 列表模糊查询
     */
    List<PlaMailTemplate> getList(@Param("enter") StringEnter enter);

}