package com.redescooter.ses.web.ros.service.wms;

import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.vo.wms.StartWhOutOrderEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutConsigneeResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailProductPartListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailProductPartListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutOrderListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutOrderListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutProductListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutProductListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutSaveEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutWhResult;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:WhOutService
 * @description: WhOutService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 10:29
 */
public interface WhOutService {
    /**
     * 出库单列表
     *
     * @param enter
     * @return
     */
    PageResult<WhOutOrderListResult> whOrderList(WhOutOrderListEnter enter);

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    WhOutDetailResult detail(IdEnter enter);

    /**
     * 订单节点
     *
     * @param enter
     * @return
     */
    CommonNodeResult nodeDetail(IdEnter enter);

    /**
     * 详情产品列表
     *
     * @param enter
     * @return
     */
    PageResult<WhOutDetailProductPartListResult> detailProductPartList(WhOutDetailProductPartListEnter enter);

    /**
     * 取消出库单
     *
     * @param enter
     * @return
     */
    GeneralResult cancel(IdEnter enter);

    /**
     * 开始出库单
     *
     * @param enter
     * @return
     */
    GeneralResult start(StartWhOutOrderEnter enter);

    /**
     * 确认备料
     * @param enter
     * @return
     */
    GeneralResult prepareMaterial(IdEnter enter);

    /**
     * 入库
     * @param enter
     * @return
     */
    GeneralResult inWh(IdEnter enter);

    /**
     * 保存
     * @param enter
     * @return
     */
    GeneralResult save(WhOutSaveEnter enter);

    /**
     * 收件人集合
     * @param enter
     * @return
     */
    List<WhOutConsigneeResult> consigneeList(GeneralEnter enter);

    /**
     * 仓库列表
     * @param enter
     * @return
     */
    List<WhOutWhResult> whList(GeneralEnter enter);

    /**
     * 发货方式
     * @param enter
     * @return
     */
    Map<String,String> consignType(GeneralEnter enter);

    /**
     *  委托方式
     * @param enter
     * @return
     */
    Map<String,String> consignMethod(StringEnter enter);

    /**
     * 订单状态列表
     * @param enter
     * @return
     */
    Map<String,String> statusList(GeneralEnter enter);

    /**
     * 订单状态统计
     * @param enter
     * @return
     */
    Map<String,Integer> statusByCount(GeneralEnter enter);
    /**
     * 产品列表
     * @param enter
     * @return
     */
    PageResult<WhOutProductListResult> productList(WhOutProductListEnter enter);
}

