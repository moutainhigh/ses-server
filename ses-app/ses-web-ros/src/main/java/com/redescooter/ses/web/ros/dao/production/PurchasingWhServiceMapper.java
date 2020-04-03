package com.redescooter.ses.web.ros.dao.production;

import com.redescooter.ses.web.ros.dm.OpePurchasBQc;
import com.redescooter.ses.web.ros.vo.production.wh.AvailableListResult;
import com.redescooter.ses.web.ros.vo.production.wh.OutWhResult;
import com.redescooter.ses.web.ros.vo.production.wh.QcingListResult;
import com.redescooter.ses.web.ros.vo.production.wh.TobeStoredResult;
import com.redescooter.ses.web.ros.vo.production.wh.WasteResult;
import com.redescooter.ses.web.ros.vo.production.wh.WhEnter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName:PurchasingWhServiceMapper
 * @description: PurchasingWhServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/03 15:33
 */
public interface PurchasingWhServiceMapper {

    /**
     * 可用列表统计
     *
     * @param enter
     * @param whseId
     * @return
     */
    int availableListCount(@Param("enter") WhEnter enter, @Param("whseId") Long whseId);

    /**
     * 可用列表
     *
     * @param enter
     * @param whseId
     * @return
     */
    List<AvailableListResult> availableList(@Param("enter") WhEnter enter, @Param("whseId") Long whseId);

    /**
     * QC 列表
     *
     * @param enter
     * @return
     */
    int qcingListCount(WhEnter enter);

    /**
     * QC 列表
     *
     * @param enter
     * @return
     */
    List<QcingListResult> qcingList(WhEnter enter);

    /**
     * 待入库列表
     *
     * @param enter
     * @return
     */
    int tobeStoredListCount(WhEnter enter);

    /**
     * 待入库列表
     *
     * @param enter
     * @return
     */
    List<TobeStoredResult> tobeStoredList(WhEnter enter);

    /**
     * 出库列表
     *
     * @param enter
     * @return
     */
    int outWhListCount(@Param("enter") WhEnter enter, @Param("whseIds") List<Long> whseIds);

    /**
     * 出库列表
     *
     * @param enter
     * @return
     */
    List<OutWhResult> outWhList(@Param("enter") WhEnter enter, @Param("whseIds") List<Long> whseIds);

    /**
     * 废料列表
     *
     * @param enter
     * @return
     */
    int wasteListCount(WhEnter enter);

    /**
     * 废料列表
     *
     * @param enter
     * @return
     */
    List<WasteResult> wasteList(WhEnter enter);

    /**
     * 质检记录
     *
     * @param partIds
     * @param status
     * @return
     */
    List<OpePurchasBQc> PurchasBQc(@Param("partIds") List<Long> partIds, @Param("status") String status);
}
