package com.redescooter.ses.web.ros.service.sellsy;

import com.redescooter.ses.web.ros.vo.sellsy.enter.client.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.client.SellsyClientAddressDetailResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.client.SellsyClientResult;

import java.util.List;

/**
 * @ClassName:SellsyClientService
 * @description: SellsyClientService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/26 14:42
 */
public interface SellsyClientService {
    /**
     * 查询客户列表
     *
     *
     */
    public List<SellsyClientResult> queryClientList(SellsyClientListEnter enter);
    
    /**
     * 客户查询
     *
     * @param enter
     * @return
     */
    public SellsyClientResult queryClientOne(SellsyQueryClientOneEnter enter);

    /**
     * 客户创建
     *
     * @param enter
     */
    public SellsyIdResult createClient(SellsyCreateClientEnter enter);

    /**
     * 删除客户
     * @param enter
     */
    public void deleteClient(SellsyDeleteClientEnter enter);


    /**
     * 获取客户地址详情
     * @param enter
     */
    public SellsyClientAddressDetailResult queryClientAddress(SellsyClientAddressEnter enter);
}
