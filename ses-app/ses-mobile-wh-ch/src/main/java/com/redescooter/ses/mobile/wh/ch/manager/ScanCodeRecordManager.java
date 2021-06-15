package com.redescooter.ses.mobile.wh.ch.manager;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.wh.ch.vo.ScanCodeRecordEnter;
import com.redescooter.ses.mobile.wh.ch.vo.ScanCodeRecordReqEnter;
import com.redescooter.ses.mobile.wh.ch.vo.ScanCodeRecordResult;

/**
 * @ClassName ScanManager
 * @Description 部件扫码记录 业务逻辑
 * @Author Charles
 * @Date 2021/06/04 16:12
 */
public interface ScanCodeRecordManager {

    /**
     * @Title: insertScanCodeRecode
     * @Description: // 部件信息录入/修改时加id
     * @Param: [enter]
     * @Return: boolean
     * @Date: 2021/6/4 4:42 下午
     * @Author: Charles
     */
    boolean insertScanCodeRecode(ScanCodeRecordEnter enter);

    /**
     * @Title: checkRsn
     * @Description: // 验证rsn码
     * @Param: [enter]
     * @Return: boolean
     * @Date: 2021/6/4 5:09 下午
     * @Author: Charles
     */
    boolean checkRsn(ScanCodeRecordReqEnter enter);

    /**
     * @Title: scanCodeRecordList
     * @Description: // 部件信息列表
     * @Param: [enter]
     * @Return: com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.web.ros.vo.app.ScanCodeRecordResult>
     * @Date: 2021/6/7 12:00 下午
     * @Author: Charles
     */
    PageResult<ScanCodeRecordResult> scanCodeRecordList(ScanCodeRecordReqEnter enter);

    /**
     * @Title: scanCodeRecordDetail
     * @Description: // 根据id查询部件信息详情
     * @Param: [enter]
     * @Return: com.redescooter.ses.web.ros.vo.app.ScanCodeRecordEnter
     * @Date: 2021/6/7 12:00 下午
     * @Author: Charles
     */
    ScanCodeRecordEnter scanCodeRecordDetail(ScanCodeRecordReqEnter enter);

    /**
     * @Title: checkScanCodeRecode
     * @Description: // 校验录入信息
     * @Param: [enter]
     * @Return: boolean
     * @Date: 2021/6/7 12:00 下午
     * @Author: Charles
     */
    boolean checkScanCodeRecode(ScanCodeRecordEnter enter);
}
