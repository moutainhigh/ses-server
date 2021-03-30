package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantNode;

import java.util.List;

public interface PlaTenantNodeService extends IService<PlaTenantNode> {


    int updateBatch(List<PlaTenantNode> list);

    int batchInsert(List<PlaTenantNode> list);

    int insertOrUpdate(PlaTenantNode record);

    int insertOrUpdateSelective(PlaTenantNode record);

}
