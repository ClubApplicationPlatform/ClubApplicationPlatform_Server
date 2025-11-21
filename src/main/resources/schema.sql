-- Clubs
CREATE TABLE IF NOT EXISTS Clubs (
    club_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    short_desc VARCHAR(255) NOT NULL,
    description TEXT,
    activities TEXT,
    vision TEXT,
    type ENUM('ACADEMIC','HOBBY','SPORTS','ART','SERVICE') NOT NULL,
    department VARCHAR(255),
    category VARCHAR(255),
    status ENUM('pending','active','inactive') DEFAULT 'pending',
    recruit_status ENUM('open','closed') DEFAULT 'closed',
    recruitment_notice TEXT,
    recruitment_start_date DATE,
    recruitment_end_date DATE,
    leader_id BIGINT NOT NULL,
    FOREIGN KEY (leader_id) REFERENCES Users(user_id)
);

-- Club Questions
CREATE TABLE IF NOT EXISTS Club_Question (
    qid BIGINT AUTO_INCREMENT PRIMARY KEY,
    club_id BIGINT NOT NULL,
    question VARCHAR(500) NOT NULL,
    answer VARCHAR(500),
    active INT DEFAULT 1,
    FOREIGN KEY (club_id) REFERENCES Clubs(club_id)
);

-- Users
CREATE TABLE IF NOT EXISTS Users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    department VARCHAR(255) NOT NULL,
    student_id VARCHAR(20) NOT NULL UNIQUE,
    role ENUM('ADMIN','LEADER','USER') NOT NULL DEFAULT 'USER',
    phone VARCHAR(20),
    grade INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
