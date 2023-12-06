SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Table `discord-coa-bot`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `discord-coa-bot`.`User` (
                                                `UserId` BIGINT UNSIGNED NOT NULL,
                                                `Spending` BIGINT UNSIGNED NOT NULL DEFAULT 0,
                                                `Earning` BIGINT UNSIGNED NOT NULL DEFAULT 0,
                                                `Reputation` INT UNSIGNED NOT NULL DEFAULT 0,
                                                PRIMARY KEY (`UserId`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `discord-coa-bot`.`Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `discord-coa-bot`.`Product` (
                                                   `ProductId` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                   `Name` VARCHAR(30) NOT NULL,
                                                   `Code` VARCHAR(4) NOT NULL,
                                                   `Cost` BIGINT UNSIGNED NOT NULL DEFAULT 0,
                                                   `Type` VARCHAR(30) NOT NULL,
                                                   PRIMARY KEY (`ProductId`),
                                                   UNIQUE INDEX `Code_UNIQUE` (`Code` ASC) VISIBLE)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
