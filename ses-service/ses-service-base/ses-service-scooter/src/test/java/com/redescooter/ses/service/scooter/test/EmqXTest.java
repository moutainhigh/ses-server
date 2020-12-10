package com.redescooter.ses.service.scooter.test;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterTabletUpdatePublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SetScooterModelPublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SpecificDefGroupPublishDTO;
import com.redescooter.ses.service.scooter.base.BaseTest;
import com.redescooter.ses.service.scooter.config.emqx.MqttClientUtil;
import com.redescooter.ses.starter.emqx.constants.EmqXTopicConstant;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * EMQ X测试类
 * @author assert
 * @date 2020/12/1 11:41
 */
public class EmqXTest {

    @Resource
    private MqttClientUtil mqttClientUtil;

    /**
     * 车辆平板升级测试
     */
    @Test
    public void scooterTabletUpdateTest() {
        ScooterTabletUpdatePublishDTO tabletUpdatePublish = new ScooterTabletUpdatePublishDTO();
        tabletUpdatePublish.setTabletSn("OF894HSG4T9LHECY");
        tabletUpdatePublish.setDownloadLink("http://test.redescooter.net/download/appCode5.apk");
        tabletUpdatePublish.setVersionCode("5");
        tabletUpdatePublish.setUpdateOrRollBack(false);

        mqttClientUtil.publish(String.format(EmqXTopicConstant.SCOOTER_TABLET_UPDATE_TOPIC, "OF894HSG4T9LHECY"),
                JSONObject.toJSONString(tabletUpdatePublish));
    }

    /**
     * 设置车辆类型测试
     */
    @Test
    public void scooterModelTest() {
        String tabletSn = "IBJBAIZHTWMBK7PB";
        SetScooterModelPublishDTO publish = new SetScooterModelPublishDTO();
        publish.setTabletSn(tabletSn);
        publish.setType(ScooterModelEnum.SCOOTER_E100.getType());
        publish.setSpecificDefGroupList(buildSpecificDefGroupData());

        System.out.println("json：" + JSONObject.toJSONString(publish));
//        mqttClientUtil.publish(String.format(EmqXTopicConstant.SET_SCOOTER_MODEL_TOPIC, tabletSn),
//                JSONObject.toJSONString(publish));
    }

    /**
     * 组装自定义规格分组信息
     * @return
     */
    private List<SpecificDefGroupPublishDTO> buildSpecificDefGroupData() {
        List<SpecificDefGroupPublishDTO> groupList = new ArrayList<>();

        double speedRatio = 22.0 / 130.0;
        SpecificDefGroupPublishDTO batteryOne = SpecificDefGroupPublishDTO.builder()
                .batteryBatchNo("1")
                .wheelDiameter("52")
                .speedRatio(String.valueOf(speedRatio))
                .limitSpeedBos("55")
                .limiting("35")
                .speedLimit("45")
                .socRedWarning("20")
                .orangeWarning("5")
                .stallSOC("0")
                .setSOCTo0AtStallUndervoltage("62")
                .stallVoltageUndervoltage("58")
                .voltageLegalRecognitionMin("50")
                .voltageLegalRecognitionMax("84")
                .controllerUndervoltage("58")
                .controllerUndervoltageRecovery("62")
                .build();


        // 设置规格自定义项分组信息 -- 现在默认一辆车有四节电池
        groupList.add(batteryOne);
        groupList.add(new SpecificDefGroupPublishDTO());
        groupList.add(new SpecificDefGroupPublishDTO());
        groupList.add(new SpecificDefGroupPublishDTO());

        return groupList;
    }

}
