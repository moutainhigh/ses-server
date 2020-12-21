package com.redescooter.ses.web.ros.service.distributor;

import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorAddEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorCityAndCPSelectorEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorEnableOrDisableEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorListEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorNameEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorUpdateEnter;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorCityAndCPSelectorResult;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorDetailResult;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorListResult;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorSaleProductResult;

import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2020/12/16 15:58
 */
public interface DistributorService {

    /**
     * 门店列表
     */
    Response<PageResult<DistributorListResult>> getList(DistributorListEnter enter);

    /**
     * 城市下拉框,邮政编码下拉框,城市和邮政编码联动
     */
    Response<DistributorCityAndCPSelectorResult> getCityAndCPSelector(DistributorCityAndCPSelectorEnter enter);

    /**
     * 启用/停用门店
     */
    Response<GeneralResult> enableOrDisable(DistributorEnableOrDisableEnter enter);

    /**
     * 新增门店
     */
    Response<GeneralResult> add(DistributorAddEnter enter);

    /**
     * 编辑门店
     */
    Response<GeneralResult> update(DistributorUpdateEnter enter);

    /**
     * 删除门店
     */
    Response<GeneralResult> delete(IdEnter enter);

    /**
     * 门店详情
     */
    Response<DistributorDetailResult> getDetail(IdEnter enter);

    /**
     * 门店名称
     */
    Response<BooleanResult> getNameList(DistributorNameEnter enter);

    /**
     * 门店类型选择销售,可售卖产品的数据来源
     */
    Response<List<DistributorSaleProductResult>> getSaleProduct(GeneralEnter enter);

}
