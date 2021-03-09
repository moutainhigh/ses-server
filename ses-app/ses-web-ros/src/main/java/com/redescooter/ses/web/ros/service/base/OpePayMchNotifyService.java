package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePayMchNotify;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpePayMchNotifyService extends IService<OpePayMchNotify> {

    int updateBatch(List<OpePayMchNotify> list);

    int batchInsert(List<OpePayMchNotify> list);

    int insertOrUpdate(OpePayMchNotify record);

    int insertOrUpdateSelective(OpePayMchNotify record);

}
