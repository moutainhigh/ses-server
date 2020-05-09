package com.redescooter.ses.web.ros.service.production;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.production.ScanBarCodeEnter;

/**
 * @ClassName:RpsServvice
 * @description: RpsServvice
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/25 12:11
 */
public interface RpsServvice {

    // 模拟Rps 进行质检
    GeneralResult scanBarCode(ScanBarCodeEnter enter);
}
