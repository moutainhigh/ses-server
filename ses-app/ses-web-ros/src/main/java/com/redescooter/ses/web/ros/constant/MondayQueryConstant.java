package com.redescooter.ses.web.ros.constant;

/**
 * @ClassName:MondayQueryConstant
 * @description: MondayQueryConstant
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/09 16:44
 */
public interface MondayQueryConstant {

    String testQuery="query {\n" +
            "teams {\n" +
            "name\n" +
            "picture_url\n" +
            "users {\n" +
            "created_at\n" +
            "phone\n" +
            "}\n" +
            "}\n" +
            "}";
}
