package com.redescooter.ses.web.ros.vo.sellsy.result.staff;

import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyAvatarGeneralResult;

import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyStaffResult {

    private String id;

    private String peopleid;

    private String ownerid;

    private String permissionprofile;

    private String type;

    private String prefsid;

    private String corpid;

    private String sessionid;

    private String status;

    private String footer;

    private String footerAuto;

    private String optin;

    private String alertsEmail;

    private String catalogueOpen;

    private String dashboardOpen;

    private String fastsearchOpen;

    private String timezone;

    private String uilang;

    private String showNotif;

    private String notifs;

    private String offlineMobileIsActived;

    private String notificationType;

    private String isDecisionMaker;

    private String attributes;

    private String forename;

    private String name;

    private String email;

    private String tel;

    private String mobile;

    private String fax;

    private String pic;

    private String civil;

    private String actif;

    private String position;

    private String created;

    private String lastUpdate;

    private String formatted_tel;

    private String formatted_mobile;

    private String formatted_fax;

    private SellsyAvatarGeneralResult avatar;

    private String fullName;

    private String staffList;

    private String isAdmin;

    private int linkedid;

    private String linkedtype;

}
