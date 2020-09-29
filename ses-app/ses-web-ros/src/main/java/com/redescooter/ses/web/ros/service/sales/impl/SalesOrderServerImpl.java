package com.redescooter.ses.web.ros.service.sales.impl;

import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerInquiryMapper;
import com.redescooter.ses.web.ros.dao.sales.SalesOrderServerMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.service.sales.SalesOrderServer;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderDetailsResult;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderEnter;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderListResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: ses-server
 * @description: 销售订单接口服务实现类
 * @author: Jerry
 * @created: 2020/09/29 22:43
 */
@Slf4j
@Service
public class SalesOrderServerImpl implements SalesOrderServer {

    @Autowired
    private SalesOrderServerMapper salesOrderServerMapper;

    @Autowired
    private OpeCustomerInquiryMapper baseCustomerInquiryMapper;

    /**
     * 销售订单状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countStatus(GeneralEnter enter) {
        List<CountByStatusResult> statusResultList = salesOrderServerMapper.countStatus();
        Map<String, Integer> map = new HashMap<String, Integer>();
        statusResultList.forEach(item -> {
            map.put(item.getStatus(), item.getTotalCount());
        });

        for (InquiryStatusEnums item : InquiryStatusEnums.values()) {
            if (!map.containsKey(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        map.remove(InquiryStatusEnums.PROCESSED.getValue());
        map.remove(InquiryStatusEnums.PAY_LAST_PARAGRAPH.getValue());
        return map;
    }

    /**
     * 销售单列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<SalesOrderListResult> list(SalesOrderEnter enter) {
        if (enter.getKeyword() != null && enter.getKeyword().length() > 50) {
            return PageResult.createZeroRowResult(enter);
        }
        int count = salesOrderServerMapper.listCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<SalesOrderListResult> list = salesOrderServerMapper.list(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * 销售订单详情
     *
     * @param enter
     * @param id
     * @return
     */
    @Override
    public SalesOrderDetailsResult details(IdEnter enter, Long id) {

        enter.setId(id);
        SalesOrderDetailsResult detailsResult = salesOrderServerMapper.details(enter);

        if (detailsResult == null) {
            return new SalesOrderDetailsResult();
        }
        return detailsResult;
    }

    /**
     * 销售订单添加或者删除标签
     *
     * @param enter
     * @param id
     * @return
     */
    @Override
    public GeneralResult labels(IdEnter enter, Long id) {

        enter.setId(id);
        OpeCustomerInquiry inquiry = baseCustomerInquiryMapper.selectById(enter.getId());
        /*标签标记*/
        String labels = inquiry.getDef5();
        if (labels == null) {
            labels = String.valueOf(!Boolean.FALSE);
        } else {
            labels = String.valueOf(!Boolean.parseBoolean(labels));
        }
        inquiry.setDef5(labels);
        baseCustomerInquiryMapper.updateById(inquiry);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 取消销售订单提醒
     *
     * @param enter
     * @param id
     * @return
     */
    @Override
    public GeneralResult cancelWarn(IdEnter enter, Long id) {
        enter.setId(id);
        OpeCustomerInquiry inquiry = baseCustomerInquiryMapper.selectById(enter.getId());
        /*提示标记*/
        Double warnDouble = inquiry.getDef6();
        if (warnDouble == null) {
            warnDouble = Double.POSITIVE_INFINITY;
        } else if (warnDouble == Double.NEGATIVE_INFINITY) {
            warnDouble = Double.POSITIVE_INFINITY;
        } else {
            return new GeneralResult(enter.getRequestId());
        }
        inquiry.setDef6(warnDouble);
        baseCustomerInquiryMapper.updateById(inquiry);
        return new GeneralResult(enter.getRequestId());
    }
}
