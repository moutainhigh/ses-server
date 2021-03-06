-- MySQL dump 10.14  Distrib 5.5.65-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: platform
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
-- Table structure for table `pdman_db_version`
--

DROP TABLE IF EXISTS `pdman_db_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pdman_db_version` (
  `DB_VERSION` varchar(256) DEFAULT NULL,
  `VERSION_DESC` varchar(1024) DEFAULT NULL,
  `CREATED_TIME` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_app_version`
--

DROP TABLE IF EXISTS `pla_app_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_app_version` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `system_id` varchar(32) DEFAULT NULL COMMENT '系统ID',
  `app_id` varchar(32) DEFAULT NULL COMMENT '应用ID',
  `system_type` int(2) DEFAULT NULL COMMENT 'IOS or ANDROID',
  `type` int(3) NOT NULL COMMENT '版本类型',
  `code` varchar(32) CHARACTER SET ucs2 NOT NULL COMMENT '版本应用编码',
  `is_force` int(11) DEFAULT NULL COMMENT '是否强制更新 1是true，0是false',
  `update_content` varchar(256) DEFAULT NULL COMMENT '更新提示内容',
  `update_link` varchar(255) DEFAULT NULL COMMENT '更新地址',
  `new_version_num` varchar(225) DEFAULT NULL COMMENT '新版本号',
  `package_size` varchar(64) DEFAULT NULL COMMENT '安装包大小',
  `new_version_name` varchar(8) DEFAULT NULL COMMENT '新版本号名称',
  `min_version_num` int(11) DEFAULT NULL COMMENT '最小版本',
  `nin_version_name` varchar(8) DEFAULT NULL COMMENT '最小版本号名称',
  `status` varchar(32) DEFAULT NULL COMMENT '状态 NEW:新版本；Closed：已关闭',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用版本管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_city`
--

DROP TABLE IF EXISTS `pla_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_city` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `code` varchar(32) DEFAULT NULL COMMENT '编码',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `level` int(11) DEFAULT NULL COMMENT '级别，国家为首级，默认为1',
  `p_id` bigint(20) DEFAULT NULL COMMENT '父ID，默认首级节点为0',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `post_code` varchar(32) DEFAULT NULL COMMENT '邮政编码',
  `time_zone` varchar(32) DEFAULT NULL COMMENT '时区',
  `language` varchar(32) DEFAULT NULL COMMENT '语言',
  `longitude` decimal(32,10) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32,10) DEFAULT NULL COMMENT '纬度',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_city_test`
--

DROP TABLE IF EXISTS `pla_city_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_city_test` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `code` varchar(32) DEFAULT NULL COMMENT '编码',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `level` int(11) DEFAULT NULL COMMENT '级别，国家为首级，默认为1',
  `p_id` bigint(20) DEFAULT NULL COMMENT '父ID，默认首级节点为0',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `time_zone` varchar(32) DEFAULT NULL COMMENT '时区',
  `language` varchar(32) DEFAULT NULL COMMENT '语言',
  `longitude` decimal(32,10) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32,10) DEFAULT NULL COMMENT '纬度',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_country_code`
--

DROP TABLE IF EXISTS `pla_country_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_country_code` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除主键',
  `icon` varchar(256) DEFAULT NULL COMMENT '国家国旗图标',
  `country_code` varchar(128) NOT NULL COMMENT '国家编码',
  `country_name` varchar(128) NOT NULL COMMENT '国家名称',
  `country_language` varchar(64) NOT NULL COMMENT '国家语言',
  `time_zone` varchar(128) DEFAULT NULL COMMENT '时区',
  `def1` varchar(64) DEFAULT NULL,
  `def2` varchar(64) DEFAULT NULL,
  `def3` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='国家编码,用于手机号前缀等';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_i18n_config`
--

DROP TABLE IF EXISTS `pla_i18n_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_i18n_config` (
  `ID` bigint(20) NOT NULL,
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `group` varchar(32) NOT NULL,
  `key` varchar(32) NOT NULL,
  `type` varchar(64) NOT NULL,
  `country` varchar(32) NOT NULL,
  `value` varchar(64) NOT NULL,
  `desc` varchar(128) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_jpush_user`
--

DROP TABLE IF EXISTS `pla_jpush_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_jpush_user` (
  `id` bigint(20) NOT NULL,
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户主键',
  `registration_id` varchar(64) NOT NULL COMMENT '设备唯一标识',
  `tag` varchar(24) DEFAULT NULL COMMENT '标签',
  `alias` varchar(24) DEFAULT NULL COMMENT '别名',
  `platform_type` varchar(24) DEFAULT NULL COMMENT '推送平台:支持 Android, iOS, Windows Phone 三个平台的推送。其关键字分别为："android", "ios", "winphone"。',
  `audience_type` varchar(24) DEFAULT NULL COMMENT '推送目标:别名ALIAS、标签TAG、注册唯一属性REGISTRATION_ID、分群SEGMENT、广播',
  `push_time` datetime DEFAULT NULL COMMENT '推送时间',
  `status` int(11) DEFAULT '0' COMMENT '登录绑定：0，注销解绑：1',
  `status_code` varchar(24) DEFAULT NULL COMMENT '状态码：登录绑定LOGIN，注销解绑LOGOUT',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='极光用户关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_mail_config`
--

DROP TABLE IF EXISTS `pla_mail_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_mail_config` (
  `id` bigint(20) NOT NULL DEFAULT '10000' COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `status` varchar(64) NOT NULL DEFAULT 'NORMAL' COMMENT 'normal正常，Disabled失效的',
  `mail_template_no` int(11) DEFAULT NULL COMMENT '模板编号',
  `system_id` varchar(24) NOT NULL COMMENT '系统ID',
  `app_id` varchar(24) NOT NULL COMMENT '应用ID',
  `param_key` varchar(64) NOT NULL COMMENT '对应key，不可为空',
  `param_value` varchar(128) DEFAULT NULL COMMENT '对应值域，可为空',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_mail_task`
--

DROP TABLE IF EXISTS `pla_mail_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_mail_task` (
  `id` bigint(20) NOT NULL DEFAULT '10000' COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `status` varchar(64) NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING待发送，SUCCESS发送成功，FAIL发送失败',
  `system_id` varchar(64) NOT NULL COMMENT '系统ID',
  `app_id` varchar(64) NOT NULL COMMENT '应用ID',
  `request_id` varchar(128) NOT NULL COMMENT '请求ID',
  `mail_template_no` int(11) DEFAULT NULL COMMENT '模板编号',
  `send_mail` varchar(64) DEFAULT NULL COMMENT '发送方',
  `receive_mail` varchar(64) NOT NULL COMMENT '接受方',
  `to_user_id` bigint(64) NOT NULL COMMENT '收件方用户ID',
  `subject` varchar(256) NOT NULL COMMENT '主题',
  `content` text COMMENT '内容',
  `file_path` varchar(128) DEFAULT NULL COMMENT '附件地址，可为空',
  `parameter` varchar(4096) DEFAULT NULL COMMENT '发送json保存',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_mail_template`
--

DROP TABLE IF EXISTS `pla_mail_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_mail_template` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `status` varchar(64) NOT NULL DEFAULT 'NORMAL' COMMENT 'Normal正常的，Disabled失效的',
  `mail_template_no` int(11) DEFAULT NULL COMMENT '模板编号',
  `name` varchar(64) NOT NULL,
  `event` varchar(64) NOT NULL COMMENT '邮件发送事件',
  `subject` varchar(255) NOT NULL COMMENT '主题',
  `content` text NOT NULL COMMENT '模板实际内容',
  `memo` varchar(64) NOT NULL COMMENT '说明',
  `backup` varchar(256) DEFAULT NULL COMMENT '模板远程备份',
  `expire` int(11) NOT NULL DEFAULT '259200' COMMENT '邮件有效期，单位秒',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件模板配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_message`
--

DROP TABLE IF EXISTS `pla_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_message` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `system_id` varchar(32) DEFAULT NULL COMMENT '系统ID',
  `app_id` varchar(32) DEFAULT NULL COMMENT '应用ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `message_type` varchar(16) DEFAULT NULL COMMENT '消息类型：推送消息PUSH，站内消息SITE',
  `biz_type` varchar(32) DEFAULT NULL COMMENT '消息类型',
  `biz_id` mediumtext COMMENT '业务Id',
  `status` varchar(32) DEFAULT NULL COMMENT '消息状态',
  `business_status` varchar(255) DEFAULT NULL COMMENT '当前业务的业务状态',
  `title` varchar(64) DEFAULT NULL COMMENT '消息标题',
  `message_priority` varchar(32) DEFAULT NULL COMMENT '0 无需提示 1 小红点 2 强提醒',
  `content` varchar(102) DEFAULT NULL COMMENT '消息内容',
  `memo` varchar(128) DEFAULT NULL COMMENT '消息内容参数',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `read_time` datetime DEFAULT NULL COMMENT '已读时间',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_push_result`
--

DROP TABLE IF EXISTS `pla_push_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_push_result` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `msg_id` bigint(20) DEFAULT NULL COMMENT '推送返回消息主键 无返回值 默认为0',
  `send_no` int(11) DEFAULT NULL COMMENT '推送序号 无返回值 默认为0',
  `status_code` int(11) DEFAULT NULL COMMENT '状态 无返回值 默认为5 区分极光成功时 返回值是0',
  `error_code` int(11) DEFAULT NULL COMMENT '错误码 由于成功状态 无错误码 自定义为 1  表示成功',
  `error_message` varchar(64) DEFAULT NULL COMMENT '错误信息 成功无错误信息 自定义 成功是 错误信息为空',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='极光推送结果记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_sys_config`
--

DROP TABLE IF EXISTS `pla_sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_sys_config` (
  `id` int(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `group` varchar(32) NOT NULL,
  `key` varchar(32) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `value` varchar(64) DEFAULT NULL,
  `desc` varchar(128) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `created_time` datetime NOT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_sys_group_setting`
--

DROP TABLE IF EXISTS `pla_sys_group_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_sys_group_setting` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `system_type` varchar(32) DEFAULT NULL COMMENT '系统类型',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `group_name` varchar(32) DEFAULT NULL COMMENT '分组名称',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='分组设置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_sys_param_setting`
--

DROP TABLE IF EXISTS `pla_sys_param_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_sys_param_setting` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `system_type` varchar(32) DEFAULT NULL COMMENT '系统类型',
  `group_id` bigint(20) DEFAULT NULL COMMENT '分组Id',
  `parameter_name` varchar(64) DEFAULT NULL COMMENT '参数名',
  `param_key` varchar(64) DEFAULT NULL COMMENT 'key值',
  `param_value` varchar(255) DEFAULT NULL COMMENT '属性值',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统参数设置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_sys_sequence`
--

DROP TABLE IF EXISTS `pla_sys_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_sys_sequence` (
  `NAME` varchar(64) NOT NULL COMMENT 'sequence名称',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `CURRENT_VALUE` bigint(20) NOT NULL DEFAULT '1000000' COMMENT '当前值',
  `INCREMENT` int(11) NOT NULL DEFAULT '1' COMMENT '增量',
  `CACHE` int(11) NOT NULL DEFAULT '1000' COMMENT '缓存大小',
  `CREATED_BY` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `CREATED_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATED_BY` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `UPDATED_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='序列';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_tenant`
--

DROP TABLE IF EXISTS `pla_tenant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_tenant` (
  `id` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `p_id` bigint(11) DEFAULT NULL COMMENT 'P_ID',
  `tenant_name` varchar(32) DEFAULT NULL COMMENT '租户名，即客户名',
  `email` varchar(64) NOT NULL COMMENT '邮件',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `country_id` bigint(20) DEFAULT NULL,
  `city_id` bigint(11) DEFAULT NULL COMMENT 'city_Id',
  `distrust_id` bigint(20) DEFAULT NULL,
  `driver_counts` int(11) DEFAULT NULL COMMENT '司机数量',
  `sales_id` bigint(20) DEFAULT NULL COMMENT '销售',
  `tenant_source` varchar(32) DEFAULT NULL COMMENT '来源渠道 官网/Email/电话',
  `tenant_type` varchar(32) DEFAULT NULL COMMENT '租户类型 企业/个人',
  `tenant_industry` varchar(32) DEFAULT NULL COMMENT '租户行业',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `contact` varchar(32) DEFAULT NULL COMMENT '联系人',
  `position` varchar(32) DEFAULT NULL COMMENT '职位',
  `tenant_code` varchar(64) DEFAULT NULL COMMENT '租户编码',
  `longitude` decimal(32,10) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32,10) DEFAULT NULL COMMENT '纬度',
  `tel_1` varchar(32) DEFAULT NULL COMMENT '电话',
  `tel_2` varchar(32) DEFAULT NULL COMMENT '电话',
  `tel_3` varchar(32) DEFAULT NULL COMMENT '电话',
  `email_1` varchar(64) DEFAULT NULL COMMENT '邮件',
  `email_2` varchar(64) DEFAULT NULL COMMENT '邮件',
  `email_3` varchar(64) DEFAULT NULL COMMENT '邮件',
  `time_zone` varchar(32) DEFAULT NULL COMMENT '时区',
  `effective_time` datetime DEFAULT NULL COMMENT '生效时间',
  `activation_time` datetime DEFAULT NULL COMMENT '激活时间',
  `expire_time` datetime DEFAULT NULL COMMENT '到期时间',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_tenant_config`
--

DROP TABLE IF EXISTS `pla_tenant_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_tenant_config` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `language` varchar(32) DEFAULT NULL COMMENT '语言',
  `time_zone` varchar(32) DEFAULT NULL COMMENT '时区',
  `longitude` decimal(32,10) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32,10) DEFAULT NULL COMMENT '纬度',
  `address` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `status` varchar(16) CHARACTER SET utf8 DEFAULT NULL COMMENT '营业状态 OPEN 1、CLOSE  2 营业中、打烊',
  `start_week` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '从周几开始',
  `end_week` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '从周几结束',
  `begin_time` time DEFAULT NULL COMMENT '开始时间',
  `end_time` time DEFAULT NULL COMMENT '结束时间',
  `timeout_expectde` bigint(20) DEFAULT NULL COMMENT '超时预期值,单位min',
  `estimated_duration` bigint(20) DEFAULT NULL COMMENT '估计配送持续时间,单位min',
  `distribution_range` bigint(20) DEFAULT NULL COMMENT '配送范围,单位km',
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='租户配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_tenant_node`
--

DROP TABLE IF EXISTS `pla_tenant_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_tenant_node` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户主键',
  `event` varchar(64) DEFAULT NULL COMMENT '事件',
  `event_time` datetime DEFAULT NULL COMMENT '事件时间',
  `memo` varchar(64) DEFAULT NULL COMMENT '备注',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租户节点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_user`
--

DROP TABLE IF EXISTS `pla_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_user` (
  `ID` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `TENANT_ID` bigint(11) DEFAULT NULL COMMENT '租户ID',
  `SYSTEM_ID` varchar(64) DEFAULT NULL COMMENT '系统ID',
  `APP_ID` varchar(64) DEFAULT NULL COMMENT '应用ip，SAAS_WEB:SaaS配送，SAAS_APP:SaaS移动，SAAS_REPAIR_WEB:SaaS维修，SES_ROS:RedE办公系统，SES_DEVL:RedE开发系统',
  `LOGIN_NAME` varchar(64) NOT NULL COMMENT '登录名',
  `LOGIN_TYPE` int(11) NOT NULL DEFAULT '1' COMMENT '登录类型',
  `STATUS` varchar(32) DEFAULT NULL COMMENT '状态 Normal,Lock,Cancel',
  `USER_TYPE` int(11) NOT NULL DEFAULT '0' COMMENT '用户类型:1餐厅配送SaaS,2快递配送SaaS,3餐厅移动端,4快递移动端,5个人移动端,6维修端',
  `LAST_LOGIN_TIME` datetime DEFAULT NULL COMMENT '最后登录时间',
  `LAST_LOGIN_TOKEN` varchar(64) DEFAULT NULL COMMENT '最后登录TOKEN',
  `EFFECTIVE_TIME` datetime DEFAULT NULL COMMENT '生效时间',
  `ACTIVATION_TIME` datetime DEFAULT NULL COMMENT '激活时间',
  `EXPIRE_TIME` datetime DEFAULT NULL COMMENT '到期时间',
  `CREATED_BY` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `CREATED_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATED_BY` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `UPDATED_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `LAST_LOGIN_IP` varchar(16) DEFAULT NULL COMMENT '最后登录IP地址',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_user_node`
--

DROP TABLE IF EXISTS `pla_user_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_user_node` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'user表主键',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户主键',
  `event` varchar(64) DEFAULT NULL COMMENT '事件',
  `event_time` datetime DEFAULT NULL COMMENT '事件时间',
  `memo` varchar(64) DEFAULT NULL COMMENT '备注',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户节点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_user_password`
--

DROP TABLE IF EXISTS `pla_user_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_user_password` (
  `ID` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `login_name` varchar(64) NOT NULL COMMENT '登录名，即用户邮箱',
  `SALT` varchar(32) DEFAULT NULL COMMENT '盐',
  `PASSWORD` varchar(128) DEFAULT NULL COMMENT '密码',
  `CREATED_BY` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `CREATED_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATED_BY` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `UPDATED_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户密码表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pla_user_permission`
--

DROP TABLE IF EXISTS `pla_user_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pla_user_permission` (
  `ID` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `USER_ID` bigint(11) NOT NULL COMMENT '用户ID',
  `SYSTEM_ID` varchar(32) DEFAULT NULL COMMENT '系统ID',
  `APP_ID` varchar(32) NOT NULL COMMENT '应用ID',
  `STATUS` varchar(32) NOT NULL DEFAULT 'NORMAL' COMMENT '状态',
  `CREATED_BY` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `CREATED_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATED_BY` bigint(11) NOT NULL DEFAULT '0' COMMENT '更新人',
  `UPDATED_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户应用表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-20  7:36:52
