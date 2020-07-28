package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.api.foundation.vo.common.CityPostResult;
import com.redescooter.ses.service.foundation.dm.base.PlaCity;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaCityMapper extends BaseMapper<PlaCity> {
    int updateBatch(List<PlaCity> list);

    int batchInsert(@Param("list") List<PlaCity> list);

    int insertOrUpdate(PlaCity record);

    int insertOrUpdateSelective(PlaCity record);

    List<PlaCity> countryAndCity();

    List<CityPostResult> cityPostCode(@Param("cityName") String cityName);
}