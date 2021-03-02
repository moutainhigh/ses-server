package com.redescooter.ses.admin.dev.service.datacount;

import com.redescooter.ses.api.common.vo.count.CustomerCountEnter;
import com.redescooter.ses.api.common.vo.count.CustomerCountResult;
import com.redescooter.ses.api.common.vo.count.ScooterCountEnter;
import com.redescooter.ses.api.common.vo.count.ScooterCountResult;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * @ClassName:DataCountService
 * @description: DataCountService
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/17 11:43
 */
public interface DataCountService {

    /**
     * 客户统计
     * @param enter
     * @return
     */
    List<CustomerCountResult> customerCount(CustomerCountEnter enter);


    /**
     * 车辆销售统计
     * @param enter
     * @return
     */
    ScooterCountResult scooterCount(ScooterCountEnter enter);

}
