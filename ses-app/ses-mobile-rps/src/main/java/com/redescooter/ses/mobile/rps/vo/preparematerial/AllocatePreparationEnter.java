package com.redescooter.ses.mobile.rps.vo.preparematerial;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.*;

/**
 * @ClassName:AllocatePreparationEnter
 * @description: AllocatePreparationEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/26 17:21
 */
@ApiModel(value = "调拨备料", description = "调拨备料")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AllocatePreparationEnter extends GeneralEnter {

    @ApiModelProperty(value = "Id")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "Id不为空")
    private Long id;

    @ApiModelProperty(value = "部件集合")
    List<SavePrepareMaterialPartListEnter> savePrepareMaterialListEnterList;

    @ApiModelProperty(value = "部件基本数据")
    Map<Long, List<SavePartBasicDateEnter>> savePartBasicDateMap;
}
