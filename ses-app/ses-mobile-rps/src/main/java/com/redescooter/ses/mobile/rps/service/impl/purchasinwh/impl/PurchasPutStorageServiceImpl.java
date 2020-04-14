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
                .inWaitWhTotal("8999").build();
                int count=1;

        return PageResult.create(enter,count, Arrays.asList(outStorageResult));
    }

    @Override
    public PageResult<PurchasDetailsListResult> storageDetailsList(PurchasDetailsEnter enter) {
/*        int count= purchasPutStorageMapper.purchasDetailListCount();
        if (count==0){
            return PageResult.createZeroRowResult(enter);

        }*/
        PurchasDetailsListResult purchasDetailsListResult=PurchasDetailsListResult.builder()
                .contractNo("UUU89879977")
                .inWaitWhQty("888")
                .partsNumber("OOOIIHYYYH")
                .cnName("小花").build();
        int count=1;
        //return PageResult.create(enter,count, purchasPutStorageMapper.storageDetailsResult(enter)) ;
        return PageResult.create(enter,count, Arrays.asList(purchasDetailsListResult));
    }

    @Override
    public PageResult<HaveIDPartsResult> haveIDPartsResult(PurchasDetailsEnter enter) {
        HaveIDPartsResult haveIDPartsResult=HaveIDPartsResult.builder()
                .serialNum("IUHUGHU093")
                .batchNo("UUU89879977")
                .inWaitWhQty("888")
                .partsNumber("OOOIIHYYYH")
                .cnName("小花").build();
        int count=1;
        //return PageResult.create(enter,count, purchasPutStorageMapper.storageDetailsResult(enter)) ;
        return PageResult.create(enter,count, Arrays.asList(haveIDPartsResult));
    }

    @Override
    public PageResult<NotIDPartsResult> notIDPartsResult(PurchasDetailsEnter enter) {
        NotIDPartsResult notIDPartsResult=NotIDPartsResult.builder()
                .batchNo("UUU89879977")
                .partsNumber("OOOIIHYYYH")
                .inWaitWhQty("22")
                .cnName("小花").build();
        int count=1;
        //return PageResult.create(enter,count, purchasPutStorageMapper.storageDetailsResult(enter)) ;
        return PageResult.create(enter,count, Arrays.asList(notIDPartsResult));
    }

    @Override
    public PageResult<NotIDPartsSucceedResult> otIDPartsSucceedResult(PurchasDetailsEnter enter) {
        NotIDPartsSucceedResult notIDPartsSucceedResult=NotIDPartsSucceedResult.builder()
                .batchNo("UUU89879977")
                .partsNumber("OOOIIHYYYH")
                .inWaitWhQty("88")
                .cnName("小花").build();
        int count=1;
        //return PageResult.create(enter,count, purchasPutStorageMapper.storageDetailsResult(enter)) ;
        return PageResult.create(enter,count, Arrays.asList(notIDPartsSucceedResult));
    }


}
