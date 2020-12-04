package com.redescooter.ses.api.foundation.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文件上传返回结果 DTO
 * @author assert
 * @date 2020/12/4 19:55
 */
@Data
@ApiModel(value = "文件上传返回结果")
public class FileUploadResultDTO {

    @ApiModelProperty(value = "文件url地址", dataType = "String")
    private String fileUrl;

    @ApiModelProperty(value = "文件大小", dataType = "String")
    private String fileSize;

}
