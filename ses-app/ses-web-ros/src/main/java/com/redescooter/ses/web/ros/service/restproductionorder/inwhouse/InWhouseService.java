package com.redescooter.ses.web.ros.service.restproductionorder.inwhouse;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListResult;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @ClassNameInWhouseService
 * @Description
 * @Author Aleks
 * @Date2020/11/11 10:20
 * @Version V1.0
 **/
public interface InWhouseService {

    /**
     * @Author Aleks
     * @Description  入库单列表接口
     * @Date  2020/11/11 10:50
     * @Param [enter]
     * @return
     **/
    PageResult<InWhouseListResult> inWhouseList(InWhouseListEnter enter);

}
