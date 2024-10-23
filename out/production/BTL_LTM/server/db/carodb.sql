-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.5.5-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for carodb
CREATE DATABASE IF NOT EXISTS `carodb` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `carodb`;

-- Dumping structure for table carodb.gamematch
CREATE TABLE IF NOT EXISTS `gamematch` (
                                           `ID` int(11) NOT NULL AUTO_INCREMENT,
    `PlayerID1` int(11) NOT NULL,
    `PlayerID2` int(11) NOT NULL,
    `WinnerID` int(11) DEFAULT NULL,
    `PlayTime` int(11) NOT NULL,
    `TotalMove` int(11) NOT NULL,
    `StartedTime` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
    `Chat` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`ID`)
    ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table carodb.gamematch: ~6 rows (approximately)
/*!40000 ALTER TABLE `gamematch` DISABLE KEYS */;
REPLACE INTO `gamematch` (`ID`, `PlayerID1`, `PlayerID2`, `WinnerID`, `PlayTime`, `TotalMove`, `StartedTime`, `Chat`) VALUES
	(1, 1, 2, 1, 10, 15, '2020-11-23T17:22:06.081', NULL),
	(2, 2, 3, 2, 20, 25, '2020-11-23T17:22:06.081', NULL),
	(3, 3, 4, 4, 30, 35, '2020-11-23T17:22:06.081', NULL),
	(4, 1, 4, 4, 40, 45, '2020-11-23T17:22:06.081', NULL),
	(5, 3, 2, 3, 50, 55, '2020-11-23T17:22:06.081', NULL),
	(6, 4, 5, 5, 90, 50, '2020-11-23T17:22:06.081', NULL);
/*!40000 ALTER TABLE `gamematch` ENABLE KEYS */;

-- Dumping structure for table carodb.player
CREATE TABLE IF NOT EXISTS `player` (
                                        `ID` int(11) NOT NULL AUTO_INCREMENT,
    `Username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
    `Password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
    `Avatar` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
    `Name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
    `Gender` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Nam',
    `YearOfBirth` int(4) NOT NULL DEFAULT 2000,
    `Score` int(11) NOT NULL DEFAULT 0,
    `MatchCount` int(11) NOT NULL DEFAULT 0,
    `WinCount` int(11) NOT NULL DEFAULT 0,
    `DrawCount` int(11) NOT NULL DEFAULT 0,
    `LoseCount` int(11) NOT NULL DEFAULT 0,
    `CurrentStreak` int(11) NOT NULL DEFAULT 0,
    `Rank` int(11) NOT NULL DEFAULT -1,
    `Blocked` tinyint(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UNIQUE` (`Username`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
ALTER TABLE player
    MODIFY COLUMN Score DOUBLE NOT NULL DEFAULT 0;

-- Dumping data for table carodb.player: ~10 rows (approximately)
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
REPLACE INTO `player` (`ID`, `Username`, `Password`, `Avatar`, `Name`, `Gender`, `YearOfBirth`, `Score`, `MatchCount`, `WinCount`, `DrawCount`, `LoseCount`, `CurrentStreak`, `Rank`, `Blocked`) VALUES
	(1, '99.hoangtran', 'df10ef8509dc176d733d59549e7dbfaf', 'icons8_alien_96px.png', 'hoang tran', 'nam', 1999, 0, 0, 0, 0, 0, 0, 0, 0),
	(2, 'hientran', 'df10ef8509dc176d733d59549e7dbfaf', 'icons8_angry_face_meme_96px.png', 'thu hien', 'nữ', 1988, 0, 0, 0, 0, 0, 0, 0, 0),
	(3, 'nguyenthienhuu', 'df10ef8509dc176d733d59549e7dbfaf', 'icons8_angry_face_meme_96px.png', 'huu', 'nữ', 1999, 0, 0, 0, 0, 0, 0, 0, 0),
	(4, 'dinhkhoa', 'df10ef8509dc176d733d59549e7dbfaf', 'icons8_angry_face_meme_96px.png', 'khoa', 'nữ', 1987, 0, 0, 0, 0, 0, 0, 0, 0),
	(5, 'admin', 'df10ef8509dc176d733d59549e7dbfaf', 'icons8_circled_user_male_skin_type_7_96px.png', 'admin', 'Nam', 1999, 0, 0, 0, 0, 0, 0, 0, 0),
	(6, 'anh', 'e10adc3949ba59abbe56e057f20f883e', 'icons8_circled_user_male_skin_type_7_96px.png', 'anhanh', 'Nam', 1987, 0, 0, 0, 0, 0, 0, 0, 0),
	(7, 'anhhai', 'e10adc3949ba59abbe56e057f20f883e', 'icons8_circled_user_male_skin_type_7_96px.png', 'heo', 'Nam', 1987, 0, 0, 0, 0, 0, 0, 0, 0),
	(8, 'ecec', 'e10adc3949ba59abbe56e057f20f883e', 'icons8_circled_user_male_skin_type_7_96px.png', 'heo', 'Nam', 1997, 0, 0, 0, 0, 0, 0, 0, 0),
	(9, 'superman', 'e10adc3949ba59abbe56e057f20f883e', 'icons8_circled_user_male_skin_type_7_96px.png', 'man', 'Nam', 2000, 0, 0, 0, 0, 0, 0, 0, 0),
	(10, 'yes', 'e10adc3949ba59abbe56e057f20f883e', 'icons8_circled_user_male_skin_type_7_96px.png', 'nonono', 'Nam', 1987, 0, 0, 0, 0, 0, 0, 0, 0);

/*!40000 ALTER TABLE `player` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;


CREATE TABLE `carodb`.`servers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `server_ip` VARCHAR(255) NOT NULL,
  `server_port` INT NOT NULL,
  PRIMARY KEY (`id`));
ALTER TABLE `carodb`.`servers` 
ADD COLUMN `status` VARCHAR(45) NOT NULL AFTER `server_port`;

INSERT INTO servers(server_ip, server_port, status) VALUES('127.0.0.1',5056,'Bật');
INSERT INTO servers(server_ip, server_port, status) VALUES('127.0.0.1',5056,'Bật');
INSERT INTO servers(server_ip, server_port, status) VALUES('127.0.0.1',5056,'Bật');
INSERT INTO servers(server_ip, server_port, status) VALUES('127.0.0.1',5056,'Bật');

UPDATE `carodb`.`servers` SET `server_ip` = '192.168.0.113' WHERE (`id` = '2');
DELETE FROM `carodb`.`servers` WHERE (`id` = '3');
DELETE FROM `carodb`.`servers` WHERE (`id` = '4');
