/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : consumer

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 14/06/2020 18:36:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for con_driver_ride_stat
-- ----------------------------
DROP TABLE IF EXISTS `con_driver_ride_stat`;
CREATE TABLE `con_driver_ride_stat`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID',
  `driver_id` bigint(0) NOT NULL COMMENT 'userId',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '司机骑行统计数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of con_driver_ride_stat
-- ----------------------------

-- ----------------------------
-- Table structure for con_driver_ride_stat_detail
-- ----------------------------
DROP TABLE IF EXISTS `con_driver_ride_stat_detail`;
CREATE TABLE `con_driver_ride_stat_detail`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID',
  `biz_id` bigint(0) NULL DEFAULT NULL COMMENT '业务id',
  `biz_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `driver_id` bigint(0) NOT NULL COMMENT 'userId',
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
-- Records of con_driver_ride_stat_detail
-- ----------------------------

-- ----------------------------
-- Table structure for con_invoice
-- ----------------------------
DROP TABLE IF EXISTS `con_invoice`;
CREATE TABLE `con_invoice`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `payer` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '付款人',
  `payee` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收款人',
  `amount` decimal(32, 8) NULL DEFAULT NULL COMMENT '总金额',
  `invoice_time` datetime(0) NULL DEFAULT NULL COMMENT '发票时间',
  `invoice_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票号',
  `picture` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userid',
  `biz_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票业务类型',
  `biz_id` bigint(0) NULL DEFAULT NULL COMMENT '发票产生的的具体业务ID 、存放 scooteId',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '发票表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of con_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for con_penalty
-- ----------------------------
DROP TABLE IF EXISTS `con_penalty`;
CREATE TABLE `con_penalty`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_id` bigint(0) NULL DEFAULT NULL COMMENT '车辆ID',
  `institution` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '罚款开具的机构',
  `amount` decimal(32, 8) NULL DEFAULT NULL COMMENT '金额',
  `penalty_time` datetime(0) NULL DEFAULT NULL COMMENT '罚款时间',
  `penalty_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '罚款单号',
  `pay_link` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付链接地址',
  `picture` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userid',
  `status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `CREATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `CREATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `UPDATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `UPDATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '罚款' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of con_penalty
-- ----------------------------

-- ----------------------------
-- Table structure for con_scooter_ride_stat
-- ----------------------------
DROP TABLE IF EXISTS `con_scooter_ride_stat`;
CREATE TABLE `con_scooter_ride_stat`  (
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
-- Records of con_scooter_ride_stat
-- ----------------------------

-- ----------------------------
-- Table structure for con_scooter_ride_stat_detail
-- ----------------------------
DROP TABLE IF EXISTS `con_scooter_ride_stat_detail`;
CREATE TABLE `con_scooter_ride_stat_detail`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID',
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
-- Records of con_scooter_ride_stat_detail
-- ----------------------------

-- ----------------------------
-- Table structure for con_user_bank_card
-- ----------------------------
DROP TABLE IF EXISTS `con_user_bank_card`;
CREATE TABLE `con_user_bank_card`  (
  `id` bigint(0) NOT NULL,
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `card_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卡号',
  `card_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡类型',
  `bank_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行名称',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态  启用、解绑 Enable、Untied',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userid',
  `CREATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `CREATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `UPDATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `UPDATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of con_user_bank_card
-- ----------------------------

-- ----------------------------
-- Table structure for con_user_profile
-- ----------------------------
DROP TABLE IF EXISTS `con_user_profile`;
CREATE TABLE `con_user_profile`  (
  `ID` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `TENANT_ID` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `USER_ID` bigint(0) NOT NULL COMMENT '用户ID',
  `FIRST_NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓',
  `LAST_NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名',
  `FULL_NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `PICTURE` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片',
  `EMAIL_1` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `EMAIL_2` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `EMAIL_3` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `country_code_1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码如+86',
  `TEL_NUMBER_1` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号',
  `country_code_2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码如+86',
  `TEL_NUMBER_2` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号',
  `country_code_3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码如+86',
  `TEL_NUMBER_3` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号',
  `GENDER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `BIRTHDAY` date NULL DEFAULT NULL COMMENT '生日',
  `CERTIFICATE_TYPE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型1身份证，2驾驶证，3护照',
  `CERTIFICATE_NEGATIVE_ANNEX` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件反面图片',
  `CERTIFICATE_POSITIVE_ANNEX` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件正面图片',
  `ROLE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色 DRIVER-司机；MANAGE-管理者',
  `PLACE_BIRTH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生地',
  `ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地址',
  `JOIN_DATE` date NULL DEFAULT NULL COMMENT '加入日期',
  `TIME_ZONE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时区',
  `CREATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `CREATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `UPDATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `UPDATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of con_user_profile
-- ----------------------------

-- ----------------------------
-- Table structure for con_user_rfid
-- ----------------------------
DROP TABLE IF EXISTS `con_user_rfid`;
CREATE TABLE `con_user_rfid`  (
  `ID` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `SN` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '序列号',
  `SECRET_KEY` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '秘钥',
  `TENANT_ID` bigint(0) NULL DEFAULT NULL COMMENT '门店Id',
  `SCOOTER_ID` bigint(0) NULL DEFAULT NULL,
  `USER_ID` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `STATUS` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '已分配：allocation已写卡：available已失效：unavailable（暂时不要）空闲：FREE',
  `CREATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `CREATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `UPDATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `UPDATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'RFID表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of con_user_rfid
-- ----------------------------

-- ----------------------------
-- Table structure for con_user_scooter
-- ----------------------------
DROP TABLE IF EXISTS `con_user_scooter`;
CREATE TABLE `con_user_scooter`  (
  `ID` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `TENANT_ID` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `USER_ID` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `SCOOTER_ID` bigint(0) NULL DEFAULT NULL COMMENT '车辆ID',
  `BEGIN_TIME` datetime(0) NULL DEFAULT NULL COMMENT '车辆分配开始时间',
  `END_TIME` datetime(0) NULL DEFAULT NULL COMMENT '车辆归还时间',
  `STATUS` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态 USED，FINSH，使用中、未使用',
  `MILEAGE` double(20, 0) NULL DEFAULT NULL COMMENT '行驶里程',
  `CREATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `CREATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `UPDATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `UPDATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '司机分配车辆表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of con_user_scooter
-- ----------------------------

-- ----------------------------
-- Table structure for pla_user_node
-- ----------------------------
DROP TABLE IF EXISTS `pla_user_node`;
CREATE TABLE `pla_user_node`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'user表主键',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户主键',
  `event` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '事件',
  `event_time` datetime(0) NULL DEFAULT NULL COMMENT '事件时间',
  `memo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户节点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_user_node
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
