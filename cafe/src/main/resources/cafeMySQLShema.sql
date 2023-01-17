DROP SCHEMA IF EXISTS `cafe`;
CREATE SCHEMA IF NOT EXISTS `cafe` DEFAULT CHARACTER SET utf8;

USE `cafe`;

CREATE TABLE IF NOT EXISTS `cafe`.`category` (
    `id_category` INT NOT NULL AUTO_INCREMENT,
    `category_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id_category`))
ENGINE = InnoDB
AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS `cafe`.`dish` (
        `id_dish` INT NOT NULL AUTO_INCREMENT,
        `dish_name` VARCHAR(256) NOT NULL,
        `weight` INT NOT NULL,
        `price` DECIMAL(9,2) NOT NULL,
        `fk_category` INT NOT NULL,
        `description` VARCHAR(512) NOT NULL DEFAULT ' ',
        PRIMARY KEY (`id_dish`),
        INDEX `fk_dish_category_idx` (`fk_category` ASC) VISIBLE,
        CONSTRAINT `fk_dish_category`
        FOREIGN KEY (`fk_category`)
        REFERENCES `cafe`.`category` (`id_category`)
        ON DELETE RESTRICT
        ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1;