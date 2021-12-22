-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: trade
-- ------------------------------------------------------
-- Server version	5.6.41

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
-- Table structure for table `equity`
--

DROP TABLE IF EXISTS `equity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `unit_price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1265 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equity`
--

LOCK TABLES `equity` WRITE;
/*!40000 ALTER TABLE `equity` DISABLE KEYS */;
INSERT INTO `equity` VALUES (1261,'EQUITY1',10.00),(1262,'EQUITY2',5.00),(1263,'EQUITY1',10.00),(1264,'EQUITY2',5.00);
/*!40000 ALTER TABLE `equity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trade_holding`
--

DROP TABLE IF EXISTS `trade_holding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trade_holding` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trade_id` int(11) NOT NULL,
  `equity_id` int(11) NOT NULL,
  `total_holding` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `traderHolding` (`trade_id`),
  KEY `equityHolding` (`equity_id`),
  CONSTRAINT `equityHolding` FOREIGN KEY (`equity_id`) REFERENCES `equity` (`id`),
  CONSTRAINT `traderHolding` FOREIGN KEY (`trade_id`) REFERENCES `trader` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade_holding`
--

LOCK TABLES `trade_holding` WRITE;
/*!40000 ALTER TABLE `trade_holding` DISABLE KEYS */;
INSERT INTO `trade_holding` VALUES (1,1261,1261,4);
/*!40000 ALTER TABLE `trade_holding` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trader`
--

DROP TABLE IF EXISTS `trader`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trader` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `fund_available` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1270 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trader`
--

LOCK TABLES `trader` WRITE;
/*!40000 ALTER TABLE `trader` DISABLE KEYS */;
INSERT INTO `trader` VALUES (1,'Vincenzo',100.00),(1261,'TEST1',120.00),(1262,'Test2',200.00),(1263,'TEST3',100.00),(1267,'TEST3',100.00),(1268,'Test1',100.00),(1269,'Test2',200.00);
/*!40000 ALTER TABLE `trader` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-22  0:16:10
