package com.redescooter.ses.web.ros.vo.wms;


import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNamewmsEnter
 * @Description
 * @Author Joan
 * @Date2020/7/16 18:54
 * @Version V1.0
 **/
@ApiModel(value = "仓储可用入参", description = "仓储入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WmsStockEnter extends PageEnter {

@ApiModelProperty(value = "类型")
private String type;

@ApiModelProperty(value = "关键字,名称、标签查询")
private  String keyword;
}