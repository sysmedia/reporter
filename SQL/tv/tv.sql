/*
Navicat MySQL Data Transfer

Source Server         : dagong
Source Server Version : 50560
Source Host           : 192.168.1.90:3306
Source Database       : tv

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2019-04-17 15:03:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ic_user_obtain_count
-- ----------------------------
DROP TABLE IF EXISTS `ic_user_obtain_count`;
CREATE TABLE `ic_user_obtain_count` (
  `channel_id` varchar(255) DEFAULT NULL,
  `brand_id` varchar(255) DEFAULT NULL,
  `top_cat_name` varchar(255) DEFAULT NULL,
  `mid_cat_name` varchar(255) DEFAULT NULL,
  `leaf_cat_name` varchar(255) DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `obtain_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ic_user_obtain_count
-- ----------------------------

-- ----------------------------
-- Table structure for ic_user_register_count
-- ----------------------------
DROP TABLE IF EXISTS `ic_user_register_count`;
CREATE TABLE `ic_user_register_count` (
  `day` varchar(255) DEFAULT NULL,
  `register_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ic_user_register_count
-- ----------------------------

-- ----------------------------
-- Table structure for ic_user_used_count
-- ----------------------------
DROP TABLE IF EXISTS `ic_user_used_count`;
CREATE TABLE `ic_user_used_count` (
  `market_id` varchar(255) DEFAULT NULL,
  `channel_id` varchar(255) DEFAULT NULL,
  `brand_id` varchar(255) DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `top_cat_name` varchar(255) DEFAULT NULL,
  `mid_cat_name` varchar(255) DEFAULT NULL,
  `leaf_cat_name` varchar(255) DEFAULT NULL,
  `used_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ic_user_used_count
-- ----------------------------
INSERT INTO `ic_user_used_count` VALUES ('1', '1', '1', '2019-04-17', '1', '1', '1', '1');

-- ----------------------------
-- Table structure for ic_user_view_count
-- ----------------------------
DROP TABLE IF EXISTS `ic_user_view_count`;
CREATE TABLE `ic_user_view_count` (
  `day` varchar(255) DEFAULT NULL,
  `channel` varchar(255) DEFAULT NULL,
  `view_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ic_user_view_count
-- ----------------------------
INSERT INTO `ic_user_view_count` VALUES ('2019-04-11', '13', '82');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-13', '13', '4');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-17', '13', '116');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-15', '13', '60');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-02', '2', '192');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-04', '2', '3');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-01', '2', '7');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-03', '2', '200');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-08', '13', '141');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-04', '13', '4');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-09', '13', '149');
INSERT INTO `ic_user_view_count` VALUES ('2019-03-27', '2', '39');
INSERT INTO `ic_user_view_count` VALUES ('2019-03-29', '2', '4');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-16', '13', '97');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-12', '13', '16788');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-10', '13', '150');
INSERT INTO `ic_user_view_count` VALUES ('2019-04-14', '13', '8');

-- ----------------------------
-- Table structure for t_media_category_count
-- ----------------------------
DROP TABLE IF EXISTS `t_media_category_count`;
CREATE TABLE `t_media_category_count` (
  `sm` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `shop` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `top_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `mid_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `leaf_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `category` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `saleCount` double DEFAULT NULL,
  `saleamount` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_media_category_count
-- ----------------------------

-- ----------------------------
-- Table structure for t_media_compete_category_count
-- ----------------------------
DROP TABLE IF EXISTS `t_media_compete_category_count`;
CREATE TABLE `t_media_compete_category_count` (
  `sm` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `shop` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `day` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `top_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `mid_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `leaf_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `category` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `compete_category` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `saleCount` double DEFAULT NULL,
  `saleamount` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_media_compete_category_count
-- ----------------------------
