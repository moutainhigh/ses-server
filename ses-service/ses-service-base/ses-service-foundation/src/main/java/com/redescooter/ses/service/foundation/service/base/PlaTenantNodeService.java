package com.redescooter.ses.service.foundation.service.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantNode;
import com.baomidou.mybatisplus.extension.service.IService;
public interface PlaTenantNodeService extends IService<PlaTenantNode>{


    int updateBatch(List<PlaTenantNode> list);

    int batchInsert(List<PlaTenantNode> list);

    int insertOrUpdate(PlaTenantNode record);

    int insertOrUpdateSelective(PlaTenantNode record);

}
