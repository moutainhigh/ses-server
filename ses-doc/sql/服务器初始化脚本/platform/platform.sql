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

 Date: 29/05/2020 17:25:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pdman_db_version
-- ----------------------------
DROP TABLE IF EXISTS `pdman_db_version`;
CREATE TABLE `pdman_db_version`  (
  `DB_VERSION` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `VERSION_DESC` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CREATED_TIME` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pdman_db_version
-- ----------------------------

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
INSERT INTO `pla_country_code` VALUES (10000, 0, NULL, '86', 'ZH', 'zh-CN', '0', 'China	', NULL, NULL);
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
INSERT INTO `pla_jpush_user` VALUES (1000000, 0, 1000003, '1104a89792c58493f90', NULL, NULL, 'android', 'REGISTRATION_ID', NULL, 0, 'LOGIN', '2020-02-25 08:22:37', 1000003, '2020-02-20 09:03:20', 1000001, NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_jpush_user` VALUES (1002141, 0, 1000005, '140fe1da9ec745086f2', NULL, NULL, 'android', 'REGISTRATION_ID', NULL, 0, 'LOGIN', '2020-03-11 08:55:49', 1000005, '2020-02-20 11:18:43', 1000004, NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_jpush_user` VALUES (1004227, 0, 1000004, '160a3797c89755575cd', NULL, NULL, 'android', 'REGISTRATION_ID', NULL, 0, 'LOGIN', '2020-03-13 09:09:01', 1000004, '2020-03-03 07:52:58', 1000007, NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_jpush_user` VALUES (1006371, 0, 1021170, '140fe1da9ed85c96c03', NULL, NULL, 'android', 'REGISTRATION_ID', NULL, 0, 'LOGIN', '2020-05-27 06:53:25', 1021170, '2020-05-27 06:49:45', 1021170, NULL, NULL, NULL, NULL, 0);

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
INSERT INTO `pla_mail_config` VALUES (10013, 0, 'NORMAL', 13, 'REDE_SES', '4', 'url', 'https://redelectric.fr/officical-m/user/password/reset?requestId=', 0, '2020-05-28 15:59:36', 0, '2020-05-28 15:59:36', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100000, 0, 'NORMAL', 1, 'REDE_SAAS', '1', 'url', 'https://redelectric.fr/account-plugin/#/?requestId=', 0, '2020-05-28 15:59:36', 0, '2020-05-28 15:59:36', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100001, 0, 'NORMAL', 1, 'REDE_SAAS', '3', 'url', 'https://redelectric.fr/repair/#/retrieve?requestId=', 0, '2020-05-28 15:59:37', 0, '2020-05-28 15:59:37', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100002, 0, 'NORMAL', 3, 'REDE_SAAS', '3', 'url', 'https://redelectric.fr/repair/#/retrieve?requestId=', 0, '2020-05-28 15:59:38', 0, '2020-05-28 15:59:38', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100003, 0, 'NORMAL', 3, 'REDE_SAAS', '1', 'url', 'https://redelectric.fr/account-plugin/#/?requestId=', 0, '2020-05-28 15:59:39', 0, '2020-05-28 15:59:39', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100004, 0, 'NORMAL', 4, 'REDE_SAAS', '2', 'verificationCode', NULL, 0, '2020-05-28 15:59:40', 0, '2020-05-28 15:59:40', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100005, 0, 'NORMAL', 4, 'REDE_SES', '2', 'verificationCode', NULL, 0, '2020-05-28 15:59:40', 0, '2020-05-28 15:59:40', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100006, 0, 'NORMAL', 5, 'REDE_SAAS', '1', 'freezeDate', NULL, 0, '2020-05-28 15:59:41', 0, '2020-05-28 15:59:41', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100007, 0, 'NORMAL', 5, 'REDE_SAAS', '1', 'consumerHotline', '18721403004', 0, '2020-05-28 15:59:41', 0, '2020-05-28 15:59:41', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100008, 0, 'NORMAL', 6, 'REDE_SAAS', '1', 'renewalDate', NULL, 0, '2020-05-28 15:59:42', 0, '2020-05-28 15:59:42', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100009, 0, 'NORMAL', 7, 'REDE_SAAS', '1', 'unfreezeStatDate', NULL, 0, '2020-05-28 15:59:44', 0, '2020-05-28 15:59:44', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100010, 0, 'NORMAL', 7, 'REDE_SAAS', '1', 'unfreezeEndDate', NULL, 0, '2020-05-28 15:59:45', 0, '2020-05-28 15:59:45', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_config` VALUES (100011, 0, 'NORMAL', 8, 'REDE_SAAS', '1', 'consumerHotline', '18721403004', 0, '2020-05-28 15:59:45', 0, '2020-05-28 15:59:45', NULL, NULL, NULL, NULL, 0);

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
INSERT INTO `pla_mail_template` VALUES (1000000, 0, 'NORMAL', 1, 'web_activate', 'WEB_ACTIVATE', 'Activation Your Account', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账号激活</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n        .emailBox{\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        Activation your account\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        You can via the RedE Pass/Register email by clicking the button below to activate your account . The link is valid for 72 hours, please check it.\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RedE pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"/>\n        </div>\n    </div>\n    <div style=\"padding-left: 10px;margin-top: 25px\">\n            <div style=\"width:172px;height:44px;text-align: center;line-height: 44px;\n                background:rgba(242,53,77,1);border-radius:5px;font-size:16px;font-family:SourceSansPro-Semibold;\n                font-weight:600;color:rgba(255,255,255,1);\">\n                <a th:href=\"${url}+${requestId}\" style=\"text-decoration:none;color:\n                #ffffff\" >\n                Activate Account\n                </a>\n            </div>\n    </div>\n\n    <div style=\"padding-left: 10px;font-size:12px;font-weight:400;font-family:SourceSansPro-Regular;margin-top: 40px\">\n        <span style=\"color:rgba(77,79,92,1);\">\n            Button not working? Paste the following link into your browser:\n        </span>\n        <a th:href=\"${url}+${requestId}\"\n            style=\"color: rgba(53,130,251,0.8);text-decoration:blink\">\n            <span th:text=\"${url}+${requestId}\" />\n        </a>\n    </div>\n\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>\n', 'WEB账号激活', NULL, 259200, 0, '2019-10-11 08:41:56', 0, '2019-10-11 08:41:56', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000001, 0, 'NORMAL', 2, 'mobile_activate', 'MOBILE_ACTIVATE', 'Activation your account', '<!DOCTYPE html>\n<html style=\"font-size: 1px;font-family:SourceSansPro-Regular;\" xmlns:th=\"http://www.thymeleaf.org\">\n<meta charset=\"utf-8\">\n<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no\" />\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账号激活</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n            box-sizing: inherit;\n        }\n        a , a:hover{\n            text-decoration: none;\n        }\n        .emailBox {\n            background: rgba(255, 255, 255, 1);\n            margin: auto;\n            padding: 32px 8% 24px 8%;\n        }\n    </style>\n</head>\n<body style=\"background-color: #fff\">\n<div class=\"emailBox\">\n    <div style=\"font-size:18px;font-family:HelveticaNeue;font-weight:bold;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"/> ,\n    </div>\n    <div style=\"font-size:17px;font-family:HelveticaNeue;font-weight:bold;color:rgba(77,79,92,1);margin-top: 16px\">\n        Activation Your Account\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:HelveticaNeue;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 20px\">\n        By the button below or scan the QR code to download the app,activate your account via RedE Pass/Register Email.\n    </div>\n    <div style=\"font-size:12px;font-family:HelveticaNeue;font-weight:400;color:rgba(77,79,92,1);\">\n        RedE Pass ：\n    </div>\n    <div style=\"margin-top: 10px\">\n        <div style=\"background:rgba(77,79,92,0.1);font-size: 16px;\n        height: 40px;line-height: 40px;\n        padding:0 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"/>\n        </div>\n    </div>\n    <div style=\"margin-top: 25px;font-size:12px;color:rgba(77,79,92,1);\">\n        <!--Verification code-->\n    </div>\n    <div style=\"margin-top: 10px\">\n        <div style=\"background:rgba(255,255,255,0.1);padding:0 15px;height: 36px;line-height: 36px;\n            border-radius:3px;font-size: 16px;font-weight:bold;\n            letter-spacing:5px;display: inline-block\">\n<!--            46297-->\n        </div>\n    </div>\n\n    <div style=\"text-align: center;margin-top: 45px;display: flex;justify-content: center;flex-direction: row;\">\n        <div style=\"width: 70px;height: 70px;margin-right: 20px\">\n            <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/redeWeb.svg\'\n                 style=\"width: 70px;height: 70px\"\n                 alt=\"\">\n        </div>\n        <div style=\"display: flex;flex-direction: column;height: 70px\">\n            <a href=\"\" style=\"text-decoration:none;height: 30px;display: block\">\n                <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/DowIos.svg\'\n                     style=\"\" alt=\"\">\n            </a>\n            <div style=\"flex: 1\"></div>\n            <a href=\"\" style=\"text-decoration:none;height: 30px;display: block\">\n                <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/DowAndroid.svg\'\n                     style=\"\" alt=\"\">\n            </a>\n        </div>\n    </div>\n\n    <div style=\"text-align: center;font-size:12px;margin-top:20px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,0.5);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>\n', 'MOBILE账号激活', NULL, 259200, 0, '2019-10-11 08:47:37', 0, '2019-10-11 08:47:37', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000002, 0, 'NORMAL', 3, 'web_password', 'WEB_PASSWORD', 'Set Password', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>Set Password</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n        .emailBox{\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        Reset Password\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        If you need to change or reset your password, you can click the button below to reset your password. Valid for 5 minutes, please check it.\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        <!--RedE pass ：-->\n    </div>\n\n  <div style=\"padding-left: 10px;margin-top: 25px\">\n      <a th:href=\"${url}+${requestId}\" style=\"text-decoration:none\">\n          <div style=\"width:172px;height:44px;text-align: center;line-height: 44px;\n              background:rgba(242,53,77,1);border-radius:5px;font-size:16px;font-family:SourceSansPro-Semibold;\n              font-weight:600;color:rgba(255,255,255,1);\">\n              Set Password\n          </div>\n      </a>\n  </div>\n\n    <div style=\"padding-left: 10px;font-size:12px;font-weight:400;font-family:SourceSansPro-Regular;margin-top: 40px\">\n        <span style=\"color:rgba(77,79,92,1);\">\n            Button not working? Paste the following link into your browser:\n        </span>\n        <a th:href=\"${url}+${requestId}\"\n           style=\"color: rgba(53,130,251,0.8);text-decoration:blink\">\n            <span th:text=\"${url}+${requestId}\" />\n        </a>\n    </div>\n  <div style=\"height: 100px\">\n  </div>\n\n  <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n          font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n      <p>\n          contact us at support@redescooter.com.<br/>\n          www.redescooter.com\n      </p>\n  </div>\n</div>\n</body>\n</html>\n', 'WEB设置密码', NULL, 259200, 0, '2019-10-11 08:47:58', 0, '2019-10-11 08:47:58', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000003, 0, 'NORMAL', 4, 'mobile_password', 'MOBILE_PASSWORD', 'Set Password', '<!DOCTYPE html>\n<html style=\"font-size: 1px;font-family:HelveticaNeue;\" xmlns:th=\"http://www.thymeleaf.org\">\n	<meta charset=\"utf-8\">\n	<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no\" />\n	<head>\n		<meta charset=\"UTF-8\">\n		<title>Set Password</title>\n		<style>\n			* {\n				margin: 0;\n				padding: 0;\n				box-sizing: inherit;\n			}\n\n			a,\n			a:hover {\n				text-decoration: none;\n			}\n\n			.emailBox {\n				background: rgba(255, 255, 255, 1);\n				margin: auto;\n				padding: 32px 8% 24px 8%;\n			}\n		</style>\n	</head>\n	<body style=\"background-color: #fff\">\n		<div class=\"emailBox\">\n			<div style=\"font-size:18px;font-family:HelveticaNeue;font-weight:bold;color:rgba(242,53,77,1);\">\n				Hi <span th:text=\"${name}\"></span>,\n			</div>\n			<div style=\"font-size:17px;font-family:HelveticaNeue;font-weight:bold;color:rgba(77,79,92,1);margin-top: 16px\">\n				Set Password\n			</div>\n			<div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n			<div style=\"font-size:14px;font-family:HelveticaNeue;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 20px\">\n				Used to change/reset passwords, please do not disclose the dynamic code to others, valid for 5 minutes, please\n				check it.\n			</div>\n			<div style=\"font-size:12px;font-family:HelveticaNeue;font-weight:400;color:rgba(77,79,92,1);\">\n				RedE Pass ：\n			</div>\n			<div style=\"margin-top: 10px\">\n				<div style=\"background:rgba(77,79,92,0.1);font-size: 16px;\n        height: 40px;line-height: 40px;\n        padding:0 15px;border-radius:3px;\">\n					<span th:text=\"${email}\"></span>\n				</div>\n			</div>\n			<div style=\"margin-top: 25px;font-size:12px;color:rgba(77,79,92,1);\">\n				Verification code\n			</div>\n			<div style=\"margin-top: 10px\">\n				<div style=\"background:rgba(77,79,92,0.1);padding:0 15px;height: 36px;line-height: 36px;\n            border-radius:3px;font-size: 16px;font-weight:bold;\n            letter-spacing:5px;display: inline-block\">\n					<span th:text=\"${verificationCode}\"></span>\n				</div>\n			</div>\n\n			<div style=\"text-align: center;margin-top: 45px;display: flex;justify-content: center;flex-direction: row;\">\n				<div style=\"width: 70px;height: 70px;margin-right: 20px\">\n					<img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/redeWeb.svg\' style=\"width: 70px;height: 70px\" alt=\"\">\n				</div>\n				<div style=\"display: flex;flex-direction: column;height: 70px\">\n					<a href=\"\" style=\"text-decoration:none;height: 30px;display: block\">\n						<img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/DowIos.svg\' style=\"\" alt=\"\">\n					</a>\n					<div style=\"flex: 1\"></div>\n					<a href=\"\" style=\"text-decoration:none;height: 30px;display: block\">\n						<img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/DowAndroid.svg\' style=\"\" alt=\"\">\n					</a>\n				</div>\n			</div>\n\n			<div style=\"text-align: center;font-size:12px;margin-top:20px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,0.5);\">\n				<p>\n					contact us at support@redescooter.com.<br />\n					www.redescooter.com\n				</p>\n			</div>\n		</div>\n	</body>\n</html>', 'MOBILE设置密码', NULL, 259200, 0, '2019-10-11 08:48:22', 0, '2019-10-11 08:48:22', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000004, 0, 'NORMAL', 5, 'web_freeze_warn', 'WEB_FREEZE_WARN', 'Account Freeze', '<!DOCTYPE html><html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账户冻结</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n        .emailBox {\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <!--/*@thymesVar id=\"name\" type=\"\"*/-->\n        <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n       Account Freeze\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        Respected customers, your account is frozen in <span th:text=\"${freezeDate}\"></span>. If you have any questions, please contact customer service <span th:text=\"${consumerHotline}\"></span>.\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RedE pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"></span>\n        </div>\n    </div>\n\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>\n', 'WEB冻结通知', NULL, 259200, 0, '2019-10-11 08:48:53', 0, '2019-10-11 08:48:53', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000005, 0, 'NORMAL', 6, 'web_renewal_warn', 'WEB_RENEWAL_WARN', 'Account Renewal', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账户续约</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n\n        .emailBox {\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        Account Renewal\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        You account renewal fee 30 days successful, valid until the <span th:text=\"${renewalDate}\"></span> .\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RedE pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"></span>\n        </div>\n    </div>\n\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>\n', 'WEB续约通知', NULL, 259200, 0, '2019-10-11 08:49:12', 0, '2019-10-11 08:49:12', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000006, 0, 'NORMAL', 7, 'web_unfreeze_warn', 'WEB_UNFREEZE_WARN', 'Account Unfreeze', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账户解冻</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n\n        .emailBox {\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        Account Unfreeze\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        Your account unfreeze in <span th:text=\"${unfreezeStatDate}\"></span> , <span th:text=\"${unfreezeEndDate}\"></span> .\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RedE pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"></span>\n        </div>\n    </div>\n\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>\n', 'WEB解冻通知', NULL, 259200, 0, '2019-10-11 08:47:58', 0, '2019-10-11 08:47:58', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000007, 0, 'NORMAL', 8, 'web_expired_warn', 'WEB_EXPIRED_WARN', 'Account Expired', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账户过期</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n\n        .emailBox {\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        <span th:text=\"${expiredSubtitle}\"></span>\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        <span th:text=\"${expiredMemo}\"></span>\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RedE pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"></span>\n        </div>\n    </div>\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>', 'WEB过期通知', NULL, 259200, 0, '2019-10-11 08:48:22', 0, '2019-10-11 08:48:22', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000008, 0, 'NORMAL', 10, 'customer_inquiry_last_paragraph', 'CUSTOMER_INQUIRY_PAY_DEPOSIT', 'Customer Inquiry Last Paragraph', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账户过期</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n\n        .emailBox {\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        <span th:text=\"${expiredSubtitle}\"></span>\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        <span th:text=\"${expiredMemo}\"></span>\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RedE pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"></span>\n        </div>\n    </div>\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>\n</html>', '客户询价单支付尾款', NULL, 259200, 0, '2019-10-11 08:48:22', 0, '2019-10-11 08:48:22', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000009, 0, 'NORMAL', 11, 'customer_inquiry_pay_deposit', 'CUSTOMER_INQUIRY_LAST_PARAGRAPH', 'Customer Inquiry Pay Deposit', '<!DOCTYPE html>\n<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>账户过期</title>\n    <style>\n        * {\n            margin: 0;\n            padding: 0;\n        }\n\n        .emailBox {\n            background:rgba(255,255,255,1);\n            margin: auto;\n            padding: 32px 2% 24px 2%;\n            position: relative;\n        }\n    </style>\n</head>\n<body style=\"background:rgba(245,245,245,1);\">\n<div class=\"emailBox\">\n    <div style=\"text-align: right\">\n        <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\n    </div>\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\n        Hi <span th:text=\"${name}\"></span>,\n    </div>\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\n        <span th:text=\"${expiredSubtitle}\"></span>\n    </div>\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\n\n    <div style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\n        <span th:text=\"${expiredMemo}\"></span>\n    </div>\n    <div style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\n        RedE pass ：\n    </div>\n    <div style=\"padding: 0 40px 0 10px;margin-top: 10px\">\n        <div style=\"background:rgba(250,250,250,1);padding: 15px;border-radius:3px;\">\n            <span th:text=\"${email}\"></span>\n        </div>\n    </div>\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\n            font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\n        <p>\n            contact us at support@redescooter.com.<br/>\n            www.redescooter.com\n        </p>\n    </div>\n</div>\n</body>', '客户询价单支付定金', NULL, 259200, 0, '2019-10-11 08:48:22', 0, '2019-10-11 08:48:22', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000010, 0, 'NORMAL', 12, 'subscription_pay_succeed_send_eamil', 'SUBSCRIPTION_PAY_SUCCEED_SEND_EAMIL', 'Subscription Pay Succeed', '<!DOCTYPE html>\r\n<html lang=\"fr\" xmlns:th=\"http://www.thymeleaf.org\">\r\n\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <title>Pour les précommandes</title>\r\n    <style>\r\n        * {\r\n            margin: 0;\r\n            padding: 0;\r\n        }\r\n\r\n    </style>\r\n</head>\r\n\r\n<body style=\"font-family: Avenir-Heavy, Avenir;background-color: #fff;font-size: 14px;\">\r\n    <div style=\"padding: 24px 40px;\">\r\n        <div style=\"padding-bottom: 16px;color: #F2354D;font-size: 18px;font-weight: bold;line-height: 25px;\">\r\n            Hello <span th:text=\"${name}\"/> ,\r\n        </div>\r\n\r\n        <div style=\"color: #4D4F5C;line-height: 24px;\">\r\n            Nous avons bien reçu votre précommande et vous remercions de votre confiance.\r\n            <br>\r\n            <br>\r\n            Votre RedE 2GO va être manufacturé avec le plus grand soin selon les spécifications que vous avez choisies.\r\n            <br>\r\n            <br>\r\n            Nous vous contacterons quelques jours avant qu’il ne vous soit expédié afin de finaliser votre paiement et\r\n            choisir votre mode de livraison (Concessionnaire ou option à domicile).\r\n            <br>\r\n            <br>\r\n            Nous vous offrirons plusieurs modes de paiement et\r\n            solutions de financement - et vous assisterons pour optimiser votre expérience.\r\n            <br>\r\n            <br>\r\n            Une question ? Une suggestion ?\r\n            <br>\r\n            Nous restons à votre disposition -\r\n            <br>\r\n            Contactez-nous sur : support@redescooter.com\r\n            <br>\r\n            <br>\r\n            Bien à vous,\r\n            <br>\r\n            <br>\r\n            RedE Group - Division Experience Client\r\n        </div>\r\n    </div>\r\n</body>\r\n\r\n</html>\r\n', '客户定金支付成功', NULL, 259200, 0, '2020-05-21 16:01:39', 0, '2020-05-21 16:02:09', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_mail_template` VALUES (1000013, 0, 'NORMAL', 13, 'forget_psd_send_mail', 'FORGET_PSD_SEND_MAIL', 'Forget Your Password', '<!DOCTYPE html>\r\n<html lang=\"fr\">\r\n\r\n<head>\r\n  <meta charset=\"UTF-8\">\r\n  <title>Set Password</title>\r\n  <style>\r\n    * {\r\n      margin: 0;\r\n      padding: 0;\r\n    }\r\n\r\n    .emailBox {\r\n      background: rgba(255, 255, 255, 1);\r\n      margin: auto;\r\n      padding: 32px 2% 24px 2%;\r\n      position: relative;\r\n    }\r\n\r\n  </style>\r\n</head>\r\n\r\n<body style=\"background:rgba(245,245,245,1);\">\r\n  <div class=\"emailBox\">\r\n    <div style=\"text-align: right\">\r\n      <img src=\'https://rede.oss-cn-shanghai.aliyuncs.com/system/logo1.png\' style=\"width:67px;height:20px;\" alt=\"\">\r\n    </div>\r\n    <div style=\"font-size:26px;font-family:SourceSansPro-Semibold;font-weight:600;color:rgba(242,53,77,1);\">\r\n      Hi <span th:text=\"${name}\"></span>,\r\n    </div>\r\n    <div style=\"font-size:17px;font-family:SourceSansPro-Bold;font-weight:bold;color:rgba(77,79,92,1);margin-top: 25px\">\r\n      Reset Password\r\n    </div>\r\n    <div style=\"height: 1px;background-color: rgba(74, 74, 74, 0.1);margin: 15px 0\"></div>\r\n\r\n    <div\r\n      style=\"font-size:14px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);line-height: 25px;margin-bottom: 65px\">\r\n      If you need to change or reset your password, you can click the button below to reset your password. Valid for 5\r\n      minutes, please check it.\r\n    </div>\r\n    <div\r\n      style=\"font-size:16px;font-family:SourceSansPro-Regular;font-weight:400;color:rgba(77,79,92,1);padding-left: 10px\">\r\n      <!--RedE pass ：-->\r\n    </div>\r\n\r\n    <div style=\"padding-left: 10px;margin-top: 25px\">\r\n      <a th:href=\"${url}+${requestId}\" style=\"text-decoration:none\">\r\n        <div style=\"width:172px;height:44px;text-align: center;line-height: 44px;\r\n              background:rgba(242,53,77,1);border-radius:5px;font-size:16px;font-family:SourceSansPro-Semibold;\r\n              font-weight:600;color:rgba(255,255,255,1);\">\r\n          Set Password\r\n        </div>\r\n      </a>\r\n    </div>\r\n\r\n    <div style=\"padding-left: 10px;font-size:12px;font-weight:400;font-family:SourceSansPro-Regular;margin-top: 40px\">\r\n      <span style=\"color:rgba(77,79,92,1);\">\r\n        Button not working? Paste the following link into your browser:\r\n      </span>\r\n      <a th:href=\"${url}+${requestId}\" style=\"color: rgba(53,130,251,0.8);text-decoration:blink\">\r\n        <span th:text=\"${url}+${requestId}\" />\r\n      </a>\r\n    </div>\r\n    <div style=\"height: 100px\">\r\n    </div>\r\n\r\n    <div style=\"text-align: center;font-size:12px;margin-top: 150px;\r\n          font-family:PingFangSC-Regular;font-weight:400;color:rgba(77,79,92,1);\">\r\n      <p>\r\n        contact us at support@redescooter.com.<br />\r\n        www.redescooter.com\r\n      </p>\r\n    </div>\r\n  </div>\r\n</body>\r\n\r\n</html>\r\n', '官网忘记密码发送邮件', NULL, 259200, 0, '2019-10-11 08:41:56', 0, '2020-05-27 10:41:56', NULL, NULL, NULL, NULL, 0);

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
INSERT INTO `pla_sys_sequence` VALUES ('CON_USER_PROFILE', 0, 1021059, 1, 1000, 0, '2020-02-20 08:36:56', 0, '2020-05-26 09:27:07');
INSERT INTO `pla_sys_sequence` VALUES ('COR_DELIVERY', 0, 1004146, 1, 1000, 0, '2020-02-20 09:10:21', 0, '2020-02-20 10:22:29');
INSERT INTO `pla_sys_sequence` VALUES ('COR_DELIVERY_TRACE', 0, 1004131, 1, 1000, 0, '2020-02-20 09:10:21', 0, '2020-02-20 10:22:29');
INSERT INTO `pla_sys_sequence` VALUES ('COR_DRIVER', 0, 1006142, 1, 1000, 0, '2020-02-20 07:57:07', 0, '2020-05-25 10:50:54');
INSERT INTO `pla_sys_sequence` VALUES ('COR_DRIVER_SCOOTER', 0, 1012700, 1, 1000, 0, '2020-02-20 07:57:08', 0, '2020-05-25 10:50:54');
INSERT INTO `pla_sys_sequence` VALUES ('COR_EXPRESS_DELIVERY', 0, 1002165, 1, 1000, 0, '2020-02-25 08:17:12', 0, '2020-02-25 08:17:12');
INSERT INTO `pla_sys_sequence` VALUES ('COR_EXPRESS_DELIVERY_DETAIL', 0, 1002122, 1, 1000, 0, '2020-02-25 08:17:12', 0, '2020-02-25 08:17:12');
INSERT INTO `pla_sys_sequence` VALUES ('COR_EXPRESS_ORDER', 0, 1006314, 1, 1000, 0, '2020-02-20 09:12:55', 0, '2020-05-20 13:00:15');
INSERT INTO `pla_sys_sequence` VALUES ('COR_EXPRESS_ORDER_TRACE', 0, 1006462, 1, 1000, 0, '2020-02-20 09:12:55', 0, '2020-05-20 13:00:15');
INSERT INTO `pla_sys_sequence` VALUES ('COR_USER_PROFILE', 0, 1012613, 1, 1000, 0, '2020-02-20 07:49:43', 0, '2020-05-25 10:48:20');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_ALLOCATE', 0, 1014732, 1, 1000, 0, '2020-04-27 21:01:57', 0, '2020-05-28 05:59:37');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_ALLOCATE_B', 0, 1014817, 1, 1000, 0, '2020-04-27 21:01:57', 0, '2020-05-28 05:59:37');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_ALLOCATE_B_TRACE', 0, 1014770, 1, 1000, 0, '2020-04-27 21:11:22', 0, '2020-05-28 06:03:41');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_ALLOCATE_TRACE', 0, 1018712, 1, 1000, 0, '2020-04-27 21:05:16', 0, '2020-05-28 06:03:06');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_ASSEMBIY_ORDER_TRACE', 0, 1016737, 1, 1000, 0, '2020-04-27 21:45:47', 0, '2020-05-26 08:09:12');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_ASSEMBLY_B_QC', 0, 1010581, 1, 1000, 0, '2020-04-28 12:57:14', 0, '2020-05-26 08:10:39');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_ASSEMBLY_LOT_TRACE', 0, 1004268, 1, 1000, 0, '2020-05-04 01:00:07', 0, '2020-05-26 08:10:39');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_ASSEMBLY_ORDER', 0, 1010484, 1, 1000, 0, '2020-04-27 21:43:35', 0, '2020-05-28 06:08:23');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_ASSEMBLY_ORDER_PART', 0, 1010459, 1, 1000, 0, '2020-04-27 21:44:58', 0, '2020-05-28 06:08:23');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_ASSEMBLY_PREPARATION', 0, 1008547, 1, 1000, 0, '2020-04-27 21:56:37', 0, '2020-05-26 08:09:12');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_ASSEMBLY_QC_ITEM', 0, 1010461, 1, 1000, 0, '2020-04-28 12:56:55', 0, '2020-05-26 08:10:39');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_ASSEMBLY_QC_TRACE', 0, 1010552, 1, 1000, 0, '2020-04-28 12:57:02', 0, '2020-05-26 08:10:39');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_CUSTOMER', 0, 1027236, 1, 1000, 0, '2020-02-20 07:48:27', 0, '2020-05-29 07:56:50');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_CUSTOMER_INQUIRY', 0, 1014537, 1, 1000, 0, '2020-03-06 05:48:20', 0, '2020-05-28 12:42:59');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_CUSTOMER_INQUIRY_B', 0, 1010526, 1, 1000, 0, '2020-05-25 07:25:14', 0, '2020-05-28 12:42:59');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_EXCLE_IMPORT', 0, 1014511, 1, 1000, 0, '2020-03-06 10:17:51', 0, '2020-05-29 16:21:46');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_FACTORY', 0, 1010618, 1, 1000, 0, '2020-02-21 07:30:59', 0, '2020-05-29 16:18:29');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_FACTORY_TRACE', 0, 1012584, 1, 1000, 0, '2020-02-21 08:41:52', 0, '2020-05-29 16:18:29');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS', 0, 1026499, 1, 1000, 0, '2020-02-26 10:38:21', 0, '2020-05-29 16:29:22');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS_ASSEMBLY', 0, 1014713, 1, 1000, 0, '2020-02-27 06:56:51', 0, '2020-03-03 06:03:59');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS_ASSEMBLY_B', 0, 1014605, 1, 1000, 0, '2020-02-27 07:22:20', 0, '2020-03-03 06:03:59');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS_DRAFT', 0, 1012552, 1, 1000, 0, '2020-04-27 19:21:27', 0, '2020-05-29 16:21:46');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS_DRAFT_HISTORY_RECORD', 0, 1014631, 1, 1000, 0, '2020-04-27 19:21:27', 0, '2020-05-29 16:21:46');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS_HISTORY_RECORD', 0, 1015998, 1, 1000, 0, '2020-02-26 17:11:44', 0, '2020-03-09 08:44:51');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS_PRODUCT', 0, 1016664, 1, 1000, 0, '2020-03-06 10:30:15', 0, '2020-05-29 16:29:22');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS_PRODUCT_B', 0, 1016869, 1, 1000, 0, '2020-03-06 10:30:15', 0, '2020-05-29 16:29:22');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PARTS_TYPE', 0, 1002140, 1, 1000, 0, '2020-02-27 08:34:19', 0, '2020-02-27 08:34:19');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PART_DRAFT_QC_TEMPLATE', 0, 1012475, 1, 1000, 0, '2020-04-27 19:22:18', 0, '2020-05-29 16:28:15');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PART_DRAFT_QC_TEMPLATE_B', 0, 1012654, 1, 1000, 0, '2020-04-27 19:22:18', 0, '2020-05-29 16:28:15');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PART_QC_TEMPLATE', 0, 1012480, 1, 1000, 0, '2020-04-27 19:22:34', 0, '2020-05-29 16:29:22');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PART_QC_TEMPLATE_B', 0, 1012632, 1, 1000, 0, '2020-04-27 19:22:35', 0, '2020-05-29 16:29:22');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PRICE_SHEET', 0, 1029175, 1, 1000, 0, '2020-03-02 09:16:59', 0, '2020-05-29 16:30:19');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PRICE_SHEET_HISTORY', 0, 1031257, 1, 1000, 0, '2020-03-02 09:29:59', 0, '2020-05-29 16:30:19');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PRODUCT_ASSEMBLY', 0, 1008436, 1, 1000, 0, '2020-04-28 10:52:35', 0, '2020-05-26 08:09:31');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PRODUCT_ASSEMBLY_B', 0, 1008491, 1, 1000, 0, '2020-04-28 10:52:35', 0, '2020-05-26 08:09:31');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PRODUCT_QC_TEMPLATE', 0, 1018649, 1, 1000, 0, '2020-04-27 19:22:35', 0, '2020-05-29 16:29:22');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PRODUCT_QC_TEMPLATE_B', 0, 1018838, 1, 1000, 0, '2020-04-27 19:22:35', 0, '2020-05-29 16:29:22');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PURCHAS', 0, 1023038, 1, 1000, 0, '2020-04-27 19:23:24', 0, '2020-05-28 05:31:47');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PURCHAS_B', 0, 1022949, 1, 1000, 0, '2020-04-27 19:23:25', 0, '2020-05-28 05:30:01');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PURCHAS_B_QC', 0, 1021235, 1, 1000, 0, '2020-04-27 19:44:15', 0, '2020-05-28 05:53:09');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PURCHAS_B_QC_ITEM', 0, 1022986, 1, 1000, 0, '2020-04-27 19:44:16', 0, '2020-05-28 05:53:09');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PURCHAS_LOT_TRACE', 0, 1014744, 1, 1000, 0, '2020-04-29 17:00:25', 0, '2020-05-26 03:23:11');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PURCHAS_PAYMENT', 0, 1023156, 1, 1000, 0, '2020-04-27 19:23:25', 0, '2020-05-28 05:31:58');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PURCHAS_PRODUCT', 0, 1023195, 1, 1000, 0, '2020-04-27 19:23:25', 0, '2020-05-28 05:34:17');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PURCHAS_QC_TRACE', 0, 1023129, 1, 1000, 0, '2020-04-27 19:47:06', 0, '2020-05-28 05:53:09');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_PURCHAS_TRACE', 0, 1031422, 1, 1000, 0, '2020-04-27 19:23:26', 0, '2020-05-28 05:35:15');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_REGIONAL_PRICE_SHEET', 0, 1021080, 1, 1000, 0, '2020-03-02 11:03:12', 0, '2020-05-29 16:30:12');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_REGIONAL_PRICE_SHEET_HISTORY', 0, 1027172, 1, 1000, 0, '2020-03-02 11:03:13', 0, '2020-05-29 16:30:13');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_STOCK', 0, 1023176, 1, 1000, 0, '2020-04-27 20:49:50', 0, '2020-05-28 05:57:19');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_STOCK_BILL', 0, 1023092, 1, 1000, 0, '2020-04-27 20:51:54', 0, '2020-05-28 05:57:19');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_STOCK_PROD_PART', 0, 1016805, 1, 1000, 0, '2020-04-27 21:39:53', 0, '2020-05-28 06:04:04');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_STOCK_PROD_PRODUCT', 0, 1008392, 1, 1000, 0, '2020-04-28 14:06:11', 0, '2020-05-26 08:10:50');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_STOCK_PURCHAS', 0, 1014613, 1, 1000, 0, '2020-04-27 20:56:49', 0, '2020-05-28 05:57:19');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SUPPLIER', 0, 1008297, 1, 1000, 0, '2020-02-21 09:15:14', 0, '2020-05-29 16:21:15');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SUPPLIER_TRACE', 0, 1008485, 1, 1000, 0, '2020-02-21 09:15:14', 0, '2020-05-29 16:21:15');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SYS_DEPT', 0, 1006319, 1, 1000, 0, '2020-03-12 10:10:41', 0, '2020-03-16 08:42:02');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SYS_MENU', 0, 1012541, 1, 1000, 0, '2020-03-12 10:17:25', 0, '2020-05-22 11:22:55');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SYS_ROLE', 0, 1008485, 1, 1000, 0, '2020-03-13 02:24:24', 0, '2020-05-22 03:07:02');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SYS_STAFF', 0, 1010593, 1, 1000, 0, '2020-03-13 09:28:04', 0, '2020-05-28 08:37:52');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SYS_USER', 0, 1014644, 1, 1000, 0, '2020-02-20 07:13:16', 0, '2020-05-28 08:37:52');
INSERT INTO `pla_sys_sequence` VALUES ('OPE_SYS_USER_PROFILE', 0, 1014664, 1, 1000, 0, '2020-02-20 07:13:16', 0, '2020-05-28 08:37:52');
INSERT INTO `pla_sys_sequence` VALUES ('PLA_JPUSHUSER', 0, 1008459, 1, 1000, 0, '2020-02-20 09:03:20', 0, '2020-05-27 06:49:44');
INSERT INTO `pla_sys_sequence` VALUES ('PLA_MAIL_TASK', 0, 1073771, 1, 1000, 0, '2020-02-20 07:49:44', 0, '2020-05-28 12:43:02');
INSERT INTO `pla_sys_sequence` VALUES ('PLA_MESSAGE', 0, 1004165, 1, 1000, 0, '2020-02-20 10:26:52', 0, '2020-02-25 08:17:15');
INSERT INTO `pla_sys_sequence` VALUES ('PLA_TENANT', 0, 1008361, 1, 1000, 0, '2020-02-20 07:49:43', 0, '2020-05-25 10:48:20');
INSERT INTO `pla_sys_sequence` VALUES ('PLA_TENANT_CONFIG', 0, 1008495, 1, 1000, 0, '2020-02-20 07:49:43', 0, '2020-05-25 10:48:20');
INSERT INTO `pla_sys_sequence` VALUES ('PLA_TENANT_NODE', 0, 1012708, 1, 1000, 0, '2020-02-20 07:49:43', 0, '2020-05-25 10:48:20');
INSERT INTO `pla_sys_sequence` VALUES ('PLA_USER', 0, 1023302, 1, 1000, 0, '2020-02-20 07:49:43', 0, '2020-05-25 10:48:20');
INSERT INTO `pla_sys_sequence` VALUES ('PLA_USER_NODE', 0, 1018792, 1, 1000, 0, '2020-03-17 16:34:21', 0, '2020-05-25 10:48:20');
INSERT INTO `pla_sys_sequence` VALUES ('PLA_USER_PASSWORD', 0, 1023058, 1, 1000, 0, '2020-02-20 07:49:43', 0, '2020-05-25 10:48:20');
INSERT INTO `pla_sys_sequence` VALUES ('PLA_USER_PERMISSION', 0, 1022969, 1, 1000, 0, '2020-02-20 07:49:43', 0, '2020-05-25 10:48:20');
INSERT INTO `pla_sys_sequence` VALUES ('SCO_SCOOTER', 0, 1006326, 1, 1000, 0, '2020-02-20 09:10:49', 0, '2020-02-25 08:23:55');
INSERT INTO `pla_sys_sequence` VALUES ('SCO_SCOOTER_ACTION_TRACE', 0, 1008416, 1, 1000, 0, '2020-02-20 09:03:24', 0, '2020-03-03 08:02:03');

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
INSERT INTO `pla_tenant` VALUES (1006344, 0, 0, 'amy', 'amy@redescooter.com', '1', NULL, 1000005, NULL, 1, NULL, '1', '1', '1', 'SSSniperWolf House, Palisade Rim Drive, 亨德森内华达美国', 'amy', NULL, NULL, -115.0370124000, 36.0017375000, '1234567896', NULL, NULL, NULL, NULL, NULL, ' 08:00', '2020-06-14 16:00:00', '2020-05-25 10:48:48', '2020-06-15 15:59:59', 1000006, '2020-05-25 10:48:21', 1021166, '2020-05-25 10:50:54', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_tenant` VALUES (1006345, 0, 0, 'R', 'Davina@redescooter.com', '1', NULL, 1000005, 1000040, 1, NULL, '1', '1', '1', 'Paris, 法国', 'Davina', NULL, NULL, 2.3522219000, 48.8566140000, '13488827272', NULL, NULL, NULL, NULL, NULL, ' 08:00', '2020-05-26 16:00:00', '2020-05-27 06:34:05', '2021-06-26 15:59:59', 1000010, '2020-05-27 06:29:45', 1021169, '2020-05-27 06:43:50', NULL, NULL, NULL, NULL, 0);

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
INSERT INTO `pla_tenant_config` VALUES (1006350, 0, 1006344, NULL, ' 08:00', -115.0370124000, 36.0017375000, 'SSSniperWolf House, Palisade Rim Drive, 亨德森内华达美国', '1', '1', '0', '10:00:00', '16:00:00', 45, 45, 5, 0, '2020-05-25 10:48:21', 0, '2020-05-25 10:48:21', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_tenant_config` VALUES (1006351, 0, 1006345, NULL, ' 08:00', 2.3522219000, 48.8566140000, 'Paris, 法国', '1', '1', '4', '10:00:00', '17:00:00', 45, 45, 5, 0, '2020-05-27 06:29:45', 0, '2020-05-27 06:29:45', NULL, NULL, NULL, NULL, 0);

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
INSERT INTO `pla_tenant_node` VALUES (1010567, 0, 1006344, '1', '2020-05-25 10:48:21', NULL, '2020-05-25 10:48:21', 1000006, '2020-05-25 10:48:21', 1000006, NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_tenant_node` VALUES (1010568, 0, 1006345, '1', '2020-05-27 06:29:45', NULL, '2020-05-27 06:29:45', 1000010, '2020-05-27 06:29:45', 1000010, NULL, NULL, NULL, NULL, 0);

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
INSERT INTO `pla_user` VALUES (1021166, 0, 1006344, 'REDE_SAAS', '1', 'amy@redescooter.com', 1, '1', 2, '2020-05-25 10:49:04', '722fc2aad6bd40ff9872507b49c243f5', '2020-06-14 16:00:00', NULL, '2020-06-15 15:59:59', 1000006, '2020-05-25 10:48:21', 1000006, '2020-05-25 10:49:04', '192.168.2.200', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_user` VALUES (1021167, 0, 1006344, 'REDE_SAAS', '2', 'amy@redessscooter.com', 1, '0', 4, NULL, NULL, NULL, NULL, NULL, 1021166, '2020-05-25 10:50:55', 1021166, '2020-05-25 10:50:55', NULL, 'ACCOUNT::ACTIVAT::BEFORE', NULL, NULL, NULL, 0);
INSERT INTO `pla_user` VALUES (1021168, 0, 0, 'REDE_SAAS', '2', 'jnane@redescooter.com', 1, '1', 6, NULL, NULL, '2020-05-25 16:00:00', NULL, '2020-06-27 15:59:59', 1000002, '2020-05-26 09:27:08', 1000002, '2020-05-26 09:27:08', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_user` VALUES (1021169, 0, 1006345, 'REDE_SAAS', '1', 'Davina@redescooter.com', 1, '1', 2, '2020-05-29 03:36:08', 'eb6b3b2de0864553bc806c3412df47da', '2020-05-26 16:00:00', NULL, '2021-06-26 15:59:59', 1000010, '2020-05-27 06:29:45', 1000010, '2020-05-29 03:36:08', '192.168.2.200', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_user` VALUES (1021170, 0, 1006345, 'REDE_SAAS', '2', 'Davina940@163.com', 1, '1', 4, '2020-05-27 06:53:25', 'dd811abba5fd4669bd79cd3968146776', NULL, '2020-05-27 06:49:26', NULL, 1021169, '2020-05-27 06:43:50', 1021170, '2020-05-27 06:53:24', '192.168.2.200', 'ACCOUNT::ACTIVAT::BEFORE', NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for pla_user_node
-- ----------------------------
DROP TABLE IF EXISTS `pla_user_node`;
CREATE TABLE `pla_user_node`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'user表主键',
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
-- Records of pla_user_node
-- ----------------------------
INSERT INTO `pla_user_node` VALUES (1016711, 0, 1021166, 0, '1', '2020-05-25 10:48:21', NULL, '2020-05-25 10:48:21', 1000006, '2020-05-25 10:48:21', 1000006, NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_user_node` VALUES (1016712, 0, 1021168, 0, '1', '2020-05-26 09:27:08', NULL, '2020-05-26 09:27:08', 1000002, '2020-05-26 09:27:08', 1000002, NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_user_node` VALUES (1016713, 0, 1021169, 0, '1', '2020-05-27 06:29:45', NULL, '2020-05-27 06:29:45', 1000010, '2020-05-27 06:29:45', 1000010, NULL, NULL, NULL, NULL, 0);

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
INSERT INTO `pla_user_password` VALUES (1020970, 0, 'amy@redescooter.com', '76454', '1b92e89b5d43f233217bb18773c261d4', 1000006, '2020-05-25 10:48:21', 1000006, '2020-05-26 07:42:31', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_user_password` VALUES (1020971, 0, 'amy@redessscooter.com', '44610', NULL, 1021166, '2020-05-25 10:50:55', 1021166, '2020-05-25 10:50:55', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_user_password` VALUES (1020972, 0, 'jnane@redescooter.com', '27301', NULL, 1000002, '2020-05-26 09:27:08', 1000002, '2020-05-26 09:27:08', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_user_password` VALUES (1020973, 0, 'Davina@redescooter.com', '33940', '24bf7c4f84d85cdadcec6a3413bf238b', 1000010, '2020-05-27 06:29:45', 1021169, '2020-05-27 06:34:05', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_user_password` VALUES (1020974, 0, 'Davina940@163.com', '55054', '42a1fa8b323874d8849c48be56d47949', 1021169, '2020-05-27 06:43:50', 1021170, '2020-05-27 06:49:26', NULL, NULL, NULL, NULL, 0);

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
INSERT INTO `pla_user_permission` VALUES (1020868, 0, 1021166, 'REDE_SAAS', '1', '1', 1000006, '2020-05-25 10:48:21', 1000006, '2020-05-25 10:48:21', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_user_permission` VALUES (1020869, 0, 1021167, 'REDE_SAAS', '2', '1', 1021166, '2020-05-25 10:50:55', 1021166, '2020-05-25 10:50:55', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_user_permission` VALUES (1020870, 0, 1021168, 'REDE_SAAS', '2', '1', 1000002, '2020-05-26 09:27:08', 1000002, '2020-05-26 09:27:08', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_user_permission` VALUES (1020871, 0, 1021169, 'REDE_SAAS', '1', '1', 1000010, '2020-05-27 06:29:45', 1000010, '2020-05-27 06:29:45', NULL, NULL, NULL, NULL, 0);
INSERT INTO `pla_user_permission` VALUES (1020872, 0, 1021170, 'REDE_SAAS', '2', '1', 1021169, '2020-05-27 06:43:50', 1021169, '2020-05-27 06:43:50', NULL, NULL, NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
