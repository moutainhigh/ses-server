package com.redescooter.ses.web.ros.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dm.OpeSupplier;
import com.redescooter.ses.web.ros.vo.supplier.SupplierEditEnter;
import com.redescooter.ses.web.ros.vo.supplier.SupplierPage;
import com.redescooter.ses.web.ros.vo.supplier.SupplierResult;
import com.redescooter.ses.web.ros.vo.supplier.SupplierSaveEnter;


import java.util.Map;

public interface SupplierRosService {

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countStatus(GeneralEnter enter);

    /**
     * 保存采购商
     *
     * @param enter
     * @return
     */
    GeneralResult save(SupplierSaveEnter enter);

    /**
     * 编辑采购商
     *
     * @param enter
     * @return
     */
    GeneralResult edit(SupplierEditEnter enter);

    /**
     * 采购商详情
     *
     * @param enter
     * @return
     */
    SupplierResult details(IdEnter enter);


    /**
     * 采购商列表
     *
     * @param page
     * @return
     */
    PageResult<SupplierResult> list(SupplierPage page);

    /**
     * 保存采购商操作事件节点
     *
     * @param event
     * @param Supplier
     * @return
     */
    GeneralResult saveSupplierTrace(String event, OpeSupplier Supplier);

}
