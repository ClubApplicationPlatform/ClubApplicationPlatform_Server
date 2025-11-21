INSERT INTO Clubs (club_id, name, short_desc, type, leader_id)
VALUES (1, '축구 동아리', '축구를 사랑하는 사람들의 모임', 'SPORTS', 1);

INSERT INTO Clubs (club_id, name, short_desc, type, leader_id)
VALUES (2, '음악 사랑방', '음악을 좋아하는 사람들', 'ART', 1);

INSERT INTO Clubs (club_id, name, short_desc, type, leader_id)
VALUES (3, '코딩 클럽', '프로그래밍을 배우는 동아리', 'ACADEMIC', 1);

INSERT INTO Clubs (club_id, name, short_desc, type, leader_id)
VALUES (4, '독서 모임', '읽고 토론하는 독서회', 'HOBBY', 1);

INSERT INTO Clubs (club_id, name, short_desc, type, leader_id)
VALUES (5, '농구 팀', '농구를 즐기는 사람들', 'SPORTS', 1);


INSERT INTO Club_Question (club_id, question, answer, active)
VALUES 
(1, '첫 모임은 언제인가요?', NULL, 1),
(1, '회원 모집 중인가요?', '네, 모집 중입니다.', 1),
(3, '초보자도 참여 가능한가요?', '네, 가능합니다.', 1);


INSERT INTO Users (email, username, password, nickname, department, student_id)
VALUES 
('gyubin01@example.com', 'gyubin01', '1234', '규빈', '컴퓨터공학과', '20201234'),
('hong123@example.com', 'hong123', '1234', '홍길동', '전자공학과', '20205678'),
('kim45@example.com', 'kim45', '1234', '김철수', '경영학과', '20209999'),
('lee77@example.com', 'lee77', '1234', '이영희', '국어국문학과', '20201111');
