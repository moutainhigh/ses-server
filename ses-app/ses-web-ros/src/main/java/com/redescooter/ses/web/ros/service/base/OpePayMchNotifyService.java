package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePayMchNotify;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePayMchNotifyService extends IService<OpePayMchNotify> {

    int updateBatch(List<OpePayMchNotify> list);

    int batchInsert(List<OpePayMchNotify> list);

    int insertOrUpdate(OpePayMchNotify record);

    int insertOrUpdateSelective(OpePayMchNotify record);

}
