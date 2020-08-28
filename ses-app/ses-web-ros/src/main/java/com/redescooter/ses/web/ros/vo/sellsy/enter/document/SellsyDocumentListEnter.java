package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName:SellsyDocumentListEnter
 * @description: SellsyDocumentListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 14:26
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyDocumentListEnter {

    //单据类型
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCTYPE_IS_EMPTY, message = "单据类型为空")
    private String doctype;
    
    //在清单中包括付款信息(只有发票) yes or no
    private String includePayments;
    
    //排序 ASC  or DESC
    private String direction;
    
    // 文档日期 doc_ident or doc_thirdname or doc_displayedDate or doc_totalAmountTaxesFree
    private String order;
    
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
    
    //分页字段
    private int nberpage=10;
    
    private int pagenum=1;
}
