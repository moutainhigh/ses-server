package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaCity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author      Mr.lijiating
* @Date:       24/12/2019 12:11 上午
* @ClassName:  ${NAME}
* @Function:   TODO
* @version     V1.0
*/
@Transactional
public interface PlaCityService extends IService<PlaCity> {


int updateBatch(List<PlaCity> list);

int batchInsert(List<PlaCity> list);

int insertOrUpdate(PlaCity record);

int insertOrUpdateSelective(PlaCity record);

}
