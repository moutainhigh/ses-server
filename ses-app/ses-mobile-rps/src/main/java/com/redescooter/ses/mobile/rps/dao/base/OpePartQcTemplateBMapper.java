package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePartQcTemplateB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePartQcTemplateBMapper extends BaseMapper<OpePartQcTemplateB> {
    int updateBatch(List<OpePartQcTemplateB> list);

    int batchInsert(@Param("list") List<OpePartQcTemplateB> list);

    int insertOrUpdate(OpePartQcTemplateB record);

    int insertOrUpdateSelective(OpePartQcTemplateB record);
}