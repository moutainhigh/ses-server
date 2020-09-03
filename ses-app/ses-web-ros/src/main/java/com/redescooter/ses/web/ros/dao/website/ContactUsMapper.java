package com.redescooter.ses.web.ros.dao.website;

import com.redescooter.ses.web.ros.vo.customer.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNameContactUsMapper
 * @Description
 * @Author Aleks
 * @Date2020/8/4 20:11
 * @Version V1.0
 **/
public interface ContactUsMapper {
 int totalRows(@Param("enter") ContactUsListEnter enter);

 List<ContactUsListResult> list(@Param("enter") ContactUsListEnter enter);

 List<ContactUsDetailResult> detailList(@Param("enter") ContactUsEnter enter);

 List<ContactUsListResult> export(@Param("enter") ContactUsListEnter enter);

}
