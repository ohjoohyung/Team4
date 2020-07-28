CREATE SCHEMA `bodiary` DEFAULT CHARACTER SET utf8mb4 ;
use bodiary;

-- 회원
CREATE TABLE `User` (
	`user_email`         VARCHAR(50)  NOT NULL, -- 이메일
	`user_pwd`           VARCHAR(100)  NOT NULL, -- 비밀번호
	`user_nickname`      VARCHAR(20)  NOT NULL, -- 닉네임
	`user_gender`        VARCHAR(20)  NOT NULL, -- 성별
	`user_age`           INT          NOT NULL, -- 나이
	`user_height`        INT          NOT NULL, -- 키
	`user_weight`        INT          NOT NULL, -- 몸무게
	`user_img`           VARCHAR(100) NOT NULL, -- 프로필사진
	`user_reportedcount` INT          NOT NULL, -- 유저누적신고수
	`enabled`            INT          NOT NULL, -- 계정활성화 여부
	`user_grade`         VARCHAR(50)  NOT NULL, -- 등급
	`user_insertdate`    DATETIME     NOT NULL, -- 가입날짜
	`user_deletedate`    DATETIME     NULL,      -- 탈퇴날짜
    user_snstype 	    varchar(50)   null
);

-- 회원
ALTER TABLE `User`
	ADD CONSTRAINT `PK_User` -- 회원 기본키
		PRIMARY KEY (
			`user_email` -- 이메일
		);

-- 일지
CREATE TABLE `Diary` (
	`diary_seq`          INT           NOT NULL, -- 일지번호
	`user_email`         VARCHAR(50)   NOT NULL, -- 이메일
	`routine_cart_seq`   INT           NOT NULL, -- 루틴장바구니시퀀스
	`meal_cart_seq`      INT           NULL,     -- 식단장바구니시퀀스
	`diary_content`      VARCHAR(2000) NULL,     -- 일지 오늘의 한마디
	`diary_condition`    VARCHAR(20)   NOT NULL, -- 컨디션
	`diary_main_img`     VARCHAR(100)  NOT NULL, -- 눈바디사진
	`diary_date`         DATETIME      NOT NULL, -- 날짜
	`diary_today_weight` INT           NOT NULL, -- 몸무게
	`diary_metabolism`   INT           NOT NULL, -- 기초대사량
	`diary_recom`        INT           NOT NULL, -- 권장섭취량
	`diary_nut`          INT           NULL      -- 일일섭취량
);

-- 일지
ALTER TABLE `Diary`
	ADD CONSTRAINT `PK_Diary` -- 일지 기본키
		PRIMARY KEY (
			`diary_seq` -- 일지번호
		);

ALTER TABLE `Diary`
	MODIFY COLUMN `diary_seq` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `Diary`
	AUTO_INCREMENT = 1;

-- 루틴자랑게시판
CREATE TABLE `RoutineBoard` (
	`routine_brd_seq`     INT           NOT NULL, -- 글번호
	`user_email`          VARCHAR(50)   NOT NULL, -- 이메일
	`routine_brd_title`   VARCHAR(500)   NOT NULL, -- 글제목
	`routine_brd_content` VARCHAR(2000) NOT NULL, -- 내용
	`routine_brd_regdate` DATETIME      NOT NULL, -- 작성날짜
	`routine_brd_hit`     INT           NOT NULL, -- 조회수
	`routine_cart_seq`    INT           NOT NULL, -- 루틴장바구니시퀀스
	`brd_image1`          VARCHAR(100)  NOT NULL, -- 이미지1
	`brd_image2`          VARCHAR(100)  NOT NULL  -- 이미지2
);

-- 루틴자랑게시판
ALTER TABLE `RoutineBoard`
	ADD CONSTRAINT `PK_RoutineBoard` -- 루틴자랑게시판 기본키
		PRIMARY KEY (
			`routine_brd_seq` -- 글번호
		);

ALTER TABLE `RoutineBoard`
	MODIFY COLUMN `routine_brd_seq` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `RoutineBoard`
	AUTO_INCREMENT = 1;

-- 문의게시판
CREATE TABLE `QnaBrd` (
	`qna_brd_seq`     INT           NOT NULL, -- 글번호
	`user_email`      VARCHAR(50)   NOT NULL, -- 이메일
	`qna_brd_title`   VARCHAR(500)   NOT NULL, -- 글제목
	`qna_brd_content` VARCHAR(2000) NOT NULL, -- 내용
	`qna_brd_regdate` DATETIME      NOT NULL, -- 작성날짜
	`qna_brd_ref`     INT           NOT NULL, -- REF
	`qna_brd_depth`   INT           NOT NULL, -- DEPTH
	`qna_brd_step`    INT           NOT NULL,  -- STEP
    qna_brd_answer VARCHAR(20) NOT NULL DEFAULT '1' -- 답변
);

-- 문의게시판
ALTER TABLE `QnaBrd`
	ADD CONSTRAINT `PK_QnaBrd` -- 문의게시판 기본키
		PRIMARY KEY (
			`qna_brd_seq` -- 글번호
		);

ALTER TABLE `QnaBrd`
	MODIFY COLUMN `qna_brd_seq` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `QnaBrd`
	AUTO_INCREMENT = 1;


-- 게시판
CREATE TABLE `FreeBoard` (
	`free_brd_seq`        INT           NOT NULL, -- 글번호
	`free_cat`            INT           NOT NULL, -- 대분류아이디
	`user_email`          VARCHAR(50)   NOT NULL, -- 이메일
	`free_brd_title`      VARCHAR(500)   NOT NULL, -- 글제목
	`free_brd_hits`        INT           NOT NULL default 0, -- 조회수
	`free_brd_content`    VARCHAR(2000) NOT NULL, -- 내용
	`free_brd_date`       DATETIME      NOT NULL, -- 작성날짜
	`free_brd_report_num` INT           NOT NULL default 0  -- 신고수
);

-- 게시판
ALTER TABLE `FreeBoard`
	ADD CONSTRAINT `PK_FreeBoard` -- 게시판 기본키
		PRIMARY KEY (
			`free_brd_seq` -- 글번호
		);

ALTER TABLE `FreeBoard`
	MODIFY COLUMN `free_brd_seq` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `FreeBoard`
	AUTO_INCREMENT = 1;

-- 댓글
CREATE TABLE `FreeBoardComment` (
	`brd_cmt_seq`   INT           NOT NULL, -- 댓글번호
	`free_brd_seq`  INT           NOT NULL, -- 글번호
	`free_cat`      INT           NOT NULL, -- 대분류아이디
	`user_email`    VARCHAR(50)   NOT NULL, -- 이메일
	`brd_cmt`       VARCHAR(2000) NOT NULL, -- 댓글내용
	`brd_cmt_date`  DATETIME      NOT NULL, -- 작성날짜
	`brd_cmt_ref`   INT           NOT NULL, -- REF
	`brd_cmt_depth` INT           NOT NULL, -- DEPTH
	`brd_cmt_step`  INT           NOT NULL  -- STEP
);

-- 댓글
ALTER TABLE `FreeBoardComment`
	ADD CONSTRAINT `PK_FreeBoardComment` -- 댓글 기본키
		PRIMARY KEY (
			`brd_cmt_seq` -- 댓글번호
		);

ALTER TABLE `FreeBoardComment`
	MODIFY COLUMN `brd_cmt_seq` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `FreeBoardComment`
	AUTO_INCREMENT = 1;

-- 게시판카테고리
CREATE TABLE `FreeBoardCat` (
	`free_cat`      INT         NOT NULL, -- 대분류아이디
	`free_cat_name` VARCHAR(20) NOT NULL  -- 카테고리이름
);

-- 게시판카테고리
ALTER TABLE `FreeBoardCat`
	ADD CONSTRAINT `PK_FreeBoardCat` -- 게시판카테고리 기본키
		PRIMARY KEY (
			`free_cat` -- 대분류아이디
		);

-- 하루식단
CREATE TABLE `DailyMeal` (
	`meal_seq`      INT NOT NULL, -- 끼니코드
	`food_seq`      INT NOT NULL, -- 음식코드
	`meal_cart_seq` INT NOT NULL, -- 식단장바구니시퀀스
	`food_count`    INT NOT NULL  -- 음식수량
);

-- 하루식단
ALTER TABLE `DailyMeal`
	ADD CONSTRAINT `PK_DailyMeal` -- 하루식단 기본키
		PRIMARY KEY (
			`meal_seq`,      -- 끼니코드
			`food_seq`,      -- 음식코드
			`meal_cart_seq`  -- 식단장바구니시퀀스
		);

-- 끼니코드
CREATE TABLE `Meal` (
	`meal_seq`  INT         NOT NULL, -- 끼니코드
	`meal_name` VARCHAR(20) NOT NULL  -- 카테고리이름
);

-- 끼니코드
ALTER TABLE `Meal`
	ADD CONSTRAINT `PK_Meal` -- 끼니코드 기본키
		PRIMARY KEY (
			`meal_seq` -- 끼니코드
		);

-- 음식
CREATE TABLE `Food` (
	`food_seq`  INT          NOT NULL, -- 음식코드
	`food_name` VARCHAR(500) NOT NULL, -- 음식이름
	`food_cal`  INT          NOT NULL  -- 칼로리
);

-- 음식
ALTER TABLE `Food`
	ADD CONSTRAINT `PK_Food` -- 음식 기본키
		PRIMARY KEY (
			`food_seq` -- 음식코드
		);

ALTER TABLE `Food`
	MODIFY COLUMN `food_seq` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `Food`
	AUTO_INCREMENT = 1;

-- 루틴장바구니
CREATE TABLE `RoutineCart` (
	`routine_cart_seq`   INT         NOT NULL, -- 루틴장바구니시퀀스
	`routine_cart_title` VARCHAR(100) NOT NULL,  -- 루틴제목
	routine_cart_check char(1) not null default 'I'
);

-- 루틴장바구니
ALTER TABLE `RoutineCart`
	ADD CONSTRAINT `PK_RoutineCart` -- 루틴장바구니 기본키
		PRIMARY KEY (
			`routine_cart_seq` -- 루틴장바구니시퀀스
		);

ALTER TABLE `RoutineCart`
	MODIFY COLUMN `routine_cart_seq` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `RoutineCart`
	AUTO_INCREMENT = 1;

-- 루틴운동
CREATE TABLE `Routine` (
	`routine_seq`           INT     NOT NULL, -- 루틴시퀀스
	`excs_seq`              INT     NOT NULL, -- 운동코드
	`routine_exercise_hour` INT     NULL,     -- 시간
	`routine_set`           INT     NULL,     -- 세트수
	`routine_reps_per_set`  INT     NULL,     -- 1세트당횟수
	`routine_done_check`    CHAR(1) NOT NULL  -- 수행운동체킹
);

-- 루틴운동
ALTER TABLE `Routine`
	ADD CONSTRAINT `PK_Routine` -- 루틴운동 기본키
		PRIMARY KEY (
			`routine_seq` -- 루틴시퀀스
		);

ALTER TABLE `Routine`
	MODIFY COLUMN `routine_seq` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `Routine`
	AUTO_INCREMENT = 1;

-- 운동
CREATE TABLE `Exercise` (
	`excs_seq`               INT           NOT NULL, -- 운동코드
	`excs_kind`              CHAR(1)       NOT NULL, -- 유/무산소
	`excs_name`              VARCHAR(100)   NOT NULL, -- 이름
	`excs_cal_per_minweight` FLOAT         NULL,     -- 분몸무게당칼로리
	`excs_body_part`         VARCHAR(20)   NULL,     -- 부위
	`excs_equipment`         CHAR(1)       NULL,     -- 기구여부
	`excs_description`       VARCHAR(2000) NOT NULL, -- 설명
	`excs_video_link`        VARCHAR(500)  NULL,     -- 동영상
	`excs_level`             FLOAT         NOT NULL  -- 난이도
);

-- 운동
ALTER TABLE `Exercise`
	ADD CONSTRAINT `PK_Exercise` -- 운동 기본키
		PRIMARY KEY (
			`excs_seq` -- 운동코드
		);

ALTER TABLE `Exercise`
	MODIFY COLUMN `excs_seq` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `Exercise`
	AUTO_INCREMENT = 1;



-- 루틴장바구니묶음
CREATE TABLE `RoutineBundle` (
	`routine_cart_seq` INT         NOT NULL, -- 루틴장바구니시퀀스
	`user_email`       VARCHAR(50) NOT NULL, -- 이메일
	`routine_seq`      INT         NOT NULL  -- 루틴시퀀스
);

-- 루틴장바구니묶음
ALTER TABLE `RoutineBundle`
	ADD CONSTRAINT `PK_RoutineBundle` -- 루틴장바구니묶음 기본키
		PRIMARY KEY (
			`routine_cart_seq`, -- 루틴장바구니시퀀스
			`user_email`,       -- 이메일
			`routine_seq`       -- 루틴시퀀스
		);

-- Role
CREATE TABLE `Role` (
	`user_email` VARCHAR(50) NOT NULL, -- 이메일
	`role_name`  VARCHAR(20) NOT NULL  -- 롤이름
);

-- Role
ALTER TABLE `Role`
	ADD CONSTRAINT `PK_Role` -- Role 기본키
		PRIMARY KEY (
			`user_email`, -- 이메일
			`role_name`   -- 롤이름
		);

-- 루틴자랑게시판댓글
CREATE TABLE `RoutineBoardComment` (
	`routine_cmt_seq`   INT           NOT NULL, -- 댓글번호
	`routine_brd_seq`   INT           NOT NULL, -- 글번호
	`user_email`        VARCHAR(50)   NOT NULL, -- 이메일
	`routine_cmt`       VARCHAR(2000) NOT NULL, -- 댓글내용
	`routine_cmt_date`  DATETIME      NOT NULL, -- 작성날짜
	`routine_cmt_ref`   INT           NOT NULL, -- REF
	`routine_cmt_depth` INT           NOT NULL, -- DEPTH
	`routine_cmt_step`  INT           NOT NULL,  -- STEP
    user_nickname varchar(20) not null
);

-- 루틴자랑게시판댓글
ALTER TABLE `RoutineBoardComment`
	ADD CONSTRAINT `PK_RoutineBoardComment` -- 루틴자랑게시판댓글 기본키
		PRIMARY KEY (
			`routine_cmt_seq` -- 댓글번호
		);

ALTER TABLE `RoutineBoardComment`
	MODIFY COLUMN `routine_cmt_seq` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `RoutineBoardComment`
	AUTO_INCREMENT = 1;


-- 루틴자랑좋아요
CREATE TABLE `RoutineBoardLike` (
	`routine_brd_like_seq` INT         NOT NULL, -- 루틴자랑좋아요
	`user_email`           VARCHAR(50) NOT NULL, -- 이메일
	`routine_brd_seq`      INT         NOT NULL  -- 글번호
);

-- 루틴자랑좋아요
ALTER TABLE `RoutineBoardLike`
	ADD CONSTRAINT `PK_RoutineBoardLike` -- 루틴자랑좋아요 기본키
		PRIMARY KEY (
			`routine_brd_like_seq` -- 루틴자랑좋아요
		);

ALTER TABLE `RoutineBoardLike`
	MODIFY COLUMN `routine_brd_like_seq` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `RoutineBoardLike`
	AUTO_INCREMENT = 1;


-- 식단장바구니
CREATE TABLE `MealCart` (
	`meal_cart_seq` INT NOT NULL -- 식단장바구니시퀀스
);

-- 식단장바구니
ALTER TABLE `MealCart`
	ADD CONSTRAINT `PK_MealCart` -- 식단장바구니 기본키
		PRIMARY KEY (
			`meal_cart_seq` -- 식단장바구니시퀀스
		);

ALTER TABLE `MealCart`
	MODIFY COLUMN `meal_cart_seq` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `MealCart`
	AUTO_INCREMENT = 1;


-- 공지사항게시판
CREATE TABLE `NoticeBoard` (
	`notice_brd_seq`     INT           NOT NULL, -- 글번호
	`user_email`         VARCHAR(50)   NOT NULL, -- 이메일
	`notice_brd_title`   VARCHAR(500)   NOT NULL, -- 글제목
	`notice_brd_content` VARCHAR(2000) NOT NULL, -- 내용
	`notice_brd_regdate` DATETIME      NOT NULL,  -- 작성날짜
    notice_brd_hit INT DEFAULT 0 
);

-- 공지사항게시판
ALTER TABLE `NoticeBoard`
	ADD CONSTRAINT `PK_NoticeBoard` -- 공지사항게시판 기본키
		PRIMARY KEY (
			`notice_brd_seq` -- 글번호
		);

ALTER TABLE `NoticeBoard`
	MODIFY COLUMN `notice_brd_seq` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `NoticeBoard`
	AUTO_INCREMENT = 1;

-- 채팅방
CREATE TABLE `ChatRoom` (
	`room_number` INT          NOT NULL, -- 방번호
	`room_title`  VARCHAR(100) NOT NULL, -- 방제목
	`room_count`  INT          NOT NULL, -- 인원수
	`room_secret` CHAR(1)      NOT NULL, -- 비밀방여부
	`room_pwd`    INT          NULL,     -- 방비밀번호
	`user_email`  VARCHAR(50)  NOT NULL  -- 이메일
);

-- 채팅방
ALTER TABLE `ChatRoom`
	ADD CONSTRAINT `PK_ChatRoom` -- 채팅방 기본키
		PRIMARY KEY (
			`room_number` -- 방번호
		);

ALTER TABLE `ChatRoom`
	MODIFY COLUMN `room_number` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `ChatRoom`
	AUTO_INCREMENT = 1;

-- 채팅멤버
CREATE TABLE `ChatMember` (
	`mem_number`  INT         NOT NULL, -- 멤버번호
	`room_number` INT         NOT NULL, -- 방번호
	`user_email`  VARCHAR(50) NOT NULL  -- 이메일
);

-- 채팅멤버
ALTER TABLE `ChatMember`
	ADD CONSTRAINT `PK_ChatMember` -- 채팅멤버 기본키
		PRIMARY KEY (
			`room_number`, -- 방번호
			`user_email`   -- 이메일
		);

-- 일지
ALTER TABLE `Diary`
	ADD CONSTRAINT `FK_User_TO_Diary` -- 회원 -> 일지
		FOREIGN KEY (
			`user_email` -- 이메일
		)
		REFERENCES `User` ( -- 회원
			`user_email` -- 이메일
		
        )
        ON DELETE CASCADE;

-- 일지
ALTER TABLE `Diary`
	ADD CONSTRAINT `FK_MealCart_TO_Diary` -- 식단장바구니 -> 일지
		FOREIGN KEY (
			`meal_cart_seq` -- 식단장바구니시퀀스
		)
		REFERENCES `MealCart` ( -- 식단장바구니
			`meal_cart_seq` -- 식단장바구니시퀀스
		);



-- 루틴자랑게시판
ALTER TABLE `RoutineBoard`
	ADD CONSTRAINT `FK_User_TO_RoutineBoard` -- 회원 -> 루틴자랑게시판
		FOREIGN KEY (
			`user_email` -- 이메일
		)
		REFERENCES `User` ( -- 회원
			`user_email` -- 이메일
		)
        ON DELETE CASCADE;

-- 문의게시판
ALTER TABLE `QnaBrd`
	ADD CONSTRAINT `FK_User_TO_QnaBrd` -- 회원 -> 문의게시판
		FOREIGN KEY (
			`user_email` -- 이메일
		)
		REFERENCES `User` ( -- 회원
			`user_email` -- 이메일
		)
        ON DELETE CASCADE;



-- 게시판
ALTER TABLE `FreeBoard`
	ADD CONSTRAINT `FK_User_TO_FreeBoard` -- 회원 -> 게시판
		FOREIGN KEY (
			`user_email` -- 이메일
		)
		REFERENCES `User` ( -- 회원
			`user_email` -- 이메일
		)
        ON DELETE CASCADE;

-- 게시판
ALTER TABLE `FreeBoard`
	ADD CONSTRAINT `FK_FreeBoardCat_TO_FreeBoard` -- 게시판카테고리 -> 게시판
		FOREIGN KEY (
			`free_cat` -- 대분류아이디
		)
		REFERENCES `FreeBoardCat` ( -- 게시판카테고리
			`free_cat` -- 대분류아이디
		);

-- 댓글
ALTER TABLE `FreeBoardComment`
	ADD CONSTRAINT `FK_FreeBoard_TO_FreeBoardComment` -- 게시판 -> 댓글
		FOREIGN KEY (
			`free_brd_seq` -- 글번호
		)
		REFERENCES `FreeBoard` ( -- 게시판
			`free_brd_seq` -- 글번호
		) ON DELETE CASCADE;
        

-- 댓글
ALTER TABLE `FreeBoardComment`
	ADD CONSTRAINT `FK_User_TO_FreeBoardComment` -- 회원 -> 댓글
		FOREIGN KEY (
			`user_email` -- 이메일
		)
		REFERENCES `User` ( -- 회원
			`user_email` -- 이메일
		)
        ON DELETE CASCADE;

-- 하루식단
ALTER TABLE `DailyMeal`
	ADD CONSTRAINT `FK_Meal_TO_DailyMeal` -- 끼니코드 -> 하루식단
		FOREIGN KEY (
			`meal_seq` -- 끼니코드
		)
		REFERENCES `Meal` ( -- 끼니코드
			`meal_seq` -- 끼니코드
		);

-- 하루식단
ALTER TABLE `DailyMeal`
	ADD CONSTRAINT `FK_Food_TO_DailyMeal` -- 음식 -> 하루식단
		FOREIGN KEY (
			`food_seq` -- 음식코드
		)
		REFERENCES `Food` ( -- 음식
			`food_seq` -- 음식코드
		);

-- 하루식단
ALTER TABLE `DailyMeal`
	ADD CONSTRAINT `FK_MealCart_TO_DailyMeal` -- 식단장바구니 -> 하루식단
		FOREIGN KEY (
			`meal_cart_seq` -- 식단장바구니시퀀스
		)
		REFERENCES `MealCart` ( -- 식단장바구니
			`meal_cart_seq` -- 식단장바구니시퀀스
		);

-- 루틴운동
ALTER TABLE `Routine`
	ADD CONSTRAINT `FK_Exercise_TO_Routine` -- 운동 -> 루틴운동
		FOREIGN KEY (
			`excs_seq` -- 운동코드
		)
		REFERENCES `Exercise` ( -- 운동
			`excs_seq` -- 운동코드
		);



-- 루틴장바구니묶음
ALTER TABLE `RoutineBundle`
	ADD CONSTRAINT `FK_RoutineCart_TO_RoutineBundle` -- 루틴장바구니 -> 루틴장바구니묶음
		FOREIGN KEY (
			`routine_cart_seq` -- 루틴장바구니시퀀스
		)
		REFERENCES `RoutineCart` ( -- 루틴장바구니
			`routine_cart_seq` -- 루틴장바구니시퀀스
		);

-- 루틴장바구니묶음
ALTER TABLE `RoutineBundle`
	ADD CONSTRAINT `FK_User_TO_RoutineBundle` -- 회원 -> 루틴장바구니묶음
		FOREIGN KEY (
			`user_email` -- 이메일
		)
		REFERENCES `User` ( -- 회원
			`user_email` -- 이메일
		)
        ON DELETE CASCADE;

-- 루틴장바구니묶음
ALTER TABLE `RoutineBundle`
	ADD CONSTRAINT `FK_Routine_TO_RoutineBundle` -- 루틴운동 -> 루틴장바구니묶음
		FOREIGN KEY (
			`routine_seq` -- 루틴시퀀스
		)
		REFERENCES `Routine` ( -- 루틴운동
			`routine_seq` -- 루틴시퀀스
		);

-- Role
ALTER TABLE `Role`
	ADD CONSTRAINT `FK_User_TO_Role` -- 회원 -> Role
		FOREIGN KEY (
			`user_email` -- 이메일
		)
		REFERENCES `User` ( -- 회원
			`user_email` -- 이메일
		)
        ON DELETE CASCADE;

-- 루틴자랑게시판댓글
ALTER TABLE `RoutineBoardComment`
	ADD CONSTRAINT `FK_RoutineBoard_TO_RoutineBoardComment` -- 루틴자랑게시판 -> 루틴자랑게시판댓글
		FOREIGN KEY (
			`routine_brd_seq` -- 글번호
		)
		REFERENCES `RoutineBoard` ( -- 루틴자랑게시판
			`routine_brd_seq` -- 글번호
		)
         ON DELETE CASCADE;

-- 루틴자랑게시판댓글
ALTER TABLE `RoutineBoardComment`
	ADD CONSTRAINT `FK_User_TO_RoutineBoardComment` -- 회원 -> 루틴자랑게시판댓글
		FOREIGN KEY (
			`user_email` -- 이메일
		)
		REFERENCES `User` ( -- 회원
			`user_email` -- 이메일
		)
        ON DELETE CASCADE;



-- 루틴자랑좋아요
ALTER TABLE `RoutineBoardLike`
	ADD CONSTRAINT `FK_RoutineBoard_TO_RoutineBoardLike` -- 루틴자랑게시판 -> 루틴자랑좋아요
		FOREIGN KEY (
			`routine_brd_seq` -- 글번호
		)
		REFERENCES `RoutineBoard` ( -- 루틴자랑게시판
			`routine_brd_seq` -- 글번호
		) ON DELETE CASCADE;

-- 루틴자랑좋아요
ALTER TABLE `RoutineBoardLike`
	ADD CONSTRAINT `FK_User_TO_RoutineBoardLike` -- 회원 -> 루틴자랑좋아요
		FOREIGN KEY (
			`user_email` -- 이메일
		)
		REFERENCES `User` ( -- 회원
			`user_email` -- 이메일
		)
        ON DELETE CASCADE;


-- 공지사항게시판
ALTER TABLE `NoticeBoard`
	ADD CONSTRAINT `FK_User_TO_NoticeBoard` -- 회원 -> 공지사항게시판
		FOREIGN KEY (
			`user_email` -- 이메일
		)
		REFERENCES `User` ( -- 회원
			`user_email` -- 이메일
		)
        ON DELETE CASCADE;

-- 채팅방
ALTER TABLE `ChatRoom`
	ADD CONSTRAINT `FK_User_TO_ChatRoom` -- 회원 -> 채팅방
		FOREIGN KEY (
			`user_email` -- 이메일
		)
		REFERENCES `User` ( -- 회원
			`user_email` -- 이메일
		)
        ON DELETE CASCADE;

-- 채팅멤버
ALTER TABLE `ChatMember`
	ADD CONSTRAINT `FK_ChatRoom_TO_ChatMember` -- 채팅방 -> 채팅멤버
		FOREIGN KEY (
			`room_number` -- 방번호
		)
		REFERENCES `ChatRoom` ( -- 채팅방
			`room_number` -- 방번호
		) ON DELETE CASCADE;

-- 채팅멤버
ALTER TABLE `ChatMember`
	ADD CONSTRAINT `FK_User_TO_ChatMember` -- 회원 -> 채팅멤버
		FOREIGN KEY (
			`user_email` -- 이메일
		)
		REFERENCES `User` ( -- 회원
			`user_email` -- 이메일
		)
        ON DELETE CASCADE;



-- 회원가입 트리거
DELIMITER $$
create trigger insert_role
after insert on User
for each row
BEGIN
IF NEW.user_email = 'admin@bodiary.or.kr' THEN
insert into role (user_email, role_name)
values(NEW.user_email ,'ROLE_ADMIN'), (NEW.user_email ,'ROLE_REGULAR_USER');
ELSE
insert into role(user_email, role_name)
values(NEW.user_email ,'ROLE_ASSOCIATE_USER');
END IF;
END $$
DELIMITER ;


#일지 작성시 몸무게 증가 트리거
DELIMITER $$
create trigger update_weight
after insert on diary
for each row
BEGIN
update user set
user_weight = NEW.diary_today_weight
where user_email = NEW.user_email;
END $$
DELIMITER ;


#일지 생성 프로시저 (Stored Procedures에서 생성)
CREATE DEFINER=`bodiary`@`%` PROCEDURE `insert_bodiary`(
IN in_diary_today_weight int,
IN in_user_email varchar(100),
IN in_routine_cart_seq int,
IN in_meal_cart_seq int,
IN in_diary_content varchar(2000),
IN in_diary_condition varchar(20),
IN in_diary_main_img varchar(100),
IN in_diary_date datetime
)
BEGIN
DECLARE metabolism int;
DECLARE recom int;
DECLARE nut int;
DECLARE v_gender varchar(20);
DECLARE v_height int;
select user_gender into v_gender from user where user_email = in_user_email;
select user_height into v_height from user where user_email = in_user_email;
select sum(a.food_cal * a.food_count) into nut
from (select f.food_cal, d.food_count from (select * from dailymeal where meal_cart_seq = in_meal_cart_seq) d JOIN food f ON d.food_seq = f.food_seq)a;
set metabolism =
CASE
WHEN v_gender ='남' THEN (66.47 + (13.75*in_diary_today_weight) +
(5*(select user_height from user where user_email = in_user_email)) - (6.76*(select user_age from user where user_email = in_user_email)))
WHEN v_gender ='여' then (655.1 + (9.56*in_diary_today_weight)+(1.85*(select user_height from user where user_email = in_user_email))-(4.68*(select user_age from user where user_email = in_user_email)))
end;

set recom = ((v_height-100)*0.9)*35;

insert into diary (meal_cart_seq, user_email, routine_cart_seq, diary_content
, diary_condition, diary_main_img, diary_date
, diary_today_weight, diary_metabolism, diary_recom, diary_nut)
values(in_meal_cart_seq, in_user_email, in_routine_cart_seq, in_diary_content
, in_diary_condition, in_diary_main_img, in_diary_date
, in_diary_today_weight, metabolism, recom, nut);

select max(diary_seq) from diary;
END


#일지 수정 프로시저 (Stored Procedures에서 생성)
CREATE DEFINER=`bodiary`@`%` PROCEDURE `update_bodiary`(
IN in_diary_seq int,
IN in_diary_today_weight int,
IN in_user_email varchar(100),
IN in_routine_cart_seq int,
IN in_meal_cart_seq int,
IN in_diary_content varchar(2000),
IN in_diary_condition varchar(20),
IN in_diary_main_img varchar(100)
)
BEGIN
DECLARE metabolism int;
DECLARE recom int;
DECLARE nut int;
DECLARE v_gender varchar(20);
DECLARE v_height int;
select user_gender into v_gender from user where user_email = in_user_email;
select user_height into v_height from user where user_email = in_user_email;
select sum(a.food_cal * a.food_count) into nut
from (select f.food_cal, d.food_count from (select * from dailymeal where meal_cart_seq = in_meal_cart_seq) d JOIN food f ON d.food_seq = f.food_seq)a;
set metabolism =
CASE
WHEN v_gender ='남' THEN (66.47 + (13.75*in_diary_today_weight) +
(5*(select user_height from user where user_email = in_user_email)) - (6.76*(select user_age from user where user_email = in_user_email)))
WHEN v_gender ='여' then (655.1 + (9.56*in_diary_today_weight)+(1.85*(select user_height from user where user_email = in_user_email))-(4.68*(select user_age from user where user_email = in_user_email)))
end;

set recom = ((v_height-100)*0.9)*35;

update diary set 
meal_cart_seq = in_meal_cart_seq, 
routine_cart_seq = in_routine_cart_seq, 
diary_content = in_diary_content,
diary_condition = in_diary_condition, 
diary_main_img = in_diary_main_img,  
diary_today_weight = in_diary_today_weight, 
diary_metabolism = metabolism, 
diary_recom = recom, 
diary_nut = nut
where diary_seq = in_diary_seq;
END

