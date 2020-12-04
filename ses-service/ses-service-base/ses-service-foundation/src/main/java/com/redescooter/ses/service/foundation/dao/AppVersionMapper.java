package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.foundation.vo.app.AppVersionDTO;
import com.redescooter.ses.api.foundation.vo.app.QueryAppVersionParamDTO;
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
     * @return com.redescooter.ses.api.foundation.vo.app.AppVersionDTO
     * @author assert
     * @date 2020/11/30
     */
    AppVersionDTO getAppVersionById(Long id);

    /**
     * 查询应用版本列表信息
     * @param paramDTO
     * @return java.util.List<com.redescooter.ses.api.foundation.vo.app.AppVersionDTO>
     * @author assert
     * @date 2020/12/3
    */
    List<AppVersionDTO> queryAppVersion(QueryAppVersionParamDTO paramDTO);

    /**
     * 创建应用版本
     * @param appVersionDTO
     * @return int
     * @author assert
     * @date 2020/12/4
    */
    int insertAppVersion(AppVersionDTO appVersionDTO);

    /**
     * 修改应用版本信息
     * @param appVersionDTO
     * @return int
     * @author assert
     * @date 2020/12/4
    */
    int updateAppVersion(AppVersionDTO appVersionDTO);

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
     * @return java.util.List<com.redescooter.ses.api.foundation.vo.app.AppVersionDTO>
     * @author assert
     * @date 2020/12/4
    */
    List<AppVersionDTO> getActiveAppVersion(Integer status);

    /**
     * 查询应用版本数量
     * @param paramDTO
     * @return int
     * @author assert
     * @date 2020/12/4
    */
    int countByAppVersion(QueryAppVersionParamDTO paramDTO);

}
