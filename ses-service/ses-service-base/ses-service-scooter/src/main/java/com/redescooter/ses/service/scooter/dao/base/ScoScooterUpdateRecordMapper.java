package com.redescooter.ses.service.scooter.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterUpdateRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 平板升级更新记录表 Mapper 接口
 * </p>
 *
 * @author chris
 * @since 2021-05-26
 */
@Mapper
public interface ScoScooterUpdateRecordMapper extends BaseMapper<ScoScooterUpdateRecord> {

}