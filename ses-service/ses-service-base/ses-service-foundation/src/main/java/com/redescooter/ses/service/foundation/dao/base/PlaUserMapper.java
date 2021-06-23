package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlaUserMapper extends BaseMapper<PlaUser> {
    int updateBatch(List<PlaUser> list);

    int batchInsert(@Param("list") List<PlaUser> list);

    int insertOrUpdate(PlaUser record);

    int insertOrUpdateSelective(PlaUser record);

    /**
     * 删除pla_user表
     */
    int deleteUser(@Param("userId") Long userId);

    /**
     * 删除pla_user_node表
     */
    int deleteUserNode(@Param("userId") Long userId);

    /**
     * 删除pla_user_password表
     */
    int deletePwd(@Param("email") String email);

    /**
     * 删除pla_user_permission表
     */
    int deletePermission(@Param("userId") Long userId);

}