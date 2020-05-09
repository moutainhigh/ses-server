package com.redescooter.ses.mobile.rps.dao.assembly;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.mobile.rps.vo.assembly.AssemblyDetailEnter;
import com.redescooter.ses.mobile.rps.vo.assembly.ProductFormulaResult;
import com.redescooter.ses.mobile.rps.vo.assembly.WaitAssemblyDetailResult;
import com.redescooter.ses.mobile.rps.vo.assembly.WaitAssemblyListResult;

import java.util.List;

/**
 * @ClassName:AssemblyServiceMapper
 * @description: AssemblyServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/16 21:01
 */
public interface AssemblyServiceMapper {

    /**
     * 列表数据
     *
     * @param enter
     * @return
     */
    List<WaitAssemblyListResult> list(PageEnter enter);

    /**
     * 详情列表数据
     *
     * @param enter
     * @return
     */
    List<WaitAssemblyDetailResult> detailList(AssemblyDetailEnter enter);

    /**
     * 获取产品配方
     *
     * @param id
     * @return
     */
    List<ProductFormulaResult> formulaByAssemblyBId(Long id);
}
