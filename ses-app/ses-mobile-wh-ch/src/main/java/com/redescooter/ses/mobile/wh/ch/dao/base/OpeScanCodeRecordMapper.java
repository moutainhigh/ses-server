package com.redescooter.ses.mobile.wh.ch.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.wh.ch.dm.OpeScanCodeRecord;
import com.redescooter.ses.mobile.wh.ch.vo.ScanCodeRecordResult;
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
