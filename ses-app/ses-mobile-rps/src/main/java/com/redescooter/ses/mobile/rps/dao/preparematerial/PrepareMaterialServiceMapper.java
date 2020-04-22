package com.redescooter.ses.mobile.rps.dao.preparematerial;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.mobile.rps.dm.OpeStockBill;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialListResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    int allocateListCount(PrepareMaterialDetailEnter enter);

    /**
     * 详情列表数据
     *
     * @param enter
     * @return
     */
    List<PrepareMaterialDetailResult> allocatelList(PrepareMaterialDetailEnter enter);

    /**
     * 查询采购出库单、组装出库单
     *
     * @param enter
     * @return
     */
    List<OpeStockBill> stockBillList(PageEnter enter);

    /**
     * 组装单备料详情列表统计
     *
     * @param enter
     * @return
     */
    int assemblyListCount(PrepareMaterialDetailEnter enter);

    /**
     * 组装单备料详情列表
     *
     * @param enter
     * @return
     */
    List<PrepareMaterialDetailResult> assemblyList(PrepareMaterialDetailEnter enter);
}
