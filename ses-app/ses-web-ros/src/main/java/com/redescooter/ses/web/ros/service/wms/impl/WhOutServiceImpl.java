package com.redescooter.ses.web.ros.service.wms.impl;

import cn.hutool.db.Page;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.wms.ConsignMethodEnums;
import com.redescooter.ses.api.common.enums.wms.ConsignTypeEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutEventEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutStatusEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutTypeEnums;
import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.whse.WhseTypeEnums;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.wms.WhOutServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeOutwhOrder;
import com.redescooter.ses.web.ros.dm.OpeOutwhTrace;
import com.redescooter.ses.web.ros.dm.OpeStock;
import com.redescooter.ses.web.ros.dm.OpeStockPurchas;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.exception.ExceptionCode;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeOutwhOrderService;
import com.redescooter.ses.web.ros.service.base.OpeOutwhTraceService;
import com.redescooter.ses.web.ros.service.base.OpeStockProdProductService;
import com.redescooter.ses.web.ros.service.base.OpeStockPurchasService;
import com.redescooter.ses.web.ros.service.base.OpeStockService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.wms.WhOutService;
import com.redescooter.ses.web.ros.vo.wms.SavePartProductEnter;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private OpeOutwhOrderService opeOutwhOrderService;

    @Autowired
    private OpeOutwhTraceService opeOutwhTraceService;

    @Autowired
    private OpeWhseService opeWhseService;

    @Autowired
    private OpeStockService opeStockService;

    @Autowired
    private OpeStockPurchasService opeStockPurchasService;

    @Autowired
    private OpeStockProdProductService opeStockProdProductService;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    @Reference
    private IdAppService idAppService;


    /**
     * 出库单列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WhOutOrderListResult> whOrderList(WhOutOrderListEnter enter) {
        Integer count = whOutServiceMapper.whOrderListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, whOutServiceMapper.whOrderList(enter));
    }

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    @Override
    public WhOutDetailResult detail(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (opeOutwhOrder == null) {
            //异常
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        return whOutServiceMapper.detail(enter);
    }

    /**
     * 订单节点
     *
     * @param enter
     * @return
     */
    @Override
    public CommonNodeResult nodeList(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (opeOutwhOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        return whOutServiceMapper.nodeList(enter);
    }

    /**
     * 详情产品列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WhOutDetailProductPartListResult> detailProductPartList(WhOutDetailProductPartListEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (opeOutwhOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        int count = whOutServiceMapper.detailProductPartListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, whOutServiceMapper.detailProductPartList(enter));
    }

    /**
     * 取消出库单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult cancel(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (opeOutwhOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(WhOutStatusEnums.PENDING.getValue(), opeOutwhOrder.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        //修改主订单
        opeOutwhOrder.setStatus(WhOutStatusEnums.CANCELLED.getValue());
        opeOutwhOrder.setUpdatedBy(enter.getUserId());
        opeOutwhOrder.setUpdatedTime(new Date());
        opeOutwhOrderService.updateById(opeOutwhOrder);
        //保存节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeOutwhOrder.getId());
        saveNodeEnter.setStatus(WhOutStatusEnums.CANCELLED.getValue());
        saveNodeEnter.setEvent(WhOutEventEnums.CANCELLED.getValue());
        saveNodeEnter.setMemo(null);
        saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 开始出库单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult start(StartWhOutOrderEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (opeOutwhOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(WhOutStatusEnums.PREPARE_MATERIAL.getValue(), opeOutwhOrder.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        //修改主订单
        opeOutwhOrder.setStatus(WhOutStatusEnums.OUT_WH.getValue());
        opeOutwhOrder.setUpdatedBy(enter.getUserId());
        opeOutwhOrder.setUpdatedTime(new Date());
        opeOutwhOrderService.updateById(opeOutwhOrder);
        //保存节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeOutwhOrder.getId());
        saveNodeEnter.setStatus(WhOutStatusEnums.OUT_WH.getValue());
        saveNodeEnter.setEvent(WhOutEventEnums.OUT_WH.getValue());
        saveNodeEnter.setMemo(null);
        saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 确认备料
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult prepareMaterial(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (opeOutwhOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(WhOutStatusEnums.PENDING.getValue(), opeOutwhOrder.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        //修改主订单
        opeOutwhOrder.setStatus(WhOutStatusEnums.PREPARE_MATERIAL.getValue());
        opeOutwhOrder.setUpdatedBy(enter.getUserId());
        opeOutwhOrder.setUpdatedTime(new Date());
        opeOutwhOrderService.updateById(opeOutwhOrder);

        //todo 库存删减

        //保存节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeOutwhOrder.getId());
        saveNodeEnter.setStatus(WhOutStatusEnums.PREPARE_MATERIAL.getValue());
        saveNodeEnter.setEvent(WhOutEventEnums.PREPARE_MATERIAL.getValue());
        saveNodeEnter.setMemo(null);
        saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
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
        List<SavePartProductEnter> savePartProductEnterList = new ArrayList<>();
        try {
            savePartProductEnterList.addAll(JSON.parseArray(enter.getProductList(), SavePartProductEnter.class));
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(savePartProductEnterList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_PART_IS_EMPTY.getCode(), ExceptionCodeEnums.PRODUCT_PART_IS_EMPTY.getMessage());
        }
        //查询仓库是否存在
        OpeWhse gogalWh = opeWhseService.getById(enter.getGogalId());
        if (gogalWh == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }
        //校验收货人
        OpeSysUserProfile opeSysUserProfile = opeSysUserProfileService.getOne(new LambdaQueryWrapper<OpeSysUserProfile>().eq(OpeSysUserProfile::getSysUserId, enter.getUserId()));
        if (opeSysUserProfile == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //校验物流方式
        if (ConsignTypeEnums.getEnumsByValue(enter.getConsignType()) == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CONSIGN_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.CONSIGN_TYPE_NOT_EXIST.getMessage());
        }

        //查询采购仓库和组装仓库
        List<OpeWhse> opeWhseList = opeWhseService.list(new LambdaQueryWrapper<OpeWhse>().in(OpeWhse::getType, WhseTypeEnums.PURCHAS.getValue(), WhseTypeEnums.ASSEMBLY.getValue()));
        if (CollectionUtils.isEmpty(opeWhseList)){
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }
        opeWhseList.for

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
        return whOutServiceMapper.whList(enter);
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
        Map<String, String> resultMap = new HashMap<>();
        for (ConsignMethodEnums item : ConsignMethodEnums.values()) {
            resultMap.put(item.getCode(), item.getValue());
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
        Map<String, String> resultMap = new HashMap<>();
        for (WhOutStatusEnums item : WhOutStatusEnums.values()) {
            resultMap.put(item.getCode(), item.getValue());
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
        Map<String, Integer> resultMap = new HashMap<>();

        //统计查询
        List<CountByStatusResult> statusResultList = whOutServiceMapper.statusByCount(enter);
        if (CollectionUtils.isNotEmpty(statusResultList)) {
            statusResultList.forEach(item -> {
                resultMap.put(item.getStatus(), item.getTotalCount());
            });
        }
        for (WhOutTypeEnums item : WhOutTypeEnums.values()) {
            if (!resultMap.containsKey(item.getValue())) {
                resultMap.put(item.getValue(), 0);
            }
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
        if (count == null) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, whOutServiceMapper.productListList(enter));
    }

    /**
     * 保存订单节点
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveNode(SaveNodeEnter enter) {
        OpeOutwhTrace opeOutwhTrace = OpeOutwhTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_OUTWH_TRACE))
                .dr(0)
                .whOutOrderId(enter.getId())
                .status(enter.getStatus())
                .event(enter.getEvent())
                .eventTime(new Date())
                .memo(enter.getMemo())
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
        opeOutwhTraceService.save(opeOutwhTrace);
        return new GeneralResult(enter.getRequestId());
    }
}
