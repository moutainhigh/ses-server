/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : platform

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 14/06/2020 19:27:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pla_app_version
-- ----------------------------
DROP TABLE IF EXISTS `pla_app_version`;
CREATE TABLE `pla_app_version`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `system_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统ID',
  `app_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用ID',
  `system_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IOS or ANDROID',
  `is_force` int(0) NULL DEFAULT NULL COMMENT '是否强制更新 1时true，0时false',
  `update_content` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新提示内容',
  `update_link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新地址',
  `new_version_num` int(0) NULL DEFAULT NULL COMMENT '新版本号',
  `package_size` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安装包大小',
  `new_version_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '新版本号名称',
  `min_version_num` int(0) NULL DEFAULT NULL COMMENT '最小版本',
  `nin_version_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最小版本号名称',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态 NEW:新版本；Closed：已关闭',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '应用版本管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_app_version
-- ----------------------------

-- ----------------------------
-- Table structure for pla_city
-- ----------------------------
DROP TABLE IF EXISTS `pla_city`;
CREATE TABLE `pla_city`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `level` int(0) NULL DEFAULT NULL COMMENT '级别，国家为首级，默认为1',
  `p_id` bigint(0) NULL DEFAULT NULL COMMENT '父ID，默认首级节点为0',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `time_zone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时区',
  `language` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语言',
  `longitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '纬度',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '城市表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_city
-- ----------------------------
INSERT INTO `pla_city` VALUES (1, 0, 'word', 'WORD', 0, 0, '1', NULL, NULL, NULL, NULL, 0, '2020-02-20 03:30:49', 0, '2020-02-20 03:31:48', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000000, 0, 'FRA', 'France', 1, 1, '1', NULL, NULL, NULL, NULL, 0, '2020-02-20 03:31:48', 0, '2020-02-20 03:31:48', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000001, 1, 'ARL', 'Arles', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000002, 1, 'AJA', 'Ajaccio', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000003, 1, 'QXB', 'Aix-en-Provence', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000004, 1, 'ORR', 'Orleans', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000005, 0, 'PAR', 'Paris', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-02-20 03:41:37', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000006, 1, 'BSN', 'Besancon', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000007, 1, 'FRJ', 'Frejus', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000008, 1, 'CFR', 'Caen', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000009, 1, 'RNS', 'Rennes', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:45', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000010, 1, 'LIO', 'Lyon', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000011, 1, 'LLE', 'Lille', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000012, 0, 'URO', 'Rouen', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:57:02', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000013, 1, 'MRS', 'Marseille', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000014, 1, 'MZM', 'Metz', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000015, 1, 'MPL', 'Montpellier', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000016, 1, 'NTE', 'Nantes', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000017, 1, 'NCE', 'Nice', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000018, 1, 'CSM', 'Chalons-en-Champagne', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:57:02', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000019, 1, 'TLS', 'Toulouse', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000020, 1, 'VAA', 'Valence', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000021, 1, 'AMI', 'Amiens', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000022, 1, 'LIG', 'Limoges', 2, 1000000, '1', NULL, 'fr', NULL, NULL, 0, '2020-02-20 03:41:37', 0, '2020-03-13 02:56:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000023, 0, '75103', '75003', 3, 1000005, '1', NULL, 'fr', 48.8630541318, 2.3593610590, 0, '2020-02-20 03:58:21', 0, '2020-02-25 04:04:25', 'PARIS 03\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000024, 0, '75105', '75005', 3, 1000005, '1', NULL, 'fr', 48.8445086596, 2.3498593856, 0, '2020-02-20 03:58:21', 0, '2020-02-25 04:04:25', 'PARIS 05\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000025, 0, '75108', '75008', 3, 1000005, '1', NULL, 'fr', 48.8725272666, 2.3125825604, 0, '2020-02-20 03:58:21', 0, '2020-02-25 04:04:26', 'PARIS 08\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000026, 0, '75110', '75010', 3, 1000005, '1', NULL, 'fr', 48.8760285569, 2.3611129046, 0, '2020-02-20 03:58:21', 0, '2020-02-25 04:04:25', 'PARIS 10\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000027, 0, '75112', '75012', 3, 1000005, '1', NULL, 'fr', 48.8351562307, 2.4198070350, 0, '2020-02-20 03:58:21', 0, '2020-02-25 04:04:25', 'PARIS 12\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000028, 0, '75115', '75015', 3, 1000005, '1', NULL, 'fr', 48.8401554186, 2.2935593724, 0, '2020-02-20 03:58:21', 0, '2020-02-25 04:04:25', 'PARIS 15\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000029, 0, '75118', '75018', 3, 1000005, '1', NULL, 'fr', 48.8927350746, 2.3487119339, 0, '2020-02-20 03:58:21', 0, '2020-02-25 04:04:26', 'PARIS 18\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000030, 0, '75120', '75020', 3, 1000005, '1', NULL, 'fr', 48.8631867774, 2.4008198267, 0, '2020-02-20 03:58:22', 0, '2020-02-25 04:04:25', 'PARIS 20\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000031, 0, '75102', '75002', 3, 1000005, '1', NULL, 'fr', 48.8679033789, 2.3441071667, 0, '2020-02-20 03:58:22', 0, '2020-02-25 04:04:25', 'PARIS 02\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000032, 0, '75104', '75004', 3, 1000005, '1', NULL, 'fr', 48.8542282820, 2.3573619381, 0, '2020-02-20 03:58:22', 0, '2020-02-25 04:04:25', 'PARIS 04\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000033, 0, '75116', '75016', 3, 1000005, '1', NULL, 'fr', 48.8603987604, 2.2620995594, 0, '2020-02-20 03:58:22', 0, '2020-02-25 04:04:25', 'PARIS 16\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000034, 0, '75111', '75011', 3, 1000005, '1', NULL, 'fr', 48.8594154976, 2.3787410602, 0, '2020-02-20 03:58:22', 0, '2020-02-25 04:04:25', 'PARIS 11\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000035, 0, '75116', '75116', 3, 1000005, '1', NULL, 'fr', 48.8603987604, 2.2620995594, 0, '2020-02-20 03:58:22', 0, '2020-02-25 04:04:25', 'PARIS 16\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000036, 0, '75117', '75017', 3, 1000005, '1', NULL, 'fr', 48.8873371665, 2.3074855595, 0, '2020-02-20 03:58:22', 0, '2020-02-25 04:04:25', 'PARIS 17\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000037, 0, '75106', '75006', 3, 1000005, '1', NULL, 'fr', 48.8489680919, 2.3326708986, 0, '2020-02-20 03:58:22', 0, '2020-02-25 04:04:25', 'PARIS 06\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000038, 0, '75113', '75013', 3, 1000005, '1', NULL, 'fr', 48.8287176845, 2.3624682285, 0, '2020-02-20 03:58:22', 0, '2020-02-25 04:04:26', 'PARIS 13\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000039, 0, '75119', '75019', 3, 1000005, '1', NULL, 'fr', 48.8868686230, 2.3846943279, 0, '2020-02-20 03:58:22', 0, '2020-02-25 04:04:25', 'PARIS 19\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000040, 0, '75101', '75001', 3, 1000005, '1', NULL, 'fr', 48.8626304852, 2.3362934466, 0, '2020-02-20 03:58:22', 0, '2020-02-25 04:04:25', 'PARIS 01\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000041, 0, '75107', '75007', 3, 1000005, '1', NULL, 'fr', 48.8560825982, 2.3124386877, 0, '2020-02-20 03:58:22', 0, '2020-02-25 04:04:25', 'PARIS 07\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000042, 0, '75109', '75009', 3, 1000005, '1', NULL, 'fr', 48.8768961624, 2.3374602414, 0, '2020-02-20 03:58:22', 0, '2020-02-25 04:04:26', 'PARIS 09\r', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000043, 0, '75114', '75014', 3, 1000005, '1', NULL, 'fr', 48.8289932116, 2.3271008833, 0, '2020-02-20 03:58:22', 0, '2020-02-20 03:58:22', 'PARIS 14', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000044, 0, '85101', '85101', 3, 1000012, '1', NULL, 'fr', 48.8289932116, 2.3271008833, 0, '2020-02-20 03:58:22', 0, '2020-02-20 03:58:22', 'PARIS 14', NULL, NULL, NULL, 0);
INSERT INTO `pla_city` VALUES (1000045, 0, '85102', '85102', 3, 1000012, '1', NULL, 'fr', 48.8289932116, 2.3271008833, 0, '2020-02-20 03:58:22', 0, '2020-02-20 03:58:22', 'PARIS 14', NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for pla_country_code
-- ----------------------------
DROP TABLE IF EXISTS `pla_country_code`;
CREATE TABLE `pla_country_code`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除主键',
  `icon` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家国旗图标',
  `country_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '国家编码',
  `country_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '国家名称',
  `country_language` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '国家语言',
  `time_zone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时区',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '国家编码,用于手机号前缀等' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_country_code
-- ----------------------------
INSERT INTO `pla_country_code` VALUES (10004, 0, NULL, '33', 'FR', 'fr-FR', '-8', 'France', NULL, NULL);
INSERT INTO `pla_country_code` VALUES (10005, 0, NULL, '1', 'US', 'en-US', '0', 'United States of America	', NULL, NULL);

-- ----------------------------
-- Table structure for pla_i18n_config
-- ----------------------------
DROP TABLE IF EXISTS `pla_i18n_config`;
CREATE TABLE `pla_i18n_config`  (
  `ID` bigint(0) NOT NULL,
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `group` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `country` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `value` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `desc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `created_time` datetime(0) NOT NULL,
  `deleted` tinyint(1) NULL DEFAULT 0,
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_i18n_config
-- ----------------------------

-- ----------------------------
-- Table structure for pla_jpush_user
-- ----------------------------
DROP TABLE IF EXISTS `pla_jpush_user`;
CREATE TABLE `pla_jpush_user`  (
  `id` bigint(0) NOT NULL,
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户主键',
  `registration_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备唯一标识',
  `tag` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
  `alias` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `platform_type` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推送平台:支持 Android, iOS, Windows Phone 三个平台的推送。其关键字分别为：\"android\", \"ios\", \"winphone\"。',
  `audience_type` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推送目标:别名ALIAS、标签TAG、注册唯一属性REGISTRATION_ID、分群SEGMENT、广播',
  `push_time` datetime(0) NULL DEFAULT NULL COMMENT '推送时间',
  `status` int(0) NULL DEFAULT 0 COMMENT '登录绑定：0，注销解绑：1',
  `status_code` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态码：登录绑定LOGIN，注销解绑LOGOUT',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '极光用户关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_jpush_user
-- ----------------------------

-- ----------------------------
-- Table structure for pla_mail_config
-- ----------------------------
DROP TABLE IF EXISTS `pla_mail_config`;
CREATE TABLE `pla_mail_config`  (
  `id` bigint(0) NOT NULL DEFAULT 10000 COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'NORMAL' COMMENT 'normal正常，Disabled失效的',
  `mail_template_no` int(0) NULL DEFAULT NULL COMMENT '模板编号',
  `system_id` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统ID',
  `app_id` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用ID',
  `param_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应key，不可为空',
  `param_value` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应值域，可为空',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '邮件配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_mail_config
-- ----------------------------
INSERT INTO `pla_mail_config` VALUES (10000, 0, 'NORMAL', 12, 'REDE_SES', '4', 'subscriptionPaySucceedSendEamil', '18721403004', 0, '2020-05-28 15:59:34', 0, '2020-05-28 15:59:34', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (10013, 0, 'NORMAL', 13, 'REDE_SES', '4', 'url', 'https://redelectric.fr/#/reset-password?requestId=', 0, '2020-05-28 15:59:36', 0, '2020-05-28 15:59:36', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100000, 0, 'NORMAL', 1, 'REDE_SAAS', '1', 'url', 'https://redelectric.fr/account-plugin/#/?requestId=', 0, '2019-10-14 17:58:27', 0, '2019-10-14 17:58:33', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100001, 0, 'NORMAL', 1, 'REDE_SAAS', '3', 'url', 'https://redelectric.fr/repair/#/retrieve?requestId=', 0, '2019-10-15 11:31:04', 0, '2019-10-15 11:31:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100002, 0, 'NORMAL', 3, 'REDE_SAAS', '3', 'url', 'https://redelectric.fr/repair/#/retrieve?requestId=', 0, '2019-10-15 11:48:06', 0, '2019-10-15 11:48:09', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100003, 0, 'NORMAL', 3, 'REDE_SAAS', '1', 'url', 'https://redelectric.fr/account-plugin/#/?requestId=', 0, '2019-10-15 11:49:34', 0, '2019-10-15 11:49:39', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100004, 0, 'NORMAL', 4, 'REDE_SAAS', '2', 'verificationCode', NULL, 0, '2019-10-15 11:55:32', 0, '2019-10-15 11:55:35', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100005, 0, 'NORMAL', 4, 'REDE_SES', '2', 'verificationCode', NULL, 0, '2019-10-15 11:56:15', 0, '2019-10-15 11:56:18', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100006, 0, 'NORMAL', 5, 'REDE_SAAS', '1', 'freezeDate', NULL, 0, '2019-10-15 12:03:54', 0, '2019-10-15 12:03:58', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100007, 0, 'NORMAL', 5, 'REDE_SAAS', '1', 'consumerHotline', '18721403004', 0, '2019-10-15 12:03:52', 0, '2019-10-15 12:04:01', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100008, 0, 'NORMAL', 6, 'REDE_SAAS', '1', 'renewalDate', NULL, 0, '2019-10-15 12:51:17', 0, '2019-10-15 12:51:19', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100009, 0, 'NORMAL', 7, 'REDE_SAAS', '1', 'unfreezeStatDate', NULL, 0, '2019-10-15 12:58:23', 0, '2019-10-15 12:58:29', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100010, 0, 'NORMAL', 7, 'REDE_SAAS', '1', 'unfreezeEndDate', NULL, 0, '2019-10-15 12:58:26', 0, '2019-10-15 12:58:31', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100011, 0, 'NORMAL', 8, 'REDE_SAAS', '1', 'consumerHotline', '18721403004', 0, '2019-10-15 13:00:42', 0, '2019-10-15 13:00:47', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for pla_mail_task
-- ----------------------------
DROP TABLE IF EXISTS `pla_mail_task`;
CREATE TABLE `pla_mail_task`  (
  `id` bigint(0) NOT NULL DEFAULT 10000 COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING待发送，SUCCESS发送成功，FAIL发送失败',
  `system_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统ID',
  `app_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用ID',
  `request_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求ID',
  `mail_template_no` int(0) NULL DEFAULT NULL COMMENT '模板编号',
  `send_mail` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送方',
  `receive_mail` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接受方',
  `to_user_id` bigint(0) NOT NULL COMMENT '收件方用户ID',
  `subject` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `file_path` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件地址，可为空',
  `parameter` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送json保存',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '邮件任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_mail_task
-- ----------------------------

-- ----------------------------
-- Table structure for pla_mail_template
-- ----------------------------
DROP TABLE IF EXISTS `pla_mail_template`;
CREATE TABLE `pla_mail_template`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'NORMAL' COMMENT 'Normal正常的，Disabled失效的',
  `mail_template_no` int(0) NULL DEFAULT NULL COMMENT '模板编号',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `event` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮件发送事件',
  `subject` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板实际内容',
  `memo` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '说明',
  `backup` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板远程备份',
  `expire` int(0) NOT NULL DEFAULT 259200 COMMENT '邮件有效期，单位秒',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '邮件模板配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_mail_template
-- ----------------------------
INSERT INTO `pla_mail_template` VALUES (1000000, 0, 'NORMAL', 1, 'web_activate', 'WEB_ACTIVATE', 'Activation Your Account', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账号激活</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n        .emailBox{\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        Activation your account\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        You can via the RED Pass/Register email by clicking the button below to activate your account . The link is valid for 72 hours, please check it.\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RED pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"/>\n        </div>\n    </div>\n    <div style=\"padding-left: 10px;margin-top: 25px\">\n            <div style=\"width:172px;height:44px;text-align: center;line-height: 44px;\n                background:rgba(242,53,77,1);border-radius:5px;font-size:16px;font-family:SourceSansPro-Semibold;\n                font-weight:600;color:rgba(255,255,255,1);\">\n                <a th:href=\"${url}+${requestId}\" style=\"text-decoration:none;color:\n                #ffffff\" >\n                Activate Account\n                </a>\n            </div>\n    </div>\n\n    <div style=\"padding-left: 10px;font-size:12px;font-weight:400;font-family:SourceSansPro-Regular;margin-top: 40px\">\n        <span style=\"color:rgba(77,79,92,1);\">\n            Button not working? Paste the following link into your browser:\n        </span>\n        <a th:href=\"${url}+${requestId}\"\n            style=\"color: rgba(53,130,251,0.8);text-decoration:blink\">\n            <span th:text=\"${url}+${requestId}\" />\n        </a>\n    </div>\n\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>\n', 'WEB账号激活', NULL, 3600, 0, '2019-10-11 08:41:56', 0, '2019-10-11 08:41:56', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000001, 0, 'NORMAL', 2, 'mobile_activate', 'MOBILE_ACTIVATE', 'Activation your account', '<!DOCTYPE html>\n<html style=\"font-size: 1px;font-family:SourceSansPro-Regular;\" xmlns:th=\"http://www.thymeleaf.org\">\n<meta charset=\"utf-8\">\n<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no\" />\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账号激活</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n            box-sizing: inherit;\n        }\n        a , a:hover{\n            text-decoration: none;\n        }\n        .emailBox {\n            background: rgba(255, 255, 255, 1);\n            margin: auto;\n            padding: 32px 8% 24px 8%;\n        }\n    </style>\n</head>\n<body style=\"background-color: #fff\">\n<div class=\"emailBox\">\n    <div style=\"font-size:18px;font-family:HelveticaNeue;font-weight:bold;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"/> ,\n    </div>\n    <div style=\"font-size:17px;font-family:HelveticaNeue;font-weight:bold;color:rgba(77,79,92,1);margin-top: 16px\">\n        Activation Your Account\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:HelveticaNeue;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 20px\">\n        By the button below or scan the QR code to download the app,activate your account via RED Pass/Register Email.\n    </div>\n    <div style=\"font-size:12px;font-family:HelveticaNeue;font-weight:400;color:rgba(77,79,92,1);\">\n        RedE Pass ：\n    </div>\n    <div style=\"margin-top: 10px\">\n        <div style=\"background:rgba(77,79,92,0.1);font-size: 16px;\n        height: 40px;line-height: 40px;\n        padding:0 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"/>\n        </div>\n    </div>\n    <div style=\"margin-top: 25px;font-size:12px;color:rgba(77,79,92,1);\">\n        <!--Verification code-->\n    </div>\n    <div style=\"margin-top: 10px\">\n        <div style=\"background:rgba(255,255,255,0.1);padding:0 15px;height: 36px;line-height: 36px;\n            border-radius:3px;font-size: 16px;font-weight:bold;\n            letter-spacing:5px;display: inline-block\">\n<!--            46297-->\n        </div>\n    </div>\n\n    <div style=\"text-align: center;margin-top: 45px;display: flex;justify-content: center;flex-direction: row;\">\n        <div style=\"width: 70px;height: 70px;margin-right: 20px\">\n            <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/redeWeb.svg\'\n                 style=\"width: 70px;height: 70px\"\n                 alt=\"\">\n        </div>\n        <div style=\"display: flex;flex-direction: column;height: 70px\">\n            <a href=\"\" style=\"text-decoration:none;height: 30px;display: block\">\n                <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/DowIos.svg\'\n                     style=\"\" alt=\"\">\n            </a>\n            <div style=\"flex: 1\"></div>\n            <a href=\"https://www.pgyer.com/XOpc?sign=&auSign=&code=\" style=\"text-decoration:none;height: 30px;display: block\">\n                <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/DowAndroid.svg\'\n                     style=\"\" alt=\"\">\n            </a>\n        </div>\n    </div>\n\n    <div style=\"text-align: center;font-size:12px;margin-top:20px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,0.5);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>\n', 'MOBILE账号激活', NULL, 3600, 0, '2019-10-11 08:47:37', 0, '2019-10-11 08:47:37', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000002, 0, 'NORMAL', 3, 'web_password', 'WEB_PASSWORD', 'Set Password', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>Set Password</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n        .emailBox{\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        Reset Password\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        If you need to change or reset your password, you can click the button below to reset your password. Valid for 5 minutes, please check it.\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        <!--RedE pass ：-->\n    </div>\n\n  <div style=\"padding-left: 10px;margin-top: 25px\">\n      <a th:href=\"${url}+${requestId}\" style=\"text-decoration:none\">\n          <div style=\"width:172px;height:44px;text-align: center;line-height: 44px;\n              background:rgba(242,53,77,1);border-radius:5px;font-size:16px;font-family:SourceSansPro-Semibold;\n              font-weight:600;color:rgba(255,255,255,1);\">\n              Set Password\n          </div>\n      </a>\n  </div>\n\n    <div style=\"padding-left: 10px;font-size:12px;font-weight:400;font-family:SourceSansPro-Regular;margin-top: 40px\">\n        <span style=\"color:rgba(77,79,92,1);\">\n            Button not working? Paste the following link into your browser:\n        </span>\n        <a th:href=\"${url}+${requestId}\"\n           style=\"color: rgba(53,130,251,0.8);text-decoration:blink\">\n            <span th:text=\"${url}+${requestId}\" />\n        </a>\n    </div>\n  <div style=\"height: 100px\">\n  </div>\n\n  <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n          font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n      <p>\n          contact us at support@redescooter.com.<br/>\n          www.redescooter.com\n      </p>\n  </div>\n</div>\n</body>\n</html>\n', 'WEB设置密码', NULL, 3600, 0, '2019-10-11 08:47:58', 0, '2019-10-11 08:47:58', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000003, 0, 'NORMAL', 4, 'mobile_password', 'MOBILE_PASSWORD', 'Set Password', '<!DOCTYPE html>\n<html style=\"font-size: 1px;font-family:HelveticaNeue;\" xmlns:th=\"http://www.thymeleaf.org\">\n	<meta charset=\"utf-8\">\n	<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no\" />\n	<head>\n		<meta charset=\"UTF-8\">\n		<title>Set Password</title>\n		<style>\n			* {\n				margin: 0;\n				padding: 0;\n				box-sizing: inherit;\n			}\n\n			a,\n			a:hover {\n				text-decoration: none;\n			}\n\n			.emailBox {\n				background: rgba(255, 255, 255, 1);\n				margin: auto;\n				padding: 32px 8% 24px 8%;\n			}\n		</style>\n	</head>\n	<body style=\"background-color: #fff\">\n		<div class=\"emailBox\">\n			<div style=\"font-size:18px;font-family:HelveticaNeue;font-weight:bold;color:rgba(242,53,77,1);\">\n				Hi <span th:text=\"${name}\"></span>,\n			</div>\n			<div style=\"font-size:17px;font-family:HelveticaNeue;font-weight:bold;color:rgba(77,79,92,1);margin-top: 16px\">\n				Set Password\n			</div>\n			<div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n			<div style=\"font-size:14px;font-family:HelveticaNeue;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 20px\">\n				Used to change/reset passwords, please do not disclose the dynamic code to others, valid for 5 minutes, please\n				check it.\n			</div>\n			<div style=\"font-size:12px;font-family:HelveticaNeue;font-weight:400;color:rgba(77,79,92,1);\">\n				RED Pass ：\n			</div>\n			<div style=\"margin-top: 10px\">\n				<div style=\"background:rgba(77,79,92,0.1);font-size: 16px;\n        height: 40px;line-height: 40px;\n        padding:0 15px;border-radius:3px;\">\n					<span th:text=\"${email}\"></span>\n				</div>\n			</div>\n			<div style=\"margin-top: 25px;font-size:12px;color:rgba(77,79,92,1);\">\n				Verification code\n			</div>\n			<div style=\"margin-top: 10px\">\n				<div style=\"background:rgba(77,79,92,0.1);padding:0 15px;height: 36px;line-height: 36px;\n            border-radius:3px;font-size: 16px;font-weight:bold;\n            letter-spacing:5px;display: inline-block\">\n					<span th:text=\"${verificationCode}\"></span>\n				</div>\n			</div>\n\n			<div style=\"text-align: center;margin-top: 45px;display: flex;justify-content: center;flex-direction: row;\">\n				<div style=\"width: 70px;height: 70px;margin-right: 20px\">\n					<img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/redeWeb.svg\' style=\"width: 70px;height: 70px\" alt=\"\">\n				</div>\n				<div style=\"display: flex;flex-direction: column;height: 70px\">\n					<a href=\"\" style=\"text-decoration:none;height: 30px;display: block\">\n						<img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/DowIos.svg\' style=\"\" alt=\"\">\n					</a>\n					<div style=\"flex: 1\"></div>\n					<a href=\"\" style=\"text-decoration:none;height: 30px;display: block\">\n						<img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/DowAndroid.svg\' style=\"\" alt=\"\">\n					</a>\n				</div>\n			</div>\n\n			<div style=\"text-align: center;font-size:12px;margin-top:20px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,0.5);\">\n				<p>\n					contact us at support@redescooter.com.<br />\n					www.redescooter.com\n				</p>\n			</div>\n		</div>\n	</body>\n</html>', 'MOBILE设置密码', NULL, 3600, 0, '2019-10-11 08:48:22', 0, '2019-10-11 08:48:22', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000004, 0, 'NORMAL', 5, 'web_freeze_warn', 'WEB_FREEZE_WARN', 'Account Freeze', '<!DOCTYPE html><html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账户冻结</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n        .emailBox {\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <!--/*@thymesVar id=\"name\" type=\"\"*/-->\n        <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n       Account Freeze\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        Respected customers, your account is frozen in <span th:text=\"${freezeDate}\"></span>. If you have any questions, please contact customer service <span th:text=\"${consumerHotline}\"></span>.\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RED pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"></span>\n        </div>\n    </div>\n\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>\n', 'WEB冻结通知', NULL, 3600, 0, '2019-10-11 08:48:53', 0, '2019-10-11 08:48:53', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000005, 0, 'NORMAL', 6, 'web_renewal_warn', 'WEB_RENEWAL_WARN', 'Account Renewal', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账户续约</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n\n        .emailBox {\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        Account Renewal\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        You account renewal fee 30 days successful, valid until the <span th:text=\"${renewalDate}\"></span> .\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RedE pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"></span>\n        </div>\n    </div>\n\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>\n', 'WEB续约通知', NULL, 3600, 0, '2019-10-11 08:49:12', 0, '2019-10-11 08:49:12', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000006, 0, 'NORMAL', 7, 'web_unfreeze_warn', 'WEB_UNFREEZE_WARN', 'Account Unfreeze', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账户解冻</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n\n        .emailBox {\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        Account Unfreeze\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        Your account unfreeze in <span th:text=\"${unfreezeStatDate}\"></span> , <span th:text=\"${unfreezeEndDate}\"></span> .\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RED pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"></span>\n        </div>\n    </div>\n\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>\n', 'WEB解冻通知', NULL, 3600, 0, '2019-10-11 08:47:58', 0, '2019-10-11 08:47:58', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000007, 0, 'NORMAL', 8, 'web_expired_warn', 'WEB_EXPIRED_WARN', 'Account Expired', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账户过期</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n\n        .emailBox {\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        <span th:text=\"${expiredSubtitle}\"></span>\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        <span th:text=\"${expiredMemo}\"></span>\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RED pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"></span>\n        </div>\n    </div>\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>', 'WEB过期通知', NULL, 3600, 0, '2019-10-11 08:48:22', 0, '2019-10-11 08:48:22', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000008, 0, 'NORMAL', 10, 'customer_inquiry_last_paragraph', 'CUSTOMER_INQUIRY_PAY_DEPOSIT', 'Customer Inquiry Last Paragraph', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账户过期</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n\n        .emailBox {\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        <span th:text=\"${expiredSubtitle}\"></span>\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        <span th:text=\"${expiredMemo}\"></span>\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RedE pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"></span>\n        </div>\n    </div>\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>', '客户询价单支付尾款', NULL, 3600, 0, '2019-10-11 08:48:22', 0, '2019-10-11 08:48:22', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000009, 0, 'NORMAL', 11, 'customer_inquiry_pay_deposit', 'CUSTOMER_INQUIRY_LAST_PARAGRAPH', 'Customer Inquiry Pay Deposit', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账户过期</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n\n        .emailBox {\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        <span th:text=\"${expiredSubtitle}\"></span>\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        <span th:text=\"${expiredMemo}\"></span>\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RED pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"></span>\n        </div>\n    </div>\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>', '客户询价单支付定金', NULL, 3600, 0, '2019-10-11 08:48:22', 0, '2019-10-11 08:48:22', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000010, 0, 'NORMAL', 12, 'subscription_pay_succeed_send_eamil', 'SUBSCRIPTION_PAY_SUCCEED_SEND_EAMIL', 'Confirmation de commande', '<!DOCTYPE html>\r\n<html lang=\"fr\">\r\n\r\n	<head>\r\n		<meta charset=\"UTF-8\">\r\n		<title>Pour les précommandes</title>\r\n		<style>\r\n			* {\r\n				margin: 0;\r\n				padding: 0;\r\n			}\r\n		</style>\r\n	</head>\r\n\r\n	<body style=\"font-family: Avenir-Heavy, Avenir;background-color: #fff;font-size: 14px;\">\r\n		<div style=\"padding: 24px 40px;\">\r\n			<div style=\"padding-bottom: 16px;color: #F2354D;font-size: 18px;font-weight: bold;line-height: 25px;\">\r\n				Bonjour <span th:text=\"${name}\" />,\r\n			</div>\r\n\r\n			<div style=\"color: #4D4F5C;line-height: 24px;\">\r\n				Nous avons bien reçu votre précommande et vous remercions de votre confiance.\r\n				<br>\r\n				<br>\r\n				Votre <span th:text=\"${model}\" /> va être manufacturé avec le plus grand soin selon les spécifications que vous\r\n				avez choisies.\r\n				<br>\r\n				<br>\r\n				Nous vous contacterons quelques jours avant qu\'il ne vous soit expédié afin de finaliser votre paiement et\r\n				choisir votre mode de livraison (Concessionnaire ou option à domicile).\r\n				<br>\r\n				<br>\r\n				Nous vous offrirons plusieurs modes de paiement et\r\n				solutions de financement - et vous assisterons pour optimiser votre expérience.\r\n				<br>\r\n				<br>\r\n				Une question ? Une suggestion ?\r\n				<br>\r\n				Nous restons à votre disposition -\r\n				<br>\r\n				Contactez-nous sur : support@redescooter.com\r\n				<br>\r\n				<br>\r\n				Bien à vous,\r\n				<br>\r\n				<br>\r\n				RED Group - Division Experience Client\r\n			</div>\r\n		</div>\r\n	</body>\r\n\r\n</html>\r\n', '客户定金支付成功', NULL, 3600, 0, '2020-05-21 16:01:39', 0, '2020-05-21 16:02:09', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000013, 0, 'NORMAL', 13, 'forget_psd_send_mail', 'FORGET_PSD_SEND_MAIL', 'Réinitialisation de votre mot de passe', '<!DOCTYPE html>\r\n<html lang=\"fr\">\r\n\r\n	<head>\r\n		<meta charset=\"UTF-8\">\r\n		<title>Réinitialiser le mot de passe</title>\r\n		<style>\r\n			* {\r\n				margin: 0;\r\n				padding: 0;\r\n			}\r\n		</style>\r\n	</head>\r\n\r\n	<body style=\"padding: 0 24px;\">\r\n		<div style=\"padding-top: 40px;color:#F2354D;font-size:18px;font-weight: bold;font-family: HelveticaNeue;\">\r\n			Mot de passe oublié ?\r\n		</div>\r\n\r\n		<div style=\"padding: 16px 0 32px;color: #111;font-family: HelveticaNeue;\">\r\n			Cela arrive ! Il suffit de cliquer sur le lien en dessous, dans un délai de 1 heures après l\'envoi de ce mail.\r\n		</div>\r\n\r\n		<a th:href=\"${url}+${requestId}\" style=\"font-family: HelveticaNeue;display: block; padding: 0 16px; height: 36px; max-width: 300px; line-height: 36px; cursor: pointer; border-radius: 4px; color: #FFF; background-color: #F2354D; font-size: 15px; font-weight: bold; outline: none; border: none;text-align: center;text-decoration: none;\">\r\n			Réinitialiser le mot de passe\r\n		</a>\r\n\r\n		<div style=\"font-family: HelveticaNeue;padding-top: 32px; color: #4D4F5C; font-size: 13px;\">\r\n			Si le bouton ne fonctionne pas, vous pouvez copier et coller le lien suivant dans la barre d\'adresse de votre\r\n			navigateur :\r\n			<a th:href=\"${url}+${requestId}\"><span th:text=\"${url}+${requestId}\" /></a>\r\n		</div>\r\n	</body>\r\n\r\n</html>', '官网忘记密码发送邮件', NULL, 3600, 0, '2019-10-11 08:41:56', 0, '2020-05-27 10:41:56', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000014, 0, 'NORMAL', 14, 'Subscribe_to_email_successfully', 'SUBSCRIBE_TO_EMAIL_SUCCESSFULLY', 'Confirmation d\'inscription à la newsletter RED Electric', '<!DOCTYPE html>\r\n<html lang=\"fr\">\r\n\r\n<head>\r\n  <meta charset=\"UTF-8\">\r\n  <title>Confirmation d\'inscription à la newsletter RED Electric</title>\r\n  <style>\r\n    * {\r\n      margin: 0;\r\n      padding: 0;\r\n    }\r\n\r\n  </style>\r\n</head>\r\n\r\n<body style=\"font-family: Avenir-Heavy, Avenir;background-color: #fff;font-size: 14px;\">\r\n  <div style=\"padding: 24px 40px;\">\r\n    <div style=\"padding-bottom: 16px;color: #F2354D;font-size: 18px;font-weight: bold;line-height: 25px;\">\r\n      Bonjour <span th:text=\"${name}\"></span>,\r\n    </div>\r\n	<br>\r\n    <div style=\"color: #4D4F5C;line-height: 24px;\">\r\n      Nous avons bien reçu votre précommande et vous remercions de votre confiance.\r\n      <br>\r\n      <br>\r\n      Parce que vous êtes une personne spéciale, recevez avant la presse les informations sur le développement de ce scooter RED unique. Vous recevrez également avant tout le monde nos offres exclusives.\r\n      <br>\r\n      <br>\r\n      Une question? Une suggestion? Nous restons à votre disposition\r\n      <br>\r\n      <br>\r\n      Contactez-nous sur : support@redescooter.com\r\n      <br>\r\n      <br>\r\n      Bien à vous,\r\n      <br>\r\n      <br>\r\n      RED Electric - Division Experience Client\r\n    </div>\r\n  </div>\r\n</body>\r\n\r\n</html>\r\n', '官网邮件订阅成功后确认邮件', NULL, 3600, 0, '2019-10-11 08:41:56', 0, '2020-05-27 10:41:56', NULL, NULL, NULL, NULL, 0);


-- ----------------------------
-- Table structure for pla_message
-- ----------------------------
DROP TABLE IF EXISTS `pla_message`;
CREATE TABLE `pla_message`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `system_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统ID',
  `app_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用ID',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `message_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息类型：推送消息PUSH，站内消息SITE',
  `biz_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息类型',
  `biz_id` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '业务Id',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息状态',
  `business_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前业务的业务状态',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息标题',
  `message_priority` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0 无需提示 1 小红点 2 强提醒',
  `content` varchar(102) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `memo` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息内容参数',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  `read_time` datetime(0) NULL DEFAULT NULL COMMENT '已读时间',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_message
-- ----------------------------

-- ----------------------------
-- Table structure for pla_push_result
-- ----------------------------
DROP TABLE IF EXISTS `pla_push_result`;
CREATE TABLE `pla_push_result`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `msg_id` bigint(0) NULL DEFAULT NULL COMMENT '推送返回消息主键 无返回值 默认为0',
  `send_no` int(0) NULL DEFAULT NULL COMMENT '推送序号 无返回值 默认为0',
  `status_code` int(0) NULL DEFAULT NULL COMMENT '状态 无返回值 默认为5 区分极光成功时 返回值是0',
  `error_code` int(0) NULL DEFAULT NULL COMMENT '错误码 由于成功状态 无错误码 自定义为 1  表示成功',
  `error_message` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '错误信息 成功无错误信息 自定义 成功是 错误信息为空',
  `created_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '极光推送结果记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_push_result
-- ----------------------------

-- ----------------------------
-- Table structure for pla_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `pla_sys_config`;
CREATE TABLE `pla_sys_config`  (
  `id` int(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `group` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `value` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `desc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NOT NULL,
  `created_time` datetime(0) NOT NULL,
  `deleted` tinyint(1) NULL DEFAULT 0,
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for pla_sys_sequence
-- ----------------------------
DROP TABLE IF EXISTS `pla_sys_sequence`;
CREATE TABLE `pla_sys_sequence`  (
  `NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'sequence名称',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `CURRENT_VALUE` bigint(0) NOT NULL DEFAULT 1000000 COMMENT '当前值',
  `INCREMENT` int(0) NOT NULL DEFAULT 1 COMMENT '增量',
  `CACHE` int(0) NOT NULL DEFAULT 1000 COMMENT '缓存大小',
  `CREATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `CREATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `UPDATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `UPDATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '序列' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_sys_sequence
-- ----------------------------
INSERT INTO `pla_sys_sequence` VALUES ('OPE_EXCLE_IMPORT', 0, 1002043, 1, 1000, 0, '2020-06-14 17:58:19', 0, '2020-06-14 17:58:19');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_FACTORY', 0, 1002158, 1, 1000, 0, '2020-06-14 18:05:54', 0, '2020-06-14 18:05:54');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_FACTORY_TRACE', 0, 1002060, 1, 1000, 0, '2020-06-14 18:05:54', 0, '2020-06-14 18:05:54');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS', 0, 1002091, 1, 1000, 0, '2020-06-14 18:12:49', 0, '2020-06-14 18:12:49');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS_DRAFT', 0, 1002032, 1, 1000, 0, '2020-06-14 17:58:19', 0, '2020-06-14 17:58:19');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS_DRAFT_HISTORY_RECORD', 0, 1002032, 1, 1000, 0, '2020-06-14 17:58:19', 0, '2020-06-14 17:58:19');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS_PRODUCT', 0, 1002140, 1, 1000, 0, '2020-06-14 18:12:49', 0, '2020-06-14 18:12:49');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS_PRODUCT_B', 0, 1002094, 1, 1000, 0, '2020-06-14 18:12:49', 0, '2020-06-14 18:12:49');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PART_DRAFT_QC_TEMPLATE', 0, 1002082, 1, 1000, 0, '2020-06-14 18:12:46', 0, '2020-06-14 18:12:46');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PART_DRAFT_QC_TEMPLATE_B', 0, 1002129, 1, 1000, 0, '2020-06-14 18:12:46', 0, '2020-06-14 18:12:46');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PART_QC_TEMPLATE', 0, 1002116, 1, 1000, 0, '2020-06-14 18:12:49', 0, '2020-06-14 18:12:49');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PART_QC_TEMPLATE_B', 0, 1002132, 1, 1000, 0, '2020-06-14 18:12:49', 0, '2020-06-14 18:12:49');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PRICE_SHEET', 0, 1002147, 1, 1000, 0, '2020-06-14 18:13:14', 0, '2020-06-14 18:13:14');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PRICE_SHEET_HISTORY', 0, 1002015, 1, 1000, 0, '2020-06-14 18:13:14', 0, '2020-06-14 18:13:14');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PRODUCT_QC_TEMPLATE', 0, 1002049, 1, 1000, 0, '2020-06-14 18:12:49', 0, '2020-06-14 18:12:49');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PRODUCT_QC_TEMPLATE_B', 0, 1002139, 1, 1000, 0, '2020-06-14 18:12:49', 0, '2020-06-14 18:12:49');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_REGIONAL_PRICE_SHEET', 0, 1004201, 1, 1000, 0, '2020-06-14 18:13:22', 0, '2020-06-14 19:24:52');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_REGIONAL_PRICE_SHEET_HISTORY', 0, 1004203, 1, 1000, 0, '2020-06-14 18:13:22', 0, '2020-06-14 19:24:52');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SUPPLIER', 0, 1002016, 1, 1000, 0, '2020-06-14 18:11:53', 0, '2020-06-14 18:11:53');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SUPPLIER_TRACE', 0, 1002080, 1, 1000, 0, '2020-06-14 18:11:53', 0, '2020-06-14 18:11:53');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SYS_ROLE', 0, 1002082, 1, 1000, 0, '2020-06-14 17:26:20', 0, '2020-06-14 17:26:20');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SYS_STAFF', 0, 1002069, 1, 1000, 0, '2020-06-14 17:26:20', 0, '2020-06-14 17:26:20');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SYS_USER', 0, 1002178, 1, 1000, 0, '2020-06-14 17:26:20', 0, '2020-06-14 17:26:20');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SYS_USER_PROFILE', 0, 1002019, 1, 1000, 0, '2020-06-14 17:26:20', 0, '2020-06-14 17:26:20');

-- ----------------------------
-- Table structure for pla_tenant
-- ----------------------------
DROP TABLE IF EXISTS `pla_tenant`;
CREATE TABLE `pla_tenant`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `p_id` bigint(0) NULL DEFAULT NULL COMMENT 'P_ID',
  `tenant_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户名，即客户名',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮件',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `country_id` bigint(0) NULL DEFAULT NULL,
  `city_id` bigint(0) NULL DEFAULT NULL COMMENT 'city_Id',
  `distrust_id` bigint(0) NULL DEFAULT NULL,
  `driver_counts` int(0) NULL DEFAULT NULL COMMENT '司机数量',
  `sales_id` bigint(0) NULL DEFAULT NULL COMMENT '销售',
  `tenant_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源渠道 官网/Email/电话',
  `tenant_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户类型 企业/个人',
  `tenant_industry` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户行业',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `contact` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `position` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位',
  `tenant_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户编码',
  `longitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '纬度',
  `tel_1` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `tel_2` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `tel_3` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email_1` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件',
  `email_2` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件',
  `email_3` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件',
  `time_zone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时区',
  `effective_time` datetime(0) NULL DEFAULT NULL COMMENT '生效时间',
  `activation_time` datetime(0) NULL DEFAULT NULL COMMENT '激活时间',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '到期时间',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '租户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_tenant
-- ----------------------------

-- ----------------------------
-- Table structure for pla_tenant_config
-- ----------------------------
DROP TABLE IF EXISTS `pla_tenant_config`;
CREATE TABLE `pla_tenant_config`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户id',
  `language` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '语言',
  `time_zone` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '时区',
  `longitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '纬度',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '营业状态 OPEN 1、CLOSE  2 营业中、打烊',
  `start_week` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '从周几开始',
  `end_week` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '从周几结束',
  `begin_time` time(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` time(0) NULL DEFAULT NULL COMMENT '结束时间',
  `timeout_expectde` bigint(0) NULL DEFAULT NULL COMMENT '超时预期值,单位min',
  `estimated_duration` bigint(0) NULL DEFAULT NULL COMMENT '估计配送持续时间,单位min',
  `distribution_range` bigint(0) NULL DEFAULT NULL COMMENT '配送范围,单位km',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '租户配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_tenant_config
-- ----------------------------

-- ----------------------------
-- Table structure for pla_tenant_node
-- ----------------------------
DROP TABLE IF EXISTS `pla_tenant_node`;
CREATE TABLE `pla_tenant_node`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户主键',
  `event` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件',
  `event_time` datetime(0) NULL DEFAULT NULL COMMENT '事件时间',
  `memo` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '租户节点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_tenant_node
-- ----------------------------

-- ----------------------------
-- Table structure for pla_user
-- ----------------------------
DROP TABLE IF EXISTS `pla_user`;
CREATE TABLE `pla_user`  (
  `ID` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `TENANT_ID` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `SYSTEM_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统ID',
  `APP_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用ip，SAAS_WEB:SaaS配送，SAAS_APP:SaaS移动，SAAS_REPAIR_WEB:SaaS维修，SES_ROS:RedE办公系统，SES_DEVL:RedE开发系统',
  `LOGIN_NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名',
  `LOGIN_TYPE` int(0) NOT NULL DEFAULT 1 COMMENT '登录类型',
  `STATUS` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态 Normal,Lock,Cancel',
  `USER_TYPE` int(0) NOT NULL DEFAULT 0 COMMENT '用户类型:1餐厅配送SaaS,2快递配送SaaS,3餐厅移动端,4快递移动端,5个人移动端,6维修端',
  `LAST_LOGIN_TIME` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `LAST_LOGIN_TOKEN` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登录TOKEN',
  `EFFECTIVE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '生效时间',
  `ACTIVATION_TIME` datetime(0) NULL DEFAULT NULL COMMENT '激活时间',
  `EXPIRE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '到期时间',
  `CREATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `CREATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `UPDATED_BY` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `UPDATED_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `LAST_LOGIN_IP` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登录IP地址',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_user
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

-- ----------------------------
-- Table structure for pla_user_password
-- ----------------------------
DROP TABLE IF EXISTS `pla_user_password`;
CREATE TABLE `pla_user_password`  (
  `ID` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `login_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名，即用户邮箱',
  `SALT` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `PASSWORD` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账户密码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_user_password
-- ----------------------------

-- ----------------------------
-- Table structure for pla_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `pla_user_permission`;
CREATE TABLE `pla_user_permission`  (
  `ID` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `USER_ID` bigint(0) NOT NULL COMMENT '用户ID',
  `SYSTEM_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统ID',
  `APP_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用ID',
  `STATUS` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'NORMAL' COMMENT '状态',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户应用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pla_user_permission
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
