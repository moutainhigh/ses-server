package com.redescooter.ses.web.ros.service.specificat;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.common.vo.specification.QuerySpecificTypeDetailResultDTO;
import com.redescooter.ses.web.ros.vo.specificat.*;
import com.redescooter.ses.web.ros.vo.specificat.dto.InsertSpecificTypeParamDTO;

import java.util.List;

/**
 * @ClassNameSpecificatTypeService
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:46
 * @Version V1.0
 **/
public interface SpecificatTypeService {

    /**
     * @Author Aleks
     * @Description  规格新增
     * @Date  2020/9/28 18:25
     * @Param [enter]
     * @return
     **/
    GeneralResult specificatTypeSave(SpecificatTypeSaveOrEditEnter enter);


    /**
     * @Author Aleks
     * @Description  规格删除
     * @Date  2020/9/28 18:28
     * @Param [enter]
     * @return
     **/
    GeneralResult specificatTypeDelete(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  规格修改
     * @Date  2020/9/28 18:30
     * @Param [enter]
     * @return
     **/
    GeneralResult specificatTypeEdit(SpecificatTypeSaveOrEditEnter enter);


    /**
     * @Author Aleks
     * @Description  规格列表
     * @Date  2020/9/28 18:53
     * @Param [enter]
     * @return
     **/
    PageResult<SpecificatTypeListResult> specificatTypeList(SpecificatTypeListEnter enter);


    /**
     * @Author Aleks
     * @Description  规格详情
     * @Date  2020/9/28 19:16
     * @Param [enter]
     * @return
     **/
    SpecificatTypeDetailResult specificatTypeDetail(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  规格类型名称的唯一性校验
     * @Date  2020/10/9 11:20
     * @Param [enter]
     * @return 名称不存在返回true 名称存在返回false
     **/
    BooleanResult specificatNameCheck(SpecificatTypeSaveOrEditEnter enter);


    /**
     * @Author Aleks
     * @Description  获取规格下拉数据的接口
     * @Date  2020/10/13 15:26
     * @Param
     * @return
     **/
    List<SpecificatTypeDataResult> specificatTypeData(GeneralEnter enter);

    /**
     * 新增规格类型,新接口(new)
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2020/12/7
    */
    GeneralResult insertSpecificType(InsertSpecificTypeParamDTO paramDTO);

    /**
     * 修改规格类型,新接口(new)
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2020/12/17
    */
    GeneralResult updateSpecificType(InsertSpecificTypeParamDTO paramDTO);

    /**
     * 根据id查询规格类型详情,新接口(new)
     * @param enter
     * @return com.redescooter.ses.api.common.vo.specification.QuerySpecificTypeDetailResultDTO
     * @author assert
     * @date 2020/12/17
    */
    QuerySpecificTypeDetailResultDTO getSpecificTypeDetailById(IdEnter enter);

}
