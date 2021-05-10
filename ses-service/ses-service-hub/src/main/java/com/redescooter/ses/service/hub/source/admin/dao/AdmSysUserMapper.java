package com.redescooter.ses.service.hub.source.admin.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.api.common.vo.base.OperatingEnter;
import com.redescooter.ses.api.hub.vo.admin.AdmSysUser;
import com.redescooter.ses.api.hub.vo.admin.OperatingAccountListResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Courtney
 * @since 2021-04-30
 */
@Mapper
public interface AdmSysUserMapper extends BaseMapper<AdmSysUser> {

    int accountTotal(@Param("enter") OperatingEnter enter);

    List<OperatingAccountListResult> accountList(@Param("enter")  OperatingEnter enter);

    OperatingAccountListResult getAccountById(String loginName,Long id);

    }