package com.redescooter.ses.service.foundation.service.impl;

import com.redescooter.ses.api.common.enums.base.AppVersionStatusEnum;
import com.redescooter.ses.api.common.enums.base.AppVersionTypeEnum;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.common.vo.version.ReleaseAppVersionParamDTO;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.AppVersionService;
import com.redescooter.ses.api.foundation.vo.app.AppVersionDTO;
import com.redescooter.ses.api.foundation.vo.app.InsertAppVersionDTO;
import com.redescooter.ses.api.foundation.vo.app.QueryAppVersionParamDTO;
import com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO;
import com.redescooter.ses.api.hub.service.operation.SysUserService;
import com.redescooter.ses.api.hub.vo.SysUserStaffDTO;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.AppVersionMapper;
import com.redescooter.ses.service.foundation.dao.AppVersionUpdateLogMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaAppVersion;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2020/11/30 11:54
 */
@Slf4j
@DubboService
public class AppVersionServiceImpl implements AppVersionService {

    @DubboReference
    private ScooterEmqXService scooterEmqXService;

    @DubboReference
    private SysUserService sysUserService;

    @DubboReference
    private IdAppService idAppService;

    @Resource
    private AppVersionUpdateLogMapper appVersionUpdateLogMapper;

    @Resource
    private AppVersionMapper appVersionMapper;

    @Override
    public QueryAppVersionResultDTO getAppVersionById(Long id) {
        QueryAppVersionResultDTO appVersionResult = appVersionMapper.getAppVersionById(id);
        if (null != appVersionResult) {
            // ???operation?????????????????????????????????
            SysUserStaffDTO userStaff = sysUserService.getSysUserStaffByUserId(appVersionResult.getCreatedBy());
            if (null != userStaff) {
                appVersionResult.setCreatedName(userStaff.getFullName());
                appVersionResult.setHeadPortrait(userStaff.getEmployeePicture());
            }
        }
        return appVersionResult;
    }

    @Override
    public PageResult<QueryAppVersionResultDTO> queryAppVersion(QueryAppVersionParamDTO paramDTO) {
        PageEnter enter = new PageEnter();
        enter.setPageNo(paramDTO.getPageNo());
        enter.setPageSize(paramDTO.getPageSize());

        if (AppVersionTypeEnum.IOS.getType().equals(paramDTO.getType())) {
            // ????????????-9????????????ios???????????????
            paramDTO.setType(9);
            // ????????????[App]??????????????????
            if (null != paramDTO.getPlatformType()) {
                paramDTO.setType(paramDTO.getPlatformType());
            }
        }

        int count = appVersionMapper.countByAppVersion(paramDTO);

        return PageResult.create(enter, count, appVersionMapper.queryAppVersion(paramDTO));
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int insertAppVersion(InsertAppVersionDTO appVersionDTO) {

        PlaAppVersion appVersion = new PlaAppVersion();
        BeanUtils.copyProperties(appVersionDTO, appVersion);

        /**
         * ???????????????????????????
         */
        String versionNumber = appVersionMapper.existsAppVersionByVersionNumberAndType(appVersionDTO.getNewVersionNum(), appVersionDTO.getType());
        if (StringUtils.isNotBlank(versionNumber)) {
            throw new FoundationException(ExceptionCodeEnums.VERSION_NUMBER_EXISTS.getCode(),
                    ExceptionCodeEnums.VERSION_NUMBER_EXISTS.getMessage());
        }

        String versionCode = null;
        /**
         * ?????????IOS????????????????????????????????????????????????????????????,?????????????????????????????????
         */
        if (AppVersionTypeEnum.ANDROID.getType().equals(appVersionDTO.getType())
                || AppVersionTypeEnum.SCS.getType().equals(appVersionDTO.getType())
                || AppVersionTypeEnum.IOS.getType().equals(appVersionDTO.getType())) {
            // ??????????????????????????????
            String versionCodeData = appVersionMapper.existsAppVersionByCodeAndType(appVersionDTO.getCode(),
                    appVersionDTO.getType());
            if (StringUtils.isNotBlank(versionCodeData)) {
                throw new FoundationException(ExceptionCodeEnums.VERSION_CODE_EXISTS.getCode(),
                        ExceptionCodeEnums.VERSION_CODE_EXISTS.getMessage());
            }
            versionCode = appVersionDTO.getCode();

        } else {
            Integer vCode = 1;

            String maxVersionCode = appVersionMapper.getAppVersionMaxCodeByType(appVersionDTO.getType());
            if (StringUtils.isNotBlank(maxVersionCode)) {
                // ???????????? +1
                vCode = Integer.valueOf(maxVersionCode) + 1;
            }
            versionCode = vCode.toString();
        }

        appVersion.setId(idAppService.getId(SequenceName.PLA_APP_VERSION));
        appVersion.setCode(versionCode);
        appVersion.setIsForce(1);
        appVersion.setStatus(AppVersionStatusEnum.UNRELEASED.getStatus());
        appVersion.setCreatedBy(appVersionDTO.getUserId());
        appVersion.setCreatedTime(new Date());
        appVersion.setUpdatedBy(appVersionDTO.getUserId());
        appVersion.setUpdatedTime(new Date());
        return appVersionMapper.insertAppVersion(appVersion);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int updateAppVersion(InsertAppVersionDTO appVersionDTO) {
        QueryAppVersionResultDTO appVersion = appVersionMapper.getAppVersionById(appVersionDTO.getId());
        if (null == appVersion) {
            throw new FoundationException(ExceptionCodeEnums.VERSION_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.VERSION_IS_NOT_EXIST.getMessage());
        }

        /**
         * ???????????????????????????
         */
        String versionNumber = appVersionMapper.existsAppVersionByVersionNumberAndType(appVersionDTO.getNewVersionNum(), appVersionDTO.getType());
        if (StringUtils.isNotBlank(versionNumber) && !versionNumber.equals(appVersion.getNewVersionNum())) {
            throw new FoundationException(ExceptionCodeEnums.VERSION_NUMBER_EXISTS.getCode(),
                    ExceptionCodeEnums.VERSION_NUMBER_EXISTS.getMessage());
        }

        /**
         * ??????????????????????????????,?????????????????????????????????
         */
        if (AppVersionTypeEnum.ANDROID.getType().equals(appVersionDTO.getType())
                || AppVersionTypeEnum.SCS.getType().equals(appVersionDTO.getType())
                || AppVersionTypeEnum.IOS.getType().equals(appVersionDTO.getType())) {

            String versionCodeData = appVersionMapper.existsAppVersionByCodeAndType(appVersionDTO.getCode(),
                    appVersionDTO.getType());
            if (StringUtils.isNotBlank(versionCodeData) && !versionCodeData.equals(appVersion.getCode())) {
                throw new FoundationException(ExceptionCodeEnums.VERSION_CODE_EXISTS.getCode(),
                        ExceptionCodeEnums.VERSION_CODE_EXISTS.getMessage());
            }
        }

        // ????????????????????????????????????
        if (!AppVersionStatusEnum.UNRELEASED.getStatus().equals(appVersion.getStatus())) {
            throw new FoundationException(ExceptionCodeEnums.VERSION_STATUS_IS_NOT_UNRELEASED.getCode(),
                    ExceptionCodeEnums.VERSION_STATUS_IS_NOT_UNRELEASED.getMessage());
        }

        PlaAppVersion plaAppVersion = new PlaAppVersion();
        BeanUtils.copyProperties(appVersionDTO, plaAppVersion);
        plaAppVersion.setCode(appVersionDTO.getCode());
        plaAppVersion.setUpdatedBy(appVersionDTO.getUserId());
        plaAppVersion.setUpdatedTime(new Date());
        return appVersionMapper.updateAppVersion(plaAppVersion);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public int releaseAppVersion(ReleaseAppVersionParamDTO paramDTO) {
        QueryAppVersionResultDTO appVersion = appVersionMapper.getAppVersionById(paramDTO.getId());
        if (null == appVersion) {
            // ????????????????????????????????????????????????(???????????????????????????-Controller??????messages.properties?????????)
            throw new FoundationException(ExceptionCodeEnums.VERSION_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.VERSION_IS_NOT_EXIST.getMessage());
        }

        // ????????????????????????????????????
        if (!AppVersionStatusEnum.UNRELEASED.getStatus().equals(appVersion.getStatus())) {
            throw new FoundationException(ExceptionCodeEnums.VERSION_STATUS_IS_NOT_UNRELEASED.getCode(),
                    ExceptionCodeEnums.VERSION_STATUS_IS_NOT_UNRELEASED.getMessage());
        }

        /**
         * ??????????????????SCS(????????????)????????????emq??????????????????????????????????????????????????????????????? ???????????????
         * ???????????? -- ??????????????????????????????????????????
         */
        try {
            if (AppVersionTypeEnum.SCS.getType().equals(paramDTO.getType())) {
                scooterEmqXService.updateScooterTablet(paramDTO);
                // SCS(????????????)???????????? ??????????????? ?????????, ????????????????????????????????????????????????????????? ???????????????
                if (4 == paramDTO.getReleaseType()) {
                    appVersionMapper.updateAppVersionStatusByType(paramDTO.getType(),
                            AppVersionStatusEnum.RELEASED.getStatus(), paramDTO.getId());
                }
            } else {
                // ????????????????????????????????????????????????????????? ???????????????
                appVersionMapper.updateAppVersionStatusByType(paramDTO.getType(),
                        AppVersionStatusEnum.RELEASED.getStatus(), paramDTO.getId());
            }
            appVersionMapper.updateAppVersionStatusById(paramDTO.getId(), AppVersionStatusEnum.ACTIVE.getStatus());
        } catch (Exception e) {
            log.error("????????????????????????----{}", ExceptionUtils.getStackTrace(e));
        }
        return 1;
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int deleteAppVersionById(Long id) {
        QueryAppVersionResultDTO appVersion = appVersionMapper.getAppVersionById(id);
        // ????????????????????????????????????
        if (!AppVersionStatusEnum.UNRELEASED.getStatus().equals(appVersion.getStatus())) {
            throw new FoundationException(ExceptionCodeEnums.VERSION_STATUS_IS_NOT_UNRELEASED.getCode(),
                    ExceptionCodeEnums.VERSION_STATUS_IS_NOT_UNRELEASED.getMessage());
        }

        return appVersionMapper.deleteAppVersionById(id);
    }

    @Override
    public Map<String, Integer> getAppVersionTypeCount() {
        Map<String, Integer> result = new HashMap<>(7);
        /**
         * ???????????????????????????????????????
         */
        List<AppVersionDTO> appVersionList = appVersionMapper.getAppVersions();

        Map<String, List<AppVersionDTO>> appVersionMap = getAppVersionByType(appVersionList);
        for (Map.Entry<String, List<AppVersionDTO>> version : appVersionMap.entrySet()) {
            result.put(version.getKey(), version.getValue().size());
        }

        return result;
    }

    @Override
    public List<QueryAppVersionResultDTO> getAllActiveAppVersion() {
        return appVersionMapper.getActiveAppVersion(AppVersionStatusEnum.ACTIVE.getStatus());
    }

    @Override
    public List<SelectBaseResultDTO> getAppVersionByType(Integer type) {
        return appVersionMapper.getAppVersionByType(type);
    }

    @Override
    public List<String> getAppVersionLabelByLabel(String label) {
        return appVersionMapper.getAppVersionLabelByLabel(label);
    }


    /**
     * ???????????????????????????????????????
     *
     * @param appVersionList
     * @return
     */
    private Map<String, List<AppVersionDTO>> getAppVersionByType(List<AppVersionDTO> appVersionList) {
        // set map initialCapacity
        int initialCapacity = (int) (5 / 0.75) + 1;
        Map<String, List<AppVersionDTO>> result = new HashMap<>(initialCapacity);
        /**
         * ????????????????????????????????????????????????(SCS???????????????????????????????????????)
         */
        // APP(ios,??????)
        List<AppVersionDTO> app = appVersionList.stream().filter(
                v -> AppVersionTypeEnum.IOS.getType().equals(v.getType()) || AppVersionTypeEnum.ANDROID.getType().equals(v.getType())
        ).collect(Collectors.toList());

        // scs(????????????)
        List<AppVersionDTO> scs = appVersionList.stream().filter(
                v -> AppVersionTypeEnum.SCS.getType().equals(v.getType())
        ).collect(Collectors.toList());

        // SaaS
        List<AppVersionDTO> saaS = appVersionList.stream().filter(
                v -> AppVersionTypeEnum.SaaS.getType().equals(v.getType())
        ).collect(Collectors.toList());

        // server(????????????)
        List<AppVersionDTO> server = appVersionList.stream().filter(
                v -> AppVersionTypeEnum.SERVER.getType().equals(v.getType())
        ).collect(Collectors.toList());

        // ros
        List<AppVersionDTO> ros = appVersionList.stream().filter(
                v -> AppVersionTypeEnum.ROS.getType().equals(v.getType())
        ).collect(Collectors.toList());

        // put k,v
        result.put("APP", app);
        result.put(AppVersionTypeEnum.SCS.getCode(), scs);
        result.put(AppVersionTypeEnum.SaaS.getCode(), saaS);
        result.put(AppVersionTypeEnum.SERVER.getCode(), server);
        result.put(AppVersionTypeEnum.ROS.getCode(), ros);

        return result;
    }

}
