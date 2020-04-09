package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePartDraftQcTemplateB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePartDraftQcTemplateBMapper extends BaseMapper<OpePartDraftQcTemplateB> {
    int updateBatch(List<OpePartDraftQcTemplateB> list);

    int batchInsert(@Param("list") List<OpePartDraftQcTemplateB> list);

    int insertOrUpdate(OpePartDraftQcTemplateB record);

    int insertOrUpdateSelective(OpePartDraftQcTemplateB record);
}