package com.redescooter.ses.web.ros.service.wms.impl;

import com.redescooter.ses.api.common.enums.wms.ConsignMethodEnums;
import com.redescooter.ses.api.common.enums.wms.ConsignTypeEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutStatusEnums;
import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.dao.wms.WhOutServiceMapper;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.wms.WhOutService;
import com.redescooter.ses.web.ros.vo.wms.StartWhOutOrderEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutConsigneeResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailProductPartListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailProductPartListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutOrderListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutOrderListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutProductListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutProductListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutSaveEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutWhResult;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:WhOutServiceImpl
 * @description: WhOutServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 10:30
 */

@Log4j
@Service
public class WhOutServiceImpl implements WhOutService {

    @Autowired
    private WhOutServiceMapper whOutServiceMapper;


    /**
     * 出库单列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WhOutOrderListResult> whOrderList(WhOutOrderListEnter enter) {
        return null;
    }

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    @Override
    public WhOutDetailResult detail(IdEnter enter) {
        return null;
    }

    /**
     * 订单节点
     *
     * @param enter
     * @return
     */
    @Override
    public CommonNodeResult nodeDetail(IdEnter enter) {
        return null;
    }

    /**
     * 详情产品列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WhOutDetailProductPartListResult> detailProductPartList(WhOutDetailProductPartListEnter enter) {
        return null;
    }

    /**
     * 取消出库单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult cancel(IdEnter enter) {
        return null;
    }

    /**
     * 开始出库单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult start(StartWhOutOrderEnter enter) {
        return null;
    }

    /**
     * 确认备料
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult prepareMaterial(IdEnter enter) {
        return null;
    }

    /**
     * 入库
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult inWh(IdEnter enter) {
        return null;
    }

    /**
     * 保存
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(WhOutSaveEnter enter) {
        return null;
    }

    /**
     * 收件人集合
     *
     * @param enter
     * @return
     */
    @Override
    public List<WhOutConsigneeResult> consigneeList(GeneralEnter enter) {
        return whOutServiceMapper.consigneeList(enter);
    }

    /**
     * 仓库列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<WhOutWhResult> whList(GeneralEnter enter) {
        return null;
    }

    /**
     * 发货方式
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> consignType(GeneralEnter enter) {
        Map<String, String> resultMap = new HashMap<>();
        for (ConsignTypeEnums item : ConsignTypeEnums.values()) {
            resultMap.put(item.getCode(), item.getValue());
        }
        return resultMap;
    }

    /**
     * 委托方式
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> consignMethod(StringEnter enter) {
        if (!StringUtils.equals(ConsignTypeEnums.getEnumsByCode(enter.getSt()).getValue(), ConsignTypeEnums.AIR_PARCEL.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.NO_LOAN.getCode(), ExceptionCodeEnums.NO_LOAN.getMessage());
        }
        Map<String,String> resultMap=new HashMap<>();
        for (ConsignMethodEnums item : ConsignMethodEnums.values()) {
            resultMap.put(item.getCode(),item.getValue());
        }
        return resultMap;
    }

    /**
     * 订单状态列表
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> statusList(GeneralEnter enter) {
        Map<String,String> resultMap=new HashMap<>();
        for (WhOutStatusEnums item : WhOutStatusEnums.values()) {
            resultMap.put(item.getCode(),item.getValue());
        }
        return resultMap;
    }

    /**
     * 订单状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> statusByCount(GeneralEnter enter) {
        //统计查询
        Map<String,Integer> resultMap=new HashMap<>();
        for (WhOutStatusEnums item : WhOutStatusEnums.values()) {
            resultMap.put(item.getValue(),0);
        }
        return resultMap;
    }

    /**
     * 产品列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WhOutProductListResult> productList(WhOutProductListEnter enter) {
        //采购仓库 成品仓库库存
        Integer count = whOutServiceMapper.productListCount(enter);
        if (count==null){
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter,count,whOutServiceMapper.productListList(enter));
    }
}
