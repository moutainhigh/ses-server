package com.redescooter.ses.web.ros.dao;

import java.util.List;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.supplierChaim.SupplierChaimListEnter;
import com.redescooter.ses.web.ros.vo.supplierChaim.SupplierChaimListResult;

/**
 * @ClassName:SupplierChaimRosService
 * @description: SupplierChaimRosService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/28 10:08
 */
public interface SupplierChaimRosServiceMapper {

    /**
     * @desc: 产品类型分类
     * @param: enter
     * @retrn: CountByStatusResult
     * @auther: alex
     * @date: 2020/2/28 10:12
     * @Version: Ros 1.2
     */
    List<CountByStatusResult> countByPartType(GeneralEnter enter);

    /**
     * @desc: 供应链列表
     * @param: enter
     * @retrn: int
     * @auther: alex
     * @date: 2020/2/28 10:49
     * @Version: Ros 1.2
     */
    int supplierChaimListCount(SupplierChaimListEnter enter);

    /**
     * @desc: 供应链列表
     * @param: enter
     * @retrn: SupplierChaimListResult
     * @auther: alex
     * @date: 2020/2/28 10:51
     * @Version: Ros 1.2
     */
    List<SupplierChaimListResult> supplierChaimList(SupplierChaimListEnter enter);
}
