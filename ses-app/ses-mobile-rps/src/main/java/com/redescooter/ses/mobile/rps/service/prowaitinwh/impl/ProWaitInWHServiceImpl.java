package com.redescooter.ses.mobile.rps.service.prowaitinwh.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.dao.prowaitinwh.ProWaitInWHMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyBOrder;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrder;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyBOrderService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.mobile.rps.service.prowaitinwh.ProWaitInWHService;
import com.redescooter.ses.mobile.rps.vo.prowaitinwh.*;
import com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcPartResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassNameProWaitInWHServiceImpl
 * @Description
 * @Author kyle
 * @Date2020/4/14 17:52
 * @Version V1.0
 **/
@Service
public class ProWaitInWHServiceImpl implements ProWaitInWHService {


    @Autowired
    private ProWaitInWHMapper proWaitInWHMapper;

    @Autowired
    private OpeAssemblyOrderService opeAssemblyOrderService;

    @Autowired
    private OpeAssemblyBOrderService opeAssemblyBOrderService;



    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、查询生产仓库待入库列表
     * @Date 2020/4/14 17:51
     * @Param [enter]
     */
    @Override
    public PageResult<ProWaitInWHLOneResult> proWaitInWHList(PageEnter enter) {


        int count = proWaitInWHMapper.proWaitInWHListCount();
        List<ProWaitInWHLOneResult> proWaitInWHLOneResults = new ArrayList<>();
        ProWaitInWHLOneResult proWaitInWHLOneResult = null;
        //opeAssemblyOrderService对应的数据库表为空的时候直接返回
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        } else {
            QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
            //待入库数量不为0
            opeAssemblyOrderQueryWrapper.ne(OpeAssemblyOrder.COL_IN_WAIT_WH_TOTAL,0);
            List<OpeAssemblyOrder> opeAssemblyOrderList = opeAssemblyOrderService.list(opeAssemblyOrderQueryWrapper);
            if (!CollectionUtils.isEmpty(opeAssemblyOrderList)) {
                for (OpeAssemblyOrder opeAssemblyOrder : opeAssemblyOrderList) {
                    proWaitInWHLOneResults.add(
                            proWaitInWHLOneResult = ProWaitInWHLOneResult.builder()
                                    .scooterId(opeAssemblyOrder.getId())
                                    .waitInWHNum(opeAssemblyOrder.getInWaitWhTotal())
                                    .waitInWHStr(opeAssemblyOrder.getAssemblyNumber())
                                    .inWHTListTime(new Date())
                                    .build());
                }
            } else {
                return PageResult.createZeroRowResult(enter);
            }
        }
        return PageResult.create(enter, count,proWaitInWHLOneResults);
    }

    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、根据组装单id查询对应的待入库商品部件详情列表
     * @Date 2020/4/14 17:49
     * @Param [enter]
     */
    @Override
    public PageResult<ProWaitInWHItemResult> proWaitWHItemList(ProWaitInWHIdEnter enter) {
        int count = proWaitInWHMapper.proWaitWHItemListCount();
        ProWaitInWHItemResult proWaitInWHItemResult = null;
        List<ProWaitInWHItemResult> proWaitWHItemListResult = new ArrayList<>();
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        } else {
            QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
            opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_PRODUCT_ID, enter.getPartId());
            //通过组装单子表id查找
            opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getScooterBId());
            List<OpeAssemblyBOrder> opeAssemblyBOrderList = opeAssemblyBOrderService.list(opeAssemblyBOrderQueryWrapper);
            if (!CollectionUtils.isEmpty(opeAssemblyBOrderList)) {
                for (OpeAssemblyBOrder opeAssemblyBOrder : opeAssemblyBOrderList) {
                    proWaitWHItemListResult.add(
                            proWaitInWHItemResult = ProWaitInWHItemResult.builder()
                                    .id(opeAssemblyBOrder.getId())
                                    .scooterBId(opeAssemblyBOrder.getAssemblyId())
                                    .partId(opeAssemblyBOrder.getProductId())
                                    .partNum(opeAssemblyBOrder.getLaveWaitQcQty())
                                    .partStr(opeAssemblyBOrder.getProductNumber())
                                    .partName(opeAssemblyBOrder.getEnName())
                                    .build());
                }
                ;
            } else {
                return PageResult.createZeroRowResult(enter);
            }
        }

        return PageResult.create(enter,1,proWaitWHItemListResult);
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
                .proTime(new Date())
                .build();

        return proWaitInWHInfoResult;
    }


}
