package com.redescooter.ses.service.foundation.service.impl.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaJpushUser;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaJpushUserService extends IService<PlaJpushUser>{


    int updateBatch(List<PlaJpushUser> list);

    int batchInsert(List<PlaJpushUser> list);

    int insertOrUpdate(PlaJpushUser record);

    int insertOrUpdateSelective(PlaJpushUser record);

}
