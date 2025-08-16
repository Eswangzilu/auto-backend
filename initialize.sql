CREATE SCHEMA IF NOT EXISTS `auto`;

CREATE TABLE `auto`.`users` (
    `user_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `account_name` VARCHAR(255) NOT NULL,
    `account_pwd` VARCHAR(255) NOT NULL,
    UNIQUE INDEX `un_account_name_users`(`account_name`)
);