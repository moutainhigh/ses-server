-- MySQL dump 10.14  Distrib 5.5.65-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: operation
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
-- Table structure for table `ope_allocate`
--

DROP TABLE IF EXISTS `ope_allocate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_allocate` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT NULL COMMENT '删除标识',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'userid',
  `allocate_num` varchar(32) DEFAULT NULL COMMENT '调拨编号',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `count` int(20) DEFAULT NULL COMMENT '数量',
  `consignee_id` bigint(20) DEFAULT NULL COMMENT '收获人Id',
  `preparation_wait_total` int(20) DEFAULT NULL COMMENT '待备料总数',
  `pending_storage_total` int(10) DEFAULT NULL COMMENT '待入库数量',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='调拨单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_allocate_b`
--

DROP TABLE IF EXISTS `ope_allocate_b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_allocate_b` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT NULL COMMENT '删除标识',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'userId',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `allocate_id` bigint(20) DEFAULT NULL COMMENT '调拨单Id',
  `part_id` bigint(20) DEFAULT NULL COMMENT '部件Id',
  `materiel_product_id` bigint(20) DEFAULT NULL COMMENT '产品物料id',
  `materiel_product_type` varchar(32) DEFAULT NULL COMMENT '产品物料类型',
  `total` int(20) DEFAULT NULL COMMENT '数量',
  `preparation_wait_qty` int(20) DEFAULT NULL COMMENT '待备料总数',
  `pending_storage_qty` int(10) DEFAULT NULL COMMENT '待入库数量',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='调拨单子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_allocate_b_trace`
--

DROP TABLE IF EXISTS `ope_allocate_b_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_allocate_b_trace` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT NULL COMMENT '删除标识',
  `allocate_id` bigint(20) DEFAULT NULL COMMENT '调拨单Id',
  `allocate_b_id` bigint(20) DEFAULT NULL COMMENT '调拨单子表Id',
  `part_id` bigint(20) DEFAULT NULL COMMENT '部件Id',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '批次号',
  `qty` int(20) DEFAULT NULL COMMENT '备料数量',
  `serial_num` varchar(64) DEFAULT NULL COMMENT '序列号',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='调拨备料记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_allocate_trace`
--

DROP TABLE IF EXISTS `ope_allocate_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_allocate_trace` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'userId',
  `allocate_id` bigint(20) NOT NULL COMMENT '采购订单id',
  `status` varchar(64) DEFAULT NULL COMMENT '采购单状态',
  `event` varchar(64) DEFAULT NULL COMMENT '事件',
  `event_time` datetime DEFAULT NULL COMMENT '事件时间',
  `memo` varchar(64) DEFAULT NULL COMMENT '备注 放话术参数',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调拨单节点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_assembiy_order_trace`
--

DROP TABLE IF EXISTS `ope_assembiy_order_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_assembiy_order_trace` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `ope_assembiy_order_id` bigint(20) DEFAULT NULL COMMENT '组装单主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `status` varchar(64) DEFAULT NULL COMMENT '状态',
  `event` varchar(64) DEFAULT NULL COMMENT '事件',
  `event_time` datetime DEFAULT NULL COMMENT '事件时间',
  `memo` varchar(64) DEFAULT NULL COMMENT '备注说明',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组装单节点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_assembly_b_order`
--

DROP TABLE IF EXISTS `ope_assembly_b_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_assembly_b_order` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT NULL COMMENT '删除标识',
  `status` varchar(64) DEFAULT NULL COMMENT '状态',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `assembly_id` bigint(20) DEFAULT NULL COMMENT '组装单id',
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品id',
  `assemblyb_number` varchar(32) DEFAULT NULL COMMENT '子表编号',
  `product_number` varchar(32) DEFAULT NULL COMMENT '产品编码',
  `en_name` varchar(64) DEFAULT NULL COMMENT '产品英文名',
  `price` decimal(10,2) DEFAULT NULL COMMENT '产品单价',
  `wait_assembly_qty` int(11) DEFAULT NULL COMMENT '待组装数量',
  `in_wait_wh_qty` int(11) DEFAULT NULL COMMENT '待入库数',
  `lave_wait_qc_qty` int(10) DEFAULT NULL COMMENT '待质检数',
  `assembly_qty` int(11) DEFAULT NULL COMMENT '组装总数量',
  `complete_qty` int(11) DEFAULT NULL COMMENT '完成组装数',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建表',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组装单产品明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_assembly_b_qc`
--

DROP TABLE IF EXISTS `ope_assembly_b_qc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_assembly_b_qc` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT NULL COMMENT '删除标志',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'userId',
  `assembly_b_id` bigint(20) DEFAULT NULL COMMENT '组装子表Id',
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品Id',
  `quality_inspector_id` bigint(20) DEFAULT NULL COMMENT '质检人Id',
  `batch_no` varchar(32) CHARACTER SET latin1 DEFAULT NULL COMMENT '质检批次号',
  `status` varchar(32) DEFAULT NULL COMMENT '质检状态',
  `total_quality_inspected` int(20) DEFAULT NULL COMMENT '需要质检总数',
  `pass_count` int(11) DEFAULT NULL COMMENT '质检通过数量',
  `fail_count` int(11) DEFAULT NULL COMMENT '质检失败数量',
  `quality_inspection_time` datetime DEFAULT NULL COMMENT '质检时间',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购条目QC质检';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_assembly_lot_trace`
--

DROP TABLE IF EXISTS `ope_assembly_lot_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_assembly_lot_trace` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT NULL COMMENT '删除标志',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'userId',
  `assembly_id` bigint(20) DEFAULT NULL COMMENT '组装表Id',
  `quality_inspector_id` bigint(20) DEFAULT NULL COMMENT '质检人Id',
  `batch_no` varchar(32) CHARACTER SET latin1 DEFAULT NULL COMMENT '质检批次号',
  `total_quality_inspected` int(20) DEFAULT NULL COMMENT '需要质检总数',
  `pass_count` int(11) DEFAULT NULL COMMENT '质检通过数量',
  `fail_count` int(11) DEFAULT NULL COMMENT '质检失败数量',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购条目QC质检';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_assembly_order`
--

DROP TABLE IF EXISTS `ope_assembly_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_assembly_order` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT NULL COMMENT '逻辑删除标识',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `tenant_id` bigint(20) DEFAULT '0' COMMENT '租户id',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `assembly_number` varchar(64) DEFAULT NULL COMMENT '组装单订单号',
  `total_qty` int(11) DEFAULT NULL COMMENT '产品数量之和',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '总金额',
  `processing_fee_ratio` decimal(10,2) DEFAULT NULL COMMENT '加工费比例',
  `processing_fee` decimal(10,2) DEFAULT NULL COMMENT '加工费',
  `payment_type` varchar(32) DEFAULT NULL COMMENT '付款类型',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '产品生产价格',
  `factory_id` bigint(20) DEFAULT NULL COMMENT '代工厂id',
  `factory_annex` varchar(128) DEFAULT NULL COMMENT '工厂附件',
  `consignee_id` bigint(20) DEFAULT NULL COMMENT '收货人姓氏',
  `wait_assembly_total` int(11) DEFAULT NULL COMMENT '待组装总数量',
  `in_wait_wh_total` int(10) DEFAULT NULL COMMENT '待入库总数',
  `lave_wait_qc_total` int(10) DEFAULT NULL COMMENT '待质检总数',
  `wait_preparation_total` int(10) DEFAULT NULL COMMENT '待备料数量',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组装单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_assembly_order_part`
--

DROP TABLE IF EXISTS `ope_assembly_order_part`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_assembly_order_part` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT NULL COMMENT '逻辑删除标识',
  `stock_id` bigint(20) DEFAULT NULL COMMENT '库存Id',
  `part_id` bigint(20) DEFAULT NULL COMMENT '部件Id',
  `assembly_id` bigint(20) DEFAULT NULL COMMENT '组装单Id',
  `total_qty` int(20) DEFAULT NULL COMMENT '消耗部件数量',
  `wait_preparation_qty` int(10) DEFAULT NULL COMMENT '待备料数量',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_assembly_order_payment`
--

DROP TABLE IF EXISTS `ope_assembly_order_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_assembly_order_payment` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(11) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'userId',
  `ope_assembly_order_id` bigint(20) DEFAULT NULL COMMENT '组装单主键',
  `payment_type` varchar(32) NOT NULL COMMENT '分期，月结',
  `planned_payment_time` datetime NOT NULL COMMENT '预计付款时间',
  `payment_day` int(11) DEFAULT NULL COMMENT '付款周期',
  `payment_time` datetime DEFAULT NULL COMMENT '实际付款时间',
  `payment_status` varchar(32) DEFAULT NULL COMMENT '支付状态:Paid Unpaid',
  `payment_priority` int(11) NOT NULL DEFAULT '0' COMMENT '支付优先级',
  `description` varchar(64) DEFAULT NULL COMMENT '描述',
  `amount` decimal(10,2) NOT NULL COMMENT '价格',
  `amount_proportion` int(11) NOT NULL COMMENT '55% 标示为 55',
  `invoice_num` varchar(32) DEFAULT NULL COMMENT '发票单号',
  `invoice_picture` varchar(64) DEFAULT NULL COMMENT '发票附件',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组装单付款表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_assembly_preparation`
--

DROP TABLE IF EXISTS `ope_assembly_preparation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_assembly_preparation` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT NULL COMMENT '逻辑删除标识',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `tenant_id` bigint(20) DEFAULT '0' COMMENT '租户id',
  `assembly_id` bigint(20) DEFAULT NULL COMMENT '组装单Id',
  `assembly_order_part_id` bigint(20) DEFAULT NULL COMMENT '组装部品表Id',
  `part_id` bigint(20) DEFAULT NULL COMMENT '部件Id',
  `serial_num` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '序列号',
  `batch_no` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '批次号',
  `qty` int(20) DEFAULT NULL COMMENT '备料数量',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组装备料记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_assembly_qc_item`
--

DROP TABLE IF EXISTS `ope_assembly_qc_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_assembly_qc_item` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT NULL COMMENT '删除标识',
  `assembly_id` bigint(20) DEFAULT NULL COMMENT '组装单id',
  `assembly_b_id` bigint(20) DEFAULT NULL COMMENT '组装单子表Id',
  `assembly_b_qc_id` bigint(20) DEFAULT NULL COMMENT '质检结果表',
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品Id',
  `assembly_lot_id` bigint(20) DEFAULT NULL COMMENT '批次质检记录id',
  `serial_num` varchar(64) DEFAULT NULL COMMENT '序列号',
  `batch_no` varchar(64) DEFAULT NULL COMMENT '批次号',
  `qc_result` varchar(32) DEFAULT NULL COMMENT '质检结果',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建表',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组装单质检条目';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_assembly_qc_trace`
--

DROP TABLE IF EXISTS `ope_assembly_qc_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_assembly_qc_trace` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT NULL COMMENT '删除标识',
  `ope_assembly_b_id` bigint(20) DEFAULT NULL COMMENT '组装单子表id',
  `product_qc_template_b_id` bigint(20) DEFAULT NULL COMMENT '质检项结果Id',
  `product_qc_template_b_name` varchar(64) DEFAULT NULL COMMENT '质检结果',
  `product_qc_template_id` bigint(20) DEFAULT NULL COMMENT '质检项Id',
  `product_qc_template_name` varchar(64) DEFAULT NULL COMMENT '质检项名称',
  `assembly_qc_item_id` bigint(20) DEFAULT NULL COMMENT '商品质检条目',
  `picture` varchar(128) DEFAULT NULL COMMENT '质检图片（多个图片逗号分隔）',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组装单质检记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_contact_us`
--

DROP TABLE IF EXISTS `ope_contact_us`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_contact_us` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT '0' COMMENT '删除标志',
  `email` varchar(64) DEFAULT NULL COMMENT '客户邮箱',
  `first_name` varchar(64) DEFAULT NULL COMMENT '名',
  `last_name` varchar(64) DEFAULT NULL COMMENT '姓',
  `full_name` varchar(64) DEFAULT NULL COMMENT '全名',
  `telephone` varchar(64) DEFAULT NULL COMMENT '电话',
  `country` bigint(20) DEFAULT NULL COMMENT '国家Id',
  `country_code` varchar(64) DEFAULT NULL COMMENT '国家代码',
  `country_name` varchar(32) DEFAULT NULL COMMENT '国家名称',
  `city_name` varchar(32) DEFAULT NULL COMMENT '城市名称',
  `district` bigint(20) DEFAULT NULL COMMENT '区域Id',
  `district_name` varchar(255) DEFAULT NULL COMMENT '区域编码',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `frequency` int(11) DEFAULT NULL COMMENT '联系次数',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `remark` varchar(2048) DEFAULT NULL COMMENT '备注',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime(3) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='官网联系我们';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_contact_us_trace`
--

DROP TABLE IF EXISTS `ope_contact_us_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_contact_us_trace` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT '0' COMMENT '删除标志',
  `contact_us_id` bigint(20) NOT NULL COMMENT '联系我们id',
  `email` varchar(64) DEFAULT NULL COMMENT '客户邮箱',
  `first_name` varchar(64) DEFAULT NULL COMMENT '名',
  `last_name` varchar(64) DEFAULT NULL COMMENT '姓',
  `full_name` varchar(64) DEFAULT NULL COMMENT '全名',
  `telephone` varchar(64) DEFAULT NULL COMMENT '电话',
  `country_name` varchar(64) DEFAULT NULL COMMENT '国家名称',
  `city_name` varchar(32) DEFAULT NULL COMMENT '城市名称',
  `district_name` varchar(32) DEFAULT NULL COMMENT '区域编码',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `remark` varchar(4096) DEFAULT NULL COMMENT '备注',
  `message_type` varchar(32) DEFAULT NULL COMMENT '消息类型：1.留言 2回复',
  `leave_word_id` bigint(20) DEFAULT NULL COMMENT '留言id',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime(3) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='官网联系我们历史记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_customer`
--

DROP TABLE IF EXISTS `ope_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_customer` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `time_zone` varchar(16) DEFAULT NULL COMMENT '时区',
  `country` bigint(20) DEFAULT NULL COMMENT '国家',
  `country_code` varchar(64) DEFAULT NULL COMMENT '国家编码，如手机号 中国 +86',
  `city` bigint(20) DEFAULT NULL COMMENT '城市',
  `distrust` bigint(20) DEFAULT NULL COMMENT '区域',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `sales_id` bigint(20) DEFAULT NULL COMMENT '销售',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编码',
  `customer_first_name` varchar(128) DEFAULT NULL COMMENT '客户名字',
  `customer_last_name` varchar(128) DEFAULT NULL COMMENT '客户名字',
  `customer_full_name` varchar(256) DEFAULT NULL COMMENT '客户名字',
  `company_name` varchar(128) DEFAULT NULL COMMENT '企业名称',
  `picture` varchar(128) DEFAULT NULL COMMENT '客户头像',
  `customer_source` varchar(32) DEFAULT NULL COMMENT '客户来源渠道 官网/email/电话',
  `customer_type` varchar(24) DEFAULT NULL COMMENT '客户类型 1企业/2个人',
  `industry_type` varchar(24) DEFAULT NULL COMMENT '客户行业类型，1餐厅/2快递',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `place_id` varchar(256) DEFAULT NULL COMMENT '地点编号',
  `longitude` decimal(32,10) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32,10) DEFAULT NULL COMMENT '纬度',
  `contact_first_name` varchar(128) DEFAULT NULL COMMENT '客户名字',
  `contact_last_name` varchar(256) DEFAULT NULL COMMENT '客户名字',
  `contact_full_name` varchar(128) DEFAULT NULL COMMENT '联系人全名',
  `telephone` varchar(32) DEFAULT NULL COMMENT '电话',
  `email` varchar(64) NOT NULL COMMENT '邮件',
  `memo` varchar(256) DEFAULT NULL COMMENT '备注信息',
  `scooter_quantity` int(20) DEFAULT NULL COMMENT '车辆数量',
  `assignation_scooter_qty` int(11) DEFAULT NULL COMMENT '已分配车辆数',
  `certificate_type` varchar(24) DEFAULT NULL COMMENT '证件类型1身份证，2驾驶证，3护照',
  `certificate_positive_annex` varchar(128) DEFAULT NULL COMMENT '证件正面图片',
  `certificate_negative_annex` varchar(128) DEFAULT NULL COMMENT '证件反面图片',
  `business_license_num` varchar(64) DEFAULT NULL COMMENT '营业执照编号',
  `business_license_annex` varchar(128) DEFAULT NULL COMMENT '营业执照图片',
  `invoice_num` varchar(128) DEFAULT NULL COMMENT '发票编号',
  `invoice_annex` varchar(128) DEFAULT NULL COMMENT '发票附件',
  `description` varchar(256) DEFAULT NULL COMMENT '删除说明',
  `contract_annex` varchar(255) DEFAULT NULL COMMENT '合同附件',
  `account_flag` varchar(32) DEFAULT NULL COMMENT '账号使用标识，即激活使用过1，未激活未使用0',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_customer_accessories`
--

DROP TABLE IF EXISTS `ope_customer_accessories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_customer_accessories` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `dr` int(11) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `product_name` varchar(64) DEFAULT NULL COMMENT '产品名称',
  `product_id` bigint(20) DEFAULT NULL COMMENT '适用于产品的产品Id',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品配件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_customer_contact`
--

DROP TABLE IF EXISTS `ope_customer_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_customer_contact` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `country_code` varchar(64) DEFAULT NULL COMMENT '国家编码如+86',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '客户id',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `first_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `last_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `telephone` varchar(32) DEFAULT NULL COMMENT '电话',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `position` varchar(32) DEFAULT NULL COMMENT '职位',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户联系人表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_customer_inquiry`
--

DROP TABLE IF EXISTS `ope_customer_inquiry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_customer_inquiry` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `dr` int(11) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `order_no` varchar(64) DEFAULT NULL COMMENT '订单号',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '客户id',
  `country` bigint(20) DEFAULT NULL COMMENT '国家',
  `city` bigint(20) DEFAULT NULL COMMENT '城市',
  `district` bigint(20) DEFAULT NULL COMMENT '区域',
  `customer_source` varchar(32) DEFAULT NULL COMMENT '客户来源渠道 官网/email/电话',
  `sales_id` bigint(20) DEFAULT NULL COMMENT '销售员id',
  `status` varchar(32) DEFAULT NULL COMMENT '状态 已处理/未处理（默认）',
  `industry` varchar(32) DEFAULT NULL COMMENT '行业 行业',
  `customer_type` varchar(32) DEFAULT NULL COMMENT '客户类型 企业/个人',
  `first_name` varchar(128) DEFAULT NULL COMMENT '客户名字',
  `last_name` varchar(128) DEFAULT NULL COMMENT '客户名字',
  `full_name` varchar(256) DEFAULT NULL COMMENT '客户名字',
  `company_name` varchar(32) DEFAULT NULL COMMENT '公司名称',
  `country_code` varchar(64) DEFAULT NULL COMMENT '国家编码如+86',
  `telephone` varchar(32) DEFAULT NULL COMMENT '电话',
  `email` varchar(64) DEFAULT NULL COMMENT '客户邮箱',
  `address` varchar(255) DEFAULT NULL COMMENT '客户地址',
  `contact_first` varchar(32) DEFAULT NULL COMMENT '联系人',
  `contact_last` varchar(32) DEFAULT NULL,
  `contant_full_name` varchar(64) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品Id ',
  `product_model` varchar(32) DEFAULT NULL COMMENT '产品型号',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '产品单价',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '单据总价',
  `scooter_quantity` int(11) DEFAULT NULL COMMENT '需求车辆数',
  `pay_status` varchar(32) DEFAULT NULL COMMENT '支付状态',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `source` varchar(32) DEFAULT NULL COMMENT '来源1 询价单 2预订单',
  `bank_card_name` varchar(64) DEFAULT NULL COMMENT '银行卡上面名字',
  `cvv` varchar(64) DEFAULT NULL COMMENT 'cvv',
  `expired_time` date DEFAULT NULL COMMENT '过期时间',
  `card_Num` varchar(64) DEFAULT NULL COMMENT '卡号',
  `postal_code` varchar(64) DEFAULT NULL COMMENT '安全码',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户咨询管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_customer_inquiry_b`
--

DROP TABLE IF EXISTS `ope_customer_inquiry_b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_customer_inquiry_b` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `dr` int(11) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `inquiry_id` bigint(20) DEFAULT NULL COMMENT '询价单Id',
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品Id',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '产品价格',
  `product_qty` int(11) DEFAULT NULL COMMENT '产品数量',
  `product_type` varchar(32) DEFAULT NULL COMMENT '产品类型',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `ope_excle_import`
--

DROP TABLE IF EXISTS `ope_excle_import`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_excle_import` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(11) DEFAULT NULL COMMENT '逻辑删除标识 0正常 1删除',
  `status` varchar(32) NOT NULL COMMENT '状态',
  `batch_no` varchar(32) NOT NULL COMMENT '生产批次号',
  `data_json` text NOT NULL COMMENT '数据json保存记录',
  `count` int(11) NOT NULL DEFAULT '0' COMMENT '导入成功数量',
  `attachment` varchar(64) NOT NULL COMMENT '附件',
  `service_type` varchar(32) NOT NULL COMMENT '业务类型',
  `message` varchar(24) DEFAULT NULL COMMENT '导入说明',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(10,2) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表格导入记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_factory`
--

DROP TABLE IF EXISTS `ope_factory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_factory` (
  `id` bigint(11) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识',
  `status` varchar(64) NOT NULL COMMENT '状态',
  `tenant_id` bigint(11) DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `factory_name` varchar(64) NOT NULL COMMENT '代工厂名称',
  `factory_address` varchar(256) NOT NULL COMMENT '代工厂地址',
  `factory_country` varchar(64) NOT NULL COMMENT '代工厂国家',
  `factory_longitude` decimal(32,10) DEFAULT NULL COMMENT '代工厂经度',
  `factory_latitude` decimal(32,10) DEFAULT NULL COMMENT '代工厂纬度',
  `place_id` varchar(128) DEFAULT NULL COMMENT '地址唯一ID',
  `geo_hash` varchar(64) DEFAULT NULL COMMENT 'geo_hash',
  `factory_tag` varchar(24) NOT NULL COMMENT '代工厂标签',
  `factory_memo` varchar(256) DEFAULT NULL COMMENT '代工厂备注',
  `contact_first_name` varchar(64) DEFAULT NULL COMMENT '联系人名字',
  `contact_last_name` varchar(64) DEFAULT NULL COMMENT '联系人姓氏',
  `contact_full_name` varchar(64) DEFAULT NULL COMMENT '联系人全名',
  `contact_email` varchar(64) NOT NULL COMMENT '联系人邮箱',
  `contact_phone_country_code` varchar(64) NOT NULL COMMENT '手机号归属国家',
  `contact_phone` varchar(24) NOT NULL COMMENT '联系人手机号',
  `payment_cycle` int(11) NOT NULL COMMENT '付款周期',
  `cooperation_time_start` datetime DEFAULT NULL COMMENT '合作开始时间',
  `cooperation_time_end` datetime DEFAULT NULL COMMENT '合作结束时间',
  `business_number` varchar(128) NOT NULL COMMENT '营业执照编号',
  `business_license_annex` varchar(256) NOT NULL COMMENT '营业执照附件',
  `contract_number` varchar(64) DEFAULT NULL COMMENT '合同编号',
  `contract_annex` varchar(256) NOT NULL COMMENT '合同附件',
  `overdue_flag` int(11) NOT NULL DEFAULT '0' COMMENT '是否过期：默认0不过期，-1过期',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `created_by` bigint(11) NOT NULL COMMENT '创建人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `updated_by` bigint(11) NOT NULL COMMENT '更新人',
  `def1` varchar(256) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(256) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(256) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(256) DEFAULT NULL COMMENT '冗余字段',
  `def6` double DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代工厂';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_factory_trace`
--

DROP TABLE IF EXISTS `ope_factory_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_factory_trace` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `factory_id` bigint(20) NOT NULL COMMENT '代工厂ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `status` varchar(64) DEFAULT NULL COMMENT '代工厂操作状态',
  `event` varchar(32) DEFAULT NULL COMMENT '代工厂操作事件',
  `event_time` datetime DEFAULT NULL COMMENT '事件时间',
  `reason` varchar(128) DEFAULT NULL COMMENT '备注说明',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代工厂操作根据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_fr_stock`
--

DROP TABLE IF EXISTS `ope_fr_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_fr_stock` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `whse_id` bigint(20) DEFAULT NULL COMMENT '所属仓库Id',
  `int_total` int(11) DEFAULT NULL COMMENT '入库总数',
  `out_total` int(11) DEFAULT NULL COMMENT '出库总数',
  `available_total` int(11) DEFAULT NULL COMMENT '剩余库存',
  `worn_total` int(11) DEFAULT NULL COMMENT '破损总数',
  `lock_total` int(11) DEFAULT NULL COMMENT '锁定库存',
  `materiel_product_id` bigint(20) DEFAULT NULL COMMENT '所属物料产品Id',
  `materiel_product_type` varchar(32) DEFAULT NULL COMMENT '所属物料产品类型',
  `materiel_product_name` varchar(48) DEFAULT NULL COMMENT '所属物料名称',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime(3) DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='法国仓库库存表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_fr_stock_bill`
--

DROP TABLE IF EXISTS `ope_fr_stock_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_fr_stock_bill` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `stock_id` bigint(20) DEFAULT NULL COMMENT '库存id',
  `direction` varchar(32) DEFAULT NULL COMMENT '入库 0:In 出库 1:Out',
  `source_id` bigint(20) DEFAULT NULL COMMENT '单据来源ID',
  `status` varchar(32) DEFAULT NULL COMMENT '状态:0正常，1异常',
  `total` int(11) DEFAULT NULL COMMENT '入库单数量总计',
  `source_type` varchar(32) DEFAULT NULL COMMENT '单据来源:1采购入库单，2采购入库单，3调拨入库单，4调拨出库单',
  `principal_id` bigint(20) DEFAULT NULL COMMENT '负责人',
  `operatine_time` datetime DEFAULT NULL COMMENT '操作时间',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_fr_stock_product`
--

DROP TABLE IF EXISTS `ope_fr_stock_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_fr_stock_product` (
  `id` bigint(20) NOT NULL,
  `dr` int(11) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL COMMENT '可用、破损',
  `stock_id` bigint(20) DEFAULT NULL COMMENT '库存id',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品Id',
  `lot` varchar(32) DEFAULT NULL COMMENT '批次号',
  `serial_number` varchar(64) DEFAULT NULL COMMENT '序列号',
  `product_number` varchar(32) DEFAULT NULL COMMENT '部件号',
  `product_type` varchar(32) DEFAULT NULL COMMENT '产品类型',
  `in_stock_bill_id` bigint(32) DEFAULT NULL COMMENT '入库单Id',
  `principal_id` bigint(20) DEFAULT NULL COMMENT '入库负责人Id',
  `in_wh_qty` int(10) DEFAULT NULL COMMENT '入库数量',
  `in_stock_time` datetime DEFAULT NULL COMMENT '入库时间',
  `out_stock_bill_id` bigint(20) DEFAULT NULL COMMENT '出库单Id',
  `source_type` varchar(32) DEFAULT NULL COMMENT '单据来源',
  `out_principal_id` bigint(20) DEFAULT NULL COMMENT '出库负责人',
  `out_stock_time` datetime DEFAULT NULL COMMENT '出库时间',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime(3) DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='法国仓库成品库';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_outwh_order`
--

DROP TABLE IF EXISTS `ope_outwh_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_outwh_order` (
  `id` bigint(20) NOT NULL COMMENT '主键 主键',
  `dr` int(11) DEFAULT NULL COMMENT '逻辑删除标识 逻辑删除标识',
  `order_no` varchar(32) DEFAULT NULL COMMENT '订单编号',
  `tracking_num` varchar(64) DEFAULT NULL COMMENT '物流编号',
  `in_wh_id` bigint(20) DEFAULT NULL COMMENT '入库仓库Id',
  `address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `consignee_id` bigint(20) DEFAULT NULL COMMENT '收货人Id（userId）',
  `consignee_phone` varchar(64) DEFAULT NULL COMMENT '收货人电话',
  `country_code` varchar(32) DEFAULT NULL COMMENT '电话国家代码',
  `consignee_email` varchar(64) DEFAULT NULL COMMENT '收货人邮箱',
  `notify_first_name` varchar(32) DEFAULT NULL COMMENT '通知人姓名',
  `notify_last_name` varchar(32) DEFAULT NULL COMMENT '通知人姓名',
  `consign_type` varchar(32) DEFAULT NULL COMMENT '物流方式',
  `consign_method` varchar(32) DEFAULT NULL COMMENT '委托方式',
  `consign_company` varchar(64) DEFAULT NULL COMMENT '物流公司',
  `annex` varchar(255) DEFAULT NULL COMMENT '发货发票',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `product_count` int(11) DEFAULT NULL COMMENT '产品总数',
  `logistics_price` decimal(10,2) DEFAULT NULL COMMENT '物流价格',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime(3) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='中国生产仓库出库单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_outwh_order_b`
--

DROP TABLE IF EXISTS `ope_outwh_order_b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_outwh_order_b` (
  `id` bigint(20) NOT NULL COMMENT '主键 主键',
  `dr` int(11) DEFAULT NULL COMMENT '逻辑删除标识 逻辑删除标识',
  `outwh_order_id` bigint(20) DEFAULT NULL COMMENT '出库单id',
  `stock_id` bigint(20) DEFAULT NULL COMMENT '库存Id',
  `part_product_id` bigint(20) DEFAULT NULL COMMENT '部件产品Id 和 下面的来源一起使用',
  `product_type` varchar(32) DEFAULT NULL COMMENT '产品类型 ',
  `total_count` int(11) DEFAULT NULL COMMENT '总数量',
  `last_out_count` int(11) DEFAULT NULL COMMENT '剩余出货数量',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime(3) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='中国生产仓库出库单子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_outwh_trace`
--

DROP TABLE IF EXISTS `ope_outwh_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_outwh_trace` (
  `id` bigint(20) NOT NULL COMMENT '主键 主键',
  `dr` int(11) DEFAULT NULL COMMENT '逻辑删除标识 逻辑删除标识',
  `wh_out_order_id` bigint(20) DEFAULT NULL COMMENT '出库单id',
  `status` varchar(64) DEFAULT NULL COMMENT '采购单状态',
  `event` varchar(64) DEFAULT NULL COMMENT '事件',
  `event_time` datetime DEFAULT NULL COMMENT '事件时间',
  `memo` varchar(64) DEFAULT NULL COMMENT '备注 放话术参数',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime(3) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='中国生产仓库出库单节点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_part_draft_qc_template`
--

DROP TABLE IF EXISTS `ope_part_draft_qc_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_part_draft_qc_template` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除',
  `part_draft_id` bigint(20) DEFAULT NULL COMMENT '部件Id',
  `import_excel_batch_no` varchar(64) DEFAULT NULL COMMENT '导入批次号',
  `source_type` varchar(32) DEFAULT NULL COMMENT '来源方式',
  `qc_item_name` varchar(128) DEFAULT NULL COMMENT '质检项名称',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部件质检模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_part_draft_qc_template_b`
--

DROP TABLE IF EXISTS `ope_part_draft_qc_template_b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_part_draft_qc_template_b` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除',
  `part_draft_qc_template_id` bigint(20) DEFAULT NULL COMMENT '质检项Id',
  `qc_result` varchar(32) DEFAULT NULL COMMENT '质检结果',
  `pass_flag` tinyint(1) DEFAULT NULL COMMENT '通过标记',
  `upload_flag` tinyint(1) DEFAULT NULL COMMENT '是否允许上传图片',
  `results_sequence` int(11) DEFAULT NULL COMMENT '结果集排序优先级',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部件质检模板结果项';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_part_qc_template`
--

DROP TABLE IF EXISTS `ope_part_qc_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_part_qc_template` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除',
  `part_id` bigint(20) DEFAULT NULL COMMENT '部件Id',
  `import_excel_batch_no` varchar(64) DEFAULT NULL COMMENT '导入批次号',
  `source_type` varchar(32) DEFAULT NULL COMMENT '来源方式',
  `qc_item_name` varchar(128) DEFAULT NULL COMMENT '质检项名称',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部件质检模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_part_qc_template_b`
--

DROP TABLE IF EXISTS `ope_part_qc_template_b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_part_qc_template_b` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除',
  `part_qc_template_id` bigint(20) DEFAULT NULL COMMENT '质检项Id',
  `qc_result` varchar(32) DEFAULT NULL COMMENT '质检结果',
  `pass_flag` tinyint(4) DEFAULT NULL COMMENT '通过标记',
  `upload_flag` tinyint(1) DEFAULT NULL COMMENT '是否允许上传图片',
  `results_sequence` int(11) DEFAULT NULL COMMENT '结果集排序优先级',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部件质检模板结果项';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_parts`
--

DROP TABLE IF EXISTS `ope_parts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_parts` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `import_lot` varchar(32) NOT NULL COMMENT '导入批次号',
  `status` varchar(32) NOT NULL COMMENT '状态',
  `parts_draft_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '部件草稿主键',
  `parts_type` varchar(16) NOT NULL COMMENT '类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery',
  `sec` varchar(32) NOT NULL COMMENT '项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。',
  `parts_number` varchar(32) NOT NULL COMMENT '部品号',
  `cn_name` varchar(32) DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) DEFAULT NULL COMMENT '英文名称',
  `sn_class` varchar(10) NOT NULL COMMENT '是否可销售 是否可销售,0:SC仅可采购，1:SSC可销售可采购',
  `parts_qty` int(11) DEFAULT NULL COMMENT '部品数量',
  `production_cycle` varchar(32) DEFAULT NULL COMMENT '生产周期',
  `cost` varchar(32) DEFAULT NULL COMMENT '成本',
  `supplier_id` bigint(20) DEFAULT NULL COMMENT '供应商',
  `dwg` varchar(128) DEFAULT NULL COMMENT '图纸',
  `note` varchar(32) DEFAULT NULL COMMENT '备注',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `id_class` tinyint(1) DEFAULT NULL COMMENT '是否有唯一编码',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_parts_draft`
--

DROP TABLE IF EXISTS `ope_parts_draft`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_parts_draft` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `import_lot` varchar(32) NOT NULL COMMENT '导入批次号',
  `status` varchar(32) NOT NULL COMMENT '状态',
  `parts_type` varchar(16) NOT NULL COMMENT '类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery',
  `sec` varchar(32) NOT NULL COMMENT '项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。',
  `parts_number` varchar(32) NOT NULL COMMENT '部品号',
  `cn_name` varchar(32) DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) DEFAULT NULL COMMENT '英文名称',
  `sn_class` varchar(10) NOT NULL COMMENT '是否可销售 是否可销售,0:SC仅可采购，1:SSC可销售可采购',
  `parts_qty` int(11) DEFAULT NULL COMMENT '部品数量',
  `production_cycle` varchar(32) DEFAULT NULL COMMENT '生产周期',
  `cost` varchar(32) DEFAULT NULL COMMENT '成本',
  `supplier_id` bigint(20) DEFAULT NULL COMMENT '供应商',
  `dwg` varchar(128) DEFAULT NULL COMMENT '图纸',
  `note` varchar(32) DEFAULT NULL COMMENT '备注',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `id_class` tinyint(1) DEFAULT NULL COMMENT '是否有唯一编码',
  `perfect_flag` tinyint(1) DEFAULT '0' COMMENT '是否信息完善',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步，只有在信息完善的前提下可以进行同步操作',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料草稿表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_parts_draft_history_record`
--

DROP TABLE IF EXISTS `ope_parts_draft_history_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_parts_draft_history_record` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `parts_draft_id` bigint(20) NOT NULL COMMENT '产品部品表主键',
  `event` varchar(32) NOT NULL COMMENT '操作事件',
  `import_lot` varchar(32) NOT NULL COMMENT '导入批次号',
  `status` varchar(16) NOT NULL COMMENT '状态',
  `parts_type` varchar(32) NOT NULL COMMENT '类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery',
  `sec` varchar(32) NOT NULL COMMENT '项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。',
  `parts_number` varchar(32) NOT NULL COMMENT '部品号',
  `cn_name` varchar(32) DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) DEFAULT NULL COMMENT '英文名称',
  `sn_class` varchar(10) NOT NULL COMMENT '是否可销售,0:SC仅可采购，1:SSC可销售可采购',
  `parts_qty` int(11) DEFAULT NULL COMMENT '部品数量',
  `production_cycle` varchar(32) DEFAULT NULL COMMENT '生产周期',
  `cost` varchar(32) DEFAULT NULL COMMENT '成本',
  `supplier_id` bigint(20) DEFAULT NULL COMMENT '供应商',
  `dwg` varchar(128) DEFAULT NULL COMMENT '图纸',
  `note` varchar(32) DEFAULT NULL COMMENT '备注',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,2) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='BOM产品部件草稿表操作记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_parts_product`
--

DROP TABLE IF EXISTS `ope_parts_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_parts_product` (
  `id` bigint(20) NOT NULL COMMENT '主键 主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `status` varchar(32) NOT NULL COMMENT '状态 up上架，down下架(默认)',
  `sn_class` varchar(10) NOT NULL COMMENT '是否可销售,0:SC仅可采购，1:SSC可销售可采购',
  `product_type` int(11) NOT NULL COMMENT '产品类型 如1整车，2组装套件，电池',
  `product_code` varchar(32) DEFAULT NULL COMMENT '产品编码',
  `product_number` varchar(32) NOT NULL COMMENT '产品编号',
  `cn_name` varchar(32) DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) DEFAULT NULL COMMENT '英文名称',
  `production_cycle` varchar(32) DEFAULT NULL COMMENT '生产周期',
  `sum_parts_qty` int(11) NOT NULL COMMENT '已选部品数量',
  `model` varchar(32) DEFAULT NULL COMMENT '型号',
  `pictures` varchar(64) DEFAULT NULL COMMENT '图片',
  `color` varchar(32) DEFAULT NULL COMMENT '颜色',
  `after_sales_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否支持售后服务',
  `added_services_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否支持增值服务',
  `mater_parameter` varchar(32) DEFAULT NULL COMMENT '产品参数 存储JSON',
  `other_parameter` varchar(32) DEFAULT NULL COMMENT '其他参数 存储JSON',
  `note` varchar(32) DEFAULT NULL COMMENT '备注',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部件产品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_parts_product_b`
--

DROP TABLE IF EXISTS `ope_parts_product_b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_parts_product_b` (
  `id` bigint(20) NOT NULL COMMENT '主键 主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除 逻辑删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID 租户ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID 用户ID',
  `status` varchar(32) NOT NULL COMMENT '状态 状态',
  `parts_id` bigint(20) NOT NULL COMMENT '部品主键 部品主键',
  `parts_product_id` bigint(20) NOT NULL COMMENT '部品组装表主键 部品组装表主键',
  `parts_qty` int(11) NOT NULL COMMENT '部品数量 数量',
  `note` varchar(32) DEFAULT NULL COMMENT '备注 备注',
  `revision` int(11) NOT NULL COMMENT '乐观锁 乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人 创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间 创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人 更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间 更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段 冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部件产品表条目';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_parts_sec`
--

DROP TABLE IF EXISTS `ope_parts_sec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_parts_sec` (
  `id` bigint(20) NOT NULL COMMENT '主键 主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除 逻辑删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID 租户ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID 用户ID',
  `status` varchar(32) NOT NULL COMMENT '状态 状态',
  `name` varchar(32) DEFAULT NULL COMMENT '项目区域名称 项目区域名称',
  `code` varchar(32) DEFAULT NULL COMMENT '项目区域编码 项目区域编码',
  `value` int(11) DEFAULT NULL COMMENT '值',
  `note` varchar(32) DEFAULT NULL COMMENT '备注 备注',
  `revision` int(11) NOT NULL COMMENT '乐观锁 乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人 创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间 创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人 更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间 更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段 冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部品项目区域表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_parts_type`
--

DROP TABLE IF EXISTS `ope_parts_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_parts_type` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `status` varchar(32) NOT NULL COMMENT '状态',
  `name` varchar(32) DEFAULT NULL COMMENT '部品类型名称',
  `code` varchar(32) DEFAULT NULL COMMENT '部品类型编码',
  `value` int(11) DEFAULT NULL COMMENT '值',
  `note` varchar(32) DEFAULT NULL COMMENT '备注',
  `sn_class_flag` varchar(1) NOT NULL DEFAULT '0' COMMENT '是否可销售,0:SC仅可采购，1:SSC可销售可采购',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部品类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_price_sheet`
--

DROP TABLE IF EXISTS `ope_price_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_price_sheet` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `status` varchar(32) NOT NULL COMMENT '状态 Invalid失效，Effective有效，Cancel取消',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格 浮点型价格',
  `currency_type` varchar(32) DEFAULT NULL COMMENT '货币类型 如英镑，美元，人民币',
  `currency_unit` varchar(32) DEFAULT NULL COMMENT '货币单位 如¥，$，€，	￡',
  `standard_currency` varchar(32) DEFAULT NULL COMMENT '标准货币 用户货币转换',
  `exchange_rate` varchar(32) DEFAULT NULL COMMENT '汇率 用于汇率转换',
  `parts_id` bigint(20) DEFAULT NULL COMMENT '部品主键 用于关联部品',
  `begin_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,2) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报价列表 报价列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_price_sheet_history`
--

DROP TABLE IF EXISTS `ope_price_sheet_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_price_sheet_history` (
  `id` bigint(20) NOT NULL COMMENT '主键 主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除 逻辑删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID 租户ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID 用户ID',
  `parts_id` bigint(20) DEFAULT NULL COMMENT '部品主键 用于关联部品',
  `price_sheet_id` bigint(20) DEFAULT NULL COMMENT '报价表主键 用于关联报价表',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格 浮点型价格',
  `currency_type` varchar(32) DEFAULT NULL COMMENT '货币类型 如英镑，美元，人民币',
  `currency_unit` varchar(32) DEFAULT NULL COMMENT '货币单位 如¥，$，€，	￡',
  `standard_currency` varchar(32) DEFAULT NULL COMMENT '标准货币 用户货币转换',
  `exchange_rate` varchar(32) DEFAULT NULL COMMENT '汇率 用于汇率转换',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,2) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报价列表历史记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_product_assembly`
--

DROP TABLE IF EXISTS `ope_product_assembly`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_product_assembly` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT NULL COMMENT '删除标识',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品Id',
  `assembly_b_id` bigint(20) DEFAULT NULL COMMENT '组装单子表id',
  `assembly_id` bigint(20) DEFAULT NULL COMMENT '组装单id',
  `inwh_status` tinyint(1) DEFAULT NULL COMMENT '是否入库',
  `product_name` varchar(32) DEFAULT NULL COMMENT '产品名称',
  `product_serial_num` varchar(32) DEFAULT NULL COMMENT '产品序列号',
  `product_type` varchar(32) DEFAULT NULL COMMENT '产品类型',
  `product_code` varchar(32) DEFAULT NULL COMMENT '产品编码',
  `production_date` datetime DEFAULT NULL COMMENT '生产日期',
  `print_flag` tinyint(1) DEFAULT NULL COMMENT '是否打印标识',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品组装表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_product_assembly_b`
--

DROP TABLE IF EXISTS `ope_product_assembly_b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_product_assembly_b` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT NULL COMMENT '删除标识',
  `product_assembly_id` bigint(20) DEFAULT NULL COMMENT '产品组装表Id',
  `part_id` bigint(20) DEFAULT NULL COMMENT '部件Id',
  `qty` int(20) DEFAULT NULL COMMENT '部件数量',
  `part_serial_num` varchar(64) DEFAULT NULL COMMENT '部件序列号',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品组装子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_product_qc_template`
--

DROP TABLE IF EXISTS `ope_product_qc_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_product_qc_template` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除',
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品Id',
  `import_excel_batch_no` varchar(64) DEFAULT NULL COMMENT '导入批次号',
  `source_type` varchar(32) DEFAULT NULL COMMENT '来源方式',
  `qc_item_name` varchar(128) DEFAULT NULL COMMENT '质检项名称',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品质检模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_product_qc_template_b`
--

DROP TABLE IF EXISTS `ope_product_qc_template_b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_product_qc_template_b` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除',
  `product_qc_template_id` bigint(20) DEFAULT NULL COMMENT '质检项Id',
  `qc_result` varchar(32) DEFAULT NULL COMMENT '质检结果',
  `pass_flag` tinyint(1) DEFAULT NULL COMMENT '通过标记',
  `upload_flag` tinyint(1) DEFAULT NULL COMMENT '是否允许上传图片',
  `results_sequence` int(11) DEFAULT NULL COMMENT '质检结果集优先级',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部件质检模板结果项';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_purchas`
--

DROP TABLE IF EXISTS `ope_purchas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_purchas` (
  `id` bigint(20) NOT NULL COMMENT '主键 主键',
  `dr` int(11) DEFAULT NULL COMMENT '逻辑删除标识 逻辑删除标识',
  `user_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `consignee_id` bigint(20) DEFAULT NULL COMMENT '收货人Id',
  `contract_no` varchar(128) CHARACTER SET latin1 DEFAULT NULL COMMENT '采购单编号 采购单标号',
  `status` varchar(128) CHARACTER SET latin1 DEFAULT NULL COMMENT '状态 采购单状态',
  `payment_type` varchar(32) DEFAULT NULL COMMENT '付款类型',
  `factory_id` bigint(20) DEFAULT NULL COMMENT '工厂主键 代工厂Id',
  `factory_annex` varchar(256) CHARACTER SET latin1 DEFAULT NULL COMMENT '工厂组装附件',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '总金额 总金额',
  `total_qty` int(11) DEFAULT NULL COMMENT '总数量 总数量',
  `lave_wait_qc_total` int(10) DEFAULT NULL COMMENT '待质检总数',
  `in_wait_wh_total` int(10) DEFAULT NULL COMMENT '待入库总数',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_purchas_b`
--

DROP TABLE IF EXISTS `ope_purchas_b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_purchas_b` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT NULL COMMENT '逻辑删除表示',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `purchas_id` bigint(20) DEFAULT NULL COMMENT '采购订单Id',
  `part_id` bigint(20) DEFAULT NULL COMMENT '部品Id',
  `supplier_id` bigint(20) DEFAULT NULL COMMENT '供应商Id',
  `supplier_annex` varchar(256) CHARACTER SET latin1 DEFAULT NULL COMMENT '供应商附件',
  `qc_status` varchar(32) CHARACTER SET latin1 DEFAULT NULL COMMENT 'qc质检状态',
  `price` decimal(10,2) DEFAULT NULL COMMENT '单价',
  `total_count` int(10) DEFAULT NULL COMMENT '总数量',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '总价格',
  `lave_wait_qc_qty` int(10) DEFAULT NULL COMMENT '待质检数',
  `in_wait_wh_qty` int(10) DEFAULT NULL COMMENT '待入库数量',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购单条目';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_purchas_b_qc`
--

DROP TABLE IF EXISTS `ope_purchas_b_qc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_purchas_b_qc` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT NULL COMMENT '删除标志',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'userId',
  `purchas_b_id` bigint(20) DEFAULT NULL COMMENT '采购单条目Id',
  `parts_id` bigint(20) DEFAULT NULL COMMENT '部品Id',
  `quality_inspector_id` bigint(20) DEFAULT NULL COMMENT '质检人Id',
  `batch_no` varchar(32) CHARACTER SET latin1 DEFAULT NULL COMMENT '质检批次号',
  `status` varchar(32) DEFAULT NULL COMMENT '质检状态',
  `total_quality_inspected` int(20) DEFAULT NULL COMMENT '需要质检总数',
  `pass_count` int(11) DEFAULT NULL COMMENT '质检通过数量',
  `fail_count` int(11) DEFAULT NULL COMMENT '质检失败数量',
  `quality_inspection_time` datetime DEFAULT NULL COMMENT '质检时间',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购条目QC质检';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_purchas_b_qc_item`
--

DROP TABLE IF EXISTS `ope_purchas_b_qc_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_purchas_b_qc_item` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT NULL COMMENT '删除标志',
  `purchas_b_id` bigint(20) DEFAULT NULL COMMENT '采购单子表Id',
  `part_id` bigint(20) DEFAULT NULL COMMENT '部品Id',
  `purchas_b_qc_id` bigint(20) DEFAULT '0' COMMENT '质检结果Id',
  `purchas_b_lot_trace_id` bigint(20) DEFAULT NULL COMMENT '质检批次记录Id',
  `batch_no` varchar(64) DEFAULT NULL COMMENT '批次号',
  `serial_num` varchar(64) DEFAULT NULL COMMENT '部品序列号',
  `qc_batch_total` int(32) DEFAULT NULL COMMENT '批量质检数量 和质检方式连用',
  `qc_result` varchar(32) DEFAULT NULL COMMENT '质检结果',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部件质检条目';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_purchas_lot_trace`
--

DROP TABLE IF EXISTS `ope_purchas_lot_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_purchas_lot_trace` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT NULL COMMENT '删除标志',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'userId',
  `purchas_id` bigint(20) DEFAULT NULL COMMENT '采购单号',
  `quality_inspector_id` bigint(20) DEFAULT NULL COMMENT '质检人Id',
  `batch_no` varchar(32) CHARACTER SET latin1 DEFAULT NULL COMMENT '质检批次号',
  `total_quality_inspected` int(20) DEFAULT NULL COMMENT '需要质检总数',
  `pass_count` int(11) DEFAULT NULL COMMENT '质检通过数量',
  `fail_count` int(11) DEFAULT NULL COMMENT '质检失败数量',
  `quality_inspection_time` datetime DEFAULT NULL COMMENT '质检时间',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购单批次质检记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_purchas_payment`
--

DROP TABLE IF EXISTS `ope_purchas_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_purchas_payment` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'userId',
  `purchas_id` bigint(20) DEFAULT NULL COMMENT '采购订单ID',
  `payment_type` varchar(32) NOT NULL COMMENT '分期，月结',
  `planned_payment_time` datetime NOT NULL COMMENT '预计付款时间',
  `payment_day` int(10) DEFAULT NULL COMMENT '付款周期',
  `payment_time` datetime DEFAULT NULL COMMENT '实际付款时间',
  `payment_status` varchar(32) DEFAULT NULL COMMENT '支付状态:Paid Unpaid',
  `payment_priority` int(11) NOT NULL DEFAULT '0' COMMENT '支付优先级',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `amount` decimal(10,2) NOT NULL COMMENT '价格',
  `amount_proportion` int(11) NOT NULL COMMENT '55% 标示为 55',
  `invoice_num` varchar(32) DEFAULT NULL COMMENT '发票单号',
  `invoice_picture` varchar(64) DEFAULT NULL COMMENT '发票附件',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售订单支付表 分期时表明是第几期支付的';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_purchas_product`
--

DROP TABLE IF EXISTS `ope_purchas_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_purchas_product` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT NULL COMMENT '删除表示',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `purchas_id` bigint(20) DEFAULT NULL COMMENT '采购订单id',
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品Id',
  `id_class` varchar(32) CHARACTER SET latin1 DEFAULT NULL COMMENT 'qc质检类型（整车质检、部品质检）',
  `product_type` varchar(32) CHARACTER SET latin1 DEFAULT NULL COMMENT '产品类型',
  `product_num` varchar(32) CHARACTER SET latin1 DEFAULT NULL COMMENT '产品编号',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购订单产品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_purchas_qc_trace`
--

DROP TABLE IF EXISTS `ope_purchas_qc_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_purchas_qc_trace` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT NULL COMMENT '删除标识',
  `part_id` bigint(20) DEFAULT NULL COMMENT '部件Id',
  `purchas_id` bigint(20) DEFAULT NULL COMMENT '采购单id',
  `purchas_b_qc_id` bigint(20) DEFAULT NULL COMMENT '采购单子表Id',
  `part_qc_template_id` bigint(20) DEFAULT NULL COMMENT '质检模板Id',
  `part_qc_template_name` varchar(64) DEFAULT NULL COMMENT '模板质检项名称',
  `part_qc_template_b_id` bigint(32) DEFAULT NULL COMMENT '质检结果Id',
  `part_qc_template_b_name` varchar(64) DEFAULT NULL COMMENT '模板质检结果名称',
  `purchas_b_qc_item_id` bigint(20) DEFAULT NULL COMMENT '质检条目Id',
  `part_qc_picture` varchar(255) DEFAULT NULL COMMENT '质检图片的逗号分隔',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购单质检记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_purchas_trace`
--

DROP TABLE IF EXISTS `ope_purchas_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_purchas_trace` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'userId',
  `purchas_id` bigint(20) NOT NULL COMMENT '采购订单id',
  `status` varchar(64) DEFAULT NULL COMMENT '采购单状态',
  `event` varchar(64) DEFAULT NULL COMMENT '事件',
  `event_time` datetime DEFAULT NULL COMMENT '事件时间',
  `memo` varchar(64) DEFAULT NULL COMMENT '备注 放话术参数',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购订单节点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_regional_price_sheet`
--

DROP TABLE IF EXISTS `ope_regional_price_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_regional_price_sheet` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `status` varchar(32) NOT NULL COMMENT '状态 状态',
  `parts_product_id` bigint(20) DEFAULT NULL COMMENT '产品Id',
  `price_type` varchar(32) NOT NULL COMMENT '价格类型，1.销售价，2采购价，3其他价格',
  `sales_price` decimal(10,2) DEFAULT NULL COMMENT '销售价格 浮点型价格',
  `effective_time` datetime DEFAULT NULL COMMENT '生效时间 默认当前生效',
  `valid_flag` varchar(32) DEFAULT NULL COMMENT '有效标识 标识当前区域报价是否生效',
  `currency_type` varchar(32) DEFAULT NULL COMMENT '货币类型 如英镑，美元，人民币',
  `currency_unit` varchar(32) DEFAULT NULL COMMENT '货币单位 如¥，$，€，	￡',
  `standard_currency` varchar(32) DEFAULT NULL COMMENT '标准货币 用户货币转换',
  `exchange_rate` varchar(32) DEFAULT NULL COMMENT '汇率 用于汇率转换',
  `country_code` varchar(128) NOT NULL COMMENT '国家编码 当前销售国家',
  `country_city` varchar(128) NOT NULL COMMENT '国家城市 当然销售国家的城市',
  `country_language` varchar(64) NOT NULL COMMENT '国家语言 当前销售国家语言',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,2) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区域报价表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_regional_price_sheet_history`
--

DROP TABLE IF EXISTS `ope_regional_price_sheet_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_regional_price_sheet_history` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL COMMENT '逻辑删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `regional_price_sheet_id` bigint(20) DEFAULT NULL COMMENT '区域报价主键 关联区域报价表',
  `parts_product_id` bigint(20) DEFAULT NULL COMMENT '产品ID',
  `price_type` varchar(32) NOT NULL COMMENT '价格类型，1.销售价，2采购价，3其他价格',
  `sales_price` decimal(10,2) DEFAULT NULL COMMENT '销售价格 浮点型价格',
  `currency_type` varchar(32) DEFAULT NULL COMMENT '货币类型 如英镑，美元，人民币',
  `currency_unit` varchar(32) DEFAULT NULL COMMENT '货币单位 如¥，$，€，	￡',
  `standard_currency` varchar(32) DEFAULT NULL COMMENT '标准货币 用户货币转换',
  `exchange_rate` varchar(32) DEFAULT NULL COMMENT '汇率 用于汇率转换',
  `country_code` varchar(128) NOT NULL COMMENT '国家编码 当前销售国家',
  `country_city` varchar(128) NOT NULL COMMENT '国家城市 当然销售国家的城市',
  `country_language` varchar(64) NOT NULL COMMENT '国家语言 当前销售国家语言',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,2) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区域报价表历史记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sale_area`
--

DROP TABLE IF EXISTS `ope_sale_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sale_area` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识',
  `p_id` bigint(20) DEFAULT NULL COMMENT '父级区域id',
  `area_code` varchar(50) DEFAULT NULL COMMENT '区域编码',
  `area_name` varchar(100) DEFAULT NULL COMMENT '区域名称',
  `level` int(2) DEFAULT NULL COMMENT '区域等级',
  `longitude` varchar(50) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(50) DEFAULT NULL COMMENT '纬度',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `update_time` datetime(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售区域表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_stock`
--

DROP TABLE IF EXISTS `ope_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_stock` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `tenant_id` bigint(20) DEFAULT NULL,
  `whse_id` bigint(20) DEFAULT NULL COMMENT '所属仓库Id',
  `int_total` int(11) DEFAULT NULL COMMENT '入库总数',
  `out_total` int(11) DEFAULT NULL COMMENT '出库总数',
  `available_total` int(11) DEFAULT NULL COMMENT '剩余库存',
  `worn_total` int(11) DEFAULT NULL COMMENT '破损总数',
  `lock_total` int(11) DEFAULT NULL COMMENT '锁定库存',
  `wait_product_total` int(11) DEFAULT NULL COMMENT '待生产',
  `wait_stored_total` int(11) DEFAULT NULL COMMENT '待入库',
  `materiel_product_id` bigint(20) DEFAULT NULL COMMENT '所属物料产品Id',
  `materiel_product_type` varchar(32) DEFAULT NULL COMMENT '所属物料产品类型',
  `materiel_product_name` varchar(48) DEFAULT NULL COMMENT '所属物料名称',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存总表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_stock_bill`
--

DROP TABLE IF EXISTS `ope_stock_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_stock_bill` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `stock_id` bigint(20) DEFAULT NULL COMMENT '库存id',
  `direction` varchar(32) DEFAULT NULL COMMENT '入库 0:In 出库 1:Out',
  `source_id` bigint(20) DEFAULT NULL COMMENT '单据来源ID',
  `status` varchar(32) DEFAULT NULL COMMENT '状态:0正常，1异常',
  `total` int(11) DEFAULT NULL COMMENT '入库单数量总计',
  `source_type` varchar(32) DEFAULT NULL COMMENT '单据来源:1采购入库单，2采购入库单，3调拨入库单，4调拨出库单',
  `principal_id` bigint(20) DEFAULT NULL COMMENT '负责人',
  `operatine_time` datetime DEFAULT NULL COMMENT '操作时间',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_stock_prod_part`
--

DROP TABLE IF EXISTS `ope_stock_prod_part`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_stock_prod_part` (
  `id` bigint(20) NOT NULL,
  `dr` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `status` varchar(32) DEFAULT NULL COMMENT '可用、破损',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `stock_id` bigint(20) DEFAULT NULL COMMENT '库存id',
  `part_id` bigint(20) DEFAULT NULL COMMENT '物料ID',
  `lot` varchar(32) DEFAULT NULL COMMENT '批次号',
  `in_stock_bill_id` bigint(32) DEFAULT NULL COMMENT '入库单Id',
  `out_stock_bill_id` bigint(20) DEFAULT NULL COMMENT '出库单Id',
  `serial_number` varchar(64) DEFAULT NULL COMMENT '序列号',
  `parts_number` varchar(32) DEFAULT NULL COMMENT '部品号',
  `principal_id` bigint(20) DEFAULT NULL COMMENT '入库负责人Id',
  `in__wh_qty` int(10) DEFAULT NULL COMMENT '入库数量',
  `available_qty` int(11) DEFAULT NULL COMMENT '可用数量',
  `in_stock_time` datetime DEFAULT NULL COMMENT '入库时间',
  `out_stock_time` datetime DEFAULT NULL COMMENT '出库时间',
  `out_principal_id` bigint(20) DEFAULT NULL COMMENT '出库负责人',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生产原料库存';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_stock_prod_product`
--

DROP TABLE IF EXISTS `ope_stock_prod_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_stock_prod_product` (
  `id` bigint(20) NOT NULL,
  `dr` int(11) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL COMMENT '可用、破损',
  `stock_id` bigint(20) DEFAULT NULL COMMENT '库存id',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品Id',
  `lot` varchar(32) DEFAULT NULL COMMENT '批次号',
  `serial_number` varchar(64) DEFAULT NULL COMMENT '序列号',
  `product_number` varchar(32) DEFAULT NULL COMMENT '部件号',
  `in_stock_bill_id` bigint(32) DEFAULT NULL COMMENT '入库单Id',
  `principal_id` bigint(20) DEFAULT NULL COMMENT '入库负责人Id',
  `in__wh_qty` int(10) DEFAULT NULL COMMENT '入库数量',
  `available_qty` int(11) DEFAULT NULL COMMENT '可用数量',
  `in_stock_time` datetime DEFAULT NULL COMMENT '入库时间',
  `out_stock_bill_id` bigint(20) DEFAULT NULL COMMENT '出库单Id',
  `bind_order_id` bigint(20) DEFAULT NULL COMMENT '绑定的出库单Id',
  `source_type` varchar(32) DEFAULT NULL COMMENT '单据来源',
  `out_principal_id` bigint(20) DEFAULT NULL COMMENT '出库负责人',
  `out_stock_time` datetime DEFAULT NULL COMMENT '出库时间',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生产成品库';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_stock_purchas`
--

DROP TABLE IF EXISTS `ope_stock_purchas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_stock_purchas` (
  `id` bigint(20) NOT NULL,
  `dr` int(11) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL COMMENT '可用、破损',
  `stock_id` bigint(20) DEFAULT NULL COMMENT '库存id',
  `part_id` bigint(20) DEFAULT NULL,
  `lot` varchar(32) DEFAULT NULL COMMENT '批次号',
  `part_number` varchar(32) DEFAULT NULL COMMENT '物料号',
  `serial_number` varchar(64) DEFAULT NULL COMMENT '序列号:QC质检合格后产生',
  `principal_id` bigint(20) DEFAULT NULL COMMENT '入库负责人Id',
  `in__wh_qty` int(10) DEFAULT NULL COMMENT '入库数量',
  `available_qty` int(11) DEFAULT NULL COMMENT '可用数量',
  `in_stock_time` datetime DEFAULT NULL COMMENT '入库时间',
  `in_stock_bill_id` bigint(32) DEFAULT NULL COMMENT '入库单Id',
  `out_stock_bill_id` bigint(20) DEFAULT NULL COMMENT '出库单Id',
  `bind_order_id` bigint(20) DEFAULT NULL COMMENT '绑定的出库单Id',
  `source_type` varchar(32) DEFAULT NULL COMMENT '单据来源',
  `out_principal_id` bigint(20) DEFAULT NULL COMMENT '出库负责人',
  `out_stock_time` datetime DEFAULT NULL COMMENT '出库时间',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购库存';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_supplier`
--

DROP TABLE IF EXISTS `ope_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_supplier` (
  `id` bigint(11) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识',
  `status` varchar(64) NOT NULL COMMENT '状态',
  `tenant_id` bigint(11) DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `supplier_name` varchar(64) NOT NULL COMMENT '采购商名称',
  `supplier_address` varchar(256) NOT NULL COMMENT '采购商地址',
  `supplier_country` varchar(64) NOT NULL COMMENT '采购商国家',
  `supplier_longitude` decimal(32,10) DEFAULT NULL COMMENT '采购商经度',
  `supplier_latitude` decimal(32,10) DEFAULT NULL COMMENT '采购商纬度',
  `place_id` varchar(128) DEFAULT NULL COMMENT '地址唯一ID',
  `geo_hash` varchar(64) DEFAULT NULL COMMENT 'geo_hash',
  `supplier_tag` varchar(24) NOT NULL COMMENT '采购商标签',
  `supplier_memo` varchar(256) DEFAULT NULL COMMENT '采购商备注',
  `contact_first_name` varchar(64) DEFAULT NULL COMMENT '联系人名字',
  `contact_last_name` varchar(64) DEFAULT NULL COMMENT '联系人姓氏',
  `contact_full_name` varchar(64) DEFAULT NULL COMMENT '联系人全名',
  `contact_email` varchar(64) NOT NULL COMMENT '联系人邮箱',
  `contact_phone_country_code` varchar(64) NOT NULL COMMENT '手机号归属国家',
  `contact_phone` varchar(24) NOT NULL COMMENT '联系人手机号',
  `payment_cycle` int(11) NOT NULL COMMENT '付款周期',
  `cooperation_time_start` datetime DEFAULT NULL COMMENT '合作开始时间',
  `cooperation_time_end` datetime DEFAULT NULL COMMENT '合作结束时间',
  `business_number` varchar(128) NOT NULL COMMENT '营业执照编号',
  `business_license_annex` varchar(256) NOT NULL COMMENT '营业执照附件',
  `contract_number` varchar(64) DEFAULT NULL COMMENT '合同编号',
  `contract_annex` varchar(256) NOT NULL COMMENT '合同附件',
  `overdue_flag` int(11) NOT NULL DEFAULT '0' COMMENT '是否过期：默认0不过期，-1过期',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `created_by` bigint(11) NOT NULL COMMENT '创建人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `updated_by` bigint(11) NOT NULL COMMENT '更新人',
  `def1` varchar(256) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(256) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(256) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(256) DEFAULT NULL COMMENT '冗余字段',
  `def6` double DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购商';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_supplier_trace`
--

DROP TABLE IF EXISTS `ope_supplier_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_supplier_trace` (
  `id` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `supplier_id` bigint(11) NOT NULL COMMENT '采购商ID',
  `tenant_id` bigint(11) DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `status` varchar(64) DEFAULT NULL COMMENT '采购商操作状态',
  `event` varchar(32) DEFAULT NULL COMMENT '采购商操作事件',
  `event_time` datetime DEFAULT NULL COMMENT '事件时间',
  `reason` varchar(128) DEFAULT NULL COMMENT '备注说明',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购商操作根据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sys_dept`
--

DROP TABLE IF EXISTS `ope_sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `p_id` bigint(20) DEFAULT NULL COMMENT '父级部门id',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `principal` bigint(20) DEFAULT '0' COMMENT '负责人',
  `level` int(11) NOT NULL DEFAULT '1' COMMENT '级别0公司，1部门',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `code` varchar(64) DEFAULT NULL COMMENT '部门编码',
  `dept_status` int(2) DEFAULT '1' COMMENT '部门状态 1：正常，2：禁用',
  `description` varchar(128) DEFAULT NULL COMMENT '描述说明',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1004416 DEFAULT CHARSET=utf8mb4 COMMENT='部门管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sys_dept_relation`
--

DROP TABLE IF EXISTS `ope_sys_dept_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_dept_relation` (
  `ancestor` bigint(20) NOT NULL COMMENT '祖先节点',
  `descendant` bigint(20) NOT NULL COMMENT '后代节点',
  UNIQUE KEY `ancestor` (`ancestor`,`descendant`) USING BTREE,
  KEY `idx1` (`ancestor`) USING BTREE,
  KEY `idx2` (`descendant`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sys_log`
--

DROP TABLE IF EXISTS `ope_sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_log` (
  `id` bigint(11) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT '0' COMMENT '逻辑删除标识 0：正常，1：删除',
  `op_modul` varchar(255) DEFAULT NULL COMMENT '操作模块',
  `op_user_name` varchar(255) DEFAULT NULL COMMENT '操作人名称',
  `op_user_code` varchar(255) DEFAULT NULL COMMENT '操作人编码',
  `op_user_dept_name` varchar(255) DEFAULT NULL COMMENT '操作人部门',
  `op_method` varchar(255) DEFAULT NULL COMMENT '操作方法',
  `login_ip` varchar(255) DEFAULT NULL COMMENT '登陆的IP',
  `request_address` varchar(255) DEFAULT NULL COMMENT '请求地址',
  `request_param` json DEFAULT NULL COMMENT '请求参数',
  `request_type` varchar(20) DEFAULT NULL COMMENT '请求方式',
  `response_param` json DEFAULT NULL COMMENT '返回参数',
  `exp_msg` varchar(2000) DEFAULT NULL COMMENT '异常信息',
  `log_type` int(2) DEFAULT '1' COMMENT '日志类型，1:登陆，2：操作',
  `if_success` int(2) DEFAULT NULL COMMENT '请求是否成功，1：是，0：否',
  `time_consum` bigint(11) DEFAULT NULL COMMENT '请求时长（毫秒）',
  `created_by` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `updated_by` bigint(11) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '冗余字段',
  `def4` decimal(10,2) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sys_menu`
--

DROP TABLE IF EXISTS `ope_sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识',
  `p_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(32) DEFAULT NULL COMMENT '菜单名称',
  `code` varchar(64) NOT NULL COMMENT '菜单编码',
  `permission` varchar(32) DEFAULT NULL COMMENT '权限码',
  `path` varchar(128) DEFAULT NULL COMMENT '路由',
  `component` varchar(64) DEFAULT NULL COMMENT '对应路由组件component',
  `type` char(1) DEFAULT '0' COMMENT '菜单类型：1:目录，2:菜单，3：按钮，4:开放API   ',
  `if_to_link` char(1) DEFAULT NULL COMMENT '是否外链。0：否，1：是',
  `menu_status` int(2) unsigned DEFAULT '1' COMMENT '状态 1：正常，2：禁用',
  `icon` varchar(32) DEFAULT NULL COMMENT '图表',
  `level` int(11) DEFAULT '0' COMMENT '等级,1:目录，2:菜单，3：按钮',
  `sort` int(11) DEFAULT '1' COMMENT '菜单权重',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` decimal(10,2) DEFAULT NULL COMMENT '冗余字段',
  `def6` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1010625 DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sys_position`
--

DROP TABLE IF EXISTS `ope_sys_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_position` (
  `id` bigint(11) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT '0' COMMENT '逻辑删除标识 0：正常，1：删除',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `position_name` varchar(50) DEFAULT NULL COMMENT '岗位名称',
  `position_code` varchar(32) DEFAULT NULL COMMENT '岗位编码',
  `dept_id` bigint(11) DEFAULT NULL COMMENT '所属部门id',
  `sort` int(2) DEFAULT NULL COMMENT '岗位排序',
  `position_status` int(2) DEFAULT '1' COMMENT '岗位状态  1：正常，2：禁用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `created_by` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `updated_by` bigint(11) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '冗余字段',
  `def4` decimal(10,2) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sys_role`
--

DROP TABLE IF EXISTS `ope_sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `position_id` bigint(11) DEFAULT NULL COMMENT '所属岗位id',
  `role_name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(64) DEFAULT NULL COMMENT '角色编码',
  `role_status` int(2) DEFAULT '1' COMMENT '角色状态  1：正常，2：禁用',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `sale_area` int(1) DEFAULT '0' COMMENT '是否开启销售区域功能 0：否，1：是',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `role_idx1_role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1012625 DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sys_role_data`
--

DROP TABLE IF EXISTS `ope_sys_role_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_role_data` (
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `data_type` int(2) DEFAULT NULL COMMENT '类型，1:全部,2:本人,3:本部门,4:本部门及下属部门'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色数据权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sys_role_dept`
--

DROP TABLE IF EXISTS `ope_sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sys_role_menu`
--

DROP TABLE IF EXISTS `ope_sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  UNIQUE KEY `role_id` (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sys_role_sales_cidy`
--

DROP TABLE IF EXISTS `ope_sys_role_sales_cidy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_role_sales_cidy` (
  `role_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `city_id` bigint(20) DEFAULT NULL COMMENT '城市ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户销售区域关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sys_staff`
--

DROP TABLE IF EXISTS `ope_sys_staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_staff` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `status` int(2) DEFAULT NULL COMMENT '员工状态 1：正常，2：禁用',
  `dept_id` bigint(11) DEFAULT NULL COMMENT '所属部门id',
  `position_id` bigint(11) DEFAULT NULL COMMENT '所属岗位id',
  `role_id` bigint(11) DEFAULT NULL COMMENT '所属角色id',
  `country_code` varchar(64) DEFAULT NULL COMMENT '国家编码如+86',
  `telephone` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱账号',
  `entry_date` date DEFAULT NULL COMMENT '入职日期',
  `country_name` varchar(32) DEFAULT NULL COMMENT '国籍',
  `address_1` varchar(500) DEFAULT NULL COMMENT '地址1',
  `address_2` varchar(500) DEFAULT NULL COMMENT '地址2',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `certificate_type` int(2) DEFAULT '1' COMMENT '证件类型 1：身份证，2：驾驶证，3：护照',
  `certificat_picture_1` varchar(255) DEFAULT NULL COMMENT '图片1（身份证正面或者驾驶证或者护照）',
  `certificat_picture_2` varchar(255) DEFAULT NULL COMMENT '图片2（身份证反面）',
  `open_account` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '是否开通账号 0：否，1：是',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `employee_picture` varchar(255) DEFAULT NULL COMMENT '员工头像',
  `first_name` varchar(128) DEFAULT NULL COMMENT '员工姓',
  `last_name` varchar(128) DEFAULT NULL COMMENT '员工名',
  `full_name` varchar(256) DEFAULT NULL COMMENT '员工全名',
  `code` varchar(50) DEFAULT NULL COMMENT '员工编码',
  `gender` int(2) DEFAULT '1' COMMENT '性别 1：男，2：女',
  `sys_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `if_safe_code` int(2) DEFAULT NULL COMMENT '是否开启安全码（0：否，1：是）',
  `safe_code` varchar(2500) DEFAULT NULL COMMENT '安全码',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户员工表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sys_user`
--

DROP TABLE IF EXISTS `ope_sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_user` (
  `id` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `dept_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '组织部门ID',
  `org_staff_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '员工主键',
  `app_id` varchar(32) DEFAULT NULL COMMENT '应用ID',
  `system_id` varchar(32) DEFAULT NULL COMMENT '系统Id',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐',
  `status` varchar(32) DEFAULT NULL COMMENT '状态 Normal,Lock,Cancel，Expired',
  `login_name` varchar(64) NOT NULL COMMENT '登录名',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_token` varchar(64) DEFAULT NULL COMMENT '最后登录TOKEN',
  `last_login_ip` varchar(16) DEFAULT NULL COMMENT '最后登录IP地址',
  `activation_time` date DEFAULT NULL COMMENT '账户激活时间',
  `expire_date` date DEFAULT NULL COMMENT '账户到期时间',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_sys_user_profile`
--

DROP TABLE IF EXISTS `ope_sys_user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_user_profile` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `repair_shop_Id` bigint(11) NOT NULL DEFAULT '0' COMMENT '维修店ID',
  `sys_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `picture` varchar(2048) DEFAULT NULL COMMENT '照片',
  `first_name` varchar(32) DEFAULT NULL COMMENT '名',
  `last_name` varchar(32) DEFAULT NULL COMMENT '姓',
  `full_name` varchar(32) DEFAULT NULL COMMENT '全名',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `country_code` varchar(64) DEFAULT NULL COMMENT '国家编码如+86',
  `tel_number` varchar(32) DEFAULT NULL COMMENT '电话号',
  `gender` varchar(32) DEFAULT NULL COMMENT '性别 Male 男 Female 女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `place_birth` varchar(255) DEFAULT NULL COMMENT '出生地',
  `address_country_code` varchar(48) DEFAULT NULL COMMENT '地址国家编码',
  `address` varchar(128) DEFAULT NULL COMMENT '地址',
  `address_bureau` varchar(32) DEFAULT NULL COMMENT '办公地点Id(具体数据可查看 AddressBureauEnums )',
  `certificate_type` varchar(32) DEFAULT NULL COMMENT '证件类型，1身份证，2驾驶证，3护照',
  `certificate_negative_annex` varchar(64) DEFAULT NULL COMMENT '证件正面',
  `certificate_positive_annex` varchar(64) DEFAULT NULL COMMENT '证件背面',
  `join_date` datetime DEFAULT NULL COMMENT '入职加入时间',
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

--
-- Table structure for table `ope_sys_user_role`
--

DROP TABLE IF EXISTS `ope_sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ope_whse`
--

DROP TABLE IF EXISTS `ope_whse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ope_whse` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dr` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除字段',
  `name` varchar(48) DEFAULT NULL COMMENT '分类名称',
  `code` varchar(48) DEFAULT NULL COMMENT '分类编码',
  `product_range` varchar(48) DEFAULT NULL COMMENT '产品范围',
  `type` varchar(64) DEFAULT NULL COMMENT '仓库类型，1采购，2生产，3销售',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  `country_id` varchar(24) DEFAULT NULL COMMENT '国家',
  `city_id` varchar(24) DEFAULT NULL COMMENT '城市id',
  `area_id` int(11) DEFAULT NULL COMMENT '区域id',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库表';
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-20  7:36:41
