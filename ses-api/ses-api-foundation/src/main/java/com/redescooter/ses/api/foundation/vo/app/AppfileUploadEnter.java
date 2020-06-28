package com.redescooter.ses.api.foundation.vo.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @ClassNameAppfileUploadEnter
 * @Description
 * @Author Aleks
 * @Date2020/6/17 19:02
 * @Version V1.0
 **/
@Data
public class AppfileUploadEnter implements Serializable {

    @ApiModelProperty("上传的文件")
    private MultipartFile file;

    @ApiModelProperty("版本号，code，中间用‘-’隔开")
    private String fileMsg;

}
