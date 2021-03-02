package com.redescooter.ses.admin.dev.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.admin.dev.dm.AdmSysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdmSysUserMapper extends BaseMapper<AdmSysUser> {
    int updateBatch(List<AdmSysUser> list);

    int updateBatchSelective(List<AdmSysUser> list);

    int batchInsert(@Param("list") List<AdmSysUser> list);

    int insertOrUpdate(AdmSysUser record);

    int insertOrUpdateSelective(AdmSysUser record);
}