package com.redescooter.ses.starter.db.config;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import com.alibaba.druid.support.http.StatViewServlet;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 22/1/2020 5:00 下午
 * @ClassName: DruidStatViewServlet
 * @Function: druid控制台servlet，支持设置账号和密码访问登录
 */

@WebServlet(

        urlPatterns = {"/druid/*"},
        initParams = {
                @WebInitParam(name ="allow", value =""),
                @WebInitParam(name ="loginUsername", value ="admin"),
                @WebInitParam(name ="loginPassword", value ="admin"),
                @WebInitParam(name ="resetEnable", value ="false")
        })
public class DruidStatViewServlet extends StatViewServlet implements Servlet {

    private static final long serialVersionUID =8782104600990278875L;

}
