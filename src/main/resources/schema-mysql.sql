CREATE TABLE IF NOT EXISTS `quiz` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `is_published` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
);
