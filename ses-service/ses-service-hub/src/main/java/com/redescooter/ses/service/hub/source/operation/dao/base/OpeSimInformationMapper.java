package com.redescooter.ses.service.hub.source.operation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.api.hub.vo.operation.OpeSimInformation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * sim卡信息 Mapper 接口
 * </p>
 *
 * @author chris
 * @since 2021-06-21
 */
@Mapper
public interface OpeSimInformationMapper extends BaseMapper<OpeSimInformation> {

}