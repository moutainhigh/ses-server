package com.redescooter.ses.web.ros.service.website;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.customer.*;

import java.util.List;

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

  /*
   * @Author joan
   * @Description  联系我们详情接口
   * @Date  2020/8/4 20:48
   * @Param [enter]
   * @return
   **/
  List<ContactUsDetailResult> detail(ContactUsEnter enter);

  /*
   * @Author joan
   * @Description  联系我们历史接口
   * @Date  2020/8/4 20:48
   * @Param [enter]
   * @return
   **/
  List<ContactUsHistoryResult> trace(ContactUsEnter enter);
}
