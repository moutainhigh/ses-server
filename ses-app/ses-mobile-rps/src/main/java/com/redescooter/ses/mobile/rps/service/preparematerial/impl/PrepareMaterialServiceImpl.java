package com.redescooter.ses.mobile.rps.service.preparematerial.impl;

import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.service.preparematerial.PrepareMaterialService;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialListResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.SavePrepareMaterialEnter;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        return PageResult.create(enter,1, Lists.newArrayList(PrepareMaterialListResult.builder()
                .id(1312321L)
                .allocatN("dasdasda")
                .createdTime(new Date())
                .preparationWaitTotal(0)
                .build()));
    }

    /**
     * 待备料详情
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<PrepareMaterialDetailResult> detail(PrepareMaterialDetailEnter enter) {
        return PageResult.create(enter,1,Lists.newArrayList(PrepareMaterialDetailResult.builder()
                .id(423432L)
                .partId(443432L)
                .partCnName("轮胎")
                .preparationWaitQty(0)
                .build()));
    }

    /**
     * 保存
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(SavePrepareMaterialEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }
}
