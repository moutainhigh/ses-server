package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.BaseCountryCodeEnter;
import com.redescooter.ses.api.common.vo.base.BaseCountryCodeResult;
import com.redescooter.ses.api.foundation.service.base.CountryCodeBaseService;
import com.redescooter.ses.service.foundation.dao.base.PlaCountryCodeMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaCountryCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 27/12/2019 4:16 下午
 * @ClassName: CountryCodeBaseServiceImpl
 * @Function: TODO
 */
@Slf4j
@DubboService
public class CountryCodeBaseServiceImpl implements CountryCodeBaseService {

    @Autowired
    private PlaCountryCodeMapper countryCodeMapper;

    @Override
    public List<BaseCountryCodeResult> getCountryCodeList(BaseCountryCodeEnter enter) {

        List<BaseCountryCodeResult> results = new ArrayList<>();

        QueryWrapper<PlaCountryCode> query = new QueryWrapper<>();
        query.eq(PlaCountryCode.COL_DR, 0);
        if (StringUtils.isNotBlank(enter.getCountryLanguage())) {
            query.eq(PlaCountryCode.COL_COUNTRY_LANGUAGE, enter.getCountryLanguage());
        }
        if (StringUtils.isNotBlank(enter.getCountryName())) {
            query.eq(PlaCountryCode.COL_COUNTRY_NAME, enter.getCountryName());
        }

        List<PlaCountryCode> list = countryCodeMapper.selectList(query);

        if (list != null || list.isEmpty()) {
            list.forEach(cy -> {
                BaseCountryCodeResult baseCountryCodeResult = new BaseCountryCodeResult();
                BeanUtils.copyProperties(cy, baseCountryCodeResult);
                results.add(baseCountryCodeResult);
            });
        }

        return results;
    }
}
