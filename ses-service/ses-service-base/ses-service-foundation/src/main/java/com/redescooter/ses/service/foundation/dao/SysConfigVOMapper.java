package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.foundation.vo.common.SysConfigVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysConfigVOMapper {

    /**
     * 获取配置表中所有配置，慎用。
     */
    @Select("SELECT * FROM pla_sys_config")
    List<SysConfigVO> getAll();

    /**
     * 获取配置表中某一个配置组中的所有配置。
     */
    @Select("SELECT * FROM pla_sys_config WHERE `group` = #{group}")
    List<SysConfigVO> getByGroup(String group);

    /**
     * 获取配置表中某一个配置项的的配。
     */
    @Select("SELECT * FROM pla_sys_config WHERE `group` = #{group} AND `key` = #{key}")
    SysConfigVO get(@Param("group") String group, @Param("key") String key);
}
