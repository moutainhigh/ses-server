package com.redescooter.ses.web.ros.service.sales.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.inquiry.InquiryPayStatusEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerInquiryMapper;
import com.redescooter.ses.web.ros.dao.sales.SalesOrderServerMapper;
import com.redescooter.ses.web.ros.dm.OpeColor;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.service.base.OpeColorService;
import com.redescooter.ses.web.ros.service.sales.SalesOrderServer;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.sales.ColorCountResult;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderDetailsResult;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderEnter;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderListResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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

    @Autowired
    private OpeColorService opeColorService;

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
     * 颜色统计
     *
     * @param enter
     * @return
     */
    @Override
    public List<ColorCountResult> colorCount(GeneralEnter enter) {
        List<ColorCountResult> resultList = Lists.newArrayList();
        LambdaQueryWrapper<OpeColor> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeColor::getDr, Constant.DR_FALSE);
        List<OpeColor> list = opeColorService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (OpeColor color : list) {
                ColorCountResult model = new ColorCountResult();
                model.setId(color.getId());
                model.setName(color.getColorName());
                resultList.add(model);
            }
        }
        return resultList;
    }

    /**
     * 支付状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> payStatusCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (InquiryPayStatusEnums item : InquiryPayStatusEnums.values()) {
            if (!map.containsKey(item.getCode())) {
                map.put(item.getCode(), Integer.parseInt(item.getValue()));
            }
        }
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
        if (NumberUtil.notNullAndGtFifty(enter.getKeyword())) {
            return PageResult.createZeroRowResult(enter);
        }
        int count = salesOrderServerMapper.listCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<SalesOrderListResult> list = salesOrderServerMapper.list(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * 销售订单详情
     *
     * @param enter
     * @return
     */
    @Override
    public SalesOrderDetailsResult details(IdEnter enter) {
        SalesOrderDetailsResult detailsResult = salesOrderServerMapper.details(enter);

        if (StringManaConstant.entityIsNull(detailsResult)) {
            return new SalesOrderDetailsResult();
        }
        return detailsResult;
    }

    /**
     * 销售订单添加或者删除标签
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult labels(IdEnter enter) {
        OpeCustomerInquiry inquiry = baseCustomerInquiryMapper.selectById(enter.getId());
        /*标签标记*/
        String labels = inquiry.getDef5();
        if (StringManaConstant.entityIsNull(labels)) {
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
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult cancelWarn(IdEnter enter) {
        OpeCustomerInquiry inquiry = baseCustomerInquiryMapper.selectById(enter.getId());
        /*提示标记*/
        Double warnDouble = inquiry.getDef6();
        if (StringManaConstant.entityIsNull(warnDouble)) {
            warnDouble = new Double("1");
        } else if (0 >= warnDouble) {
            warnDouble = new Double("1");
        } else {
            return new GeneralResult(enter.getRequestId());
        }
        inquiry.setDef6(warnDouble);
        baseCustomerInquiryMapper.updateById(inquiry);
        return new GeneralResult(enter.getRequestId());
    }
}
