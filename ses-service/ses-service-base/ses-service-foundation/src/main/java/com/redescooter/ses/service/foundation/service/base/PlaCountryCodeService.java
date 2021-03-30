package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaCountryCode;

import java.util.List;

/**
 * @author Mr.lijiating
 * @Date: 27/12/2019 4:05 下午
 * @ClassName: ${NAME}
 * @Function: TODO
 * @version V1.0
 */
public interface PlaCountryCodeService extends IService<PlaCountryCode> {

    int updateBatch(List<PlaCountryCode> list);

    int batchInsert(List<PlaCountryCode> list);

    int insertOrUpdate(PlaCountryCode record);

    int insertOrUpdateSelective(PlaCountryCode record);

}
