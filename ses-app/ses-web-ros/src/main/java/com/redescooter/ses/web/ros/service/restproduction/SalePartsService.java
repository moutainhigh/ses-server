package com.redescooter.ses.web.ros.service.restproduction;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproduct.*;

import java.util.List;

/**
 * @Author Aleks
 * @Description
 * @Date 2020/10/20 10:29
 * @Param
 * @return
 **/
public interface SalePartsService {

    /**
     * @return
     * @Author Aleks
     * @Description 销售部件新增
     * @Date 2020/10/20 13:55
     * @Param [enter]
     **/
    GeneralResult saveSaleParts(SalePartsSaveOrUpdateEnter enter);


    /**
     * @return
     * @Author Aleks
     * @Description 销售部件编辑
     * @Date 2020/10/20 13:56
     * @Param [enter]
     **/
    GeneralResult editSaleParts(SalePartsSaveOrUpdateEnter enter);


    /**
     * @return
     * @Author Aleks
     * @Description 销售部件删除
     * @Date 2020/10/20 13:56
     * @Param [enter]
     **/
    GeneralResult deleteSaleParts(IdEnter enter);


    /**
     * @return
     * @Author Aleks
     * @Description 销售部件状态修改
     * @Date 2020/10/20 13:56
     * @Param [enter]
     **/
    GeneralResult editSalePartsStatus(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  销售部件列表
     * @Date  2020/10/20 14:24
     * @Param [enter]
     * @return
     **/
    PageResult<SalePartsListResult> salePartsList(SaleListEnter enter);


    /**
     * @Author Aleks
     * @Description  正式部件库中所有可销售的部件的英文名称
     * @Date  2020/10/20 14:36
     * @Param [enter]
     * @return
     **/
    List<PartsNameData> partsNameData(GeneralEnter enter);



    List<PartsNoData> partsNoData(PartsNoEnter enter);

}
