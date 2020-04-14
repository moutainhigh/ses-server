package com.redescooter.ses.mobile.rps.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.vo.materialqc.RpsHeadResult;

/**
 * @ClassName:RpsHeadService
 * @description: RpsHeadService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 16:48
 */
public interface RpsHeadService {
    /**
     * rps 首页数据
     *
     * @param enter
     * @return
     */
    RpsHeadResult rpsHead(GeneralEnter enter);
}
