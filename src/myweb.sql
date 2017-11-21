/*
SQLyog Enterprise Trial - MySQL GUI v7.11 
MySQL - 5.6.17 : Database - mywebdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`mywebdb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mywebdb`;

/*Table structure for table `answer_info` */

DROP TABLE IF EXISTS `answer_info`;

CREATE TABLE `answer_info` (
  `asId` int(11) NOT NULL AUTO_INCREMENT,
  `qsId` int(11) DEFAULT NULL,
  `selectId` int(11) DEFAULT NULL,
  PRIMARY KEY (`asId`),
  KEY `FK_Reference_10` (`selectId`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`selectId`) REFERENCES `select_info` (`selectId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `answer_info` */

insert  into `answer_info`(`asId`,`qsId`,`selectId`) values (1,5,6);

/*Table structure for table `class_info` */

DROP TABLE IF EXISTS `class_info`;

CREATE TABLE `class_info` (
  `classId` int(20) NOT NULL AUTO_INCREMENT,
  `className` varchar(20) DEFAULT NULL,
  `special` varchar(20) DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  `endTime` date DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `status` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `class_info` */

insert  into `class_info`(`classId`,`className`,`special`,`createTime`,`endTime`,`remark`,`status`) values (1,'qweq','审计','2017-01-03','2017-01-05','wwdads','0'),(2,'wqeq','法律','2017-01-04','2017-01-03','qweqwe','0'),(3,'sadsa','法律','2016-12-28','2017-01-13','sadas','0'),(4,'cxzc','刑侦','2017-01-04','2017-01-20','sad','0'),(5,'qewq','审计','2017-01-18','2017-12-18','qwe','0'),(6,'java','审计','2017-01-18','2017-12-18','qweq','0'),(7,'qe','刑侦','2016-01-18','2017-01-18','f fdfs','0'),(8,'cc','审计','2017-01-16','2017-01-18','ccas','0'),(9,'java','刑侦','2017-01-22','2017-02-22','rematk','0');

/*Table structure for table `dict_item` */

DROP TABLE IF EXISTS `dict_item`;

CREATE TABLE `dict_item` (
  `dictId` int(11) NOT NULL AUTO_INCREMENT,
  `dictCode` varchar(20) DEFAULT NULL,
  `dictValue` varchar(20) DEFAULT NULL,
  `groupCode` varchar(20) DEFAULT NULL,
  `groupName` varchar(20) DEFAULT NULL,
  `sn` int(11) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `parentId` int(20) DEFAULT NULL,
  PRIMARY KEY (`dictId`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

/*Data for the table `dict_item` */

insert  into `dict_item`(`dictId`,`dictCode`,`dictValue`,`groupCode`,`groupName`,`sn`,`status`,`remark`,`parentId`) values (19,'CQ','重庆','JG','籍贯',2,'0',NULL,NULL),(20,'SC','四川','JG','籍贯',3,'0',NULL,NULL),(21,'SH','上海','JG','籍贯',4,'0',NULL,NULL),(22,'Java','java','SP','专业',5,'1','311',NULL),(23,'SZ','深圳','JG','籍贯',6,'1','311',NULL),(24,'C','c++','SP','专业',5,'1','3234',NULL),(25,'.net','.net','SP','专业',2,'1','3132',NULL),(26,'chinese','语文','subject','科目',1,NULL,NULL,22),(27,'math','数学','subject','科目',2,NULL,NULL,22),(28,'english','外语','subject','科目',3,NULL,NULL,24),(29,'political','政治','subject','科目',4,NULL,NULL,24),(30,'history','历史','subject','科目',5,NULL,NULL,25),(31,'geographic','地理','subject','科目',6,NULL,NULL,25),(32,'poetry','诗词鉴赏','knowledge','知识点',NULL,NULL,NULL,26),(33,'literal','文学写作','knowledge','知识点',NULL,NULL,NULL,26),(34,'function','函数','knowledge','知识点',NULL,NULL,NULL,27),(35,'shape','图形','knowledge','知识点',NULL,NULL,NULL,27),(36,'audition','听力','knowledge','知识点',NULL,NULL,NULL,28),(37,'read','阅读理解','knowledge','知识点',NULL,NULL,NULL,28),(38,'pchoose','政治选择题','knowledge','知识点',NULL,NULL,NULL,29),(39,'pshort','政治简答题','knowledge','知识点',NULL,NULL,NULL,29),(40,'hjudge','历史判断题','knowledge','知识点',NULL,NULL,NULL,30),(41,'hshort','历史简答题','knowledge','知识点',NULL,NULL,NULL,30),(42,'gchoose','地理选择题','knowledge','知识点',NULL,NULL,NULL,31),(43,'gshort','地理简答题','knowledge','知识点',NULL,NULL,NULL,31),(44,'single','单选题','options','单选题',NULL,NULL,NULL,NULL),(45,'multiple','多选题','options','多选题',NULL,NULL,NULL,NULL),(46,'shortanswer','简答题','options','简答题',NULL,NULL,NULL,NULL);

/*Table structure for table `prive_info` */

DROP TABLE IF EXISTS `prive_info`;

CREATE TABLE `prive_info` (
  `priveId` varchar(20) NOT NULL,
  `priveName` varchar(20) DEFAULT NULL,
  `remark` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`priveId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `prive_info` */

insert  into `prive_info`(`priveId`,`priveName`,`remark`,`status`) values ('BJGL','班级管理','班级管理','0'),('KSGL','考试管理','考试管理','0'),('LSJL','历史记录','历史记录','0'),('SJGL','试卷管理','试卷管理','0'),('TKGL','题库管理','题库管理','0'),('XTGL','系统管理','系统管理','0'),('YHGL','用户管理','用户管理','0'),('ZXKS','在线考试','在线考试','0');

/*Table structure for table `question_bank_info` */

DROP TABLE IF EXISTS `question_bank_info`;

CREATE TABLE `question_bank_info` (
  `qsId` int(11) NOT NULL AUTO_INCREMENT,
  `use_userid` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `question` varchar(1000) DEFAULT NULL,
  `specialtyType` varchar(20) DEFAULT NULL,
  `subjectType` varchar(20) DEFAULT NULL,
  `knowledgePoint` varchar(20) DEFAULT NULL,
  `questionType` varchar(20) DEFAULT NULL,
  `status` varchar(5) DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  PRIMARY KEY (`qsId`),
  KEY `FK_Reference_8` (`use_userid`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`use_userid`) REFERENCES `user_info` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `question_bank_info` */

insert  into `question_bank_info`(`qsId`,`use_userid`,`userId`,`question`,`specialtyType`,`subjectType`,`knowledgePoint`,`questionType`,`status`,`createTime`) values (1,NULL,1,'世界地理','.net','geographic','gchoose','single','0','2017-02-10'),(2,NULL,1,'全球','.net','geographic','gchoose','single','0','2017-02-10'),(3,NULL,1,'全球','.net','geographic','gchoose','single','0','2017-02-10'),(4,NULL,1,'qwe','C','political','pchoose','single','0','2017-02-10'),(5,NULL,1,'as','.net','history','hjudge','single','0','2017-02-10');

/*Table structure for table `role_info` */

DROP TABLE IF EXISTS `role_info`;

CREATE TABLE `role_info` (
  `roleId` varchar(20) NOT NULL,
  `roleName` varchar(20) DEFAULT NULL,
  `remark` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_info` */

insert  into `role_info`(`roleId`,`roleName`,`remark`,`status`) values ('admin','超级用户','超级用户','0'),('manger','管理员','管理员','0'),('student','学生','学生','0'),('teacher','教师','教师','0');

/*Table structure for table `role_prive_relaction` */

DROP TABLE IF EXISTS `role_prive_relaction`;

CREATE TABLE `role_prive_relaction` (
  `rpId` int(11) NOT NULL AUTO_INCREMENT,
  `priveId` varchar(20) DEFAULT NULL,
  `roleId` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`rpId`),
  KEY `FK_Reference_3` (`priveId`),
  KEY `FK_Reference_4` (`roleId`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`priveId`) REFERENCES `prive_info` (`priveId`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`roleId`) REFERENCES `role_info` (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `role_prive_relaction` */

insert  into `role_prive_relaction`(`rpId`,`priveId`,`roleId`) values (1,'XTGL','admin'),(2,'YHGL','admin'),(3,'BJGL','admin'),(4,'TKGL','admin'),(5,'SJGL','admin'),(6,'KSGL','admin'),(7,'ZXKS','admin'),(8,'LSJL','admin'),(9,'XTGL','manger'),(10,'YHGL','manger'),(11,'BJGL','manger'),(12,'KSGL','teacher'),(13,'SJGL','teacher'),(14,'TKGL','teacher'),(15,'ZXKS','student'),(16,'LSJL','student');

/*Table structure for table `select_info` */

DROP TABLE IF EXISTS `select_info`;

CREATE TABLE `select_info` (
  `selectId` int(11) NOT NULL AUTO_INCREMENT,
  `qsId` int(11) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`selectId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `select_info` */

insert  into `select_info`(`selectId`,`qsId`,`content`) values (1,1,'大学地理'),(2,1,'初中地理'),(3,1,'高中地理'),(4,5,'dqwe'),(5,5,'qweqw'),(6,5,'dwsdad');

/*Table structure for table `user_class_relaction` */

DROP TABLE IF EXISTS `user_class_relaction`;

CREATE TABLE `user_class_relaction` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `classId` int(11) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_6` (`classId`),
  KEY `FK_Reference_7` (`userid`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`classId`) REFERENCES `class_info` (`classId`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`userid`) REFERENCES `user_info` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `user_class_relaction` */

insert  into `user_class_relaction`(`ID`,`classId`,`userid`) values (2,1,1),(3,4,9);

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `userid` int(20) NOT NULL AUTO_INCREMENT,
  `userno` varchar(20) DEFAULT NULL,
  `userpw` varchar(20) DEFAULT NULL,
  `usernm` varchar(20) DEFAULT NULL,
  `userag` varchar(20) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `ah` varchar(20) DEFAULT NULL,
  `jg` varchar(20) DEFAULT NULL,
  `photo` varchar(50) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `roleId` varchar(20) DEFAULT NULL,
  `jj` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

/*Data for the table `user_info` */

insert  into `user_info`(`userid`,`userno`,`userpw`,`usernm`,`userag`,`sex`,`ah`,`jg`,`photo`,`birthday`,`createTime`,`status`,`roleId`,`jj`) values (1,'1','1','1','1','1','1','1',NULL,NULL,NULL,NULL,'admin',NULL),(3,'vip','123','qwe','12','1','1_2_3','SC','0.jpg','2017-01-15','2016-01-15','1','teacher',NULL),(4,'32','322','we','23','0','1_2','QQW','0.jpg','2017-01-10','2017-01-15','1','manger','weqweq'),(5,'5465','32','qeefs','45','1','2_3','SC','0.jpg','2017-01-06','2017-01-15','0','student','erwersf'),(8,'12','32','32231','123','0','','CQ','','2017-01-02','2017-01-15','1','manger','12'),(9,'123','322','3223112','1234','0','1','SC','0.jpg','2017-01-03','2017-01-16','1','teacher','21'),(10,'T02','123','小李','A','男','玩','北京',NULL,NULL,NULL,NULL,NULL,NULL),(11,'T03','123','小程','A','女','买','重庆',NULL,NULL,NULL,NULL,NULL,NULL),(12,'T04','123','阿斯顿','2.0','女','刷','四川',NULL,NULL,NULL,NULL,NULL,NULL),(13,'534','123','袁磊','21','1','2_3','SC','0.jpg','2017-01-03','2017-01-17','1','teacher','委屈'),(14,'T02','123','小李','A','男','玩','北京',NULL,NULL,NULL,NULL,NULL,NULL),(15,'T03','123','小程','A','女','买','重庆',NULL,NULL,NULL,NULL,NULL,NULL),(16,'T04','123','阿斯顿','2.0','女','刷','四川',NULL,NULL,NULL,NULL,NULL,NULL),(17,'T02','123','小李','A','男','玩','北京',NULL,NULL,NULL,NULL,NULL,NULL),(18,'T03','123','小程','A','女','买','重庆',NULL,NULL,NULL,NULL,NULL,NULL),(19,'T04','123','阿斯顿','2.0','女','刷','四川',NULL,NULL,NULL,NULL,NULL,NULL),(20,'56',NULL,'思维','45',NULL,'3',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,'1233',NULL,'擦拭','32',NULL,'2',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,'3343432',NULL,'的v从','31',NULL,'1@@@2',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(23,'3211',NULL,'理发店','43',NULL,'2',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(24,'1',NULL,'擦拭','31',NULL,'2',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25,'453453',NULL,'发士大夫','42',NULL,'2',NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
