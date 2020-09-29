package com.redescooter.ses.web.ros.service.specificat;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.vo.specificat.ColorDataResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorListResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorSaveOrEditEnter;

import java.util.List;

/**
 * @ClassNameColorService
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:36
 * @Version V1.0
 **/
public interface ColorService {

    /**
     * @Author Aleks
     * @Description  颜色新增
     * @Date  2020/9/28 14:55
     * @Param [enter]
     * @return
     **/
   GeneralResult colorSave(ColorSaveOrEditEnter enter);


   /**
    * @Author Aleks
    * @Description  颜色列表
    * @Date  2020/9/28 15:04
    * @Param [enter]
    * @return
    **/
   PageResult<ColorListResult> colorList(PageEnter enter);


    /**
     * @Author Aleks
     * @Description  删除颜色
     * @Date  2020/9/28 15:24
     * @Param [enter]
     * @return
     **/
    GeneralResult colorDelete(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  颜色的下拉数据源接口
     * @Date  2020/9/28 15:32
     * @Param [enter]
     * @return
     **/
    List<ColorDataResult> colorData(GeneralEnter enter);


}
