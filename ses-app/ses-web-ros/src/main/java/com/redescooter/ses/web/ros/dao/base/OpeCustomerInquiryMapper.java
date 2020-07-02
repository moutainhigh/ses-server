package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeCustomerInquiryMapper extends BaseMapper<OpeCustomerInquiry> {
    int updateBatch(List<OpeCustomerInquiry> list);

    int batchInsert(@Param("list") List<OpeCustomerInquiry> list);

    int insertOrUpdate(OpeCustomerInquiry record);

    int insertOrUpdateSelective(OpeCustomerInquiry record);
}
