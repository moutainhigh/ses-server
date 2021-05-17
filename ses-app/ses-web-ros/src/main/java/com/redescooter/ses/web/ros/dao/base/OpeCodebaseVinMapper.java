package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeCodebaseVin;
import com.redescooter.ses.web.ros.vo.codebase.ExportVINResult;
import com.redescooter.ses.web.ros.vo.codebase.VINListEnter;
import com.redescooter.ses.web.ros.vo.codebase.VINListResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 码库vin表 Mapper 接口
 * </p>
 *
 * @author chris
 * @since 2021-05-17
 */
@Mapper
public interface OpeCodebaseVinMapper extends BaseMapper<OpeCodebaseVin> {

    /**
     * vin列表count
     */
    int getVinListCount(@Param("enter") VINListEnter enter);

    /**
     * vin列表
     */
    List<VINListResult> getVinList(@Param("enter") VINListEnter enter);

    /**
     * vin导出
     */
    List<ExportVINResult> exportVin(@Param("enter") VINListEnter enter);

}