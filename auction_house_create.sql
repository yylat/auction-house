-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.20-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for auction_house
CREATE DATABASE IF NOT EXISTS `auction_house` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `auction_house`;

-- Dumping structure for table auction_house.bid
CREATE TABLE IF NOT EXISTS `bid` (
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table auction_house.delivery_status
CREATE TABLE IF NOT EXISTS `delivery_status` (
  `delivery_status_id` int(11) NOT NULL,
  `delivery_status_description` varchar(40) NOT NULL DEFAULT '0',
  PRIMARY KEY (`delivery_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for procedure auction_house.insert_bid
DELIMITER //
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

END//
DELIMITER ;

-- Dumping structure for table auction_house.item
CREATE TABLE IF NOT EXISTS `item` (
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
  `delivery_status_id` int(11) NOT NULL DEFAULT '0',
  `delivery_date` date DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `name_idx` (`name`),
  KEY `fk_item_item_status_idx` (`item_status_id`),
  KEY `fk_item_item_category_idx` (`item_category_id`),
  KEY `fk_item_user_idx` (`seller_id`),
  KEY `start_date_idx` (`start_date`),
  KEY `close_date_idx` (`close_date`),
  KEY `fk_item_delivery_status` (`delivery_status_id`),
  CONSTRAINT `fk_item_delivery_status` FOREIGN KEY (`delivery_status_id`) REFERENCES `delivery_status` (`delivery_status_id`),
  CONSTRAINT `fk_item_item_category` FOREIGN KEY (`item_category_id`) REFERENCES `item_category` (`item_category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_item_status` FOREIGN KEY (`item_status_id`) REFERENCES `item_status` (`item_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_user` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table auction_house.item_category
CREATE TABLE IF NOT EXISTS `item_category` (
  `item_category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Surrogate PK for item_category table.',
  `category_description` varchar(45) NOT NULL COMMENT 'Description of the item category (e.g., book, jewel). ',
  `parent_item_category_id` int(11) DEFAULT NULL COMMENT 'FK that represents the parent category for this category.',
  PRIMARY KEY (`item_category_id`),
  UNIQUE KEY `category_description_UNIQUE` (`category_description`),
  KEY `fk_category_parent_category_idx` (`parent_item_category_id`),
  CONSTRAINT `fk_category_parent_category` FOREIGN KEY (`parent_item_category_id`) REFERENCES `item_category` (`item_category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table auction_house.item_status
CREATE TABLE IF NOT EXISTS `item_status` (
  `item_status_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Surrogate PK for item_status table.',
  `item_status_description` varchar(15) NOT NULL COMMENT 'Description of item status. Item can have the following statuses:\\n"created" (item created, but not put up for auction);\\n"active" (item put up for auction);\\n"sold" (item was sold);\\n"canceled" (seller canceled auction for this item).',
  PRIMARY KEY (`item_status_id`),
  UNIQUE KEY `status_description_UNIQUE` (`item_status_description`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for event auction_house.item_status_update
DELIMITER //
CREATE DEFINER=`root`@`localhost` EVENT `item_status_update` ON SCHEDULE EVERY 1 HOUR STARTS '2017-08-30 00:00:00' ON COMPLETION PRESERVE ENABLE DO BEGIN	

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
	
	
	UPDATE `item`
	SET `item`.`item_status_id` = 7
	WHERE `item`.`delivery_status_id` = 3
		OR ((`item`.`delivery_status_id` IN (0, 1, 2)) 
		AND (@cur_date - INTERVAL 7 DAY) >= `item`.`delivery_date`);

	UPDATE `item`
	SET `item`.`item_status_id` = 8
	WHERE `item`.`delivery_status_id` IN(4, 5, 6, 7, 8);
	
END//
DELIMITER ;

-- Dumping structure for table auction_house.notification
CREATE TABLE IF NOT EXISTS `notification` (
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table auction_house.notification_type
CREATE TABLE IF NOT EXISTS `notification_type` (
  `notification_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `notification_description` varchar(30) NOT NULL,
  PRIMARY KEY (`notification_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table auction_house.photo
CREATE TABLE IF NOT EXISTS `photo` (
  `photo_id` bigint(16) NOT NULL,
  `item_id` int(11) NOT NULL,
  PRIMARY KEY (`photo_id`),
  KEY `fk_photo_item1_idx` (`item_id`),
  CONSTRAINT `fk_photo_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for procedure auction_house.set_delivery_date
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `set_delivery_date`(
	IN `item_for_update_id` INT
)
BEGIN
	UPDATE `item`
	SET `delivery_date` = CURDATE()
	WHERE `item_id` = item_for_update_id;
END//
DELIMITER ;

-- Dumping structure for procedure auction_house.update_previous_winning_bid
DELIMITER //
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

END//
DELIMITER ;

-- Dumping structure for table auction_house.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Surrogate PK for user table.',
  `username` varchar(20) NOT NULL COMMENT 'User name in the system.',
  `password` varchar(100) NOT NULL COMMENT 'User password.',
  `last_name` varchar(45) NOT NULL COMMENT 'User last name.',
  `middle_name` varchar(45) DEFAULT NULL COMMENT 'User middle name.',
  `first_name` varchar(45) NOT NULL COMMENT 'User first name.',
  `phone_number` varchar(20) NOT NULL COMMENT 'User phone number.',
  `email` varchar(45) NOT NULL COMMENT 'User email address.',
  `is_banned` bit(1) NOT NULL DEFAULT b'0' COMMENT 'Boolean value that detect is user account deleted or not ("0" - not deleted, "1" - deleted).',
  `user_role_id` int(11) NOT NULL COMMENT 'FK that represents user role.',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_user_user_role_idx` (`user_role_id`),
  CONSTRAINT `fk_user_user_role` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table auction_house.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Surrogate PK for user_role table.',
  `role_description` varchar(10) NOT NULL DEFAULT 'user' COMMENT 'Description of user role. Role can be: \n    - "user";\n    - "admin".',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_description_UNIQUE` (`role_description`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for trigger auction_house.bid_after_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `bid_after_insert` AFTER INSERT ON `bid` FOR EACH ROW BEGIN
	UPDATE `item`
	SET `item`.`actual_price` = NEW.`bid_value`,
		`item`.`item_status_id` = IF(NEW.`bid_value` >= `item`.`blitz_price`, 3, `item`.`item_status_id`)
	WHERE `item`.`item_id` = NEW.`item_id`;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger auction_house.bid_after_update
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `bid_after_update` AFTER UPDATE ON `bid` FOR EACH ROW BEGIN

	IF NEW.`is_winning` = 0 THEN

	/* new notification - bid beaten */

		INSERT INTO `notification` 

			(`notification`.`notification_type_id`, `notification`.`user_id`, `notification`.`item_id`) 

			VALUES (6, NEW.`bidder_id`,  NEW.item_id);

	END IF;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger auction_house.item_after_update
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `item_after_update` AFTER UPDATE ON `item` FOR EACH ROW BEGIN

	IF NEW.`item_status_id` != OLD.`item_status_id` THEN
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
			/* new notification for all bidders - seller canceled auction */
				INSERT INTO `notification` (`notification_type_id`, `user_id`, `item_id`)
					SELECT DISTINCT 4, `bid`.`bidder_id`, NEW.`item_id` FROM `bid` WHERE `bid`.`item_id` = NEW.`item_id`;
			/* set deliveryt date */
				CALL set_delivery_date(NEW.`item_id`);
	
			ELSE
			/* do nothing */
				BEGIN END;
		END CASE;
	END IF;
	
	IF OLD.`delivery_status_id` = 0 THEN
		IF NEW.`delivery_status_id` != 0 THEN
			CALL set_delivery_date(NEW.`item_id`);
		END IF;
	END IF;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger auction_house.item_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `item_before_insert` BEFORE INSERT ON `item` FOR EACH ROW BEGIN

	SET NEW.`actual_price` = NEW.`start_price`;

	SET NEW.`item_status_id` = 0;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger auction_house.notification_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `notification_before_insert` BEFORE INSERT ON `notification` FOR EACH ROW BEGIN	

	SET NEW.`date_time` = NOW();

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
