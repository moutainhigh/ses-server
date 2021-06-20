package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeScanCodeRecordMapper;
import com.redescooter.ses.web.ros.dm.OpeScanCodeRecord;
import com.redescooter.ses.web.ros.service.base.OpeScanCodeRecordService;
import com.redescooter.ses.web.ros.vo.app.ScanCodeRecordResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description: // 部件扫码记录表(OpeScanCodeRecord)表服务实现类
 * @Date: 2021-06-04 15:56:54
 * @Author: Charles
 */
@Slf4j
@Service
public class OpeScanCodeRecordServiceImpl extends ServiceImpl<OpeScanCodeRecordMapper, OpeScanCodeRecord> implements OpeScanCodeRecordService {

    @Autowired
    private OpeScanCodeRecordMapper mapper;

    @Override
    public List<ScanCodeRecordResult> scanCodeRecordList(String searchContent) {
        return mapper.scanCodeRecordList(searchContent);
    }
}
