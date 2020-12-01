package com.redescooter.ses.web.ros.dao.restproduction;

import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterListResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassNameOpeSaleScooterMapper
 * @Description
 * @Author Aleks
 * @Date2020/10/12 17:09
 * @Version V1.0
 **/
@Repository
public interface SaleScooterMapper {

   int saleScooterTotal(@Param("enter") SaleScooterListEnter enter);


    List<SaleScooterListResult> saleScooterList(@Param("enter") SaleScooterListEnter enter);

}
