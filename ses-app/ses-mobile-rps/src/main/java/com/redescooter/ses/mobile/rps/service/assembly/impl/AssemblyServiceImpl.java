package com.redescooter.ses.mobile.rps.service.assembly.impl;

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
import com.redescooter.ses.mobile.rps.dm.OpeAssembiyOrderTrace;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyBOrder;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrder;
import com.redescooter.ses.mobile.rps.dm.OpePartsProduct;
import com.redescooter.ses.mobile.rps.dm.OpePartsProductB;
import com.redescooter.ses.mobile.rps.dm.OpeProductAssembly;
import com.redescooter.ses.mobile.rps.dm.OpeProductAssemblyB;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.BussinessNumberService;
import com.redescooter.ses.mobile.rps.service.assembly.AssemblyService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssembiyOrderTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyBOrderService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.mobile.rps.service.base.OpePartsProductBService;
import com.redescooter.ses.mobile.rps.service.base.OpePartsProductService;
import com.redescooter.ses.mobile.rps.service.base.OpeProductAssemblyBService;
import com.redescooter.ses.mobile.rps.service.base.OpeProductAssemblyService;
import com.redescooter.ses.mobile.rps.vo.assembly.AssemblyDetailEnter;
import com.redescooter.ses.mobile.rps.vo.assembly.PrintCodeEnter;
import com.redescooter.ses.mobile.rps.vo.assembly.PrintCodeResult;
import com.redescooter.ses.mobile.rps.vo.assembly.ProductFormulaResult;
import com.redescooter.ses.mobile.rps.vo.assembly.QueryProductCodeResult;
import com.redescooter.ses.mobile.rps.vo.assembly.SaveFormulaDateEnter;
import com.redescooter.ses.mobile.rps.vo.assembly.SaveFormulaDateResult;
import com.redescooter.ses.mobile.rps.vo.assembly.SaveFormulaPartListEnter;
import com.redescooter.ses.mobile.rps.vo.assembly.WaitAssemblyDetailResult;
import com.redescooter.ses.mobile.rps.vo.assembly.WaitAssemblyListResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:AssemblyServiceImpl
 * @description: AssemblyServiceImpl
 * @author: Alex
 * @Version???1.3
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

    @Autowired
    private BussinessNumberService bussinessNumberService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * ???????????????
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
     * ???????????????
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
        int count = opeAssemblyBOrderService.count(new LambdaQueryWrapper<OpeAssemblyBOrder>().eq(OpeAssemblyBOrder::getAssemblyId, opeAssemblyOrder.getId()).ne(OpeAssemblyBOrder::getWaitAssemblyQty, 0));
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, assemblyServiceMapper.detailList(enter));
    }

    /**
     * ????????????
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
     * ????????????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public SaveFormulaDateResult save(SaveFormulaDateEnter enter) {
        //??????????????????
        List<OpeProductAssemblyB> saveProductAssemblyBList = new ArrayList<>();

        List<SaveFormulaPartListEnter> saveFormulaPartListEnterList = new ArrayList<>();
        //????????????
        try {
            saveFormulaPartListEnterList.addAll(JSON.parseArray(enter.getPartListJson(), SaveFormulaPartListEnter.class));
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }
        if (CollectionUtils.isEmpty(saveFormulaPartListEnterList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        //???????????????
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getById(enter.getId());
        if (opeAssemblyBOrder == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
        }
        //????????????
        OpePartsProduct partsProduct = opePartsProductService.getById(opeAssemblyBOrder.getProductId());
        if (partsProduct == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }


        //????????????????????????
        List<OpePartsProductB> partsProductBList = opePartsProductBService.list(new LambdaQueryWrapper<OpePartsProductB>().eq(OpePartsProductB::getPartsProductId, opeAssemblyBOrder.getProductId()));
        if (CollectionUtils.isEmpty(partsProductBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_HAVE_FORMULA.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_HAVE_FORMULA.getMessage());
        }

        //???????????? ?????????map ?????????????????????
        Map<Long, Integer> partListMap = new HashMap<>();
        saveFormulaPartListEnterList.forEach(item -> {
            partListMap.put(item.getId(), item.getQty());
        });

        //??????????????????
        partsProductBList.forEach(item -> {
            if (!partListMap.keySet().contains(item.getPartsId())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_INFORMATION_IS_NOT_COMPLETE.getCode(), ExceptionCodeEnums.PART_INFORMATION_IS_NOT_COMPLETE.getMessage());
            }
            if (!partListMap.get(item.getPartsId()).equals(item.getPartsQty())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
            }
        });

        //????????????????????????
        List<OpeAssemblyBOrder> opeAssemblyBOrderList = opeAssemblyBOrderService.list(new LambdaQueryWrapper<OpeAssemblyBOrder>().eq(OpeAssemblyBOrder::getAssemblyId,
                opeAssemblyBOrder.getAssemblyId())
                .ne(OpeAssemblyBOrder::getId, opeAssemblyBOrder.getId()));
        if (opeAssemblyBOrder.getWaitAssemblyQty() < 1) {
            throw new SesMobileRpsException(ExceptionCodeEnums.THE_PRODUCT_HAS_BEEN_ASSEMBLED.getCode(), ExceptionCodeEnums.THE_PRODUCT_HAS_BEEN_ASSEMBLED.getMessage());
        }
        opeAssemblyBOrder.setWaitAssemblyQty(opeAssemblyBOrder.getWaitAssemblyQty() - 1);
        opeAssemblyBOrderList.add(opeAssemblyBOrder);

        //????????????????????? ?????????????????? ???0??? ??????????????????
        Boolean updateOpeAssemblyBStatus = Boolean.TRUE;
        for (OpeAssemblyBOrder assemblyBOrder : opeAssemblyBOrderList) {
            if (assemblyBOrder.getWaitAssemblyQty() != 0) {
                updateOpeAssemblyBStatus = Boolean.FALSE;
            }
        }
        OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getById(opeAssemblyBOrder.getAssemblyId());
        if (opeAssemblyOrder == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
        }

        //??????????????????
        if (updateOpeAssemblyBStatus) {
            opeAssemblyOrder.setStatus(AssemblyStatusEnums.QC.getValue());
            //????????????
            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(opeAssemblyOrder.getId());
            saveNodeEnter.setStatus(AssemblyStatusEnums.QC.getValue());
            saveNodeEnter.setEvent(AssemblyStatusEnums.QC.getValue());
            saveNodeEnter.setMemo(null);
            this.saveNode(saveNodeEnter);
        }

        //???????????????????????????????????????
        opeAssemblyOrder.setWaitAssemblyTotal(opeAssemblyOrder.getWaitAssemblyTotal() - 1);
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);


        //??????????????????
        OpeProductAssembly opeProductAssembly = OpeProductAssembly.builder()
                .id(idAppService.getId(SequenceName.OPE_PRODUCT_ASSEMBLY))
                .dr(0)
                .productId(opeAssemblyBOrder.getProductId())
                .assemblyBId(opeAssemblyBOrder.getId())
                .assemblyId(opeAssemblyBOrder.getAssemblyId())
                .productName(partsProduct.getCnName())
                //.productSerialNum(RandomUtil.BASE_CHAR)
                .productSerialNum(String.valueOf(System.currentTimeMillis()))
                .productType(partsProduct.getProductType().toString())
                .productCode(partsProduct.getProductCode())
                .productionDate(new Date())
                .inwhStatus(Boolean.FALSE)
                .printFlag(enter.getPrintCodeResult())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();

        IdEnter idEnter = new IdEnter();
        BeanUtils.copyProperties(enter, idEnter);
        idEnter.setId(opeProductAssembly.getId());
        String productSerialN = bussinessNumberService.productSerialN(idEnter);
        opeProductAssembly.setProductSerialNum(productSerialN);

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

        //?????????????????????
        opeAssemblyBOrder.setCompleteQty(opeAssemblyBOrder.getCompleteQty()+1);
        opeAssemblyBOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyBOrder.setUpdatedTime(new Date());
        opeAssemblyBOrderService.updateById(opeAssemblyBOrder);

        //????????????????????????
        opeProductAssemblyService.save(opeProductAssembly);
        //????????????????????????
        opeProductAssemblyBService.saveBatch(saveProductAssemblyBList);

        return SaveFormulaDateResult.builder()
                .id(opeProductAssembly.getId())
                .productN(partsProduct.getProductNumber())
                .createdTime(new Date())
                .productName(partsProduct.getCnName())
                .serialNum(opeProductAssembly.getProductSerialNum())
                .waitAssemblyQty(opeAssemblyBOrder.getAssemblyQty() - opeAssemblyBOrder.getCompleteQty())
                .build();
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public PrintCodeResult printCode(PrintCodeEnter enter) {
        OpeProductAssembly opeProductAssembly = opeProductAssemblyService.getById(enter.getId());
        if (opeProductAssembly == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_ASSEMBLY_TRACE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_ASSEMBLY_TRACE_IS_NOT_EXIST.getMessage());
        }
        //?????????????????????
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
     * ????????????????????????
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
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
