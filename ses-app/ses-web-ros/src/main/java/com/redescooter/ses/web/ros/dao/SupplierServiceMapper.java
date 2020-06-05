package com.redescooter.ses.web.ros.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.supplier.SupplierPage;
import com.redescooter.ses.web.ros.vo.supplier.SupplierResult;

import java.util.List;

public interface SupplierServiceMapper {

    List<CountByStatusResult> countStatus(GeneralEnter enter);

    int listCount(SupplierPage page);

    List<SupplierResult> list(SupplierPage page);

}
