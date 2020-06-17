package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.VersionBaseService;
import com.redescooter.ses.api.foundation.vo.app.VersionTypeEnter;
import com.redescooter.ses.api.foundation.vo.app.VersionTypeResult;
import com.redescooter.ses.service.foundation.dm.base.PlaAppVersion;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaAppVersionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassNameVersionBaseServiceImpl
 * @Description
 * @Author Joan
 * @Date2020/6/17 14:41
 * @Version V1.0
 **/
@Slf4j
@Service
public class VersionBaseServiceImpl implements VersionBaseService {

  @Autowired
  private PlaAppVersionService plaAppVersionService;

  @Override
  public VersionTypeResult getVersionData(VersionTypeEnter versionTypeEnter) {
    QueryWrapper<PlaAppVersion> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq(PlaAppVersion.COL_TYPE, versionTypeEnter.getType());
    queryWrapper.gt(PlaAppVersion.COL_CODE, versionTypeEnter.getCode());
    queryWrapper.orderByDesc(PlaAppVersion.COL_CREATED_TIME);
    queryWrapper.last("limit 1");
    PlaAppVersion one = plaAppVersionService.getOne(queryWrapper);
    if (one == null) {
      throw new FoundationException(ExceptionCodeEnums.VERSION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.VERSION_IS_NOT_EXIST.getMessage());
    }
    VersionTypeResult ersionTypeResult = new VersionTypeResult();
    BeanUtils.copyProperties(one, ersionTypeResult);
    return ersionTypeResult;
  }
}
