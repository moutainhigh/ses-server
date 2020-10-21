-- MySQL dump 10.14  Distrib 5.5.65-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: corporate
-- ------------------------------------------------------
-- Server version	5.7.31-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cor_delivery`
--

DROP TABLE IF EXISTS `cor_delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_delivery` (
  `id` bigint(30) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `order_no` varchar(32) DEFAULT NULL COMMENT '订单号',
  `tenant_id` bigint(11) DEFAULT NULL COMMENT '租户ID',
  `deliverer_id` bigint(11) DEFAULT NULL COMMENT '交付人,即司机账号的USER_ID字段的值',
  `scooter_id` bigint(11) DEFAULT NULL COMMENT 'scooterId',
  `recipient` varchar(32) DEFAULT NULL COMMENT '收件人',
  `recipient_email` varchar(64) DEFAULT NULL COMMENT '收件人邮箱',
  `country_code` varchar(64) DEFAULT NULL COMMENT '手机国家区号',
  `recipient_tel` varchar(32) DEFAULT NULL COMMENT '收件人电话',
  `recipient_address` varchar(256) DEFAULT NULL COMMENT '收件人地址',
  `longitude` decimal(32,10) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32,10) DEFAULT NULL COMMENT '纬度',
  `geohash` varchar(32) DEFAULT NULL COMMENT 'GeoHash',
  `house_info` varchar(32) DEFAULT NULL COMMENT '门牌信息',
  `parcel_quantity` int(11) DEFAULT NULL COMMENT '包裹数量',
  `goods_inventory` varchar(128) DEFAULT NULL COMMENT '商品清单',
  `result` varchar(32) DEFAULT NULL COMMENT '订单服务结果  按时、延迟、取消  ONTIME、DELAY、CANCEl',
  `status` varchar(32) DEFAULT NULL COMMENT '订单状态:PENDING待配送，  DELIVERING正在配送，REJECTED拒绝，TIMEOUT配送超时，COMPLETE已送达，CANCEL取消失败；PC订单状态显示:待配送Pending,配送中In  progress,拒单Declined,配送超时Overdue,超时完成Delayed,已送达Delivered,取消订单Cancelled',
  `planned_time` varchar(24) DEFAULT NULL COMMENT '安排配送时间',
  `timeout_expectde` varchar(24) DEFAULT NULL COMMENT '超时预警值，单位分钟',
  `time_out` datetime DEFAULT NULL COMMENT '超时预警时间',
  `etd` datetime DEFAULT NULL COMMENT '预计开始时间',
  `atd` datetime DEFAULT NULL COMMENT '实际开始时间',
  `eta` datetime DEFAULT NULL COMMENT '预计送达时间',
  `ata` datetime DEFAULT NULL COMMENT '实际送达时间',
  `driven_mileage` decimal(32,10) DEFAULT NULL COMMENT '骑行里程',
  `driven_duration` int(11) DEFAULT NULL COMMENT '骑行时长',
  `co2` decimal(32,10) DEFAULT NULL COMMENT '二氧化碳排放量',
  `savings` decimal(32,10) DEFAULT NULL COMMENT '节省金额',
  `statistics` varchar(32) DEFAULT '0' COMMENT '0 标识未统计，1 标识 已统计',
  `label` varchar(32) DEFAULT NULL COMMENT '标签',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='递送单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_delivery_trace`
--

DROP TABLE IF EXISTS `cor_delivery_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_delivery_trace` (
  `id` bigint(25) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `delivery_id` bigint(20) DEFAULT NULL COMMENT 'deliveryId',
  `tenant_id` bigint(11) DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(11) DEFAULT NULL COMMENT 'userId,即delivery表中的交付人id同时是司机的user_id',
  `status` varchar(64) DEFAULT NULL COMMENT '订单状态',
  `event` varchar(32) DEFAULT NULL COMMENT '事件',
  `reason` varchar(128) DEFAULT NULL COMMENT '订单取消时，存储取消原因；订单拒绝时，存储拒绝原因；',
  `event_time` datetime DEFAULT NULL COMMENT '事件时间',
  `longitude` decimal(32,10) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32,10) DEFAULT NULL COMMENT '维度',
  `geohash` varchar(32) DEFAULT NULL COMMENT 'GEOHASH',
  `scooter_id` bigint(32) DEFAULT NULL COMMENT 'scooterId',
  `scooter_longitude` decimal(32,10) DEFAULT NULL COMMENT 'scooter经度',
  `scooter_latitude` decimal(32,10) DEFAULT NULL COMMENT 'scooter维度',
  `scooter_location_geohash` varchar(32) DEFAULT NULL COMMENT 'scooterGEOHASH',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Delivery操作跟踪表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_driver`
--

DROP TABLE IF EXISTS `cor_driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_driver` (
  `id` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(11) DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL COMMENT 'USER_ID',
  `driving_license` varchar(256) DEFAULT NULL COMMENT '驾照',
  `driver_license_level` varchar(64) DEFAULT NULL COMMENT '驾照等级',
  `score` int(11) DEFAULT '3' COMMENT '评分',
  `status` varchar(32) DEFAULT NULL COMMENT '状态 VACATION 休假，WORKING 上班中OFFWORK 下班中',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='司机';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_driver_ride_stat`
--

DROP TABLE IF EXISTS `cor_driver_ride_stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_driver_ride_stat` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `driver_id` bigint(20) NOT NULL COMMENT '司机主键',
  `total_duration` bigint(20) DEFAULT NULL COMMENT '总时长',
  `co2_total` decimal(32,4) DEFAULT NULL COMMENT '二氧化碳总量',
  `co2_increment` decimal(32,4) DEFAULT NULL COMMENT '二氧化碳增量值',
  `saved_money` decimal(32,4) DEFAULT NULL COMMENT '节省金额',
  `svg_speed` decimal(32,2) DEFAULT NULL COMMENT '平均时速',
  `read_time` datetime DEFAULT NULL COMMENT '读取时间',
  `total_mileage` decimal(32,2) DEFAULT NULL COMMENT '总公里数',
  `first_ride_time` datetime DEFAULT NULL COMMENT '首次骑行时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='司机骑行统计数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_driver_ride_stat_detail`
--

DROP TABLE IF EXISTS `cor_driver_ride_stat_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_driver_ride_stat_detail` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `biz_type` varchar(16) DEFAULT NULL COMMENT '业务类型',
  `biz_id` bigint(20) DEFAULT NULL COMMENT '业务id',
  `driver_id` bigint(20) NOT NULL COMMENT '司机主键',
  `duration` bigint(20) DEFAULT NULL COMMENT '本次时长',
  `co2_history_total` decimal(32,4) DEFAULT NULL COMMENT 'co2 历史总量',
  `co2_increment` decimal(32,4) DEFAULT NULL COMMENT '二氧化碳增量',
  `svg_speed` decimal(32,4) DEFAULT NULL COMMENT '本次平均时速',
  `mileage` decimal(32,4) DEFAULT NULL COMMENT '本次总公里数',
  `saved_money` decimal(32,4) DEFAULT NULL COMMENT '本次节省金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='司机骑行统计数据明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_driver_scooter`
--

DROP TABLE IF EXISTS `cor_driver_scooter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_driver_scooter` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(11) DEFAULT NULL COMMENT '租户ID',
  `driver_id` bigint(11) DEFAULT NULL COMMENT '司机ID',
  `scooter_id` bigint(11) DEFAULT NULL COMMENT '车辆ID',
  `begin_time` datetime DEFAULT NULL COMMENT '车辆分配开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '车辆归还时间',
  `status` varchar(32) DEFAULT NULL COMMENT '状态 1 使用中，2 已还车',
  `mileage` varchar(255) DEFAULT NULL COMMENT '行驶里程',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='司机分配车辆表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_driver_scooter_history`
--

DROP TABLE IF EXISTS `cor_driver_scooter_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_driver_scooter_history` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(11) DEFAULT NULL COMMENT '租户ID',
  `driver_id` bigint(11) DEFAULT NULL COMMENT '司机ID',
  `scooter_id` bigint(11) DEFAULT NULL COMMENT '车辆ID',
  `begin_time` datetime DEFAULT NULL COMMENT '车辆分配开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '车辆归还时间',
  `status` varchar(32) DEFAULT NULL COMMENT '状态 1 使用中，2 已还车',
  `mileage` varchar(255) DEFAULT NULL COMMENT '行驶里程',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='司机分配车辆表历史记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_express_delivery`
--

DROP TABLE IF EXISTS `cor_express_delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_express_delivery` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户表',
  `scooter_Id` bigint(20) DEFAULT NULL,
  `status` varchar(32) NOT NULL COMMENT '状态，WAITING 待配送 SHIPPING 正在配送 COMPLETED 已配送 REFUSED 失败',
  `result` varchar(32) DEFAULT NULL COMMENT '订单服务结果',
  `driver_id` bigint(20) DEFAULT NULL COMMENT '司机ID',
  `order_sum` int(11) NOT NULL DEFAULT '0' COMMENT '订单总数',
  `order_complete_num` int(11) NOT NULL DEFAULT '0' COMMENT '订单完成数',
  `delivery_date` date DEFAULT NULL COMMENT '排单日期',
  `delivery_start_time` datetime DEFAULT NULL COMMENT '开始配送时间',
  `delivery_end_time` datetime DEFAULT NULL COMMENT '结束配送时间',
  `driven_mileage` decimal(32,10) NOT NULL DEFAULT '0.0000000000' COMMENT '行驶里程数',
  `driven_duration` int(11) DEFAULT '0' COMMENT '行驶时长',
  `co2` decimal(32,10) DEFAULT '0.0000000000' COMMENT '二氧化碳排放量',
  `savings` decimal(32,10) DEFAULT '0.0000000000' COMMENT '节省金额',
  `create_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快递递送单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_express_delivery_detail`
--

DROP TABLE IF EXISTS `cor_express_delivery_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_express_delivery_detail` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户id',
  `express_delivery_id` bigint(20) NOT NULL COMMENT '递送单主键',
  `express_order_id` bigint(20) NOT NULL COMMENT '订单主键',
  `status` varchar(64) NOT NULL COMMENT 'delivery子订单状态',
  `parcel_quantity` int(11) NOT NULL DEFAULT '1' COMMENT '包裹数',
  `etd` datetime DEFAULT NULL COMMENT '预计开始时间  快递业务没有 暂时做冗余字段',
  `ata` datetime DEFAULT NULL COMMENT '实际到达时间',
  `atd` datetime DEFAULT NULL COMMENT '实际开始时间',
  `eta` datetime DEFAULT NULL COMMENT '预计送达时间',
  `priority_sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序优先级，多个订单分配同一个配送单，所需先后顺序',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快递订单子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_express_order`
--

DROP TABLE IF EXISTS `cor_express_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_express_order` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户id',
  `batch_no` varchar(32) NOT NULL COMMENT '批次号',
  `order_no` varchar(32) NOT NULL COMMENT '订单号',
  `status` varchar(32) NOT NULL COMMENT '状态，待分配UNASGN,已分配ASGN，已完成COMPLETE',
  `recipient_country` varchar(64) DEFAULT NULL COMMENT '收货方国家',
  `recipient_province` varchar(64) DEFAULT NULL COMMENT '收件方省份',
  `recipient_city` varchar(64) DEFAULT NULL COMMENT '收件方城市',
  `recipient_postcode` varchar(32) NOT NULL COMMENT '收件方邮编',
  `recipient_address` varchar(255) DEFAULT NULL COMMENT '收件方详细地址',
  `recipient_latitude` decimal(32,10) DEFAULT '0.0000000000' COMMENT '收件方纬度',
  `recipient_longitude` decimal(32,10) DEFAULT '0.0000000000' COMMENT '收货方经度',
  `recipient_geohash` varchar(32) DEFAULT NULL COMMENT 'GeoHash',
  `customer_reference` varchar(64) DEFAULT NULL COMMENT '客户id，等价于收件人名称',
  `recipient_name` varchar(64) DEFAULT NULL COMMENT '收件人',
  `recipient_company` varchar(128) DEFAULT NULL COMMENT '收件方公司，个人时为空',
  `recipient_phone` varchar(16) DEFAULT NULL COMMENT '收件人电话',
  `recipient_mail` varchar(64) DEFAULT NULL COMMENT '收件方邮箱',
  `recipient_notes` varchar(255) DEFAULT NULL COMMENT '收件方备注',
  `sender_country` varchar(64) DEFAULT NULL COMMENT '发货方国家',
  `sender_city` varchar(64) DEFAULT NULL COMMENT '发货方城市',
  `sender_province` varchar(64) DEFAULT NULL COMMENT '发货方省份',
  `sender_postcode` varchar(32) DEFAULT NULL COMMENT '发货方邮编',
  `sender_address` varchar(255) DEFAULT NULL COMMENT '发货方详细地址',
  `sender_latitude` decimal(32,10) DEFAULT '0.0000000000' COMMENT '发货方纬度',
  `sender_longitude` decimal(32,10) DEFAULT '0.0000000000' COMMENT '发货方经度',
  `sender_geohash` varchar(32) DEFAULT NULL COMMENT 'Geohash',
  `sender_company` varchar(64) DEFAULT NULL COMMENT '发货方公司，个人时为空',
  `sender_name` varchar(64) DEFAULT NULL COMMENT '发货人',
  `sender_phone` varchar(16) DEFAULT NULL COMMENT '发货人手机',
  `sender_mail` varchar(64) DEFAULT NULL COMMENT '发货人邮箱',
  `sender_notes` varchar(255) DEFAULT NULL COMMENT '发货方备注',
  `parcel_quantity` int(11) DEFAULT '1' COMMENT '包裹数',
  `assign_flag` tinyint(1) DEFAULT '0' COMMENT '是否已分配',
  `assign_time` datetime DEFAULT NULL COMMENT '分配递送时间',
  `vehicle_type` varchar(32) DEFAULT NULL COMMENT '车辆类型',
  `expect_time_begin` datetime DEFAULT NULL COMMENT '期望送达时间,注意这是时间段',
  `expect_time_end` datetime DEFAULT NULL COMMENT '期望送达时间,注意这是时间段',
  `general_notes` varchar(255) DEFAULT NULL COMMENT '其他备注',
  `denial_reason` varchar(255) DEFAULT NULL COMMENT '拒绝原因',
  `delivered_time` datetime DEFAULT NULL COMMENT '订单完成时间',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快递订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_express_order_trace`
--

DROP TABLE IF EXISTS `cor_express_order_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_express_order_trace` (
  `id` bigint(25) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `express_delivery_id` bigint(20) DEFAULT NULL COMMENT 'deliveryId',
  `tenant_id` bigint(11) DEFAULT NULL COMMENT '租户ID',
  `express_order_id` bigint(11) DEFAULT NULL COMMENT '完成时订单id',
  `driver_id` bigint(11) DEFAULT NULL COMMENT '交付人id',
  `status` varchar(64) DEFAULT NULL COMMENT '订单状态',
  `event` varchar(32) DEFAULT NULL COMMENT '事件',
  `reason` varchar(128) DEFAULT NULL COMMENT '订单取消时，存储取消原因；订单拒绝时，存储拒绝原因；',
  `event_time` datetime DEFAULT NULL COMMENT '事件时间',
  `longitude` decimal(32,10) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32,10) DEFAULT NULL COMMENT '维度',
  `geohash` varchar(32) DEFAULT NULL COMMENT 'GEOHASH',
  `scooter_id` bigint(32) DEFAULT NULL COMMENT 'scooterId',
  `scooter_longitude` decimal(32,10) DEFAULT NULL COMMENT 'scooter经度',
  `scooter_latitude` decimal(32,10) DEFAULT NULL COMMENT 'scooter维度',
  `scooter_geohash` varchar(32) DEFAULT NULL COMMENT 'scooterGEOHASH',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快递订单操作跟踪表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_rfid`
--

DROP TABLE IF EXISTS `cor_rfid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_rfid` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `sn` varchar(32) DEFAULT NULL COMMENT '序列号',
  `secret_key` varchar(32) DEFAULT NULL COMMENT '秘钥',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '门店Id',
  `scooter_id` bigint(20) DEFAULT NULL COMMENT '已分配：allocation已写卡：available已失效：unavailable（暂时不要）空闲：FREE',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `status` varchar(32) DEFAULT NULL COMMENT '已分配：allocation已写卡：available已失效：unavailable（暂时不要）空闲：FREE',
  `created_by` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(11) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='RFID表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_scooter_ride_stat`
--

DROP TABLE IF EXISTS `cor_scooter_ride_stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_scooter_ride_stat` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `scooter_id` bigint(20) NOT NULL COMMENT '车辆主键',
  `total_duration` bigint(20) DEFAULT NULL COMMENT '总时长',
  `co2_total` decimal(32,4) DEFAULT NULL COMMENT '二氧化碳总量',
  `co2_increment` decimal(32,4) DEFAULT NULL COMMENT '二氧化碳增量值',
  `saved_money` decimal(32,4) DEFAULT NULL COMMENT '节省金额',
  `svg_speed` decimal(32,4) DEFAULT NULL COMMENT '平均时速',
  `read_time` datetime DEFAULT NULL COMMENT '读取时间',
  `total_mileage` decimal(32,4) DEFAULT NULL COMMENT '总公里数',
  `first_ride_time` datetime DEFAULT NULL COMMENT '首次骑行时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆骑行统计数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_scooter_ride_stat_detail`
--

DROP TABLE IF EXISTS `cor_scooter_ride_stat_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_scooter_ride_stat_detail` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `biz_type` varchar(16) DEFAULT NULL COMMENT '业务类型',
  `biz_id` bigint(20) DEFAULT NULL COMMENT '业务id',
  `scooter_id` bigint(20) NOT NULL COMMENT '车辆主键',
  `duration` bigint(20) DEFAULT NULL COMMENT '本次时长',
  `co2_history_total` decimal(32,4) DEFAULT NULL COMMENT 'co2 历史总量',
  `co2_increment` decimal(32,4) DEFAULT NULL COMMENT '二氧化碳增量',
  `svg_speed` decimal(32,4) DEFAULT NULL COMMENT '本次平均时速',
  `mileage` decimal(32,4) DEFAULT NULL COMMENT '本次总公里数',
  `saved_money` decimal(32,4) DEFAULT NULL COMMENT '本次节省金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆骑行统计数据明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_tenant_scooter`
--

DROP TABLE IF EXISTS `cor_tenant_scooter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_tenant_scooter` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT 'tenantId',
  `model` varchar(255) DEFAULT NULL COMMENT '车辆型号',
  `scooter_id` bigint(32) DEFAULT NULL COMMENT '车辆ID',
  `longitule` decimal(32,10) DEFAULT NULL COMMENT '车辆经度',
  `latitude` decimal(32,10) DEFAULT NULL COMMENT '车辆纬度',
  `license_plate` varchar(16) DEFAULT NULL COMMENT '车牌号',
  `license_plate_picture` varchar(128) DEFAULT NULL COMMENT '车牌号图片',
  `status` varchar(32) NOT NULL DEFAULT 'AVAILABLE' COMMENT '车辆状态 AVAILABLE;CHARGING;REPAIR;FAULT;USEING',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租户车辆表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cor_user_profile`
--

DROP TABLE IF EXISTS `cor_user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cor_user_profile` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(11) DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `is_master` int(11) DEFAULT '0' COMMENT '是否主账号:1-主账号,0-子账号',
  `picture` varchar(2048) DEFAULT NULL COMMENT '照片',
  `first_name` varchar(32) DEFAULT NULL COMMENT '名',
  `last_name` varchar(32) DEFAULT NULL COMMENT '姓',
  `full_name` varchar(32) DEFAULT NULL COMMENT '全名',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `email_1` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `email_2` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `email_3` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `tel_number_1` varchar(32) DEFAULT NULL COMMENT '电话号',
  `country_code_1` varchar(64) DEFAULT NULL COMMENT '国家编码如+86',
  `tel_number_2` varchar(32) DEFAULT NULL COMMENT '电话号',
  `country_code_2` varchar(64) DEFAULT NULL COMMENT '国家编码如+86',
  `tel_number_3` varchar(32) DEFAULT NULL COMMENT '电话号',
  `country_code_3` varchar(64) DEFAULT NULL COMMENT '国家编码如+86',
  `gender` varchar(32) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `CERTIFICATE_TYPE` varchar(32) DEFAULT NULL COMMENT '证件类型1身份证，2驾驶证，3护照',
  `CERTIFICATE_NEGATIVE_ANNEX` varchar(128) DEFAULT NULL COMMENT '证件反面图片',
  `CERTIFICATE_POSITIVE_ANNEX` varchar(128) DEFAULT NULL COMMENT '证件正面图片',
  `role` varchar(32) DEFAULT NULL COMMENT '角色 DRIVER-司机；MANAGE-管理者',
  `page_boot_tips` tinyint(1) DEFAULT '1' COMMENT '页面引导提示开关，默认true 打开 0关闭 1开启',
  `place_birth` varchar(255) DEFAULT NULL COMMENT '出生地',
  `join_date` date DEFAULT NULL COMMENT '加入日期',
  `time_zone` varchar(16) DEFAULT NULL COMMENT '时区',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-20  7:36:25
