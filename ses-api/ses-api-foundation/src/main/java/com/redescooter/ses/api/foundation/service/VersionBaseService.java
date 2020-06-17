package com.redescooter.ses.api.foundation.service;

import com.redescooter.ses.api.foundation.vo.app.VersionTypeEnter;
import com.redescooter.ses.api.foundation.vo.app.VersionTypeResult;

/**
 * @ClassNameAppBaseService
 * @Description
 * @Author Joan
 * @Date2020/6/17 11:48
 * @Version V1.0
 **/
public interface VersionBaseService {
  /**
   * 获取版本信息
   *
   * @return
   * @author joan
   */
  VersionTypeResult getVersionData(VersionTypeEnter versionTypeEnter);


}
