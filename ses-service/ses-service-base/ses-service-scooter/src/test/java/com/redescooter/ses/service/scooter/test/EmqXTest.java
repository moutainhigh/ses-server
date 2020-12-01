package com.redescooter.ses.service.scooter.test;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterTabletUpdatePublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SetScooterModelPublishDTO;
import com.redescooter.ses.service.scooter.base.BaseTest;
import com.redescooter.ses.service.scooter.config.emqx.MqttClientUtil;
import com.redescooter.ses.starter.emqx.constants.EmqXTopicConstant;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * EMQ X测试类
 * @author assert
 * @date 2020/12/1 11:41
 */
public class EmqXTest extends BaseTest {

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

        mqttClientUtil.publish(String.format(EmqXTopicConstant.SET_SCOOTER_MODEL_TOPIC, tabletSn),
                JSONObject.toJSONString(publish));
    }

}
