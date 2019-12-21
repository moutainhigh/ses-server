package com.redescooter.ses.service.foundation.service.impl.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantNode;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaTenantNodeService extends IService<PlaTenantNode>{


    int updateBatch(List<PlaTenantNode> list);

    int batchInsert(List<PlaTenantNode> list);

    int insertOrUpdate(PlaTenantNode record);

    int insertOrUpdateSelective(PlaTenantNode record);

}
