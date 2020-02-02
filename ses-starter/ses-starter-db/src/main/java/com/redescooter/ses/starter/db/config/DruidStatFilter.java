package com.redescooter.ses.starter.db.config;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import com.alibaba.druid.support.http.WebStatFilter;


/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 22/1/2020 4:59 下午
 * @ClassName: DruidStatFilter
 * @Function: druid过滤器
 */

@WebFilter(
        filterName ="druidWebStatFilter",
        urlPatterns = {"/*" },
        initParams = {
                @WebInitParam(
                        name ="exclusions",
                        value ="*.js,*.jpg,*.png,*.gif,*.ico,*.css,/druid/*")
        })
public class DruidStatFilter extends WebStatFilter {

}
