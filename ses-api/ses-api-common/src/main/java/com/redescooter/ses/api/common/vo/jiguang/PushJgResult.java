package com.redescooter.ses.api.common.vo.jiguang;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

/**
 * @ClassName:PushResult
 * @description: PushResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/10/31 10:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PushJgResult extends GeneralResult {
    public long msg_id;
    public int sendno;
    public int statusCode;
    String message;
    int code;
}
