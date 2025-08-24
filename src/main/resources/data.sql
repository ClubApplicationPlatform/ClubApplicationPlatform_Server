INSERT INTO CLUB (id, name, member_count, recruiting) VALUES (1, '축구 동아리', 15, true);
INSERT INTO CLUB (id, name, member_count, recruiting) VALUES (2, '음악 사랑방', 8, false);
INSERT INTO CLUB (id, name, member_count, recruiting) VALUES (3, '코딩 클럽', 20, true);
INSERT INTO CLUB (id, name, member_count, recruiting) VALUES (4, '독서 모임', 5, true);
INSERT INTO CLUB (id, name, member_count, recruiting) VALUES (5, '농구 팀', 12, false);



INSERT INTO CLUB_QUESTION (club_id, question, answer, active) VALUES (1, '첫 모임은 언제인가요?', NULL, 1);
INSERT INTO CLUB_QUESTION (club_id, question, answer, active) VALUES (1, '회원 모집 중인가요?', '네, 모집 중입니다.', 1);
INSERT INTO CLUB_QUESTION (club_id, question, answer, active) VALUES (3, '초보자도 참여 가능한가요?', '네, 가능합니다.', 1);

INSERT INTO users (username, password, name, department, grade, email)
VALUES ('gyubin01', '1234', '이규빈', '컴퓨터공학과', 3, 'gyubin01@example.com');

INSERT INTO users (username, password, name, department, grade, email)
VALUES ('hong123', '1234', '홍길동', '전자공학과', 2, 'hong123@example.com');

INSERT INTO users (username, password, name, department, grade, email)
VALUES ('kim45', '1234', '김철수', '경영학과', 1, 'kim45@example.com');

INSERT INTO users (username, password, name, department, grade, email)
VALUES ('lee77', '1234', '이영희', '국어국문학과', 4, 'lee77@example.com');
