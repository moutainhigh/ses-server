package com.redescooter.ses.mobile.rps.service.restproductionorder.qctrace.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dm.OpeOrderQcItem;
import com.redescooter.ses.mobile.rps.dm.OpeOrderQcTrace;
import com.redescooter.ses.mobile.rps.service.base.OpeOrderQcItemService;
import com.redescooter.ses.mobile.rps.service.base.OpeOrderQcTraceService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.qctrace.ProductQcTraceService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.qctrace.ProductQcTraceInfoResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.qctrace.ProductQcTraceListResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.qctrace.SaveProductQcTraceEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName:ProductQcTempleteServiceImpl
 * @description: ProductQcTempleteServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/04 18:35 
 */
@Service
@Slf4j
public class ProductQcTraceServiceImpl implements ProductQcTraceService {

    @Autowired
    private OpeOrderQcTraceService opeOrderQcTraceService;

    @Autowired
    private OpeOrderQcItemService opeOrderQcItemService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/4 6:36 下午
     * @Param: enter
     * @Return:
     * @desc: 保存质检记录
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(SaveProductQcTraceEnter enter) {
        OpeOrderQcItem opeOrderQcItem = new OpeOrderQcItem();
        BeanUtils.copyProperties(enter,opeOrderQcItem);
        Long opeOrderQcItemId = idAppService.getId(SequenceName.OPE_ORDER_QC_ITEM);
        opeOrderQcItem.setId(opeOrderQcItemId);
        opeOrderQcItem.setDr(0);
        opeOrderQcItem.setCreatedBy(enter.getUserId());
        opeOrderQcItem.setCreatedTime(new Date());
        opeOrderQcItem.setUpdatedBy(enter.getUserId());
        opeOrderQcItem.setUpdatedTime(new Date());
        opeOrderQcItemService.saveOrUpdate(opeOrderQcItem);

        List<OpeOrderQcTrace> saveOpeOrderQcTraceList = new ArrayList<>();
        enter.getSaveProductQcInfoEnterList().forEach(item->{
            OpeOrderQcTrace opeOrderQcTrace = new OpeOrderQcTrace();
            BeanUtils.copyProperties(item,opeOrderQcTrace);
            opeOrderQcTrace.setId(idAppService.getId(SequenceName.OPE_ORDER_QC_TRACE));
            opeOrderQcTrace.setDr(0);
            opeOrderQcTrace.setQcItemId(opeOrderQcItemId);
            opeOrderQcTrace.setCreatedBy(enter.getUserId());
            opeOrderQcTrace.setCreatedTime(new Date());
            opeOrderQcTrace.setUpdatedBy(enter.getUserId());
            opeOrderQcTrace.setUpdatedTime(new Date());
            saveOpeOrderQcTraceList.add(opeOrderQcTrace);
        });
        if (CollectionUtils.isNotEmpty(saveOpeOrderQcTraceList)){
            opeOrderQcTraceService.saveOrUpdateBatch(saveOpeOrderQcTraceList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/4 6:36 下午
     * @Param: enter
     * @Return: ProductQcTempleteListResul
     * @desc: 查询质检记录
     */
    @Override
    public List<ProductQcTraceListResult> list() {
        List<ProductQcTraceListResult> result=new ArrayList<>();
        List<OpeOrderQcItem> opeOrderQcItemList = opeOrderQcItemService.list();
        if (CollectionUtils.isNotEmpty(opeOrderQcItemList)){
            //查询质检信息
            List<OpeOrderQcTrace> opeOrderQcTraceList = opeOrderQcTraceService.list(new LambdaQueryWrapper<OpeOrderQcTrace>().in(OpeOrderQcTrace::getQcItemId,
                    opeOrderQcItemList.stream().map(OpeOrderQcItem::getId).collect(Collectors.toList())));

            opeOrderQcItemList.forEach(item->{
                ProductQcTraceListResult productQcTraceListResult = new ProductQcTraceListResult();
                BeanUtils.copyProperties(item, productQcTraceListResult);

                List<ProductQcTraceInfoResult> productQcTraceInfoResultList = new ArrayList<>();
                opeOrderQcTraceList.forEach(trace -> {
                    ProductQcTraceInfoResult productQcTraceInfoResult = new ProductQcTraceInfoResult();
                    BeanUtils.copyProperties(trace,productQcTraceInfoResult);
                    productQcTraceInfoResultList.add(productQcTraceInfoResult);
                });
                productQcTraceListResult.setProductQcTraceInfoResultList(productQcTraceInfoResultList);
                result.add(productQcTraceListResult);
            });
        }
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/4 6:37 下午
     * @Param: enter
     * @Return:
     * @desc: 查询质检记录
     * @param enter
     */
    @Override
    public ProductQcTraceListResult getById(IdEnter enter) {
        ProductQcTraceListResult result =new ProductQcTraceListResult();
        OpeOrderQcItem opeOrderQcItem = opeOrderQcItemService.getById(enter.getId());
        if (result!=null){
            //查询质检信息
            List<OpeOrderQcTrace> opeOrderQcTraceList = opeOrderQcTraceService.list(new LambdaQueryWrapper<OpeOrderQcTrace>().eq(OpeOrderQcTrace::getQcItemId,
                    opeOrderQcItem.getId()));

            List<ProductQcTraceInfoResult> productQcTraceInfoResultList = new ArrayList<>();
            opeOrderQcTraceList.forEach(trace -> {
                ProductQcTraceInfoResult productQcTraceInfoResult = new ProductQcTraceInfoResult();
                BeanUtils.copyProperties(trace,productQcTraceInfoResult);
                productQcTraceInfoResultList.add(productQcTraceInfoResult);
            });
            result.setProductQcTraceInfoResultList(productQcTraceInfoResultList);
        }
        return result;
    }
}
