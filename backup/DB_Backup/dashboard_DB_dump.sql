-- MySQL dump 10.13  Distrib 5.6.24, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: dashboard
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
-- Table structure for table `servers`
--

DROP TABLE IF EXISTS `servers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `ip` varchar(32) NOT NULL,
  `status` tinyint(1) unsigned zerofill NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `ip_UNIQUE` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servers`
--

LOCK TABLES `servers` WRITE;
/*!40000 ALTER TABLE `servers` DISABLE KEYS */;
INSERT INTO `servers` VALUES (1,'txssc-fileserv1','147.26.195.210',0,'2014-12-10 10:09:52'),(2,'txssc-dbserv1','147.26.195.135',0,'2014-12-10 10:10:12'),(3,'txssc-faxserv1','147.26.195.129',0,'2014-12-10 10:10:32'),(4,'txssc-prntserv1','147.26.195.134',0,'2014-12-10 10:10:49'),(5,'txssc-cloud1','147.26.195.139',0,'2014-12-10 10:11:28'),(6,'txssc-cloud2','147.26.195.140',0,'2014-12-10 10:11:39');
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
  `errors` varchar(100) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
  `value` double DEFAULT NULL,
  `unit` varchar(45) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `servers_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`servers_id`),
  KEY `fk_sys_monitor_servers_idx` (`servers_id`),
  CONSTRAINT `fk_sys_monitor_servers` FOREIGN KEY (`servers_id`) REFERENCES `servers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_monitor`
--

LOCK TABLES `sys_monitor` WRITE;
/*!40000 ALTER TABLE `sys_monitor` DISABLE KEYS */;
INSERT INTO `sys_monitor` VALUES (1,'none','name',0,'unit','2015-01-22 20:52:59',4),(2,'none',' Committed Virtual Memory Size',8910.203125,'megabyte','2015-01-27 15:11:58',5),(3,'none',' Total Swap Space Size',23999.99609375,'megabyte','2015-01-27 15:11:58',5),(4,'none',' Free Swap Space Size',23931.23828125,'megabyte','2015-01-27 15:11:58',5),(5,'none',' Process Cpu Time',1120000,'second','2015-01-27 15:11:58',5),(6,'none',' Free Physical Memory Size',327.92578125,'megabyte','2015-01-27 15:11:58',5),(7,'none',' Total Physical Memory Size',24103.3046875,'megabyte','2015-01-27 15:11:58',5),(8,'none',' Open File Descriptor Count',0,'','2015-01-27 15:11:58',5),(9,'none',' Max File Descriptor Count',0,'','2015-01-27 15:11:58',5),(10,'none',' System Cpu Load',0.0777666367464778,'%','2015-01-27 15:11:58',5),(11,'none',' Process Cpu Load',0.00000034799055762420943,'%','2015-01-27 15:11:58',5),(12,'none',' Committed Virtual Memory Size',8910.203125,'megabyte','2015-01-27 15:46:48',5),(13,'none',' Total Swap Space Size',23999.99609375,'megabyte','2015-01-27 15:46:48',5),(14,'none',' Free Swap Space Size',23859.61328125,'megabyte','2015-01-27 15:46:48',5),(15,'none',' Process Cpu Time',970000,'second','2015-01-27 15:46:48',5),(16,'none',' Free Physical Memory Size',216.53125,'megabyte','2015-01-27 15:46:48',5),(17,'none',' Total Physical Memory Size',24103.3046875,'megabyte','2015-01-27 15:46:48',5),(18,'none',' Open File Descriptor Count',0,'','2015-01-27 15:46:48',5),(19,'none',' Max File Descriptor Count',0,'','2015-01-27 15:46:48',5),(20,'none',' System Cpu Load',0.07953817953884329,'%','2015-01-27 15:46:48',5),(21,'none',' Process Cpu Load',0.00000029858290255359713,'%','2015-01-27 15:46:48',5),(22,'none',' Committed Virtual Memory Size',8910.203125,'megabyte','2015-01-28 20:33:45',5),(23,'none',' Total Swap Space Size',23999.99609375,'megabyte','2015-01-28 20:33:45',5),(24,'none',' Free Swap Space Size',23122.359375,'megabyte','2015-01-28 20:33:45',5),(25,'none',' Process Cpu Time',900000,'second','2015-01-28 20:33:45',5),(26,'none',' Free Physical Memory Size',399.62109375,'megabyte','2015-01-28 20:33:45',5),(27,'none',' Total Physical Memory Size',24103.3046875,'megabyte','2015-01-28 20:33:45',5),(28,'none',' Open File Descriptor Count',0,'','2015-01-28 20:33:45',5),(29,'none',' Max File Descriptor Count',0,'','2015-01-28 20:33:45',5),(30,'none',' System Cpu Load',0.08077614992801302,'%','2015-01-28 20:33:45',5),(31,'none',' Process Cpu Load',0.00000022563627834235896,'%','2015-01-28 20:33:45',5),(32,'none',' Committed Virtual Memory Size',8910.203125,'megabyte','2015-02-02 15:48:25',5),(33,'none',' Total Swap Space Size',23999.99609375,'megabyte','2015-02-02 15:48:25',5),(34,'none',' Free Swap Space Size',23387.8125,'megabyte','2015-02-02 15:48:25',5),(35,'none',' Process Cpu Time',810000,'second','2015-02-02 15:48:25',5),(36,'none',' Free Physical Memory Size',1587.84375,'megabyte','2015-02-02 15:48:25',5),(37,'none',' Total Physical Memory Size',24103.3046875,'megabyte','2015-02-02 15:48:25',5),(38,'none',' Open File Descriptor Count',0,'','2015-02-02 15:48:25',5),(39,'none',' Max File Descriptor Count',0,'','2015-02-02 15:48:25',5),(40,'none',' System Cpu Load',0.07031326279427838,'%','2015-02-02 15:48:25',5),(41,'none',' Process Cpu Load',0.00000011409248755559347,'%','2015-02-02 15:48:25',5),(42,'none',' Committed Virtual Memory Size',8910.203125,'megabyte','2015-02-03 18:25:28',4),(43,'none',' Total Swap Space Size',23999.99609375,'megabyte','2015-02-03 18:25:28',4),(44,'none',' Free Swap Space Size',23492.3671875,'megabyte','2015-02-03 18:25:28',4),(45,'none',' Process Cpu Time',1060000,'second','2015-02-03 18:25:28',4),(46,'none',' Free Physical Memory Size',184.55859375,'megabyte','2015-02-03 18:25:28',4),(47,'none',' Total Physical Memory Size',24103.3046875,'megabyte','2015-02-03 18:25:28',4),(48,'none',' Open File Descriptor Count',0,'','2015-02-03 18:25:28',4),(49,'none',' Max File Descriptor Count',0,'','2015-02-03 18:25:28',4),(50,'none',' System Cpu Load',0.07277788774153703,'%','2015-02-03 18:25:28',4),(51,'none',' Process Cpu Load',0.00000013281268454033132,'%','2015-02-03 18:25:28',4);
/*!40000 ALTER TABLE `sys_monitor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-15  9:18:50
