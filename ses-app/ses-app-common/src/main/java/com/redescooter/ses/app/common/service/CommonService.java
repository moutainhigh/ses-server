package com.redescooter.ses.app.common.service;

import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.VerificationCodeEnter;

/**
 * @ClassNameverificationCodeService
 * @Description
 * @Author Joan
 * @Date2020/8/12 10:08
 * @Version V1.0
 **/
public interface CommonService {
  BooleanResult checkVerificationCode(VerificationCodeEnter enter);
}
