package com.redescooter.ses.tool.utils;

import java.util.Date;
import java.util.UUID;

/**
 * @description: DeliveryCode
 * @author: Alex
 * @create: 2019/03/08 17:08
 */
public class DeliveryCode {
    /**随机生成23订单号  001 0001 20190308 18238123
     * 001门店编号 0001员工编号 20190308日期 18238123 10位随机数
     * @param tenantId
     * @param userId
     * @return
     */
    public static String getDeliveryCode(String tenantId, String userId) {

         StringBuilder tenantCode=new StringBuilder(tenantId);
         StringBuilder userCode=new StringBuilder(userId);
        int tenantCount=0;
        int userCount=0;
        //tenant是否是三位数 不是得话用0补到三位数
       switch (tenantId.length()){
           case 1:
               tenantCount=2;
               break;
           case 2:
               tenantCount=1;
               break;
       }
        for (int i=0;i<tenantCount;i++){
            tenantCode.insert(0,"0");
        }
        //userId 同上
        switch (userId.length()){
            case 1:
                userCount=3;
                break;
            case 2:
                userCount=2;
                break;
            case 3:
                userCount=1;
                break;
        }
        for (int i=0;i<userCount;i++){
            userCode.insert(0,"0");
        }
        Date data = new Date();
        String date = new java.sql.Date(data.getTime()).toString();
        String year = date.substring(0, 4);
        String mouth = date.substring(5, 7);
        String day = date.substring(8, 10);
        int hashCodeV = UUID.randomUUID().toString().hashCode();

        if (hashCodeV < 0) {
            //有可能是负数

            hashCodeV = -hashCodeV;

        }
        String deliveryId=tenantCode.toString()+userCode.toString()+year+mouth+day+String.format("%6d", hashCodeV);
        return deliveryId;
    }

    public static void main(String[] args){
    System.out.print(DeliveryCode.getDeliveryCode("1","1"));
    }
}