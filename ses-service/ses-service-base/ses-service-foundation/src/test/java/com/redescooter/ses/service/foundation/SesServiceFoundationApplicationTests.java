package com.redescooter.ses.service.foundation;

import com.redescooter.ses.api.foundation.service.PushService;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SesServiceFoundationApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Reference
    private PushService pushService;

    @Test
    public void test() {
        String test = "RedEScooter2019";
        System.out.println(DigestUtils.md5Hex(test + "40382"));
    }

    @SneakyThrows
    @Test
    public void test1() {
        JSONObject jsonObject = new JSONObject("{\n" +
                "    \"logoHeight\": 663,\n" +
                "    \"nbExpireDays\": \"30\",\n" +
                "    \"ecoTaxId\": \"0\",\n" +
                "    \"shippingVolume\": \"0.000000000\",\n" +
                "    \"ident\": \"FACT_202009_00100\",\n" +
                "    \"discountAmount\": \"0.000000000\",\n" +
                "    \"headerMode\": \"all\",\n" +
                "    \"isDeposit\": \"N\",\n" +
                "    \"num_precision\": \"3\",\n" +
                "    \"display_fax\": \"N\",\n" +
                "    \"conditionDocLabel_de\": \"\",\n" +
                "    \"display_email\": \"N\",\n" +
                "    \"hideColumnUnit\": \"N\",\n" +
                "    \"hideTotalTaxesFree\": \"N\",\n" +
                "    \"colorDiscreet\": \"000000\",\n" +
                "    \"formatted_totalAmountTaxesFree\": \"1 750,00 €\",\n" +
                "    \"canWriteDocNamespace\": \"Y\",\n" +
                "    \"tel\": \"000000000\",\n" +
                "    \"shippingWeightUnit\": \"g\",\n" +
                "    \"formatted_totalAmount\": \"2 100,00 €\",\n" +
                "    \"totalAmountTaxesFree\": \"1750.000000000\",\n" +
                "    \"doclayout\": \"95037\",\n" +
                "    \"hideDepositRecap\": \"N\",\n" +
                "    \"contactId\": \"0\",\n" +
                "    \"customName\": \"Facture\",\n" +
                "    \"hasRelateds\": \"Y\",\n" +
                "    \"ownerid\": \"163567\",\n" +
                "    \"tags\": [],\n" +
                "    \"discountByRows\": \"N\",\n" +
                "    \"allowBluePaidPay\": \"N\",\n" +
                "    \"filename\": \"FACT-202009-00100_1600597677.1571\",\n" +
                "    \"topMargin\": \"20\",\n" +
                "    \"deposit\": \"0.000000000\",\n" +
                "    \"analyticsType\": \"item\",\n" +
                "    \"doctypeid\": \"10681135\",\n" +
                "    \"bgCustomFile\": \"\",\n" +
                "    \"documentFormat\": \"A4\",\n" +
                "    \"defaultTax2\": \"0\",\n" +
                "    \"fiscal_end_date\": \"1000-12-31\",\n" +
                "    \"hidePaymentDelay\": \"N\",\n" +
                "    \"showWeightItemOnPdf\": \"N\",\n" +
                "    \"isSepaExported\": \"N\",\n" +
                "    \"hideColumnTaxFree\": \"N\",\n" +
                "    \"shipaddressid\": \"88104033\",\n" +
                "    \"isTaxesInc\": \"N\",\n" +
                "    \"alignDocNumber\": \"L\",\n" +
                "    \"logoWidth\": 1184,\n" +
                "    \"documentAddressOnLeft\": \"N\",\n" +
                "    \"shippingWeight\": \"0.000000000\",\n" +
                "    \"hasAnalytics\": \"Y\",\n" +
                "    \"canWriteShippings\": \"Y\",\n" +
                "    \"paycheckorder\": \"3497457\",\n" +
                "    \"addBarCodeImageToDesc\": \"N\",\n" +
                "    \"currency\": \"1\",\n" +
                "    \"langid\": \"0\",\n" +
                "    \"thirdType\": \"corporation\",\n" +
                "    \"email\": \"contactpro@redescooter.fr\",\n" +
                "    \"formatted_taxesAmountSum\": \"350,00 €\",\n" +
                "    \"showTaxBasis\": \"\",\n" +
                "    \"serviceDateStart\": \"0000-00-00\",\n" +
                "    \"bgFile\": \"custom\",\n" +
                "    \"showNbItemOnPdf\": \"N\",\n" +
                "    \"docspeakerStaffId\": \"165489\",\n" +
                "    \"timeformat\": \"dmy\",\n" +
                "    \"numericDataAlign\": \"R\",\n" +
                "    \"fullIdent\": \"Facture N° FACT_202009_00100\",\n" +
                "    \"eCommerceShopId\": \"0\",\n" +
                "    \"hasCorpAgree\": \"N\",\n" +
                "    \"currencyleft\": \"\",\n" +
                "    \"showMoreByDefault\": \"\",\n" +
                "    \"hasDeadlines\": \"N\",\n" +
                "    \"docspeakerStaffFullName\": \"Mme Farrah Nafti\",\n" +
                "    \"hideColumnNotes\": \"N\",\n" +
                "    \"font\": \"helvetica\",\n" +
                "    \"csvtextformat\": \"without\",\n" +
                "    \"country\": \"FR\",\n" +
                "    \"notes\": \"Statut de paiement:A régler.  Payment Types:Prélèvement . Payment time:\",\n" +
                "    \"bgMode\": \"all\",\n" +
                "    \"pageNumber\": \"both\",\n" +
                "    \"addBarCodeToDesc\": \"N\",\n" +
                "    \"countrycode\": \"FR\",\n" +
                "    \"conditionDocShow\": \"N\",\n" +
                "    \"formatted_packagingsAmount\": \"0,00 €\",\n" +
                "    \"taxBasis\": \"{\\\"tax\\\":[{\\\"rate\\\":\\\"20.00\\\",\\\"amount\\\":\\\"350.00\\\",\\\"totalBaseAmount\\\":\\\"1750.000000000\\\",\\\"isEcoTax\\\":\\\"N\\\"}]}\",\n" +
                "    \"useBg\": \"custom\",\n" +
                "    \"language\": \"FR\",\n" +
                "    \"canWritePayCheckOrders\": \"Y\",\n" +
                "    \"fiscalYearId\": \"87688\",\n" +
                "    \"serviceDateStop\": \"0000-00-00\",\n" +
                "    \"defaultUnit\": \"3497479\",\n" +
                "    \"headerAlign\": \"L\",\n" +
                "    \"globalOffer\": \"0.000000000\",\n" +
                "    \"showUnitIn\": \"unitamountcolumn\",\n" +
                "    \"orderIdent\": \"\",\n" +
                "    \"useDoubleVat\": \"N\",\n" +
                "    \"paymedium_other\": \"\",\n" +
                "    \"taxesAmountDetails\": \"a:1:{i:3497473;s:13:\\\"350.000000000\\\";}\",\n" +
                "    \"useServiceDates\": \"N\",\n" +
                "    \"contactName\": \"\",\n" +
                "    \"canDocOnceLine\": \"Y\",\n" +
                "    \"hideSignatureOnDelivery\": \"N\",\n" +
                "    \"addPdfToEmail\": \"Y\",\n" +
                "    \"paydate_endmonth\": \"N\",\n" +
                "    \"allowPayPalPay\": \"N\",\n" +
                "    \"useSellsyMailPopupOnThirdPeople\": \"Y\",\n" +
                "    \"packagingsAmount\": \"0.000000000\",\n" +
                "    \"thirdaddressid\": \"88104032\",\n" +
                "    \"name\": \"Facture\",\n" +
                "    \"shippingNbParcels\": \"0\",\n" +
                "    \"relateds_amount\": \"0.000000000\",\n" +
                "    \"forceDisplayCheckOrder\": \"Y\",\n" +
                "    \"hidePaymentDate\": \"N\",\n" +
                "    \"ownerFullName\": \"M Etienne MAO\",\n" +
                "    \"shippingTrackingUrl\": \"\",\n" +
                "    \"linkedtype\": \"invoice\",\n" +
                "    \"prefsid\": \"35255587\",\n" +
                "    \"num_name\": \"fr\",\n" +
                "    \"signcoords\": \"\",\n" +
                "    \"hideColumnImage\": \"Y\",\n" +
                "    \"useEcotaxe\": \"Y\",\n" +
                "    \"posId\": \"0\",\n" +
                "    \"display_footer\": \"Y\",\n" +
                "    \"globalDiscountUnit\": \"percent\",\n" +
                "    \"displayIBAN\": \"Y\",\n" +
                "    \"num_decimals\": \",\",\n" +
                "    \"discountPercent\": \"0.000000000\",\n" +
                "    \"showThirdVatOnPdf\": \"N\",\n" +
                "    \"mobile\": \"\",\n" +
                "    \"label\": \"Facture\",\n" +
                "    \"recorded\": \"N\",\n" +
                "    \"formatted_shippingsAmount\": \"0,00 €\",\n" +
                "    \"formatted_totalEcoTaxDisplay\": \"0,00 €\",\n" +
                "    \"totalEcoTaxInc\": \"0.000000000\",\n" +
                "    \"formatted_globalOffer\": \"0,00\",\n" +
                "    \"colorNotes\": \"000000\",\n" +
                "    \"colorHeaderRow\": \"000000\",\n" +
                "    \"recordable\": \"N\",\n" +
                "    \"paycheckorder_text\": \"Service comptabilité\",\n" +
                "    \"thirdVatNum\": \"\",\n" +
                "    \"display_mobile\": \"N\",\n" +
                "    \"hideSignatureOnEstimate\": \"N\",\n" +
                "    \"dueAmount\": \"2100.000000000\",\n" +
                "    \"hideMeansOfPayment\": \"N\",\n" +
                "    \"hideDeliveriesDocs\": \"N\"\n" +
                "  }");
        System.out.println(jsonObject.get("ident"));
    }
}