package com.redescooter.ses.web.ros.service.restproductionorder.inwhouse;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.KeywordEnter;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

/**
 * @ClassNameInWhouseService
 * @Description
 * @Author Aleks
 * @Date2020/11/11 10:20
 * @Version V1.0
 **/
public interface InWhouseService {

    /**
     * @Author Aleks
     * @Description  入库单列表接口
     * @Date  2020/11/11 10:50
     * @Param [enter]
     * @return
     **/
    PageResult<InWhouseListResult> inWhouseList(InWhouseListEnter enter);

    /**
     * @Author Aleks
     * @Description 入库单新增
     * @Date  2020/11/11 11:44
     * @Param
     * @return
     **/
    GeneralResult inWhouseSave(InWhouseSaveOrUpdateEnter enter);

    /**
     * @Author Aleks
     * @Description 入库单编辑
     * @Date  2020/11/11 11:44
     * @Param
     * @return
     **/
    GeneralResult inWhouseEdit(InWhouseSaveOrUpdateEnter enter);

    /**
     * @Author Aleks
     * @Description  列表统计
     * @Date  2020/11/11 12:58
     * @Param
     * @return
     **/
    Map<String,Integer> listCount(GeneralEnter enter);

    /**
     * @Author Aleks
     * @Description 入库单删除
     * @Date  2020/11/11 13:52
     * @Param
     * @return
     **/
    GeneralResult inWhouseDelete(IdEnter enter);

    /**
     * @Author Aleks
     * @Description  入库单详情
     * @Date  2020/11/11 14:42
     * @Param
     * @return
     **/
    InWhouseDetailResult inWhouseDetail(IdEnter enter);

    /**
     * @Author Aleks
     * @Description  入库确认
     * @Date  2020/11/11 14:45
     * @Param
     * @return
     **/
    GeneralResult inWhConfirm(IdEnter enter);

    /**
     * @Author Aleks
     * @Description 入库单准备质检
     * @Date  2020/11/11 14:52
     * @Param
     * @return
     **/
    GeneralResult inWhReadyQc(IdEnter enter);

    /**
     * @Author Aleks
     * @Description 关联的生产采购单单据号下拉数据源
     * @Date  2020/11/11 15:49
     * @Param
     * @return
     **/
    List<InWhRelationOrderResult> relationPurchaseOrderData(KeywordEnter enter);

    /**
     * @Author Aleks
     * @Description  采购单部件的产品信息
     * @Date  2020/11/11 16:11
     * @Param
     * @return
     **/
    List<SaveOrUpdatePartsBEnter> relationPurchaseOrderPartsData(IdEnter enter);

    /**
     * @Author Aleks
     * @Description  关联的组装单据号下拉数据源
     * @Date  2020/11/11 16:07
     * @Param [enter]
     * @return
     **/
    List<InWhRelationOrderResult> relationCombinOrderData(KeywordEnter enter);

    /**
     * @Author Aleks
     * @Description 关联的组装单的组装件产品信息
     * @Date  2020/11/11 16:24
     * @Param
     * @return
     **/
    List<SaveOrUpdateCombinBEnter> relationCombinOrderCombinData(IdEnter enter);

    /**
     * @Author Aleks
     * @Description  关联的组装单的整车产品信息
     * @Date  2020/11/11 16:28
     * @Param
     * @return
     **/
    List<SaveOrUpdateScooterBEnter> relationCombinOrderScooterData(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  模拟RPS的操作  开始质检
     * @Date  2020/11/16 11:12
     * @Param [enter]
     * @return
     **/
    GeneralResult startQc(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  模拟RPS的操作  完成质检
     * @Date  2020/11/16 11:12
     * @Param [enter]
     * @return
     **/
    GeneralResult finishQc(IdEnter enter);

}
