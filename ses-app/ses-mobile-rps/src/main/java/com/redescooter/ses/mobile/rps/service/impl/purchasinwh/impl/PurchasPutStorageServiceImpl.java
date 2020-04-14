package com.redescooter.ses.mobile.rps.service.impl.purchasinwh.impl;

import cn.hutool.db.Page;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.dao.purchasinwh.PurchasPutStorageMapper;
import com.redescooter.ses.mobile.rps.service.impl.purchasinwh.PurchasPutStroageService;
import com.redescooter.ses.mobile.rps.vo.purchasinwh.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
class PurchasPutStorageServiceImpl implements PurchasPutStroageService {
    @Autowired
    private PurchasPutStorageMapper purchasPutStorageMapper;

    @Reference
    private IdAppService idAppService;

    @Override
    public PageResult<PutStorageResult> purchasPutStroageList(PageEnter enter) {
/*      int count= purchasPutStorageMapper.purchasListCount();
        if (count==0){
            return PageResult.createZeroRowResult(enter);

        }*/
        //return  PageResult.create(enter,count, purchasPutStorageMapper.putStorageResult());\

                PutStorageResult outStorageResult= PutStorageResult.builder()
                .createTime(new Date())
                .contractNo("YUUG0989821")
                .inWaitWhTotal("899").build();


        PutStorageResult outStorageResult1= PutStorageResult.builder()
                .createTime(new Date())
                .contractNo("YUUG088854")
                .inWaitWhTotal("66").build();
                int count=1;

        return PageResult.create(enter,count, Arrays.asList(outStorageResult,outStorageResult1));
    }

    @Override
    public PageResult<PurchasDetailsListResult> storageDetailsList(PurchasDetailsEnter enter) {
/*        int count= purchasPutStorageMapper.purchasDetailListCount();
        if (count==0){
            return PageResult.createZeroRowResult(enter);

        }*/
        PurchasDetailsListResult purchasDetailsListResult=PurchasDetailsListResult.builder()
                .contractNo("YUUG088854")
                .inWaitWhQty("888")
                .partsNumber("OOOIIHYYYH")
                .cnName("轮胎").build();
        PurchasDetailsListResult purchasDetailsListResult1=PurchasDetailsListResult.builder()
                .contractNo("UUU8")
                .inWaitWhQty("888")
                .partsNumber("OOOIIHYYYH")
                .cnName("螺丝").build();
        int count=1;
        //return PageResult.create(enter,count, purchasPutStorageMapper.storageDetailsResult(enter)) ;
        return PageResult.create(enter,count, Arrays.asList(purchasDetailsListResult,purchasDetailsListResult1));
    }

    @Override
    public HaveIDPartsResult haveIDPartsResult(PurchasDetailsEnter enter) {
        HaveIDPartsResult haveIDPartsResult=HaveIDPartsResult.builder()
                .serialNum("IUHUGHU066")
                .batchNo("YUUG088854")
                .inWaitWhQty("888")
                .partsNumber("OOOIIHYYYH")
                .cnName("螺丝").build();

        //return PageResult.create(enter,count, purchasPutStorageMapper.storageDetailsResult(enter)) ;
        return haveIDPartsResult;
    }

    @Override
    public NotIDPartsResult notIDPartsResult(PurchasDetailsEnter enter) {
        NotIDPartsResult notIDPartsResult=NotIDPartsResult.builder()
                .batchNo("UUU89879977")
                .partsNumber("OOOIIHYYYH")
                .inWaitWhQty("22")
                .cnName("轮胎").build();

        //return PageResult.create(enter,count, purchasPutStorageMapper.storageDetailsResult(enter)) ;
        return notIDPartsResult;
    }

    @Override
    public NotIDPartsSucceedResult otIDPartsSucceedResult(PurchasDetailsEnter enter) {
        NotIDPartsSucceedResult notIDPartsSucceedResult=NotIDPartsSucceedResult.builder()
                .batchNo("UUU89879977")
                .partsNumber("OOOIIHYYYH")
                .inWaitWhQty("88")
                .cnName("轮胎").build();
        //return PageResult.create(enter,count, purchasPutStorageMapper.storageDetailsResult(enter)) ;
        return notIDPartsSucceedResult;
    }


}
