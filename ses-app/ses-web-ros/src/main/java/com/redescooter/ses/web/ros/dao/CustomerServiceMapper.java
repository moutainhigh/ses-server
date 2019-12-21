package com.redescooter.ses.web.ros.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 9:27 下午
 * @ClassName: CustomerServiceMapper
 * @Function: TODO
 */
public interface CustomerServiceMapper {

    List<CountByStatusResult> countStatus();

}
