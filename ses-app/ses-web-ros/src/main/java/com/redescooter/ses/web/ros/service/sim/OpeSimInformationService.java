package com.redescooter.ses.web.ros.service.sim;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSimInformation;

/**
 * sim卡信息(OpeSimInformation)表服务实现类
 *
 * @author Charles
 * @since 2021-05-26 20:58:05
 */
public interface OpeSimInformationService extends IService<OpeSimInformation> {

    void getCurrentBalance();

}
