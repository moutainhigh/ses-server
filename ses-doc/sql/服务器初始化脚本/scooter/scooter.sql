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

 Date: 14/06/2020 18:36:36
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
INSERT INTO `sco_scooter` VALUES (1020945, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-001-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1021169, '2020-06-02 02:17:16', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020946, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2', 0, '1', '1', '3', 'AA-002-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1023304, '2020-06-02 03:35:51', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020947, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2', 0, '1', '1', '3', 'AA-003-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1023311, '2020-06-02 06:59:48', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020948, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2', 0, '1', '1', '3', 'AA-004-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1000004, '2020-04-09 08:00:47', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020949, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2', 0, '1', '1', '3', 'AA-005-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1008499, '2020-03-24 03:45:48', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020950, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2', 0, '4', '1', '3', 'AA-006-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1010587, '2020-04-24 08:13:00', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020951, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2', 0, '1', '1', '3', 'AA-007-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1000005, '2020-03-23 14:46:10', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020952, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2', 0, '4', '1', '3', 'AA-008-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1000002, '2020-03-23 11:49:34', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020953, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-009-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1010557, '2020-04-22 02:28:02', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020954, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-010-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1010592, '2020-04-23 13:21:08', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020955, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-012-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1031564, '2020-02-17 06:18:43', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020956, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-011-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1010602, '2020-04-24 03:53:36', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020957, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-013-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1102868, '2020-01-07 02:56:38', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020958, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-014-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1033703, '2020-02-16 21:59:38', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020959, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-015-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1014820, '2020-02-18 17:31:28', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020960, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-016-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1010612, '2020-04-24 04:45:08', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020961, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-017-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1004235, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020962, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-018-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1004237, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020963, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-019-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1004237, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020964, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-020-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1004235, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020965, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-021-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1010611, '2020-05-03 06:16:34', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020966, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-022-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1010611, '2020-05-06 07:42:47', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020967, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-023-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1006420, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020968, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-024-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1006420, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020969, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-025-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1006420, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020970, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-026-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1029493, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020971, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-027-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1031564, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020972, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-028-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1002121, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020973, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-029-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1102868, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020974, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-030-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1033703, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020975, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-031-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1071493, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020976, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-032-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1033713, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020977, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-033-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1004235, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020978, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-034-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1004237, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020979, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-035-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1004237, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020980, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-036-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1004235, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020981, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-037-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1004236, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020982, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-038-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1006420, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020983, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-039-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1006420, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020984, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-040-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1006420, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020985, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-041-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1006420, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020986, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-042-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1029493, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020987, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-043-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1031564, '2019-11-20 04:14:40', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020988, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-044-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1033720, '2020-02-19 07:45:04', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020989, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-045-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1033729, '2020-02-18 18:56:41', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020990, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-046-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1033724, '2020-02-19 16:00:19', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020991, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-047-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1033727, '2020-02-17 16:09:12', NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter` VALUES (1020992, 0, '0123456789ABCDEF', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '1', 0, '1', '1', '3', 'AA-048-BB', 'https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg', '2019-11-20 14:31:20', '0', '2019-11-20 14:34:18', '0', 0, 1000000, '2019-11-20 04:14:40', 1033720, '2020-02-17 15:27:35', NULL, NULL, NULL, NULL);

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
INSERT INTO `sco_scooter_status` VALUES (1021168, 0, NULL, 1020945, 'LOCKED', 'LOCKED', 2.3354060000, 48.8652520000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021169, 0, NULL, 1020946, 'LOCKED', 'LOCKED', 2.3321970000, 48.8666100000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021170, 0, NULL, 1020947, 'LOCKED', 'LOCKED', 2.3348260000, 48.8662570000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021171, 0, NULL, 1020948, 'LOCKED', 'LOCKED', 2.3329360000, 48.8655840000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021172, 0, NULL, 1020949, 'LOCKED', 'LOCKED', 2.3429170000, 48.8616880000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021173, 0, NULL, 1020950, 'LOCKED', 'LOCKED', 2.3420040000, 48.8710150000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021174, 0, NULL, 1020951, 'LOCKED', 'LOCKED', 2.3460490000, 48.8700980000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021176, 0, NULL, 1020953, 'LOCKED', 'LOCKED', 2.3477770000, 48.8705330000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021177, 0, NULL, 1020954, 'LOCKED', 'LOCKED', 2.3364410000, 48.8693320000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021178, 0, NULL, 1020955, 'LOCKED', 'LOCKED', 2.3275210000, 48.8712400000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021179, 0, NULL, 1020956, 'LOCKED', 'LOCKED', 2.2949730000, 48.8354690000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021180, 0, NULL, 1020957, 'LOCKED', 'LOCKED', 2.3069040000, 48.8328230000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021181, 0, NULL, 1020958, 'LOCKED', 'LOCKED', 2.3109590000, 48.8415890000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021182, 0, NULL, 1020959, 'LOCKED', 'LOCKED', 2.3063880000, 48.8422500000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021183, 0, NULL, 1020960, 'LOCKED', 'LOCKED', 2.3354060000, 48.8652520000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021184, 0, NULL, 1020952, 'LOCKED', 'LOCKED', 2.3354060000, 48.8652520000, NULL, 100, '0', 0, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021185, 0, NULL, 1020961, 'LOCKED', 'LOCKED', 2.3354060000, 48.8652520000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021186, 0, NULL, 1020962, 'LOCKED', 'LOCKED', 2.3321970000, 48.8666100000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021187, 0, NULL, 1020963, 'LOCKED', 'LOCKED', 2.3348260000, 48.8662570000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021188, 0, NULL, 1020964, 'LOCKED', 'LOCKED', 2.3329360000, 48.8655840000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021189, 0, NULL, 1020965, 'LOCKED', 'LOCKED', 2.3429170000, 48.8616880000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021190, 0, NULL, 1020966, 'LOCKED', 'LOCKED', 2.3420040000, 48.8710150000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021191, 0, NULL, 1020967, 'LOCKED', 'LOCKED', 2.3460490000, 48.8700980000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021192, 0, NULL, 1020968, 'LOCKED', 'LOCKED', 2.3477770000, 48.8705330000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021193, 0, NULL, 1020969, 'LOCKED', 'LOCKED', 2.3364410000, 48.8693320000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021194, 0, NULL, 1020970, 'LOCKED', 'LOCKED', 2.3275210000, 48.8712400000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021195, 0, NULL, 1020971, 'LOCKED', 'LOCKED', 2.2949730000, 48.8354690000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021196, 0, NULL, 1020972, 'LOCKED', 'LOCKED', 2.3069040000, 48.8328230000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021197, 0, NULL, 1020973, 'LOCKED', 'LOCKED', 2.3109590000, 48.8415890000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021198, 0, NULL, 1020974, 'LOCKED', 'LOCKED', 2.3063880000, 48.8422500000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021199, 0, NULL, 1020975, 'LOCKED', 'LOCKED', 2.3354060000, 48.8652520000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021200, 0, NULL, 1020976, 'LOCKED', 'LOCKED', 2.3354060000, 48.8652520000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021201, 0, NULL, 1020977, 'LOCKED', 'LOCKED', 2.3354060000, 48.8652520000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021202, 0, NULL, 1020978, 'LOCKED', 'LOCKED', 2.3321970000, 48.8666100000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021203, 0, NULL, 1020979, 'LOCKED', 'LOCKED', 2.3348260000, 48.8662570000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021204, 0, NULL, 1020980, 'LOCKED', 'LOCKED', 2.3329360000, 48.8655840000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021205, 0, NULL, 1020981, 'LOCKED', 'LOCKED', 2.3429170000, 48.8616880000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021206, 0, NULL, 1020982, 'LOCKED', 'LOCKED', 2.3420040000, 48.8710150000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021207, 0, NULL, 1020983, 'LOCKED', 'LOCKED', 2.3460490000, 48.8700980000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021208, 0, NULL, 1020984, 'LOCKED', 'LOCKED', 2.3477770000, 48.8705330000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021209, 0, NULL, 1020985, 'LOCKED', 'LOCKED', 2.3364410000, 48.8693320000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021210, 0, NULL, 1020986, 'LOCKED', 'LOCKED', 2.3275210000, 48.8712400000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021211, 0, NULL, 1020987, 'LOCKED', 'LOCKED', 2.2949730000, 48.8354690000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021212, 0, NULL, 1020988, 'LOCKED', 'LOCKED', 2.3069040000, 48.8328230000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021213, 0, NULL, 1020989, 'LOCKED', 'LOCKED', 2.3109590000, 48.8415890000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021214, 0, NULL, 1020990, 'LOCKED', 'LOCKED', 2.3063880000, 48.8422500000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021215, 0, NULL, 1020991, 'LOCKED', 'LOCKED', 2.3354060000, 48.8652520000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sco_scooter_status` VALUES (1021216, 0, NULL, 1020992, 'LOCKED', 'LOCKED', 2.3354060000, 48.8652520000, NULL, 100, '0', NULL, 1000000, '2019-11-20 05:46:24', 1000000, '2019-11-20 05:46:24', NULL, NULL, NULL, NULL, NULL);

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
