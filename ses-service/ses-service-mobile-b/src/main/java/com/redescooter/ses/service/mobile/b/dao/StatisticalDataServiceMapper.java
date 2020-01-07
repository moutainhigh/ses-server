package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.mobile.b.vo.MobileBScooterChartResult;

import java.util.List;

/**
 * @ClassName:StatisticalDataServiceMapper
 * @description: StatisticalDataServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/01 17:44
 */
public interface StatisticalDataServiceMapper {


    List<MobileBScooterChartResult> mobileBScooterChart(DateTimeParmEnter enter);
}
