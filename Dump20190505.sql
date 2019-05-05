CREATE DATABASE  IF NOT EXISTS `shop_online` /*!40100 DEFAULT CHARACTER SET gb2312 COLLATE gb2312_bin */;
USE `shop_online`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: shop_online
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `admin` (
  `user` varchar(20) COLLATE gb2312_bin NOT NULL,
  `password` varchar(20) COLLATE gb2312_bin DEFAULT NULL,
  PRIMARY KEY (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COLLATE=gb2312_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('admin','admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `brand` (
  `brandID` int(8) NOT NULL,
  `brandName` varchar(20) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  `insertTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`brandID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COLLATE=gb2312_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (10000001,'Apple','2019-04-03 17:22:03'),(10000002,'MI','2019-04-03 17:22:03'),(10000003,'HP','2019-04-03 17:22:03');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `category` (
  `categoryID` int(8) NOT NULL,
  `categoryName` varchar(20) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  `insertTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`categoryID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COLLATE=gb2312_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (20000001,'手机','2019-04-03 17:20:43'),(20000002,'电脑','2019-04-03 17:20:43'),(20000003,'平板','2019-04-03 17:20:43');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `goods` (
  `goodsID` int(8) NOT NULL,
  `categoryID` int(8) NOT NULL,
  `brandID` int(8) NOT NULL,
  `goodsName` varchar(30) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  `insertTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `stock` int(6) NOT NULL,
  PRIMARY KEY (`goodsID`),
  KEY `fkone_idx` (`categoryID`),
  KEY `fktwo_idx` (`brandID`),
  CONSTRAINT `fkone` FOREIGN KEY (`categoryID`) REFERENCES `category` (`categoryid`),
  CONSTRAINT `fktwo` FOREIGN KEY (`brandID`) REFERENCES `brand` (`brandid`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COLLATE=gb2312_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (50000001,20000001,10000001,'iPhone XS Max','2019-04-03 17:58:42',999),(50000002,20000002,10000003,'暗影精灵4','2019-04-03 17:58:42',999),(50000003,20000003,10000001,'iPad Air','2019-04-03 17:58:42',999),(50000004,20000001,10000001,'iPhone XS','2019-04-08 17:33:44',998),(50000005,20000001,10000001,'iPhone XR','2019-04-08 17:33:44',998);
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goodsvalue`
--

DROP TABLE IF EXISTS `goodsvalue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `goodsvalue` (
  `spID` int(8) NOT NULL,
  `goodsID` int(8) NOT NULL,
  `valueID` int(8) NOT NULL,
  `parameterID` int(8) NOT NULL,
  PRIMARY KEY (`spID`),
  KEY `fkvalue_idx` (`valueID`),
  KEY `fkpara_idx` (`parameterID`),
  KEY `fkgoods_idx` (`goodsID`),
  CONSTRAINT `fkgoods` FOREIGN KEY (`goodsID`) REFERENCES `goods` (`goodsid`),
  CONSTRAINT `fkpara` FOREIGN KEY (`parameterID`) REFERENCES `parameter` (`parameterid`),
  CONSTRAINT `fkvalue` FOREIGN KEY (`valueID`) REFERENCES `parametervalue` (`valueid`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COLLATE=gb2312_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goodsvalue`
--

LOCK TABLES `goodsvalue` WRITE;
/*!40000 ALTER TABLE `goodsvalue` DISABLE KEYS */;
INSERT INTO `goodsvalue` VALUES (61100001,50000001,31000003,31000000),(61100002,50000001,31000005,31000000),(61100003,50000001,31000006,31000000),(61200001,50000001,32000001,32000000),(61200002,50000001,32000002,32000000),(61200003,50000001,32000003,32000000),(61300001,50000001,33000003,33000000),(62100001,50000003,31000003,31000000),(62100002,50000003,31000005,31000000),(62100003,50000003,31000006,31000000),(62200001,50000003,32000001,32000000),(62200002,50000003,32000002,32000000),(62200003,50000003,32000003,32000000),(62300001,50000003,33000007,33000000),(63100001,50000002,31000007,31000000),(63100002,50000002,31000008,31000000),(63100003,50000002,31000009,31000000),(63200001,50000002,32000002,32000000),(63200002,50000002,32000003,32000000),(63300001,50000002,33000008,33000000);
/*!40000 ALTER TABLE `goodsvalue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `img`
--

DROP TABLE IF EXISTS `img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `img` (
  `imgID` int(8) NOT NULL,
  `goodsID` int(8) NOT NULL,
  `imgSrc` varchar(45) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  `insertTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`imgID`),
  KEY `fkgoodsid_idx` (`goodsID`),
  CONSTRAINT `fkgoodsid` FOREIGN KEY (`goodsID`) REFERENCES `goods` (`goodsid`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COLLATE=gb2312_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `img`
--

LOCK TABLES `img` WRITE;
/*!40000 ALTER TABLE `img` DISABLE KEYS */;
INSERT INTO `img` VALUES (41000001,50000001,'pic/XsMAX_one.png','2019-04-03 18:14:06'),(41000002,50000001,'pic/XsMAX_two.png','2019-04-03 18:14:06'),(41000003,50000004,'pic/item.png','2019-04-09 14:25:20'),(41000004,50000004,'pic/item.png','2019-04-09 14:25:20'),(41000005,50000005,'pic/item.png','2019-04-09 14:25:20'),(41000006,50000005,'pic/item.png','2019-04-09 14:25:20'),(42000001,50000002,'pic/item.png','2019-04-03 18:14:06'),(42000002,50000002,'pic/item.png','2019-04-03 18:14:06'),(43000001,50000003,'pic/item.png','2019-04-03 18:14:06'),(43000002,50000003,'pic/item.png','2019-04-03 18:14:06');
/*!40000 ALTER TABLE `img` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parameter`
--

DROP TABLE IF EXISTS `parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `parameter` (
  `parameterID` int(8) NOT NULL,
  `parameterName` varchar(20) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  PRIMARY KEY (`parameterID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COLLATE=gb2312_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parameter`
--

LOCK TABLES `parameter` WRITE;
/*!40000 ALTER TABLE `parameter` DISABLE KEYS */;
INSERT INTO `parameter` VALUES (31000000,'存储'),(32000000,'颜色'),(33000000,'尺寸');
/*!40000 ALTER TABLE `parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parametervalue`
--

DROP TABLE IF EXISTS `parametervalue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `parametervalue` (
  `valueID` int(8) NOT NULL,
  `parameterID` int(8) NOT NULL,
  `value` varchar(20) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  PRIMARY KEY (`valueID`),
  KEY `fkparameter_idx` (`parameterID`),
  CONSTRAINT `fkparameter` FOREIGN KEY (`parameterID`) REFERENCES `parameter` (`parameterid`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COLLATE=gb2312_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parametervalue`
--

LOCK TABLES `parametervalue` WRITE;
/*!40000 ALTER TABLE `parametervalue` DISABLE KEYS */;
INSERT INTO `parametervalue` VALUES (31000001,31000000,'8G'),(31000002,31000000,'16G'),(31000003,31000000,'32G'),(31000004,31000000,'64G'),(31000005,31000000,'128G'),(31000006,31000000,'256G'),(31000007,31000000,'512G'),(31000008,31000000,'1TB'),(31000009,31000000,'2TB'),(32000001,32000000,'深空灰'),(32000002,32000000,'金色'),(32000003,32000000,'银色'),(32000004,32000000,'幻彩蓝'),(32000005,32000000,'幻彩紫'),(32000006,32000000,'透明尊享版'),(33000001,33000000,'4.7寸'),(33000002,33000000,'5.5寸'),(33000003,33000000,'6.5寸'),(33000004,33000000,'7.9寸'),(33000005,33000000,'9.6寸'),(33000006,33000000,'10.5寸'),(33000007,33000000,'12.9寸'),(33000008,33000000,'15.6寸');
/*!40000 ALTER TABLE `parametervalue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `price`
--

DROP TABLE IF EXISTS `price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `price` (
  `sku` int(8) NOT NULL,
  `goodsID` int(8) NOT NULL,
  `spID1` int(8) NOT NULL,
  `spID2` int(8) NOT NULL,
  `spID3` int(8) NOT NULL,
  `price` decimal(14,2) NOT NULL,
  `stock` int(6) NOT NULL,
  PRIMARY KEY (`sku`),
  KEY `fkp1_idx` (`goodsID`) /*!80000 INVISIBLE */,
  KEY `fkspid1` (`spID1`),
  KEY `fkspid2_idx` (`spID2`),
  KEY `fkspid3_idx` (`spID3`),
  CONSTRAINT `fkgood` FOREIGN KEY (`goodsID`) REFERENCES `goods` (`goodsid`),
  CONSTRAINT `fkspid1` FOREIGN KEY (`spID1`) REFERENCES `goodsvalue` (`spid`),
  CONSTRAINT `fkspid2` FOREIGN KEY (`spID2`) REFERENCES `goodsvalue` (`spid`),
  CONSTRAINT `fkspid3` FOREIGN KEY (`spID3`) REFERENCES `goodsvalue` (`spid`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COLLATE=gb2312_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `price`
--

LOCK TABLES `price` WRITE;
/*!40000 ALTER TABLE `price` DISABLE KEYS */;
INSERT INTO `price` VALUES (71000001,50000001,61100001,61200001,61300001,6229.00,999),(71000002,50000001,61100002,61200002,61300001,7429.00,999),(71000003,50000001,61100002,61200001,61300001,7429.00,999),(71000004,50000001,61100003,61200001,61300001,9229.00,999),(71000005,50000001,61100001,61200002,61300001,6229.00,999),(71000006,50000001,61100001,61200003,61300001,6229.00,999),(71000007,50000001,61100002,61200003,61300001,7429.00,999),(71000008,50000001,61100003,61200002,61300001,9229.00,999),(71000009,50000001,61100003,61200003,61300001,9229.00,999),(72000001,50000002,62100001,62200001,62300001,3999.00,999),(73000001,50000003,63100001,63200001,63300001,6499.00,999);
/*!40000 ALTER TABLE `price` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `shop` (
  `id` varchar(30) COLLATE gb2312_bin NOT NULL,
  `user` varchar(20) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  `sku` int(8) NOT NULL,
  `goodsName` varchar(20) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  `categoryName` varchar(20) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  `brandName` varchar(20) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  `storage` varchar(20) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  `color` varchar(20) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  `screen` varchar(20) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  `num` int(8) NOT NULL,
  `unitPrice` decimal(14,2) NOT NULL,
  `totalPrice` decimal(14,2) NOT NULL,
  `isPay` tinyint(4) NOT NULL,
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `paymentTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`user`),
  KEY `fkuser_idx` (`user`) /*!80000 INVISIBLE */,
  KEY `fksku_idx` (`sku`),
  CONSTRAINT `fksku` FOREIGN KEY (`sku`) REFERENCES `price` (`sku`),
  CONSTRAINT `fkuser` FOREIGN KEY (`user`) REFERENCES `user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COLLATE=gb2312_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES ('20190416180530','123',71000005,'iPhone XS Max ','手机','Apple','32G','金色','6.5寸',3,6229.00,18687.00,0,'2019-04-16 18:05:30',NULL),('20190416180535','123',71000008,'iPhone XS Max ','手机','Apple','256G','金色','6.5寸',4,9229.00,36916.00,0,'2019-04-16 18:05:35',NULL),('20190416213904','123',71000006,'iPhone XS Max ','手机','Apple','32G','银色','6.5寸',2,6229.00,6229.00,0,'2019-04-16 21:39:04',NULL),('20190416213942','123',71000002,'iPhone XS Max ','手机','Apple','128G','金色','6.5寸',8,7429.00,59432.00,0,'2019-04-16 21:39:42',NULL),('20190423224623','123',71000003,'iPhone XS Max ','手机','Apple','128G','深空灰','6.5寸',1,7429.00,7429.00,0,'2019-04-23 22:46:23',NULL),('20190502152600','123',71000001,'iPhone XS Max','手机',' Apple','32G','深空灰','6.5寸',2,6229.00,12458.00,0,'2019-05-02 15:26:00',NULL),('20190502153210','123',71000007,'iPhone XS Max','手机',' Apple','128G','银色','6.5寸',1,7429.00,7429.00,0,'2019-05-02 15:32:10',NULL),('20190502153212','123',71000009,'iPhone XS Max','手机',' Apple','256G','银色','6.5寸',1,9229.00,9229.00,0,'2019-05-02 15:32:12',NULL),('20190502153218','123',71000004,'iPhone XS Max','手机',' Apple','256G','深空灰','6.5寸',1,9229.00,9229.00,0,'2019-05-02 15:32:18',NULL);
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `user` varchar(20) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  `password` varchar(20) CHARACTER SET gb2312 COLLATE gb2312_bin NOT NULL,
  `regTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `viewCount` int(8) NOT NULL,
  PRIMARY KEY (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COLLATE=gb2312_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('123','123','2019-03-28 14:33:49',90),('456','456','2019-03-29 14:33:49',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-05 20:49:20
