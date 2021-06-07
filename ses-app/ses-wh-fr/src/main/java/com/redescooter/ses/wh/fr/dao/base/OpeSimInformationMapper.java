package com.redescooter.ses.wh.fr.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.wh.fr.dm.OpeSimInformation;
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
