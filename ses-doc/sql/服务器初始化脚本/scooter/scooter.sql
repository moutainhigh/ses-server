/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : scooter

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 29/05/2020 17:25:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sco_scooter
-- ----------------------------
DROP TABLE IF EXISTS `sco_scooter`;
CREATE TABLE `sco_scooter`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车辆编号',
  `picture` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车辆图片',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态:LOCKED;UNLOCKED',
  `total_mileage` bigint(0) NOT NULL DEFAULT 0 COMMENT '总里程',
  `available_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'AVAILABLE' COMMENT 'AVAILABLE;CHARGING;REPAIR;FAULT;USING',
  `box_status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'LOCKED' COMMENT '后备箱状态',
  `model` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '型号',
  `license_plate` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '牌照',
  `license_plate_picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '牌照图片',
  `license_plate_time` datetime(0) NULL DEFAULT NULL COMMENT '上牌时间',
  `scooter_id_picture` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SCOOTER_ID图片',
  `insure_time` datetime(0) NULL DEFAULT NULL COMMENT '投保时间',
  `insurance` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保险',
  `revision` int(0) NULL DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '摩托车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_scooter
-- ----------------------------
INSERT INTO `sco_scooter` VALUES (1002161, 0, 'RE-01', NULL, '1', 0, '1', '1', '3', 'AA-001-AA', NULL, '2020-05-06 07:25:36', NULL, '2020-05-06 07:25:36', NULL, 0, 1000006, '2020-05-06 07:25:36', 1000006, '2020-05-06 07:25:36', NULL, NULL, NULL, 0);
INSERT INTO `sco_scooter` VALUES (1002162, 0, 'RE-02', NULL, '1', 0, '1', '1', '3', 'AA-002-AA', NULL, '2020-05-06 07:26:17', NULL, '2020-05-06 07:26:17', NULL, 0, 1000006, '2020-05-06 07:26:17', 1000006, '2020-05-06 07:26:17', NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sco_scooter_action_trace
-- ----------------------------
DROP TABLE IF EXISTS `sco_scooter_action_trace`;
CREATE TABLE `sco_scooter_action_trace`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '司机ID',
  `scooter_id` bigint(0) NULL DEFAULT NULL COMMENT '车辆ID',
  `user_longitule` decimal(32, 10) NULL DEFAULT NULL COMMENT '用户经度',
  `user_latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '用户纬度',
  `user_location_geohash` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户GeoHash',
  `action_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型 1 开锁 2 关锁 3 开导航 4 结束导航',
  `action_result` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作结果 1 成功 0 失败',
  `action_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `battery` int(0) NULL DEFAULT NULL COMMENT '电池电量',
  `longitule` decimal(32, 10) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '纬度',
  `geohash` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GeoHash',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '车辆操作记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_scooter_action_trace
-- ----------------------------

-- ----------------------------
-- Table structure for sco_scooter_charge
-- ----------------------------
DROP TABLE IF EXISTS `sco_scooter_charge`;
CREATE TABLE `sco_scooter_charge`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_id` bigint(0) NULL DEFAULT NULL COMMENT '电动车ID',
  `begin_time` datetime(0) NULL DEFAULT NULL COMMENT '充电开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '充电结束时间',
  `charged_duration` bigint(0) NULL DEFAULT NULL COMMENT '充电时长',
  `begin_battery` int(0) NULL DEFAULT NULL COMMENT '开始充电电量',
  `end_battery` int(0) NULL DEFAULT NULL COMMENT '结束充电电量',
  `charged_battery` decimal(32, 10) NULL DEFAULT NULL COMMENT '已充电量',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '车辆充电表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_scooter_charge
-- ----------------------------

-- ----------------------------
-- Table structure for sco_scooter_ecu
-- ----------------------------
DROP TABLE IF EXISTS `sco_scooter_ecu`;
CREATE TABLE `sco_scooter_ecu`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `ecu_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备号',
  `scooter_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '摩托车ID',
  `iccid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SIM卡ICCID',
  `sim_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SIM卡卡号',
  `revision` int(0) NULL DEFAULT NULL COMMENT '乐观锁',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '智能控制系统' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_scooter_ecu
-- ----------------------------

-- ----------------------------
-- Table structure for sco_scooter_mcu
-- ----------------------------
DROP TABLE IF EXISTS `sco_scooter_mcu`;
CREATE TABLE `sco_scooter_mcu`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `mcu_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '控制器序列号',
  `scooter_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '摩托车ID',
  `version_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本号',
  `model` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '型号',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `revision` int(0) NULL DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '控制器 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_scooter_mcu
-- ----------------------------

-- ----------------------------
-- Table structure for sco_scooter_navigation
-- ----------------------------
DROP TABLE IF EXISTS `sco_scooter_navigation`;
CREATE TABLE `sco_scooter_navigation`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_id` bigint(0) NOT NULL COMMENT '车辆ID',
  `scooter_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车辆编号',
  `destination` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目的地',
  `destination_longitude` decimal(32, 10) NOT NULL COMMENT '目的地经度',
  `destination_latitude` decimal(32, 10) NOT NULL COMMENT '目的地纬度',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'NORMAL' COMMENT '状态 NORMAL;PROCESSING;COMPLETED;',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '导航表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_scooter_navigation
-- ----------------------------

-- ----------------------------
-- Table structure for sco_scooter_obd
-- ----------------------------
DROP TABLE IF EXISTS `sco_scooter_obd`;
CREATE TABLE `sco_scooter_obd`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_id` bigint(0) NOT NULL COMMENT '摩托车ID',
  `scooter_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '摩托车编号',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'NORMAL' COMMENT '状态 NORMAL;PROCESSING;COMPLETED',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '摩托车OBD检测' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_scooter_obd
-- ----------------------------

-- ----------------------------
-- Table structure for sco_scooter_obd_detail
-- ----------------------------
DROP TABLE IF EXISTS `sco_scooter_obd_detail`;
CREATE TABLE `sco_scooter_obd_detail`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_obd_id` bigint(0) NOT NULL COMMENT 'obd ID',
  `check_items` int(0) NOT NULL COMMENT '检查项',
  `check_result` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '检查结果',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '摩托车OBD详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_scooter_obd_detail
-- ----------------------------

-- ----------------------------
-- Table structure for sco_scooter_repair
-- ----------------------------
DROP TABLE IF EXISTS `sco_scooter_repair`;
CREATE TABLE `sco_scooter_repair`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_id` bigint(0) NULL DEFAULT NULL COMMENT '车辆ID',
  `status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态 PLANNED,REPAIRING,WAIT_PAYMENT,FINISH',
  `booking_time` datetime(0) NULL DEFAULT NULL COMMENT '预约时间',
  `repair_start_time` datetime(0) NULL DEFAULT NULL COMMENT '维修开始时间',
  `repair_finish_time` datetime(0) NULL DEFAULT NULL COMMENT '维修完成时间',
  `repair_duration` int(0) NULL DEFAULT NULL COMMENT '维修时长',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '车辆维修表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_scooter_repair
-- ----------------------------

-- ----------------------------
-- Table structure for sco_scooter_repair_detail
-- ----------------------------
DROP TABLE IF EXISTS `sco_scooter_repair_detail`;
CREATE TABLE `sco_scooter_repair_detail`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `repair_id` bigint(0) NULL DEFAULT NULL COMMENT '维修ID',
  `type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维修类型',
  `invoice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '维修详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_scooter_repair_detail
-- ----------------------------

-- ----------------------------
-- Table structure for sco_scooter_spairpart
-- ----------------------------
DROP TABLE IF EXISTS `sco_scooter_spairpart`;
CREATE TABLE `sco_scooter_spairpart`  (
  `scooter_id` bigint(0) NULL DEFAULT NULL COMMENT '车辆ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `sparepart_id` bigint(0) NULL DEFAULT NULL COMMENT '配件ID',
  `type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配件类型',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配件名称',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配件状态',
  `revision` int(0) NULL DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '摩托车配件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_scooter_spairpart
-- ----------------------------

-- ----------------------------
-- Table structure for sco_scooter_status
-- ----------------------------
DROP TABLE IF EXISTS `sco_scooter_status`;
CREATE TABLE `sco_scooter_status`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_ecu_id` bigint(0) NULL DEFAULT NULL COMMENT '智能控制系统ID',
  `scooter_id` bigint(0) NOT NULL COMMENT 'ScooterID',
  `lock_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车锁状态',
  `top_box_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '后备箱状态',
  `longitule` decimal(32, 10) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '纬度',
  `geohash` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GEOHash',
  `battery` int(0) NULL DEFAULT NULL COMMENT '电池电量',
  `cumulative_mileage` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '累积行驶里程',
  `revision` int(0) NULL DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '电动车实时信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_scooter_status
-- ----------------------------
INSERT INTO `sco_scooter_status` VALUES (1000030, 0, 0, 1002161, '1', '1', 0.0000000000, 0.0000000000, 's00000', 100, '0', 0, 1000006, '2020-05-06 07:25:36', 1000006, '2020-05-06 07:25:36', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sco_scooter_status` VALUES (1000031, 0, 0, 1002162, '1', '1', 0.0000000000, 0.0000000000, 's00000', 100, '0', 0, 1000006, '2020-05-06 07:26:17', 1000006, '2020-05-06 07:26:17', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sco_scooter_storage_box
-- ----------------------------
DROP TABLE IF EXISTS `sco_scooter_storage_box`;
CREATE TABLE `sco_scooter_storage_box`  (
  `id` bigint(0) NULL DEFAULT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `revision` int(0) NULL DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '摩托车后备箱' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_scooter_storage_box
-- ----------------------------

-- ----------------------------
-- Table structure for sco_sparepart
-- ----------------------------
DROP TABLE IF EXISTS `sco_sparepart`;
CREATE TABLE `sco_sparepart`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配件类型',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配件名称',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配件状态',
  `entry_warehouse_time` datetime(0) NULL DEFAULT NULL COMMENT '入库时间',
  `usage_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用时间',
  `use_reason` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用原因',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '配件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sco_sparepart
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
