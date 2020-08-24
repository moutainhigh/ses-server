package com.redescooter.ses.web.ros.vo.sellsy.enter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyAvatarEnter
 * @description: SellsyAvatarEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/24 16:11
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyAvatarEnter{
    
    private String type;
    
    private String value;
    
    private int classAnInt;
}
