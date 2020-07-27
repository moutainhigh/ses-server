package com.redescooter.ses.service.foundation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.*;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.base.PlaCityMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaCity;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Reference
    private IdAppService idAppService;

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

        PlaCity city = cityMapper.selectOne(new LambdaQueryWrapper<PlaCity>().eq(PlaCity::getName, name));

        CityResult result = new CityResult();
        if (city != null) {
            BeanUtils.copyProperties(city, result);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchSaveCity(List<CityEnter> list) {

        //存在所有城市
        List<PlaCity> saveList = new ArrayList<>();

        if (list.size() > 0) {
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
        List<CountryCityResult>  resultList = new ArrayList<>();
        List<PlaCity> alls = cityMapper.countryAndCity();


        return resultList;
    }


    public List<CountryCityResult> countryCity( List<CountryCityResult>  resultList,List<PlaCity> alls){
        List<PlaCity> conuts = alls.stream().filter(all->all.getLevel() == 1).collect(Collectors.toList());
        List<PlaCity> citys = alls.stream().filter(all->all.getLevel() == 2).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(conuts)){
            for (PlaCity conut : conuts) {
                CountryCityResult countryCityResult = new CountryCityResult();
                BeanUtil.copyProperties(conut,countryCityResult);
                List<CityResult> cityResults = new ArrayList<>();
                for(PlaCity city : citys){
                    if(city.getPId() == conut.getId()){
                        CityResult cityResult = new CityResult();
                        BeanUtil.copyProperties(city,cityResult);
                        cityResults.add(cityResult);
                    }
                }
                countryCityResult.setCitys(cityResults);
                resultList.add(countryCityResult);
            }
        }
        return resultList;
    }


    @Override
    public List<CityPostResult> cityPostCode(String cityName) {
        List<CityPostResult> resultList = new ArrayList<>();
        resultList = cityMapper.cityPostCode(cityName);
        return resultList;
    }

    /**
     * 删除ArrayList中重复元素，保持顺序
     *
     * @param list
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
}
