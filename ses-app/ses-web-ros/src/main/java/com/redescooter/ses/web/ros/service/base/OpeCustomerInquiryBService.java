package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface OpeCustomerInquiryBService extends IService<OpeCustomerInquiryB>{


    int updateBatch(List<OpeCustomerInquiryB> list);

    int batchInsert(List<OpeCustomerInquiryB> list);

    int insertOrUpdate(OpeCustomerInquiryB record);

    int insertOrUpdateSelective(OpeCustomerInquiryB record);

}
