package com.redescooter.ses.web.ros.dao.wms.cn;

import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutConsigneeResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutDetailProductPartListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutDetailProductPartListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutDetailResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutOrderListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutOrderListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutProductListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutProductListResult;
import org.apache.ibatis.annotations.Param;

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
     * @param statusList
     * @return
     */
    Integer whOrderListCount(@Param("enter") WhOutOrderListEnter enter, @Param("statusList") List<String> statusList);

    /**
     * 出库单列表
     *
     * @param enter
     * @param statusList
     * @return
     */
    List<WhOutOrderListResult> whOrderList(@Param("enter") WhOutOrderListEnter enter, @Param("statusList") List<String> statusList);

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
     * @param opeWhseList
     * @return
     */
    Integer productListCount(@Param("enter") WhOutProductListEnter enter, @Param("opeWhseList") List<OpeWhse> opeWhseList);

    /**
     * 产品列表
     *
     * @param enter
     * @param opeWhseList
     * @return
     */
    List<WhOutProductListResult> productListList(WhOutProductListEnter enter, List<OpeWhse> opeWhseList);

    /**
     * 收件人列表
     *
     * @param enter
     * @return
     */
    List<WhOutConsigneeResult> consigneeList(GeneralEnter enter);

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> statusByCount(GeneralEnter enter);

}
