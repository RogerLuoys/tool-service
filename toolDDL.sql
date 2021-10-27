-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: localhost    Database: tool
-- ------------------------------------------------------
-- Server version	5.7.29

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
-- Current Database: `tool`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `tool` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `tool`;

--
-- Table structure for table `auto_case`
--

DROP TABLE IF EXISTS `auto_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auto_case` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `case_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '业务id',
  `name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `description` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `environment` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '用例执行环境（参数）',
  `finish_time` datetime DEFAULT NULL COMMENT '用例计划完成时间',
  `type` tinyint(4) NOT NULL COMMENT '1 接口，2 UI',
  `status` tinyint(4) NOT NULL COMMENT '1 未执行，2 失败，3成功',
  `max_time` int(11) DEFAULT NULL COMMENT '用例执行的最长时间',
  `owner_id` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所属人id',
  `owner_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所属人',
  `is_delete` tinyint(4) NOT NULL COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='自动化测试用例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auto_step`
--

DROP TABLE IF EXISTS `auto_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auto_step` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `step_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '业务id',
  `name` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `description` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `type` tinyint(4) NOT NULL COMMENT '步骤类型：1 SQL，2 HTTP，3 RPC，4 UI，5 STEPS',
  `steps` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '当type为5时使用List<CommonDTO>',
  `jdbc_sql` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'sql语句列表，List<CommonDTO>类型',
  `jdbc_url` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '数据源地址',
  `jdbc_driver` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '数据源驱动',
  `jdbc_username` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '数据源用户名',
  `jdbc_password` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '数据源密码',
  `http_url` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'http完整路径，可带参数',
  `http_type` varchar(7) COLLATE utf8_bin DEFAULT NULL COMMENT 'http请求方法类型',
  `http_header` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'http请求头',
  `http_body` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT 'http请求体',
  `rpc_url` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'rpc域名或ip:prot',
  `rpc_interface` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT 'rpc接口路径，class name',
  `rpc_method` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'rpc方法',
  `rpc_parameter_type` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'rpc入参类型，完整class name',
  `rpc_parameter` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT 'rpc入参，List<CommonDTO>类型',
  `ui_type` tinyint(4) DEFAULT NULL COMMENT '控件操作类型：1 click，2 sendkey，3 判断控件是否存在，4 打开网站，5 切换frame，6 鼠标悬停',
  `ui_url` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '被测试的网址',
  `ui_element` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '自动化元素，默认xpath',
  `ui_element_id` int(11) DEFAULT NULL COMMENT '元素序号，从1开始',
  `ui_key` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '要输入的字符',
  `after_sleep` int(11) DEFAULT NULL COMMENT '实际结果取到后的等待时间',
  `assert_type` tinyint(4) NOT NULL COMMENT '断言类型：1 完全匹配，2 模糊匹配，-1 不校验',
  `assert_actual` varchar(5000) COLLATE utf8_bin DEFAULT NULL COMMENT '实际结果',
  `assert_expect` varchar(5000) COLLATE utf8_bin DEFAULT NULL COMMENT '预期结果',
  `assert_result` tinyint(4) DEFAULT NULL COMMENT '断言结果',
  `owner_id` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所属人id',
  `owner_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所属人',
  `is_public` tinyint(4) NOT NULL COMMENT '是否公用',
  `is_delete` tinyint(4) NOT NULL COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='自动化测试用例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auto_suite`
--

DROP TABLE IF EXISTS `auto_suite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auto_suite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `suite_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '业务id',
  `name` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `description` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `status` tinyint(4) NOT NULL COMMENT '测试集状态：1 空闲，2 执行中',
  `environment` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '用例执行的环境',
  `step_sleep` int(11) DEFAULT NULL COMMENT '步骤间的等待时间',
  `case_max_time` int(11) DEFAULT NULL COMMENT '用例执行的最长时间',
  `total` int(11) DEFAULT NULL COMMENT '用例总数',
  `passed` int(11) DEFAULT NULL COMMENT '成功用例数',
  `failed` int(11) DEFAULT NULL COMMENT '失败用例数',
  `owner_id` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所属人id',
  `owner_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所属人',
  `is_api_completed` tinyint(4) NOT NULL COMMENT 'api用例是否完成',
  `is_ui_completed` tinyint(4) NOT NULL COMMENT 'ui用例是否完成',
  `is_delete` tinyint(4) NOT NULL COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='自动化测试集表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `case_step_relation`
--

DROP TABLE IF EXISTS `case_step_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `case_step_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `case_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '业务id',
  `step_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '业务id',
  `sequence` int(11) DEFAULT NULL COMMENT '步骤执行顺序',
  `type` tinyint(4) NOT NULL COMMENT '1 前置步骤，2 主要步骤，3 收尾步骤',
  `is_delete` tinyint(4) NOT NULL COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用例步骤关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `common`
--

DROP TABLE IF EXISTS `common`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `common` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idx` int(11) DEFAULT NULL,
  `title` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `value` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `comment` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '业务id',
  `name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `description` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `type` tinyint(4) NOT NULL COMMENT '资源类型：1 数据库，2 设备，3 测试环境，4 从节点',
  `permission` tinyint(4) NOT NULL COMMENT '查看权限：1 公开，2 仅自己可见合',
  `jdbc_url` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '数据源地址',
  `jdbc_driver` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '数据源驱动',
  `jdbc_username` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '数据源用户名',
  `jdbc_password` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '数据源密码',
  `device_model` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '设备型号',
  `device_size` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '屏幕尺寸',
  `device_dpi` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '分辨率',
  `device_os` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '设备操作系统',
  `env_url` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '容器域名或虚拟机ip port',
  `slave_url` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '从节点ip port',
  `owner_id` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所属人id',
  `owner_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所属人昵称',
  `user_id` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '使用人id',
  `user_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '使用人昵称',
  `is_delete` tinyint(4) NOT NULL COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `suite_case_relation`
--

DROP TABLE IF EXISTS `suite_case_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suite_case_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `suite_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '业务id',
  `case_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '业务id',
  `status` tinyint(4) NOT NULL COMMENT '测试集关联用例的执行状态：1 未执行，2 失败，3成功',
  `sequence` int(11) DEFAULT NULL COMMENT '用例执行顺序，默认999',
  `is_delete` tinyint(4) NOT NULL COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='测试集用例关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '业务id',
  `name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `description` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `comment` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '1-未完成；2-已完成',
  `start_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '任务结束时间（完成时间）',
  `owner_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '所属人id',
  `owner_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所属人昵称',
  `is_delete` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tool`
--

DROP TABLE IF EXISTS `tool`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tool` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tool_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT 'uuid',
  `name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `description` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `type` tinyint(4) NOT NULL COMMENT '步骤类型：1 SQL，2 HTTP，3 RPC，4 聚合',
  `permission` tinyint(4) NOT NULL COMMENT '查看权限：1 公开，2 仅自己可见合',
  `parameter` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '入参列表，List<CommonDTO>',
  `tools` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '当type为4时使用，List<CommonDTO>类型',
  `jdbc_sql` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT 'sql语句列表，List<CommonDTO>类型',
  `jdbc_url` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '数据源地址',
  `jdbc_driver` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '数据源驱动',
  `jdbc_username` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '数据源用户名',
  `jdbc_password` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '数据源密码',
  `http_url` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'http完整路径，可带参数',
  `http_type` varchar(7) COLLATE utf8_bin DEFAULT NULL,
  `http_header` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'http请求头',
  `http_body` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT 'http请求体',
  `rpc_url` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'rpc域名或ip:prot',
  `rpc_interface` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT 'rpc接口路径，class name',
  `rpc_method` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'rpc方法',
  `rpc_parameter_type` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'rpc入参类型',
  `rpc_parameter` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT 'rpc入参，List<CommonDTO>类型',
  `owner_id` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所属人id',
  `owner_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所属人',
  `is_delete` tinyint(4) NOT NULL COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='数据工厂表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT 'uuid，即owner_id',
  `login_name` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '登录名',
  `pass_word` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '登录密码',
  `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `user_name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '用户昵称',
  `type` tinyint(4) NOT NULL COMMENT '1--管理员；2--普通账号',
  `status` tinyint(4) NOT NULL COMMENT '1-正常；',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='账号主表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-20 13:37:54
