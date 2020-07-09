package com.redescooter.ses.web.ros.service.monday.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.config.MondayConfig;
import com.redescooter.ses.web.ros.constant.MondayQueryConstant;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.vo.monday.MondayGeneralResult;
import lombok.extern.log4j.Log4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName:MondayServiceImpl
 * @description: MondayServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/09 16:46
 */
@Service
@Log4j
public class MondayServiceImpl implements MondayService {

    @Autowired
    private MondayConfig mondayConfig;

    @Override
    public GeneralResult getMondayData(GeneralEnter enter) {
        MondayGeneralResult mondayData = getMondayData(MondayQueryConstant.testQuery, HttpMethod.POST);
        System.out.println(mondayData.getData());
        return null;
    }

    private MondayGeneralResult getMondayData(String querySdl, HttpMethod method){
        //定义restTemplate 模板
        RestTemplate restTemplate = new RestTemplate();
        //定义 httpHeaders 请求
        HttpHeaders httpHeaders = new HttpHeaders();
        // 以表单的方式提交
        httpHeaders.setContentType(mondayConfig.getMediaType());
        httpHeaders.add("authorization", mondayConfig.getAuthorization());

        // 请求体提交参数
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(mondayConfig.getParamQuery(), querySdl);
        map.add(mondayConfig.getParamVariables(), null);

        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, httpHeaders);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<String> response = restTemplate.exchange(mondayConfig.getUrl(), method, requestEntity, String.class);
        MondayGeneralResult mondayGeneralResult = JSON.parseObject(response.getBody(), MondayGeneralResult.class);
        return mondayGeneralResult;
    }
}
