package com.redescooter.ses.web.ros.service.restproduction;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterListResult;
import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterSaveOrUpdateEnter;

import java.util.Map;

/**
 * @ClassNameSaleScooterService
 * @Description
 * @Author Aleks
 * @Date2020/10/12 17:02
 * @Version V1.0
 **/
public interface SaleScooterService {

    /**
     * @Author Aleks
     * @Description  新增销售车辆
     * @Date  2020/10/13 14:10
     * @Param [enter]
     * @return
     **/
    GeneralResult saveSaleScooter(SaleScooterSaveOrUpdateEnter enter);


    /**
     * @Author Aleks
     * @Description  编辑销售车辆
     * @Date  2020/10/13 14:53
     * @Param [enter]
     * @return
     **/
    GeneralResult editSaleScooter(SaleScooterSaveOrUpdateEnter enter);


    /**
     * @Author Aleks
     * @Description  删除销售车辆
     * @Date  2020/10/13 14:53
     * @Param [enter]
     * @return
     **/
    GeneralResult deleteSaleScooter(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  销售车辆状态的编辑（不管是开启还是关闭都是这个接口）
     * @Date  2020/10/13 14:53
     * @Param [enter]
     * @return
     **/
    GeneralResult editSaleScooterStatus(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  销售车辆列表
     * @Date  2020/10/13 15:51
     * @Param [enter]
     * @return
     **/
    PageResult<SaleScooterListResult> saleScooterList(SaleScooterListEnter enter);


    /**
     * @Author Aleks
     * @Description  列表统计
     * @Date  2020/10/13 17:53
     * @Param [enter]
     * @return
     **/
    Map<String,Integer> listCount(GeneralEnter enter);

}
