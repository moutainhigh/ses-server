package com.redescooter.ses.mobile.wh.fr.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.wh.fr.dm.OpeSpecificatType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeSpecificatTypeMapper extends BaseMapper<OpeSpecificatType> {
    int updateBatch(List<OpeSpecificatType> list);

    int batchInsert(@Param("list") List<OpeSpecificatType> list);

    int insertOrUpdate(OpeSpecificatType record);

    int insertOrUpdateSelective(OpeSpecificatType record);
}