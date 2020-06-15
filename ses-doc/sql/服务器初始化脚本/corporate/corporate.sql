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

 Date: 14/06/2020 18:36:47
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
INSERT INTO `cor_tenant_scooter` VALUES (1000000, 0, 1008364, '3', 1008392, 48.8628680000, 2.3139600000, 'AA-AAA-AA', NULL, '1', 1023310, '2020-06-02 06:27:59', 1023310, '2020-06-02 06:27:59', NULL, NULL, 0);
INSERT INTO `cor_tenant_scooter` VALUES (1002044, 0, 1006344, '3', 1008394, 48.8628680000, 2.3139600000, '12-345-79', NULL, '1', 1021166, '2020-06-03 03:00:43', 1021166, '2020-06-03 03:00:43', NULL, NULL, 0);
INSERT INTO `cor_tenant_scooter` VALUES (1002045, 0, 1008364, '3', 1008395, 48.8628680000, 2.3139600000, '12-345-67', NULL, '1', 1023310, '2020-06-03 03:11:58', 1023310, '2020-06-03 03:11:58', NULL, NULL, 0);
INSERT INTO `cor_tenant_scooter` VALUES (1004140, 0, 1006345, '3', 1008394, 48.8628680000, 2.3139600000, '12-345-69', NULL, '1', 1021169, '2020-06-03 08:08:43', 1021169, '2020-06-03 08:08:43', NULL, NULL, 0);
INSERT INTO `cor_tenant_scooter` VALUES (1004141, 0, 1006344, '3', 1008395, 48.8628680000, 2.3139600000, '12-453-23', NULL, '1', 1021166, '2020-06-03 08:09:11', 1021166, '2020-06-03 08:09:11', NULL, NULL, 0);
INSERT INTO `cor_tenant_scooter` VALUES (1004171, 0, 1006345, '3', 1020945, 2.3354060000, 48.8652520000, 'AA-001-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '5', 0, '1970-01-01 00:17:45', 1021169, '2020-06-02 03:24:45', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1006258, 0, 1006345, '3', 1020946, 2.3321970000, 48.8666100000, 'AA-002-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '5', 0, '1970-01-01 00:17:45', 1021169, '2020-06-02 02:57:33', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1006259, 0, 1008364, '3', 1020947, 2.3348260000, 48.8662570000, 'AA-003-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '5', 0, '1970-01-01 00:17:49', 1023310, '2020-06-02 06:33:29', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1006264, 0, 1006344, '3', 1008392, 48.8628680000, 2.3139600000, '12-345-60', NULL, '1', 1021166, '2020-06-03 08:15:03', 1021166, '2020-06-03 08:15:03', NULL, NULL, 0);
INSERT INTO `cor_tenant_scooter` VALUES (1008320, 0, 1008364, '3', 1020948, 2.3329360000, 48.8655840000, 'AA-004-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1970-01-01 00:17:49', 1010588, '2020-06-02 06:33:17', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1008321, 0, 0, '3', 1020949, 2.3429170000, 48.8616880000, 'AA-005-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1970-01-01 00:17:49', 1010587, '2020-06-01 05:45:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1008322, 0, 0, '3', 1020950, 2.3420040000, 48.8710150000, 'AA-006-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1010587, '2020-06-01 05:45:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1010353, 0, 0, '3', 1020951, 2.3460490000, 48.8700980000, 'AA-007-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1010586, '2020-05-25 07:59:28', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1010354, 0, 0, '3', 1020952, 2.3477770000, 48.8705330000, 'AA-008-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1000002, '2020-05-25 07:59:28', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1012464, 0, 0, '3', 1020953, 2.3364410000, 48.8693320000, 'AA-009-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1010592, '2020-06-01 05:45:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1012465, 0, 0, '3', 1020954, 2.3275210000, 48.8712400000, 'AA-010-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1010592, '2020-05-25 07:59:28', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1012467, 0, 0, '3', 1020956, 2.3069040000, 48.8328230000, 'AA-011-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1010597, '2020-06-01 05:45:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018680, 0, 0, '3', 1020959, 2.3354060000, 48.8652520000, 'AA-015-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1010597, '2020-06-01 05:45:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018681, 0, 0, '3', 1020960, 2.3354060000, 48.8652520000, 'AA-016-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1010611, '2020-06-01 05:45:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018682, 0, 0, '3', 1020955, 2.3069040000, 48.8328230000, 'AA-012-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1010611, '2020-06-01 05:45:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018683, 0, 0, '3', 1020957, 2.3354060000, 48.8652520000, 'AA-013-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1014818, '2020-05-25 07:59:28', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018684, 0, 0, '3', 1020958, 2.3354060000, 48.8652520000, 'AA-014-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1014818, '2020-05-25 07:59:28', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018685, 0, 0, '3', 1020961, 2.3354060000, 48.8652520000, 'AA-017-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1004234, '2020-05-25 07:59:28', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018686, 0, 0, '3', 1020962, 2.3321970000, 48.8666100000, 'AA-018-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1004234, '2020-05-25 07:59:28', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018687, 0, 0, '3', 1020963, 2.3348260000, 48.8662570000, 'AA-019-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1010625, '2020-06-01 05:45:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018688, 0, 0, '3', 1020964, 2.3329360000, 48.8655840000, 'AA-020-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1004234, '2020-05-25 07:59:28', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018689, 0, 0, '3', 1020965, 2.3429170000, 48.8616880000, 'AA-021-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1010611, '2020-06-01 05:45:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018690, 0, 0, '3', 1020966, 2.3420040000, 48.8710150000, 'AA-022-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1010611, '2020-06-01 05:45:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018691, 0, 0, '3', 1020967, 2.3460490000, 48.8700980000, 'AA-023-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1006420, '2020-02-20 08:28:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018692, 0, 0, '3', 1020968, 2.3477770000, 48.8705330000, 'AA-024-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1006420, '2020-02-20 08:28:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018693, 0, 0, '3', 1020969, 2.3364410000, 48.8693320000, 'AA-025-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1006420, '2020-02-20 08:28:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018694, 0, 0, '3', 1020970, 2.3275210000, 48.8712400000, 'AA-026-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1006420, '2020-02-20 08:28:44', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018695, 0, 0, '3', 1020971, 2.3069040000, 48.8328230000, 'AA-027-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1033718, '2020-02-17 15:45:29', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018696, 0, 0, '3', 1020972, 2.3354060000, 48.8652520000, 'AA-028-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1014818, '2020-02-17 15:45:29', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018697, 0, 0, '3', 1020973, 2.3354060000, 48.8652520000, 'AA-029-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1014818, '2020-02-17 15:45:29', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018698, 0, 0, '3', 1020974, 2.3069040000, 48.8328230000, 'AA-030-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1033718, '2020-02-17 15:45:29', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018699, 0, 0, '3', 1020975, 2.3354060000, 48.8652520000, 'AA-031-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1014818, '2020-02-17 15:45:29', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018700, 0, 0, '3', 1020976, 2.3354060000, 48.8652520000, 'AA-032-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1014818, '2020-02-17 15:45:29', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018701, 0, 0, '3', 1020977, 2.3354060000, 48.8652520000, 'AA-033-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1004234, '2020-02-17 15:39:36', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018702, 0, 0, '3', 1020978, 2.3321970000, 48.8666100000, 'AA-034-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1004234, '2020-02-17 15:39:35', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018703, 0, 0, '3', 1020979, 2.3348260000, 48.8662570000, 'AA-035-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1004234, '2020-02-17 15:45:29', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018704, 0, 0, '3', 1020980, 2.3329360000, 48.8655840000, 'AA-036-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1004234, '2020-02-17 15:45:29', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018705, 0, 0, '3', 1020981, 2.3429170000, 48.8616880000, 'AA-037-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1004234, '2020-02-17 15:45:29', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018706, 0, 0, '3', 1020982, 2.3420040000, 48.8710150000, 'AA-038-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1006420, '2020-02-17 15:45:29', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018707, 0, 0, '3', 1020983, 2.3460490000, 48.8700980000, 'AA-039-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1006420, '2020-02-17 15:45:29', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018708, 0, 0, '3', 1020984, 2.3477770000, 48.8705330000, 'AA-040-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1006420, '2020-02-17 15:39:36', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018709, 0, 0, '3', 1020985, 2.3364410000, 48.8693320000, 'AA-041-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1006420, '2020-02-17 15:39:36', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018710, 0, 0, '3', 1020986, 2.3275210000, 48.8712400000, 'AA-042-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1006420, '2020-02-17 15:45:29', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018712, 0, 0, '3', 1020988, 2.3354060000, 48.8652520000, 'AA-044-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1033720, '2020-02-20 08:22:45', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018713, 0, 0, '3', 1020989, 2.3354060000, 48.8652520000, 'AA-045-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1033720, '2020-02-20 08:28:48', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018714, 0, 0, '3', 1020990, 2.3069040000, 48.8328230000, 'AA-046-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1033720, '2020-02-20 08:28:48', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018715, 0, 0, '3', 1020991, 2.3354060000, 48.8652520000, 'AA-047-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1033720, '2020-02-20 08:28:48', NULL, NULL, NULL);
INSERT INTO `cor_tenant_scooter` VALUES (1018716, 0, 0, '3', 1020992, 2.3354060000, 48.8652520000, 'AA-048-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '2020-01-11 11:34:02', 1033720, '2020-02-20 08:22:45', NULL, NULL, NULL);

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
  `full_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全名',
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
