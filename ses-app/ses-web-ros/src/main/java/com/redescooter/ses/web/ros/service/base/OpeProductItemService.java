package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeProductItemService extends IService<OpeProductItem> {


    int updateBatch(List<OpeProductItem> list);

    int batchInsert(List<OpeProductItem> list);

    int insertOrUpdate(OpeProductItem record);

    int insertOrUpdateSelective(OpeProductItem record);

}

