package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrder;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeOutWhouseOrderService extends IService<OpeOutWhouseOrder> {


    int updateBatch(List<OpeOutWhouseOrder> list);

    int batchInsert(List<OpeOutWhouseOrder> list);

    int insertOrUpdate(OpeOutWhouseOrder record);

    int insertOrUpdateSelective(OpeOutWhouseOrder record);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(OpeOutWhouseOrder record);

    OpeOutWhouseOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpeOutWhouseOrder record);

    int updateByPrimaryKey(OpeOutWhouseOrder record);
}


