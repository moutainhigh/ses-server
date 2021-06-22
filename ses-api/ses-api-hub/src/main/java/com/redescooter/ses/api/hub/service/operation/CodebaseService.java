package com.redescooter.ses.api.hub.service.operation;

import com.redescooter.ses.api.hub.vo.operation.OpeSimInformation;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/21 20:21
 */
public interface CodebaseService {

    /**
     * 保存sim卡信息
     */
    void saveSim(OpeSimInformation enter);

}
