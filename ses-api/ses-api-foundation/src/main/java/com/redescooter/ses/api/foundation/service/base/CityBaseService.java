package com.redescooter.ses.api.foundation.service.base;

import java.util.List;

import com.redescooter.ses.api.common.vo.base.CityNameEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.vo.common.*;

/**
 * description: CityBaseService
 * author: jerry.li
 * create: 2019-05-27 16:27
 */

public interface CityBaseService {

    /**
     * 分页查询 根据条件查询城市信息列表
     *
     * @param enter
     * @return
     */
    PageResult<CityResult> queryCityByParameterPage(CityByPageEnter enter);

    /**
     * 根据id查询城市详情信息
     *
     * @param enter
     * @return
     */
    CityResult queryCityDeatliById(IdEnter enter);

    /**
     * 查询指定层级的下级
     *
     * @param enter
     * @return
     */
    List<CityResult> queryChildlevel(IdEnter enter);

    /**
     * 获取城市列表
     *
     * @param enter
     * @return
     */
    List<CityResult> list(GeneralEnter enter);

    /**
     * 根据名字查询 城市信息
     *
     * @param name
     * @return
     */
    CityResult queryCityDetailByName(String name);

    /**
     * 批量保存城市
     *
     * @param list
     * @return
     */
    int batchSaveCity(List<CityEnter> list);

    /**
     * @Author Aleks
     * @Description  国家和城市
     * @Date  2020/7/27 19:54
     * @Param
     * @return
     **/
    List<CountryCityResult> countryAndCity();

    /**
     * @Author Aleks
     * @Description  根据城市选择对应的邮编
     * @Date  2020/7/27 18:48
     * @Param
     * @return
     **/
     List<CityPostResult> cityPostCode(String cityName);


    List<CountryCityResult> countryCityPostCode(CityNameEnter cityNameEnter);

}
