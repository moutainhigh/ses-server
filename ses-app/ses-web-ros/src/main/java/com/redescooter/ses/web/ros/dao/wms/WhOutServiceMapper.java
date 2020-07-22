package com.redescooter.ses.web.ros.dao.wms;

import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.bo.PartDetailDto;
import com.redescooter.ses.web.ros.vo.wms.WhOutConsigneeResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailProductPartListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailProductPartListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutOrderListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutOrderListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutProductListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutProductListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutWhResult;

import java.util.List;

/**
 * @ClassName:WhOutServiceMapper
 * @description: WhOutServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 15:21
 */
public interface WhOutServiceMapper {


    /**
     * 出库单 统计
     *
     * @param enter
     * @return
     */
    Integer whOrderListCount(WhOutOrderListEnter enter);

    /**
     * 出库单列表
     *
     * @param enter
     * @return
     */
    List<WhOutOrderListResult> whOrderList(WhOutOrderListEnter enter);

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
     * 产品个数统计
     *
     * @param enter
     * @return
     */
    int detailProductPartListCount(WhOutDetailProductPartListEnter enter);

    /**
     * 详情中产品列表
     *
     * @param enter
     * @return
     */
    List<WhOutDetailProductPartListResult> detailProductPartList(WhOutDetailProductPartListEnter enter);

    /**
     * 产品列表
     *
     * @param enter
     * @return
     */
    Integer productListCount(WhOutProductListEnter enter);

    /**
     * 产品列表
     *
     * @param enter
     * @return
     */
    List<WhOutProductListResult> productListList(WhOutProductListEnter enter);

    /**
     * 收件人列表
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
     * 状态统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> statusByCount(GeneralEnter enter);

}
