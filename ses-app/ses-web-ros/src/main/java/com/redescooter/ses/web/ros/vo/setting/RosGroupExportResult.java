package com.redescooter.ses.web.ros.vo.setting;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @ClassNameGroupExportResult
 * @Description
 * @Author Aleks
 * @Date2020/9/18 16:40
 * @Version V1.0
 **/
@Data
public class RosGroupExportResult {

    @Excel(name = "GROUP NAME")
    private String groupName;

    @Excel(name = "DESCRIPTION")
    private String description;

    @Excel(name = "ENABLE")
    private String enable;

    @Excel(name = "FOUNDER")
    private String founder;

    @Excel(name = "CREATION TIME")
    private String createdTime;

    @Excel(name = "UPDATER")
    private String updater;

    @Excel(name = "UPDATE TIME")
    private String updatedTime;

}
