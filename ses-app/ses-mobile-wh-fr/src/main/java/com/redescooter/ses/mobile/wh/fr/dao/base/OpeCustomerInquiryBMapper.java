package com.redescooter.ses.mobile.wh.fr.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCustomerInquiryB;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeCustomerInquiryBMapper extends BaseMapper<OpeCustomerInquiryB> {
    int updateBatch(List<OpeCustomerInquiryB> list);

    int batchInsert(@Param("list") List<OpeCustomerInquiryB> list);

    int insertOrUpdate(OpeCustomerInquiryB record);

    int insertOrUpdateSelective(OpeCustomerInquiryB record);
}