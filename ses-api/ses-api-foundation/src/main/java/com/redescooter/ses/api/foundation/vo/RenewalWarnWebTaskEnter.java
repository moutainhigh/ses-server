package com.redescooter.ses.api.vo;

import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 12/10/2019 5:09 下午
 * @ClassName: RenewalWarnWebTaskEnter
 * @Function: TODO
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class RenewalWarnWebTaskEnter extends BaseMailTaskEnter {

    @ApiModelProperty(value = "续约时间")
    private Date renewalDate;
}
