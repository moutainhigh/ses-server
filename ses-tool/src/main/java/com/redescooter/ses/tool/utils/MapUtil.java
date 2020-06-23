package com.redescooter.ses.tool.utils;


import ch.hsr.geohash.GeoHash;
import org.apache.commons.lang3.StringUtils;

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

	//    合肥与杭州的经纬度距离计算
	public static void main(String ar[]) {
		System.out.println(getDistance(31.86, 117.27, 30.26, 120.19));
	}
}
