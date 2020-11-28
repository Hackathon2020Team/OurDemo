/*
 Navicat Premium Data Transfer

 Source Server         : MyServer
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 47.93.252.112:3306
 Source Schema         : hackathon

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 27/11/2020 23:39:30
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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administertask
-- ----------------------------
INSERT INTO `administertask` VALUES (1, 1, 1, '认真打扫宿舍卫生', NULL, NULL, '2020-11-27 23:33:47', NULL, NULL, '2020-11-27 23:33:24', 'NOT_RECEIVE');
INSERT INTO `administertask` VALUES (2, 2, 2, '认真打扫宿舍天空卫生', NULL, NULL, '2020-11-27 23:33:47', NULL, NULL, '2020-11-27 23:33:24', 'NOT_RECEIVE');

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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, 1, '舍长大人', 1, -1);
INSERT INTO `department` VALUES (2, 1, '副舍长大人', 2, 1);
INSERT INTO `department` VALUES (3, 1, '舍员', 3, 2);

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dotask
-- ----------------------------
INSERT INTO `dotask` VALUES (1, 1, 2, '打扫天空', '我接受打扫天空', NULL, NULL, '2020-11-27 23:35:43', NULL, '2020-11-27 23:36:51', '1970-01-01 08:00:00', 'EXECUTING');
INSERT INTO `dotask` VALUES (2, 1, 3, '打扫地板', NULL, NULL, NULL, '2020-11-27 23:35:53', NULL, NULL, '1970-01-01 08:00:00', 'NOT_RECEIVE');

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
INSERT INTO `organization` VALUES (1, 1, '万峰帝国', '2020-11-27 23:29:28');

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES (1, -1, 1, 1, '打扫卫生', '认真打扫宿舍卫生', 'EXECUTING', '2020-11-27 23:33:47', '2020-11-27 23:33:24', NULL);
INSERT INTO `task` VALUES (2, 1, 1, 1, '打扫天空卫生', '认真打扫天空卫生', 'RELEASE', '2020-11-27 23:33:47', '2020-11-27 23:33:24', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'wwf', '王万峰', '4QrcOUm6Wau+VuBX8g+IPg==', '2020-11-27 23:29:29', '2020-11-27 23:30:00', '舍长', 'https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg', 1, 1);
INSERT INTO `user` VALUES (2, 'zhy', '张洪胤', '4QrcOUm6Wau+VuBX8g+IPg==', '2020-11-27 23:31:48', '2020-11-27 23:36:03', '张洪胤', 'https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg', 2, 1);
INSERT INTO `user` VALUES (3, 'zr', '朱睿', '4QrcOUm6Wau+VuBX8g+IPg==', '2020-11-27 23:32:01', '2020-11-27 23:32:01', 'zr', 'https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg', 2, 1);
INSERT INTO `user` VALUES (4, 'xiaosu1', '小苏儿子一号', '4QrcOUm6Wau+VuBX8g+IPg==', '2020-11-27 23:32:25', '2020-11-27 23:32:25', 'xiaosu1', 'https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg', 3, 1);
INSERT INTO `user` VALUES (5, 'xiaosu2', '小苏儿子二号', '4QrcOUm6Wau+VuBX8g+IPg==', '2020-11-27 23:32:33', '2020-11-27 23:32:33', 'xiaosu2', 'https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg', 3, 1);

SET FOREIGN_KEY_CHECKS = 1;
