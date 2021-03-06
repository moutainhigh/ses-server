package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.foundation.vo.app.AppVersionDTO;
import com.redescooter.ses.api.foundation.vo.app.QueryAppVersionParamDTO;
import com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO;
import com.redescooter.ses.service.foundation.dm.base.PlaAppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 应用版本管理 Mapper接口
 * @author assert
 * @date 2020/11/30 11:56
 */
public interface AppVersionMapper {

    /**
     * 根据id查询新应用版本信息
     * @param id
     * @return com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO
     * @author assert
     * @date 2020/11/30
     */
    QueryAppVersionResultDTO getAppVersionById(Long id);

    /**
     * 查询应用版本列表信息
     * @param paramDTO
     * @return java.util.List<com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO>
     * @author assert
     * @date 2020/12/3
    */
    List<QueryAppVersionResultDTO> queryAppVersion(QueryAppVersionParamDTO paramDTO);

    /**
     * 创建应用版本
     * @param appVersionDTO
     * @return int
     * @author assert
     * @date 2020/12/4
    */
    int insertAppVersion(PlaAppVersion appVersionDTO);

    /**
     * 修改应用版本信息
     * @param appVersionDTO
     * @return int
     * @author assert
     * @date 2020/12/4
    */
    int updateAppVersion(PlaAppVersion appVersionDTO);

    /**
     * 根据id删除版本信息
     * @param id
     * @return int
     * @author assert
     * @date 2020/12/4
    */
    int deleteAppVersionById(Long id);

    /**
     * 根据id修改应用版本状态
     * @param id
     * @param status
     * @return int
     * @author assert
     * @date 2020/12/4
    */
    int updateAppVersionStatusById(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 根据type修改应用版本状态为 “已发布”
     * @param type
     * @param status
     * @param id
     * @return int
     * @author assert
     * @date 2020/12/4
    */
    int updateAppVersionStatusByType(@Param("type") Integer type, @Param("status") Integer status, @Param("id") Long id);

    /**
     * 查询所有应用正在使用的版本
     * @param status
     * @return java.util.List<com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO>
     * @author assert
     * @date 2020/12/4
    */
    List<QueryAppVersionResultDTO> getActiveAppVersion(Integer status);

    /**
     * 查询应用版本数量
     * @param paramDTO
     * @return int
     * @author assert
     * @date 2020/12/4
    */
    int countByAppVersion(QueryAppVersionParamDTO paramDTO);

    /**
     * 查询全部应用版本信息(只返回type、id字段)
     * @param
     * @return java.util.List<com.redescooter.ses.api.foundation.vo.app.AppVersionDTO>
     * @author assert
     * @date 2020/12/7
    */
    List<AppVersionDTO> getAppVersions();

    /**
     * 根据type查询当前应用版本号信息
     * @param type
     * @return java.util.List<java.lang.String>
     * @author assert
     * @date 2020/12/7
    */
    List<SelectBaseResultDTO> getAppVersionByType(@Param("type") Integer type);

    /**
     * 根据type查询应用版本最大版本编码
     * @param type
     * @return java.lang.String
     * @author assert
     * @date 2020/12/8
    */
    String getAppVersionMaxCodeByType(Integer type);

    /**
     * 根据标签全模糊检索应用标签信息
     * @param label
     * @return java.util.List<java.lang.String>
     * @author assert
     * @date 2020/12/8
    */
    List<String> getAppVersionLabelByLabel(String label);

    /**
     * 根据versionCode、type查询版本是否存在
     * @param versionCode
     * @param type
     * @return com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO
     * @author assert
     * @date 2020/12/14
    */
    String existsAppVersionByCodeAndType(@Param("versionCode") String versionCode, @Param("type") Integer type);

    /**
     * 根据versionNumber、type查询版本是否存在
     * @param versionNumber
     * @param type
     * @return java.lang.String
     * @author assert
     * @date 2020/12/17
    */
    String existsAppVersionByVersionNumberAndType(@Param("versionNumber") String versionNumber, @Param("type") Integer type);

}
