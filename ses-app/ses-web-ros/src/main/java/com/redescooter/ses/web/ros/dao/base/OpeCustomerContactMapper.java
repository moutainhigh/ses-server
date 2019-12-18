package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomerContact;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeCustomerContactMapper extends BaseMapper<OpeCustomerContact> {
    int updateBatch(List<OpeCustomerContact> list);

    int batchInsert(@Param("list") List<OpeCustomerContact> list);

    int insertOrUpdate(OpeCustomerContact record);

    int insertOrUpdateSelective(OpeCustomerContact record);
}