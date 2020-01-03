package com.redescooter.ses.service.foundation.service.impl;

import com.redescooter.ses.api.foundation.service.base.GenerateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description: GenerateServiceImpl
 * author: jerry.li
 * create: 2019-05-17 00:44
 */
@Slf4j
@Service
public class GenerateServiceImpl implements GenerateService {

    private static final char[] SEED = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final char[] SEED2 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};


    private static final String STR_FORMAT = "00000";

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public synchronized String getOrderNo() {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        String time = sdf.format(currentDate);
        String date = time.substring(0, 6);
        String hour = time.substring(6, 8);
        String minute = time.substring(8, 10);
        StringBuilder sb = new StringBuilder();
        sb.append(SEED[Integer.parseInt(hour)]);
        sb.append(SEED[Integer.parseInt(minute) / 2]);
        sb.append(date);
        DecimalFormat df = new DecimalFormat(STR_FORMAT);
        for (int i = 0; i < 10; i++) {

            int randomNumber = RandomUtils.nextInt(0, 100000);
            String randomStr = df.format(randomNumber);
            sb.append(randomStr);
            if (jedisCluster.hsetnx("ORDER_NO", sb.toString(), "") == 1) {
                break;
            }
            if (sb.length() > 8) {
                sb.delete(8, 13);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        String time = sdf.format(currentDate);
        String date = time.substring(0, 6);
        String hour = time.substring(6, 8);
        String minute = time.substring(8, 10);
        StringBuilder sb = new StringBuilder();
        sb.append(SEED[Integer.parseInt(hour)]);
        sb.append(SEED[Integer.parseInt(minute) / 2]);
        sb.append(date);
        DecimalFormat df = new DecimalFormat(STR_FORMAT);
        int j = 0;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmss");
        System.out.println("start time : " + sdf1.format(currentDate));
        for (int i = 0; i < 500000; i++) {
            int randomNumber = RandomUtils.nextInt(0, 100000);
            String randomStr = df.format(randomNumber);
            sb.append(randomStr);
            if (list.contains(sb.toString())) {
                j++;
                System.out.println("-----" + j + "=" + sb);
            } else {
                list.add(sb.toString());
            }
            if (sb.length() > 8) {
                sb.delete(8, 14);
            }
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println("end time : " + sdf1.format(new Date()) + " hs:" + (System.currentTimeMillis() - currentDate.getTime()) + "ms");

    }
}