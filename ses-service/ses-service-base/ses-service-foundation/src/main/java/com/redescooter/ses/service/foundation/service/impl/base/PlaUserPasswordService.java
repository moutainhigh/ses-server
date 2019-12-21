package com.redescooter.ses.service.foundation.service.impl.base;

import com.redescooter.ses.service.foundation.dm.base.PlaUserPassword;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaUserPasswordService extends IService<PlaUserPassword>{


    int updateBatch(List<PlaUserPassword> list);

    int batchInsert(List<PlaUserPassword> list);

    int insertOrUpdate(PlaUserPassword record);

    int insertOrUpdateSelective(PlaUserPassword record);

}
