package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseCombinB;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 11:54
 */
public interface OpeInWhouseCombinBMapper extends BaseMapper<OpeInWhouseCombinB> {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(OpeInWhouseCombinB record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    OpeInWhouseCombinB selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OpeInWhouseCombinB record);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OpeInWhouseCombinB record);
}