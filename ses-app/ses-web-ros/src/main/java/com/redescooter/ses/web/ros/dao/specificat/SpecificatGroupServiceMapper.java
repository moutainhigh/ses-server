package com.redescooter.ses.web.ros.dao.specificat;

import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupDataResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupListEnter;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupListResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNameSpecificatGroupMapper
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:44
 * @Version V1.0
 **/
public interface SpecificatGroupServiceMapper {

  int listNum(@Param("enter") SpecificatGroupListEnter enter);

    List<SpecificatGroupListResult> groupList(@Param("enter") SpecificatGroupListEnter enter);

  List<SpecificatGroupDataResult> specificatGroupData();

}
