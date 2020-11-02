package com.redescooter.ses.mobile.rps.service.restproductionorder.invoice.impl;


import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.dao.restproductionorder.invoice.InvoiceOrderSrviceMapper;
import com.redescooter.ses.mobile.rps.service.restproductionorder.invoice.InvoiceOrderService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.ProductQcTempleteResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.QcTempleteEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.SaveQcResultEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.OutboundDetailResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.OutboundOrderEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.OutboundOrderResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.ProductDetailResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  @author: aleks
 *  @Date: 2020/10/26 14:25
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Service
@Slf4j
public class InvoiceOrderServiceImpl implements InvoiceOrderService {

    @Autowired
    private InvoiceOrderSrviceMapper invoiceOrderSrviceMapper;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 12:04 下午
     * @Param: enter
     * @Return: Map
     * @desc: countByProductType
     * @param enter
     */
    @Override
    public Map<Integer, Integer> countByProductType(GeneralEnter enter) {
        Map<Integer, Integer> map = new HashMap<>();
        List<CountByStatusResult> countByStatusResultList=invoiceOrderSrviceMapper.countByProductType(enter);
        map=countByStatusResultList.stream().collect(Collectors.toMap(item->{return Integer.valueOf(item.getStatus());},CountByStatusResult::getTotalCount));

        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (!map.containsKey(item.getValue())){
                map.put(item.getValue(),0);
            }
        }
        return map;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 1:12 下午
     * @Param: enter
     * @Return: OutboundOrderResult
     * @desc: 出库单列表
     * @param enter
     */
    @Override
    public PageResult<OutboundOrderResult> list(OutboundOrderEnter enter) {
        int count=invoiceOrderSrviceMapper.listCount(enter);
        if (count==0){
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter,count,invoiceOrderSrviceMapper.list(enter));
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 1:25 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 开始质检
     * @param enter
     */
    @Override
    public GeneralResult startQc(IdEnter enter) {

        return null;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 1:37 下午
     * @Param: enter
     * @Return: OutboundDetailResult
     * @desc: 单据详情
     * @param enter
     */
    @Override
    public OutboundDetailResult detail(IdEnter enter) {
        return null;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 2:48 下午
     * @Param: enter
     * @Return: ProductDetailResult
     * @desc: 产品详情
     * @param enter
     */
    @Override
    public ProductDetailResult productDetail(IdEnter enter) {
        return null;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 2:51 下午
     * @Param: enter
     * @Return: ProductQcTempleteResult
     * @desc: 质检模版
     * @param enter
     */
    @Override
    public ProductQcTempleteResult qcTemplete(QcTempleteEnter enter) {
        return null;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 3:28 下午
     * @Param: SaveQcResultEnter
     * @Return: GeneralResult
     * @desc: 保存质检结果
     * @param enter
     */
    @Override
    public GeneralResult saveQcResult(SaveQcResultEnter enter) {
        return null;
    }
}
