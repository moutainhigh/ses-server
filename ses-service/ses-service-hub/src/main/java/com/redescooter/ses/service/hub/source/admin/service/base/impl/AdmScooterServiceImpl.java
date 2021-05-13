package com.redescooter.ses.service.hub.source.admin.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.api.hub.vo.admin.AdmScooter;
import com.redescooter.ses.service.hub.source.admin.dao.base.AdmScooterMapper;
import com.redescooter.ses.service.hub.source.admin.service.base.AdmScooterService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 车辆管理表 服务实现类
 * </p>
 *
 * @author chris
 * @since 2021-05-13
 */
@Service
@DS("admin")
public class AdmScooterServiceImpl extends ServiceImpl<AdmScooterMapper, AdmScooter> implements AdmScooterService {


}
