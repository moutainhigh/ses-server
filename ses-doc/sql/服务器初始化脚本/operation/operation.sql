/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : operation

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 14/06/2020 18:35:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ope_allocate
-- ----------------------------
DROP TABLE IF EXISTS `ope_allocate`;
CREATE TABLE `ope_allocate`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标识',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userid',
  `allocate_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调拨编号',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `count` int(0) NULL DEFAULT NULL COMMENT '数量',
  `consignee_id` bigint(0) NULL DEFAULT NULL COMMENT '收获人Id',
  `preparation_wait_total` int(0) NULL DEFAULT NULL COMMENT '待备料总数',
  `pending_storage_total` int(0) NULL DEFAULT NULL COMMENT '待入库数量',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调拨单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_allocate
-- ----------------------------

-- ----------------------------
-- Table structure for ope_allocate_b
-- ----------------------------
DROP TABLE IF EXISTS `ope_allocate_b`;
CREATE TABLE `ope_allocate_b`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标识',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userId',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `allocate_id` bigint(0) NULL DEFAULT NULL COMMENT '调拨单Id',
  `part_id` bigint(0) NULL DEFAULT NULL COMMENT '部件Id',
  `materiel_product_id` bigint(0) NULL DEFAULT NULL COMMENT '产品物料id',
  `materiel_product_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品物料类型',
  `total` int(0) NULL DEFAULT NULL COMMENT '数量',
  `preparation_wait_qty` int(0) NULL DEFAULT NULL COMMENT '待备料总数',
  `pending_storage_qty` int(0) NULL DEFAULT NULL COMMENT '待入库数量',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调拨单子表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_allocate_b
-- ----------------------------

-- ----------------------------
-- Table structure for ope_allocate_b_trace
-- ----------------------------
DROP TABLE IF EXISTS `ope_allocate_b_trace`;
CREATE TABLE `ope_allocate_b_trace`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标识',
  `allocate_id` bigint(0) NULL DEFAULT NULL COMMENT '调拨单Id',
  `allocate_b_id` bigint(0) NULL DEFAULT NULL COMMENT '调拨单子表Id',
  `part_id` bigint(0) NULL DEFAULT NULL COMMENT '部件Id',
  `batch_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `qty` int(0) NULL DEFAULT NULL COMMENT '备料数量',
  `serial_num` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '序列号',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调拨备料记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_allocate_b_trace
-- ----------------------------

-- ----------------------------
-- Table structure for ope_allocate_trace
-- ----------------------------
DROP TABLE IF EXISTS `ope_allocate_trace`;
CREATE TABLE `ope_allocate_trace`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userId',
  `allocate_id` bigint(0) NOT NULL COMMENT '采购订单id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采购单状态',
  `event` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件',
  `event_time` datetime(0) NULL DEFAULT NULL COMMENT '事件时间',
  `memo` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注 放话术参数',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '调拨单节点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_allocate_trace
-- ----------------------------

-- ----------------------------
-- Table structure for ope_assembiy_order_trace
-- ----------------------------
DROP TABLE IF EXISTS `ope_assembiy_order_trace`;
CREATE TABLE `ope_assembiy_order_trace`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `ope_assembiy_order_id` bigint(0) NULL DEFAULT NULL COMMENT '组装单主键',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `event` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件',
  `event_time` datetime(0) NULL DEFAULT NULL COMMENT '事件时间',
  `memo` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注说明',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组装单节点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_assembiy_order_trace
-- ----------------------------

-- ----------------------------
-- Table structure for ope_assembly_b_order
-- ----------------------------
DROP TABLE IF EXISTS `ope_assembly_b_order`;
CREATE TABLE `ope_assembly_b_order`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标识',
  `status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户id',
  `assembly_id` bigint(0) NULL DEFAULT NULL COMMENT '组装单id',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '产品id',
  `assemblyb_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子表编号',
  `product_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品编码',
  `en_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品英文名',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '产品单价',
  `wait_assembly_qty` int(0) NULL DEFAULT NULL COMMENT '待组装数量',
  `in_wait_wh_qty` int(0) NULL DEFAULT NULL COMMENT '待入库数',
  `lave_wait_qc_qty` int(0) NULL DEFAULT NULL COMMENT '待质检数',
  `assembly_qty` int(0) NULL DEFAULT NULL COMMENT '组装总数量',
  `complete_qty` int(0) NULL DEFAULT NULL COMMENT '完成组装数',
  `revision` int(0) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建表',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组装单产品明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_assembly_b_order
-- ----------------------------

-- ----------------------------
-- Table structure for ope_assembly_b_qc
-- ----------------------------
DROP TABLE IF EXISTS `ope_assembly_b_qc`;
CREATE TABLE `ope_assembly_b_qc`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标志',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userId',
  `assembly_b_id` bigint(0) NULL DEFAULT NULL COMMENT '组装子表Id',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '产品Id',
  `quality_inspector_id` bigint(0) NULL DEFAULT NULL COMMENT '质检人Id',
  `batch_no` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '质检批次号',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '质检状态',
  `total_quality_inspected` int(0) NULL DEFAULT NULL COMMENT '需要质检总数',
  `pass_count` int(0) NULL DEFAULT NULL COMMENT '质检通过数量',
  `fail_count` int(0) NULL DEFAULT NULL COMMENT '质检失败数量',
  `quality_inspection_time` datetime(0) NULL DEFAULT NULL COMMENT '质检时间',
  `revision` int(0) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购条目QC质检' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_assembly_b_qc
-- ----------------------------

-- ----------------------------
-- Table structure for ope_assembly_lot_trace
-- ----------------------------
DROP TABLE IF EXISTS `ope_assembly_lot_trace`;
CREATE TABLE `ope_assembly_lot_trace`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标志',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userId',
  `assembly_id` bigint(0) NULL DEFAULT NULL COMMENT '组装表Id',
  `quality_inspector_id` bigint(0) NULL DEFAULT NULL COMMENT '质检人Id',
  `batch_no` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '质检批次号',
  `total_quality_inspected` int(0) NULL DEFAULT NULL COMMENT '需要质检总数',
  `pass_count` int(0) NULL DEFAULT NULL COMMENT '质检通过数量',
  `fail_count` int(0) NULL DEFAULT NULL COMMENT '质检失败数量',
  `revision` int(0) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购条目QC质检' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_assembly_lot_trace
-- ----------------------------

-- ----------------------------
-- Table structure for ope_assembly_order
-- ----------------------------
DROP TABLE IF EXISTS `ope_assembly_order`;
CREATE TABLE `ope_assembly_order`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '逻辑删除标识',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `tenant_id` bigint(0) NULL DEFAULT 0 COMMENT '租户id',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `assembly_number` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组装单订单号',
  `total_qty` int(0) NULL DEFAULT NULL COMMENT '产品数量之和',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '总金额',
  `processing_fee_ratio` decimal(10, 2) NULL DEFAULT NULL COMMENT '加工费比例',
  `processing_fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '加工费',
  `payment_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款类型',
  `product_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '产品生产价格',
  `factory_id` bigint(0) NULL DEFAULT NULL COMMENT '代工厂id',
  `factory_annex` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工厂附件',
  `consignee_id` bigint(0) NULL DEFAULT NULL COMMENT '收货人姓氏',
  `wait_assembly_total` int(0) NULL DEFAULT NULL COMMENT '待组装总数量',
  `in_wait_wh_total` int(0) NULL DEFAULT NULL COMMENT '待入库总数',
  `lave_wait_qc_total` int(0) NULL DEFAULT NULL COMMENT '待质检总数',
  `wait_preparation_total` int(0) NULL DEFAULT NULL COMMENT '待备料数量',
  `revision` int(0) NOT NULL DEFAULT 0 COMMENT '乐观锁',
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组装单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_assembly_order
-- ----------------------------

-- ----------------------------
-- Table structure for ope_assembly_order_part
-- ----------------------------
DROP TABLE IF EXISTS `ope_assembly_order_part`;
CREATE TABLE `ope_assembly_order_part`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '逻辑删除标识',
  `stock_id` bigint(0) NULL DEFAULT NULL COMMENT '库存Id',
  `part_id` bigint(0) NULL DEFAULT NULL COMMENT '部件Id',
  `assembly_id` bigint(0) NULL DEFAULT NULL COMMENT '组装单Id',
  `total_qty` int(0) NULL DEFAULT NULL COMMENT '消耗部件数量',
  `wait_preparation_qty` int(0) NULL DEFAULT NULL COMMENT '待备料数量',
  `revision` int(0) NOT NULL DEFAULT 0 COMMENT '乐观锁',
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_assembly_order_part
-- ----------------------------

-- ----------------------------
-- Table structure for ope_assembly_order_payment
-- ----------------------------
DROP TABLE IF EXISTS `ope_assembly_order_payment`;
CREATE TABLE `ope_assembly_order_payment`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userId',
  `ope_assembly_order_id` bigint(0) NULL DEFAULT NULL COMMENT '组装单主键',
  `payment_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分期，月结',
  `planned_payment_time` datetime(0) NOT NULL COMMENT '预计付款时间',
  `payment_day` int(0) NULL DEFAULT NULL COMMENT '付款周期',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '实际付款时间',
  `payment_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付状态:Paid Unpaid',
  `payment_priority` int(0) NOT NULL DEFAULT 0 COMMENT '支付优先级',
  `description` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `amount` decimal(10, 2) NOT NULL COMMENT '价格',
  `amount_proportion` int(0) NOT NULL COMMENT '55% 标示为 55',
  `invoice_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票单号',
  `invoice_picture` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票附件',
  `revision` int(0) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组装单付款表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_assembly_order_payment
-- ----------------------------

-- ----------------------------
-- Table structure for ope_assembly_preparation
-- ----------------------------
DROP TABLE IF EXISTS `ope_assembly_preparation`;
CREATE TABLE `ope_assembly_preparation`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '逻辑删除标识',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `tenant_id` bigint(0) NULL DEFAULT 0 COMMENT '租户id',
  `assembly_id` bigint(0) NULL DEFAULT NULL COMMENT '组装单Id',
  `assembly_order_part_id` bigint(0) NULL DEFAULT NULL COMMENT '组装部品表Id',
  `part_id` bigint(0) NULL DEFAULT NULL COMMENT '部件Id',
  `serial_num` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '序列号',
  `batch_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `qty` int(0) NULL DEFAULT NULL COMMENT '备料数量',
  `revision` int(0) NOT NULL DEFAULT 0 COMMENT '乐观锁',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组装备料记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_assembly_preparation
-- ----------------------------

-- ----------------------------
-- Table structure for ope_assembly_qc_item
-- ----------------------------
DROP TABLE IF EXISTS `ope_assembly_qc_item`;
CREATE TABLE `ope_assembly_qc_item`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标识',
  `assembly_id` bigint(0) NULL DEFAULT NULL COMMENT '组装单id',
  `assembly_b_id` bigint(0) NULL DEFAULT NULL COMMENT '组装单子表Id',
  `assembly_b_qc_id` bigint(0) NULL DEFAULT NULL COMMENT '质检结果表',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '产品Id',
  `assembly_lot_id` bigint(0) NULL DEFAULT NULL COMMENT '批次质检记录id',
  `serial_num` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '序列号',
  `batch_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `qc_result` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '质检结果',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建表',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组装单质检条目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_assembly_qc_item
-- ----------------------------

-- ----------------------------
-- Table structure for ope_assembly_qc_trace
-- ----------------------------
DROP TABLE IF EXISTS `ope_assembly_qc_trace`;
CREATE TABLE `ope_assembly_qc_trace`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标识',
  `ope_assembly_b_id` bigint(0) NULL DEFAULT NULL COMMENT '组装单子表id',
  `product_qc_template_b_id` bigint(0) NULL DEFAULT NULL COMMENT '质检项结果Id',
  `product_qc_template_b_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '质检结果',
  `product_qc_template_id` bigint(0) NULL DEFAULT NULL COMMENT '质检项Id',
  `product_qc_template_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '质检项名称',
  `assembly_qc_item_id` bigint(0) NULL DEFAULT NULL COMMENT '商品质检条目',
  `picture` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '质检图片（多个图片逗号分隔）',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组装单质检记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_assembly_qc_trace
-- ----------------------------

-- ----------------------------
-- Table structure for ope_customer
-- ----------------------------
DROP TABLE IF EXISTS `ope_customer`;
CREATE TABLE `ope_customer`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户id',
  `time_zone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时区',
  `country` bigint(0) NULL DEFAULT NULL COMMENT '国家',
  `country_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码，如手机号 中国 +86',
  `city` bigint(0) NULL DEFAULT NULL COMMENT '城市',
  `distrust` bigint(0) NULL DEFAULT NULL COMMENT '区域',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `sales_id` bigint(0) NULL DEFAULT NULL COMMENT '销售',
  `customer_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编码',
  `customer_first_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名字',
  `customer_last_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名字',
  `customer_full_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名字',
  `company_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '企业名称',
  `picture` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户头像',
  `customer_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户来源渠道 官网/email/电话',
  `customer_type` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户类型 1企业/2个人',
  `industry_type` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户行业类型，1餐厅/2快递',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `place_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地点编号',
  `longitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '纬度',
  `contact_first_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名字',
  `contact_last_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名字',
  `contact_full_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人全名',
  `telephone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮件',
  `memo` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `scooter_quantity` int(0) NULL DEFAULT NULL COMMENT '车辆数量',
  `assignation_scooter_qty` int(0) NULL DEFAULT NULL COMMENT '已分配车辆数',
  `certificate_type` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型1身份证，2驾驶证，3护照',
  `certificate_positive_annex` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件正面图片',
  `certificate_negative_annex` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件反面图片',
  `business_license_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '营业执照编号',
  `business_license_annex` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '营业执照图片',
  `invoice_num` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票编号',
  `invoice_annex` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票附件',
  `contract_annex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同附件',
  `account_flag` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号使用标识，即激活使用过1，未激活未使用0',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_customer
-- ----------------------------

-- ----------------------------
-- Table structure for ope_customer_accessories
-- ----------------------------
DROP TABLE IF EXISTS `ope_customer_accessories`;
CREATE TABLE `ope_customer_accessories`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `product_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '适用于产品的产品Id',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品配件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_customer_accessories
-- ----------------------------
INSERT INTO `ope_customer_accessories` VALUES (1000000, 0, 99.00, '1', 'Battery', 0, 0, '2020-05-13 11:46:00', 0, '2020-05-13 06:41:33', NULL, NULL, NULL, NULL, 0);
INSERT INTO `ope_customer_accessories` VALUES (1000001, 0, 300.00, '2', 'TopCase', 0, 0, '2020-05-13 11:46:00', 0, '2020-05-13 06:41:33', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for ope_customer_contact
-- ----------------------------
DROP TABLE IF EXISTS `ope_customer_contact`;
CREATE TABLE `ope_customer_contact`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `country_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码如+86',
  `customer_id` bigint(0) NULL DEFAULT NULL COMMENT '客户id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `first_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `last_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `telephone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `position` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户联系人表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_customer_contact
-- ----------------------------

-- ----------------------------
-- Table structure for ope_customer_inquiry
-- ----------------------------
DROP TABLE IF EXISTS `ope_customer_inquiry`;
CREATE TABLE `ope_customer_inquiry`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `order_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `customer_id` bigint(0) NULL DEFAULT NULL COMMENT '客户id',
  `country` bigint(0) NULL DEFAULT NULL COMMENT '国家',
  `city` bigint(0) NULL DEFAULT NULL COMMENT '城市',
  `district` bigint(0) NULL DEFAULT NULL COMMENT '区域',
  `customer_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户来源渠道 官网/email/电话',
  `sales_id` bigint(0) NULL DEFAULT NULL COMMENT '销售员id',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态 已处理/未处理（默认）',
  `industry` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行业 行业',
  `customer_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户类型 企业/个人',
  `first_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名字',
  `last_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名字',
  `full_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名字',
  `company_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `country_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码如+86',
  `telephone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户邮箱',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户地址',
  `contact_first` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_last` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contant_full_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '产品Id ',
  `product_model` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品型号',
  `product_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '产品单价',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单据总价',
  `scooter_quantity` int(0) NULL DEFAULT NULL COMMENT '需求车辆数',
  `pay_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付状态',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `source` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源1 询价单 2预订单',
  `bank_card_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡上面名字',
  `cvv` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cvv',
  `expired_time` date NULL DEFAULT NULL COMMENT '过期时间',
  `card_Num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卡号',
  `postal_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安全码',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户咨询管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_customer_inquiry
-- ----------------------------

-- ----------------------------
-- Table structure for ope_customer_inquiry_b
-- ----------------------------
DROP TABLE IF EXISTS `ope_customer_inquiry_b`;
CREATE TABLE `ope_customer_inquiry_b`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `inquiry_id` bigint(0) NULL DEFAULT NULL COMMENT '询价单Id',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '产品Id',
  `product_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '产品价格',
  `product_qty` int(0) NULL DEFAULT NULL COMMENT '产品数量',
  `product_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品类型',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_customer_inquiry_b
-- ----------------------------

-- ----------------------------
-- Table structure for ope_excle_import
-- ----------------------------
DROP TABLE IF EXISTS `ope_excle_import`;
CREATE TABLE `ope_excle_import`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT NULL COMMENT '逻辑删除标识 0正常 1删除',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '生产批次号',
  `data_json` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据json保存记录',
  `count` int(0) NOT NULL DEFAULT 0 COMMENT '导入成功数量',
  `attachment` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '附件',
  `service_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务类型',
  `message` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导入说明',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(10, 2) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表格导入记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_excle_import
-- ----------------------------
INSERT INTO `ope_excle_import` VALUES (1000000, 0, '1', 'J420061411097', '[{\"cnName\":\"电池\",\"enName\":\"Battery\",\"esc\":\"F05\",\"frName\":\"La batterie\",\"partsN\":\"RERE2WA-D000001-1\",\"rowNum\":1,\"snClass\":\"SSC\",\"type\":\"Battery\"}]', 1, 'https://rede.oss-cn-shanghai.aliyuncs.com/1592128701228.xlsx', 'BOM导入', 'BOM物料部件导入', 1000003, '2020-06-14 09:58:19', 1000003, '2020-06-14 09:58:19', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_factory
-- ----------------------------
DROP TABLE IF EXISTS `ope_factory`;
CREATE TABLE `ope_factory`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识',
  `status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `factory_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '代工厂名称',
  `factory_address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '代工厂地址',
  `factory_country` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '代工厂国家',
  `factory_longitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '代工厂经度',
  `factory_latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '代工厂纬度',
  `place_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址唯一ID',
  `geo_hash` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'geo_hash',
  `factory_tag` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '代工厂标签',
  `factory_memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代工厂备注',
  `contact_first_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人名字',
  `contact_last_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人姓氏',
  `contact_full_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人全名',
  `contact_email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人邮箱',
  `contact_phone_country_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号归属国家',
  `contact_phone` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人手机号',
  `payment_cycle` int(0) NOT NULL COMMENT '付款周期',
  `cooperation_time_start` datetime(0) NULL DEFAULT NULL COMMENT '合作开始时间',
  `cooperation_time_end` datetime(0) NULL DEFAULT NULL COMMENT '合作结束时间',
  `business_number` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '营业执照编号',
  `business_license_annex` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '营业执照附件',
  `contract_number` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '合同编号',
  `contract_annex` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '合同附件',
  `overdue_flag` int(0) NOT NULL DEFAULT 0 COMMENT '是否过期：默认0不过期，-1过期',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `def1` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代工厂' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_factory
-- ----------------------------
INSERT INTO `ope_factory` VALUES (1000000, 0, '1', 0, 1000003, 'FEL', 'Shenzhen, China', 'China', NULL, NULL, NULL, NULL, 'Foundries', NULL, NULL, NULL, 'Alex', 'alex@redescooter.com', '86', '1234567890', 30, '2020-06-14 00:00:00', NULL, '1234567890', 'https://rede.oss-cn-shanghai.aliyuncs.com/1592129155806.pdf', NULL, 'https://rede.oss-cn-shanghai.aliyuncs.com/1592129157237.pdf', 0, '2020-06-14 10:05:55', 1000003, '2020-06-14 10:05:55', 1000003, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_factory_trace
-- ----------------------------
DROP TABLE IF EXISTS `ope_factory_trace`;
CREATE TABLE `ope_factory_trace`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `factory_id` bigint(0) NOT NULL COMMENT '代工厂ID',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代工厂操作状态',
  `event` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代工厂操作事件',
  `event_time` datetime(0) NULL DEFAULT NULL COMMENT '事件时间',
  `reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注说明',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代工厂操作根据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_factory_trace
-- ----------------------------
INSERT INTO `ope_factory_trace` VALUES (1000000, 0, 1000000, 0, 1000003, '1', '1', '2020-06-14 10:05:55', NULL, 1000003, '2020-06-14 10:05:55', 1000003, '2020-06-14 10:05:55', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for ope_part_draft_qc_template
-- ----------------------------
DROP TABLE IF EXISTS `ope_part_draft_qc_template`;
CREATE TABLE `ope_part_draft_qc_template`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除',
  `part_draft_id` bigint(0) NULL DEFAULT NULL COMMENT '部件Id',
  `import_excel_batch_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导入批次号',
  `source_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源方式',
  `qc_item_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '质检项名称',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 6) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部件质检模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_part_draft_qc_template
-- ----------------------------
INSERT INTO `ope_part_draft_qc_template` VALUES (1000000, 0, 1000000, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:12:46', 1000003, '2020-06-14 10:12:46', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_part_draft_qc_template_b
-- ----------------------------
DROP TABLE IF EXISTS `ope_part_draft_qc_template_b`;
CREATE TABLE `ope_part_draft_qc_template_b`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除',
  `part_draft_qc_template_id` bigint(0) NULL DEFAULT NULL COMMENT '质检项Id',
  `qc_result` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '质检结果',
  `pass_flag` tinyint(1) NULL DEFAULT NULL COMMENT '通过标记',
  `upload_flag` tinyint(1) NULL DEFAULT NULL COMMENT '是否允许上传图片',
  `results_sequence` int(0) NULL DEFAULT NULL COMMENT '结果集排序优先级',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 6) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部件质检模板结果项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_part_draft_qc_template_b
-- ----------------------------
INSERT INTO `ope_part_draft_qc_template_b` VALUES (1000000, 0, 1000000, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:12:46', 1000003, '2020-06-14 10:12:46', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_part_draft_qc_template_b` VALUES (1000001, 0, 1000000, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:12:46', 1000003, '2020-06-14 10:12:46', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_part_qc_template
-- ----------------------------
DROP TABLE IF EXISTS `ope_part_qc_template`;
CREATE TABLE `ope_part_qc_template`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除',
  `part_id` bigint(0) NULL DEFAULT NULL COMMENT '部件Id',
  `import_excel_batch_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导入批次号',
  `source_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源方式',
  `qc_item_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '质检项名称',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 6) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部件质检模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_part_qc_template
-- ----------------------------
INSERT INTO `ope_part_qc_template` VALUES (1000000, 0, 1000000, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:12:46', 1000003, '2020-06-14 10:12:46', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_part_qc_template_b
-- ----------------------------
DROP TABLE IF EXISTS `ope_part_qc_template_b`;
CREATE TABLE `ope_part_qc_template_b`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除',
  `part_qc_template_id` bigint(0) NULL DEFAULT NULL COMMENT '质检项Id',
  `qc_result` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '质检结果',
  `pass_flag` tinyint(0) NULL DEFAULT NULL COMMENT '通过标记',
  `upload_flag` tinyint(1) NULL DEFAULT NULL COMMENT '是否允许上传图片',
  `results_sequence` int(0) NULL DEFAULT NULL COMMENT '结果集排序优先级',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 6) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部件质检模板结果项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_part_qc_template_b
-- ----------------------------
INSERT INTO `ope_part_qc_template_b` VALUES (1000000, 0, 1000000, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:12:46', 1000003, '2020-06-14 10:12:46', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_part_qc_template_b` VALUES (1000001, 0, 1000000, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:12:46', 1000003, '2020-06-14 10:12:46', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_parts
-- ----------------------------
DROP TABLE IF EXISTS `ope_parts`;
CREATE TABLE `ope_parts`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `import_lot` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导入批次号',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `parts_draft_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '部件草稿主键',
  `parts_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery',
  `sec` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。',
  `parts_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部品号',
  `cn_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '英文名称',
  `sn_class` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否可销售 是否可销售,0:SC仅可采购，1:SSC可销售可采购',
  `parts_qty` int(0) NULL DEFAULT NULL COMMENT '部品数量',
  `production_cycle` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产周期',
  `cost` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '成本',
  `supplier_id` bigint(0) NULL DEFAULT NULL COMMENT '供应商',
  `dwg` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图纸',
  `note` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `id_class` tinyint(1) NULL DEFAULT NULL COMMENT '是否有唯一编码',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否同步',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 6) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '物料表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_parts
-- ----------------------------
INSERT INTO `ope_parts` VALUES (1000000, 0, 0, 1000003, 'J420061411097', '1', 1000000, 'Battery', 'F05', 'RERE2WA-D000001-1', '电池', 'La batterie', 'Battery', '2', NULL, '30', NULL, 1000000, 'https://rede.oss-cn-shanghai.aliyuncs.com/1592129541824.png', NULL, 0, 1000003, '2020-06-14 09:58:19', 1000003, '2020-06-14 10:12:46', 0, 1, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_parts_draft
-- ----------------------------
DROP TABLE IF EXISTS `ope_parts_draft`;
CREATE TABLE `ope_parts_draft`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `import_lot` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导入批次号',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `parts_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery',
  `sec` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。',
  `parts_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部品号',
  `cn_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '英文名称',
  `sn_class` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否可销售 是否可销售,0:SC仅可采购，1:SSC可销售可采购',
  `parts_qty` int(0) NULL DEFAULT NULL COMMENT '部品数量',
  `production_cycle` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产周期',
  `cost` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '成本',
  `supplier_id` bigint(0) NULL DEFAULT NULL COMMENT '供应商',
  `dwg` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图纸',
  `note` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `id_class` tinyint(1) NULL DEFAULT NULL COMMENT '是否有唯一编码',
  `perfect_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否信息完善',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否同步，只有在信息完善的前提下可以进行同步操作',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 6) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '物料草稿表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_parts_draft
-- ----------------------------
INSERT INTO `ope_parts_draft` VALUES (1000000, 0, 0, 1000003, 'J420061411097', '1', 'Battery', 'F05', 'RERE2WA-D000001-1', '电池', 'La batterie', 'Battery', '2', NULL, '30', NULL, 1000000, 'https://rede.oss-cn-shanghai.aliyuncs.com/1592129541824.png', NULL, 0, 1000003, '2020-06-14 09:58:19', 1000003, '2020-06-14 10:12:46', 0, 1, 1, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_parts_draft_history_record
-- ----------------------------
DROP TABLE IF EXISTS `ope_parts_draft_history_record`;
CREATE TABLE `ope_parts_draft_history_record`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `tenant_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `user_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '用户ID',
  `parts_draft_id` bigint(0) NOT NULL COMMENT '产品部品表主键',
  `event` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作事件',
  `import_lot` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导入批次号',
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `parts_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery',
  `sec` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。',
  `parts_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部品号',
  `cn_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '英文名称',
  `sn_class` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否可销售,0:SC仅可采购，1:SSC可销售可采购',
  `parts_qty` int(0) NULL DEFAULT NULL COMMENT '部品数量',
  `production_cycle` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产周期',
  `cost` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '成本',
  `supplier_id` bigint(0) NULL DEFAULT NULL COMMENT '供应商',
  `dwg` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图纸',
  `note` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 2) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'BOM产品部件草稿表操作记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_parts_draft_history_record
-- ----------------------------
INSERT INTO `ope_parts_draft_history_record` VALUES (1000000, 0, 0, 1000003, 1000000, '1', 'J420061411097', '1', 'Battery', 'F05', 'RERE2WA-D000001-1', '电池', 'La batterie', 'Battery', '2', 0, NULL, NULL, NULL, NULL, NULL, 0, 1000003, '2020-06-14 09:58:19', 1000003, '2020-06-14 09:58:19', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_draft_history_record` VALUES (1000001, 0, 0, 1000003, 1000000, '3', 'J420061411097', '1', 'Battery', 'F05', 'RERE2WA-D000001-1', '电池', 'La batterie', 'Battery', '2', 0, NULL, NULL, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:12:17', 1000003, '2020-06-14 10:12:17', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_parts_product
-- ----------------------------
DROP TABLE IF EXISTS `ope_parts_product`;
CREATE TABLE `ope_parts_product`  (
  `id` bigint(0) NOT NULL COMMENT '主键 主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态 up上架，down下架(默认)',
  `sn_class` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否可销售,0:SC仅可采购，1:SSC可销售可采购',
  `product_type` int(0) NOT NULL COMMENT '产品类型 如1整车，2组装套件，电池',
  `product_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品编码',
  `product_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品编号',
  `cn_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '英文名称',
  `production_cycle` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产周期',
  `sum_parts_qty` int(0) NOT NULL COMMENT '已选部品数量',
  `model` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '型号',
  `pictures` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片',
  `color` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '颜色',
  `after_sales_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否支持售后服务',
  `added_services_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否支持增值服务',
  `mater_parameter` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品参数 存储JSON',
  `other_parameter` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '其他参数 存储JSON',
  `note` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 6) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部件产品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_parts_product
-- ----------------------------
INSERT INTO `ope_parts_product` VALUES (1000000, 0, 0, 1000003, '2', '2', 3, 'Battery', 'RERE2WA-D000001-1', '电池', 'La batterie', 'Battery', '30', 1, 'RERE2WA-D000001-1', 'https://rede.oss-cn-shanghai.aliyuncs.com/1592129541824.png', NULL, 0, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:12:49', 1000003, '2020-06-14 10:12:49', '1000000', NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000001, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-50CC-01', 'RED-50CC-01', 'RED-50CC-01', 'RED-50CC-01', '30', 1, '1', NULL, '1', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:13:57', 1000003, '2020-06-14 10:13:57', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000002, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-50CC-02', 'RED-50CC-02', 'RED-50CC-02', 'RED-50CC-02', '30', 1, '1', NULL, '2', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:14:06', 1000003, '2020-06-14 10:14:06', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000003, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-50CC-03', 'RED-50CC-03', 'RED-50CC-03', 'RED-50CC-03', '30', 1, '1', NULL, '3', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:14:17', 1000003, '2020-06-14 10:14:17', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000004, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-50CC-04', 'RED-50CC-04', 'RED-50CC-04', 'RED-50CC-04', '30', 1, '1', NULL, '4', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:14:28', 1000003, '2020-06-14 10:14:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000005, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-50CC-05', 'RED-50CC-05', 'RED-50CC-05', 'RED-50CC-05', '30', 1, '1', NULL, '5', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:14:40', 1000003, '2020-06-14 10:14:40', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000006, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-100CC-01', 'RED-100CC-01', 'RED-100CC-01', 'RED-100CC-01', '30', 1, '2', NULL, '1', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:14:55', 1000003, '2020-06-14 10:14:55', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000007, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-100CC-02', 'RED-100CC-02', 'RED-100CC-02', 'RED-100CC-02', '30', 1, '2', NULL, '2', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:15:04', 1000003, '2020-06-14 10:15:04', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000008, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-100CC-03', 'RED-100CC-03', 'RED-100CC-03', 'RED-100CC-03', '30', 1, '2', NULL, '3', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:15:14', 1000003, '2020-06-14 10:15:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000009, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-100CC-04', 'RED-100CC-04', 'RED-100CC-04', 'RED-100CC-04', '30', 1, '2', NULL, '4', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:15:25', 1000003, '2020-06-14 10:15:25', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000010, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-100CC-05', 'RED-100CC-05', 'RED-100CC-05', 'RED-100CC-05', '30', 1, '2', NULL, '5', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:15:36', 1000003, '2020-06-14 10:15:36', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000011, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-125CC-01', 'RED-125CC-01', 'RED-125CC-01', 'RED-125CC-01', '10', 1, '3', NULL, '1', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:15:50', 1000003, '2020-06-14 10:15:50', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000012, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-125CC-02', 'RED-125CC-02', 'RED-125CC-02', 'RED-125CC-02', '20', 1, '3', NULL, '2', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:16:03', 1000003, '2020-06-14 10:16:03', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000013, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-125CC-03', 'RED-125CC-03', 'RED-125CC-03', 'RED-125CC-03', '30', 1, '3', NULL, '3', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:16:23', 1000003, '2020-06-14 10:16:23', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000014, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-125CC-04', 'RED-125CC-04', 'RED-125CC-04', 'RED-125CC-04', '30', 1, '3', NULL, '4', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:16:37', 1000003, '2020-06-14 10:16:37', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product` VALUES (1000015, 0, 0, 1000003, '1', '2', 4, 'Scooter', 'RED-125CC-05', 'RED-125CC-05', 'RED-125CC-05', 'RED-125CC-05', '30', 1, '3', NULL, '5', 1, 0, NULL, NULL, NULL, 0, 1000003, '2020-06-14 10:16:47', 1000003, '2020-06-14 10:16:47', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_parts_product_b
-- ----------------------------
DROP TABLE IF EXISTS `ope_parts_product_b`;
CREATE TABLE `ope_parts_product_b`  (
  `id` bigint(0) NOT NULL COMMENT '主键 主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除 逻辑删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID 租户ID',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID 用户ID',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态 状态',
  `parts_id` bigint(0) NOT NULL COMMENT '部品主键 部品主键',
  `parts_product_id` bigint(0) NOT NULL COMMENT '部品组装表主键 部品组装表主键',
  `parts_qty` int(0) NOT NULL COMMENT '部品数量 数量',
  `note` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注 备注',
  `revision` int(0) NOT NULL COMMENT '乐观锁 乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人 创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间 创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人 更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间 更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def6` decimal(10, 6) NULL DEFAULT NULL COMMENT '冗余字段 冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部件产品表条目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_parts_product_b
-- ----------------------------
INSERT INTO `ope_parts_product_b` VALUES (1000000, 0, 0, 1000003, '1', 1000000, 1000000, 1, NULL, 0, 1000003, '2020-06-14 10:12:49', 1000003, '2020-06-14 10:12:49', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000001, 0, 0, 1000003, '1', 1000000, 1000001, 1, NULL, 0, 1000003, '2020-06-14 10:13:57', 1000003, '2020-06-14 10:13:57', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000002, 0, 0, 1000003, '1', 1000000, 1000002, 1, NULL, 0, 1000003, '2020-06-14 10:14:06', 1000003, '2020-06-14 10:14:06', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000003, 0, 0, 1000003, '1', 1000000, 1000003, 1, NULL, 0, 1000003, '2020-06-14 10:14:17', 1000003, '2020-06-14 10:14:17', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000004, 0, 0, 1000003, '1', 1000000, 1000004, 1, NULL, 0, 1000003, '2020-06-14 10:14:28', 1000003, '2020-06-14 10:14:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000005, 0, 0, 1000003, '1', 1000000, 1000005, 1, NULL, 0, 1000003, '2020-06-14 10:14:40', 1000003, '2020-06-14 10:14:40', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000006, 0, 0, 1000003, '1', 1000000, 1000006, 1, NULL, 0, 1000003, '2020-06-14 10:14:55', 1000003, '2020-06-14 10:14:55', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000007, 0, 0, 1000003, '1', 1000000, 1000007, 1, NULL, 0, 1000003, '2020-06-14 10:15:04', 1000003, '2020-06-14 10:15:04', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000008, 0, 0, 1000003, '1', 1000000, 1000008, 1, NULL, 0, 1000003, '2020-06-14 10:15:14', 1000003, '2020-06-14 10:15:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000009, 0, 0, 1000003, '1', 1000000, 1000009, 1, NULL, 0, 1000003, '2020-06-14 10:15:25', 1000003, '2020-06-14 10:15:25', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000010, 0, 0, 1000003, '1', 1000000, 1000010, 1, NULL, 0, 1000003, '2020-06-14 10:15:36', 1000003, '2020-06-14 10:15:36', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000011, 0, 0, 1000003, '1', 1000000, 1000011, 1, NULL, 0, 1000003, '2020-06-14 10:15:50', 1000003, '2020-06-14 10:15:50', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000012, 0, 0, 1000003, '1', 1000000, 1000012, 1, NULL, 0, 1000003, '2020-06-14 10:16:03', 1000003, '2020-06-14 10:16:03', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000013, 0, 0, 1000003, '1', 1000000, 1000013, 1, NULL, 0, 1000003, '2020-06-14 10:16:23', 1000003, '2020-06-14 10:16:23', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000014, 0, 0, 1000003, '1', 1000000, 1000014, 1, NULL, 0, 1000003, '2020-06-14 10:16:37', 1000003, '2020-06-14 10:16:37', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_product_b` VALUES (1000015, 0, 0, 1000003, '1', 1000000, 1000015, 1, NULL, 0, 1000003, '2020-06-14 10:16:47', 1000003, '2020-06-14 10:16:47', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_parts_sec
-- ----------------------------
DROP TABLE IF EXISTS `ope_parts_sec`;
CREATE TABLE `ope_parts_sec`  (
  `id` bigint(0) NOT NULL COMMENT '主键 主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除 逻辑删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID 租户ID',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID 用户ID',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态 状态',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目区域名称 项目区域名称',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目区域编码 项目区域编码',
  `value` int(0) NULL DEFAULT NULL COMMENT '值',
  `note` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注 备注',
  `revision` int(0) NOT NULL COMMENT '乐观锁 乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人 创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间 创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人 更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间 更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段 冗余字段',
  `def6` decimal(10, 6) NULL DEFAULT NULL COMMENT '冗余字段 冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部品项目区域表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_parts_sec
-- ----------------------------
INSERT INTO `ope_parts_sec` VALUES (1000000, 0, 0, 0, '1', 'F00', 'F00', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000001, 0, 0, 0, '1', 'F01', 'F01', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000002, 0, 0, 0, '1', 'F02', 'F02', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000003, 0, 0, 0, '1', 'F03', 'F03', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000004, 0, 0, 0, '1', 'F04', 'F04', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000005, 0, 0, 0, '1', 'F05', 'F05', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000006, 0, 0, 0, '1', 'F06', 'F06', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000007, 0, 0, 0, '1', 'F07', 'F07', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000008, 0, 0, 0, '1', 'F08', 'F08', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000009, 0, 0, 0, '1', 'F09', 'F09', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000010, 0, 0, 0, '1', 'F10', 'F10', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000011, 0, 0, 0, '1', 'F11', 'F11', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000012, 0, 0, 0, '1', 'F12', 'F12', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000013, 0, 0, 0, '1', 'F13', 'F13', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000014, 0, 0, 0, '1', 'F14', 'F14', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000015, 0, 0, 0, '1', 'F15', 'F15', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000016, 0, 0, 0, '1', 'F16', 'F16', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000017, 0, 0, 0, '1', 'F17', 'F17', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000018, 0, 0, 0, '1', 'F18', 'F18', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000019, 0, 0, 0, '1', 'F19', 'F19', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000020, 0, 0, 0, '1', 'F20', 'F20', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000021, 0, 0, 0, '1', 'F21', 'F21', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000022, 0, 0, 0, '1', 'F22', 'F22', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000023, 0, 0, 0, '1', 'F23', 'F23', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000024, 0, 0, 0, '1', 'F24', 'F24', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000025, 0, 0, 0, '1', 'F25', 'F25', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000026, 0, 0, 0, '1', 'F26', 'F26', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000027, 0, 0, 0, '1', 'F27', 'F27', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000028, 0, 0, 0, '1', 'F28', 'F28', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000029, 0, 0, 0, '1', 'F29', 'F29', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000030, 0, 0, 0, '1', 'F30', 'F30', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000031, 0, 0, 0, '1', 'F31', 'F31', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000032, 0, 0, 0, '1', 'F32', 'F32', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000033, 0, 0, 0, '1', 'F33', 'F33', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000034, 0, 0, 0, '1', 'F34', 'F34', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000035, 0, 0, 0, '1', 'F35', 'F35', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000036, 0, 0, 0, '1', 'F36', 'F36', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000037, 0, 0, 0, '1', 'F37', 'F37', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000038, 0, 0, 0, '1', 'F38', 'F38', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000039, 0, 0, 0, '1', 'F39', 'F39', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000040, 0, 0, 0, '1', 'F40', 'F40', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000041, 0, 0, 0, '1', 'F41', 'F41', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000042, 0, 0, 0, '1', 'F42', 'F42', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000043, 0, 0, 0, '1', 'F43', 'F43', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000044, 0, 0, 0, '1', 'F44', 'F44', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000045, 0, 0, 0, '1', 'F45', 'F45', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000046, 0, 0, 0, '1', 'F46', 'F46', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000047, 0, 0, 0, '1', 'F47', 'F47', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000048, 0, 0, 0, '1', 'F48', 'F48', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000049, 0, 0, 0, '1', 'F49', 'F49', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000050, 0, 0, 0, '1', 'F50', 'F50', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000051, 0, 0, 0, '1', 'F51', 'F51', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000052, 0, 0, 0, '1', 'F52', 'F52', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000053, 0, 0, 0, '1', 'F53', 'F53', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000054, 0, 0, 0, '1', 'F54', 'F54', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000055, 0, 0, 0, '1', 'F55', 'F55', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000056, 0, 0, 0, '1', 'F56', 'F56', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000057, 0, 0, 0, '1', 'F57', 'F57', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000058, 0, 0, 0, '1', 'F58', 'F58', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000059, 0, 0, 0, '1', 'F59', 'F59', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000060, 0, 0, 0, '1', 'F60', 'F60', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000061, 0, 0, 0, '1', 'F61', 'F61', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000062, 0, 0, 0, '1', 'F62', 'F62', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000063, 0, 0, 0, '1', 'F63', 'F63', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000064, 0, 0, 0, '1', 'F64', 'F64', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000065, 0, 0, 0, '1', 'F65', 'F65', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000066, 0, 0, 0, '1', 'F66', 'F66', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000067, 0, 0, 0, '1', 'F67', 'F67', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000068, 0, 0, 0, '1', 'F68', 'F68', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000069, 0, 0, 0, '1', 'F69', 'F69', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000070, 0, 0, 0, '1', 'F70', 'F70', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000071, 0, 0, 0, '1', 'F71', 'F71', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000072, 0, 0, 0, '1', 'F72', 'F72', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000073, 0, 0, 0, '1', 'F73', 'F73', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000074, 0, 0, 0, '1', 'F74', 'F74', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000075, 0, 0, 0, '1', 'F75', 'F75', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000076, 0, 0, 0, '1', 'F76', 'F76', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000077, 0, 0, 0, '1', 'F77', 'F77', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000078, 0, 0, 0, '1', 'F78', 'F78', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000079, 0, 0, 0, '1', 'F79', 'F79', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000080, 0, 0, 0, '1', 'F80', 'F80', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000081, 0, 0, 0, '1', 'F81', 'F81', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000082, 0, 0, 0, '1', 'F82', 'F82', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000083, 0, 0, 0, '1', 'F83', 'F83', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000084, 0, 0, 0, '1', 'F84', 'F84', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000085, 0, 0, 0, '1', 'F85', 'F85', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000086, 0, 0, 0, '1', 'F86', 'F86', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000087, 0, 0, 0, '1', 'F87', 'F87', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000088, 0, 0, 0, '1', 'F88', 'F88', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000089, 0, 0, 0, '1', 'F89', 'F89', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000090, 0, 0, 0, '1', 'F90', 'F90', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000091, 0, 0, 0, '1', 'F91', 'F91', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000092, 0, 0, 0, '1', 'F92', 'F92', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000093, 0, 0, 0, '1', 'F93', 'F93', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000094, 0, 0, 0, '1', 'F94', 'F94', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000095, 0, 0, 0, '1', 'F95', 'F95', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000096, 0, 0, 0, '1', 'F96', 'F96', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000097, 0, 0, 0, '1', 'F97', 'F97', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000098, 0, 0, 0, '1', 'F98', 'F98', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_sec` VALUES (1000099, 0, 0, 0, '1', 'F99', 'F99', NULL, '0', 0, 0, '1970-01-01 00:00:00', 0, '1970-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_parts_type
-- ----------------------------
DROP TABLE IF EXISTS `ope_parts_type`;
CREATE TABLE `ope_parts_type`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部品类型名称',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部品类型编码',
  `value` int(0) NULL DEFAULT NULL COMMENT '值',
  `note` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sn_class_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否可销售,0:SC仅可采购，1:SSC可销售可采购',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 6) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部品类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_parts_type
-- ----------------------------
INSERT INTO `ope_parts_type` VALUES (1000000, 0, 0, 0, '1', 'Parts', 'Parts', 1, NULL, '0', 0, 0, '2020-02-27 08:32:22', 0, '2020-02-27 08:32:22', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_type` VALUES (1000001, 0, 0, 0, '1', 'Accessory', 'Accessory', 2, NULL, '0', 0, 0, '2020-02-27 08:32:22', 0, '2020-02-27 08:32:22', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_parts_type` VALUES (1000002, 0, 0, 0, '1', 'Battery', 'Battery', 3, NULL, '0', 0, 0, '2020-02-27 08:32:22', 0, '2020-02-27 08:32:22', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_price_sheet
-- ----------------------------
DROP TABLE IF EXISTS `ope_price_sheet`;
CREATE TABLE `ope_price_sheet`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `tenant_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `user_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '用户ID',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态 Invalid失效，Effective有效，Cancel取消',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格 浮点型价格',
  `currency_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货币类型 如英镑，美元，人民币',
  `currency_unit` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货币单位 如¥，$，€，	￡',
  `standard_currency` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标准货币 用户货币转换',
  `exchange_rate` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '汇率 用于汇率转换',
  `parts_id` bigint(0) NULL DEFAULT NULL COMMENT '部品主键 用于关联部品',
  `begin_date` date NULL DEFAULT NULL COMMENT '开始日期',
  `end_date` date NULL DEFAULT NULL COMMENT '结束日期',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 2) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报价列表 报价列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_price_sheet
-- ----------------------------
INSERT INTO `ope_price_sheet` VALUES (1000000, 0, 0, 1000003, '1', 990.00, 'FR', '1', '3', '0', 1000000, NULL, NULL, 0, 1000003, '2020-06-14 10:13:14', 1000003, '2020-06-14 10:13:14', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_price_sheet_history
-- ----------------------------
DROP TABLE IF EXISTS `ope_price_sheet_history`;
CREATE TABLE `ope_price_sheet_history`  (
  `id` bigint(0) NOT NULL COMMENT '主键 主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除 逻辑删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID 租户ID',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID 用户ID',
  `parts_id` bigint(0) NULL DEFAULT NULL COMMENT '部品主键 用于关联部品',
  `price_sheet_id` bigint(0) NULL DEFAULT NULL COMMENT '报价表主键 用于关联报价表',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格 浮点型价格',
  `currency_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货币类型 如英镑，美元，人民币',
  `currency_unit` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货币单位 如¥，$，€，	￡',
  `standard_currency` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标准货币 用户货币转换',
  `exchange_rate` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '汇率 用于汇率转换',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 2) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报价列表历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_price_sheet_history
-- ----------------------------
INSERT INTO `ope_price_sheet_history` VALUES (1000000, 0, 0, 1000003, 1000000, 1000000, 990.00, 'FR', '1', '3', '0', 0, 1000003, '2020-06-14 10:13:14', 1000003, '2020-06-14 10:13:14', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_product_assembly
-- ----------------------------
DROP TABLE IF EXISTS `ope_product_assembly`;
CREATE TABLE `ope_product_assembly`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标识',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '商品Id',
  `assembly_b_id` bigint(0) NULL DEFAULT NULL COMMENT '组装单子表id',
  `assembly_id` bigint(0) NULL DEFAULT NULL COMMENT '组装单id',
  `inwh_status` tinyint(1) NULL DEFAULT NULL COMMENT '是否入库',
  `product_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `product_serial_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品序列号',
  `product_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品类型',
  `product_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品编码',
  `production_date` datetime(0) NULL DEFAULT NULL COMMENT '生产日期',
  `print_flag` tinyint(1) NULL DEFAULT NULL COMMENT '是否打印标识',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '产品组装表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_product_assembly
-- ----------------------------

-- ----------------------------
-- Table structure for ope_product_assembly_b
-- ----------------------------
DROP TABLE IF EXISTS `ope_product_assembly_b`;
CREATE TABLE `ope_product_assembly_b`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标识',
  `product_assembly_id` bigint(0) NULL DEFAULT NULL COMMENT '产品组装表Id',
  `part_id` bigint(0) NULL DEFAULT NULL COMMENT '部件Id',
  `qty` int(0) NULL DEFAULT NULL COMMENT '部件数量',
  `part_serial_num` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部件序列号',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '产品组装子表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_product_assembly_b
-- ----------------------------

-- ----------------------------
-- Table structure for ope_product_qc_template
-- ----------------------------
DROP TABLE IF EXISTS `ope_product_qc_template`;
CREATE TABLE `ope_product_qc_template`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '产品Id',
  `import_excel_batch_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导入批次号',
  `source_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源方式',
  `qc_item_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '质检项名称',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 6) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品质检模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_product_qc_template
-- ----------------------------
INSERT INTO `ope_product_qc_template` VALUES (1000000, 0, 1000000, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:12:46', 1000003, '2020-06-14 10:12:46', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000001, 0, 1000001, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:17:14', 1000003, '2020-06-14 10:17:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000002, 0, 1000015, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:17:29', 1000003, '2020-06-14 10:17:29', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000003, 0, 1000014, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:17:47', 1000003, '2020-06-14 10:17:47', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000004, 0, 1000013, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:18:07', 1000003, '2020-06-14 10:18:07', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000005, 0, 1000012, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:18:51', 1000003, '2020-06-14 10:18:51', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000006, 0, 1000011, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:19:28', 1000003, '2020-06-14 10:19:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000007, 0, 1000010, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:19:49', 1000003, '2020-06-14 10:19:49', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000008, 0, 1000009, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:20:10', 1000003, '2020-06-14 10:20:10', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000009, 0, 1000008, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:20:31', 1000003, '2020-06-14 10:20:31', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000010, 0, 1000007, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:20:45', 1000003, '2020-06-14 10:20:45', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000011, 0, 1000006, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:21:01', 1000003, '2020-06-14 10:21:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000012, 0, 1000005, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:21:15', 1000003, '2020-06-14 10:21:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000013, 0, 1000004, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:21:28', 1000003, '2020-06-14 10:21:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000014, 0, 1000003, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:21:42', 1000003, '2020-06-14 10:21:42', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template` VALUES (1000015, 0, 1000002, NULL, '2', 'Appearance', 0, 1000003, '2020-06-14 10:22:00', 1000003, '2020-06-14 10:22:00', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_product_qc_template_b
-- ----------------------------
DROP TABLE IF EXISTS `ope_product_qc_template_b`;
CREATE TABLE `ope_product_qc_template_b`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除',
  `product_qc_template_id` bigint(0) NULL DEFAULT NULL COMMENT '质检项Id',
  `qc_result` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '质检结果',
  `pass_flag` tinyint(1) NULL DEFAULT NULL COMMENT '通过标记',
  `upload_flag` tinyint(1) NULL DEFAULT NULL COMMENT '是否允许上传图片',
  `results_sequence` int(0) NULL DEFAULT NULL COMMENT '质检结果集优先级',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 6) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部件质检模板结果项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_product_qc_template_b
-- ----------------------------
INSERT INTO `ope_product_qc_template_b` VALUES (1000000, 0, 1000000, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:12:46', 1000003, '2020-06-14 10:12:46', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000001, 0, 1000000, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:12:46', 1000003, '2020-06-14 10:12:46', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000002, 0, 1000001, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:17:14', 1000003, '2020-06-14 10:17:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000003, 0, 1000001, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:17:14', 1000003, '2020-06-14 10:17:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000004, 0, 1000002, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:17:29', 1000003, '2020-06-14 10:17:29', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000005, 0, 1000002, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:17:29', 1000003, '2020-06-14 10:17:29', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000006, 0, 1000003, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:17:47', 1000003, '2020-06-14 10:17:47', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000007, 0, 1000003, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:17:47', 1000003, '2020-06-14 10:17:47', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000008, 0, 1000004, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:18:07', 1000003, '2020-06-14 10:18:07', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000009, 0, 1000004, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:18:07', 1000003, '2020-06-14 10:18:07', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000010, 0, 1000005, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:18:51', 1000003, '2020-06-14 10:18:51', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000011, 0, 1000005, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:18:51', 1000003, '2020-06-14 10:18:51', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000012, 0, 1000006, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:19:28', 1000003, '2020-06-14 10:19:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000013, 0, 1000006, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:19:28', 1000003, '2020-06-14 10:19:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000014, 0, 1000007, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:19:49', 1000003, '2020-06-14 10:19:49', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000015, 0, 1000007, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:19:49', 1000003, '2020-06-14 10:19:49', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000016, 0, 1000008, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:20:10', 1000003, '2020-06-14 10:20:10', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000017, 0, 1000008, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:20:10', 1000003, '2020-06-14 10:20:10', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000018, 0, 1000009, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:20:31', 1000003, '2020-06-14 10:20:31', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000019, 0, 1000009, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:20:31', 1000003, '2020-06-14 10:20:31', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000020, 0, 1000010, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:20:45', 1000003, '2020-06-14 10:20:45', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000021, 0, 1000010, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:20:45', 1000003, '2020-06-14 10:20:45', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000022, 0, 1000011, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:21:01', 1000003, '2020-06-14 10:21:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000023, 0, 1000011, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:21:01', 1000003, '2020-06-14 10:21:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000024, 0, 1000012, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:21:15', 1000003, '2020-06-14 10:21:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000025, 0, 1000012, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:21:15', 1000003, '2020-06-14 10:21:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000026, 0, 1000013, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:21:28', 1000003, '2020-06-14 10:21:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000027, 0, 1000013, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:21:28', 1000003, '2020-06-14 10:21:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000028, 0, 1000014, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:21:42', 1000003, '2020-06-14 10:21:42', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000029, 0, 1000014, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:21:42', 1000003, '2020-06-14 10:21:42', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000030, 0, 1000015, 'PASS', 1, 1, 1, 0, 1000003, '2020-06-14 10:22:00', 1000003, '2020-06-14 10:22:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_product_qc_template_b` VALUES (1000031, 0, 1000015, 'NG', 0, 0, 2, 0, 1000003, '2020-06-14 10:22:00', 1000003, '2020-06-14 10:22:00', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_purchas
-- ----------------------------
DROP TABLE IF EXISTS `ope_purchas`;
CREATE TABLE `ope_purchas`  (
  `id` bigint(0) NOT NULL COMMENT '主键 主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '逻辑删除标识 逻辑删除标识',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `consignee_id` bigint(0) NULL DEFAULT NULL COMMENT '收货人Id',
  `contract_no` varchar(128) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '采购单编号 采购单标号',
  `status` varchar(128) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '状态 采购单状态',
  `payment_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '付款类型',
  `factory_id` bigint(0) NULL DEFAULT NULL COMMENT '工厂主键 代工厂Id',
  `factory_annex` varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '工厂组装附件',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '总金额 总金额',
  `total_qty` int(0) NULL DEFAULT NULL COMMENT '总数量 总数量',
  `lave_wait_qc_total` int(0) NULL DEFAULT NULL COMMENT '待质检总数',
  `in_wait_wh_total` int(0) NULL DEFAULT NULL COMMENT '待入库总数',
  `revision` int(0) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_purchas
-- ----------------------------

-- ----------------------------
-- Table structure for ope_purchas_b
-- ----------------------------
DROP TABLE IF EXISTS `ope_purchas_b`;
CREATE TABLE `ope_purchas_b`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '逻辑删除表示',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `purchas_id` bigint(0) NULL DEFAULT NULL COMMENT '采购订单Id',
  `part_id` bigint(0) NULL DEFAULT NULL COMMENT '部品Id',
  `supplier_id` bigint(0) NULL DEFAULT NULL COMMENT '供应商Id',
  `supplier_annex` varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '供应商附件',
  `qc_status` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'qc质检状态',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `total_count` int(0) NULL DEFAULT NULL COMMENT '总数量',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '总价格',
  `lave_wait_qc_qty` int(0) NULL DEFAULT NULL COMMENT '待质检数',
  `in_wait_wh_qty` int(0) NULL DEFAULT NULL COMMENT '待入库数量',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购单条目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_purchas_b
-- ----------------------------

-- ----------------------------
-- Table structure for ope_purchas_b_qc
-- ----------------------------
DROP TABLE IF EXISTS `ope_purchas_b_qc`;
CREATE TABLE `ope_purchas_b_qc`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标志',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userId',
  `purchas_b_id` bigint(0) NULL DEFAULT NULL COMMENT '采购单条目Id',
  `parts_id` bigint(0) NULL DEFAULT NULL COMMENT '部品Id',
  `quality_inspector_id` bigint(0) NULL DEFAULT NULL COMMENT '质检人Id',
  `batch_no` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '质检批次号',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '质检状态',
  `total_quality_inspected` int(0) NULL DEFAULT NULL COMMENT '需要质检总数',
  `pass_count` int(0) NULL DEFAULT NULL COMMENT '质检通过数量',
  `fail_count` int(0) NULL DEFAULT NULL COMMENT '质检失败数量',
  `quality_inspection_time` datetime(0) NULL DEFAULT NULL COMMENT '质检时间',
  `revision` int(0) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购条目QC质检' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_purchas_b_qc
-- ----------------------------

-- ----------------------------
-- Table structure for ope_purchas_b_qc_item
-- ----------------------------
DROP TABLE IF EXISTS `ope_purchas_b_qc_item`;
CREATE TABLE `ope_purchas_b_qc_item`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标志',
  `purchas_b_id` bigint(0) NULL DEFAULT NULL COMMENT '采购单子表Id',
  `part_id` bigint(0) NULL DEFAULT NULL COMMENT '部品Id',
  `purchas_b_qc_id` bigint(0) NULL DEFAULT 0 COMMENT '质检结果Id',
  `purchas_b_lot_trace_id` bigint(0) NULL DEFAULT NULL COMMENT '质检批次记录Id',
  `batch_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `serial_num` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部品序列号',
  `qc_batch_total` int(0) NULL DEFAULT NULL COMMENT '批量质检数量 和质检方式连用',
  `qc_result` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '质检结果',
  `revision` int(0) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部件质检条目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_purchas_b_qc_item
-- ----------------------------

-- ----------------------------
-- Table structure for ope_purchas_lot_trace
-- ----------------------------
DROP TABLE IF EXISTS `ope_purchas_lot_trace`;
CREATE TABLE `ope_purchas_lot_trace`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标志',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userId',
  `purchas_id` bigint(0) NULL DEFAULT NULL COMMENT '采购单号',
  `quality_inspector_id` bigint(0) NULL DEFAULT NULL COMMENT '质检人Id',
  `batch_no` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '质检批次号',
  `total_quality_inspected` int(0) NULL DEFAULT NULL COMMENT '需要质检总数',
  `pass_count` int(0) NULL DEFAULT NULL COMMENT '质检通过数量',
  `fail_count` int(0) NULL DEFAULT NULL COMMENT '质检失败数量',
  `quality_inspection_time` datetime(0) NULL DEFAULT NULL COMMENT '质检时间',
  `revision` int(0) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购单批次质检记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_purchas_lot_trace
-- ----------------------------

-- ----------------------------
-- Table structure for ope_purchas_payment
-- ----------------------------
DROP TABLE IF EXISTS `ope_purchas_payment`;
CREATE TABLE `ope_purchas_payment`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userId',
  `purchas_id` bigint(0) NULL DEFAULT NULL COMMENT '采购订单ID',
  `payment_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分期，月结',
  `planned_payment_time` datetime(0) NOT NULL COMMENT '预计付款时间',
  `payment_day` int(0) NULL DEFAULT NULL COMMENT '付款周期',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '实际付款时间',
  `payment_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付状态:Paid Unpaid',
  `payment_priority` int(0) NOT NULL DEFAULT 0 COMMENT '支付优先级',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `amount` decimal(10, 2) NOT NULL COMMENT '价格',
  `amount_proportion` int(0) NOT NULL COMMENT '55% 标示为 55',
  `invoice_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票单号',
  `invoice_picture` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票附件',
  `revision` int(0) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '销售订单支付表 分期时表明是第几期支付的' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_purchas_payment
-- ----------------------------

-- ----------------------------
-- Table structure for ope_purchas_product
-- ----------------------------
DROP TABLE IF EXISTS `ope_purchas_product`;
CREATE TABLE `ope_purchas_product`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除表示',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `purchas_id` bigint(0) NULL DEFAULT NULL COMMENT '采购订单id',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '产品Id',
  `id_class` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'qc质检类型（整车质检、部品质检）',
  `product_type` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '产品类型',
  `product_num` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '产品编号',
  `revision` int(0) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购订单产品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_purchas_product
-- ----------------------------

-- ----------------------------
-- Table structure for ope_purchas_qc_trace
-- ----------------------------
DROP TABLE IF EXISTS `ope_purchas_qc_trace`;
CREATE TABLE `ope_purchas_qc_trace`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL COMMENT '删除标识',
  `part_id` bigint(0) NULL DEFAULT NULL COMMENT '部件Id',
  `purchas_id` bigint(0) NULL DEFAULT NULL COMMENT '采购单id',
  `purchas_b_qc_id` bigint(0) NULL DEFAULT NULL COMMENT '采购单子表Id',
  `part_qc_template_id` bigint(0) NULL DEFAULT NULL COMMENT '质检模板Id',
  `part_qc_template_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板质检项名称',
  `part_qc_template_b_id` bigint(0) NULL DEFAULT NULL COMMENT '质检结果Id',
  `part_qc_template_b_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板质检结果名称',
  `purchas_b_qc_item_id` bigint(0) NULL DEFAULT NULL COMMENT '质检条目Id',
  `part_qc_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '质检图片的逗号分隔',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '采购单质检记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_purchas_qc_trace
-- ----------------------------

-- ----------------------------
-- Table structure for ope_purchas_trace
-- ----------------------------
DROP TABLE IF EXISTS `ope_purchas_trace`;
CREATE TABLE `ope_purchas_trace`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户Id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'userId',
  `purchas_id` bigint(0) NOT NULL COMMENT '采购订单id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采购单状态',
  `event` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件',
  `event_time` datetime(0) NULL DEFAULT NULL COMMENT '事件时间',
  `memo` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注 放话术参数',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购订单节点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_purchas_trace
-- ----------------------------

-- ----------------------------
-- Table structure for ope_regional_price_sheet
-- ----------------------------
DROP TABLE IF EXISTS `ope_regional_price_sheet`;
CREATE TABLE `ope_regional_price_sheet`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态 状态',
  `parts_product_id` bigint(0) NULL DEFAULT NULL COMMENT '产品Id',
  `price_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '价格类型，1.销售价，2采购价，3其他价格',
  `sales_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '销售价格 浮点型价格',
  `effective_time` datetime(0) NULL DEFAULT NULL COMMENT '生效时间 默认当前生效',
  `valid_flag` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '有效标识 标识当前区域报价是否生效',
  `currency_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货币类型 如英镑，美元，人民币',
  `currency_unit` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货币单位 如¥，$，€，	￡',
  `standard_currency` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标准货币 用户货币转换',
  `exchange_rate` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '汇率 用于汇率转换',
  `country_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '国家编码 当前销售国家',
  `country_city` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '国家城市 当然销售国家的城市',
  `country_language` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '国家语言 当前销售国家语言',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 2) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '区域报价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_regional_price_sheet
-- ----------------------------
INSERT INTO `ope_regional_price_sheet` VALUES (1000000, 0, 0, 1000003, '1', 1000000, '1', 990.00, '2020-06-14 10:13:23', '1', 'FR', '1', 'CN', '0', 'US', '0', 'en', 0, 1000003, '2020-06-14 10:13:23', 1000003, '2020-06-14 10:13:23', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_regional_price_sheet` VALUES (1000001, 0, 0, 1000003, '1', 1000000, '1', 990.00, '2020-06-14 10:13:23', '1', 'EN', '2', 'CN', '0', 'US', '0', 'en', 0, 1000003, '2020-06-14 10:13:23', 1000003, '2020-06-14 10:13:23', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_regional_price_sheet_history
-- ----------------------------
DROP TABLE IF EXISTS `ope_regional_price_sheet_history`;
CREATE TABLE `ope_regional_price_sheet_history`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL COMMENT '逻辑删除',
  `tenant_id` bigint(0) NOT NULL COMMENT '租户ID',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `regional_price_sheet_id` bigint(0) NULL DEFAULT NULL COMMENT '区域报价主键 关联区域报价表',
  `parts_product_id` bigint(0) NULL DEFAULT NULL COMMENT '产品ID',
  `price_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '价格类型，1.销售价，2采购价，3其他价格',
  `sales_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '销售价格 浮点型价格',
  `currency_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货币类型 如英镑，美元，人民币',
  `currency_unit` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货币单位 如¥，$，€，	￡',
  `standard_currency` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标准货币 用户货币转换',
  `exchange_rate` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '汇率 用于汇率转换',
  `country_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '国家编码 当前销售国家',
  `country_city` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '国家城市 当然销售国家的城市',
  `country_language` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '国家语言 当前销售国家语言',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `def1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10, 2) NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '区域报价表历史记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_regional_price_sheet_history
-- ----------------------------
INSERT INTO `ope_regional_price_sheet_history` VALUES (1000000, 0, 0, 1000003, 1000000, 1000000, '0', 990.00, 'FR', '1', 'CN', '0', 'US', '0', 'en', 0, 1000003, '2020-06-14 10:13:23', 1000003, '2020-06-14 10:13:23', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_regional_price_sheet_history` VALUES (1000001, 0, 0, 1000003, 1000000, 1000000, '0', 990.00, 'EN', '2', 'CN', '0', 'US', '0', 'en', 0, 1000003, '2020-06-14 10:13:23', 1000003, '2020-06-14 10:13:23', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_stock
-- ----------------------------
DROP TABLE IF EXISTS `ope_stock`;
CREATE TABLE `ope_stock`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT NULL,
  `user_id` bigint(0) NULL DEFAULT NULL,
  `tenant_id` bigint(0) NULL DEFAULT NULL,
  `whse_id` bigint(0) NULL DEFAULT NULL COMMENT '所属仓库Id',
  `int_total` int(0) NULL DEFAULT NULL COMMENT '入库总数',
  `out_total` int(0) NULL DEFAULT NULL COMMENT '出库总数',
  `available_total` int(0) NULL DEFAULT NULL COMMENT '剩余库存',
  `worn_total` int(0) NULL DEFAULT NULL COMMENT '破损总数',
  `materiel_product_id` bigint(0) NULL DEFAULT NULL COMMENT '所属物料产品Id',
  `materiel_product_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属物料产品类型',
  `materiel_product_name` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属物料名称',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存总表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_stock
-- ----------------------------

-- ----------------------------
-- Table structure for ope_stock_bill
-- ----------------------------
DROP TABLE IF EXISTS `ope_stock_bill`;
CREATE TABLE `ope_stock_bill`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL DEFAULT 0 COMMENT '删除标识',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户id',
  `stock_id` bigint(0) NULL DEFAULT NULL COMMENT '库存id',
  `direction` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入库 0:In 出库 1:Out',
  `source_id` bigint(0) NULL DEFAULT NULL COMMENT '单据来源ID',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态:0正常，1异常',
  `total` int(0) NULL DEFAULT NULL COMMENT '入库单数量总计',
  `source_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据来源:1采购入库单，2采购入库单，3调拨入库单，4调拨出库单',
  `principal_id` bigint(0) NULL DEFAULT NULL COMMENT '负责人',
  `operatine_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_stock_bill
-- ----------------------------

-- ----------------------------
-- Table structure for ope_stock_prod_part
-- ----------------------------
DROP TABLE IF EXISTS `ope_stock_prod_part`;
CREATE TABLE `ope_stock_prod_part`  (
  `id` bigint(0) NOT NULL,
  `dr` int(0) NULL DEFAULT NULL,
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '可用、破损',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户id',
  `stock_id` bigint(0) NULL DEFAULT NULL COMMENT '库存id',
  `part_id` bigint(0) NULL DEFAULT NULL COMMENT '物料ID',
  `lot` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `in_stock_bill_id` bigint(0) NULL DEFAULT NULL COMMENT '入库单Id',
  `out_stock_bill_id` bigint(0) NULL DEFAULT NULL COMMENT '出库单Id',
  `serial_number` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '序列号',
  `parts_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部品号',
  `principal_id` bigint(0) NULL DEFAULT NULL COMMENT '入库负责人Id',
  `in__wh_qty` int(0) NULL DEFAULT NULL COMMENT '入库数量',
  `in_stock_time` datetime(0) NULL DEFAULT NULL COMMENT '入库时间',
  `out_stock_time` datetime(0) NULL DEFAULT NULL COMMENT '出库时间',
  `out_principal_id` bigint(0) NULL DEFAULT NULL COMMENT '出库负责人',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '生产原料库存' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_stock_prod_part
-- ----------------------------

-- ----------------------------
-- Table structure for ope_stock_prod_product
-- ----------------------------
DROP TABLE IF EXISTS `ope_stock_prod_product`;
CREATE TABLE `ope_stock_prod_product`  (
  `id` bigint(0) NOT NULL,
  `dr` int(0) NULL DEFAULT NULL,
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '可用、破损',
  `stock_id` bigint(0) NULL DEFAULT NULL COMMENT '库存id',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '商品Id',
  `lot` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `serial_number` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '序列号',
  `product_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部件号',
  `in_stock_bill_id` bigint(0) NULL DEFAULT NULL COMMENT '入库单Id',
  `principal_id` bigint(0) NULL DEFAULT NULL COMMENT '入库负责人Id',
  `in__wh_qty` int(0) NULL DEFAULT NULL COMMENT '入库数量',
  `in_stock_time` datetime(0) NULL DEFAULT NULL COMMENT '入库时间',
  `out_stock_bill_id` bigint(0) NULL DEFAULT NULL COMMENT '出库单Id',
  `out_principal_id` bigint(0) NULL DEFAULT NULL COMMENT '出库负责人',
  `out_stock_time` datetime(0) NULL DEFAULT NULL COMMENT '出库时间',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '生产成品库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_stock_prod_product
-- ----------------------------

-- ----------------------------
-- Table structure for ope_stock_purchas
-- ----------------------------
DROP TABLE IF EXISTS `ope_stock_purchas`;
CREATE TABLE `ope_stock_purchas`  (
  `id` bigint(0) NOT NULL,
  `dr` int(0) NULL DEFAULT NULL,
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '可用、破损',
  `stock_id` bigint(0) NULL DEFAULT NULL COMMENT '库存id',
  `part_id` bigint(0) NULL DEFAULT NULL,
  `lot` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `part_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物料号',
  `serial_number` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '序列号:QC质检合格后产生',
  `principal_id` bigint(0) NULL DEFAULT NULL COMMENT '入库负责人Id',
  `in__wh_qty` int(0) NULL DEFAULT NULL COMMENT '入库数量',
  `in_stock_time` datetime(0) NULL DEFAULT NULL COMMENT '入库时间',
  `in_stock_bill_id` bigint(0) NULL DEFAULT NULL COMMENT '入库单Id',
  `out_stock_bill_id` bigint(0) NULL DEFAULT NULL COMMENT '出库单Id',
  `out_principal_id` bigint(0) NULL DEFAULT NULL COMMENT '出库负责人',
  `out_stock_time` datetime(0) NULL DEFAULT NULL COMMENT '出库时间',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '采购库存' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_stock_purchas
-- ----------------------------

-- ----------------------------
-- Table structure for ope_supplier
-- ----------------------------
DROP TABLE IF EXISTS `ope_supplier`;
CREATE TABLE `ope_supplier`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识',
  `status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `supplier_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '采购商名称',
  `supplier_address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '采购商地址',
  `supplier_country` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '采购商国家',
  `supplier_longitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '采购商经度',
  `supplier_latitude` decimal(32, 10) NULL DEFAULT NULL COMMENT '采购商纬度',
  `place_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址唯一ID',
  `geo_hash` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'geo_hash',
  `supplier_tag` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '采购商标签',
  `supplier_memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '采购商备注',
  `contact_first_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人名字',
  `contact_last_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人姓氏',
  `contact_full_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人全名',
  `contact_email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人邮箱',
  `contact_phone_country_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号归属国家',
  `contact_phone` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人手机号',
  `payment_cycle` int(0) NOT NULL COMMENT '付款周期',
  `cooperation_time_start` datetime(0) NULL DEFAULT NULL COMMENT '合作开始时间',
  `cooperation_time_end` datetime(0) NULL DEFAULT NULL COMMENT '合作结束时间',
  `business_number` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '营业执照编号',
  `business_license_annex` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '营业执照附件',
  `contract_number` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '合同编号',
  `contract_annex` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '合同附件',
  `overdue_flag` int(0) NOT NULL DEFAULT 0 COMMENT '是否过期：默认0不过期，-1过期',
  `created_time` datetime(0) NOT NULL COMMENT '创建时间',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `def1` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '采购商' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_supplier
-- ----------------------------
INSERT INTO `ope_supplier` VALUES (1000000, 0, '1', 0, 1000003, 'Emma', 'Wuxi, Jiangsu, China', 'China', NULL, NULL, NULL, NULL, 'Frame Factory', NULL, NULL, NULL, 'bill', 'bill@redescooter.com', '86', '123456789', 30, '2020-06-14 00:00:00', NULL, '1234567890', 'https://rede.oss-cn-shanghai.aliyuncs.com/1592129491969.pdf', NULL, 'https://rede.oss-cn-shanghai.aliyuncs.com/1592129495983.pdf', 0, '2020-06-14 10:11:53', 1000003, '2020-06-14 10:11:53', 1000003, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ope_supplier_trace
-- ----------------------------
DROP TABLE IF EXISTS `ope_supplier_trace`;
CREATE TABLE `ope_supplier_trace`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `supplier_id` bigint(0) NOT NULL COMMENT '采购商ID',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采购商操作状态',
  `event` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采购商操作事件',
  `event_time` datetime(0) NULL DEFAULT NULL COMMENT '事件时间',
  `reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注说明',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购商操作根据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_supplier_trace
-- ----------------------------
INSERT INTO `ope_supplier_trace` VALUES (1000000, 0, 1000000, 0, 1000003, '1', '1', '2020-06-14 10:11:53', NULL, 1000003, '2020-06-14 10:11:53', 1000003, '2020-06-14 10:11:53', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for ope_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `ope_sys_dept`;
CREATE TABLE `ope_sys_dept`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `dr` int(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `p_id` bigint(0) NULL DEFAULT NULL COMMENT '父级部门id',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户id',
  `principal` bigint(0) NULL DEFAULT 0 COMMENT '负责人',
  `level` int(0) NOT NULL DEFAULT 1 COMMENT '级别0公司，1部门',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门编码',
  `description` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述说明',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT '排序',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1004233 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_sys_dept
-- ----------------------------
INSERT INTO `ope_sys_dept` VALUES (1000000, 0, -1, 0, 1000002, 0, 'ROOT', 'ROOT', NULL, 2, 0, '2020-03-16 08:40:15', 0, '2020-06-05 07:16:51');
INSERT INTO `ope_sys_dept` VALUES (1004220, 0, 1000000, 0, 1000002, 0, 'RedEGroup', 'RedEGroup', NULL, 2, 0, '2020-03-16 08:42:12', 0, '2020-06-05 07:16:50');
INSERT INTO `ope_sys_dept` VALUES (1004221, 0, 1004220, 0, 1000002, 1, '研发部', 'R & D Department', NULL, 7, 0, '2020-03-16 08:46:07', 0, '2020-06-14 06:32:16');
INSERT INTO `ope_sys_dept` VALUES (1004222, 0, 1004220, 0, 1000002, 1, '财务部', 'Finance Department', NULL, 2, 0, '2020-03-16 08:47:04', 0, '2020-06-05 07:16:50');
INSERT INTO `ope_sys_dept` VALUES (1004223, 1, 1004222, 0, 0, 1, 'Test', 'Finance Department', NULL, 1, 1000012, '2020-06-13 09:31:22', 1000012, '2020-06-13 09:31:29');
INSERT INTO `ope_sys_dept` VALUES (1004224, 1, 1004221, 0, 0, 1, '11', 'R & D Department', NULL, 1, 1000006, '2020-06-13 09:49:38', 1000006, '2020-06-13 09:50:03');
INSERT INTO `ope_sys_dept` VALUES (1004225, 1, 1004221, 0, 0, 1, '11', 'R & D Department', NULL, 2, 1000006, '2020-06-13 09:52:24', 1000006, '2020-06-14 17:50:43');
INSERT INTO `ope_sys_dept` VALUES (1004226, 1, 1004221, 0, 0, 1, '22', 'R & D Department', NULL, 3, 1000006, '2020-06-13 09:52:38', 1000006, '2020-06-14 06:30:53');
INSERT INTO `ope_sys_dept` VALUES (1004227, 1, 1004221, 0, 0, 1, '33', 'R & D Department', NULL, 4, 1000006, '2020-06-13 10:04:51', 1000006, '2020-06-14 06:30:53');
INSERT INTO `ope_sys_dept` VALUES (1004228, 1, 1004221, 0, 0, 1, '20', 'R & D Department', NULL, 5, 1000006, '2020-06-13 10:49:36', 1000006, '2020-06-14 06:30:53');
INSERT INTO `ope_sys_dept` VALUES (1004229, 1, 1004221, 0, 0, 1, '00', 'R & D Department', NULL, 6, 1000006, '2020-06-13 10:51:25', 1000006, '2020-06-14 17:50:41');
INSERT INTO `ope_sys_dept` VALUES (1004230, 1, 1004222, 0, 1000000, 1, '00', 'Finance Department', NULL, 2, 1000006, '2020-06-13 10:55:11', 1000006, '2020-06-14 17:50:35');
INSERT INTO `ope_sys_dept` VALUES (1004231, 1, 1004221, 0, 1000000, 1, '22', 'R & D Department', NULL, 7, 1000006, '2020-06-14 04:04:38', 1000006, '2020-06-14 06:37:36');
INSERT INTO `ope_sys_dept` VALUES (1004232, 1, 1004221, 0, 1000000, 1, '33', 'R & D Department', NULL, 7, 1000006, '2020-06-14 07:04:52', 1000006, '2020-06-14 17:50:38');

-- ----------------------------
-- Table structure for ope_sys_dept_relation
-- ----------------------------
DROP TABLE IF EXISTS `ope_sys_dept_relation`;
CREATE TABLE `ope_sys_dept_relation`  (
  `ancestor` bigint(0) NOT NULL COMMENT '祖先节点',
  `descendant` bigint(0) NOT NULL COMMENT '后代节点',
  UNIQUE INDEX `ancestor`(`ancestor`, `descendant`) USING BTREE,
  INDEX `idx1`(`ancestor`) USING BTREE,
  INDEX `idx2`(`descendant`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_sys_dept_relation
-- ----------------------------
INSERT INTO `ope_sys_dept_relation` VALUES (1004219, 1004219);
INSERT INTO `ope_sys_dept_relation` VALUES (1004220, 1004220);
INSERT INTO `ope_sys_dept_relation` VALUES (1004221, 1004221);
INSERT INTO `ope_sys_dept_relation` VALUES (1004221, 1004224);
INSERT INTO `ope_sys_dept_relation` VALUES (1004221, 1004225);
INSERT INTO `ope_sys_dept_relation` VALUES (1004221, 1004228);
INSERT INTO `ope_sys_dept_relation` VALUES (1004221, 1004229);
INSERT INTO `ope_sys_dept_relation` VALUES (1004221, 1004231);
INSERT INTO `ope_sys_dept_relation` VALUES (1004221, 1004232);
INSERT INTO `ope_sys_dept_relation` VALUES (1004222, 1004222);
INSERT INTO `ope_sys_dept_relation` VALUES (1004222, 1004223);
INSERT INTO `ope_sys_dept_relation` VALUES (1004222, 1004230);
INSERT INTO `ope_sys_dept_relation` VALUES (1004223, 1004223);
INSERT INTO `ope_sys_dept_relation` VALUES (1004224, 1004224);
INSERT INTO `ope_sys_dept_relation` VALUES (1004225, 1004225);
INSERT INTO `ope_sys_dept_relation` VALUES (1004226, 1004226);
INSERT INTO `ope_sys_dept_relation` VALUES (1004227, 1004227);
INSERT INTO `ope_sys_dept_relation` VALUES (1004228, 1004228);
INSERT INTO `ope_sys_dept_relation` VALUES (1004229, 1004229);
INSERT INTO `ope_sys_dept_relation` VALUES (1004230, 1004230);
INSERT INTO `ope_sys_dept_relation` VALUES (1004231, 1004231);
INSERT INTO `ope_sys_dept_relation` VALUES (1004232, 1004232);

-- ----------------------------
-- Table structure for ope_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `ope_sys_menu`;
CREATE TABLE `ope_sys_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `dr` int(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识',
  `p_id` bigint(0) NULL DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单编码',
  `permission` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限码',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由',
  `component` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对应路由组件component',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单类型：0菜单，1按钮，3开放API',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图表',
  `level` int(0) NULL DEFAULT 0 COMMENT '等级',
  `sort` int(0) NULL DEFAULT 1 COMMENT '菜单权重',
  `remark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` decimal(10, 2) NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1010482 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_sys_menu
-- ----------------------------
INSERT INTO `ope_sys_menu` VALUES (1008301, 0, -1, 'ROOT', 'ROOT', 'SYS::ROOT', '/', NULL, '0', '', 0, 0, NULL, 0, '2020-03-16 08:51:14', 0, '2020-03-20 03:00:31', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010401, 0, 1008301, 'Dashboard', 'DASHBOARD', NULL, '/', '', '0', NULL, 1, 1, NULL, 1008371, '2020-03-18 07:48:56', 1008371, '2020-03-20 03:14:12', 'ROOT', 'ROOT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010403, 0, 1008301, 'Sales', 'SALES', NULL, '/', '', '0', NULL, 1, 1, NULL, 1008371, '2020-03-18 07:51:53', 1008371, '2020-03-20 03:14:12', 'ROOT', 'ROOT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010404, 0, 1010403, 'Inquiry', 'SALES_INQUIRY', '', '/', '', '0', '', 2, 1, '', 1008371, '2020-03-18 07:52:53', 1008371, '2020-04-11 10:44:09', 'Sales', 'SALES', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010405, 0, 1010403, 'Customer', 'SALES_CUSTOMER', NULL, '/', '', '0', NULL, 2, 1, NULL, 1008371, '2020-03-18 07:53:37', 1008371, '2020-03-20 03:14:12', 'Sales', 'SALES', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010406, 0, 1010403, 'Customer Account', 'SALES_CUSTOMER_ACCOUNT', NULL, '/', '', '0', NULL, 2, 1, NULL, 1008371, '2020-03-18 07:55:24', 1008371, '2020-03-20 03:14:12', 'Sales', 'SALES', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010407, 0, 1008301, 'Organization', 'ORGANIZATION', NULL, '/', '', '0', NULL, 1, 1, NULL, 1008371, '2020-03-18 07:58:14', 1008371, '2020-03-20 03:14:12', 'ROOT', 'ROOT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010408, 0, 1010407, 'Employee', 'ORGANIZATION_EMPLOYEE', '', '/', '', '0', '', 2, 1, '', 1008371, '2020-03-18 07:59:08', 1010426, '2020-04-11 07:05:51', 'Organization', 'ORGANIZATION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010409, 0, 1010407, 'Position', 'ORGANIZATION_POSITION', '', '/', '', '0', '', 2, 1, '', 1008371, '2020-03-18 07:59:28', 1010426, '2020-04-11 07:05:31', 'Organization', 'ORGANIZATION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010410, 0, 1008301, 'Production', 'PRODUCTION', NULL, '/', '', '0', NULL, 1, 1, NULL, 1008371, '2020-03-18 08:00:08', 1008371, '2020-03-20 03:14:12', 'ROOT', 'ROOT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010411, 0, 1010410, 'Factory', 'PRODUCTION_FACTORY', NULL, '/', '', '0', NULL, 2, 1, NULL, 1008371, '2020-03-18 08:00:31', 1008371, '2020-03-20 03:14:12', 'Production', 'PRODUCTION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010412, 0, 1010410, 'Supplier', 'PRODUCTION_SUPPLIER', NULL, '/', '', '0', NULL, 2, 1, NULL, 1008371, '2020-03-18 08:02:41', 1008371, '2020-03-20 03:14:12', 'Production', 'PRODUCTION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010413, 0, 1010410, 'purchasing WH', 'PRODUCTION_PURCHASING_WH', '', '/', '', '0', '', 2, 1, '', 1008371, '2020-03-18 08:05:18', 1010426, '2020-04-11 11:03:26', 'Production', 'PRODUCTION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010414, 0, 1010410, 'Purchasing', 'PRODUCTION_PURCHASING', '', '/', '', '0', '', 2, 1, '', 1008371, '2020-03-18 08:06:39', 1010426, '2020-04-11 07:08:13', 'Production', 'PRODUCTION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010415, 0, 1010410, 'Allocate', 'PRODUCTION_ALLOCATE', '', '/', '', '0', '', 2, 1, '', 1008371, '2020-03-18 08:06:55', 1010426, '2020-04-11 07:08:19', 'Production', 'PRODUCTION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010416, 0, 1010410, 'Assembly', 'PRODUCTION_ASSEMBLY', '', '/', '', '0', '', 2, 1, '', 1008371, '2020-03-18 08:07:26', 1010426, '2020-04-11 10:59:43', 'Production', 'PRODUCTION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010417, 0, 1008301, 'Product', 'PRODUCT', NULL, '/', '', '0', NULL, 1, 1, NULL, 1008371, '2020-03-18 08:07:55', 1008371, '2020-03-20 03:14:12', 'ROOT', 'ROOT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010418, 0, 1010417, 'BOM', 'PRODUCT_BOM', NULL, '/', '', '0', NULL, 2, 1, NULL, 1008371, '2020-03-18 08:09:18', 1008371, '2020-03-20 03:14:12', 'Product', 'PRODUCT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010419, 0, 1010417, 'Supplier Chaim', 'PRODUCT_CH', NULL, '/', '', '0', NULL, 2, 1, NULL, 1008371, '2020-03-18 08:10:19', 1008371, '2020-03-20 03:14:12', 'Product', 'PRODUCT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010420, 0, 1010417, 'Sale', 'PRODUCT_SALE', NULL, '/', '', '0', NULL, 2, 1, NULL, 1008371, '2020-03-18 08:11:04', 1008371, '2020-03-20 03:14:12', 'Product', 'PRODUCT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010421, 0, 1010404, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, NULL, 1008371, '2020-03-18 08:13:29', 1008371, '2020-03-20 03:14:12', 'Inquiry', 'INQUIRY', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010423, 0, 1010404, 'Decline', 'DECLINE', '', '/inquiry/decline', '', '1', '', 3, 1, '', 1008371, '2020-03-18 08:20:03', 1008371, '2020-03-20 06:31:03', 'Inquiry', 'INQUIRY', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010424, 0, 1010404, 'Approve', 'APPROVE', NULL, '/', '', '1', NULL, 3, 1, NULL, 1008371, '2020-03-18 08:21:03', 1008371, '2020-03-20 03:14:12', 'Inquiry', 'INQUIRY', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010425, 0, 1010405, 'Potential Customers', 'POTENTIAL_CUSTOMERS', NULL, '/', '', '0', NULL, 3, 1, NULL, 1008371, '2020-03-18 08:24:20', 1008371, '2020-03-20 03:14:12', 'Customer', 'SALES_CUSTOMER', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010426, 0, 1010405, 'Official Customers', 'OFFICIAL_CUSTOMERS', '', '/', '', '0', '', 3, 1, '', 1008371, '2020-03-18 08:26:58', 1008371, '2020-03-20 03:14:12', 'Customer', 'SALES_CUSTOMER', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010427, 0, 1010405, 'Trash', 'TRASH', NULL, '/', '', '0', NULL, 3, 1, NULL, 1008371, '2020-03-18 08:27:25', 1008371, '2020-03-20 03:14:12', 'Customer', 'SALES_CUSTOMER', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010429, 0, 1010406, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, NULL, 1008371, '2020-03-18 08:48:18', 1008371, '2020-03-20 03:14:12', 'Customer Account', 'SALES_CUSTOMER_ACCOUNT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010430, 0, 1010406, 'Edit', 'EDIT', NULL, '/', '', '1', NULL, 3, 1, NULL, 1008371, '2020-03-18 08:55:30', 1008371, '2020-03-20 03:14:12', 'Customer Account', 'SALES_CUSTOMER_ACCOUNT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010431, 0, 1010406, 'Renew', 'RENEW', NULL, '/', '', '1', NULL, 3, 1, NULL, 1008371, '2020-03-18 08:56:38', 1008371, '2020-03-20 03:14:12', 'Customer Account', 'SALES_CUSTOMER_ACCOUNT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010432, 0, 1010406, 'Freeze', 'FREEZE', NULL, '/', '', '1', NULL, 3, 1, NULL, 1008371, '2020-03-18 08:56:55', 1008371, '2020-03-20 03:14:12', 'Customer Account', 'SALES_CUSTOMER_ACCOUNT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010433, 0, 1010406, 'Unfreeze', 'UNFREEZE', NULL, '/', '', '1', NULL, 3, 1, NULL, 1008371, '2020-03-18 08:57:40', 1008371, '2020-03-20 03:14:12', 'Customer Account', 'SALES_CUSTOMER_ACCOUNT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010434, 0, 1010406, 'Password Reset', 'PASSWORD_RESET', NULL, '/', '', '1', NULL, 3, 1, NULL, 1008371, '2020-03-18 08:58:10', 1008371, '2020-03-20 03:14:12', 'Customer Account', 'SALES_CUSTOMER_ACCOUNT', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010435, 0, 1010425, 'View', 'VIEW', NULL, '/', '', '1', NULL, 4, 1, NULL, 1008371, '2020-03-18 08:58:56', 1008371, '2020-03-20 03:14:12', 'Potential Customers', 'POTENTIAL_CUSTOMERS', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010436, 0, 1010425, 'New', 'NEW', NULL, '/', '', '1', NULL, 4, 1, NULL, 1008371, '2020-03-18 08:59:18', 1008371, '2020-03-20 03:14:12', 'Potential Customers', 'POTENTIAL_CUSTOMERS', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010437, 0, 1010425, 'Edit', 'EDIT', '', '/customer/edit', '', '1', '', 4, 1, '', 1008371, '2020-03-18 08:59:35', 1008371, '2020-03-20 06:33:36', 'Potential Customers', 'POTENTIAL_CUSTOMERS', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010438, 0, 1010426, 'View', 'VIEW', NULL, '/', '', '1', NULL, 4, 1, NULL, 1008371, '2020-03-18 08:59:56', 1008371, '2020-03-20 03:14:12', 'Official Customers', 'OFFICIAL_CUSTOMERS', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010439, 0, 1010426, 'Edit', 'EDIT', NULL, '/', '', '1', NULL, 4, 1, NULL, 1008371, '2020-03-18 09:00:11', 1008371, '2020-03-20 03:14:12', 'Official Customers', 'OFFICIAL_CUSTOMERS', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010441, 0, 1010426, 'Delete', 'DELETE', NULL, '/', '', '1', NULL, 4, 1, NULL, 1008371, '2020-03-18 09:03:13', 1008371, '2020-03-20 03:14:12', 'Official Customers', 'OFFICIAL_CUSTOMERS', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010442, 0, 1010426, 'Create Account', 'CREATE_ACCOUNT', NULL, '/', '', '1', NULL, 4, 1, NULL, 1008371, '2020-03-18 09:03:40', 1008371, '2020-03-20 03:14:12', 'Official Customers', 'OFFICIAL_CUSTOMERS', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010443, 0, 1010427, 'View', 'VIEW', NULL, '/', '', '1', NULL, 4, 1, NULL, 1008371, '2020-03-18 09:04:09', 1008371, '2020-03-20 03:14:12', 'Trash', 'TRASH', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010445, 0, 1010401, 'View', 'VIEW', NULL, '/', '', '1', NULL, 2, 1, '', 1008371, '2020-03-20 01:47:53', 1008371, '2020-03-20 03:14:12', 'Dashboard', 'DASHBOARD', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010447, 0, 1010408, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1008371, '2020-03-20 02:11:34', 1008371, '2020-03-20 03:14:12', 'Employee', 'EMPLOYEE', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010448, 0, 1010408, 'Edit', 'EDIT', NULL, '/', '', '1', NULL, 3, 1, '', 1008371, '2020-03-20 02:41:20', 1008371, '2020-03-20 03:14:12', 'Employee', 'EMPLOYEE', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010449, 0, 1010409, 'View', 'VIEW', '', '/', '', '1', '', 3, 1, '', 1008371, '2020-03-20 02:41:35', 1008371, '2020-03-20 03:43:17', 'Position', 'POSITION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010450, 0, 1010409, 'New', 'NEW', NULL, '/', '', '1', NULL, 3, 1, '', 1008378, '2020-03-24 08:31:34', 1008378, '2020-03-24 08:31:34', 'Organization', 'ORGANIZATION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010451, 1, 1010411, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 09:14:33', 1010426, '2020-04-11 09:14:40', 'Production', 'PRODUCTION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010452, 1, 1010411, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 09:20:29', 1010426, '2020-04-11 09:35:36', 'Production', 'PRODUCTION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010453, 1, 1010412, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 09:21:03', 1010426, '2020-04-11 09:35:38', 'Production', 'PRODUCTION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010454, 1, 1010404, '44', '44', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 09:23:39', 1010426, '2020-04-11 09:24:31', 'Sales', 'SALES', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010455, 1, 1010411, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 09:35:51', 1010426, '2020-04-11 10:44:55', 'Factory', 'PRODUCTION_FACTORY', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010456, 1, 1010412, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 09:50:08', 1010426, '2020-04-11 10:44:47', 'Supplier', 'PRODUCTION_SUPPLIER', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010457, 1, 1010413, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 09:50:35', 1010426, '2020-04-11 10:44:40', 'Purchasing WH', 'PRODUCTION_PURCHASING_WH', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010458, 1, 1010411, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 10:56:52', 1010426, '2020-04-11 11:09:05', 'Factory', 'PRODUCTION_FACTORY', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010459, 1, 1010412, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 11:02:20', 1010426, '2020-04-11 11:08:21', 'Supplier', 'PRODUCTION_SUPPLIER', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010460, 1, 1010413, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 11:04:14', 1010426, '2020-04-11 11:08:32', 'purchasing WH', 'PRODUCTION_PURCHASING_WH', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010461, 1, 1010414, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 11:04:34', 1010426, '2020-04-11 11:08:39', 'Purchasing', 'PRODUCTION_PURCHASING', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010462, 1, 1010415, 'View	', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 11:04:49', 1010426, '2020-04-11 11:08:45', 'Allocate', 'PRODUCTION_ALLOCATE', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010463, 1, 1010416, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 11:04:59', 1010426, '2020-04-11 11:08:50', 'Assembly', 'PRODUCTION_ASSEMBLY', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010464, 0, 1010416, 'Finance', 'FINANCE', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 11:15:27', 1010426, '2020-04-11 11:15:27', 'Assembly', 'PRODUCTION_ASSEMBLY', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010465, 0, 1010416, 'View', 'VIEW', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-11 11:24:15', 1010426, '2020-04-11 11:24:15', 'Assembly', 'PRODUCTION_ASSEMBLY', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010466, 1, 1010409, 'NEW', 'NEW', NULL, '/', '', '1', NULL, 3, 1, '', 1008371, '2020-04-15 03:16:27', 1008371, '2020-04-15 03:26:35', 'Position', 'ORGANIZATION_POSITION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010467, 0, 1010418, 'New', 'New', NULL, '/', '', '1', NULL, 3, 1, '', 1008371, '2020-04-15 03:26:20', 1008371, '2020-04-15 03:26:20', 'BOM', 'PRODUCT_BOM', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010468, 0, 1010418, 'View', 'View', NULL, '/', '', '1', NULL, 3, 1, '', 1008371, '2020-04-15 03:26:44', 1008371, '2020-04-15 03:26:44', 'BOM', 'PRODUCT_BOM', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010469, 0, 1010419, 'View', 'View', NULL, '/', '', '1', NULL, 3, 1, '', 1008371, '2020-04-15 03:27:02', 1008371, '2020-04-15 03:27:02', 'Supplier Chaim', 'PRODUCT_CH', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010470, 0, 1010419, 'New', 'New', NULL, '/', '', '1', NULL, 3, 1, '', 1008371, '2020-04-15 03:27:20', 1008371, '2020-04-15 03:27:20', 'Supplier Chaim', 'PRODUCT_CH', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010471, 0, 1010420, 'New', 'New', NULL, '/', '', '1', NULL, 3, 1, '', 1008371, '2020-04-15 03:27:30', 1008371, '2020-04-15 03:27:30', 'Sale', 'PRODUCT_SALE', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010472, 0, 1010420, 'View', 'View', NULL, '/', '', '1', NULL, 3, 1, '', 1008371, '2020-04-15 03:27:46', 1008371, '2020-04-15 03:27:46', 'Sale', 'PRODUCT_SALE', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010473, 0, 1010418, 'Edit', 'Edit', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-15 03:31:35', 1010426, '2020-04-15 03:31:35', 'BOM', 'PRODUCT_BOM', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010474, 0, 1010419, 'Edit', 'Edit', NULL, '/', '', '1', NULL, 3, 1, '', 1010426, '2020-04-15 03:32:03', 1010426, '2020-04-15 03:32:03', 'Supplier Chaim', 'PRODUCT_CH', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010475, 0, 1008301, 'Setting', 'SETTING', '', '/', '', '0', '', 1, 1, '', 1000003, '2020-04-24 09:22:57', 1000006, '2020-04-24 11:44:50', 'ROOT', 'ROOT', '', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010476, 0, 1010475, 'Transfer Scooter', 'TRANSFER_SCOOTER', '', '/', '', '1', '', 2, 1, '', 1000003, '2020-04-24 09:24:42', 1000006, '2020-04-24 12:17:09', 'Setting', 'Setting', '', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010477, 0, 1010476, 'View', 'View', NULL, '/', '', '1', NULL, 3, 1, '', 1000003, '2020-04-24 09:25:19', 1000003, '2020-04-24 09:25:19', 'Transfer Scooter', 'Transfer Scooter', '', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010478, 1, 1010476, '123', '123', NULL, '/', '', '0', NULL, 3, 1, '', 1000006, '2020-04-24 09:54:20', 1000006, '2020-04-24 09:54:49', 'Transfer Scooter', 'Transfer Scooter', '', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010479, 1, 1010407, 'department', 'DEPARTMENT', NULL, '/', '', '0', NULL, 2, 1, '', 1000006, '2020-04-24 12:13:06', 1000006, '2020-04-24 12:15:25', 'Organization', 'ORGANIZATION', '0', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010480, 0, 1010476, 'Edit', 'Edit', NULL, '/', '', '1', NULL, 3, 1, '', 1000006, '2020-04-24 12:15:58', 1000006, '2020-04-24 12:15:58', 'TRANSFER_SCOOTER', 'TRANSFER_SCOOTER', '', NULL, NULL);
INSERT INTO `ope_sys_menu` VALUES (1010481, 0, 1010407, 'department', 'DEPARTMENT', NULL, '/', '', '0', NULL, 2, 1, '', 1000006, '2020-05-28 05:47:26', 1000006, '2020-05-28 05:47:26', 'Organization', 'ORGANIZATION', '0', NULL, NULL);

-- ----------------------------
-- Table structure for ope_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `ope_sys_role`;
CREATE TABLE `ope_sys_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `dr` int(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户id',
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `created_by` bigint(0) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_by` bigint(0) NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_idx1_role_code`(`role_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1012556 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_sys_role
-- ----------------------------
INSERT INTO `ope_sys_role` VALUES (1012559, 0, 0, 'root@redescooter.com', NULL, 'root@redescooter.com', 0, '2020-06-14 09:48:19', 0, '2020-06-14 09:48:19');

-- ----------------------------
-- Table structure for ope_sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `ope_sys_role_dept`;
CREATE TABLE `ope_sys_role_dept`  (
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(0) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_sys_role_dept
-- ----------------------------
INSERT INTO `ope_sys_role_dept` VALUES (1012559, 1000000);

-- ----------------------------
-- Table structure for ope_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `ope_sys_role_menu`;
CREATE TABLE `ope_sys_role_menu`  (
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(0) NOT NULL COMMENT '菜单ID',
  UNIQUE INDEX `role_id`(`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_sys_role_menu
-- ----------------------------
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1008301);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010401);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010403);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010404);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010405);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010406);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010407);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010408);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010409);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010410);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010411);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010412);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010413);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010414);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010415);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010416);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010417);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010418);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010419);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010420);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010421);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010423);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010424);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010425);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010426);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010427);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010429);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010430);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010431);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010432);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010433);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010434);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010435);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010436);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010437);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010438);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010439);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010441);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010442);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010443);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010445);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010447);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010448);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010449);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010450);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010464);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010465);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010467);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010468);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010469);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010470);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010471);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010472);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010473);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010474);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010475);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010476);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010477);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010480);
INSERT INTO `ope_sys_role_menu` VALUES (1012559, 1010481);

-- ----------------------------
-- Table structure for ope_sys_role_sales_cidy
-- ----------------------------
DROP TABLE IF EXISTS `ope_sys_role_sales_cidy`;
CREATE TABLE `ope_sys_role_sales_cidy`  (
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `city_id` bigint(0) NULL DEFAULT NULL COMMENT '城市ID',
  UNIQUE INDEX `ope_sys_user_sales_cidy_user_id_city_id_uindex`(`role_id`, `city_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户销售区域关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_sys_role_sales_cidy
-- ----------------------------
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000000);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000005);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000012);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000023);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000024);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000025);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000026);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000027);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000028);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000029);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000030);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000031);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000032);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000033);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000034);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000035);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000036);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000037);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000038);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000039);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000040);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000041);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000042);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000043);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000044);
INSERT INTO `ope_sys_role_sales_cidy` VALUES (1012559, 1000045);

-- ----------------------------
-- Table structure for ope_sys_staff
-- ----------------------------
DROP TABLE IF EXISTS `ope_sys_staff`;
CREATE TABLE `ope_sys_staff`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态：In-service在职，Leave离职',
  `sys_user_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '用户ID',
  `created_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_by` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ope_sys_staff_sys_user_id_uindex`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户员工表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_sys_staff
-- ----------------------------
INSERT INTO `ope_sys_staff` VALUES (1000003, 0, '1', 1000003, 0, '2020-06-14 09:48:19', 0, '2020-06-14 09:48:19', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for ope_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `ope_sys_user`;
CREATE TABLE `ope_sys_user`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `dept_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '组织部门ID',
  `org_staff_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '员工主键',
  `app_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用ID',
  `system_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统Id',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态 Normal,Lock,Cancel，Expired',
  `login_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_login_token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登录TOKEN',
  `last_login_ip` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登录IP地址',
  `activation_time` date NULL DEFAULT NULL COMMENT '账户激活时间',
  `expire_date` date NULL DEFAULT NULL COMMENT '账户到期时间',
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_sys_user
-- ----------------------------
INSERT INTO `ope_sys_user` VALUES (1000003, 0, 1000000, 0, '4', 'REDE_SES', '214ac9c07191d0646839138808f7ee46', '31822', '1', 'root@redescooter.com', '2020-06-14 09:50:02', 'c90e2c5e3321428d90ae89cb77145354', '192.168.2.220', '2020-06-14', NULL, 0, '2020-06-14 09:48:19', 0, '2020-06-14 09:50:02', '1', NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for ope_sys_user_profile
-- ----------------------------
DROP TABLE IF EXISTS `ope_sys_user_profile`;
CREATE TABLE `ope_sys_user_profile`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `dr` int(0) NULL DEFAULT 0 COMMENT '逻辑删除标识 0正常 1删除',
  `repair_shop_Id` bigint(0) NOT NULL DEFAULT 0 COMMENT '维修店ID',
  `sys_user_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '用户ID',
  `picture` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片',
  `first_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名',
  `last_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓',
  `full_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全名',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `country_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码如+86',
  `tel_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号',
  `gender` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别 Male 男 Female 女',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `place_birth` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生地',
  `address_country_code` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址国家编码',
  `address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `address_bureau` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办公地点Id(具体数据可查看 AddressBureauEnums )',
  `certificate_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型，1身份证，2驾驶证，3护照',
  `certificate_negative_annex` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件正面',
  `certificate_positive_annex` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件背面',
  `join_date` datetime(0) NULL DEFAULT NULL COMMENT '入职加入时间',
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
-- Records of ope_sys_user_profile
-- ----------------------------
INSERT INTO `ope_sys_user_profile` VALUES (1000003, 0, 0, 1000003, NULL, 'ROOT', 'ROOT', 'ROOT ROOT', 'root@redescooter.com', '33', '30690', NULL, '2020-06-18', NULL, NULL, NULL, '1000000', NULL, NULL, NULL, NULL, 0, '2020-06-14 09:48:19', 0, '2020-06-14 09:48:19', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for ope_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ope_sys_user_role`;
CREATE TABLE `ope_sys_user_role`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_sys_user_role
-- ----------------------------
INSERT INTO `ope_sys_user_role` VALUES (1000003, 1012559);

-- ----------------------------
-- Table structure for ope_whse
-- ----------------------------
DROP TABLE IF EXISTS `ope_whse`;
CREATE TABLE `ope_whse`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dr` int(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除字段',
  `name` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `code` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类编码',
  `product_range` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品范围',
  `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库类型，1采购，2生产，3销售',
  `remark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `country_id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家',
  `city_id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市id',
  `area_id` int(0) NULL DEFAULT NULL COMMENT '区域id',
  `revision` int(0) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(0) NULL DEFAULT 0 COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint(0) NULL DEFAULT 0 COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `def1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20, 0) NULL DEFAULT 0 COMMENT '冗余字段'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '仓库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ope_whse
-- ----------------------------
INSERT INTO `ope_whse` VALUES (1000000, 0, '采购仓库', 'REDE-0001', NULL, '1', '', '', NULL, NULL, 0, 1000000, NULL, 1000000, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `ope_whse` VALUES (1000001, 0, '调拨仓库', 'REDE-0002', NULL, '2', '', '', NULL, NULL, 0, 1000000, NULL, 1000000, '2020-03-30 11:21:16', NULL, NULL, NULL, NULL, 0);
INSERT INTO `ope_whse` VALUES (1000002, 0, '组装仓库', 'REDE-0003', NULL, '3', '', '', NULL, NULL, 0, 1000000, NULL, 1000000, '2020-03-30 11:21:16', NULL, NULL, NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
