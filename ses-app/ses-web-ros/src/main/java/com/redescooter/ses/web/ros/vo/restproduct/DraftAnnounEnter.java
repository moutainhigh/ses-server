package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameDraftAnnounEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/24 13:59
 * @Version V1.0
 **/
@Data
public class DraftAnnounEnter extends GeneralEnter {

    @ApiModelProperty("草稿id，多个用,隔开")
    private String id;

    @ApiModelProperty("发布人id")
    private Long announUserId;

}
