package com.redescooter.ses.mobile.rps.service.preparematerial.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.service.preparematerial.PrepareMaterialService;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialListResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.SavePrepareMaterialEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @ClassName:PrepareMaterialServiceImpl
 * @description: PrepareMaterialServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 13:24
 */
@Service
public class PrepareMaterialServiceImpl implements PrepareMaterialService {

    /**
     * 待备料列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<PrepareMaterialListResult> list(PageEnter enter) {
        return null;
    }

    /**
     * 待备料详情
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<PrepareMaterialDetailResult> detail(PrepareMaterialDetailEnter enter) {
        return null;
    }

    /**
     * 保存
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(SavePrepareMaterialEnter enter) {
        return null;
    }
}
