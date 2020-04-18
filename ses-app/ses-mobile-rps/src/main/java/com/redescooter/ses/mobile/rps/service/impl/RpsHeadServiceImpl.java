package com.redescooter.ses.mobile.rps.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.dao.RpsHeadServiceMapper;
import com.redescooter.ses.mobile.rps.service.RpsHeadService;
import com.redescooter.ses.mobile.rps.vo.materialqc.RpsHeadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName:RpsHeadServiceImpl
 * @description: RpsHeadServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 11:46
 */
@Service
public class RpsHeadServiceImpl implements RpsHeadService {

    @Autowired
    private RpsHeadServiceMapper rpsHeadServiceMapper;
    /**
     * rps 首页数据
     *
     * @param enter
     * @return
     */
    @Override
    public RpsHeadResult rpsHead(GeneralEnter enter) {
        return RpsHeadResult.builder()
                //来料质检
                .materialQcTotal(rpsHeadServiceMapper.rpsHeadMaterialsQc(enter))
                //采购入库
                .purchasInWhTotal(rpsHeadServiceMapper.rpsHeadPurchasInWh(enter))
                //带备料
                .prepareMaterialTotal(rpsHeadServiceMapper.rpsHeadPrepare(enter))
                //整车组装
                .assemblyTotal(rpsHeadServiceMapper.rpsHeadAssembly(enter))
                //整车质检
                .scooterQcTotal(rpsHeadServiceMapper.rpsHeadProductQc(enter))
                //生产入库
                .productionInWhTotal(rpsHeadServiceMapper.rpsHeadProductionInWh(enter))
                .build();
    }
}
