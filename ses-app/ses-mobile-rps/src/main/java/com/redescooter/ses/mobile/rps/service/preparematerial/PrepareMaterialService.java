package com.redescooter.ses.mobile.rps.service.preparematerial;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.AllocatePreparationEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.AssemblyPreparationEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.ConfirmPreparationEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialListResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.SavePrepareMaterialEnter;

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
    PageResult<PrepareMaterialListResult> list(PageEnter enter);

    /**
     * 确认备料
     *
     * @param enter
     * @return
     */
    GeneralResult confirmPreparation(ConfirmPreparationEnter enter);

    /**
     * 待备料详情
     *
     * @param enter
     * @return
     */
    PageResult<PrepareMaterialDetailResult> detail(PrepareMaterialDetailEnter enter);

    /**
     * 保存
     *
     * @return
     */
    GeneralResult save(SavePrepareMaterialEnter enter);

    /**
     * 调拨单备料
     *
     * @param enter
     * @return
     */
    GeneralResult allocatePreparation(AllocatePreparationEnter enter);

    /**
     * 组装单备料
     *
    * @return
     */
    GeneralResult assemblyPreparation(AssemblyPreparationEnter enter);
}
