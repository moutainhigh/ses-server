package com.redescooter.ses.app.common.service;

/**
 * @ClassNameverificationCodeService
 * @Description
 * @Author Joan
 * @Date2020/8/12 10:08
 * @Version V1.0
 **/
public interface CommonService {
  Boolean checkVerificationCode(String requestId,String code);
}
