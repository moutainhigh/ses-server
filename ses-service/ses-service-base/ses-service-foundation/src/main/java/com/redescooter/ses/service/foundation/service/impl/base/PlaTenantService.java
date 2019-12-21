package com.redescooter.ses.service.foundation.service.impl.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaTenantService extends IService<PlaTenant>{


    int updateBatch(List<PlaTenant> list);

    int batchInsert(List<PlaTenant> list);

    int insertOrUpdate(PlaTenant record);

    int insertOrUpdateSelective(PlaTenant record);

}
