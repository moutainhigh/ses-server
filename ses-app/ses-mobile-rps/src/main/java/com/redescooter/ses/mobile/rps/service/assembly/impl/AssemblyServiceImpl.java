package com.redescooter.ses.mobile.rps.service.assembly.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.dao.assembly.AssemblyServiceMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyBOrder;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrder;
import com.redescooter.ses.mobile.rps.dm.OpePartsProduct;
import com.redescooter.ses.mobile.rps.dm.OpePartsProductB;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.assembly.AssemblyService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyBOrderService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.mobile.rps.service.base.OpePartsProductBService;
import com.redescooter.ses.mobile.rps.service.base.OpePartsProductService;
import com.redescooter.ses.mobile.rps.vo.assembly.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    /**
     * 待组装列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WaitAssemblyListResult> list(PageEnter enter) {
//        return PageResult.create(enter,1, Lists.newArrayList(WaitAssemblyListResult.builder()
//                .id(2312312L)
//                .assemblyN("eqwewqeqw")
//                .createdTime(new Date())
//                .waitAssemblyTotal(0)
//                .build()));

        int count = opeAssemblyOrderService.count(new LambdaQueryWrapper<OpeAssemblyOrder>().ne(OpeAssemblyOrder::getWaitAssemblyTotal,0).eq(OpeAssemblyOrder::getStatus,
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
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getById(enter.getId());
        if (opeAssemblyBOrder == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
        }

        int count = opeAssemblyBOrderService.count(new LambdaQueryWrapper<OpeAssemblyBOrder>().ne(OpeAssemblyBOrder::getWaitAssemblyQty, 0));
        if (count==0){
            return PageResult.createZeroRowResult(enter);
        }

//        return PageResult.create(enter, 1, Lists.newArrayList(WaitAssemblyDetailResult.builder()
//                .id(2312312L)
//                .assemblyId(43432L)
//                .productCnName("电机")
//                .productN("dasdasdad")
//                .waitAssemblyQty(0)
//                .build()));
        return PageResult.create(enter,count,assemblyServiceMapper.detailList(enter));
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
//        return Lists.newArrayList(ProductFormulaResult.builder()
//                .id(321312L)
//                .partCnName("轮胎")
//                .partId(423432L)
//                .partN("dadad")
//                .qty(0)
//                .build());

        return  assemblyServiceMapper.formulaByAssemblyBId(enter.getId());
    }

    /**
     * 保存产品组装数据
     *
     * @param enter
     * @return
     */
    @Override
    public SaveFormulaDateResult save(SaveFormulaDateEnter enter) {
        return SaveFormulaDateResult.builder()
                .id(3432L)
                .productN("dasasda")
                .createdTime(new Date())
                .productName("REDE——2W")
                .serialNum("dasasdada")
                .build();
    }

    /**
     * 打印条码
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult printCode(PrintCodeEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 查询打印条码结果
     *
     * @param enter
     * @return
     */
    @Override
    public QueryProductCodeResult queryProductCode(IdEnter enter) {
        return QueryProductCodeResult.builder()
                .id(31231L)
                .printCodeResult(Boolean.TRUE)
                .build();
    }
}
`