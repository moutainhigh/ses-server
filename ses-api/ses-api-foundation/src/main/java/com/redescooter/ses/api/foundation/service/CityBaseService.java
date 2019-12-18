package com.redescooter.ses.api.foundation.service;


import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.vo.common.CityByPageEnter;
import com.redescooter.ses.api.foundation.vo.common.CityEnter;
import com.redescooter.ses.api.foundation.vo.common.CityResult;

import java.util.List;

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
}
