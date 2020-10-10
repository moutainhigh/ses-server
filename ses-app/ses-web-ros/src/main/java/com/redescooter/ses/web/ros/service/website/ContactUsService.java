package com.redescooter.ses.web.ros.service.website;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.customer.*;
import com.redescooter.ses.web.ros.vo.website.SaveAboutUsEnter;

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


  /*
   * @Author joan
   * @Description  联系我们留言回复
   * @Date  2020/8/4 20:48
   * @Param [enter]
   * @return
   **/
  GeneralResult message(ContactUsMessageEnter enter);

  /*
   * @Author Aleks
   * @Description  官网上联系我们的操作
   * @Date  2020/8/5 12:02
   * @Param [enter]
   * @return
   **/
  void websiteContactUs(SaveAboutUsEnter enter);

    /**
     * @return
     * @Author Aleks
     * @Description  联系我们导出
     * @Date 2020/8/18 11:27
     * @Param [enter]
     **/
    GeneralResult export(ContactUsListEnter enter);
}
