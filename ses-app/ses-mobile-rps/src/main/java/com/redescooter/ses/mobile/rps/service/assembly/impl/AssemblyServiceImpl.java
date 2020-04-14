package com.redescooter.ses.mobile.rps.service.assembly.impl;

import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.service.assembly.AssemblyService;
import com.redescooter.ses.mobile.rps.vo.assembly.*;
import org.springframework.stereotype.Service;

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

    /**
     * 待组装列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WaitAssemblyListResult> list(PageEnter enter) {
        return PageResult.create(enter,1, Lists.newArrayList(WaitAssemblyListResult.builder()
                .id(2312312L)
                .assemblyN("eqwewqeqw")
                .createdTime(new Date())
                .waitAssemblyTotal(0)
                .build()));
    }

    /**
     * 待组装详情
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WaitAssemblyDetailResult> detail(AssemblyDetailEnter enter) {
        return PageResult.create(enter,1, Lists.newArrayList(WaitAssemblyDetailResult.builder()
                .id(2312312L)
                .assemblyId(43432L)
                .productCnName("电机")
                .productN("dasdasdad")
                .waitAssemblyQty(0)
                .build()));
    }

    /**
     * 产品配方
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductFormulaResult> formula(IdEnter enter) {
        return Lists.newArrayList(ProductFormulaResult.builder()
                .id(321312L)
                .partCnName("轮胎")
                .partId(423432L)
                .partN("dadad")
                .qty(0)
                .build());
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
