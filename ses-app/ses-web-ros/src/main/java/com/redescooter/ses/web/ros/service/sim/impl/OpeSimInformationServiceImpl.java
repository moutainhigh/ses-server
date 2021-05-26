package com.redescooter.ses.web.ros.service.sim.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.sim.OpeSimInformationMapper;
import com.redescooter.ses.web.ros.dm.OpeSimInformation;
import com.redescooter.ses.web.ros.service.sim.OpeSimInformationService;
import com.redescooter.ses.web.ros.utils.HmacUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * sim卡信息(OpeSimInformation)表服务实现类
 *
 * @author Charles
 * @since 2021-05-26 20:58:05
 */
@Slf4j
@Service
public class OpeSimInformationServiceImpl extends ServiceImpl<OpeSimInformationMapper, OpeSimInformation> implements OpeSimInformationService {

    @Override
    public void getCurrentBalance() {
        try {
            Map<String, String> map = HmacUtil.generateKey();
            if (null == map) {

            }
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(HmacUtil.API_LINK + "sim_card_filter_data")
                    .method("GET", null)
                    .addHeader(HmacUtil.SIM_TIMESTAMP, map.get(HmacUtil.SIM_TIMESTAMP))
                    .addHeader(HmacUtil.SIM_HASH, map.get(HmacUtil.SIM_HASH))
                    .addHeader(HmacUtil.SIM_UUID, HmacUtil.USER_UUID)
                    .build();
            Response response = client.newCall(request).execute();
        } catch (Exception e) {

        }
    }
}
