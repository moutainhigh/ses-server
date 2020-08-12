package com.redescooter.ses.app.common.service.impl;

import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.VerificationCodeEnter;
import com.redescooter.ses.app.common.service.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;

import java.util.Map;

/**
 * @ClassNameverificationCodeServiceImpl
 * @Description
 * @Author Joan
 * @Date2020/8/12 10:11
 * @Version V1.0
 **/
@Service
public class CommonServiceImpl implements CommonService {
  @Autowired
  private JedisCluster jedisCluster;
  @Override
  public BooleanResult checkVerificationCode(VerificationCodeEnter enter) {
    Map<String, String> map = jedisCluster.hgetAll(enter.getRequestId());
    if (CollectionUtils.isEmpty(map)) {
      return BooleanResult.builder().success(Boolean.FALSE).build();
    }
    return BooleanResult.builder().success(StringUtils.equals(map.get("verificationCode"),enter.getCode())? Boolean.TRUE : Boolean.FALSE).build();
  }
}
