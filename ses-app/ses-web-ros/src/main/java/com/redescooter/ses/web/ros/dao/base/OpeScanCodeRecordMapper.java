package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeScanCodeRecord;
import com.redescooter.ses.web.ros.vo.app.ScanCodeRecordResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: // 部件扫码记录表(OpeScanCodeRecord)表数据库访问层
 * @Date: 2021-06-04 15:56:54
 * @Author: Charles
 */
public interface OpeScanCodeRecordMapper extends BaseMapper<OpeScanCodeRecord> {

    List<ScanCodeRecordResult> scanCodeRecordList(@Param("searchContent") String searchContent);
}
