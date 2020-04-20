package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductQcTemplateB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductQcTemplateBMapper extends BaseMapper<OpeProductQcTemplateB> {
    int updateBatch(List<OpeProductQcTemplateB> list);

    int batchInsert(@Param("list") List<OpeProductQcTemplateB> list);

    int insertOrUpdate(OpeProductQcTemplateB record);

    int insertOrUpdateSelective(OpeProductQcTemplateB record);
}