package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeCustomerInquiryService extends IService<OpeCustomerInquiry> {


    int updateBatch(List<OpeCustomerInquiry> list);

    int batchInsert(List<OpeCustomerInquiry> list);

    int insertOrUpdate(OpeCustomerInquiry record);

    int insertOrUpdateSelective(OpeCustomerInquiry record);

}




