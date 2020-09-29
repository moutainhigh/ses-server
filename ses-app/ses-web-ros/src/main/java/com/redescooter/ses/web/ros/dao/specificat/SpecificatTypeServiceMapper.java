package com.redescooter.ses.web.ros.dao.specificat;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeDetailResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeListEnter;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeListResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNameSpecificatTypeServiceMapper
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:47
 * @Version V1.0
 **/
public interface SpecificatTypeServiceMapper {

    int listNum(@Param("enter")SpecificatTypeListEnter enter);


    List<SpecificatTypeListResult> specificatTypeList(@Param("enter")SpecificatTypeListEnter enter);


    SpecificatTypeDetailResult specificatTypeDetail(@Param("id") Long id);

}
