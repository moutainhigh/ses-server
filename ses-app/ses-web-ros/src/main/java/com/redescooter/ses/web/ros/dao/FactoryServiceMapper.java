package com.redescooter.ses.web.ros.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.factory.FactoryPage;
import com.redescooter.ses.web.ros.vo.factory.FactoryResult;

import java.util.List;

public interface FactoryServiceMapper {

    List<CountByStatusResult> countStatus(GeneralEnter enter);

    int listCount(FactoryPage page);

    List<FactoryResult> list(FactoryPage page);

}
