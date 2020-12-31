package com.redescooter.ses.api.foundation.service;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.common.vo.version.ReleaseAppVersionParamDTO;
import com.redescooter.ses.api.foundation.vo.app.InsertAppVersionDTO;
import com.redescooter.ses.api.foundation.vo.app.QueryAppVersionParamDTO;
import com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO;

import java.util.List;
import java.util.Map;


/**
 * 应用版本管理业务接口
 * @author assert
 * @date 2020/11/30 11:53
 */
public interface AppVersionService {

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
     * @return com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO>
     * @author assert
     * @date 2020/12/9
    */
    PageResult<QueryAppVersionResultDTO> queryAppVersion(QueryAppVersionParamDTO paramDTO);

    /**
     * 创建应用版本
     * @param appVersionDTO
     * @return int
     * @author assert
     * @date 2020/12/3
    */
    int insertAppVersion(InsertAppVersionDTO appVersionDTO);

    /**
     * 修改应用版本信息
     * @param appVersionDTO
     * @return int
     * @author assert
     * @date 2020/12/4
    */
    int updateAppVersion(InsertAppVersionDTO appVersionDTO);

    /**
     * 发布版本
     * @param paramDTO
     * @return int
     * @author assert
     * @date 2020/12/3
    */
    int releaseAppVersion(ReleaseAppVersionParamDTO paramDTO);

    /**
     * 根据id删除版本信息
     * @param id
     * @return int
     * @author assert
     * @date 2020/12/3
    */
    int deleteAppVersionById(Long id);

    /**
     * 获取所有应用版本类型数量
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     * @author assert
     * @date 2020/12/3
    */
    Map<String, Integer> getAppVersionTypeCount();

    /**
     * 查询所有应用正在使用的版本
     * @param
     * @return java.util.List<com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO>
     * @author assert
     * @date 2020/12/8
    */
    List<QueryAppVersionResultDTO> getAllActiveAppVersion();

    /**
     * 根据type查询当前应用所有版本号信息
     * @param type
     * @return java.util.List<java.lang.String>
     * @author assert
     * @date 2020/12/7
    */
    List<SelectBaseResultDTO> getAppVersionByType(Integer type);

    /**
     * 根据标签全模糊检索应用标签信息
     * @param label
     * @return java.util.List<java.lang.String>
     * @author assert
     * @date 2020/12/8
    */
    List<String> getAppVersionLabelByLabel(String label);

}
