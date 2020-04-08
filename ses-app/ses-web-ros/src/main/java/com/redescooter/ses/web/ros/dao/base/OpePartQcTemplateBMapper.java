package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePartQcTemplateB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePartQcTemplateBMapper extends BaseMapper<OpePartQcTemplateB> {
    int updateBatch(List<OpePartQcTemplateB> list);

    int batchInsert(@Param("list") List<OpePartQcTemplateB> list);

    int insertOrUpdate(OpePartQcTemplateB record);

    int insertOrUpdateSelective(OpePartQcTemplateB record);
}