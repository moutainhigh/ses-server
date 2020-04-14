package com.redescooter.ses.mobile.rps.service.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.mobile.rps.vo.assembly.PrintCodeEnter;
import com.redescooter.ses.mobile.rps.vo.assembly.ProductFormulaResult;
import com.redescooter.ses.mobile.rps.vo.assembly.SaveFormulaDateEnter;
import com.redescooter.ses.mobile.rps.vo.assembly.SaveFormulaDateResult;
import com.redescooter.ses.mobile.rps.vo.assembly.WaitAssemblyDetailResult;
import com.redescooter.ses.mobile.rps.vo.assembly.WaitAssemblyListResult;

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
    WaitAssemblyListResult list(GeneralEnter enter);

    /**
     * 待组装详情
     *
     * @param enter
     * @return
     */
    WaitAssemblyDetailResult detail(GeneralEnter enter);

    /**
     * 产品配方
     *
     * @param enter
     * @return
     */
    ProductFormulaResult formula(IdEnter enter);

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
    GeneralEnter printCode(PrintCodeEnter enter);
}
