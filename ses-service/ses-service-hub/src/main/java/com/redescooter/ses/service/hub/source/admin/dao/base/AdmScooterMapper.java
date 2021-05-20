package com.redescooter.ses.service.hub.source.admin.dao.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.api.hub.vo.admin.AdmScooter;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆管理表 Mapper 接口
 * </p>
 *
 * @author chris
 * @since 2021-05-13
 */
@Mapper
@DS("admin")
public interface AdmScooterMapper extends BaseMapper<AdmScooter> {

}