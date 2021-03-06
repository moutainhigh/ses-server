package com.redescooter.ses.mobile.rps.dao.preparematerial;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.mobile.rps.dm.OpeStockProdPart;
import com.redescooter.ses.mobile.rps.dm.OpeStockPurchas;
import com.redescooter.ses.mobile.rps.vo.bo.QueryStockBillDto;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialListResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName:PrepareMaterialService
 * @description: PrepareMaterialService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/15 11:20
 */
public interface PrepareMaterialServiceMapper {

    /**
     * 详情列表数据
     *
     * @param enter
     * @return
     */
    int detailAllocateListCount(PrepareMaterialDetailEnter enter);

    /**
     * 详情列表数据
     *
     * @param enter
     * @return
     */
    List<PrepareMaterialDetailResult> detailAllocatelList(PrepareMaterialDetailEnter enter);

    /**
     * 组装单单列表
     *
     * @param enter
     * @return
     */
    List<PrepareMaterialListResult> assemblyList(PageEnter enter);

    /**
     * 查询采购出库单、组装出库单
     *
     * @param enter
     * @return
     */
    int allocatListCount(PageEnter enter);

    /**
     * 调拨单列表
     *
     * @param enter
     * @return
     */
    List<PrepareMaterialListResult> allocatList(PageEnter enter);

    /**
     * 组装单 数量统计
     *
     * @param enter
     * @return
     */
    int assemblyListCount(PageEnter enter);

    /**
     * 组装单备料详情列表统计
     *
     * @param enter
     * @return
     */
    int detailAssemblyListCount(PrepareMaterialDetailEnter enter);

    /**
     * 组装单备料详情列表
     *
     * @param enter
     * @return
     */
    List<PrepareMaterialDetailResult> detailAssemblyList(PrepareMaterialDetailEnter enter);

    /**
     * 出库单据
     *
     * @param id
     * @param value
     * @return
     */
    List<QueryStockBillDto> queryStockBillBySourceTypeId(@Param("id") Long id, @Param("value") String value);

    /**
     * 组装备料序列号
     * 
     * @param id
     * @return
     */
    List<OpeStockProdPart> queryStockProductPartSerialNList(Long id);

    /**
     * 调拨备料序列号
     * 
     * @param id
     * @return
     */
    List<OpeStockPurchas> queryStockPurchasSerialNList(Long id);
}
