package com.redescooter.ses.web.ros.vo.setting;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.*;

/**
 *  @author: alex
 *  @Date: 2020/9/16 18:29
 *  @version：V ROS 1.7.1
 *  @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class GroupListEnter extends PageEnter {

    private String keyword;
}
