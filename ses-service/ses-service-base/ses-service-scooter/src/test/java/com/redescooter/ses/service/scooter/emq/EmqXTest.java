package com.redescooter.ses.service.scooter.emq;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterTabletUpdatePublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SetScooterModelPublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SpecificDefGroupPublishDTO;
import com.redescooter.ses.service.scooter.SesServiceScooterApplicationTests;
import com.redescooter.ses.service.scooter.config.emqx.MqttClientUtil;
import com.redescooter.ses.starter.emqx.constants.EmqXTopicConstant;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * EMQ X测试类
 * @author assert
 * @date 2020/12/1 11:41
 */
public class EmqXTest extends SesServiceScooterApplicationTests {

    @Resource
    private MqttClientUtil mqttClientUtil;

    /**
     * 车辆平板升级测试
     */
    @Test
    public void scooterTabletUpdateTest() {
        ScooterTabletUpdatePublishDTO tabletUpdatePublish = new ScooterTabletUpdatePublishDTO();
        tabletUpdatePublish.setTabletSn("OF894HSG4T9LHECY");
        tabletUpdatePublish.setDownloadLink("http://test.redescooter.net/download/scs_versionCode7_versionName1.0.6.apk");
        tabletUpdatePublish.setVersionCode("7");

        mqttClientUtil.publish(String.format(EmqXTopicConstant.SCOOTER_TABLET_UPDATE_TOPIC, "OF894HSG4T9LHECY"),
                JSONObject.toJSONString(tabletUpdatePublish));
    }

    /**
     * 设置车辆类型测试
     */
    @Test
    public void scooterModelTest() {
        String tabletSn = "DECQ6HEUKJJFQGOJ";
        SetScooterModelPublishDTO publish = new SetScooterModelPublishDTO();
        publish.setTabletSn(tabletSn);
        publish.setScooterModel(ScooterModelEnum.SCOOTER_E125.getType());
        publish.setSpecificDefGroupList(buildSpecificDefGroupData());

        mqttClientUtil.publish(String.format(EmqXTopicConstant.SET_SCOOTER_MODEL_TOPIC, tabletSn),
                JSONObject.toJSONString(publish));
    }

    /**
     * 组装自定义规格分组信息
     * @return
     */
    private List<SpecificDefGroupPublishDTO> buildSpecificDefGroupData() {
        List<SpecificDefGroupPublishDTO> groupList = new ArrayList<>();

        /**
         * 组装 E125型号相关的配置信息
         */
        // 电池1
        double speedRatio = 22.0 / 105.0;
        SpecificDefGroupPublishDTO batteryOne = SpecificDefGroupPublishDTO.builder()
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

        // 电池2
        SpecificDefGroupPublishDTO batteryTwo = SpecificDefGroupPublishDTO.builder()
                .wheelDiameter("52")
                .speedRatio(String.valueOf(speedRatio))
                .limitSpeedBos("75")
                .limiting("50")
                .speedLimit("80")
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

        // 电池3
        SpecificDefGroupPublishDTO batteryThree = SpecificDefGroupPublishDTO.builder()
                .wheelDiameter("52")
                .speedRatio(String.valueOf(speedRatio))
                .limitSpeedBos("135")
                .limiting("90")
                .speedLimit("120")
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

        // 电池4
        SpecificDefGroupPublishDTO batteryFour = SpecificDefGroupPublishDTO.builder()
                .wheelDiameter("52")
                .speedRatio(String.valueOf(speedRatio))
                .limitSpeedBos("165")
                .limiting("120")
                .speedLimit("120")
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
        groupList.add(batteryFour);
        groupList.add(new SpecificDefGroupPublishDTO());
        groupList.add(new SpecificDefGroupPublishDTO());
        groupList.add(new SpecificDefGroupPublishDTO());

        return groupList;
    }

}
