package com.redescooter.ses.web.ros.vo.setting;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.*;

/**
 * @ClassNameGroupExportResult
 * @Description
 * @Author alex
 * @Date2020/9/18 16:40
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class RosParameterExportResult {

    @Excel(name = "Parameter_Name")
    private String parameterName;

    @Excel(name = "Group_Name")
    private String groupName;

    @Excel(name = "Key")
    private String key;

    @Excel(name = "Value")
    private String value;

    @Excel(name = "Enable")
    private String enable;

    @Excel(name = "Founder")
    private String founder;

    @Excel(name = "Created_Time")
    private String createdTime;

    @Excel(name = "Updater")
    private String updater;

    @Excel(name = "Updated_Time")
    private String updatedTime;

}
