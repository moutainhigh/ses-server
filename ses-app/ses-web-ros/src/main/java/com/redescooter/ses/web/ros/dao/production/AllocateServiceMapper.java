package com.redescooter.ses.web.ros.dao.production;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.production.ProductPartsListEnter;
import com.redescooter.ses.web.ros.vo.production.ProductPartsResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderNodeResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderPartResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName:AllocateServiceMapper
 * @description: AllocateServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/28 14:41
 */
public interface AllocateServiceMapper {

    /**
     * 类型统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> countByType(GeneralEnter enter);

    /**
     * 列表统计
     *
     * @param enter
     * @return
     */
    int allocateListCount(@Param("enter") AllocateOrderEnter enter, @Param("statusList") List<String> statusList);

    /**
     * 列表
     *
     * @param enter
     * @return
     */
    List<AllocateOrderResult> allocateList(@Param("enter") AllocateOrderEnter enter, @Param("statusList") List<String> statusList);

    /**
     * detail
     *
     * @param enter
     * @return
     */
    AllocateOrderResult detail(IdEnter enter);

    /**
     * 调拨单节点
     *
     * @param enter
     * @return
     */
    List<AllocateOrderNodeResult> allocateOrderNodeList(IdEnter enter);

    /**
     * 调拨订单 商品条目
     *
     * @param enter
     */
    List<AllocateOrderPartResult> allocateOrderDetailPartsList(IdEnter enter);

    /**
     * 新增调拨单 部件列表
     *
     * @param enter
     * @return
     */
    List<ProductPartsResult> allocatePartsList(ProductPartsListEnter enter);
}
