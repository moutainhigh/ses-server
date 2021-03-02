package com.redescooter.ses.service.common.service.impl;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.service.common.service.CityAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Map;

/**
 * @ClassName:CityAppServiceImpl
 * @description: CityAppServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/13 13:02
 */
@Slf4j
@DubboService
public class CityAppServiceImpl implements CityAppService {

    @DubboReference
    private CityBaseService cityBaseService;

    /**
     * 根据Code 查询城市名称
     *
     * @param id
     * @return
     */
    @Override
    public String getCityNameById(Long id) {
        CityResult cityResult = cityBaseService.queryCityDeatliById(new IdEnter(id));
        if (cityResult == null) {
            return null;
        }
        return cityResult.getName();
    }

    /**
     * 根据名字 查询code
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> getCityCodeByName(String enter) {
        return null;
    }
}
