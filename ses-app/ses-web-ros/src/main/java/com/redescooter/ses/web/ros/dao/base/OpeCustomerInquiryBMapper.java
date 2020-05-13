package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeCustomerInquiryBMapper extends BaseMapper<OpeCustomerInquiryB> {
    int updateBatch(List<OpeCustomerInquiryB> list);

    int batchInsert(@Param("list") List<OpeCustomerInquiryB> list);

    int insertOrUpdate(OpeCustomerInquiryB record);

    int insertOrUpdateSelective(OpeCustomerInquiryB record);
}