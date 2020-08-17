package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.AppSysTypeEnum;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.VersionBaseService;
import com.redescooter.ses.api.foundation.vo.app.VersionTypeEnter;
import com.redescooter.ses.api.foundation.vo.app.VersionTypeResult;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dm.base.PlaAppVersion;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaAppVersionService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.FileUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

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

  @Reference
  private IdAppService idAppService;

//  @Value("${fileUpload.path}")
  private String uploadPath;

  @Override
  public VersionTypeResult getVersionData(VersionTypeEnter versionTypeEnter) {
    VersionTypeResult ersionTypeResult = new VersionTypeResult();
    QueryWrapper<PlaAppVersion> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq(PlaAppVersion.COL_TYPE, versionTypeEnter.getType());
    queryWrapper.gt(PlaAppVersion.COL_CODE, Integer.valueOf(versionTypeEnter.getCode()));
    queryWrapper.orderByDesc(PlaAppVersion.COL_CREATED_TIME);
    queryWrapper.last("limit 1");
    PlaAppVersion one = plaAppVersionService.getOne(queryWrapper);
    if (one != null) {
      BeanUtils.copyProperties(one, ersionTypeResult);
    }
    return ersionTypeResult;
  }

    @SneakyThrows
    @Override
    public void fileUpload(MultipartFile file, String fileMsg) {
      // 1、上传文件到服务器上,返回文件的路劲
      String path = FileUtil.uploadFile(file, uploadPath);
      // 2、检测文件的大小 (单位是兆：M)
      long size = file.getSize() / 1024 / 1024;
      // 3、上传完成之后，往APP的版本表里插入数据（以便安卓那边的APP版本升级）
      PlaAppVersion appVersion = new PlaAppVersion();
      appVersion.setId(idAppService.getId(SequenceName.PLA_APP_VERSION));
      appVersion.setSystemId("");
      appVersion.setAppId("");
      appVersion.setSystemType(AppSysTypeEnum.APP_ANDROID.getValue());
      appVersion.setType(0); // todo 这个需要参数传进来
      appVersion.setCode(""); // todo 这个需要参数传进来
      appVersion.setIsForce(1);
      appVersion.setUpdateContent(""); // todo 这个需要参数传进来
      appVersion.setUpdateLink(path);
      appVersion.setNewVersionNum(""); // todo 这个需要参数传进来
      appVersion.setPackageSize(String.valueOf(size));
      appVersion.setNewVersionName(""); // todo 这个需要参数传进来
      appVersion.setStatus("new");
      appVersion.setCreatedBy(0L);
      plaAppVersionService.insertOrUpdate(appVersion);
    }
}
