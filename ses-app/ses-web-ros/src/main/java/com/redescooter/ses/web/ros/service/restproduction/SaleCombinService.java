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
 * @Date 2020/10/20 10:28
 * @Param
 * @return
 **/
public interface SaleCombinService {

    /**
     * @return
     * @Author Aleks
     * @Description 销售组装件新增
     * @Date 2020/10/20 11:45
     * @Param [enter]
     **/
    GeneralResult saveSaleCombin(SaleCombinSaveOrUpdateEnter enter);


    /**
     * @return
     * @Author Aleks
     * @Description 销售组装件列表
     * @Date 2020/10/20 11:46
     * @Param [enter]
     **/
    PageResult<SaleCombinListResult> saleCombinList(SaleListEnter enter);


    /**
     * @Author Aleks
     * @Description  销售组装件编辑
     * @Date  2020/10/20 11:47
     * @Param [enter]
     * @return
     **/
    GeneralResult editSaleCombin(SaleCombinSaveOrUpdateEnter enter);


    /**
     * @Author Aleks
     * @Description  删除销售组装件
     * @Date  2020/10/20 11:49
     * @Param [enter]
     * @return
     **/
    GeneralResult deleteSaleCombin(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  编辑组装件的销售状态
     * @Date  2020/10/20 11:51
     * @Param [enter]
     * @return
     **/
    GeneralResult editSaleCombinStatus(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  组装件下拉数据接口
     * @Date  2020/10/20 13:10
     * @Param [enter]
     * @return
     **/
    List<CombinNameData> combinNameData(GeneralEnter enter);


    /**
     * @Author Aleks
     * @Description  组装件编号下拉数据源接口
     * @Date  2020/10/20 13:19
     * @Param [enter]
     * @return
     **/
    List<BomNameData> bomNoData(BomNoEnter enter);
}
