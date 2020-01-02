package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.api.mobile.b.vo.MonthlyScooterChartResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName:StatisticalDataServiceMapper
 * @description: StatisticalDataServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/01 17:44
 */
public interface StatisticalDataServiceMapper {


    List<MonthlyScooterChartResult> mobileBScooterChart(@Param("driverId") Long driverId, @Param("tenantId") Long tenantId);
}
