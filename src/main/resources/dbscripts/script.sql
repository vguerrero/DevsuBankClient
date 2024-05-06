create database bank;

-- bank.client definition
CREATE TABLE `client` (
  `age` int NOT NULL,
  `genre` tinyint DEFAULT NULL,
  `identification` int DEFAULT NULL,
  `state` bit(1) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_powwvjq5dtrded35jufhbmcsd` (`identification`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;