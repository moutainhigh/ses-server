/*
 Navicat Premium Data Transfer

 Source Server         : home
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 192.168.1.100:3306
 Source Schema         : website

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 04/01/2021 03:14:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for site_contact_us
-- ----------------------------
DROP TABLE IF EXISTS `site_contact_us`;
CREATE TABLE `site_contact_us` (
  `id` bigint(19) NOT NULL COMMENT '主键',
  `dr` int(2) DEFAULT '0' COMMENT '删除标志',
  `email` varchar(64) DEFAULT NULL COMMENT '客户邮箱',
  `first_name` varchar(64) DEFAULT NULL COMMENT '名',
  `last_name` varchar(64) DEFAULT NULL COMMENT '姓',
  `full_name` varchar(64) DEFAULT NULL COMMENT '全名',
  `telephone` varchar(64) DEFAULT NULL COMMENT '电话',
  `country` bigint(19) DEFAULT NULL COMMENT '国家Id',
  `country_code` varchar(64) DEFAULT NULL COMMENT '国家代码',
  `country_name` varchar(32) DEFAULT NULL COMMENT '国家名称',
  `city_name` varchar(32) DEFAULT NULL COMMENT '城市名称',
  `district` bigint(19) DEFAULT NULL COMMENT '区域Id',
  `district_name` varchar(255) DEFAULT NULL COMMENT '区域编码',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `frequency` int(11) DEFAULT NULL COMMENT '联系次数',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='联系我们';

-- ----------------------------
-- Table structure for site_contact_us_trace
-- ----------------------------
DROP TABLE IF EXISTS `site_contact_us_trace`;
CREATE TABLE `site_contact_us_trace` (
  `id` bigint(19) NOT NULL COMMENT '主键',
  `dr` int(2) DEFAULT '0' COMMENT '删除标志',
  `contact_us_id` bigint(19) NOT NULL COMMENT '联系我们id',
  `email` varchar(64) DEFAULT NULL COMMENT '客户邮箱',
  `first_name` varchar(64) DEFAULT NULL COMMENT '名',
  `last_name` varchar(64) DEFAULT NULL COMMENT '姓',
  `full_name` varchar(64) DEFAULT NULL COMMENT '全名',
  `telephone` varchar(64) DEFAULT NULL COMMENT '电话',
  `country_name` varchar(64) DEFAULT NULL COMMENT '国家名称',
  `city_name` varchar(32) DEFAULT NULL COMMENT '城市名称',
  `district_name` varchar(32) DEFAULT NULL COMMENT '区域编码',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='联系我们历史记录';

-- ----------------------------
-- Table structure for site_customer
-- ----------------------------
DROP TABLE IF EXISTS `site_customer`;
CREATE TABLE `site_customer` (
  `id` bigint(19) NOT NULL COMMENT '主建',
  `dr` int(2) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `status` varchar(32) DEFAULT NULL COMMENT '状态,-1意向客户，1积极客户，0正式客户',
  `country_code` varchar(64) DEFAULT NULL COMMENT '国家编号，中国 +86',
  `customer_source` varchar(32) DEFAULT NULL COMMENT '客户来源渠道 ,1官网,2email,3电话',
  `sales_id` bigint(19) DEFAULT NULL COMMENT '销售员ID',
  `industry_type` varchar(24) DEFAULT NULL COMMENT '客户行业类型，1餐厅，2快递',
  `customer_type` varchar(24) DEFAULT NULL COMMENT '客户类型，1企业，2个人',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编码',
  `customer_head_picture` varchar(128) DEFAULT NULL COMMENT '客户头像',
  `customer_first_name` varchar(128) DEFAULT NULL COMMENT '客户名字',
  `customer_last_name` varchar(128) DEFAULT NULL COMMENT '客户姓氏',
  `customer_full_name` varchar(256) DEFAULT NULL COMMENT '客户全名',
  `company_name` varchar(128) DEFAULT NULL COMMENT '企业名称',
  `company_logo` varchar(128) DEFAULT NULL COMMENT '客户logo',
  `country_name` varchar(32) DEFAULT NULL COMMENT '国家名称',
  `city_name` varchar(32) DEFAULT NULL COMMENT '城市名称',
  `area_name` bigint(19) DEFAULT NULL COMMENT '区域',
  `postcode` varchar(255) DEFAULT NULL COMMENT '区域邮编',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `place_id` varchar(256) DEFAULT NULL COMMENT '地点编号',
  `longitude` decimal(32,10) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(32,10) DEFAULT NULL COMMENT '纬度',
  `contact_first_name` varchar(128) DEFAULT NULL COMMENT '联系人名字',
  `contact_last_name` varchar(128) DEFAULT NULL COMMENT '联系人姓氏',
  `contact_full_name` varchar(128) DEFAULT NULL COMMENT '联系人全名',
  `telephone` varchar(32) DEFAULT NULL COMMENT '电话',
  `email` varchar(64) NOT NULL COMMENT '邮件',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `purchased_scooter_qty` int(20) DEFAULT NULL COMMENT '已购买车辆数量',
  `assignation_scooter_qty` int(11) DEFAULT NULL COMMENT '已分配车辆数',
  `certificate_type` varchar(24) DEFAULT NULL COMMENT '证件类型1身份证，2驾驶证，3护照',
  `certificate_positive_annex` varchar(128) DEFAULT NULL COMMENT '证件正面图片',
  `certificate_negative_annex` varchar(128) DEFAULT NULL COMMENT '证件反面图片',
  `business_license_num` varchar(64) DEFAULT NULL COMMENT '营业执照编号',
  `business_license_annex` varchar(128) DEFAULT NULL COMMENT '营业执照图片',
  `invoice_num` varchar(128) DEFAULT NULL COMMENT '发票编号',
  `invoice_annex` varchar(128) DEFAULT NULL COMMENT '发票附件',
  `contract_annex` varchar(255) DEFAULT NULL COMMENT '合同附件',
  `account_flag` varchar(32) DEFAULT NULL COMMENT '账号使用标识，即激活使用过1，未激活未使用0',
  `cardholder` varchar(64) DEFAULT NULL COMMENT '持卡人',
  `cvv` varchar(64) DEFAULT NULL COMMENT 'cvv',
  `card_num` varchar(64) DEFAULT NULL COMMENT '卡号',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户表';

-- ----------------------------
-- Table structure for site_distributor
-- ----------------------------
DROP TABLE IF EXISTS `site_distributor`;
CREATE TABLE `site_distributor` (
  `id` bigint(19) NOT NULL COMMENT '主键',
  `dr` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除 0正常 1删除',
  `status` int(11) NOT NULL COMMENT '状态 1启用中 2未启用',
  `code` varchar(255) NOT NULL COMMENT '门店编码',
  `name` varchar(255) NOT NULL COMMENT '门店名称',
  `logo_url` varchar(1024) DEFAULT NULL COMMENT '门店logo',
  `country_code` varchar(128) DEFAULT NULL COMMENT '国家代码 86中国 33法国',
  `tel` varchar(255) NOT NULL COMMENT '电话',
  `email` varchar(255) NOT NULL COMMENT '邮件地址',
  `address` varchar(1024) NOT NULL COMMENT '地址',
  `longitude` varchar(255) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(255) DEFAULT NULL COMMENT '纬度',
  `cp` varchar(255) NOT NULL COMMENT '邮编',
  `city` varchar(255) NOT NULL COMMENT '城市',
  `area` varchar(255) NOT NULL COMMENT '地区',
  `contract_url` varchar(1024) NOT NULL COMMENT '合同url',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `type` varchar(128) NOT NULL COMMENT '门店类型,1销售门店,2维修仓库，3销售及维修',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(128) DEFAULT NULL COMMENT '冗余字段1',
  `def2` varchar(128) DEFAULT NULL COMMENT '冗余字段2',
  `def3` varchar(128) DEFAULT NULL COMMENT '冗余字段3',
  `def4` varchar(128) DEFAULT NULL COMMENT '冗余字段4',
  `def5` varchar(128) DEFAULT NULL COMMENT '冗余字段5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='经销商表';

-- ----------------------------
-- Table structure for site_order
-- ----------------------------
DROP TABLE IF EXISTS `site_order`;
CREATE TABLE `site_order` (
  `id` bigint(19) NOT NULL COMMENT '主建',
  `dr` int(2) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `order_no` varchar(64) DEFAULT NULL COMMENT '订单号',
  `customer_id` bigint(19) DEFAULT NULL COMMENT '客户id',
  `customer_source` varchar(32) DEFAULT NULL COMMENT '客户来源渠道 官网/email/电话',
  `sales_id` bigint(19) DEFAULT NULL COMMENT '销售员id',
  `status` varchar(32) DEFAULT NULL COMMENT '状态,1进行中，2支付中，3取消退款，4已完成，5关闭',
  `order_type` varchar(32) DEFAULT NULL COMMENT '订单类型，1销售，2租赁',
  `product_id` bigint(19) DEFAULT NULL COMMENT '产品Id ',
  `product_colour` varchar(100) DEFAULT NULL COMMENT '产品颜色 ',
  `full_name` varchar(256) DEFAULT NULL COMMENT '客户名字',
  `company_name` varchar(32) DEFAULT NULL COMMENT '公司名称',
  `country_name` varchar(32) DEFAULT NULL COMMENT '国家名称',
  `city_name` varchar(32) DEFAULT NULL COMMENT '城市名称',
  `postcode` varchar(255) DEFAULT NULL COMMENT '区域邮编',
  `address` varchar(255) DEFAULT NULL COMMENT '客户地址',
  `delivery_type` varchar(255) DEFAULT NULL COMMENT '提货方式',
  `freight` decimal(10,2) DEFAULT NULL COMMENT '运费',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '产品单价',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '单据总价',
  `amount_paid` decimal(10,2) DEFAULT NULL COMMENT '已付金额',
  `amount_obligation` decimal(10,2) DEFAULT NULL COMMENT '待付款金额',
  `prepaid_deposit` decimal(10,2) DEFAULT NULL COMMENT '预付定金',
  `amount_discount` decimal(10,2) DEFAULT NULL COMMENT '优惠抵扣金额',
  `payment_type_id` bigint(19) DEFAULT NULL COMMENT '支付方式',
  `pay_status` varchar(32) DEFAULT NULL COMMENT '支付状态',
  `scooter_quantity` int(11) DEFAULT NULL COMMENT '需求车辆数',
  `etd_delivery_time` datetime DEFAULT NULL COMMENT '预计交货时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售订单';

-- ----------------------------
-- Table structure for site_order_b
-- ----------------------------
DROP TABLE IF EXISTS `site_order_b`;
CREATE TABLE `site_order_b` (
  `id` bigint(19) NOT NULL COMMENT '主建',
  `dr` int(2) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `order_id` bigint(19) DEFAULT NULL COMMENT '销售订单Id',
  `product_id` bigint(19) DEFAULT NULL COMMENT '产品Id',
  `parts_id` decimal(10,2) DEFAULT NULL COMMENT '配件ID',
  `parts_qty` int(11) DEFAULT NULL COMMENT '配件数量',
  `parts_price` decimal(10,2) DEFAULT NULL COMMENT '配件单价',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售订单子表';

-- ----------------------------
-- Table structure for site_parts
-- ----------------------------
DROP TABLE IF EXISTS `site_parts`;
CREATE TABLE `site_parts` (
  `id` bigint(19) NOT NULL COMMENT '主键',
  `dr` int(2) NOT NULL COMMENT '逻辑删除',
  `status` varchar(32) NOT NULL COMMENT '状态',
  `parts_type` varchar(16) NOT NULL COMMENT '类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery',
  `sec` varchar(32) NOT NULL COMMENT '项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。',
  `parts_number` varchar(32) NOT NULL COMMENT '部品号',
  `cn_name` varchar(32) DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) DEFAULT NULL COMMENT '英文名称',
  `parts_qty` int(11) DEFAULT NULL COMMENT '部品数量',
  `production_cycle` varchar(32) DEFAULT NULL COMMENT '生产周期',
  `cost` varchar(32) DEFAULT NULL COMMENT '成本',
  `procurement_source` varchar(32) DEFAULT NULL COMMENT '采购来源',
  `supplier_id` bigint(19) DEFAULT NULL COMMENT '供应商',
  `dwg` varchar(128) DEFAULT NULL COMMENT '图纸',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `id_class` tinyint(1) DEFAULT NULL COMMENT '是否有唯一编码',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配件表';

-- ----------------------------
-- Table structure for site_parts_price
-- ----------------------------
DROP TABLE IF EXISTS `site_parts_price`;
CREATE TABLE `site_parts_price` (
  `id` bigint(19) NOT NULL COMMENT '主键',
  `dr` int(2) NOT NULL COMMENT '逻辑删除',
  `status` varchar(32) NOT NULL COMMENT '状态，1正常，-1失效',
  `parts_id` bigint(19) DEFAULT NULL COMMENT '配件Id',
  `price` decimal(10,2) DEFAULT NULL COMMENT '销售价格 浮点型价格',
  `effective_time` datetime DEFAULT NULL COMMENT '生效时间 默认当前生效',
  `currency_type` varchar(32) DEFAULT NULL COMMENT '货币类型 如英镑，美元，人民币',
  `currency_unit` varchar(32) DEFAULT NULL COMMENT '货币单位 如¥，$，€，	￡',
  `standard_currency` varchar(32) DEFAULT NULL COMMENT '标准货币 用户货币转换',
  `amount_discount` decimal(10,2) DEFAULT NULL COMMENT '优惠抵扣金额',
  `country_code` varchar(128) NOT NULL COMMENT '国家编码 当前销售国家',
  `country_city` varchar(128) NOT NULL COMMENT '国家城市 当然销售国家的城市',
  `country_language` varchar(64) NOT NULL COMMENT '国家语言 当前销售国家语言',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,2) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配件报价表';

-- ----------------------------
-- Table structure for site_payment_type
-- ----------------------------
DROP TABLE IF EXISTS `site_payment_type`;
CREATE TABLE `site_payment_type` (
  `id` bigint(19) NOT NULL COMMENT '主建',
  `dr` int(2) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `status` varchar(32) DEFAULT NULL COMMENT '状态,1进行中，2支付中，3取消退款，4已完成，5关闭',
  `payment_name` varchar(32) DEFAULT NULL COMMENT '订单类型，1销售，2租赁',
  `payment_code` varchar(100) DEFAULT NULL COMMENT '产品颜色 ',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付方式';

-- ----------------------------
-- Table structure for site_product
-- ----------------------------
DROP TABLE IF EXISTS `site_product`;
CREATE TABLE `site_product` (
  `id` bigint(19) NOT NULL COMMENT '主键 主键',
  `dr` int(2) NOT NULL COMMENT '逻辑删除',
  `status` varchar(32) NOT NULL COMMENT '状态 up上架，down下架(默认)',
  `product_type` int(11) NOT NULL COMMENT '产品类型 如1整车，2组装套件，电池',
  `product_code` varchar(32) DEFAULT NULL COMMENT '产品编码',
  `cn_name` varchar(32) DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) DEFAULT NULL COMMENT '英文名称',
  `product_model_id` bigint(19) DEFAULT NULL COMMENT '产品型号ID',
  `product_colour_id` bigint(19) DEFAULT NULL COMMENT '产品颜色ID',
  `min_battery_num` int(10) NOT NULL DEFAULT '1' COMMENT '最少电池数',
  `after_sales_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否支持售后服务',
  `added_services_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否支持增值服务',
  `mater_parameter` varchar(32) DEFAULT NULL COMMENT '产品参数 存储JSON',
  `other_parameter` varchar(32) DEFAULT NULL COMMENT '其他参数 存储JSON',
  `speed` varchar(64) DEFAULT NULL COMMENT '速度',
  `power` varchar(64) DEFAULT NULL COMMENT '功率',
  `charge_cycle` varchar(64) DEFAULT NULL COMMENT '充电周期',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品表';

-- ----------------------------
-- Table structure for site_product_class
-- ----------------------------
DROP TABLE IF EXISTS `site_product_class`;
CREATE TABLE `site_product_class` (
  `id` bigint(19) NOT NULL COMMENT '主键 主键',
  `dr` int(2) NOT NULL COMMENT '逻辑删除',
  `status` varchar(32) NOT NULL COMMENT '状态,1正常，-1失效',
  `product_class_name` varchar(10) NOT NULL COMMENT '产品种类名称',
  `product_class_code` varchar(10) NOT NULL COMMENT '产品种类编码',
  `cn_name` varchar(32) DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) DEFAULT NULL COMMENT '英文名称',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品种类表';

-- ----------------------------
-- Table structure for site_product_colour
-- ----------------------------
DROP TABLE IF EXISTS `site_product_colour`;
CREATE TABLE `site_product_colour` (
  `id` bigint(19) NOT NULL COMMENT '主键',
  `dr` int(2) NOT NULL COMMENT '逻辑删除',
  `status` varchar(32) NOT NULL COMMENT '状态,1正常，-1失效',
  `colour_range` varchar(32) NOT NULL COMMENT '颜色使用范围,1整车，-1配件',
  `colour_name` varchar(32) DEFAULT NULL COMMENT '颜色名称',
  `colour_code` varchar(32) DEFAULT NULL COMMENT '颜色编码',
  `colour_RGB` varchar(32) DEFAULT NULL COMMENT '颜色RGB值',
  `colour_16` varchar(32) DEFAULT NULL COMMENT '颜色16进制颜色编码',
  `cn_name` varchar(32) DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) DEFAULT NULL COMMENT '英文名称',
  `product_id` varchar(32) NOT NULL COMMENT '产品id',
  `pictures` varchar(64) DEFAULT NULL COMMENT '图片',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品颜色关系表';

-- ----------------------------
-- Table structure for site_product_model
-- ----------------------------
DROP TABLE IF EXISTS `site_product_model`;
CREATE TABLE `site_product_model` (
  `id` bigint(19) NOT NULL COMMENT '主键 主键',
  `dr` int(2) NOT NULL COMMENT '逻辑删除',
  `status` varchar(32) NOT NULL COMMENT '状态,1正常，-1失效',
  `product_class_id` varchar(10) NOT NULL COMMENT '产品种类主建',
  `product_model_code` varchar(32) DEFAULT NULL COMMENT '产品类型单项编码',
  `product_model_name` varchar(32) DEFAULT NULL COMMENT '产品类型单项名称',
  `cn_name` varchar(32) DEFAULT NULL COMMENT '中文名称',
  `fr_name` varchar(32) DEFAULT NULL COMMENT '法文名称',
  `en_name` varchar(32) DEFAULT NULL COMMENT '英文名称',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,6) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品型号表';

-- ----------------------------
-- Table structure for site_product_parts
-- ----------------------------
DROP TABLE IF EXISTS `site_product_parts`;
CREATE TABLE `site_product_parts` (
  `id` bigint(19) NOT NULL COMMENT '主键 主键',
  `dr` int(2) NOT NULL COMMENT '逻辑删除',
  `status` varchar(32) NOT NULL COMMENT '状态,1正常，-1失效',
  `parts_id` varchar(64) DEFAULT NULL COMMENT '配件ID',
  `product_id` bigint(19) DEFAULT NULL COMMENT '产品ID',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品配件关系表';

-- ----------------------------
-- Table structure for site_product_price
-- ----------------------------
DROP TABLE IF EXISTS `site_product_price`;
CREATE TABLE `site_product_price` (
  `id` bigint(19) NOT NULL COMMENT '主键',
  `dr` int(2) NOT NULL COMMENT '逻辑删除',
  `status` varchar(32) NOT NULL COMMENT '状态,1正常，-1失效',
  `product_id` bigint(19) DEFAULT NULL COMMENT '产品Id',
  `price_type` varchar(32) NOT NULL COMMENT '状态,0全额付款，1分期付款',
  `installment_time` varchar(32) NOT NULL COMMENT '分期付款时间数，单位month',
  `price` decimal(10,2) DEFAULT NULL COMMENT '销售价格 浮点型价格',
  `effective_time` datetime DEFAULT NULL COMMENT '生效时间 默认当前生效',
  `currency_type` varchar(32) DEFAULT NULL COMMENT '货币类型 如英镑，美元，人民币',
  `currency_unit` varchar(32) DEFAULT NULL COMMENT '货币单位 如¥，$，€，	￡',
  `standard_currency` varchar(32) DEFAULT NULL COMMENT '标准货币 用户货币转换',
  `prepaid_deposit` decimal(10,2) DEFAULT NULL COMMENT '预付定金',
  `amount_discount` decimal(10,2) DEFAULT NULL COMMENT '优惠抵扣金额',
  `country_code` varchar(128) NOT NULL COMMENT '国家编码 当前销售国家',
  `country_city` varchar(128) NOT NULL COMMENT '国家城市 当然销售国家的城市',
  `country_language` varchar(64) NOT NULL COMMENT '国家语言 当前销售国家语言',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(32) DEFAULT NULL COMMENT '冗余字段',
  `def6` decimal(10,2) DEFAULT NULL COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品报价表';

-- ----------------------------
-- Table structure for site_user
-- ----------------------------
DROP TABLE IF EXISTS `site_user`;
CREATE TABLE `site_user` (
  `id` bigint(19) NOT NULL COMMENT '主键',
  `dr` int(2) DEFAULT '0' COMMENT '逻辑删除标识 0正常 1删除',
  `app_id` varchar(32) DEFAULT NULL COMMENT '应用ID',
  `system_id` varchar(32) DEFAULT NULL COMMENT '系统Id',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐',
  `status` varchar(32) DEFAULT NULL COMMENT '状态 1:Normal，2:Lock,Cancel，3:Expired',
  `login_name` varchar(32) NOT NULL COMMENT '登录名',
  `customer_id` varchar(32) NOT NULL COMMENT '客户ID',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_token` varchar(64) DEFAULT NULL COMMENT '最后登录TOKEN',
  `last_login_ip` varchar(16) DEFAULT NULL COMMENT '最后登录IP地址',
  `activation_time` date DEFAULT NULL COMMENT '账户激活时间',
  `expire_date` date DEFAULT NULL COMMENT '账户到期时间',
  `synchronize_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同步',
  `revision` int(11) NOT NULL COMMENT '乐观锁',
  `created_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `def1` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def2` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def3` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def5` varchar(64) DEFAULT NULL COMMENT '冗余字段',
  `def6` double(20,0) DEFAULT '0' COMMENT '冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='官网用户表';

SET FOREIGN_KEY_CHECKS = 1;
