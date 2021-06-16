package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeCodebaseRsn;
import com.redescooter.ses.web.ros.vo.codebase.ExportRSNResult;
import com.redescooter.ses.web.ros.vo.codebase.RSNListEnter;
import com.redescooter.ses.web.ros.vo.codebase.RSNListResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 码库rsn表 Mapper 接口
 * </p>
 *
 * @author chris
 * @since 2021-05-17
 */
@Mapper
public interface OpeCodebaseRsnMapper extends BaseMapper<OpeCodebaseRsn> {

    /**
     * rsn列表count
     */
    int getRsnListCount(@Param("enter") RSNListEnter enter);

    /**
     * rsn列表
     */
    List<RSNListResult> getRsnList(@Param("enter") RSNListEnter enter);

    /**
     * 导出rsn
     */
    List<ExportRSNResult> exportRsn(@Param("enter") RSNListEnter enter);

}