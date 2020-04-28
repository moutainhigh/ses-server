package com.redescooter.ses.mobile.rps.service.assembly.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.assembly.AssemblyServiceMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.assembly.AssemblyService;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.base.OpeAssembiyOrderTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.mobile.rps.vo.assembly.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName:AssemblyServiceImpl
 * @description: AssemblyServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 13:25
 */
@Service
public class AssemblyServiceImpl implements AssemblyService {

    @Autowired
    private AssemblyServiceMapper assemblyServiceMapper;

    @Autowired
    private OpeAssemblyOrderService opeAssemblyOrderService;

    @Autowired
    private OpeAssemblyBOrderService opeAssemblyBOrderService;

    @Autowired
    private OpePartsProductBService opePartsProductBService;

    @Autowired
    private OpeProductAssemblyService opeProductAssemblyService;

    @Autowired
    private OpePartsProductService opePartsProductService;

    @Autowired
    private OpeProductAssemblyBService opeProductAssemblyBService;

    @Autowired
    private OpeAssembiyOrderTraceService opeAssembiyOrderTraceService;

    @Reference
    private IdAppService idAppService;

    /**
     * 待组装列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WaitAssemblyListResult> list(PageEnter enter) {
        int count = opeAssemblyOrderService.count(new LambdaQueryWrapper<OpeAssemblyOrder>().ne(OpeAssemblyOrder::getWaitAssemblyTotal, 0).eq(OpeAssemblyOrder::getStatus,
                AssemblyStatusEnums.ASSEMBLING.getValue()));
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, assemblyServiceMapper.list(enter));
    }

    /**
     * 待组装详情
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WaitAssemblyDetailResult> detail(AssemblyDetailEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getById(enter.getId());
        if (opeAssemblyOrder == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
        }

        int count = opeAssemblyBOrderService.count(new LambdaQueryWrapper<OpeAssemblyBOrder>().ne(OpeAssemblyBOrder::getWaitAssemblyQty, 0));
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, assemblyServiceMapper.detailList(enter));
    }

    /**
     * 产品配方
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductFormulaResult> formula(IdEnter enter) {
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getById(enter.getId());
        if (opeAssemblyBOrder == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
        }
        return assemblyServiceMapper.formulaByAssemblyBId(enter.getId());
    }

    /**
     * 保存产品组装数据
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public SaveFormulaDateResult save(SaveFormulaDateEnter enter) {
        //商品组装部件
        List<OpeProductAssemblyB> saveProductAssemblyBList = new ArrayList<>();

        List<SaveFormulaPartListEnter> saveFormulaPartListEnterList = new ArrayList<>();
        //入参解析
        try {
            saveFormulaPartListEnterList.addAll(JSON.parseArray(enter.getPartListJson(), SaveFormulaPartListEnter.class));
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }
        if (CollectionUtils.isEmpty(saveFormulaPartListEnterList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        //组装单校验
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getById(enter.getId());
        if (opeAssemblyBOrder == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
        }
        //查询产品
        OpePartsProduct partsProduct = opePartsProductService.getById(opeAssemblyBOrder.getProductId());
        if (partsProduct == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }


        //查询产品组装规则
        List<OpePartsProductB> partsProductBList = opePartsProductBService.list(new LambdaQueryWrapper<OpePartsProductB>().eq(OpePartsProductB::getPartsProductId, opeAssemblyBOrder.getProductId()));
        if (CollectionUtils.isEmpty(partsProductBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_HAVE_FORMULA.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_HAVE_FORMULA.getMessage());
        }

        //封装数据 验证的map 并进行后续验证
        Map<Long, Integer> partListMap = new HashMap<>();
        saveFormulaPartListEnterList.forEach(item -> {
            partListMap.put(item.getId(), item.getQty());
        });

        //入参部件验证
        partsProductBList.forEach(item -> {
            if (!partListMap.keySet().contains(item.getPartsId())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_INFORMATION_IS_NOT_COMPLETE.getCode(), ExceptionCodeEnums.PART_INFORMATION_IS_NOT_COMPLETE.getMessage());
            }
            if (!partListMap.get(item.getPartsId()).equals(item.getPartsQty())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
            }
        });

        //查询其他订单条目
        List<OpeAssemblyBOrder> opeAssemblyBOrderList = opeAssemblyBOrderService.list(new LambdaQueryWrapper<OpeAssemblyBOrder>().eq(OpeAssemblyBOrder::getAssemblyId,
                opeAssemblyBOrder.getAssemblyId())
                .ne(OpeAssemblyBOrder::getId, opeAssemblyBOrder.getId()));
        if (opeAssemblyBOrder.getWaitAssemblyQty() < 1) {
            throw new SesMobileRpsException(ExceptionCodeEnums.THE_PRODUCT_HAS_BEEN_ASSEMBLED.getCode(), ExceptionCodeEnums.THE_PRODUCT_HAS_BEEN_ASSEMBLED.getMessage());
        }
        opeAssemblyBOrder.setWaitAssemblyQty(opeAssemblyBOrder.getWaitAssemblyQty() - 1);
        opeAssemblyBOrderList.add(opeAssemblyBOrder);

        //判断子订单条目 待组装的条目 为0时 修改主表状态
        Boolean updateOpeAssemblyBStatus = Boolean.TRUE;
        for (OpeAssemblyBOrder assemblyBOrder : opeAssemblyBOrderList) {
            if (assemblyBOrder.getWaitAssemblyQty() != 0) {
                updateOpeAssemblyBStatus = Boolean.FALSE;
            }
        }

        //主表信息更新
        if (updateOpeAssemblyBStatus) {
            OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getById(opeAssemblyBOrder.getAssemblyId());
            if (opeAssemblyOrder == null) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
            }
            opeAssemblyOrder.setStatus(AssemblyStatusEnums.QC.getValue());
            opeAssemblyOrder.setWaitAssemblyTotal(0);
            opeAssemblyOrder.setUpdatedBy(enter.getUserId());
            opeAssemblyOrder.setUpdatedTime(new Date());
            opeAssemblyOrderService.updateById(opeAssemblyOrder);

            //日志记录
            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(opeAssemblyOrder.getId());
            saveNodeEnter.setStatus(AssemblyStatusEnums.QC.getValue());
            saveNodeEnter.setEvent(AssemblyStatusEnums.QC.getValue());
            saveNodeEnter.setMemo(null);
            this.saveNode(saveNodeEnter);
        }

        //整车组装记录
        OpeProductAssembly opeProductAssembly = OpeProductAssembly.builder()
                .id(idAppService.getId(SequenceName.OPE_PRODUCT_ASSEMBLY))
                .dr(0)
                .productId(opeAssemblyBOrder.getProductId())
                .assemblyBId(opeAssemblyBOrder.getId())
                .assemblyId(opeAssemblyBOrder.getAssemblyId())
                .productSerialNum(RandomUtil.BASE_CHAR_NUMBER)
                .productName(partsProduct.getCnName())
                .productSerialNum(RandomUtil.BASE_CHAR)
                .productType(partsProduct.getProductType().toString())
                .productCode(partsProduct.getProductCode())
                .productionDate(new Date())
                .printFlag(enter.getPrintCodeResult())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();

        saveFormulaPartListEnterList.forEach(item -> {
            saveProductAssemblyBList.add(
                    OpeProductAssemblyB.builder()
                            .id(idAppService.getId(SequenceName.OPE_PRODUCT_ASSEMBLY_B))
                            .dr(0)
                            .productAssemblyId(opeProductAssembly.getId())
                            .partId(item.getId())
                            .qty(item.getQty())
                            .partSerialNum(item.getSerialN())
                            .revision(0)
                            .createdBy(enter.getUserId())
                            .createdTime(new Date())
                            .updatedBy(enter.getUserId())
                            .updatedTime(new Date())
                            .build()
            );
        });

        //子订单数据更新
        opeAssemblyBOrder.setCompleteQty(1);
        opeAssemblyBOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyBOrder.setUpdatedTime(new Date());
        opeAssemblyBOrderService.updateById(opeAssemblyBOrder);

        //产品条目数据保存
        opeProductAssemblyService.save(opeProductAssembly);
        //产品组装部件保存
        opeProductAssemblyBService.saveBatch(saveProductAssemblyBList);

        return SaveFormulaDateResult.builder()
                .id(opeProductAssembly.getId())
                .productN(partsProduct.getProductNumber())
                .createdTime(new Date())
                .productName(partsProduct.getCnName())
                .serialNum(opeProductAssembly.getProductSerialNum())
                .build();
    }

    /**
     * 打印条码
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public PrintCodeResult printCode(PrintCodeEnter enter) {
        OpeProductAssembly opeProductAssembly = opeProductAssemblyService.getById(enter.getId());
        if (opeProductAssembly == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_ASSEMBLY_TRACE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_ASSEMBLY_TRACE_IS_NOT_EXIST.getMessage());
        }
        //条码已成功打印
        if (opeProductAssembly.getPrintFlag()) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_SERIAL_NUMBER_NON_REPEATABLE_PRINTING.getCode(), ExceptionCodeEnums.PRODUCT_SERIAL_NUMBER_NON_REPEATABLE_PRINTING.getMessage());
        }

        if (!opeProductAssembly.getPrintFlag() && enter.getPrintCodeResult()) {
            opeProductAssembly.setPrintFlag(Boolean.TRUE);
            opeProductAssembly.setUpdatedBy(enter.getUserId());
            opeProductAssembly.setUpdatedTime(new Date());
            opeProductAssemblyService.updateById(opeProductAssembly);
        }
        return PrintCodeResult.builder().printCodeResult(enter.getPrintCodeResult()).build();
    }

    /**
     * 查询打印条码结果
     *
     * @param enter
     * @return
     */
    @Override
    public QueryProductCodeResult queryProductCode(IdEnter enter) {
        OpeProductAssembly opeProductAssembly = opeProductAssemblyService.getById(enter.getId());
        if (opeProductAssembly == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_ASSEMBLY_TRACE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_ASSEMBLY_TRACE_IS_NOT_EXIST.getMessage());
        }
        return QueryProductCodeResult.builder()
                .id(opeProductAssembly.getId())
                .printCodeResult(opeProductAssembly.getPrintFlag())
                .build();
    }

    /**
     * 保存节点
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveNode(SaveNodeEnter enter) {
        opeAssembiyOrderTraceService.save(OpeAssembiyOrderTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_TRACE))
                .dr(0)
                .userId(enter.getUserId())
                .opeAssembiyOrderId(enter.getId())
                .status(enter.getStatus())
                .event(enter.getEvent())
                .eventTime(new Date())
                .memo(StringUtils.isBlank(enter.getMemo()) == true ? null : enter.getMemo())
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build());
        return new GeneralResult(enter.getRequestId());
    }
}
