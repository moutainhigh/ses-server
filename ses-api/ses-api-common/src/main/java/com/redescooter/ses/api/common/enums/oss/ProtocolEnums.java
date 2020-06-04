package com.redescooter.ses.api.common.enums.oss;

import com.aliyun.oss.common.comm.Protocol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName:ProtocolEnums
 * @description: ProtocolEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/06/04 16:05
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProtocolEnums {

    HTTP("HTTP","HTTP","HTTP"),
    HTTPS("HTTPS","HTTPS","HTTPS");

    private String code;

    private String message;

    private String value;


    /**
     * 默认使用http 配置
     * @param value
     * @return
     */
    public static Protocol getProtocol(String value){
        if (StringUtils.isNotEmpty(value)){
            if (StringUtils.equals(value,ProtocolEnums.HTTP.getValue())){
                return Protocol.HTTP;
            }

            if (StringUtils.equals(value,ProtocolEnums.HTTPS.getValue())){
                return Protocol.HTTPS;
            }
        }
        return Protocol.HTTP;
    }
}
