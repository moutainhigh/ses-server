package com.redescooter.ses.web.ros.vo.sellsy.result.catalogue;

import lombok.*;

import java.util.List;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCatalogueResult {
    private String id;

    private String type;

    private String corpid;

    private String ownerid;

    private String status;

    private String actif;

    private String isEnabled;

    private String rank;

    private String name;

    private String tradename;

    private String tradenametonote;

    private String notes;

    private String unitAmount;

    private String unitAmountIsTaxesFree;

    private String useEcoTax;

    private String ecoTaxType;

    private String ecoTax;

    private String unitid;

    private String unit;

    private String qt;

    private String taxid;

    private String taxrate;

    private String purchaseAmount;

    private String costPerHour;

    private String createdAt;

    private String updatedAt;

    private String useDeclination;

    private String stickyNote;

    private String categoryid;

    private String inPos;

    private String timetrackingEnabled;

    private String categoryName;

    private String promoid;

    private String translationsLangs;

    private String itemid;

    private int decl_id;

    private int declid;

    private String analyticsCode;

    private String accountingPurchaseCode;

    private String accountingCode;

    private String refAmount;

    private String accountingCodeService;

    private String hasPricesExecption;

    private String typeLabel;

    private String nameSample;

    private String notesSample;

    private String notesFormated;

    private String notesHTMLSample;

    private String isTaxesFree;

    private String isTaxesFreeBool;

    private String purchaseAmountTaxesInc;

    private String unitAmountTaxesInc;

    private String formatted_purchaseAmount;

    private String formatted_purchaseAmountTaxesInc;

    private String formatted_unitAmount;

    private String formatted_unitAmountTaxesInc;

    private String formatted_qt;

    private String formatted_taxrate;

    private String slug;

    private String promotion;

    private SellsyCataloguePriceResult prices;

    private String formated_rc_143036;

    private String formated_rc_143037;

    private SellsyCatalogueImageResult defaultImage;

    private List<String> galery;

    private List<String> tags;

    private String accountingCodeVal;

    private String accountingPurchaseCodeVal;
}
