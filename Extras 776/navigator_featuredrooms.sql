/*
Navicat MySQL Data Transfer

Source Server         : hihihihi
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : orion

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2017-12-29 16:57:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for navigator_featuredrooms
-- ----------------------------
DROP TABLE IF EXISTS `navigator_featuredrooms`;
CREATE TABLE `navigator_featuredrooms` (
  `id` int(11) NOT NULL DEFAULT '0',
  `banner_type` enum('big','small') NOT NULL DEFAULT 'big',
  `caption` varchar(50) NOT NULL DEFAULT '',
  `description` varchar(50) NOT NULL DEFAULT '',
  `image` varchar(50) NOT NULL DEFAULT '',
  `image_type` enum('internal','external') NOT NULL DEFAULT 'internal',
  `room_id` int(11) NOT NULL DEFAULT '0',
  `category_id` int(11) NOT NULL,
  `enabled` enum('false','true') NOT NULL DEFAULT 'true',
  `recommended` enum('0','1') NOT NULL DEFAULT '1',
  `type` varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of navigator_featuredrooms
-- ----------------------------
INSERT INTO `navigator_featuredrooms` VALUES ('1', 'big', 'Horunge', 'Jag är king', '/room_images/cf.png', 'external', '0', '0', 'true', '0', 'category');
INSERT INTO `navigator_featuredrooms` VALUES ('2', 'small', 'Hora', 'Kuksugare', '/room_images/cf.png', 'internal', '2', '1', 'true', '0', '');
