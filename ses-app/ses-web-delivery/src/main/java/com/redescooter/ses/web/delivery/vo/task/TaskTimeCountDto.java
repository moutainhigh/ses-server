package com.redescooter.ses.web.delivery.vo.task;

import lombok.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/2/2020 6:27 下午
 * @ClassName: TaskTimeCountDto
 * @Function: TODO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class TaskTimeCountDto {
    private int hnum = 0;
    private int tnum = 0;
}
