package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.base.AppVersionStatusEnum;
import com.redescooter.ses.api.common.enums.base.AppVersionTypeEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.service.VersionBaseService;
import com.redescooter.ses.api.foundation.vo.app.VersionTypeEnter;
import com.redescooter.ses.api.foundation.vo.app.VersionTypeResult;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dm.base.PlaAppVersion;
import com.redescooter.ses.service.foundation.service.base.PlaAppVersionService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.file.FileUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassNameVersionBaseServiceImpl
 * @Description
 * @Author Joan
 * @Date2020/6/17 14:41
 * @Version V1.0
 **/
@Slf4j
@DubboService
public class VersionBaseServiceImpl implements VersionBaseService {

    @Autowired
    private PlaAppVersionService plaAppVersionService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DubboReference
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
        if (null != one) {
            BeanUtils.copyProperties(one, ersionTypeResult);
        }
        return ersionTypeResult;
    }

    @SneakyThrows
    @Override
    public void fileUpload(MultipartFile file, String fileMsg) {
        // 1??????????????????????????????,?????????????????????
        String path = FileUtil.uploadFile(file, uploadPath);
        // 2???????????????????????? (???????????????M)
        long size = file.getSize() / 1024 / 1024;
        // 3???????????????????????????APP???????????????????????????????????????????????????APP???????????????
        PlaAppVersion appVersion = new PlaAppVersion();
        appVersion.setId(idAppService.getId(SequenceName.PLA_APP_VERSION));
        appVersion.setSystemId("");
        appVersion.setAppId("");
        appVersion.setSystemType(AppVersionTypeEnum.ANDROID.getType());
        appVersion.setType(0); // todo ???????????????????????????
        appVersion.setCode(""); // todo ???????????????????????????
        appVersion.setIsForce(1);
        appVersion.setUpdateContent(""); // todo ???????????????????????????
        appVersion.setUpdateLink(path);
        appVersion.setNewVersionNum(""); // todo ???????????????????????????
        appVersion.setPackageSize(String.valueOf(size));
        appVersion.setNewVersionName(""); // todo ???????????????????????????
        appVersion.setStatus(AppVersionStatusEnum.UNRELEASED.getStatus());
        appVersion.setCreatedBy(0L);
        plaAppVersionService.insertOrUpdate(appVersion);
    }

    /**
     * ????????????????????????
     *
     * @param versionTypeEnter
     * @return
     * @author joan
     */
    @Override
    public VersionTypeResult getAppNewVersionChData(VersionTypeEnter versionTypeEnter) {
        VersionTypeResult ersionTypeResult = new VersionTypeResult();
        QueryWrapper<PlaAppVersion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(PlaAppVersion.COL_TYPE, versionTypeEnter.getType());
        queryWrapper.eq(PlaAppVersion.COL_DEF1, Constant.CHINA);
        queryWrapper.gt(PlaAppVersion.COL_CODE, Integer.valueOf(versionTypeEnter.getCode()));
        queryWrapper.orderByDesc(PlaAppVersion.COL_CREATED_TIME);
        queryWrapper.last("limit 1");
        PlaAppVersion one = plaAppVersionService.getOne(queryWrapper);
        if (null != one) {
            BeanUtils.copyProperties(one, ersionTypeResult);
        }
        return ersionTypeResult;
    }

    /**
     * ?????????????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult testGlobalTransactional(GeneralEnter enter) {
        jdbcTemplate.update("update t_good set amount = amount - 1 where id = 1");
        int i = 1 / 0;
        return new GeneralResult();
    }

}
