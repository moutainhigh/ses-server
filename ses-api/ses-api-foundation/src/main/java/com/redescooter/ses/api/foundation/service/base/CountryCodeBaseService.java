package com.redescooter.ses.api.foundation.service.base;

import com.redescooter.ses.api.common.vo.base.BaseCountryCodeEnter;
import com.redescooter.ses.api.common.vo.base.BaseCountryCodeResult;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 27/12/2019 4:10 下午
 * @ClassName: CountryCodeBaseService
 * @Function: TODO
 */
public interface CountryCodeBaseService {

    List<BaseCountryCodeResult> getCountryCodeList(BaseCountryCodeEnter enter);
}
