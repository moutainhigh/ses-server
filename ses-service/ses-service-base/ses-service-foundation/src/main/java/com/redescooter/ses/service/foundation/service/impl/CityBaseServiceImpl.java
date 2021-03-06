package com.redescooter.ses.service.foundation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.CityNameEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityByPageEnter;
import com.redescooter.ses.api.foundation.vo.common.CityEnter;
import com.redescooter.ses.api.foundation.vo.common.CityPostResult;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.api.foundation.vo.common.CountryCityResult;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.base.PlaCityMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaCity;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * description: CityBaseServiceImpl
 * author: jerry.li
 * create: 2019-05-27 17:05
 */
@Slf4j
@DubboService
public class CityBaseServiceImpl implements CityBaseService {

    @Autowired
    private PlaCityMapper cityMapper;

    @DubboReference
    private IdAppService idAppService;

    /**
     * ???????????? ????????????????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<CityResult> queryCityByParameterPage(CityByPageEnter enter) {

        List<CityResult> resultlist = new ArrayList<>();
        CityResult result = null;

        QueryWrapper<PlaCity> wrapper = new QueryWrapper<>();
        if (null != enter.getId()) {
            wrapper.eq("id", enter.getId());
        } else if (null != enter.getPId()) {
            wrapper.eq(PlaCity.COL_P_ID, enter.getId());
        }

        Integer totalRows = cityMapper.selectCount(wrapper);
        if (null == totalRows || 0 == totalRows) {
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
     * ??????id????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public CityResult queryCityDeatliById(IdEnter enter) {

        if (null == enter) {
            return new CityResult();
        }

        PlaCity city = cityMapper.selectById(enter.getId());

        CityResult result = new CityResult();
        if (null != city) {
            BeanUtils.copyProperties(city, result);
        }
        return result;
    }

    /**
     * ???????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<CityResult> queryChildlevel(IdEnter enter) {

        QueryWrapper<PlaCity> wrapper = new QueryWrapper<>();
        List<CityResult> resultlist = new ArrayList<>();
        CityResult result = null;

        if (null == enter.getId()) {
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
     * ?????????????????? ????????????
     *
     * @param name
     * @return
     */
    @Override
    public CityResult queryCityDetailByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return new CityResult();
        }

        PlaCity city = cityMapper.selectOne(new LambdaQueryWrapper<PlaCity>().eq(PlaCity::getName, name));

        CityResult result = new CityResult();
        if (null != city) {
            BeanUtils.copyProperties(city, result);
        }
        return result;
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int batchSaveCity(List<CityEnter> list) {

        //??????????????????
        List<PlaCity> saveList = new ArrayList<>();

        if (0 < list.size()) {
            list.forEach(c -> {
                PlaCity city = new PlaCity();
                city.setId(idAppService.getId(SequenceName.PLA_CITY));
                city.setDr(Constant.DR_FALSE);
                city.setPId(c.getPId());
                city.setLevel(c.getLevel());
                city.setCode(c.getCode());
                city.setName(c.getName());
                city.setLanguage(c.getLanguage());
                city.setStatus(c.getStatus());
                city.setLongitude(c.getLongitude());
                city.setLatitude(c.getLatitude());
                city.setTimeZone("UTC +1");
                city.setDef1(c.getDef1());
                city.setCreatedTime(new Date());
                city.setCreatedBy(new Long("0"));
                city.setUpdatedTime(new Date());
                city.setUpdatedBy(new Long("0"));
                saveList.add(city);
            });
            return cityMapper.batchInsert(saveList);
        }
        return 0;
    }

    @Override
    public List<CountryCityResult> countryAndCity() {
        List<CountryCityResult> resultList = new ArrayList<>();
        List<PlaCity> alls = cityMapper.countryAndCity();
        countryCity(resultList, alls);
        return resultList;
    }


    public List<CountryCityResult> countryCity(List<CountryCityResult> resultList, List<PlaCity> alls) {
        List<PlaCity> conuts = alls.stream().filter(all -> all.getLevel() == 1).collect(Collectors.toList());
        List<PlaCity> citys = alls.stream().filter(all -> all.getLevel() == 2).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(conuts)) {
        }
        return resultList;
    }


    @Override
    public List<CityPostResult> cityPostCode(String cityName) {
        List<CityPostResult> resultList = new ArrayList<>();
        resultList = cityMapper.cityPostCode(cityName);
        return resultList;
    }

    @Override
    public List<CountryCityResult> countryCityPostCode(CityNameEnter cityNameEnter) {
        Integer level = cityNameEnter.getLevel();
        List<CountryCityResult> list = new ArrayList<>();
        QueryWrapper<PlaCity> qw = new QueryWrapper<>();
        switch (level) {
            case 1:
                // ???????????????
                qw.eq(PlaCity.COL_LEVEL, 1);
                if (!Strings.isNullOrEmpty(cityNameEnter.getKeyWord())) {
                    qw.like(PlaCity.COL_NAME, cityNameEnter.getKeyWord());
                }
            default:
                break;
            case 2:
                // ????????????
                if (Strings.isNullOrEmpty(cityNameEnter.getKeyWord())) {
                    return list;
                }
                qw.eq(PlaCity.COL_P_ID, cityNameEnter.getId());
                if (!Strings.isNullOrEmpty(cityNameEnter.getKeyWord())) {
                    qw.like(PlaCity.COL_NAME, cityNameEnter.getKeyWord());
                }
                qw.select(" DISTINCT name");
                qw.last(" group by name order by CHARACTER_LENGTH(name) limit 50");
                break;
            case 3:
                //??????????????????
                // ????????????????????? ????????????
                if (Strings.isNullOrEmpty(cityNameEnter.getCity())) {
                    return list;
                }
                qw.isNotNull(PlaCity.COL_POST_CODE);
                qw.eq(PlaCity.COL_NAME, cityNameEnter.getCity());
                if (!Strings.isNullOrEmpty(cityNameEnter.getKeyWord())) {
                    qw.like(PlaCity.COL_POST_CODE, cityNameEnter.getKeyWord());
                }
                break;
        }
        List<PlaCity> all = cityMapper.selectList(qw);
        if (CollectionUtils.isNotEmpty(all)) {
            formatData(list, level, all);
        }
        return list;
    }


    public List<CountryCityResult> formatData(List<CountryCityResult> list, Integer level, List<PlaCity> cityList) {
        switch (level) {
            case 1:
                // ???????????????
                for (PlaCity city : cityList) {
                    CountryCityResult countryCityResult = new CountryCityResult();
                    BeanUtil.copyProperties(city, countryCityResult);
                    list.add(countryCityResult);
                }
            default:
                break;
            case 2:
                // ???????????????,?????????????????????
                // ?????????????????????
//                Map<String, List<PlaCity>> map = cityList.stream().collect(Collectors.groupingBy(PlaCity::getName));
                for (PlaCity city : cityList) {
                    CountryCityResult countryCityResult = new CountryCityResult();
                    countryCityResult.setName(city.getName());
                    list.add(countryCityResult);
                }
                break;
            case 3:
                //??????????????????  ?????????
                Set<String> sets = cityList.stream().map(PlaCity::getPostCode).collect(Collectors.toSet());
                for (String set : sets) {
                    CountryCityResult countryCityResult = new CountryCityResult();
                    countryCityResult.setName(set);
                    list.add(countryCityResult);
                }
                break;
        }
        return list;
    }


    /**
     * ??????ArrayList??????????????????????????????
     * <p>
     * //     * @param list
     */
//    private List<CityEnter> removeDuplicateWithOrder(List<CityEnter> list) {
//        Set set = new HashSet();
//        List newList = new ArrayList();
//        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
//            Object element = iter.next();
//            if (set.add(element)) {
//                newList.add(element);
//            }
//        }
//        list.clear();
//        list.addAll(newList);
//        System.out.println(" remove duplicate " + list);
//
//        return newList;
//    }
    @Override
    public Long getDistrictId(String cityName, String districtPost) {
        QueryWrapper<PlaCity> qw = new QueryWrapper<>();
        qw.eq(PlaCity.COL_NAME, cityName);
        qw.eq(PlaCity.COL_POST_CODE, districtPost);
        qw.last("limit 1");
        PlaCity plaCity = cityMapper.selectOne(qw);
        if (null == plaCity) {
            return 0L;
        }
        return plaCity.getId();
    }
}
