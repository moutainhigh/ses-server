package com.redescooter.ses.api.common.vo.router;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 构建 Vue路由
 *
 * @author jerry
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VueRouter<T> implements Serializable {

    private static final long serialVersionUID = -3327478146308500708L;

    @JsonIgnore
    private String id;
    @JsonIgnore
    private String pId;

    private String path;
    private String name;
    private String code;
    private String type;
    private String component;
    private String redirect;
    private Integer level;
    private RouterMeta meta;
    private Boolean hidden = false;
    private Boolean alwaysShow = false;
    private List<VueRouter<T>> children;

    @JsonIgnore
    private Boolean hasParent = false;

    @JsonIgnore
    private Boolean hasChildren = false;

    public void initChildren() {
        this.children = new ArrayList<>();
    }

}
