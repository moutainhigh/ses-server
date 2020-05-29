/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : corporate

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 29/05/2020 17:24:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cor_delivery
-- ----------------------------
DROP TABLE IF EXISTS `cor_delivery`;
CREATE TABLE `cor_delivery`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `deliverer_id` bigint(0) NULL DEFAULT NULL COMMENT '交付人,即司机账号的USER_ID字段的值',
  `scooter_id` bigint(0) NULL DEFAULT NULL COMMENT 'scooterId',
  `recipient` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人',
  `recipient_email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人邮箱',
  `country_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机国家区号',
  `recipient_tel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人电话',
  `recipient_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人地址',
  `longitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '纬度',
  `geohash` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GeoHash',
  `house_info` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门牌信息',
  `parcel_quantity` int(0) NULL DEFAULT NULL COMMENT '包裹数量',
  `goods_inventory` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品清单',
  `result` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单服务结果  按时、延迟、取消  ONTIME、DELAY、CANCEl',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态:PENDING待配送，  DELIVERING正在配送，REJECTED拒绝，TIMEOUT配送超时，COMPLETE已送达，CANCEL取消失败；PC订单状态显示:待配送Pending,配送中In  progress,拒单Declined,配送超时Overdue,超时完成Delayed,已送达Delivered,取消订单Cancelled',
  `planned_time` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安排配送时间',
  `timeout_expectde` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '超时预警值，单位分钟',
  `time_out` datetime(0) NULL DEFAULT NULL COMMENT '超时预警时间',
  `etd` datetime(0) NULL DEFAULT NULL COMMENT '预计开始时间',
  `atd` datetime(0) NULL DEFAULT NULL COMMENT '实际开始时间',
  `eta` datetime(0) NULL DEFAULT NULL COMMENT '预计送达时间',
  `ata` datetime(0) NULL DEFAULT NULL COMMENT '实际送达时间',
  `driven_mileage` decimal(32, 10) NULL DEFAULT NULL COMMENT '骑行里程',
  `driven_duration` int(0) NULL DEFAULT NULL COMMENT '骑行时长',
  `co2` decimal(32, 10) NULL DEFAULT NULL COMMENT '二氧化碳排放量',
  `savings` decimal(32, 10) NULL DEFAULT NULL COMMENT '节省金额',
  `statistics` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0 标识未统计，1 标识 已统计',
  `label` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '递送单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_delivery
-- ----------------------------

-- ----------------------------
-- Table structure for cor_delivery_trace
-- ----------------------------
DROP TABLE IF EXISTS `cor_delivery_trace`;
CREATE TABLE `cor_delivery_trace`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `delivery_id` bigint(0) NULL DEFAULT NULL COMMENT 'deliveryId',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userId,即delivery表中的交付人id同时是司机的user_id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态',
  `event` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件',
  `reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单取消时，存储取消原因；订单拒绝时，存储拒绝原因；',
  `event_time` datetime(0) NULL DEFAULT NULL COMMENT '事件时间',
  `longitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '维度',
  `geohash` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GEOHASH',
  `scooter_id` bigint(0) NULL DEFAULT NULL COMMENT 'scooterId',
  `scooter_longitude` decimal(32, 10) NULL DEFAULT NULL COMMENT 'scooter经度',
  `scooter_latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT 'scooter维度',
  `scooter_location_geohash` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'scooterGEOHASH',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Delivery操作跟踪表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_delivery_trace
-- ----------------------------

-- ----------------------------
-- Table structure for cor_driver
-- ----------------------------
DROP TABLE IF EXISTS `cor_driver`;
CREATE TABLE `cor_driver`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(0) NOT NULL COMMENT 'USER_ID',
  `driving_license` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '驾照',
  `driver_license_level` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '驾照等级',
  `score` int(0) NULL DEFAULT 3 COMMENT '评分',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态 VACATION 休假，WORKING 上班中OFFWORK 下班中',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '司机' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_driver
-- ----------------------------

-- ----------------------------
-- Table structure for cor_driver_ride_stat
-- ----------------------------
DROP TABLE IF EXISTS `cor_driver_ride_stat`;
CREATE TABLE `cor_driver_ride_stat`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID',
  `driver_id` bigint(0) NOT NULL COMMENT '司机主键',
  `total_duration` bigint(0) NULL DEFAULT NULL COMMENT '总时长',
  `co2_total` decimal(32, 4) NULL DEFAULT NULL COMMENT '二氧化碳总量',
  `co2_increment` decimal(32, 4) NULL DEFAULT NULL COMMENT '二氧化碳增量值',
  `saved_money` decimal(32, 4) NULL DEFAULT NULL COMMENT '节省金额',
  `svg_speed` decimal(32, 2) NULL DEFAULT NULL COMMENT '平均时速',
  `read_time` datetime(0) NULL DEFAULT NULL COMMENT '读取时间',
  `total_mileage` decimal(32, 2) NULL DEFAULT NULL COMMENT '总公里数',
  `first_ride_time` datetime(0) NULL DEFAULT NULL COMMENT '首次骑行时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '司机骑行统计数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_driver_ride_stat
-- ----------------------------

-- ----------------------------
-- Table structure for cor_driver_ride_stat_detail
-- ----------------------------
DROP TABLE IF EXISTS `cor_driver_ride_stat_detail`;
CREATE TABLE `cor_driver_ride_stat_detail`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `biz_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `biz_id` bigint(0) NULL DEFAULT NULL COMMENT '业务id',
  `driver_id` bigint(0) NOT NULL COMMENT '司机主键',
  `duration` bigint(0) NULL DEFAULT NULL COMMENT '本次时长',
  `co2_history_total` decimal(32, 4) NULL DEFAULT NULL COMMENT 'co2 历史总量',
  `co2_increment` decimal(32, 4) NULL DEFAULT NULL COMMENT '二氧化碳增量',
  `svg_speed` decimal(32, 4) NULL DEFAULT NULL COMMENT '本次平均时速',
  `mileage` decimal(32, 4) NULL DEFAULT NULL COMMENT '本次总公里数',
  `saved_money` decimal(32, 4) NULL DEFAULT NULL COMMENT '本次节省金额',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '司机骑行统计数据明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_driver_ride_stat_detail
-- ----------------------------

-- ----------------------------
-- Table structure for cor_driver_scooter
-- ----------------------------
DROP TABLE IF EXISTS `cor_driver_scooter`;
CREATE TABLE `cor_driver_scooter`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `driver_id` bigint(0) NULL DEFAULT NULL COMMENT '司机ID',
  `scooter_id` bigint(0) NULL DEFAULT NULL COMMENT '车辆ID',
  `begin_time` datetime(0) NULL DEFAULT NULL COMMENT '车辆分配开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '车辆归还时间',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态 1 使用中，2 已还车',
  `mileage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行驶里程',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '司机分配车辆表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_driver_scooter
-- ----------------------------

-- ----------------------------
-- Table structure for cor_driver_scooter_history
-- ----------------------------
DROP TABLE IF EXISTS `cor_driver_scooter_history`;
CREATE TABLE `cor_driver_scooter_history`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `driver_id` bigint(0) NULL DEFAULT NULL COMMENT '司机ID',
  `scooter_id` bigint(0) NULL DEFAULT NULL COMMENT '车辆ID',
  `begin_time` datetime(0) NULL DEFAULT NULL COMMENT '车辆分配开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '车辆归还时间',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态 1 使用中，2 已还车',
  `mileage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行驶里程',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '司机分配车辆表历史记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_driver_scooter_history
-- ----------------------------

-- ----------------------------
-- Table structure for cor_express_delivery
-- ----------------------------
DROP TABLE IF EXISTS `cor_express_delivery`;
CREATE TABLE `cor_express_delivery`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户表',
  `scooter_Id` bigint(0) NULL DEFAULT NULL,
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态，WAITING 待配送 SHIPPING 正在配送 COMPLETED 已配送 REFUSED 失败',
  `result` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单服务结果',
  `driver_id` bigint(0) NULL DEFAULT NULL COMMENT '司机ID',
  `order_sum` int(0) NOT NULL DEFAULT 0 COMMENT '订单总数',
  `order_complete_num` int(0) NOT NULL DEFAULT 0 COMMENT '订单完成数',
  `delivery_date` date NULL DEFAULT NULL COMMENT '排单日期',
  `delivery_start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始配送时间',
  `delivery_end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束配送时间',
  `driven_mileage` decimal(32, 10) NOT NULL DEFAULT 0.0000000000 COMMENT '行驶里程数',
  `driven_duration` int(0) NULL DEFAULT 0 COMMENT '行驶时长',
  `co2` decimal(32, 10) NULL DEFAULT 0.0000000000 COMMENT '二氧化碳排放量',
  `savings` decimal(32, 10) NULL DEFAULT 0.0000000000 COMMENT '节省金额',
  `create_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '快递递送单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_express_delivery
-- ----------------------------

-- ----------------------------
-- Table structure for cor_express_delivery_detail
-- ----------------------------
DROP TABLE IF EXISTS `cor_express_delivery_detail`;
CREATE TABLE `cor_express_delivery_detail`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户id',
  `express_delivery_id` bigint(0) NOT NULL COMMENT '递送单主键',
  `express_order_id` bigint(0) NOT NULL COMMENT '订单主键',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'delivery子订单状态',
  `parcel_quantity` int(0) NOT NULL DEFAULT 1 COMMENT '包裹数',
  `etd` datetime(0) NULL DEFAULT NULL COMMENT '预计开始时间  快递业务没有 暂时做冗余字段',
  `ata` datetime(0) NULL DEFAULT NULL COMMENT '实际到达时间',
  `atd` datetime(0) NULL DEFAULT NULL COMMENT '实际开始时间',
  `eta` datetime(0) NULL DEFAULT NULL COMMENT '预计送达时间',
  `priority_sort` int(0) NOT NULL DEFAULT 0 COMMENT '排序优先级，多个订单分配同一个配送单，所需先后顺序',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '快递订单子表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_express_delivery_detail
-- ----------------------------

-- ----------------------------
-- Table structure for cor_express_order
-- ----------------------------
DROP TABLE IF EXISTS `cor_express_order`;
CREATE TABLE `cor_express_order`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户id',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '批次号',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态，待分配UNASGN,已分配ASGN，已完成COMPLETE',
  `recipient_country` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货方国家',
  `recipient_province` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件方省份',
  `recipient_city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件方城市',
  `recipient_postcode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收件方邮编',
  `recipient_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件方详细地址',
  `recipient_latitude` decimal(32, 10) NULL DEFAULT 0.0000000000 COMMENT '收件方纬度',
  `recipient_longitude` decimal(32, 10) NULL DEFAULT 0.0000000000 COMMENT '收货方经度',
  `recipient_geohash` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GeoHash',
  `customer_reference` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户id，等价于收件人名称',
  `recipient_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人',
  `recipient_company` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件方公司，个人时为空',
  `recipient_phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人电话',
  `recipient_mail` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件方邮箱',
  `recipient_notes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件方备注',
  `sender_country` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货方国家',
  `sender_city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货方城市',
  `sender_province` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货方省份',
  `sender_postcode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货方邮编',
  `sender_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货方详细地址',
  `sender_latitude` decimal(32, 10) NULL DEFAULT 0.0000000000 COMMENT '发货方纬度',
  `sender_longitude` decimal(32, 10) NULL DEFAULT 0.0000000000 COMMENT '发货方经度',
  `sender_geohash` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Geohash',
  `sender_company` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货方公司，个人时为空',
  `sender_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货人',
  `sender_phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货人手机',
  `sender_mail` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货人邮箱',
  `sender_notes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货方备注',
  `parcel_quantity` int(0) NULL DEFAULT 1 COMMENT '包裹数',
  `assign_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否已分配',
  `assign_time` datetime(0) NULL DEFAULT NULL COMMENT '分配递送时间',
  `vehicle_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车辆类型',
  `expect_time_begin` datetime(0) NULL DEFAULT NULL COMMENT '期望送达时间,注意这是时间段',
  `expect_time_end` datetime(0) NULL DEFAULT NULL COMMENT '期望送达时间,注意这是时间段',
  `general_notes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他备注',
  `denial_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拒绝原因',
  `delivered_time` datetime(0) NULL DEFAULT NULL COMMENT '订单完成时间',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '快递订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_express_order
-- ----------------------------

-- ----------------------------
-- Table structure for cor_express_order_trace
-- ----------------------------
DROP TABLE IF EXISTS `cor_express_order_trace`;
CREATE TABLE `cor_express_order_trace`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `express_delivery_id` bigint(0) NULL DEFAULT NULL COMMENT 'deliveryId',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `express_order_id` bigint(0) NULL DEFAULT NULL COMMENT '完成时订单id',
  `driver_id` bigint(0) NULL DEFAULT NULL COMMENT '交付人id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态',
  `event` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件',
  `reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单取消时，存储取消原因；订单拒绝时，存储拒绝原因；',
  `event_time` datetime(0) NULL DEFAULT NULL COMMENT '事件时间',
  `longitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '维度',
  `geohash` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GEOHASH',
  `scooter_id` bigint(0) NULL DEFAULT NULL COMMENT 'scooterId',
  `scooter_longitude` decimal(32, 10) NULL DEFAULT NULL COMMENT 'scooter经度',
  `scooter_latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT 'scooter维度',
  `scooter_geohash` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'scooterGEOHASH',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '快递订单操作跟踪表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_express_order_trace
-- ----------------------------

-- ----------------------------
-- Table structure for cor_rfid
-- ----------------------------
DROP TABLE IF EXISTS `cor_rfid`;
CREATE TABLE `cor_rfid`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `sn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '序列号',
  `secret_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '秘钥',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '门店Id',
  `scooter_id` bigint(0) NULL DEFAULT NULL COMMENT '已分配：allocation已写卡：available已失效：unavailable（暂时不要）空闲：FREE',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '已分配：allocation已写卡：available已失效：unavailable（暂时不要）空闲：FREE',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'RFID表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_rfid
-- ----------------------------

-- ----------------------------
-- Table structure for cor_scooter_ride_stat
-- ----------------------------
DROP TABLE IF EXISTS `cor_scooter_ride_stat`;
CREATE TABLE `cor_scooter_ride_stat`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID',
  `scooter_id` bigint(0) NOT NULL COMMENT '车辆主键',
  `total_duration` bigint(0) NULL DEFAULT NULL COMMENT '总时长',
  `co2_total` decimal(32, 4) NULL DEFAULT NULL COMMENT '二氧化碳总量',
  `co2_increment` decimal(32, 4) NULL DEFAULT NULL COMMENT '二氧化碳增量值',
  `saved_money` decimal(32, 4) NULL DEFAULT NULL COMMENT '节省金额',
  `svg_speed` decimal(32, 4) NULL DEFAULT NULL COMMENT '平均时速',
  `read_time` datetime(0) NULL DEFAULT NULL COMMENT '读取时间',
  `total_mileage` decimal(32, 4) NULL DEFAULT NULL COMMENT '总公里数',
  `first_ride_time` datetime(0) NULL DEFAULT NULL COMMENT '首次骑行时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '车辆骑行统计数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_scooter_ride_stat
-- ----------------------------

-- ----------------------------
-- Table structure for cor_scooter_ride_stat_detail
-- ----------------------------
DROP TABLE IF EXISTS `cor_scooter_ride_stat_detail`;
CREATE TABLE `cor_scooter_ride_stat_detail`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `biz_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `biz_id` bigint(0) NULL DEFAULT NULL COMMENT '业务id',
  `scooter_id` bigint(0) NOT NULL COMMENT '车辆主键',
  `duration` bigint(0) NULL DEFAULT NULL COMMENT '本次时长',
  `co2_history_total` decimal(32, 4) NULL DEFAULT NULL COMMENT 'co2 历史总量',
  `co2_increment` decimal(32, 4) NULL DEFAULT NULL COMMENT '二氧化碳增量',
  `svg_speed` decimal(32, 4) NULL DEFAULT NULL COMMENT '本次平均时速',
  `mileage` decimal(32, 4) NULL DEFAULT NULL COMMENT '本次总公里数',
  `saved_money` decimal(32, 4) NULL DEFAULT NULL COMMENT '本次节省金额',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '车辆骑行统计数据明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_scooter_ride_stat_detail
-- ----------------------------

-- ----------------------------
-- Table structure for cor_tenant_scooter
-- ----------------------------
DROP TABLE IF EXISTS `cor_tenant_scooter`;
CREATE TABLE `cor_tenant_scooter`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT 'tenantId',
  `model` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车辆型号',
  `scooter_id` bigint(0) NULL DEFAULT NULL COMMENT '车辆ID',
  `longitule` decimal(32, 10) NULL DEFAULT NULL COMMENT '车辆经度',
  `latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '车辆纬度',
  `license_plate` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车牌号',
  `license_plate_picture` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车牌号图片',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'AVAILABLE' COMMENT '车辆状态 AVAILABLE;CHARGING;REPAIR;FAULT;USEING',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '租户车辆表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_tenant_scooter
-- ----------------------------

-- ----------------------------
-- Table structure for cor_user_profile
-- ----------------------------
DROP TABLE IF EXISTS `cor_user_profile`;
CREATE TABLE `cor_user_profile`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `is_master` int(0) NULL DEFAULT 0 COMMENT '是否主账号:1-主账号,0-子账号',
  `picture` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片',
  `first_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名',
  `last_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓',
  `full_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全名',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `email_1` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `email_2` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `email_3` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `tel_number_1` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号',
  `country_code_1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码如+86',
  `tel_number_2` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号',
  `country_code_2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码如+86',
  `tel_number_3` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号',
  `country_code_3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码如+86',
  `gender` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `CERTIFICATE_TYPE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型1身份证，2驾驶证，3护照',
  `CERTIFICATE_NEGATIVE_ANNEX` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件反面图片',
  `CERTIFICATE_POSITIVE_ANNEX` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件正面图片',
  `role` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色 DRIVER-司机；MANAGE-管理者',
  `page_boot_tips` tinyint(1) NULL DEFAULT 1 COMMENT '页面引导提示开关，默认true 打开 0关闭 1开启',
  `place_birth` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生地',
  `join_date` date NULL DEFAULT NULL COMMENT '加入日期',
  `time_zone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时区',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cor_user_profile
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
