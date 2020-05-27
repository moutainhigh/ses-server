package com.redescooter.ses.api.common.vo.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:GetAccountKeyResult
 * @description: GetAccountKeyResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/26 14:02
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class GetAccountKeyResult extends GeneralResult {

    @ApiModelProperty(value = "公钥")
    private String publicKey;
}
