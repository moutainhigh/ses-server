package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustProductSerialNum;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeEntrustProductSerialNumMapper extends BaseMapper<OpeEntrustProductSerialNum> {
    int updateBatch(List<OpeEntrustProductSerialNum> list);

    int batchInsert(@Param("list") List<OpeEntrustProductSerialNum> list);

    int insertOrUpdate(OpeEntrustProductSerialNum record);

    int insertOrUpdateSelective(OpeEntrustProductSerialNum record);
}