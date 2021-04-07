package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePayOrder;

import java.util.List;

public interface OpePayOrderService extends IService<OpePayOrder> {

    int updateBatch(List<OpePayOrder> list);

    int batchInsert(List<OpePayOrder> list);

    int insertOrUpdate(OpePayOrder record);

    int insertOrUpdateSelective(OpePayOrder record);

}
