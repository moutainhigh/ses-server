package com.redescooter.ses.service.foundation.service.impl.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaUserService extends IService<PlaUser>{


    int updateBatch(List<PlaUser> list);

    int batchInsert(List<PlaUser> list);

    int insertOrUpdate(PlaUser record);

    int insertOrUpdateSelective(PlaUser record);

}
