package com.redescooter.ses.mobile.rps.service.entrustorder.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustOrderMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceProductSerialNum;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.entrustorder.EntrustOrderService;
import com.redescooter.ses.mobile.rps.vo.entrustorder.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2020/12/30 18:12
 */
@Slf4j
@Service
public class EntrustOrderServiceImpl implements EntrustOrderService {

    @Resource
    private EntrustOrderMapper entrustOrderMapper;


    @Override
    public Map<Integer, Integer> getEntrustOrderTypeCount(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResults = entrustOrderMapper.getEntrustOrderTypeCount();

        /**
         * {entrustType, totalCount}
         */
        Map<Integer, Integer> resultMap = countByStatusResults.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult::getTotalCount)
        );

        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (null == resultMap.get(item.getValue())) {
                resultMap.put(item.getValue(), 0);
            }
        }

        return resultMap;
    }

    @Override
    public PageResult<QueryEntrustOrderResultDTO> getEntrustOrderList(QueryEntrustOrderParamDTO paramDTO) {
        int count = entrustOrderMapper.countByEntrustOrder(paramDTO);
        if (0 == count) {
            return PageResult.createZeroRowResult(paramDTO);
        }

        return PageResult.create(paramDTO, count, entrustOrderMapper.getEntrustOrderList(paramDTO));
    }

    @Override
    public EntrustOrderDetailDTO getEntrustOrderDetailById(IdEnter enter) {
        EntrustOrderDetailDTO entrustOrderDetail = entrustOrderMapper.getEntrustOrderDetailById(enter.getId());
        if (null == entrustOrderDetail) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }

        List<EntrustOrderProductDTO> productList = null;

        /**
         * 查询委托单产品信息 ---- 1：整车 2：组装件 3：部件
         */
        switch (entrustOrderDetail.getType()) {
            case 1:
                productList = entrustOrderMapper.getEntrustOrderScooterByEntrustId(enter.getId());
                break;
            case 2:
                productList = entrustOrderMapper.getEntrustOrderCombinByEntrustId(enter.getId());
                break;
            default:
                productList = entrustOrderMapper.getEntrustOrderPartsByEntrustId(enter.getId());
                break;
        }

        entrustOrderDetail.setProductList(productList);

        return entrustOrderDetail;
    }

    @Override
    public GeneralResult entrustOrderDeliver(EntrustOrderDeliverParamDTO paramDTO) {
        List<EntrustOrderProductDTO> entrustOrderProductList = null;
        try {
            entrustOrderProductList = JSONArray.parseArray(paramDTO.getSt(), EntrustOrderProductDTO.class);
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(),ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        /**
         * query ope_entrust_order info
         */
        EntrustOrderDetailDTO entrustOrderDetail = entrustOrderMapper.getEntrustOrderDetailById(paramDTO.getId());
        if (null == entrustOrderDetail) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }

        List<Long> idList = entrustOrderProductList.stream().map(EntrustOrderProductDTO::getId).collect(Collectors.toList());

        List<OpeInvoiceProductSerialNum> invoiceProductSerialNums = null;
        /**
         * {relationId, List<OpeInvoiceProductSerialNum>}
         */
        Map<Long, List<OpeInvoiceProductSerialNum>> serialNumMap = invoiceProductSerialNums.stream().collect(
                Collectors.groupingBy(OpeInvoiceProductSerialNum::getRelationId)
        );

        entrustOrderProductList.forEach(pro -> {

        });

        return new GeneralResult(paramDTO.getRequestId());
    }

    @Override
    public GeneralResult updatePartActualDeliveryQty(UpdatePartActualDeliveryQtyParamDTO paramDTO) {

        return new GeneralResult(paramDTO.getRequestId());
    }

}
