package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 31/12/2019 1:53 下午
 * @ClassName: SaveDriverEnter
 * @Function: TODO
 */
@ApiModel(value = "创建司机", description = "创建司机")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class SaveDriverEnter extends GeneralEnter {
}
