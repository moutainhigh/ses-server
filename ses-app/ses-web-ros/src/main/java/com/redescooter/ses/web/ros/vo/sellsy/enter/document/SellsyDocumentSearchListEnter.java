package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyDocumentSearchListEnter {
    //文档编号
    private String ident;

    //steps
    private List<String> steps;

    //客户身份
    private List<Integer> thirds;

    //拥有者
    private Integer owner;

    //店铺Id
    private List<Integer> shops;

    //用逗号分隔的标签
    private String tags;

    //文件日期范围的开始
    private Timestamp periodecreated_start;

    //文件日期范围的结束
    private Timestamp periodecreated_end;

    //创建日期范围的结束
    private Timestamp periodecreationDate_start;

    //创建日期范围的结束
    private Timestamp periodecreationDate_end;

    //文件到期日期范围的开始
    private Timestamp periodeexpired_start;

    //文件到期日期范围的开始
    private Timestamp periodeexpired_end;

}
