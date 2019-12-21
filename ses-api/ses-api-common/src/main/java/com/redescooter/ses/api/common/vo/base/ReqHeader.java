package com.redescooter.ses.api.common.vo.base;

import lombok.*;

import java.io.Serializable;

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ReqHeader implements Serializable {

    private static final long serialVersionUID = 6799682561585226797L;

    //    @ApiModelProperty(value = "定位的城市ID 默认0 [整数]", value = "gps_map_id", example = "123")
    private String gpsMapId;

    //    @ApiModelProperty(value = "mac地址 [字符串]", value = "mac", example = "12353kj2u52hj2kh542")
    private String mac;

    //    @ApiModelProperty(value = "网络类型（中国联通，CU; 中国移动，CM; 中国电信，CT; 不能确定，G）[字符串]", value = "net", example = "CT")
    private String net;

    //    @ApiModelProperty(value = "带点号的版本号[字符串]", value = "version", example = "1.1.0")
    private String version;

    //    @ApiModelProperty(value = "手机系统版本号 [字符串]", value = "sys_version", example = "3.5.0")
    private String sysVersion;

    //    @ApiModelProperty(value = "设备唯一识别码[字符串]", value = "uuid", example = "96210EE1-2C08-428B-8EFF-521DE9B10D1E")
    private String uuid;

    //    @ApiModelProperty(value = "手机型号[字符串]", value = "device", example = "G8")
    private String device;

    //    @ApiModelProperty(value = "定位的纬度，用户未开启定位或未获取到位置信息传0.0 [数字]", value = "lat", example = "31.123456")
    private String lat;

    //    @ApiModelProperty(value = "定位的经度，用户未开启定位或未获取到位置信息传0.0 [数字]", value = "lng", example = "121.123456")
    private String lng;

    //    @ApiModelProperty(value = "用户Token[字符串]", value = "token", example = "109DC0BE17A394CB37A9DB2475FB9C8E")
    private String token;

    //    @ApiModelProperty(value = "IOS Android PC[字符串]", value = "app_id", example = "I0810JB0100APPS")
    private String clientType;

    //    @ApiModelProperty(value = "请求时间戳(毫秒)[字符串]", value = "timestamp", example = "1534303756000")
    private String timestamp;

    //    @ApiModelProperty(value = "请求id UUID去除'-'[字符串]", value = "request_id", example = "30F793C69B7D49359676AF584E012A9E")
    private String requestId;   // 请求id

    private String timeZone;

    private String language;

    private String country;
}
