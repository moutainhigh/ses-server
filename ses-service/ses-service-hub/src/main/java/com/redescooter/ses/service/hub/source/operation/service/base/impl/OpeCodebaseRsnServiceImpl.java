package com.redescooter.ses.service.hub.source.operation.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.operation.dao.base.OpeCodebaseRsnMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCodebaseRsn;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeCodebaseRsnService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 码库rsn表 服务实现类
 * </p>
 *
 * @author chris
 * @since 2021-06-21
 */
@Service
@DS("operation")
public class OpeCodebaseRsnServiceImpl extends ServiceImpl<OpeCodebaseRsnMapper, OpeCodebaseRsn> implements OpeCodebaseRsnService {


}
