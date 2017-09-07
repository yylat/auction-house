CREATE DATABASE  IF NOT EXISTS `auction_house` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `auction_house`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: auction_house
-- ------------------------------------------------------
-- Server version	5.7.12-log

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
-- Table structure for table `bid`
--

DROP TABLE IF EXISTS `bid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bid` (
  `bid_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Surrogate PK for bid table.',
  `item_id` int(11) NOT NULL COMMENT 'Part of PK that represents the item on which the bid is placed.',
  `bidder_id` int(11) NOT NULL COMMENT 'Part of PK that represents the bidder.',
  `bid_value` decimal(21,1) NOT NULL COMMENT 'The value of the bid.',
  `is_winning` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`bid_id`),
  KEY `fk_bid_user_idx` (`bidder_id`),
  KEY `fk_bid_item_idx` (`item_id`),
  CONSTRAINT `fk_bid_item` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_bid_user` FOREIGN KEY (`bidder_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `bid_after_insert` AFTER INSERT ON `bid` FOR EACH ROW BEGIN
	UPDATE `item`
	SET `item`.`actual_price` = NEW.`bid_value`,
		`item`.`item_status_id` = IF(NEW.`bid_value` >= `item`.`blitz_price`, 3, `item`.`item_status_id`)
	WHERE `item`.`item_id` = NEW.`item_id`;
	
	UPDATE `user`
	SET `user`.`balance` = `user`.`balance` - NEW.`bid_value`
	WHERE `user`.`user_id` = NEW.`bidder_id`;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `bid_after_update` AFTER UPDATE ON `bid` FOR EACH ROW BEGIN

	IF NEW.`is_winning` = 0 THEN

	/* return bid value to bidder */

		CALL return_bid_to_bidder(NEW.`bid_value`, NEW.`bidder_id`);

	/* new notification - bid beaten */

		INSERT INTO `notification` 

			(`notification`.`notification_type_id`, `notification`.`user_id`, `notification`.`item_id`) 

			VALUES (6, NEW.`bidder_id`,  NEW.item_id);

	END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Surrogate PK for item table.',
  `name` varchar(45) NOT NULL COMMENT 'Name of the item.',
  `description` text COMMENT 'Description of the item.',
  `start_price` decimal(21,1) NOT NULL COMMENT 'Start price that represents the min price at which the auction begins.',
  `blitz_price` decimal(21,1) DEFAULT NULL COMMENT 'Blitz price. If bidder make bid with such value, auction ends.',
  `actual_price` decimal(21,1) DEFAULT NULL COMMENT 'The price at which the item was sold.',
  `start_date` date NOT NULL,
  `close_date` date NOT NULL,
  `item_status_id` int(11) NOT NULL COMMENT 'FK that represents status of the item.',
  `item_category_id` int(11) NOT NULL COMMENT 'FK that represents item category.',
  `seller_id` int(11) NOT NULL COMMENT 'FK that represents seller.',
  PRIMARY KEY (`item_id`),
  KEY `name_idx` (`name`),
  KEY `fk_item_item_status_idx` (`item_status_id`),
  KEY `fk_item_item_category_idx` (`item_category_id`),
  KEY `fk_item_user_idx` (`seller_id`),
  KEY `start_date_idx` (`start_date`),
  KEY `close_date_idx` (`close_date`),
  CONSTRAINT `fk_item_item_category` FOREIGN KEY (`item_category_id`) REFERENCES `item_category` (`item_category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_item_status` FOREIGN KEY (`item_status_id`) REFERENCES `item_status` (`item_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_user` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `item_before_insert` BEFORE INSERT ON `item` FOR EACH ROW BEGIN

	SET NEW.`actual_price` = NEW.`start_price`;

	SET NEW.`item_status_id` = 0;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `item_after_update` AFTER UPDATE ON `item` FOR EACH ROW BEGIN

	CASE NEW.`item_status_id`

		WHEN 1 THEN

		/* new notification - item confirmed */

			INSERT INTO `notification` (`notification_type_id`, `user_id`, `item_id`)

				VALUES(0, NEW.`seller_id`, NEW.`item_id`);

				

		WHEN 6 THEN

		/* new notification - item not confirmed */

			INSERT INTO `notification` (`notification_type_id`, `user_id`, `item_id`)

				VALUES(1, NEW.`seller_id`, NEW.`item_id`);

				

		WHEN 3 THEN

		/* add to seller balance value of winning bid */

			UPDATE `user` 

			SET `user`.`balance` = `user`.`balance` + NEW.`actual_price`

			WHERE `user`.`user_id` = NEW.`seller_id`;

		/* new notification for bidder - bid win */

			INSERT INTO `notification` (`notification_type_id`, `user_id`, `item_id`)

				VALUES(5,

				(SELECT `bid`.`bidder_id` FROM `bid` WHERE `item_id` = NEW.`item_id` AND `bid`.`is_winning` = 1 LIMIT 1),

				NEW.`item_id`);

		/* new notification for seller - item sold */	

			INSERT INTO `notification` (`notification_type_id`, `user_id`, `item_id`)

				VALUES(2, NEW.`seller_id`, NEW.`item_id`);

				

		WHEN 5 THEN

		/* new notification - no bids for item */	

			INSERT INTO `notification` (`notification_type_id`, `user_id`, `item_id`)

				VALUES(3, NEW.`seller_id`, NEW.`item_id`);

		

		WHEN 4 THEN

		/* return bid value to winning bidder */

			CALL return_bid_to_winning_bidder(NEW.`item_id`);

		/* new notification for all bidders - seller canceled auction */

			INSERT INTO `notification` (`notification_type_id`, `user_id`, `item_id`)

				SELECT DISTINCT 4, `bid`.`bidder_id`, NEW.`item_id` FROM `bid` WHERE `bid`.`item_id` = NEW.`item_id`;

				

		ELSE

		/* do nothing */

			BEGIN END;

	END CASE;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `item_category`
--

DROP TABLE IF EXISTS `item_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_category` (
  `item_category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Surrogate PK for item_category table.',
  `category_description` varchar(45) NOT NULL COMMENT 'Description of the item category (e.g., book, jewel). ',
  `parent_item_category_id` int(11) DEFAULT NULL COMMENT 'FK that represents the parent category for this category.',
  PRIMARY KEY (`item_category_id`),
  UNIQUE KEY `category_description_UNIQUE` (`category_description`),
  KEY `fk_category_parent_category_idx` (`parent_item_category_id`),
  CONSTRAINT `fk_category_parent_category` FOREIGN KEY (`parent_item_category_id`) REFERENCES `item_category` (`item_category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Dumping data for table `item_category`
--

LOCK TABLES `item_category` WRITE;
/*!40000 ALTER TABLE `item_category` DISABLE KEYS */;
INSERT INTO `item_category` (`item_category_id`, `category_description`, `parent_item_category_id`) VALUES (1,'antiques',NULL),(2,'literature',NULL),(3,'music',NULL),(4,'real estate',NULL),(5,'sport',NULL),(6,'collection',NULL),(7,'cars',NULL),(8,'computers',NULL);
/*!40000 ALTER TABLE `item_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_status`
--

DROP TABLE IF EXISTS `item_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_status` (
  `item_status_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Surrogate PK for item_status table.',
  `item_status_description` varchar(15) NOT NULL COMMENT 'Description of item status. Item can have the following statuses:\\n"created" (item created, but not put up for auction);\\n"active" (item put up for auction);\\n"sold" (item was sold);\\n"canceled" (seller canceled auction for this item).',
  PRIMARY KEY (`item_status_id`),
  UNIQUE KEY `status_description_UNIQUE` (`item_status_description`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Dumping data for table `item_status`
--

LOCK TABLES `item_status` WRITE;
/*!40000 ALTER TABLE `item_status` DISABLE KEYS */;
INSERT INTO `item_status` (`item_status_id`, `item_status_description`) VALUES (2,'active'),(4,'canceled'),(1,'confirmed'),(0,'created'),(5,'ended'),(6,'not_confirmed'),(3,'sold');
/*!40000 ALTER TABLE `item_status` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `notification_type_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`notification_id`),
  KEY `fk_notification_user_idx` (`user_id`),
  KEY `fk_notification_item_idx` (`item_id`),
  KEY `fk_notification_notification_type_idx` (`notification_type_id`),
  CONSTRAINT `fk_notification_item` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_notification_notification_type` FOREIGN KEY (`notification_type_id`) REFERENCES `notification_type` (`notification_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_notification_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `notification_before_insert` BEFORE INSERT ON `notification` FOR EACH ROW BEGIN	

	SET NEW.`date_time` = NOW();

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `notification_type`
--

DROP TABLE IF EXISTS `notification_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_type` (
  `notification_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `notification_description` varchar(30) NOT NULL,
  PRIMARY KEY (`notification_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Dumping data for table `notification_type`
--

LOCK TABLES `notification_type` WRITE;
/*!40000 ALTER TABLE `notification_type` DISABLE KEYS */;
INSERT INTO `notification_type` (`notification_type_id`, `notification_description`) VALUES (0,'item_confirmed'),(1,'item_not_confirmed'),(2,'item_sold'),(3,'no_bids_for_item'),(4,'seller_canceled_auction'),(5,'bid_win'),(6,'bid_beaten');
/*!40000 ALTER TABLE `notification_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `photo`
--

DROP TABLE IF EXISTS `photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `photo` (
  `photo_id` bigint(16) NOT NULL,
  `item_id` int(11) NOT NULL,
  PRIMARY KEY (`photo_id`),
  KEY `fk_photo_item1_idx` (`item_id`),
  CONSTRAINT `fk_photo_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Surrogate PK for user table.',
  `username` varchar(20) NOT NULL COMMENT 'User name in the system.',
  `password` varchar(100) NOT NULL COMMENT 'User password.',
  `last_name` varchar(45) NOT NULL COMMENT 'User last name.',
  `middle_name` varchar(45) DEFAULT NULL COMMENT 'User middle name.',
  `first_name` varchar(45) NOT NULL COMMENT 'User first name.',
  `phone_number` varchar(20) NOT NULL COMMENT 'User phone number.',
  `email` varchar(45) NOT NULL COMMENT 'User email address.',
  `balance` decimal(21,1) NOT NULL DEFAULT '0.0',
  `is_banned` bit(1) NOT NULL DEFAULT b'0' COMMENT 'Boolean value that detect is user account deleted or not ("0" - not deleted, "1" - deleted).',
  `user_role_id` int(11) NOT NULL COMMENT 'FK that represents user role.',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_user_user_role_idx` (`user_role_id`),
  CONSTRAINT `fk_user_user_role` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `username`, `password`, `last_name`, `middle_name`, `first_name`, `phone_number`, `email`, `balance`, `is_banned`, `user_role_id`) VALUES (1,'admin','595752746157356864574e30615739755833567a5a58493d','admin',NULL,'admin','+375297113447','auction.house.web@gmail.com',0.0,'\0',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Surrogate PK for user_role table.',
  `role_description` varchar(10) NOT NULL DEFAULT 'user' COMMENT 'Description of user role. Role can be: \n    - "user";\n    - "admin".',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_description_UNIQUE` (`role_description`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`role_id`, `role_description`) VALUES (0,'admin'),(1,'user');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'auction_house'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `item_status_update` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`localhost`*/ /*!50106 EVENT `item_status_update` ON SCHEDULE EVERY 1 HOUR STARTS '2017-08-30 00:00:00' ON COMPLETION PRESERVE ENABLE DO BEGIN	

	SET @cur_date=CURDATE();



	/* Set item status to active for items with @cur_date between start and close date. */

	UPDATE `item`

	SET `item`.`item_status_id` = 2

	WHERE `item`.`item_status_id` = 1 

		AND (@cur_date BETWEEN `item`.`start_date` AND `item`.`close_date`);

	

	UPDATE `item`

	SET `item`.`item_status_id` = 

		IF((EXISTS (SELECT * FROM `bid` WHERE `bid`.`item_id` = `item`.`item_id`)) > 0, 3, 5)

	WHERE `item`.`item_status_id` = 2 AND `item`.`close_date` <= @cur_date;

END */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'auction_house'
--
/*!50003 DROP PROCEDURE IF EXISTS `insert_bid` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_bid`(

	IN `item_id` INT,

	IN `bidder_id` INT,

	IN `bid_value` DECIMAL(21,3),

	IN `winning_bid_id` INT
)
BEGIN

	UPDATE `bid` 

	SET `bid`.`is_winning` = 0

	WHERE `bid`.`bid_id` = winning_bid_id;

	INSERT INTO `bid` (`item_id`, `bidder_id`, `bid_value`) VALUES (item_id, bidder_id, bid_value);

	SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `return_bid_to_bidder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `return_bid_to_bidder`(

	IN `bid_value` DECIMAL(21,3),

	IN `bidder_id` INT

)
BEGIN

	UPDATE `user`

	SET `user`.`balance` = `user`.`balance` + bid_value

	WHERE `user`.`user_id` = bidder_id;	

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `return_bid_to_winning_bidder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `return_bid_to_winning_bidder`(

	IN `item_id` INT

)
BEGIN

	DECLARE bid_value DECIMAL(21,3);

	DECLARE bidder_id INT;

	
	SELECT `bid`.`bid_value`, `bid`.`bidder_id`

	INTO bid_value, bidder_id

	FROM `bid`

	WHERE `bid`.`item_id` = item_id AND `bid`.`is_winning` = 1;

	
	IF bidder_id IS NOT NULL THEN

		CALL return_bid_to_bidder(bid_value, bidder_id);

	END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_previous_winning_bid` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_previous_winning_bid`(

	IN `item_id` INT
)
BEGIN

	DECLARE bid_id INT;

	DECLARE bid_value DECIMAL(21,3);

	DECLARE bidder_id INT;


	SELECT `bid`.`bid_id`, `bid`.`bid_value`, `bid`.`bidder_id`

	INTO bid_id, bid_value, bidder_id

	FROM `bid`

	WHERE `bid`.`item_id` = item_id AND `bid`.`is_winning` = 1;


	IF bidder_id IS NOT NULL THEN

		/* Set is_winning to false for previous winning bid. */

		UPDATE `bid` 

		SET `bid`.`is_winning` = 0

		WHERE `bid`.`bid_id` = bid_id;

	END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-07 14:13:08
