/*
 Navicat Premium Data Transfer

 Source Server         : mySql
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : association_db

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 09/10/2022 11:17:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activities
-- ----------------------------
DROP TABLE IF EXISTS `activities`;
CREATE TABLE `activities`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `association_id` bigint NOT NULL COMMENT '社团id',
  `status` int NOT NULL DEFAULT 1 COMMENT '申请状态(0,申请成功;1,待审核;2,申请失败)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activities
-- ----------------------------
INSERT INTO `activities` VALUES (1, 1, 1, 1, '2022-10-09 00:18:11', '2022-10-09 00:18:15');

-- ----------------------------
-- Table structure for activities_belongs
-- ----------------------------
DROP TABLE IF EXISTS `activities_belongs`;
CREATE TABLE `activities_belongs`  (
  `id` bigint NOT NULL COMMENT '主键',
  `activities_id` bigint NOT NULL COMMENT '申请活动的id',
  `belongs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activities_belongs
-- ----------------------------
INSERT INTO `activities_belongs` VALUES (1, 1, '技术部');

-- ----------------------------
-- Table structure for association
-- ----------------------------
DROP TABLE IF EXISTS `association`;
CREATE TABLE `association`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '社团名称',
  `leader_id` bigint NOT NULL COMMENT '负责人',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '社团logo',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '社团描述',
  `requirement` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '加入社团要求',
  `status` int NOT NULL DEFAULT 1 COMMENT '社团状态(0,正常;1,待审批;2,已销毁)',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '级别',
  `academic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属学院',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of association
-- ----------------------------
INSERT INTO `association` VALUES (1, '社团名称', 1, 'a', 'a', 'a', 0, 'a', 'a', '2022-10-07 22:32:28', '2022-10-09 10:42:31');
INSERT INTO `association` VALUES (1578410798517059586, '社团的名字', 1, '社团logoUrl', '社团简介', '加入社团要求', 1, '社团级别,校级', '所属学院', '2022-10-07 23:43:54', '2022-10-07 23:43:54');
INSERT INTO `association` VALUES (1578413169272020994, '社团的名字测试成功', 1, '社团logoUrl', '社团简介', '加入社团要求', 0, '社团级别,校级', '所属学院', '2022-10-07 23:53:20', '2022-10-07 23:53:20');
INSERT INTO `association` VALUES (1578745968331890689, '社团的名字测试成功1', 1, '社团logoUrl', '社团简介', '加入社团要求', 1, '社团级别,校级', '所属学院', '2022-10-08 21:55:45', '2022-10-08 21:55:45');
INSERT INTO `association` VALUES (1578752693214740481, '社团的名字测试成功2', 1, '社团logoUrl', '社团简介', '加入社团要求', 1, '社团级别,校级', '所属学院', '2022-10-08 22:22:29', '2022-10-08 22:22:29');
INSERT INTO `association` VALUES (1578936166772637698, '社团名称,测试部门', 1, '社团logoUrl', '社团简介', '加入社团要求', 1, '社团级别,校级', '所属学院', '2022-10-09 10:31:32', '2022-10-09 10:31:32');

-- ----------------------------
-- Table structure for association_belongs
-- ----------------------------
DROP TABLE IF EXISTS `association_belongs`;
CREATE TABLE `association_belongs`  (
  `id` bigint NOT NULL COMMENT '主键',
  `association_id` bigint NOT NULL COMMENT '社团id',
  `belongs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of association_belongs
-- ----------------------------
INSERT INTO `association_belongs` VALUES (1578936167036878850, 1578936166772637698, '组织部');
INSERT INTO `association_belongs` VALUES (1578936167036878851, 1578936166772637698, '技术部');
INSERT INTO `association_belongs` VALUES (1578938930579910657, 1, '组织部');
INSERT INTO `association_belongs` VALUES (1578938930659602433, 1, '技术部');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_number` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `tel` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `academic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属学院',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '1111111111111', 'e10adc3949ba59abbe56e057f20f883e', 'fly', '12345678901', NULL, '2022-10-06 16:09:32', '2022-10-06 16:09:35');
INSERT INTO `user` VALUES (1578002604028370946, '1111111111112', 'e10adc3949ba59abbe56e057f20f883e', '', '12345678901', NULL, '2022-10-06 20:41:53', '2022-10-07 22:36:09');

SET FOREIGN_KEY_CHECKS = 1;
