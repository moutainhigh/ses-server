package com.redescooter.ses.app.common.service.impl;

import com.redescooter.ses.api.common.exception.BaseException;
import com.redescooter.ses.app.common.service.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import java.util.Map;

/**
 * @ClassNameverificationCodeServiceImpl
 * @Description
 * @Author Joan
 * @Date2020/8/12 10:11
 * @Version V1.0
 **/
public class CommonServiceImpl implements CommonService {
  @Autowired
  private JedisCluster jedisCluster;
  @Override
  public Boolean checkVerificationCode(String requestId,String code) {
    Map<String, String> map = jedisCluster.hgetAll(requestId);
    if (map == null) {
      throw new BaseException(BaseException.DEFAULE_ERRORCODE, "upload file failure.");
    }
    return StringUtils.equals(map.get("verificationCode"),code)? Boolean.TRUE : Boolean.FALSE;
  }
}
