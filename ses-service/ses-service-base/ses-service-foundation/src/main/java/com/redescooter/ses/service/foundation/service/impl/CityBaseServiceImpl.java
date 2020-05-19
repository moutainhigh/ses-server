package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityByPageEnter;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.service.foundation.dao.base.PlaCityMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaCity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        wrapper.orderByAsc(PlaCity.COL_CODE);
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

        if (enter == null) {
            return new CityResult();
        }

        PlaCity city = cityMapper.selectById(enter.getId());

        CityResult result = new CityResult();
        if (city != null) {
            BeanUtils.copyProperties(city, result);
        }
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
        wrapper.orderByAsc(PlaCity.COL_CODE);
        List<PlaCity> plaCities = cityMapper.selectList(wrapper);
        for (PlaCity city : plaCities) {
            result = new CityResult();
            BeanUtils.copyProperties(city, result);
            resultlist.add(result);
        }
        return resultlist;
    }

    @Override
    public List<CityResult> list(GeneralEnter enter) {

        QueryWrapper<PlaCity> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaCity.COL_DR, Constant.DR_FALSE);
        List<PlaCity> plaCities = cityMapper.selectList(wrapper);

        List<CityResult> resultlist = new ArrayList<>();
        for (PlaCity city : plaCities) {
            CityResult result = new CityResult();
            BeanUtils.copyProperties(city, result);
            resultlist.add(result);
        }
        return resultlist;
    }

    /**
     * 根据名字查询 城市信息
     *
     * @param name
     * @return
     */
    @Override
    public CityResult queryCityDetailByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return new CityResult();
        }

        PlaCity city = cityMapper.selectOne(new LambdaQueryWrapper<PlaCity>().eq(PlaCity::getName,name));

        CityResult result = new CityResult();
        if (city != null) {
            BeanUtils.copyProperties(city, result);
        }
        return result;
    }
}
