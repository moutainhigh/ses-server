package com.redescooter.ses.mobile.rps.service.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.assembly.*;

import java.util.List;

/**
 * @ClassName:AssemblyService
 * @description: AssemblyService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 19:30
 */
public interface AssemblyService {
    /**
     * 待组装列表
     *
     * @param enter
     * @return
     */
    PageResult<WaitAssemblyListResult> list(PageEnter enter);

    /**
     * 待组装详情
     *
     * @param enter
     * @return
     */
    PageResult<WaitAssemblyDetailResult> detail(AssemblyDetailEnter enter);

    /**
     * 产品配方
     *
     * @param enter
     * @return
     */
    List<ProductFormulaResult> formula(IdEnter enter);

    /**
     * 保存产品组装数据
     *
     * @param enter
     * @return
     */
    SaveFormulaDateResult save(SaveFormulaDateEnter enter);

    /**
     * 打印条码
     *
     * @param enter
     * @return
     */
    GeneralResult printCode(PrintCodeEnter enter);

    /**
     * 查询打印条码结果
     *
     * @param enter
     * @return
     */
    QueryProductCodeResult queryProductCode(IdEnter enter);
}
