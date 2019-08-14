/*
 Navicat Premium Data Transfer

 Source Server         : 测试环境root
 Source Server Type    : MySQL
 Source Server Version : 50715
 Source Host           : 101.200.32.177:3306
 Source Schema         : pdd

 Target Server Type    : MySQL
 Target Server Version : 50715
 File Encoding         : 65001

 Date: 07/05/2019 12:07:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (1, 0, 'MEX', 0, 0);
INSERT INTO `sys_dept` VALUES (2, 1, '长沙分公司', 1, 0);
INSERT INTO `sys_dept` VALUES (3, 1, '上海分公司', 2, 0);
INSERT INTO `sys_dept` VALUES (4, 3, '技术部', 0, 0);
INSERT INTO `sys_dept` VALUES (5, 3, '销售部', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `type` varchar(100) NOT NULL COMMENT '字典类型',
  `code` varchar(100) NOT NULL COMMENT '字典码',
  `value` varchar(1000) NOT NULL COMMENT '字典值',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标记  -1：已删除  0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `type` (`type`,`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES (1, '性别', 'sex', '0', '女', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (2, '性别', 'sex', '1', '男', 1, NULL, 1);
INSERT INTO `sys_dict` VALUES (3, '性别', 'sex', '2', '未知', 3, NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `result` varchar(5000) DEFAULT NULL COMMENT '返回值',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` VALUES (1, 'admin', '修改用户', 'com.mex.cpa.modules.admin.sys.controller.SysUserController.update()', '{\"createTime\":1552898667000,\"createUserId\":1,\"email\":\"wangjianwen@mobiexchanger.com\",\"mobile\":\"\",\"password\":\"40ca26faf8c16d7f6f4d52501bf0ab334dc71f7e4cfdd873674d4c1e47966a2a\",\"roleIdList\":[1],\"salt\":\"OqEKRkoadCVvo0QotSac\",\"status\":\"enable\",\"userId\":22,\"username\":\"王建文\"}', '{\"body\":\"修改成功\",\"headers\":{},\"statusCode\":\"OK\",\"statusCodeValue\":200}', 149, '1.119.191.47', '2019-03-21 13:48:50');
INSERT INTO `sys_log` VALUES (47, 'admin', '修改用户', 'com.mex.cpa.modules.admin.sys.controller.SysUserController.update()', '{\"createTime\":1552644439000,\"createUserId\":1,\"email\":\"chenyuxin@mobiexchanger.com\",\"password\":\"2755f53daf034b838a9e7a9e289b4fd813afc41fd7810e830ad87d1a43478ac1\",\"roleIdList\":[3],\"salt\":\"eiv11Md7S2QVddcce3jv\",\"status\":\"enable\",\"userId\":21,\"username\":\"Chloe\"}', '{\"body\":\"修改成功\",\"headers\":{},\"statusCode\":\"OK\",\"statusCodeValue\":200}', 109, '185.239.225.212', '2019-03-21 15:40:12');
INSERT INTO `sys_log` VALUES (48, 'admin', '修改用户', 'com.mex.cpa.modules.admin.sys.controller.SysUserController.update()', '{\"createTime\":1551913096000,\"createUserId\":1,\"email\":\"sally.yu@mobiexchanger.com\",\"mobile\":\"admin\",\"password\":\"79228522b106ea56ad17da74e119847dc29e1298337a7ba75bbfd51e76b36e35\",\"roleIdList\":[4],\"salt\":\"Fj5oiJe2JIwCqTOhiIVm\",\"status\":\"enable\",\"userId\":12,\"username\":\"Sally\"}', '{\"body\":\"修改成功\",\"headers\":{},\"statusCode\":\"OK\",\"statusCodeValue\":200}', 138, '104.233.227.177', '2019-03-21 16:08:46');
INSERT INTO `sys_log` VALUES (49, 'admin', '保存用户', 'com.mex.cpa.modules.admin.sys.controller.SysUserController.save()', '{\"createTime\":1553159767690,\"createUserId\":1,\"email\":\"iris.l@mobiexchanger.com\",\"password\":\"8dd587f44a4d440ad7582ea7c6e67b04241ea801425b13a3679cea5b6bc1c705\",\"roleIdList\":[4],\"salt\":\"j4OywgsXfd9cKpDpHqiA\",\"status\":\"enable\",\"userId\":24,\"username\":\"Iris\"}', '{\"body\":\"保存成功\",\"headers\":{},\"statusCode\":\"OK\",\"statusCodeValue\":200}', 33, '45.79.111.80', '2019-03-21 17:16:08');
INSERT INTO `sys_log` VALUES (50, 'admin', '保存用户', 'com.mex.cpa.modules.admin.sys.controller.SysUserController.save()', '{\"createTime\":1553220843114,\"createUserId\":1,\"email\":\"sherry.zhao@mobiexchanger.com\",\"mobile\":\"\",\"password\":\"02b3fbb827efbc65da179b956eb89cd185a9eb9948c398751e4f37c16e87bc6c\",\"roleIdList\":[3],\"salt\":\"mIC5dJNE3dPDx5HtsmSp\",\"status\":\"enable\",\"userId\":25,\"username\":\"sherry\"}', '{\"body\":\"保存成功\",\"headers\":{},\"statusCode\":\"OK\",\"statusCodeValue\":200}', 135, '45.76.208.30', '2019-03-22 10:14:03');
INSERT INTO `sys_log` VALUES (51, '王建文', '修改用户', 'com.mex.cpa.modules.admin.sys.controller.SysUserController.update()', '{\"createTime\":1552898667000,\"createUserId\":22,\"email\":\"wangjianwen@mobiexchanger.com\",\"mobile\":\"13717789037\",\"password\":\"40ca26faf8c16d7f6f4d52501bf0ab334dc71f7e4cfdd873674d4c1e47966a2a\",\"roleIdList\":[1],\"salt\":\"OqEKRkoadCVvo0QotSac\",\"status\":\"enable\",\"userId\":22,\"username\":\"王建文\"}', '{\"body\":\"修改成功\",\"headers\":{},\"statusCode\":\"OK\",\"statusCodeValue\":200}', 53, '1.119.191.47', '2019-03-25 12:07:20');
INSERT INTO `sys_log` VALUES (52, '王建文', '保存用户', 'com.mex.cpa.modules.admin.sys.controller.SysUserController.save()', '{\"createTime\":1553497890587,\"createUserId\":22,\"email\":\"Iris@mobiexchanger.com\",\"mobile\":\"\",\"password\":\"8743b8996d024345670c3c0d3bf698c395cb5144fc5a9987cde813219c07b639\",\"roleIdList\":[2],\"salt\":\"ehDXFtn1Cf9vQWAhb3iy\",\"status\":\"enable\",\"userId\":26,\"username\":\"Iris-Sales\"}', '{\"body\":\"保存成功\",\"headers\":{},\"statusCode\":\"OK\",\"statusCodeValue\":200}', 29, '1.119.191.47', '2019-03-25 15:11:31');
INSERT INTO `sys_log` VALUES (53, 'admin', '保存用户', 'com.mex.cpa.modules.admin.sys.controller.SysUserController.save()', '{\"createTime\":1554100499577,\"createUserId\":1,\"email\":\"elbe.li@mobiexchanger.com\",\"mobile\":\"\",\"password\":\"c637c947a4381594dcb3809e308bf52557a8953a9b8e3caf1ad991e9a3bbbf66\",\"roleIdList\":[2],\"salt\":\"l1bow9cB6Bf2ZKlhVI21\",\"status\":\"enable\",\"userId\":27,\"username\":\"elbe\"}', '{\"body\":\"保存成功\",\"headers\":{},\"statusCode\":\"OK\",\"statusCodeValue\":200}', 161, '1.119.191.47', '2019-04-01 14:35:00');
INSERT INTO `sys_log` VALUES (54, 'admin', '保存用户', 'com.mex.cpa.modules.admin.sys.controller.SysUserController.save()', '{\"createTime\":1554191002402,\"createUserId\":1,\"email\":\"aodi.z@mobiexchanger.com\",\"password\":\"2f56cd3b4626630220ee7bf48728d8e3c982fba94cc9ba6cfec1b35a52c3444e\",\"roleIdList\":[1],\"salt\":\"MyCwxUWMWUVNKNYfSpLv\",\"status\":\"enable\",\"userId\":28,\"username\":\"aodi\"}', '{\"body\":\"保存成功\",\"headers\":{},\"statusCode\":\"OK\",\"statusCodeValue\":200}', 112, '45.79.111.80', '2019-04-02 15:43:22');
INSERT INTO `sys_log` VALUES (55, 'admin', '修改用户', 'com.mex.cpa.modules.admin.sys.controller.SysUserController.update()', '{\"createTime\":1551911732000,\"createUserId\":1,\"email\":\"nicole.guo@mobiexchanger.com\",\"mobile\":\"6868979890786\",\"password\":\"2f29bc2957559d509747b0c73912361f5fd7f093f1344f1497f2ee1da788d511\",\"roleIdList\":[3],\"salt\":\"8yLg43Aw0uGOIrpVb7Tp\",\"status\":\"enable\",\"userId\":11,\"username\":\"Nicole\"}', '{\"body\":\"修改成功\",\"headers\":{},\"statusCode\":\"OK\",\"statusCodeValue\":200}', 107, '180.154.164.174', '2019-04-10 17:15:39');
INSERT INTO `sys_log` VALUES (56, 'admin', '修改用户', 'com.mex.cpa.modules.admin.sys.controller.SysUserController.update()', '{\"createTime\":1551913096000,\"createUserId\":1,\"email\":\"sally.yu@mobiexchanger.com\",\"mobile\":\"admin\",\"password\":\"9edee8e67e24534a81c966024e28dce731530e22f6afb544862defde08520788\",\"roleIdList\":[4],\"salt\":\"Fj5oiJe2JIwCqTOhiIVm\",\"status\":\"enable\",\"userId\":12,\"username\":\"Sally\"}', '{\"body\":\"修改成功\",\"headers\":{},\"statusCode\":\"OK\",\"statusCodeValue\":200}', 81, '1.119.191.47', '2019-04-11 19:10:13');
INSERT INTO `sys_log` VALUES (57, 'admin', '修改用户', 'com.mex.cpa.modules.admin.sys.controller.SysUserController.update()', '{\"createTime\":1551913096000,\"createUserId\":1,\"email\":\"sally.yu@mobiexchanger.com\",\"mobile\":\"admin\",\"password\":\"4bb838821ec258561366b79e720eda00f33daac1888439f12fd3dca8c8b0133b\",\"roleIdList\":[4],\"salt\":\"Fj5oiJe2JIwCqTOhiIVm\",\"status\":\"enable\",\"userId\":12,\"username\":\"Sally\"}', '{\"body\":\"修改成功\",\"headers\":{},\"statusCode\":\"OK\",\"statusCodeValue\":200}', 22, '1.119.191.47', '2019-04-12 10:07:08');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', NULL, NULL, 0, 'fa fa-cog', 0);
INSERT INTO `sys_menu` VALUES (2, 1, '管理员管理', 'modules/sys/user.html', NULL, 1, 'fa fa-user', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', 'modules/sys/role.html', NULL, 1, 'fa fa-user-secret', 2);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', 'modules/sys/menu.html', NULL, 1, 'fa fa-th-list', 3);
INSERT INTO `sys_menu` VALUES (5, 1, 'SQL监控', 'druid/sql.html', NULL, 1, 'fa fa-bug', 4);
INSERT INTO `sys_menu` VALUES (6, 1, '定时任务', 'modules/job/schedule.html', NULL, 1, 'fa fa-tasks', 5);
INSERT INTO `sys_menu` VALUES (7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:perms', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:perms', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (27, 1, '参数管理', 'modules/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'fa fa-sun-o', 6);
INSERT INTO `sys_menu` VALUES (29, 1, '系统日志', 'modules/sys/log.html', 'sys:log:list', 1, 'fa fa-file-text-o', 7);
INSERT INTO `sys_menu` VALUES (30, 1, '文件上传', 'modules/oss/oss.html', 'sys:oss:all', 1, 'fa fa-file-image-o', 6);
INSERT INTO `sys_menu` VALUES (31, 1, '部门管理', 'modules/sys/dept.html', NULL, 1, 'fa fa-file-code-o', 1);
INSERT INTO `sys_menu` VALUES (32, 31, '查看', NULL, 'sys:dept:list,sys:dept:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (33, 31, '新增', NULL, 'sys:dept:save,sys:dept:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (34, 31, '修改', NULL, 'sys:dept:update,sys:dept:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (35, 31, '删除', NULL, 'sys:dept:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (36, 1, '字典管理', 'modules/sys/dict.html', NULL, 1, 'fa fa-bookmark-o', 6);
INSERT INTO `sys_menu` VALUES (37, 36, '查看', NULL, 'sys:dict:list,sys:dict:info', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (38, 36, '新增', NULL, 'sys:dict:save', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (39, 36, '修改', NULL, 'sys:dict:update', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (40, 36, '删除', NULL, 'sys:dict:delete', 2, NULL, 6);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '超级管理员', '超级管理员', 1, '2018-01-24 17:16:56');
INSERT INTO `sys_role` VALUES (2, '销售', '销售', 1, '2018-01-24 17:00:29');
INSERT INTO `sys_role` VALUES (3, '运营', '运营', 1, '2018-01-24 17:00:56');
INSERT INTO `sys_role` VALUES (4, '媒介', '媒介', 1, '2018-01-24 17:00:56');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` varchar(10) DEFAULT 'enable' COMMENT '状态',
  `dept_id` bigint(20) DEFAULT '1' COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `sys_user_email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'admin', 'cdac762d0ba79875489f6a8b430fa8b5dfe0cdd81da38b80f02f33328af7fd4a', 'YzcmCZNvbXocrsz9dm8e', 'david.wang@mobiexchanger.com', '13612345678', 'enable', 1, '2016-11-11 11:11:11');
INSERT INTO `sys_user` VALUES (10, '武宇', '647fc9c41bf9fbbc7e4df4ef86424213b8917e2fceb7e7aa3d37182f7324858b', 'OaRvEjX6kSrp59n2Hkkn', 'clara.wu@mobiexchanger.com', 'admin', 'enable', 1, '2019-03-07 06:34:48');
INSERT INTO `sys_user` VALUES (11, 'Nicole', '2f29bc2957559d509747b0c73912361f5fd7f093f1344f1497f2ee1da788d511', '8yLg43Aw0uGOIrpVb7Tp', 'nicole.guo@mobiexchanger.com', '6868979890786', 'enable', 1, '2019-03-07 06:35:32');
INSERT INTO `sys_user` VALUES (12, 'Sally', '4bb838821ec258561366b79e720eda00f33daac1888439f12fd3dca8c8b0133b', 'Fj5oiJe2JIwCqTOhiIVm', 'sally.yu@mobiexchanger.com', 'admin', 'enable', 1, '2019-03-07 06:58:16');
INSERT INTO `sys_user` VALUES (21, 'Chloe', '2755f53daf034b838a9e7a9e289b4fd813afc41fd7810e830ad87d1a43478ac1', 'eiv11Md7S2QVddcce3jv', 'chenyuxin@mobiexchanger.com', NULL, 'enable', 1, '2019-03-15 18:07:19');
INSERT INTO `sys_user` VALUES (22, '王建文', '40ca26faf8c16d7f6f4d52501bf0ab334dc71f7e4cfdd873674d4c1e47966a2a', 'OqEKRkoadCVvo0QotSac', 'wangjianwen@mobiexchanger.com', '13717789037', 'enable', 1, '2019-03-18 16:44:27');
INSERT INTO `sys_user` VALUES (23, '于淑', '274df958fae4fa58d729494be59588c891398182c5d2706c21a6ffdc927ad81b', 'gtYNlM6NjaMbi8bd8CQf', 'yushu@mobiexchanger.com', NULL, 'enable', 1, '2019-03-19 09:26:17');
INSERT INTO `sys_user` VALUES (24, 'Iris', '8dd587f44a4d440ad7582ea7c6e67b04241ea801425b13a3679cea5b6bc1c705', 'j4OywgsXfd9cKpDpHqiA', 'iris.li@mobiexchanger.com', NULL, 'enable', 1, '2019-03-21 17:16:08');
INSERT INTO `sys_user` VALUES (25, 'sherry', '02b3fbb827efbc65da179b956eb89cd185a9eb9948c398751e4f37c16e87bc6c', 'mIC5dJNE3dPDx5HtsmSp', 'sherry.zhao@mobiexchanger.com', NULL, 'enable', 1, '2019-03-22 10:14:03');
INSERT INTO `sys_user` VALUES (26, 'Iris-Sales', '8743b8996d024345670c3c0d3bf698c395cb5144fc5a9987cde813219c07b639', 'ehDXFtn1Cf9vQWAhb3iy', 'Iris@mobiexchanger.com', NULL, 'enable', 1, '2019-03-25 15:11:31');
INSERT INTO `sys_user` VALUES (27, 'elbe', 'c637c947a4381594dcb3809e308bf52557a8953a9b8e3caf1ad991e9a3bbbf66', 'l1bow9cB6Bf2ZKlhVI21', 'elbe.li@mobiexchanger.com', NULL, 'enable', 1, '2019-04-01 14:35:00');
INSERT INTO `sys_user` VALUES (28, 'aodi', '2f56cd3b4626630220ee7bf48728d8e3c982fba94cc9ba6cfec1b35a52c3444e', 'MyCwxUWMWUVNKNYfSpLv', 'aodi.z@mobiexchanger.com', NULL, 'enable', 1, '2019-04-02 15:43:22');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (2, 2, 2);
INSERT INTO `sys_user_role` VALUES (10, 3, 1);
INSERT INTO `sys_user_role` VALUES (13, 4, 4);
INSERT INTO `sys_user_role` VALUES (16, 6, 1);
INSERT INTO `sys_user_role` VALUES (17, 7, 4);
INSERT INTO `sys_user_role` VALUES (18, 8, 3);
INSERT INTO `sys_user_role` VALUES (19, 9, 2);
INSERT INTO `sys_user_role` VALUES (20, 5, 3);
INSERT INTO `sys_user_role` VALUES (25, 1, 1);
INSERT INTO `sys_user_role` VALUES (31, 10, 2);
INSERT INTO `sys_user_role` VALUES (46, 23, 3);
INSERT INTO `sys_user_role` VALUES (48, 21, 3);
INSERT INTO `sys_user_role` VALUES (50, 24, 4);
INSERT INTO `sys_user_role` VALUES (51, 25, 3);
INSERT INTO `sys_user_role` VALUES (52, 22, 1);
INSERT INTO `sys_user_role` VALUES (53, 26, 2);
INSERT INTO `sys_user_role` VALUES (54, 27, 2);
INSERT INTO `sys_user_role` VALUES (55, 28, 1);
INSERT INTO `sys_user_role` VALUES (56, 11, 3);
INSERT INTO `sys_user_role` VALUES (58, 12, 4);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `token` (`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_token` VALUES (1, 'b2aa6157511afee2c15fcd782b6c47f6', '2019-04-28 05:24:00', '2019-04-27 21:24:00');
INSERT INTO `sys_user_token` VALUES (11, '755c3b063c924550e940ac81b6079c36', '2019-04-19 02:11:17', '2019-04-18 18:11:17');
INSERT INTO `sys_user_token` VALUES (12, '2badc9252d806d82594c9f51860f55b7', '2019-04-19 01:59:59', '2019-04-18 17:59:59');
INSERT INTO `sys_user_token` VALUES (21, '35dcb49c342a538f1b8d9f58d65c7239', '2019-04-19 02:00:39', '2019-04-18 18:00:39');
INSERT INTO `sys_user_token` VALUES (22, '1a661536eefeaf9303c5f80ef47f8e50', '2019-04-18 23:51:48', '2019-04-18 15:51:48');
INSERT INTO `sys_user_token` VALUES (23, '1bbf71091f49e522e6e7a2918e4bffbf', '2019-04-19 02:36:33', '2019-04-18 18:36:33');
INSERT INTO `sys_user_token` VALUES (24, 'f6f8d9435ebe0db4766009f9e60b557a', '2019-04-19 01:47:41', '2019-04-18 17:47:41');
INSERT INTO `sys_user_token` VALUES (28, 'dcf162c5a9f831ec856ed7e4b11fb5ce', '2019-04-17 21:45:31', '2019-04-17 13:45:31');
COMMIT;

-- ----------------------------
-- Table structure for t_project
-- ----------------------------
DROP TABLE IF EXISTS `t_project`;
CREATE TABLE `t_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '项目名称',
  `sn` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '项目编号',
  `contract_no` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '合同编号',
  `plat` tinyint(4) NOT NULL COMMENT '所属平台',
  `adver_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '广告主ID',
  `adver_subject_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '广告主体客户编号',
  `rebate_type` tinyint(4) DEFAULT NULL COMMENT '返点形式',
  `discount_type` tinyint(4) DEFAULT NULL COMMENT '折扣类型 1返现2返货',
  `rebate_detail` text COLLATE utf8_unicode_ci COMMENT '返点信息JSON',
  `company_id` int(11) DEFAULT NULL COMMENT '公司主体ID',
  `contract_amt` decimal(16,2) DEFAULT NULL COMMENT '签约金额',
  `start_date` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '项目开始时间',
  `end_date` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '项目结束时间',
  `serial` int(11) DEFAULT NULL COMMENT '序号',
  `remarks` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注',
  `create_by` int(255) DEFAULT NULL COMMENT '创建人',
  `update_by` int(255) DEFAULT NULL COMMENT '更新人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(1) DEFAULT '1' COMMENT '删除标记0为删除1为正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='项目表';

-- ----------------------------
-- Records of t_project
-- ----------------------------
BEGIN;
INSERT INTO `t_project` VALUES (1, 'test', '1', '[]', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
