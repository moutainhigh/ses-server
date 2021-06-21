package com.redescooter.ses.api.hub.service.operation;

import com.redescooter.ses.api.hub.vo.operation.OpeSimInformation;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/21 20:21
 */
public interface CodebaseService {

    /**
     * 校验rsn在码库是否存在
     */
    boolean checkRsn(String rsn);

    /**
     * 修改码库rsn为已用
     */
    void updateRsn(String rsn, Long userId);

    /**
     * 校验vin在码库是否存在
     */
    boolean checkVin(String vinCode);

    /**
     * 修改码库vin为已用
     */
    void updateVin(String vinCode, Long userId);

    /**
     * 保存sim卡信息
     */
    void saveSim(OpeSimInformation enter);

}
