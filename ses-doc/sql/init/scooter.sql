-- MySQL dump 10.14  Distrib 5.5.65-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: scooter
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
-- Table structure for table `sco_scooter`
--

DROP TABLE IF EXISTS `sco_scooter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_scooter` (
  `id` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_no` varchar(32) NOT NULL COMMENT '车辆编号',
  `picture` varchar(64) DEFAULT NULL COMMENT '车辆图片',
  `status` varchar(32) NOT NULL COMMENT '状态:LOCKED;UNLOCKED',
  `total_mileage` bigint(11) NOT NULL DEFAULT '0' COMMENT '总里程',
  `available_status` varchar(32) DEFAULT 'AVAILABLE' COMMENT 'AVAILABLE;CHARGING;REPAIR;FAULT;USING',
  `box_status` varchar(16) NOT NULL DEFAULT 'LOCKED' COMMENT '后备箱状态',
  `model` varchar(32) NOT NULL COMMENT '型号',
  `license_plate` varchar(32) DEFAULT NULL COMMENT '牌照',
  `license_plate_picture` varchar(255) DEFAULT NULL COMMENT '牌照图片',
  `license_plate_time` datetime DEFAULT NULL COMMENT '上牌时间',
  `scooter_id_picture` varchar(32) DEFAULT NULL COMMENT 'SCOOTER_ID图片',
  `insure_time` datetime DEFAULT NULL COMMENT '投保时间',
  `insurance` varchar(255) DEFAULT NULL COMMENT '保险',
  `revision` int(11) DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint(32) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(32) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摩托车';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sco_scooter_action_trace`
--

DROP TABLE IF EXISTS `sco_scooter_action_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_scooter_action_trace` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(11) DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(11) DEFAULT NULL COMMENT '司机ID',
  `scooter_id` bigint(11) DEFAULT NULL COMMENT '车辆ID',
  `user_longitule` decimal(32,10) DEFAULT NULL COMMENT '用户经度',
  `user_latitude` decimal(32,10) DEFAULT NULL COMMENT '用户纬度',
  `user_location_geohash` varchar(32) DEFAULT NULL COMMENT '用户GeoHash',
  `action_type` varchar(32) DEFAULT NULL COMMENT '操作类型 1 开锁 2 关锁 3 开导航 4 结束导航',
  `action_result` varchar(32) DEFAULT NULL COMMENT '操作结果 1 成功 0 失败',
  `action_time` datetime DEFAULT NULL COMMENT '操作时间',
  `battery` int(11) DEFAULT NULL COMMENT '电池电量',
  `longitule` decimal(32,10) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32,10) DEFAULT NULL COMMENT '纬度',
  `geohash` varchar(32) DEFAULT NULL COMMENT 'GeoHash',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆操作记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sco_scooter_charge`
--

DROP TABLE IF EXISTS `sco_scooter_charge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_scooter_charge` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_id` bigint(20) DEFAULT NULL COMMENT '电动车ID',
  `begin_time` datetime DEFAULT NULL COMMENT '充电开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '充电结束时间',
  `charged_duration` bigint(20) DEFAULT NULL COMMENT '充电时长',
  `begin_battery` int(32) DEFAULT NULL COMMENT '开始充电电量',
  `end_battery` int(32) DEFAULT NULL COMMENT '结束充电电量',
  `charged_battery` decimal(32,10) DEFAULT NULL COMMENT '已充电量',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆充电表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sco_scooter_ecu`
--

DROP TABLE IF EXISTS `sco_scooter_ecu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_scooter_ecu` (
  `id` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `ecu_no` varchar(32) NOT NULL COMMENT '设备号',
  `scooter_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '摩托车ID',
  `iccid` varchar(32) DEFAULT NULL COMMENT 'SIM卡ICCID',
  `sim_no` varchar(32) DEFAULT NULL COMMENT 'SIM卡卡号',
  `revision` int(11) DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint(32) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(32) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='智能控制系统';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sco_scooter_mcu`
--

DROP TABLE IF EXISTS `sco_scooter_mcu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_scooter_mcu` (
  `id` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `mcu_no` varchar(32) NOT NULL COMMENT '控制器序列号',
  `scooter_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '摩托车ID',
  `version_no` varchar(32) DEFAULT NULL COMMENT '版本号',
  `model` varchar(32) DEFAULT NULL COMMENT '型号',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `revision` int(11) DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint(32) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(32) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='控制器 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sco_scooter_navigation`
--

DROP TABLE IF EXISTS `sco_scooter_navigation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_scooter_navigation` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_id` bigint(11) NOT NULL COMMENT '车辆ID',
  `scooter_no` varchar(32) NOT NULL COMMENT '车辆编号',
  `destination` varchar(128) NOT NULL COMMENT '目的地',
  `destination_longitude` decimal(32,10) NOT NULL COMMENT '目的地经度',
  `destination_latitude` decimal(32,10) NOT NULL COMMENT '目的地纬度',
  `status` varchar(32) NOT NULL DEFAULT 'NORMAL' COMMENT '状态 NORMAL;PROCESSING;COMPLETED;',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='导航表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sco_scooter_obd`
--

DROP TABLE IF EXISTS `sco_scooter_obd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_scooter_obd` (
  `id` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_id` bigint(11) NOT NULL COMMENT '摩托车ID',
  `scooter_no` varchar(32) NOT NULL COMMENT '摩托车编号',
  `status` varchar(32) NOT NULL DEFAULT 'NORMAL' COMMENT '状态 NORMAL;PROCESSING;COMPLETED',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摩托车OBD检测';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sco_scooter_obd_detail`
--

DROP TABLE IF EXISTS `sco_scooter_obd_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_scooter_obd_detail` (
  `id` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_obd_id` bigint(11) NOT NULL COMMENT 'obd ID',
  `check_items` int(11) NOT NULL COMMENT '检查项',
  `check_result` varchar(32) NOT NULL COMMENT '检查结果',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摩托车OBD详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sco_scooter_repair`
--

DROP TABLE IF EXISTS `sco_scooter_repair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_scooter_repair` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_id` bigint(20) DEFAULT NULL COMMENT '车辆ID',
  `status` varchar(16) DEFAULT NULL COMMENT '状态 PLANNED,REPAIRING,WAIT_PAYMENT,FINISH',
  `booking_time` datetime DEFAULT NULL COMMENT '预约时间',
  `repair_start_time` datetime DEFAULT NULL COMMENT '维修开始时间',
  `repair_finish_time` datetime DEFAULT NULL COMMENT '维修完成时间',
  `repair_duration` int(11) DEFAULT NULL COMMENT '维修时长',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆维修表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sco_scooter_repair_detail`
--

DROP TABLE IF EXISTS `sco_scooter_repair_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_scooter_repair_detail` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `repair_id` bigint(20) DEFAULT NULL COMMENT '维修ID',
  `type` varchar(32) DEFAULT NULL COMMENT '维修类型',
  `invoice` varchar(255) DEFAULT NULL COMMENT '发票',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维修详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sco_scooter_spairpart`
--

DROP TABLE IF EXISTS `sco_scooter_spairpart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_scooter_spairpart` (
  `scooter_id` bigint(11) DEFAULT NULL COMMENT '车辆ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `sparepart_id` bigint(11) DEFAULT NULL COMMENT '配件ID',
  `type` varchar(32) DEFAULT NULL COMMENT '配件类型',
  `name` varchar(32) DEFAULT NULL COMMENT '配件名称',
  `status` varchar(32) DEFAULT NULL COMMENT '配件状态',
  `revision` int(11) DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint(32) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(32) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摩托车配件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sco_scooter_status`
--

DROP TABLE IF EXISTS `sco_scooter_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_scooter_status` (
  `id` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `scooter_ecu_id` bigint(11) DEFAULT NULL COMMENT '智能控制系统ID',
  `scooter_id` bigint(11) NOT NULL COMMENT 'ScooterID',
  `lock_status` varchar(32) DEFAULT NULL COMMENT '车锁状态',
  `top_box_status` varchar(32) DEFAULT NULL COMMENT '后备箱状态',
  `longitule` decimal(32,10) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32,10) DEFAULT NULL COMMENT '纬度',
  `geohash` varchar(32) DEFAULT NULL COMMENT 'GEOHash',
  `battery` int(11) DEFAULT NULL COMMENT '电池电量',
  `cumulative_mileage` varchar(32) DEFAULT NULL COMMENT '累积行驶里程',
  `revision` int(11) DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint(32) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(32) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电动车实时信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sco_scooter_storage_box`
--

DROP TABLE IF EXISTS `sco_scooter_storage_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_scooter_storage_box` (
  `id` bigint(11) DEFAULT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `revision` int(11) DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint(32) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(32) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摩托车后备箱';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sco_sparepart`
--

DROP TABLE IF EXISTS `sco_sparepart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sco_sparepart` (
  `id` bigint(11) NOT NULL COMMENT 'ID',
  `dr` int(20) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `type` varchar(32) DEFAULT NULL COMMENT '配件类型',
  `name` varchar(32) DEFAULT NULL COMMENT '配件名称',
  `status` varchar(32) DEFAULT NULL COMMENT '配件状态',
  `entry_warehouse_time` datetime DEFAULT NULL COMMENT '入库时间',
  `usage_time` varchar(32) DEFAULT NULL COMMENT '使用时间',
  `use_reason` varchar(32) DEFAULT NULL COMMENT '使用原因',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配件表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-20  7:37:03
