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
INSERT INTO `bank_mapping` VALUES ('1584784717714','NGUYEN VAN A','501971','3742','0365590786:32efb2e461361974','MSB','msb',1,'5019717010103742');
/*!40000 ALTER TABLE `bank_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mobile_card`
--

DROP TABLE IF EXISTS `mobile_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mobile_card` (
  `card_number` varchar(50) NOT NULL,
  `seri_number` varchar(50) NOT NULL,
  `card_type` varchar(10) NOT NULL,
  `order_id` bigint NOT NULL,
  `status` int NOT NULL,
  `amount` bigint NOT NULL,
  PRIMARY KEY (`card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mobile_card`
--

LOCK TABLES `mobile_card` WRITE;
/*!40000 ALTER TABLE `mobile_card` DISABLE KEYS */;
INSERT INTO `mobile_card` VALUES ('511694006503554','10005908326766','VT',1585817493051,2,20000),('511694006503555','10005908326767','VT',1587269482127,2,20000),('511694006503556','10005908326768','VT',1587269689481,2,20000),('511694006503557','10005908326769','VT',1587269909430,2,20000),('511694006503558','10005908326770','VT',0,1,20000),('511694006503561','10005908326766','VINA',0,1,20000),('511694006503562','10005908326767','VINA',0,1,20000),('511694006503563','10005908326768','VINA',0,1,20000),('511694006503564','10005908326769','VINA',0,1,20000),('511694006503565','10005908326770','VINA',0,1,20000);
/*!40000 ALTER TABLE `mobile_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mobile_card_order`
--

DROP TABLE IF EXISTS `mobile_card_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mobile_card_order` (
  `order_id` bigint NOT NULL,
  `amount` bigint DEFAULT NULL,
  `card_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mobile_card_order`
--

LOCK TABLES `mobile_card_order` WRITE;
/*!40000 ALTER TABLE `mobile_card_order` DISABLE KEYS */;
INSERT INTO `mobile_card_order` VALUES (1585817493051,20000,'VT'),(1587269482127,20000,'VT'),(1587269689481,20000,'VT'),(1587269909430,20000,'VT');
/*!40000 ALTER TABLE `mobile_card_order` ENABLE KEYS */;
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
INSERT INTO `money_transfer_order` VALUES (1585400409439,100000,'0365535079'),(1588255147577,100000,'0365535079'),(1588255236047,100000,'0365535079'),(1588255282925,100000,'0365535079');
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
  `charge_time` bigint DEFAULT NULL,
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
INSERT INTO `transaction` VALUES (1584880240806,1,'1584784717714',1,1588251103680,100,1,1),(1584880273330,2,'1584784717714',1,1588251103681,100,1,1),(1584880299280,3,'1584784717714',1,1588251103682,100,1,1),(1584880351940,4,'1584784717714',1,1588251103683,100,0,1),(1584880381944,5,'1584784717714',1,1588251103684,100,1,1),(1584880520439,6,'1584784717714',2,1588251103685,100,1,1),(1584880595421,7,'1584784717714',2,1588251103686,100,1,1),(1584880631177,8,'1584784717714',2,1588251103687,9999969933,0,1),(1585450745438,1585450672795,'1584784717714',2,1588251103688,100,1,2),(1585450834543,1585450782799,'1584784717714',2,1588251103689,100,1,2),(1585450965390,1585450959375,'1584784717714',2,1588251103690,100,1,2),(1585457374879,1585457313965,'1584784717714',1,1588251103691,100,1,2),(1585457424994,1585457419036,'1584784717714',1,1588251103692,100,1,2),(1585457463697,1585457457134,'1584784717714',1,1588251103693,100,1,2),(1585457580550,1585457518287,'1584784717714',1,1588251103694,100000,1,2),(1587269555825,1587269482127,'1584784717714',1,1588251103695,20000,1,4),(1587269694771,1587269689481,'1584784717714',1,1588251103696,20000,1,4),(1587269916722,1587269909430,'1584784717714',1,1588251103697,20000,1,4);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notify`
--

DROP TABLE IF EXISTS `user_notify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_notify` (
  `notify_id` bigint NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `service_type` int NOT NULL,
  `title` varchar(45) NOT NULL,
  `content` varchar(1048) NOT NULL,
  `create_date` bigint NOT NULL,
  `status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`notify_id`),
  UNIQUE KEY `id_UNIQUE` (`notify_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notify`
--

LOCK TABLES `user_notify` WRITE;
/*!40000 ALTER TABLE `user_notify` DISABLE KEYS */;
INSERT INTO `user_notify` VALUES (1588254094273,'1584784717714',5,'Liên kết ngân hàng MSB','{\"bankCode\":\"msb\",\"first6CardNo\":\"501971\",\"last6CardNo\":\"3742\"}',1588254094278,1),(1588254418426,'1584784717714',6,'Hủy liên kết ngân hàng MSB','{\"bankCode\":\"msb\",\"first6CardNo\":\"501971\",\"last6CardNo\":\"3742\"}',1588254418431,1),(1588254428637,'1584784717714',5,'Liên kết ngân hàng MSB','{\"bankCode\":\"msb\",\"first6CardNo\":\"501971\",\"last6CardNo\":\"3742\"}',1588254428638,1),(1588255292399,'1584784717714',3,'Nhận tiền từ Nguyen Quoc Thai','{\"amount\":100000,\"status\":1,\"orderid\":\"1588255282925\",\"transactionid\":\"103\",\"sender\":\"Nguyen Quoc Thai\"}',1588255292405,1),(1588255732065,'1584784717714',7,'Xác thực thông tin tài khoản thành công','{\"status\":1,\"comment\":\"AAAAAAAAA\"}',1588255732069,1),(1588255760272,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255760272,1),(1588255776179,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255776179,1),(1588255779243,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255779243,1),(1588255780258,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255780258,1),(1588255798042,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255798049,1),(1588255800441,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255800441,1),(1588255802138,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255802138,1),(1588255803286,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255803286,1),(1588255803953,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255803953,1),(1588255804286,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255804286,1),(1588255804476,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255804476,1),(1588255804697,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255804697,1),(1588255805027,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255805027,1),(1588255806210,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255806210,1),(1588255806394,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255806394,1),(1588255806645,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255806645,1),(1588255806957,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255806957,1),(1588255807156,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255807157,1),(1588255807348,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255807348,1),(1588255807543,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255807543,1),(1588255807761,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255807761,1),(1588255807939,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255807939,1),(1588255808177,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255808177,1),(1588255808361,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255808361,1),(1588255808574,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255808574,1),(1588255808785,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255808785,1),(1588255808979,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255808979,1),(1588255809174,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255809174,1),(1588255809383,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255809383,1),(1588255809544,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255809544,1),(1588255809737,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255809737,1),(1588255809925,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255809925,1),(1588255810165,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255810165,1),(1588255810347,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255810347,1),(1588255810525,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255810525,1),(1588255810727,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255810727,1),(1588255810910,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255810910,1),(1588255811116,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255811116,1),(1588255811305,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255811305,1),(1588255811510,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255811510,1),(1588255811679,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255811679,1),(1588255811880,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255811880,1),(1588255812075,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255812075,1),(1588255812276,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255812276,1),(1588255812883,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255812883,1),(1588255813293,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255813293,1),(1588255813515,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255813515,1),(1588255813743,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255813743,1),(1588255813929,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255813929,1),(1588255814124,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255814124,1),(1588255814284,'1584784717714',7,'Xác thực thông tin tài khoản thất bại','{\"status\":2,\"comment\":\"AAAAAAAAA\"}',1588255814284,1);
/*!40000 ALTER TABLE `user_notify` ENABLE KEYS */;
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
INSERT INTO `wallet_topup_order` VALUES (1585390573726,100000),(1585450959375,100000);
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
  `dob` varchar(50) DEFAULT NULL,
  `full_name` varchar(50) DEFAULT NULL,
  `cmnd` varchar(20) DEFAULT NULL,
  `pin` varchar(6) DEFAULT NULL,
  `cmnd_font_img` varchar(128) DEFAULT NULL,
  `cmnd_back_img` varchar(128) DEFAULT NULL,
  `avatar` varchar(128) DEFAULT NULL,
  `verify` int DEFAULT '0',
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
INSERT INTO `wallet_user` VALUES ('1584784717714','0365535079','HCM','01/02/2001','Nguyen Quoc Thai','312198889','123456','a55ceff4-fc76-4fc8-b093-f973e10f560b','a55ceff4-fc76-4fc8-b093-f973e10f560b','a55ceff4-fc76-4fc8-b093-f973e10f560b',2);
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
INSERT INTO `z_wallet` VALUES ('1212121212',100,'2020-03-21 12:35:35',121212122),('1212121212',300,'2020-03-21 12:36:01',1212121223),('1212121212',400,'2020-03-21 12:36:15',1212121244),('1212121212',300,'2020-03-21 13:20:46',1212121254),('1584784717714',91020092,'2020-03-28 10:00:07',1),('1584784717714',91120092,'2020-03-28 10:16:35',2),('1584784717714',91220092,'2020-03-28 13:03:47',100),('1584784717714',91259792,'2020-04-30 14:00:06',101),('1584784717714',91359792,'2020-04-30 14:00:46',102),('1584784717714',91459792,'2020-04-30 14:01:32',103),('1584784717714',200,'2020-03-22 12:29:19',121212121),('1584784717714',100,'2020-03-22 12:30:41',1584880240806),('1584784717714',90920192,'2020-03-22 12:32:54',1584880273330),('1584784717714',90920092,'2020-03-22 12:33:02',1584880381944),('1584784717714',91320092,'2020-03-29 03:02:50',1585450965390),('1584784717714',91319992,'2020-03-29 04:49:35',1585457374879),('1584784717714',91319892,'2020-03-29 04:50:25',1585457424994),('1584784717714',91319792,'2020-03-29 04:51:04',1585457463697),('1584784717714',91219792,'2020-03-29 04:53:01',1585457580550),('1584784717714',91199792,'2020-04-19 04:12:36',1587269555825),('1584784717714',91179792,'2020-04-19 04:14:55',1587269694771),('1584784717714',91159792,'2020-04-19 04:18:37',1587269916722);
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

-- Dump completed on 2020-04-30 21:12:40
