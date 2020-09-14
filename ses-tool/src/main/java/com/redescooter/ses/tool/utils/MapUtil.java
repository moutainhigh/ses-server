package com.redescooter.ses.tool.utils;


import ch.hsr.geohash.GeoHash;
import com.redescooter.ses.api.common.constant.Constant;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @description: MapUtil
 * @author: Darren
 * @create: 2019/01/21 17:44
 */
public class MapUtil {

	private static double EARTH_RADIUS = 6378.137;// 单位千米

	/**
	 * 角度弧度计算公式 rad:(). <br/>
	 * <p>
	 * 360度=2π π=Math.PI
	 * <p>
	 * x度 = x*π/360 弧度
	 *
	 * @param degree
	 * @return
	 * @author chiwei
	 * @since JDK 1.6
	 */
	private static double getRadian(double degree) {
		return degree * Math.PI / 180.0;
	}

	/**
	 * 依据经纬度计算两点之间的距离 GetDistance:(). <br/>
	 *
	 * @param lat1 1点的纬度
	 * @param lng1 1点的经度
	 * @param lat2 2点的纬度
	 * @param lng2 2点的经度
	 * @return 距离 单位 米
	 * @author chiwei
	 * @since JDK 1.6
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = getRadian(lat1);
		double radLat2 = getRadian(lat2);
		double a = radLat1 - radLat2;// 两点纬度差
		double b = getRadian(lng1) - getRadian(lng2);// 两点的经度差
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
				* Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return s * 1000;
	}

	public static double getDistance(String lat1, String lng1, String lat2, String lng2) {
		double lat1Double = StringUtils.isBlank(lat1) ? 0 : Double.parseDouble(lat1);
		double lng1Double = StringUtils.isBlank(lat1) ? 0 : Double.parseDouble(lng1);
		double lat2Double = StringUtils.isBlank(lat1) ? 0 : Double.parseDouble(lat2);
		double lng2Double = StringUtils.isBlank(lat1) ? 0 : Double.parseDouble(lng2);
		return getDistance(lat1Double, lng1Double, lat2Double, lng2Double);
	}

	/**
	 * 根据经纬度 计算geoHash
	 * 注意：经纬度 写反了 会出现geo算法越界
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public static String geoHash(String longitude, String latitude) {
		double longItude = Double.parseDouble(longitude);
		double latItude = Double.parseDouble(latitude);
		GeoHash userGeohash = GeoHash.withCharacterPrecision(latItude, longItude, 6);
		String geoHash = userGeohash.toBase32();
		return geoHash;
	}

    /**
     * @Title: randomLonLat @Description: 在矩形内随机生成经纬度
     * @return BigDecimal
     *
     */
    public static BigDecimal randomLonLat(String type) {
        Random random = new Random();

        Double maxlng = Double.valueOf(Constant.maxlng);
        Double minlng = Double.valueOf(Constant.minlng);
        Double minlat = Double.valueOf(Constant.minlat);
        Double maxlat = Double.valueOf(Constant.maxlat);
        // , double MaxLon, double MinLat, double MaxLat,
        BigDecimal db = new BigDecimal(Math.random() * (maxlng - minlng) + minlng);
        BigDecimal lon = db.setScale(Constant.precision, BigDecimal.ROUND_HALF_UP);// 小数后6位
        db = new BigDecimal(Math.random() * (maxlat - minlat) + minlat);
        BigDecimal lat = db.setScale(Constant.precision, BigDecimal.ROUND_HALF_UP);

        if (StringUtils.equals(type, Constant.lng)) {
            if (lon.intValue() == 0) {
                return Constant.LONGITUDE;
            }
            return lon;
        }
        if (lat.intValue() == 0) {
            return Constant.LATITUDE;
        }
        return lat;
	}
}
