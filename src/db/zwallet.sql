CREATE DATABASE  IF NOT EXISTS `z_wallet` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `z_wallet`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 10.0.0.200    Database: z_wallet
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `bank_config`
--

DROP TABLE IF EXISTS `bank_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bank_config` (
  `bank_code` varchar(20) NOT NULL,
  `withdraw_min` bigint NOT NULL,
  `withdraw_max` bigint NOT NULL,
  `topup_min` bigint NOT NULL,
  `topup_max` bigint NOT NULL,
  PRIMARY KEY (`bank_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_config`
--

LOCK TABLES `bank_config` WRITE;
/*!40000 ALTER TABLE `bank_config` DISABLE KEYS */;
INSERT INTO `bank_config` VALUES ('msb',10000,50000000,10000,50000000);
/*!40000 ALTER TABLE `bank_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_mapping`
--

DROP TABLE IF EXISTS `bank_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bank_mapping` (
  `userid` varchar(50) NOT NULL,
  `fulllname` varchar(45) DEFAULT NULL,
  `f6cardno` varchar(6) DEFAULT NULL,
  `l4cardno` varchar(4) DEFAULT NULL,
  `bank_token` varchar(100) DEFAULT NULL,
  `card_name` varchar(45) DEFAULT NULL,
  `bank_code` varchar(45) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `card_number` varchar(45) NOT NULL,
  PRIMARY KEY (`userid`,`card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_mapping`
--

LOCK TABLES `bank_mapping` WRITE;
/*!40000 ALTER TABLE `bank_mapping` DISABLE KEYS */;
INSERT INTO `bank_mapping` VALUES ('1584784717714','NGUYEN VAN A','501971','3742','0365590786:1823b96e19b9bec7','MSB','msb',1,'5019717010103742');
/*!40000 ALTER TABLE `bank_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `money_transfer_order`
--

DROP TABLE IF EXISTS `money_transfer_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `money_transfer_order` (
  `order_id` bigint NOT NULL,
  `amount` bigint NOT NULL,
  `receiver_phone` varchar(45) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `money_transfer_order`
--

LOCK TABLES `money_transfer_order` WRITE;
/*!40000 ALTER TABLE `money_transfer_order` DISABLE KEYS */;
INSERT INTO `money_transfer_order` VALUES (1585400409439,100000,'0365535079');
/*!40000 ALTER TABLE `money_transfer_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `transaction_id` bigint NOT NULL,
  `order_id` bigint DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `source_of_fund` int DEFAULT NULL,
  `charge_time` timestamp NULL DEFAULT NULL,
  `amount` bigint DEFAULT NULL,
  `status` int DEFAULT NULL,
  `service_type` int DEFAULT NULL,
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1584880240806,1,'1584784717714',1,'2020-03-22 12:30:41',100,1,1),(1584880273330,2,'1584784717714',1,'2020-03-22 12:31:13',100,1,1),(1584880299280,3,'1584784717714',1,'2020-03-22 12:31:39',100,1,1),(1584880351940,4,'1584784717714',1,'2020-03-22 12:32:32',100,0,1),(1584880381944,5,'1584784717714',1,'2020-03-22 12:33:02',100,1,1),(1584880520439,6,'1584784717714',2,'2020-03-22 12:35:20',100,1,1),(1584880595421,7,'1584784717714',2,'2020-03-22 12:36:35',100,1,1),(1584880631177,8,'1584784717714',2,'2020-03-22 12:37:11',9999969933,0,1);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet_topup_order`
--

DROP TABLE IF EXISTS `wallet_topup_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wallet_topup_order` (
  `order_id` bigint NOT NULL,
  `amount` bigint NOT NULL,
  PRIMARY KEY (`order_id`,`amount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet_topup_order`
--

LOCK TABLES `wallet_topup_order` WRITE;
/*!40000 ALTER TABLE `wallet_topup_order` DISABLE KEYS */;
INSERT INTO `wallet_topup_order` VALUES (1585390573726,100000);
/*!40000 ALTER TABLE `wallet_topup_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet_user`
--

DROP TABLE IF EXISTS `wallet_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wallet_user` (
  `user_id` varchar(50) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(125) DEFAULT NULL,
  `dob` datetime DEFAULT NULL,
  `full_name` varchar(50) DEFAULT NULL,
  `cmnd` varchar(20) DEFAULT NULL,
  `pin` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet_user`
--

LOCK TABLES `wallet_user` WRITE;
/*!40000 ALTER TABLE `wallet_user` DISABLE KEYS */;
INSERT INTO `wallet_user` VALUES ('1584784717714','0365535079',NULL,NULL,'Nguyen Quoc Thai','312198889','123456');
/*!40000 ALTER TABLE `wallet_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `withdraw_order`
--

DROP TABLE IF EXISTS `withdraw_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `withdraw_order` (
  `order_id` bigint NOT NULL,
  `f6cardno` varchar(6) NOT NULL,
  `l4cardno` varchar(4) NOT NULL,
  `amount` bigint NOT NULL,
  `bankcode` varchar(10) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `withdraw_order`
--

LOCK TABLES `withdraw_order` WRITE;
/*!40000 ALTER TABLE `withdraw_order` DISABLE KEYS */;
INSERT INTO `withdraw_order` VALUES (1585390895990,'501971','3742',100000,'msb');
/*!40000 ALTER TABLE `withdraw_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `z_wallet`
--

DROP TABLE IF EXISTS `z_wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `z_wallet` (
  `user_id` varchar(50) NOT NULL,
  `balance` bigint NOT NULL,
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `transaction_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `z_wallet`
--

LOCK TABLES `z_wallet` WRITE;
/*!40000 ALTER TABLE `z_wallet` DISABLE KEYS */;
INSERT INTO `z_wallet` VALUES ('1212121212',100,'2020-03-21 12:35:35',121212122),('1212121212',300,'2020-03-21 12:36:01',1212121223),('1212121212',400,'2020-03-21 12:36:15',1212121244),('1212121212',300,'2020-03-21 13:20:46',1212121254),('1584784717714',91020092,'2020-03-28 10:00:07',1),('1584784717714',91120092,'2020-03-28 10:16:35',2),('1584784717714',91220092,'2020-03-28 13:03:47',100),('1584784717714',200,'2020-03-22 12:29:19',121212121),('1584784717714',100,'2020-03-22 12:30:41',1584880240806),('1584784717714',90920192,'2020-03-22 12:32:54',1584880273330),('1584784717714',90920092,'2020-03-22 12:33:02',1584880381944);
/*!40000 ALTER TABLE `z_wallet` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-28 20:07:00
