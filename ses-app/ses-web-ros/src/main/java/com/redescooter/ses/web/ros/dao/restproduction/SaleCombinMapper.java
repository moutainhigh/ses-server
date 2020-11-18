package com.redescooter.ses.web.ros.dao.restproduction;

import com.redescooter.ses.web.ros.vo.restproduct.SaleListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SaleCombinListResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Aleks
 * @Description  
 * @Date  2020/10/20 10:44
 * @Param
 * @return
 **/
@Repository
public interface SaleCombinMapper {

    int saleCombinTotal(@Param("enter") SaleListEnter enter);


    List<SaleCombinListResult> saleCombinList(@Param("enter") SaleListEnter enter);


}
