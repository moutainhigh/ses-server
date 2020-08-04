package com.redescooter.ses.web.ros.service.website;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.customer.ContactUsListEnter;
import com.redescooter.ses.web.ros.vo.customer.ContactUsListResult;

/**
 * @ClassNameOpeContactUsService
 * @Description
 * @Author Aleks
 * @Date2020/8/4 20:06
 * @Version V1.0
 **/
public interface ContactUsService {

    /*
     * @Author Aleks
     * @Description  联系我们列表接口
     * @Date  2020/8/4 20:48
     * @Param [enter]
     * @return
     **/
    PageResult<ContactUsListResult> list(ContactUsListEnter enter);
}
