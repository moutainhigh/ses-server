package com.redescooter.ses.web.ros.vo.monday.enter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.support.SimpleTriggerContext;

import io.swagger.annotations.*;

/**
 * @ClassName: MondayMutationBoardEnter
 * @description: MondayMutationBoardEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/31 14:26
 */
@ApiModel(value = "保存板子入参", description = "保存板子入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MondayMutationBoardEnter {

    @ApiModelProperty(value = "板子名称")
    private String boardName;

    @ApiModelProperty(value = "板子类型")
    private String boardKind;

    @ApiModelProperty(value = "工作空间Id")
    private int workspaceId;

    @ApiModelProperty(value = "模板Id")
    private int templateId;
}
