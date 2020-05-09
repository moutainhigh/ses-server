package com.redescooter.ses.service.hub.source.operation.service.base;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.service.hub.source.operation.dm.OpeOrgStaff;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 23/12/2019 11:23 下午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@DS("operation")
public interface OpeOrgStaffService extends IService<OpeOrgStaff> {


    int updateBatch(List<OpeOrgStaff> list);

    int batchInsert(List<OpeOrgStaff> list);

    int insertOrUpdate(OpeOrgStaff record);

    int insertOrUpdateSelective(OpeOrgStaff record);

}
