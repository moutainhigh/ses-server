package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpePartQcTemplateB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePartQcTemplateBMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpePartQcTemplateB record);

    int insertOrUpdate(OpePartQcTemplateB record);

    int insertOrUpdateSelective(OpePartQcTemplateB record);

    int insertSelective(OpePartQcTemplateB record);

    OpePartQcTemplateB selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpePartQcTemplateB record);

    int updateByPrimaryKey(OpePartQcTemplateB record);

    int updateBatch(List<OpePartQcTemplateB> list);

    int updateBatchSelective(List<OpePartQcTemplateB> list);

    int batchInsert(@Param("list") List<OpePartQcTemplateB> list);
}