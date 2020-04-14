package com.redescooter.ses.mobile.rps.service.prowaitinwh.impl;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.service.prowaitinwh.ProWaitInWHService;
import com.redescooter.ses.mobile.rps.vo.prowaitinwh.*;
import com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcItemResult;
import org.apache.dubbo.config.annotation.Service;

import java.util.Arrays;
import java.util.Date;

/**
 * @ClassNameProWaitInWHServiceImpl
 * @Description
 * @Author kyle
 * @Date2020/4/14 17:52
 * @Version V1.0
 **/
@Service
public class ProWaitInWHServiceImpl implements ProWaitInWHService {


    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、查询生产仓库待入库列表
     * @Date 2020/4/14 17:51
     * @Param [enter]
     */
    @Override
    public PageResult<ProWaitInWHListResult> proWaitInWHList(PageEnter enter) {
        ProWaitInWHLOneResult proWaitInWHLOneResult = ProWaitInWHLOneResult.builder()
                .id(1L)
                .waitInWHStr("REDE_INWH_01")
                .waitInWHNum(520)
                .scooterId(1L)
                .inWHTListTime(new Date())
                .scooterId(1L)
                .build();

        ProWaitInWHListResult proWaitInWHListResult = ProWaitInWHListResult.builder()
                .id(1L)
                .scooterId(1L)
                .proWaitInWHLOneResultList(Arrays.asList(proWaitInWHLOneResult))
                .build();

        return PageResult.create(enter,10,Arrays.asList(proWaitInWHListResult));
    }

    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、根据组装单id查询对应的部件详情列表
     * @Date 2020/4/14 17:49
     * @Param [enter]
     */
    @Override
    public ProWaitWHItemListResult proWaitWHItemList(IdEnter enter) {
        ProWaitInWHItemResult proWaitInWHItemResult = ProWaitInWHItemResult.builder()
                .id(1L)
                .scooterId(1L)
                .scooterNum("#REDE_SCOOTERQC_01")
                .waitInWHNum(2000)
                .partId(1L)
                .partName("轮胎")
                .partType("120/70-14")
                .build();

        ProWaitWHItemListResult proWaitWHItemListResult = ProWaitWHItemListResult.builder()
                .id(1L)
                .proWaitInWHItemResultList(Arrays.asList(proWaitInWHItemResult))
                .build();
        return proWaitWHItemListResult;
    }

    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、根据具体的部品id查询生产仓库待入库详情
     * @Date 2020/4/14 17:50
     * @Param [enter]
     */
    @Override
    public ProWaitInWHInfoResult proWaitInWHInfoOut(IdEnter enter) {
        ProWaitInWHInfoResult proWaitInWHInfoResult = ProWaitInWHInfoResult.builder()
                .id(1L)
                .partNum("REDE_PART_01")
                .partName("轮胎")
                .scooterId(1L)
                .partId(1L)
                .batchNum("REDE_BATCH_01")
                .shouldInWHNum(100)
                .inWHNum(0)
                .residueNum(100)
                .build();


        return proWaitInWHInfoResult;
    }

    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、提交生产仓库入库信息
     * @Date 2020/4/14 17:52
     * @Param [enter]
     */
    @Override
    public ProWaitInWHInfoResult proWaitInWHInfoIn(ProWaitInWHInfoEnter enter) {

        ProWaitInWHInfoResult proWaitInWHInfoResult = ProWaitInWHInfoResult.builder()
                .id(1L)
                .partNum("REDE_PART_01")
                .partName("轮胎")
                .scooterId(1L)
                .partId(1L)
                .batchNum("REDE_BATCH_01")
                .shouldInWHNum(100)
                .inWHNum(70)
                .residueNum(30)
                .build();


        return proWaitInWHInfoResult;
    }


}
