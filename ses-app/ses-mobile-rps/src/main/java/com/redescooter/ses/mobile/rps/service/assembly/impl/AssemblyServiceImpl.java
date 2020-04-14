package com.redescooter.ses.mobile.rps.service.assembly.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.service.assembly.AssemblyService;
import com.redescooter.ses.mobile.rps.vo.assembly.*;
import org.springframework.stereotype.Service;

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
        return null;
    }

    /**
     * 待组装详情
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WaitAssemblyDetailResult> detail(AssemblyDetailEnter enter) {
        return null;
    }

    /**
     * 产品配方
     *
     * @param enter
     * @return
     */
    @Override
    public ProductFormulaResult formula(IdEnter enter) {
        return null;
    }

    /**
     * 保存产品组装数据
     *
     * @param enter
     * @return
     */
    @Override
    public SaveFormulaDateResult save(SaveFormulaDateEnter enter) {
        return null;
    }

    /**
     * 打印条码
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult printCode(PrintCodeEnter enter) {
        return null;
    }

    /**
     * 查询打印条码结果
     *
     * @param enter
     * @return
     */
    @Override
    public QueryProductCodeResult queryProductCode(IdEnter enter) {
        return null;
    }
}
