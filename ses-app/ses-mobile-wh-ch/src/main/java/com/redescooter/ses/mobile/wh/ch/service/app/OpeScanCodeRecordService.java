package com.redescooter.ses.mobile.wh.ch.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.wh.ch.dm.OpeScanCodeRecord;
import com.redescooter.ses.mobile.wh.ch.vo.ScanCodeRecordResult;

import java.util.List;

/**
 * @Description: // 部件扫码记录表(OpeScanCodeRecord)表服务接口
 * @Date: 2021-06-04 15:56:54
 * @Author: Charles
 */
public interface OpeScanCodeRecordService extends IService<OpeScanCodeRecord> {

    List<ScanCodeRecordResult> scanCodeRecordList(String searchContent);
}
