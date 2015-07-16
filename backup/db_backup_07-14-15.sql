-- MySQL dump 10.13  Distrib 5.6.24, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: 
-- ------------------------------------------------------
-- Server version	5.6.24-0ubuntu2

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
-- Current Database: `backup`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `backup` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `backup`;

--
-- Current Database: `dashboard`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `dashboard` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `dashboard`;

--
-- Table structure for table `printers`
--

DROP TABLE IF EXISTS `printers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `printers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `fqdn` varchar(45) DEFAULT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `status` tinyint(1) unsigned zerofill NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fqdn_unique` (`fqdn`),
  UNIQUE KEY `ip_unique` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `printers`
--

LOCK TABLES `printers` WRITE;
/*!40000 ALTER TABLE `printers` DISABLE KEYS */;
INSERT INTO `printers` VALUES (1,'Up High Yield 1','NPI371708','147.26.195.144',1,'2015-07-07 15:59:41'),(2,'Upstairs 2','','147.26.195.132',1,'2015-07-07 16:03:45'),(3,'Up High Yield 3','NPI331774','147.26.195.89',1,'2015-07-07 16:05:02'),(4,'Upstairs 4','CP3525-UP','147.26.195.160',1,'2015-07-07 16:10:59'),(5,'Down High Yield 1','TXSSC_DN_HY1','147.26.195.46',1,'2015-07-07 16:12:26'),(8,'Downstairs 2','2','147.26.195.119',0,'2015-07-07 16:14:05'),(9,'Down High Yield 3','NPI3307B4','147.26.195.152',0,'2015-07-07 16:15:51');
/*!40000 ALTER TABLE `printers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servers`
--

DROP TABLE IF EXISTS `servers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `fqdn` varchar(45) NOT NULL,
  `ip` varchar(32) NOT NULL,
  `status` tinyint(1) unsigned zerofill NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`fqdn`),
  UNIQUE KEY `ip_UNIQUE` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servers`
--

LOCK TABLES `servers` WRITE;
/*!40000 ALTER TABLE `servers` DISABLE KEYS */;
INSERT INTO `servers` VALUES (1,'File Server','txssc-fileserv1','147.26.195.113',1,'2014-12-10 10:09:52'),(2,'Database Server','txssc-dbserv1','147.26.195.135',1,'2014-12-10 10:10:12'),(3,'Fax Server','txssc-faxserv1','147.26.195.129',1,'2014-12-10 10:10:32'),(4,'Print Server','txssc-prntserv1','147.26.195.134',1,'2014-12-10 10:10:49'),(5,'Cloud 1','txssc-cloud1','147.26.195.139',0,'2014-12-10 10:11:28'),(6,'Cloud 2','txssc-cloud2','147.26.195.140',1,'2014-12-10 10:11:39'),(7,'DJ OL VM','txssc-dj-ol-vm','147.26.195.133',1,'2015-07-01 21:51:14');
/*!40000 ALTER TABLE `servers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `snmp`
--

DROP TABLE IF EXISTS `snmp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `snmp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `errors` varchar(100) DEFAULT '0',
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `servers_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`servers_id`),
  KEY `fk_snmp_servers1_idx` (`servers_id`),
  CONSTRAINT `fk_snmp_servers1` FOREIGN KEY (`servers_id`) REFERENCES `servers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `snmp`
--

LOCK TABLES `snmp` WRITE;
/*!40000 ALTER TABLE `snmp` DISABLE KEYS */;
INSERT INTO `snmp` VALUES (1,'0','2015-06-15 14:07:42',5);
/*!40000 ALTER TABLE `snmp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_monitor`
--

DROP TABLE IF EXISTS `sys_monitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_monitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `errors` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `value` varchar(100) DEFAULT NULL,
  `unit` varchar(45) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `servers_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`servers_id`),
  KEY `fk_sys_monitor_servers_idx` (`servers_id`),
  CONSTRAINT `fk_sys_monitor_servers` FOREIGN KEY (`servers_id`) REFERENCES `servers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=245 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_monitor`
--

LOCK TABLES `sys_monitor` WRITE;
/*!40000 ALTER TABLE `sys_monitor` DISABLE KEYS */;
INSERT INTO `sys_monitor` VALUES (1,'none','name','0','unit','2015-01-23 02:52:59',4),(2,'none',' Committed Virtual Memory Size','8910.203125','megabyte','2015-01-27 21:11:58',5),(3,'none',' Total Swap Space Size','23999.99609375','megabyte','2015-01-27 21:11:58',5),(4,'none',' Free Swap Space Size','23931.23828125','megabyte','2015-01-27 21:11:58',5),(5,'none',' Process Cpu Time','1120000','second','2015-01-27 21:11:58',5),(6,'none',' Free Physical Memory Size','327.92578125','megabyte','2015-01-27 21:11:58',5),(7,'none',' Total Physical Memory Size','24103.3046875','megabyte','2015-01-27 21:11:58',5),(8,'none',' Open File Descriptor Count','0','','2015-01-27 21:11:58',5),(9,'none',' Max File Descriptor Count','0','','2015-01-27 21:11:58',5),(10,'none',' System Cpu Load','0.0777666367464778','%','2015-01-27 21:11:58',5),(11,'none',' Process Cpu Load','0.00000034799055762420943','%','2015-01-27 21:11:58',5),(12,'none',' Committed Virtual Memory Size','8910.203125','megabyte','2015-01-27 21:46:48',5),(13,'none',' Total Swap Space Size','23999.99609375','megabyte','2015-01-27 21:46:48',5),(14,'none',' Free Swap Space Size','23859.61328125','megabyte','2015-01-27 21:46:48',5),(15,'none',' Process Cpu Time','970000','second','2015-01-27 21:46:48',5),(16,'none',' Free Physical Memory Size','216.53125','megabyte','2015-01-27 21:46:48',5),(17,'none',' Total Physical Memory Size','24103.3046875','megabyte','2015-01-27 21:46:48',5),(18,'none',' Open File Descriptor Count','0','','2015-01-27 21:46:48',5),(19,'none',' Max File Descriptor Count','0','','2015-01-27 21:46:48',5),(20,'none',' System Cpu Load','0.07953817953884329','%','2015-01-27 21:46:48',5),(21,'none',' Process Cpu Load','0.00000029858290255359713','%','2015-01-27 21:46:48',5),(22,'none',' Committed Virtual Memory Size','8910.203125','megabyte','2015-01-29 02:33:45',5),(23,'none',' Total Swap Space Size','23999.99609375','megabyte','2015-01-29 02:33:45',5),(24,'none',' Free Swap Space Size','23122.359375','megabyte','2015-01-29 02:33:45',5),(25,'none',' Process Cpu Time','900000','second','2015-01-29 02:33:45',5),(26,'none',' Free Physical Memory Size','399.62109375','megabyte','2015-01-29 02:33:45',5),(27,'none',' Total Physical Memory Size','24103.3046875','megabyte','2015-01-29 02:33:45',5),(28,'none',' Open File Descriptor Count','0','','2015-01-29 02:33:45',5),(29,'none',' Max File Descriptor Count','0','','2015-01-29 02:33:45',5),(30,'none',' System Cpu Load','0.08077614992801302','%','2015-01-29 02:33:45',5),(31,'none',' Process Cpu Load','0.00000022563627834235896','%','2015-01-29 02:33:45',5),(32,'none',' Committed Virtual Memory Size','8910.203125','megabyte','2015-02-02 21:48:25',5),(33,'none',' Total Swap Space Size','23999.99609375','megabyte','2015-02-02 21:48:25',5),(34,'none',' Free Swap Space Size','23387.8125','megabyte','2015-02-02 21:48:25',5),(35,'none',' Process Cpu Time','810000','second','2015-02-02 21:48:25',5),(36,'none',' Free Physical Memory Size','1587.84375','megabyte','2015-02-02 21:48:25',5),(37,'none',' Total Physical Memory Size','24103.3046875','megabyte','2015-02-02 21:48:25',5),(38,'none',' Open File Descriptor Count','0','','2015-02-02 21:48:25',5),(39,'none',' Max File Descriptor Count','0','','2015-02-02 21:48:25',5),(40,'none',' System Cpu Load','0.07031326279427838','%','2015-02-02 21:48:25',5),(41,'none',' Process Cpu Load','0.00000011409248755559347','%','2015-02-02 21:48:25',5),(42,'none',' Committed Virtual Memory Size','8910.203125','megabyte','2015-02-04 00:25:28',4),(43,'none',' Total Swap Space Size','23999.99609375','megabyte','2015-02-04 00:25:28',4),(44,'none',' Free Swap Space Size','23492.3671875','megabyte','2015-02-04 00:25:28',4),(45,'none',' Process Cpu Time','1060000','second','2015-02-04 00:25:28',4),(46,'none',' Free Physical Memory Size','184.55859375','megabyte','2015-02-04 00:25:28',4),(47,'none',' Total Physical Memory Size','24103.3046875','megabyte','2015-02-04 00:25:28',4),(48,'none',' Open File Descriptor Count','0','','2015-02-04 00:25:28',4),(49,'none',' Max File Descriptor Count','0','','2015-02-04 00:25:28',4),(50,'none',' System Cpu Load','0.07277788774153703','%','2015-02-04 00:25:28',4),(51,'none',' Process Cpu Load','0.00000013281268454033132','%','2015-02-04 00:25:28',4),(215,NULL,'Operating System Info','',NULL,'2015-07-14 21:02:46',7),(216,NULL,'Name','Windows 7',NULL,'2015-07-14 21:02:46',7),(217,NULL,'Version','6.1',NULL,'2015-07-14 21:02:46',7),(218,NULL,'Architecture','32',NULL,'2015-07-14 21:02:46',7),(219,NULL,'CPU Info','',NULL,'2015-07-14 21:02:46',7),(220,NULL,'Architecture','32',NULL,'2015-07-14 21:02:46',7),(221,NULL,'Make','null',NULL,'2015-07-14 21:02:46',7),(222,NULL,'Model Name','null',NULL,'2015-07-14 21:02:46',7),(223,NULL,'Model','0',NULL,'2015-07-14 21:02:46',7),(224,NULL,'Family','0',NULL,'2015-07-14 21:02:46',7),(225,NULL,'Number of Processors','0',NULL,'2015-07-14 21:02:46',7),(226,NULL,'Number of Cores','0',NULL,'2015-07-14 21:02:46',7),(227,NULL,'Core Speed','0.0',NULL,'2015-07-14 21:02:46',7),(228,NULL,'Cache Size','0',NULL,'2015-07-14 21:02:46',7),(229,NULL,'User Info','',NULL,'2015-07-14 21:02:46',7),(230,NULL,'Username','su-dd27',NULL,'2015-07-14 21:02:46',7),(231,NULL,'Home Directory','C',NULL,'2015-07-14 21:02:46',7),(232,NULL,'Network Info','',NULL,'2015-07-14 21:02:46',7),(233,NULL,'Display Name','Software Loopback Interface 1',NULL,'2015-07-14 21:02:46',7),(234,NULL,'Interface Name','lo',NULL,'2015-07-14 21:02:46',7),(235,NULL,'IPv4','0',NULL,'2015-07-14 21:02:46',7),(236,NULL,'IPv6','127.0.0.1',NULL,'2015-07-14 21:02:46',7),(237,NULL,'Display Name','Microsoft 6to4 Adapter',NULL,'2015-07-14 21:02:46',7),(238,NULL,'Interface Name','net4',NULL,'2015-07-14 21:02:46',7),(239,NULL,'IPv4','2002',NULL,'2015-07-14 21:02:46',7),(240,NULL,'IPv6','fe80',NULL,'2015-07-14 21:02:46',7),(241,NULL,'Display Name','Intel(R) PRO/1000 MT Desktop Adapter',NULL,'2015-07-14 21:02:46',7),(242,NULL,'Interface Name','eth3',NULL,'2015-07-14 21:02:46',7),(243,NULL,'IPv4','fe80',NULL,'2015-07-14 21:02:46',7),(244,NULL,'IPv6','147.26.195.133',NULL,'2015-07-14 21:02:46',7);
/*!40000 ALTER TABLE `sys_monitor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `mysql`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `mysql` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `mysql`;
