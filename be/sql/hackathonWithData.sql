/*
 Navicat Premium Data Transfer

 Source Server         : MyComputer
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : hackathon

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 29/11/2020 03:44:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administertask
-- ----------------------------
DROP TABLE IF EXISTS `administertask`;
CREATE TABLE `administertask`  (
  `administerTaskId` int(11) NOT NULL AUTO_INCREMENT,
  `taskId` int(11) NULL DEFAULT NULL,
  `userId` int(11) NULL DEFAULT NULL,
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `receiveMsg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `checkMsg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `checkTime` datetime(0) NULL DEFAULT NULL,
  `receiveTime` datetime(0) NULL DEFAULT NULL,
  `finishTime` datetime(0) NULL DEFAULT NULL,
  `personTaskStatus` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`administerTaskId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administertask
-- ----------------------------
INSERT INTO `administertask` VALUES (1, 1, 1, '打扫宿舍卫生', NULL, NULL, '2020-11-29 03:40:27', NULL, NULL, '2020-11-29 03:42:44', 'FINISHED');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `departmentId` int(11) NOT NULL AUTO_INCREMENT,
  `organizationId` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT NULL,
  `higherId` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`departmentId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, 1, '舍长', 1, -1);
INSERT INTO `department` VALUES (2, 1, '副舍长', 2, 1);
INSERT INTO `department` VALUES (3, 1, '舍员', 3, 2);
INSERT INTO `department` VALUES (4, 1, '舍员的儿子', 4, 3);

-- ----------------------------
-- Table structure for dotask
-- ----------------------------
DROP TABLE IF EXISTS `dotask`;
CREATE TABLE `dotask`  (
  `doTaskId` int(11) NOT NULL AUTO_INCREMENT,
  `taskId` int(11) NULL DEFAULT NULL,
  `userId` int(11) NULL DEFAULT NULL,
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `receiveMsg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `checkMsg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `upload` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `checkTime` datetime(0) NULL DEFAULT NULL,
  `receiveTime` datetime(0) NULL DEFAULT NULL,
  `finishTime` datetime(0) NULL DEFAULT NULL,
  `personTaskStatus` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`doTaskId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dotask
-- ----------------------------
INSERT INTO `dotask` VALUES (1, 1, 2, '打扫天空卫生', '打扫天空', '天空真干净', '我打扫好了我天空的卫生', '2020-11-29 03:40:59', '2020-11-29 03:42:44', '2020-11-29 03:41:44', '1970-01-01 08:00:00', 'FINISHED');

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization`  (
  `organizationId` int(11) NOT NULL AUTO_INCREMENT,
  `administerId` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`organizationId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO `organization` VALUES (1, 1, '陶二421', '2020-11-29 03:37:24');

-- ----------------------------
-- Table structure for statuslog
-- ----------------------------
DROP TABLE IF EXISTS `statuslog`;
CREATE TABLE `statuslog`  (
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `taskId` int(11) NULL DEFAULT NULL,
  `userId` int(11) NULL DEFAULT NULL,
  `preStatus` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `afterStatus` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `changeTime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`logId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `taskId` int(11) NOT NULL AUTO_INCREMENT,
  `superTaskId` int(11) NULL DEFAULT NULL,
  `organizationId` int(11) NULL DEFAULT NULL,
  `createUserId` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `deadLine` datetime(0) NULL DEFAULT NULL,
  `endTime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`taskId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES (1, -1, 1, 1, '打扫宿舍卫生', '打扫宿舍卫生', 'FINISHED', '2020-11-29 03:40:27', '1970-01-01 08:00:00', '2020-11-29 03:42:56');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `realName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `lastLoginTime` datetime(0) NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `departmentId` int(11) NULL DEFAULT NULL,
  `organizationId` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'wwf', '王万峰', '4QrcOUm6Wau+VuBX8g+IPg==', '2020-11-29 03:37:24', '2020-11-29 03:42:24', '王万峰的介绍', 'https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg', 1, 1);
INSERT INTO `user` VALUES (2, 'zr', '朱睿', '4QrcOUm6Wau+VuBX8g+IPg==', '2020-11-29 03:39:09', '2020-11-29 03:41:09', '朱睿的介绍', 'https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg', 2, 1);
INSERT INTO `user` VALUES (3, 'zhy', '张洪胤', '4QrcOUm6Wau+VuBX8g+IPg==', '2020-11-29 03:39:21', '2020-11-29 03:39:21', '张洪胤的介绍', 'https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg', 2, 1);
INSERT INTO `user` VALUES (4, 'xiaosu1', '小苏1', '4QrcOUm6Wau+VuBX8g+IPg==', '2020-11-29 03:39:33', '2020-11-29 03:39:33', '小苏1的介绍', 'https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg', 3, 1);
INSERT INTO `user` VALUES (5, 'xiaosu2', '小苏2', '4QrcOUm6Wau+VuBX8g+IPg==', '2020-11-29 03:39:40', '2020-11-29 03:39:40', '小苏2的介绍', 'https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg', 3, 1);
INSERT INTO `user` VALUES (6, 'xiaosu3', '小苏3', '4QrcOUm6Wau+VuBX8g+IPg==', '2020-11-29 03:39:55', '2020-11-29 03:39:55', '小苏3的介绍', 'https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg', 4, 1);

SET FOREIGN_KEY_CHECKS = 1;
