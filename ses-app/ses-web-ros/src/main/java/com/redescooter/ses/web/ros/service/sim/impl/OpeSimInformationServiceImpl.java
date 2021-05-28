package com.redescooter.ses.web.ros.service.sim.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.web.ros.dao.sim.OpeSimInformationMapper;
import com.redescooter.ses.web.ros.dm.OpeSimInformation;
import com.redescooter.ses.web.ros.service.sim.OpeSimInformationService;
import com.redescooter.ses.web.ros.utils.HmacUtil;
import com.redescooter.ses.web.ros.vo.sim.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
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

    private String status = "?status_id=";

    /**
     * @Title: getSimInformation
     * @Description: // 获取基本信息
     * @Param: []
     * @Return: com.redescooter.ses.web.ros.vo.sim.SimEssentialInformation
     * @Date: 2021/5/27 1:39 下午
     * @Author: Charles
     */
    public SimEssentialInformation getSimInformation() {
        String simInformation = sendGetSimRequest(getReqMethod(null, null, null, null));
        if (StringUtils.isBlank(simInformation)) {
            return null;
        }
        SimEssentialInformation simEssentialInformation = JSONObject.parseObject(simInformation, SimEssentialInformation.class);
        return simEssentialInformation;
    }

    /**
     * @Title: getSimTransactionRecords
     * @Description: // 获取交易记录
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimDataResult>
     * @Date: 2021/5/28 8:58 上午
     * @Author: Charles
     */
    @Override
    public SimDataResult getSimTransactionRecords(SimEnter simEnter) {
        String body = sendGetSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_BALANCE_PAYMENTS, null, null, getPage(simEnter)));
        if (StringUtils.isBlank(body)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String data = jsonObject.getString("data");
        int total = jsonObject.getInteger("total");
        List<SimTransactionRecordsResult> list = JSONArray.parseArray(data, SimTransactionRecordsResult.class);
        return new SimDataResult(list, total);
    }

    /**
     * @Title: getSimCardList
     * @Description: // Sim卡 列表信息
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimDataResult>
     * @Date: 2021/5/28 8:58 上午
     * @Author: Charles
     */
    @Override
    public SimDataResult getSimCardList(SimEnter simEnter) {
        String body = sendGetSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_IOT_SIM_CARDS, null, null, getPage(simEnter)));
        if (StringUtils.isBlank(body)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String data = jsonObject.getString("data");
        int total = jsonObject.getInteger("total");
        List<SimCardListResult> list = JSONArray.parseArray(data, SimCardListResult.class);
        return new SimDataResult(list, total);
    }

    /**
     * @Title: activationSim
     * @Description: // Sim卡 激活
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response
     * @Date: 2021/5/28 8:59 上午
     * @Author: Charles
     */
    @Override
    public boolean activationSim(SimEnter simEnter) {
        SimEssentialInformation simEssentialInformation = getSimInformation();
        if (null == simEssentialInformation) {
            return false;
        }
        // 余额不足要加提示
        // 账户余额不足，请充值后再来开启
        if (simEssentialInformation.is_low_balance()) {
            return false;
        }
        String body = sendPostSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_SIM_CARD, simEnter.getIccid(), SimInterfaceMethod.SIM_METHOD_ACTIVATION_READY, null));
        if (StringUtils.isBlank(body)) {
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String ack = jsonObject.getString("ack");
        if ("success".equals(ack)) {
            return true;
        }
        return false;
    }

    /**
     * @Title: deactivatedSim
     * @Description: // Sim卡 停用
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response
     * @Date: 2021/5/28 9:00 上午
     * @Author: Charles
     */
    @Override
    public boolean deactivatedSim(SimEnter simEnter) {
        String body = sendPostSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_SIM_CARD, simEnter.getIccid(), SimInterfaceMethod.SIM_METHOD_DEACTIVATE, null));
        if (StringUtils.isBlank(body)) {
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String ack = jsonObject.getString("ack");
        if ("success".equals(ack)) {
            return true;
        }
        return false;
    }

    /**
     * @Title: getSimEssentialInformation
     * @Description: // 获取sim基本信息
     * @Param: []
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimResult>
     * @Date: 2021/5/28 9:40 上午
     * @Author: Charles
     */
    @Override
    public SimResult getSimEssentialInformation() {
        SimEssentialInformation simInformation = getSimInformation();
        if (null == simInformation) {
            return null;
        }
        // 已激活:1-Activated, 激活就绪:4-Activation Ready, 停用:2-Deactivated, 暂停:3-Suspended
        int activatedTotal = getTotalByStatus(status + 1);
        int activatedReadyTotal = getTotalByStatus(status + 4);
        int deactivatedTotal = getTotalByStatus(status + 2);
        int suspendedTotal = getTotalByStatus(status + 3);

        int total = activatedTotal + activatedReadyTotal + deactivatedTotal + suspendedTotal;

        SimResult simResult = new SimResult();
        simResult.setCurrent_balance(simInformation.getCurrent_balance());
        simResult.setSimCount(total);
        simResult.setActivatedCount(activatedTotal);
        simResult.setActivationReadyCount(activatedReadyTotal);
        simResult.setDeactivatedCount(deactivatedTotal);
        simResult.setSuspended(suspendedTotal);
        return simResult;
    }

    /**
     * @Title: getTotalByStatus
     * @Description: // 查询各状态的总数据量
     * @Param: [status]
     * @Return: int
     * @Date: 2021/5/28 9:23 上午
     * @Author: Charles
     */
    private int getTotalByStatus(String status) {
        String body = sendGetSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_IOT_SIM_CARDS, null, status, null));
        if (StringUtils.isBlank(body)) {
            return 0;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        int total = jsonObject.getInteger("total");
        return total;
    }

    /**
     * @Title: getSimDetails
     * @Description: // Sim卡 详情
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimBaseCodeResult>
     * @Date: 2021/5/28 8:59 上午
     * @Author: Charles
     */
    @Override
    public SimBaseCodeResult getSimDetails(SimEnter simEnter) {
        QueryWrapper<OpeSimInformation> qw = new QueryWrapper<OpeSimInformation>();
        qw.eq(OpeSimInformation.COL_DR, Constant.DR_FALSE);
        if (StringUtils.isNotBlank(simEnter.getIccid())) {
            qw.eq(OpeSimInformation.COL_SIM_ICCID, simEnter.getIccid());
        }
        if (StringUtils.isNotBlank(simEnter.getTabledSn())) {
            qw.eq(OpeSimInformation.COL_TABLET_SN, simEnter.getTabledSn());
        }
        OpeSimInformation opeSimInformation = this.getOne(qw);
        if (null == opeSimInformation) {
            return null;
        }
        String body = sendGetSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_IOT_SIM_CARDS, "?" + HmacUtil.COMMON_FILTER + "=" + simEnter.getIccid(), null, null));
        if (StringUtils.isBlank(body)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String data = jsonObject.getString("data");
        SimBaseCodeResult simBaseCodeResult = JSONObject.parseObject(data, SimBaseCodeResult.class);
        simBaseCodeResult.setRsn(opeSimInformation == null ? "" : opeSimInformation.getRsn());
        simBaseCodeResult.setVin(opeSimInformation == null ? "" : opeSimInformation.getVin());
        simBaseCodeResult.setMacAddress(opeSimInformation == null ? "" : opeSimInformation.getBluetoothMacAddress());
        return simBaseCodeResult;
    }

    /**
     * @Title: getSimConnectRecord
     * @Description: // 获取连接记录
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimDataResult>
     * @Date: 2021/5/28 8:58 上午
     * @Author: Charles
     */
    @Override
    public SimDataResult getSimConnectRecord(SimEnter simEnter) {
        String body = sendGetSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_SIM_CARD, simEnter.getIccid(), SimInterfaceMethod.SIM_METHOD_SESSIONS, getPage(simEnter)));
        if (StringUtils.isBlank(body)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String data = jsonObject.getString("data");
        int total = jsonObject.getInteger("total");
        List<SimCardSessionsResult> list = JSONArray.parseArray(data, SimCardSessionsResult.class);
        return new SimDataResult(list, total);
    }

    /**
     * @Title: getSimDailyStatistics
     * @Description: // Sim卡 日统计
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<java.util.List < com.redescooter.ses.web.ros.vo.sim.SimDailyStatisticsResult>>
     * @Date: 2021/5/28 8:59 上午
     * @Author: Charles
     */
    @Override
    public List<SimDailyStatisticsResult> getSimDailyStatistics(SimEnter simEnter) {
        String body = sendGetSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_SIM_CARD, simEnter.getIccid(), SimInterfaceMethod.SIM_METHOD_DAILY_STATISTICS, null));
        if (StringUtils.isBlank(body)) {
            return null;
        }
        List<SimDailyStatisticsResult> list = JSONArray.parseArray(body, SimDailyStatisticsResult.class);
        list.stream().forEach((SimDailyStatisticsResult result) -> {
            result.setUsageMb(kbToMb(result.getUsage_kb()));
        });
        return list;
    }

    private String getPage(SimEnter simEnter) {
        return "?page=" + simEnter.getPageNo() + "&per_page=" + simEnter.getPageSize();
    }

    private String getReqMethod(String url1, String iccid, String url2, String page) {
        return (url1 == null ? "" : url1) + (iccid == null ? "" : iccid) + (url2 == null ? "" : url2) + (page == null ? "" : page);
    }

    private String sendGetSimRequest(String reqMethod) {
        try {
            Map<String, String> map = HmacUtil.generateKey();
            if (null == map) {
                return null;
            }
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url(HmacUtil.API_LINK + reqMethod)
                    .method("GET", null)
                    .addHeader(HmacUtil.SIM_TIMESTAMP, map.get(HmacUtil.SIM_TIMESTAMP))
                    .addHeader(HmacUtil.SIM_HASH, map.get(HmacUtil.SIM_HASH))
                    .addHeader(HmacUtil.SIM_UUID, HmacUtil.USER_UUID)
                    .build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            String body = response.body().string();
            log.info("sim 相应结果 {}", body);
            return body;
        } catch (Exception e) {
            log.error("sim {}接口请求异常", reqMethod);
        }
        return null;
    }


    private String sendPostSimRequest(String reqMethod) {
        try {
            Map<String, String> map = HmacUtil.generateKey();
            if (null == map) {
                return null;
            }
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            MediaType json = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(json, "{}");
            Request request = new Request.Builder()
                    .url(HmacUtil.API_LINK + reqMethod)
                    .method("POST", body)
                    .addHeader(HmacUtil.SIM_TIMESTAMP, map.get(HmacUtil.SIM_TIMESTAMP))
                    .addHeader(HmacUtil.SIM_HASH, map.get(HmacUtil.SIM_HASH))
                    .addHeader(HmacUtil.SIM_UUID, HmacUtil.USER_UUID)
                    .build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            String bodyJson = response.body().string();
            log.info("sim 相应结果 {}", bodyJson);
            return bodyJson;
        } catch (Exception e) {
            log.error("sim {}接口请求异常", reqMethod);
        }
        return null;
    }

    private static double kbToMb(String kb) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(Double.valueOf(kb) / 1024));
    }

}
