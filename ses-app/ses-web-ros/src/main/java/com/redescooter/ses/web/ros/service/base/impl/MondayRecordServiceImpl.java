package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.MondayRecordMapper;
import com.redescooter.ses.web.ros.dm.MondayRecord;
import com.redescooter.ses.web.ros.service.base.MondayRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * monday记录表(MondayRecord)表服务实现类
 *
 * @author Charles
 * @since 2021-06-23 12:50:19
 */
@Slf4j
@Service
public class MondayRecordServiceImpl extends ServiceImpl<MondayRecordMapper, MondayRecord> implements MondayRecordService {

}
