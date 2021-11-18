CREATE TABLE Teams (
                     TeamID int NOT NULL AUTO_INCREMENT,
                     TeamFlag VARCHAR(30)  NOT NULL,
                     TeamName VARCHAR(30)  NOT NULL,
                     Continent VARCHAR(50) NOT NULL,
                     Played int NOT NULL,
                     Won int NOT NULL,
                     Drawn int NOT NULL,
                     Lost int NOT NULL,
                     PRIMARY KEY (TeamID)
);

