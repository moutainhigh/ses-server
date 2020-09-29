package com.redescooter.ses.web.ros.service.specificat;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupListEnter;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupListResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupSaveOrEditEnter;

import java.util.List;

/**
 * @ClassNameSpecificatGroupService
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:43
 * @Version V1.0
 **/
public interface SpecificatGroupService {

    /**
     * @Author Aleks
     * @Description  分组新增
     * @Date  2020/9/28 16:48
     * @Param [enter]
     * @return
     **/
    GeneralResult specificatGroupSave(SpecificatGroupSaveOrEditEnter enter);


    /**
     * @Author Aleks
     * @Description   分组修改
     * @Date  2020/9/28 16:49
     * @Param [enter]
     * @return
     **/
    GeneralResult specificatGroupEdit(SpecificatGroupSaveOrEditEnter enter);


    /**
     * @Author Aleks
     * @Description  分组列表
     * @Date  2020/9/28 16:50
     * @Param [enter]
     * @return
     **/
    PageResult<SpecificatGroupListResult> specificatGroupList(SpecificatGroupListEnter enter);


    /**
     * @Author Aleks
     * @Description  分组删除
     * @Date  2020/9/28 16:59
     * @Param [enter]
     * @return
     **/
    GeneralResult specificatGroupDelete(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  分组下拉数据源接口
     * @Date  2020/9/28 17:12
     * @Param [enter]
     * @return
     **/
    List<SpecificatGroupSaveOrEditEnter> specificatGroupData(GeneralEnter enter);


}
