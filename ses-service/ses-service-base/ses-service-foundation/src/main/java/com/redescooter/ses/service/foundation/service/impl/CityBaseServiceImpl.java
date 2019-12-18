package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityByPageEnter;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.service.foundation.dao.base.PlaCityMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaCity;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * description: CityBaseServiceImpl
 * author: jerry.li
 * create: 2019-05-27 17:05
 */

@Slf4j
@Service
public class CityBaseServiceImpl implements CityBaseService {

    @Autowired
    private PlaCityMapper cityMapper;

    /**
     * 分页查询 根据条件查询城市信息列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<CityResult> queryCityByParameterPage(CityByPageEnter enter) {

        List<CityResult> resultlist = new ArrayList<>();
        CityResult result = null;

        QueryWrapper<PlaCity> wrapper = new QueryWrapper<>();
        if (enter.getId() != null) {
            wrapper.eq("id", enter.getId());
        } else if (enter.getPId() != null) {
            wrapper.eq(PlaCity.COL_P_ID, enter.getId());
        }

        Integer totalRows = cityMapper.selectCount(wrapper);
        if (totalRows == null || totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        List<PlaCity> plaCities = cityMapper.selectList(wrapper);

        for (PlaCity city : plaCities) {
            result = new CityResult();
            BeanUtils.copyProperties(city, result);
            resultlist.add(result);
        }

        return PageResult.create(enter, totalRows, resultlist);
    }

    /**
     * 根据id查询城市详情信息
     *
     * @param enter
     * @return
     */
    @Override
    public CityResult queryCityDeatliById(IdEnter enter) {

        PlaCity city = cityMapper.selectById(enter.getId());

        CityResult result = new CityResult();
        BeanUtils.copyProperties(city, result);

        return result;
    }

    /**
     * 查询指定层级的下级
     *
     * @param enter
     * @return
     */
    @Override
    public List<CityResult> queryChildlevel(IdEnter enter) {

        QueryWrapper<PlaCity> wrapper = new QueryWrapper<>();
        List<CityResult> resultlist = new ArrayList<>();
        CityResult result = null;

        if (enter.getId() == null) {
            wrapper.eq(PlaCity.COL_LEVEL, 0);
        } else {
            wrapper.eq(PlaCity.COL_P_ID, enter.getId());
        }
        List<PlaCity> plaCities = cityMapper.selectList(wrapper);
        for (PlaCity city : plaCities) {
            result = new CityResult();
            BeanUtils.copyProperties(city, result);
            resultlist.add(result);
        }
        return resultlist;
    }
}
