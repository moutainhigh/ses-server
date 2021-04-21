package com.redescooter.ses.api.hub.service.operation;

import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;

public interface CustomerInquiryService {
    /**
     * 再次支付校验的接口
     * @param enter
     * @return
     */
    BooleanResult payAgainCheck(IdEnter enter);
}
