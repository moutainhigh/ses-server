package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePayMchNotify;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePayMchNotifyMapper extends BaseMapper<OpePayMchNotify> {
    int updateBatch(List<OpePayMchNotify> list);

    int batchInsert(@Param("list") List<OpePayMchNotify> list);

    int insertOrUpdate(OpePayMchNotify record);

    int insertOrUpdateSelective(OpePayMchNotify record);
}