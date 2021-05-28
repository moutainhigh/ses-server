package com.redescooter.ses.web.ros.dao.sim;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSimInformation;
import org.apache.ibatis.annotations.Mapper;

/**
 * sim卡信息(OpeSimInformation)表数据库访问层
 *
 * @author Charles
 * @since 2021-05-26 20:58:05
 */
@Mapper
public interface OpeSimInformationMapper extends BaseMapper<OpeSimInformation> {

}
