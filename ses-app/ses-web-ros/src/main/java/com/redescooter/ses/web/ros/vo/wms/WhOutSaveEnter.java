package com.redescooter.ses.web.ros.vo.wms;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:WhOutSaveEnter
 * @description: WhOutSaveEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 12:09
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhOutSaveEnter extends GeneralEnter {

    private Long id;

}
