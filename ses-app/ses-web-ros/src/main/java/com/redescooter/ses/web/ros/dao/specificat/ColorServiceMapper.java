package com.redescooter.ses.web.ros.dao.specificat;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.web.ros.vo.specificat.ColorDataResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorListResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNameColorServiceMapper
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:38
 * @Version V1.0
 **/
public interface ColorServiceMapper {

    int listNum();

    List<ColorListResult> colorList(@Param("enter") PageEnter enter);

    List<ColorDataResult> colorData();



}
