package com.redescooter.ses.web.ros.vo.sellsy.result.document;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @ClassName:SellsyDocumentDetailResult
 * @description: SellsyDocumentDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 16:17
 */
@Data //生成getter,setter等函数
@EqualsAndHashCode(callSuper = false)
public class SellsyDocumentDetailResult {
    private String id;
    
    private String ownerFullName;
    
    private String status;
    
    private String filename;
    
    private String fileid;
    
    private String nbpages;
    
    private String ident;
    
    private String thirdident;
    
    private String thirdid;
    
    private String contactId;
    
    private String contactName;
    
    private String displayedDate;
    
    private String currencysymbol;
    
    private String subject;
    
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
    
    private String hasDeadlines;
    
    private String shippingNbParcels;
    
    private String shippingWeight;
    
    private String shippingWeightUnit;
    
    private String shippingVolume;
    
    private String shippingTrackingNumber;
    
    private String shippingTrackingUrl;
    
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
    
    private String doctypeid;
    
    private String step;
    
    private String isDeposit;
    
    private String posId;
    
    private String dueAmount;
    
    private String isSepaExported;
    
    private String lastSepaExportDate;
    
    private String orderIdent;
    
    private String docid;
    
    private String countrycode;
    
    private String defaultAddressCountry;
    
    private String dimensionUnit;
    
    private String weightUnit;
    
    private String globalDiscount;
    
    private String globalDiscountUnit;
    
    private String globalOffer;
    
    private String useDeposit;
    
    private String deposit;
    
    private String depositUnit;
    
    private String discountByRows;
    
    private String addPdfToEmail;
    
    private String addGtuToEmail;
    
    private String useSellsyMailPopupOnThirdPeople;
    
    private String hideColumnName;
    
    private String hideColumnNotes;
    
    private String hideColumnUnit;
    
    private String hideColumnTaxFree;
    
    private String hideColumnTax;
    
    private String hideColumnTaxInc;
    
    private String hideColumnQt;
    
    private String hideColumnImage;
    
    private String hideColumnDiscount;
    
    private String hideTotalTaxesFree;
    
    private String hideTotal;
    
    private String hidePayment;
    
    private String hidePaymentDate;
    
    private String hidePricesOnDelivery;
    
    private String useDoubleVat;
    
    private String hideTaxes;
    
    private String hasPenaltyRetardWarning;
    
    private String penaltyRetardWarningText;
    
    private String hasTvaLawText;
    
    private String tvaLawText;
    
    private String showContactOnPdf;
    
    private String showThirdVatOnPdf;
    
    private String showParentOnPdf;
    
    private String hasCorpAgree;
    
    private String hasThirdAgree;
    
    private String notes;
    
    private String nbExpireDays;
    
    private String displayAmounts;
    
    private String display_footer;
    
    private String display_ident;
    
    private String display_email;
    
    private String display_tel;
    
    private String display_mobile;
    
    private String display_fax;
    
    private String display_addresse;
    
    private String displayBankAccount;
    
    private String displayIBAN;
    
    private String displayBIC;
    
    private String hideTips;
    
    private String hideOrdersDocs;
    
    private String hideDeliveriesDocs;
    
    private String hideProformaDocs;
    
    private String forceDisplayBankAccount;
    
    private String forceDisplayCheckOrder;
    
    private String showUnitIn;
    
    private String addBarCodeToDesc;
    
    private String addBarCodeImageToDesc;
    
    private String showNbItemOnPdf;
    
    private String hideSignatureOnDelivery;
    
    private String hideSignatureOnOrder;
    
    private String hideSignatureOnEstimate;
    
    private String hidePaymentDelay;
    
    private String csvdelimiter;
    
    private String csvwrapper;
    
    private String csvtextformat;
    
    private String saasNotifications;
    
    private String showClientNumber;
    
    private String useReliquat;
    
    private String showWeightItemOnPdf;
    
    private String showMoreByDefault;
    
    private String hideMeansOfPayment;
    
    private String hideDeadlinePayment;
    
    private String hideDepositDetails;
    
    private String showThirdSiretOnPdf;
    
    private String allowPayPalPay;
    
    private String allowBluePaidPay;
    
    private String allowStripePay;
    
    private String allowAtosPay;
    
    private String allowAdyenPay;
    
    private String showRefClient;
    
    private String showThirdSirenOnPdf;
    
    private String phoneformat;
    
    private String showTaxBasis;
    
    private String publicLinkAvailable;
    
    private String conditionDocShow;
    
    private String conditionDocLabel;
    
    private String conditionDocFileId;
    
    private String useTaxOnDeposit;
    
    private String fiscal_end_date;
    
    private String allowDirectdebit;
    
    private String isdefault;
    
    private String name;
    
    private String headerMode;
    
    private String footerMode;
    
    private String logoMode;
    
    private String useBg;
    
    private String bgMode;
    
    private String bgFile;
    
    private String bgCustomFile;
    
    private String hideTopPart;
    
    private String hideBottomSeparator;
    
    private String documentIdentOnLeft;
    
    private String hideFill;
    
    private String colorFill;
    
    private String hideDepositRecap;
    
    private String colorText;
    
    private String colorHeaderRow;
    
    private String colorNotes;
    
    private String colorDiscreet;
    
    private String colorHeaderFooter;
    
    private String font;
    
    private String fontsize;
    
    private String language;
    
    private String langid;
    
    private String timeformat;
    
    private String txts;
    
    private String customName;
    
    private String topMargin;
    
    private String headerAlign;
    
    private String documentAddressOnLeft;
    
    private String documentFormat;
    
    private String pageNumber;
    
    private String alignDocNumber;
    
    private String numericDataAlign;
    
    private String customfieldsGroups;
    
    private String context;
    
    private String country;
    
    private String doclang;
    
    private String type;
    
    private List<String> relateds;
    
    private String relateds_amount;
    
    private SellsyDocumentByIdCorpAddressResult corpAddress;
    
    private String hasThird;
    
    private String thirdType;
    
    private String thirdSiret;
    
    private String thirdSiren;
    
    private String thirdRelationType;
    
    private String thirdStatus;
    
    private boolean isThirdLoadable;
    
    private SellsyShipAddressResult thirdAddress;
    
    private SellsyShipAddressResult shipAddress;
    
    private List<SellsyConditionDocResult> conditionDoc;
    
    private int conditionDocFileId_fr;
    
    private String conditionDocLabel_fr;
    
    private int conditionDocFileId_en;
    
    private String conditionDocLabel_en;
    
    private int conditionDocFileId_de;
    
    private String conditionDocLabel_de;
    
    private int conditionDocFileId_es;
    
    private String conditionDocLabel_es;
    
    private String canWriteDocNamespace;
    
    private String logoFile;
    
    private String logoFile_system;
    
    private int logoHeight;
    
    private int logoWidth;
    
    private String label;
    
    private String fullIdent;
    
    private String numformat;
    
    private String numformats;
    
    private String precisions;
    
    private String currencies;
    
    private String currencypositions;
    
    private String hasRelateds;
    
    private String corpName;
    
    private String thirdName;
    
    private String thirdVatNum;
    
    private String docspeaker_text;
    
    private String paydate_text;
    
    private String paydate_custom;
    
    private String paymediums_text;
    
    private String paycheckorder_text;
    
    private String paybankaccount_text;
    
    private String currency;
    
    private String num_currencypos;
    
    private String docspeaker;
    
    private String doclayout;
    
    private String docLang;
    
    private String num_name;
    
    private String num_decimals;
    
    private String num_thousands;
    
    private String num_precision;
    
    private String num_precisioncustom;
    
    private String defaultTax;
    
    private String defaultTax2;
    
    private String defaultUnit;
    
    private String defaultShipping;
    
    private String defaultPackaging;
    
    private String paydate;
    
    private String paydate_xdays;
    
    private String paydate_endmonth;
    
    private String paydate_scaledDetails;
    
    private String paymediums;
    
    private String paymedium_other;
    
    private String paycheckorder;
    
    private String paybankaccount;
    
    private List<String> weightUnits;
    
    private String hideableColumns;
    
    private String doclayouts;
    
    private String docLangs;
    
    private String currencyleft;
    
    private String currencyright;
    
    private String discountUnits;
    
    private String taxes;
    
    private String defaultWeightUnit;
    
    private String shippings;
    
    private String packagings;
    
    private String units;
    
    private String sellCodes;
    
    private String shippingCodes;
    
    private String packagingCodes;
    
    private String hasAnalytics;
    
    private String analyticsType;
    
    private String canWriteShippings;
    
    private String canWritePackagings;
    
    private String canWriteUnits;
    
    private String canWriteTaxes;
    
    private String canWritePaydates;
    
    private String paydates;
    
    private String canWritePaymediums;
    
    private String canWritePayCheckOrders;
    
    private String paycheckorders;
    
    private String bankaccounts;
    
    private String canWriteDocSpeakers;
    
    private String docspeakers;
    
    private String canDocOnceLine;
    
    private String formatted_rowsAmount;
    
    private String formatted_discountPercent;
    
    private String formatted_discountAmount;
    
    private String formatted_rowsAmountDiscounted;
    
    private String formatted_offerAmount;
    
    private String formatted_rowsAmountAllInc;
    
    private String formatted_packagingsAmount;
    
    private String formatted_shippingsAmount;
    
    private String formatted_totalAmountTaxesFree;
    
    private String formatted_taxesAmountSum;
    
    private String formatted_totalAmount;
    
    private String formatted_shippingWeight;
    
    private String formatted_shippingVolume;
    
    private String formatted_globalDiscount;
    
    private String formatted_globalOffer;
    
    private String formatted_deposit;
    
    private String formatted_dueAmount;
    
    private String formatted_totalEcoTaxDisplay;
    
    private String tel;
    
    private String fax;
    
    private String email;
    
    private String mobile;
    
    private String formatted_taxesAmountDetails;
    
    private String corpFooter;
    
    private String map;
    
    private SellsyVatSummaryResult vatSummary;
    
    private String prefs;
    
    private List<String> tags;
    
    private String related;
}
