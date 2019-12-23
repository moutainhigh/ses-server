package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;

import java.util.List;

/**
* @author      Mr.lijiating
* @Date:       24/12/2019 12:12 上午
* @ClassName:  ${NAME}
* @Function:   TODO
* @version     V1.0
*/
public interface PlaTenantService extends IService<PlaTenant> {


int updateBatch(List<PlaTenant> list);

int batchInsert(List<PlaTenant> list);

int insertOrUpdate(PlaTenant record);

int insertOrUpdateSelective(PlaTenant record);

}
