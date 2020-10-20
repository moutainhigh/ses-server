-- MySQL dump 10.14  Distrib 5.5.65-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: website
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
-- Table structure for table `ope_delivery_option`
--

DROP TABLE IF EXISTS `ope_delivery_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_delivery_option` (
  `id` bigint(19) NOT NULL COMMENT '主建',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识【0正常，1删除】',
  `tenant_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `user_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态标识【0正常，1失效】',
  `option_code` varchar(64) NOT NULL COMMENT '交货方式编码',
  `option_neme` varchar(64) NOT NULL COMMENT '交货名称',
  `price` double NOT NULL DEFAULT '0',
  `memo` varchar(256) DEFAULT NULL COMMENT '备注说明',
  `created_by` bigint(9) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交货方式';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_parts`
--

DROP TABLE IF EXISTS `ope_parts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_parts` (
  `id` bigint(11) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `tenant_id` bigint(11) NOT NULL COMMENT '租户ID',
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `status` varchar(32) NOT NULL COMMENT '状态',
  `parts_code` varchar(32) NOT NULL COMMENT '配件代码',
  `parts_type` varchar(16) NOT NULL COMMENT '部件大类',
  `sec` varchar(32) NOT NULL COMMENT '部件区域',
  `cn_name` varchar(32) DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) DEFAULT NULL COMMENT '英文名称',
  `sn_class` varchar(10) NOT NULL COMMENT '是否可销售 是否可销售,0:SC仅可采购，1:SSC可销售可采购',
  `id_class` tinyint(1) DEFAULT NULL COMMENT '是否有唯一编码，如供应商是否提供',
  `note` varchar(32) DEFAULT NULL COMMENT '备注',
  `created_by` bigint(11) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(11) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_presale_order`
--

DROP TABLE IF EXISTS `ope_presale_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_presale_order` (
  `id` bigint(11) NOT NULL COMMENT '主键 主键',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `tenant_id` bigint(11) NOT NULL COMMENT '租户ID',
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `order_no` varchar(64) DEFAULT NULL COMMENT '订单号',
  `order_type` varchar(64) DEFAULT NULL COMMENT '订单类型，整车预售单/配件预售单',
  `customer_id` bigint(11) DEFAULT NULL COMMENT '客户id',
  `sales_id` bigint(11) DEFAULT NULL COMMENT '销售员id',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `first_name` varchar(128) DEFAULT NULL COMMENT '客户名字',
  `last_name` varchar(128) DEFAULT NULL COMMENT '客户姓氏',
  `full_name` varchar(256) DEFAULT NULL COMMENT '客户全名',
  `company_name` varchar(32) DEFAULT NULL COMMENT '公司名称',
  `country` varchar(32) DEFAULT NULL COMMENT '国家名称',
  `city` varchar(32) DEFAULT NULL COMMENT '城市名称',
  `district` varchar(32) DEFAULT NULL COMMENT '区域名称',
  `postcode` varchar(32) DEFAULT NULL COMMENT '邮编名称',
  `address` varchar(255) DEFAULT NULL COMMENT '客户地址',
  `phone_code` varchar(64) DEFAULT NULL COMMENT '手机区号，如+86',
  `phone` varchar(32) DEFAULT NULL COMMENT '移动电话',
  `telephone` varchar(32) DEFAULT NULL COMMENT '固定电话',
  `email` varchar(64) DEFAULT NULL COMMENT '客户邮箱',
  `product_id` bigint(11) DEFAULT NULL COMMENT '产品Id ',
  `product_model_name` varchar(32) DEFAULT NULL COMMENT '产品名称',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '产品单价',
  `parts_id` bigint(20) DEFAULT NULL COMMENT '配件ID',
  `parts_price` decimal(10,2) DEFAULT NULL COMMENT '配件单价',
  `parts_type` varchar(32) DEFAULT NULL COMMENT '配件类型',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '总价',
  `qty` int(11) DEFAULT NULL COMMENT '数量',
  `battery_qty` int(11) NOT NULL COMMENT '电池数量',
  `trunk_qty` int(11) NOT NULL COMMENT '后备箱数量',
  `pay_status` varchar(32) DEFAULT NULL COMMENT '支付状态',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `bank_card_account_name` varchar(64) DEFAULT NULL COMMENT '银行卡开户名',
  `cvv` varchar(64) DEFAULT NULL COMMENT 'CVV代码',
  `card_Num` varchar(64) DEFAULT NULL COMMENT '卡号',
  `created_by` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updated_by` bigint(11) NOT NULL DEFAULT '0' COMMENT '更新人',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='官网预售单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_presale_order_b`
--

DROP TABLE IF EXISTS `ope_presale_order_b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_presale_order_b` (
  `id` bigint(11) NOT NULL COMMENT '主键 主键',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `tenant_id` bigint(11) NOT NULL COMMENT '租户ID',
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `presale_order_id` bigint(20) DEFAULT NULL COMMENT '预售单ID',
  `parts_id` bigint(20) DEFAULT NULL COMMENT '配件ID',
  `parts_price` decimal(10,2) DEFAULT NULL COMMENT '配件单价',
  `parts_qty` int(11) DEFAULT NULL COMMENT '配件数量',
  `parts_type` varchar(32) DEFAULT NULL COMMENT '配件类型',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='官网预售单子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_product`
--

DROP TABLE IF EXISTS `ope_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_product` (
  `id` bigint(11) NOT NULL COMMENT '主键 主键',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `tenant_id` bigint(11) NOT NULL COMMENT '租户ID',
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `status` varchar(32) NOT NULL COMMENT '状态 up上架，down下架(默认)',
  `product_type` int(11) NOT NULL COMMENT '产品大类',
  `product_code` varchar(32) DEFAULT NULL COMMENT '产品编码',
  `product_number` varchar(32) NOT NULL COMMENT '产品编号',
  `cn_name` varchar(32) DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) DEFAULT NULL COMMENT '英文名称',
  `model` varchar(32) DEFAULT NULL COMMENT '型号',
  `pictures` varchar(64) DEFAULT NULL COMMENT '图片',
  `battery_qty` int(11) NOT NULL COMMENT '电池数量',
  `trunk_qty` int(11) NOT NULL COMMENT '后备箱数量',
  `other_parts_qty` int(11) NOT NULL COMMENT '其他配件数量',
  `stock_num` int(11) NOT NULL COMMENT '可用库存',
  `sold_num` int(11) NOT NULL COMMENT '已售库存',
  `after_sales_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否支持售后服务',
  `added_services_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否支持增值服务',
  `mater_parameter` varchar(32) DEFAULT NULL COMMENT '产品参数 存储JSON',
  `other_parameter` varchar(32) DEFAULT NULL COMMENT '其他参数 存储JSON',
  `note` varchar(32) DEFAULT NULL COMMENT '备注',
  `created_by` bigint(11) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(11) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_product_color`
--

DROP TABLE IF EXISTS `ope_product_color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_product_color` (
  `id` bigint(11) NOT NULL COMMENT '主键 主键',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `tenant_id` bigint(11) NOT NULL COMMENT '租户ID',
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `status` varchar(32) NOT NULL COMMENT '状态',
  `color_name` varchar(32) NOT NULL COMMENT '颜色名称',
  `color_code` varchar(32) DEFAULT NULL COMMENT '颜色代码',
  `product_id` int(11) NOT NULL COMMENT '产品主键',
  `note` varchar(32) DEFAULT NULL COMMENT '备注',
  `created_by` bigint(11) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(11) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品配色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_website_account`
--

DROP TABLE IF EXISTS `ope_website_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_website_account` (
  `ID` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(2) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(11) DEFAULT '0' COMMENT '租户ID',
  `system_id` varchar(64) DEFAULT NULL COMMENT '系统ID',
  `app_id` varchar(64) DEFAULT NULL COMMENT '应用IP',
  `login_name` varchar(64) NOT NULL COMMENT '登录名',
  `customer_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '客户ID',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `status` varchar(32) DEFAULT NULL COMMENT '状态 Normal,Cancel',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_token` varchar(64) DEFAULT NULL COMMENT '最后登录TOKEN',
  `created_by` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(11) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_login_ip` varchar(16) DEFAULT NULL COMMENT '最后登录IP地址',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='官网账号表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_withdrawal_site`
--

DROP TABLE IF EXISTS `ope_withdrawal_site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_withdrawal_site` (
  `id` bigint(19) NOT NULL COMMENT '主建',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '店铺状态 0正常 1冻结',
  `tenant_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `user_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `business_status` int(11) NOT NULL DEFAULT '1' COMMENT '营业状态【0开门，1关门】',
  `type` int(2) DEFAULT NULL COMMENT '店铺类型【1维修，-1销售，0全部】',
  `street_number` varchar(64) DEFAULT NULL COMMENT '街道编号',
  `street_name` varchar(128) DEFAULT NULL COMMENT '街道名称',
  `contact_first` varchar(128) DEFAULT NULL COMMENT '联系人姓氏',
  `contact_last` varchar(128) DEFAULT NULL COMMENT '联系人名字',
  `contant_full_name` int(11) DEFAULT NULL COMMENT '联系人全名',
  `email` varchar(128) DEFAULT NULL COMMENT '店铺邮箱',
  `phone_number` varchar(128) DEFAULT NULL COMMENT '店铺联系电话',
  `open_time` datetime DEFAULT NULL COMMENT '营业开始时间',
  `close_time` datetime DEFAULT NULL COMMENT '营业结束时间',
  `other_params` text COMMENT '以json的数据格式，key 和value的方式进行存储',
  `store_code` varchar(64) NOT NULL COMMENT '门店编码',
  `store_name` varchar(256) NOT NULL COMMENT '门店名称',
  `country` varchar(128) DEFAULT NULL COMMENT '国家',
  `city` varchar(128) DEFAULT NULL COMMENT '城市',
  `area` varchar(128) DEFAULT NULL COMMENT '区域',
  `code_postal` varchar(64) NOT NULL COMMENT '邮政编码',
  `address` varchar(256) DEFAULT NULL COMMENT '详细地址',
  `remarks` varchar(128) DEFAULT NULL COMMENT '备注',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='取货地址';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_worker_node`
--

DROP TABLE IF EXISTS `ope_worker_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_worker_node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动递增编号',
  `host_name` varchar(64) NOT NULL COMMENT '主机名',
  `port` varchar(64) NOT NULL COMMENT 'port',
  `type` int(11) NOT NULL COMMENT 'node type: actual or container',
  `launch_date` date NOT NULL COMMENT 'launch date',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'modified time',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='db workerid assigner for uid generator';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-20  7:39:31
