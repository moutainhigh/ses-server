package com.redescooter.ses.admin.dev.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.admin.dev.dm.AdmSysUser;

import java.util.List;
public interface AdmSysUserService extends IService<AdmSysUser> {


    int updateBatch(List<AdmSysUser> list);

    int updateBatchSelective(List<AdmSysUser> list);

    int batchInsert(List<AdmSysUser> list);

    int insertOrUpdate(AdmSysUser record);

    int insertOrUpdateSelective(AdmSysUser record);

}
