package com.redescooter.ses.service.hub.source.website.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.website.dm.SiteParts;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 配件表 Mapper 接口
 * </p>
 *
 * @author chris
 * @since 2021-03-16
 */
@Mapper
@DS("website")
public interface SitePartsMapper extends BaseMapper<SiteParts> {

}