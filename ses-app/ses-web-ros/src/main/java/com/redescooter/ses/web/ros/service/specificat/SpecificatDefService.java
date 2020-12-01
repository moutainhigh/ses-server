package com.redescooter.ses.web.ros.service.specificat;

import com.redescooter.ses.web.ros.dm.OpeSpecificatDef;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatDefEnter;

import java.util.List;

/**
 * @ClassNameSpecificatTypeService
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:46
 * @Version V1.0
 **/
public interface SpecificatDefService {

    /**
     * @Author Aleks
     * @Description  生成规格类型的自定义项
     * @Date  2020/9/28 18:22
     * @Param [defEnterList, userId]
     * @return
     **/
    void saveSpecificatDef(List<SpecificatDefEnter> defEnterList,Long userId);


    /**
     * @Author Aleks
     * @Description  删除规格类型的自定义项
     * @Date  2020/9/28 19:21
     * @Param [specificatId]
     * @return
     **/
    void deleSpecificatDef(Long specificatId);


    /**
     * @Author Aleks
     * @Description  查找规格类型的自定义项
     * @Date  2020/9/28 19:22
     * @Param 规格类型的id
     * @return
     **/
    List<OpeSpecificatDef> getDef(Long specificatId);
}
