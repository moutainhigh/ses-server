package com.redescooter.ses.mobile.rps.dao.preparematerial;

import com.redescooter.ses.api.common.vo.base.PageEnter;
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
     * 带备料列表统计
     *
     * @param enter
     * @return
     */
    int listCount(PageEnter enter);

    /**
     * 列表统计
     *
     * @param enter
     * @return
     */
    List<PrepareMaterialListResult> list(PageEnter enter);

    /**
     * 详情列表数据
     *
     * @param enter
     * @return
     */
    int detailListCount(PrepareMaterialDetailEnter enter);

    /**
     * 详情列表数据
     *
     * @param enter
     * @return
     */
    List<PrepareMaterialDetailResult> detailList(PrepareMaterialDetailEnter enter);
}
