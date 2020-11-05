package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeInvoiceProductSerialNum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeInvoiceProductSerialNumMapper extends BaseMapper<OpeInvoiceProductSerialNum> {
    int updateBatch(List<OpeInvoiceProductSerialNum> list);

    int batchInsert(@Param("list") List<OpeInvoiceProductSerialNum> list);

    int insertOrUpdate(OpeInvoiceProductSerialNum record);

    int insertOrUpdateSelective(OpeInvoiceProductSerialNum record);
}