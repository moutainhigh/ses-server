package com.redescooter.ses.service.hub.source.operation.dao.base;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomerInquiry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeCustomerInquiryMapper extends BaseMapper<OpeCustomerInquiry> {
    int updateBatch(List<OpeCustomerInquiry> list);

    int batchInsert(@Param("list") List<OpeCustomerInquiry> list);

    int insertOrUpdate(OpeCustomerInquiry record);

    int insertOrUpdateSelective(OpeCustomerInquiry record);
}
