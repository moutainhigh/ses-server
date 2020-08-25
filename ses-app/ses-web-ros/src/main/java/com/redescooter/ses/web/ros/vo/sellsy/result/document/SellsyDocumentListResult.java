package com.redescooter.ses.web.ros.vo.sellsy.result.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:SellsyDocvumentListResult
 * @description: SellsyDocvumentListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 14:49
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyDocumentListResult {
    
    private String id;
    
    private String corpname;
    
    private String ownerFullName;
    
    private String status;
    
    private String filename;
    
    private String fileid;
    
    private String nbpages;
    
    private String ident;
    
    private String thirdident;
    
    private String thirdname;
    
    private String thirdid;
    
    private String thirdvatnum;
    
    private String contactId;
    
    private String contactName;
    
    private String displayedDate;
    
    private String currencysymbol;
    
    private String subject;
    
    private String docspeakerText;
    
    private String docspeakerStaffId;
    
    private String docspeakerStaffFullName;
    
    private String corpaddressid;
    
    private String thirdaddressid;
    
    private String shipaddressid;
    
    private String rowsAmount;
    
    private String discountPercent;
    
    private String discountAmount;
    
    private String rowsAmountDiscounted;
    
    private String offerAmount;
    
    private String rowsAmountAllInc;
    
    private String packagingsAmount;
    
    private String shippingsAmount;
    
    private String totalAmountTaxesFree;
    
    private String taxesAmountSum;
    
    private String taxesAmountDetails;
    
    private String totalAmount;
    
    private String useEcotaxe;
    
    private String totalEcoTaxFree;
    
    private String totalEcoTaxInc;
    
    private String ecoTaxId;
    
    private String taxBasis;
    
    private String payDateText;
    
    private String payDateCustom;
    
    private String hasDeadlines;
    
    private String payMediumsText;
    
    private String payCheckOrderText;
    
    private String payBankAccountText;
    
    private String shippingNbParcels;
    
    private String shippingWeight;
    
    private String shippingWeightUnit;
    
    private String shippingVolume;
    
    private String shippingTrackingNumber;
    
    private String shippingTrackingUrl;
    
    private String shippingDate;
    
    private String saveThirdPrefs;
    
    private String displayShipAddress;
    
    private String analyticsCode;
    
    private String recorded;
    
    private String recordable;
    
    private String rateCategory;
    
    private String isTaxesInc;
    
    private String hasDoubleVat;
    
    private String stockImpact;
    
    private String isFromPresta;
    
    private String eCommerceShopId;
    
    private String signcoords;
    
    private String esignID;
    
    private String promotionid;
    
    private String useServiceDates;
    
    private String serviceDateStart;
    
    private String serviceDateStop;
    
    private String locked;
    
    private String reconciledStatus;
    
    private String fiscalYearId;
    
    private String corpid;
    
    private String ownerid;
    
    private String linkedtype;
    
    private String linkedid;
    
    private String created;
    
    private String prefsid;
    
    private String parentid;
    
    private String docmapid;
    
    private String hasVat;
    
    private String period_start;
    
    private String thirdRelationType;
    
    private String auxCode;
    
    private String thirdemail;
    
    private String thirdtel;
    
    private String thirdmobile;
    
    private String doctypeid;
    
    private String step;
    
    private String isDeposit;
    
    private String posId;
    
    private String dueAmount;
    
    private String isSepaExported;
    
    private String lastSepaExportDate;
    
    private String orderIdent;
    
    private String docid;
    
    private String currencyid;
    
    private String currencyposition;
    
    private String numberformat;
    
    private String numberdecimals;
    
    private String numberthousands;
    
    private String numberprecision;
    
    private String notes;
    
    private String bankaccountid;
    
    private String publicLinkAvailable;
    
    private String marge;
    
    private String percentmarge;
    
    private String marge_tauxMarque;
    
    private String marge_tauxMarge;
    
    private String payDateCustomUnix;
    
    private String formatted_dueAmount;
    
    private String formatted_tauxMarque;
    
    private String formatted_tauxMarge;
    
    private String formatted_marge;
    
    private String note;
    
    private String step_color;
    
    private String step_hex;
    
    private String step_label;
    
    private String step_css;
    
    private String step_banner;
    
    private String step_id;
    
    private String displayed_payMediumsText;
    
    private String formatted_totalAmount;
    
    private String formatted_totalAmountTaxesFree;
    
    private String formatted_created;
    
    private String formatted_displayedDate;
    
    private String formatted_payDateCustom;
    
    private String formatted_serviceDateStart;
    
    private String formatted_serviceDateStop;
    
    private String formatted_lastSepaExportDate;
    
    private String formatted_lastpayment;
    
    private String noedit;
    
    private String rateCategoryFormated;
    
    private String publicLinkShort;
    
    private String fiscalYear;
    
    private List<String> tags;
    
    private List<String> smartTags;
    
    private String thirdStatus;
    
    private SellsyReconciliationStatusResult reconciliationStatus;
    
    private String reconciliationStatusName;
}
