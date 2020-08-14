package com.redescooter.ses.web.ros.service.website;

import com.redescooter.ses.web.ros.dm.OpeContactUs;

/**
 * @ClassNameContactUsTraceService
 * @Description
 * @Author Aleks
 * @Date2020/8/4 20:07
 * @Version V1.0
 **/
public interface ContactUsTraceService {

    /**
     * @Author Aleks
     * @Description  创建联系我们的历史记录
     * @Date  2020/8/5 14:44
     * @Param
     * @return
     **/
     void createContactUsTrace(OpeContactUs contactUsEntity);

}
