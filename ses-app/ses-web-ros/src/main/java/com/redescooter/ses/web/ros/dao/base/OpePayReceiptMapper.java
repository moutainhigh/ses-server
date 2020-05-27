package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePayReceipt;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePayReceiptMapper extends BaseMapper<OpePayReceipt> {
    int updateBatch(List<OpePayReceipt> list);

    int batchInsert(@Param("list") List<OpePayReceipt> list);

    int insertOrUpdate(OpePayReceipt record);

    int insertOrUpdateSelective(OpePayReceipt record);
}