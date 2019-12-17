package com.redescooter.ses.service.common.service;

import java.util.Map;

/**
 * @ClassName:CityService
 * @description: CityAppService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/08/22 17:02
 */
public interface CityAppService {
    /**
     * 根据Code 查询城市名称
     * @param id
     * @return
     */
    String getCityNameById(Long id);

    /**
     * 根据名字 查询code
     * @param enter
     * @return
     */
    Map<String, String> getCityCodeByName(String enter);
}
