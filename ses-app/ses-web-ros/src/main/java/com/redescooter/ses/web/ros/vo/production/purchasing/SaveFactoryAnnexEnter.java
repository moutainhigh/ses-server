package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveFactoryAnnexEnter
 * @description: SaveFactoryAnnexEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 10:39
 */
@ApiModel(value = "工厂附件上传", description = "工厂附件上传")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveFactoryAnnexEnter extends GeneralEnter {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id 为空")
    private Long id;

    @ApiModelProperty(value = "工厂Id", required = true)
    @NotNull(code = ValidationExceptionCode.FACTORY_ID_EMPTY, message = "工厂Id为空")
    private Long factoryId;

    @ApiModelProperty(value = "工厂附件", required = true)
    @NotNull(code = ValidationExceptionCode.ANNEX_IS_EMPTY, message = "附件为空")
    private String factoryAnnexPicture;

    @ApiModelProperty(value = "供应商附件列表 格式：id：1000，picture：www.baidu.com", required = true)
    private String supplierAnnexPictureList;
}
