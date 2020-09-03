package com.redescooter.ses.web.ros.service.wms.cn;

import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.StartWhOutOrderEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutConsigneeResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutDetailProductPartListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutDetailProductPartListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutDetailResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutOrderListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutOrderListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutProductListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutProductListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutSaveEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutWhResult;

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
    List<CommonNodeResult> nodeList(IdEnter enter);

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
     *
     * @param enter
     * @return
     */
    GeneralResult prepareMaterial(IdEnter enter);

    /**
     * 出库
     *
     * @param enter
     * @return
     */
    GeneralResult outwh(IdEnter enter);

    /**
     * 入库
     *
     * @param enter
     * @return
     */
    GeneralResult inWh(IdEnter enter);

    /**
     * 保存
     *
     * @param enter
     * @return
     */
    GeneralResult save(WhOutSaveEnter enter);

    /**
     * 收件人集合
     *
     * @param enter
     * @return
     */
    List<WhOutConsigneeResult> consigneeList(GeneralEnter enter);

    /**
     * 仓库列表
     *
     * @param enter
     * @return
     */
    List<WhOutWhResult> whList(GeneralEnter enter);

    /**
     * 发货方式
     *
     * @param enter
     * @return
     */
    Map<String, String> consignType(GeneralEnter enter);

    /**
     * 委托方式
     *
     * @param enter
     * @return
     */
    Map<String, String> consignMethod(StringEnter enter);

    /**
     * 订单状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> statusByCount(GeneralEnter enter);

    /**
     * 产品列表
     *
     * @param enter
     * @return
     */
    PageResult<WhOutProductListResult> productList(WhOutProductListEnter enter);

    /**
     * 保存订单节点
     *
     * @param enter
     * @return
     */
    GeneralResult saveNode(SaveNodeEnter enter);

    /**
     * 出库单库存锁定
     *
     * @param ids
     * @return
     */
    void lockStock(List<Long> ids);
}

