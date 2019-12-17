package com.redescooter.ses.service.foundation.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityByPageEnter;
import com.redescooter.ses.api.foundation.vo.common.CityEnter;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

/**
 * description: CityBaseServiceImpl
 * author: jerry.li
 * create: 2019-05-27 17:05
 */

@Slf4j
@Service
public class CityBaseServiceImpl implements CityBaseService {

    /**
     * 根据条件查询城市信息列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<CityResult> queryCityByParameter(CityEnter enter) {
        return null;
    }

    /**
     * 分页查询 根据条件查询城市信息列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<CityResult> queryCityByParameterPage(CityByPageEnter enter) {
        return null;
    }

    /**
     * 根据id查询城市详情信息
     *
     * @param enter
     * @return
     */
    @Override
    public CityResult queryCityDeatliById(CityEnter enter) {
        return null;
    }

    /**
     * 查询全部城市
     *
     * @param enter
     * @return
     */
    @Override
    public List<CityResult> queryAllCity(GeneralEnter enter) {
        return null;
    }

    /**
     * 查询全部城市分页
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<CityResult> queryAllCityPage(PageEnter enter) {
        return null;
    }

    /**
     * 查询指定层级的下级
     *
     * @param cityId
     * @return
     */
    @Override
    public List<CityResult> queryChildlevelByCity(Long cityId) {
        return null;
    }
}
