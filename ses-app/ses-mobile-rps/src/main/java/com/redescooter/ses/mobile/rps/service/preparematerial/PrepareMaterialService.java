package com.redescooter.ses.mobile.rps.service.preparematerial;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialListResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.SavePrepareMaterialEnter;

import java.util.List;

/**
 * @ClassName:PrepareMaterialService
 * @description: PrepareMaterialService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 19:07
 */
public interface PrepareMaterialService {
    /**
     * 待备料列表
     *
     * @param enter
     * @return
     */
    PrepareMaterialListResult list(GeneralEnter enter);

    /**
     * 待备料详情
     *
     * @param enter
     * @return
     */
    List<PrepareMaterialDetailResult> detail(GeneralEnter enter);

    /**
     * 保存
     * @return
     */
    GeneralResult save(SavePrepareMaterialEnter enter);
}
