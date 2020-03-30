package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName:AssemblyQcEnter
 * @description: AssemblyQcEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 17:09
 */
@ApiModel(value = "Qc质检记录", description = "Qc质检记录")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AssemblyQcEnter extends PageEnter {

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "质检开始时间")
    private Date qcStartTime;

    @ApiModelProperty(value = "质检结束时间")
    private Date qcEndTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
