package com.redescooter.ses.mobile.rps.dao.material;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.mobile.rps.dm.OpePurchasB;
import com.redescooter.ses.mobile.rps.vo.bo.RpsPartDetailDto;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialDetailResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialQcDetailEnter;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialQcListResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:MaterialServiceMapper
 * @description: MaterialServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 13:57
 */
public interface MaterialServiceMapper {
    /**
     * 列表数据
     *
     * @param enter
     * @return
     */
    List<MaterialQcListResult> list(PageEnter enter);

    /**
     * 详情列表
     *
     * @param enter
     * @return
     */
    int materialQcDetailListCount(MaterialQcDetailEnter enter);

    /**
     * 来料质检详情
     *
     * @param enter
     * @return
     */
    List<MaterialDetailResult> detailList(MaterialQcDetailEnter enter);

    /**
     * 质检失败列表
     *
     * @param enter
     * @return
     */
    int failListCount(PageEnter enter);

    /**
     * 质检失败列表
     *
     * @param enter
     * @return
     */
    List<MaterialQcListResult> failList(PageEnter enter);

    /**
     * @param enter
     * @return
     */
    int qcFailDetailCount(MaterialQcDetailEnter enter);

    /**
     * 质检失败详情
     *
     * @param enter
     * @return
     */
    List<MaterialDetailResult> qcFailDetail(MaterialQcDetailEnter enter);

    /**
     * @param enter
     * @return
     */
    List<OpePurchasB> qcFailPurchasBList(GeneralEnter enter);

    /**
     * 查询产品价格
     *
     * @param longs
     * @return
     */
    List<RpsPartDetailDto> partDetailById(ArrayList<Long> longs);
}
