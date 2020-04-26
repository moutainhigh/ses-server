package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:AssemblyQcItemViewResult
 * @description: AssemblyQcItemViewResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 16:18
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AssemblyQcItemViewResult extends GeneralResult {

    private Long id;

    private String itemName;

    private Long qcRrsultId;

    private String qcResultName;
}
