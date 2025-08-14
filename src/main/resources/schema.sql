CREATE TABLE CLUB (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255),
                      member_count INT,
                      recruiting BOOLEAN
);

CREATE TABLE CLUB_QUESTION (
                               qid BIGINT PRIMARY KEY AUTO_INCREMENT,
                               club_id BIGINT NOT NULL,
                               question VARCHAR(500) NOT NULL,
                               answer VARCHAR(500),
                               active INT DEFAULT 1,
                               FOREIGN KEY (club_id) REFERENCES CLUB(id)
);
