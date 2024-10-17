DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS lecture;
DROP TABLE IF EXISTS attend;
DROP TABLE IF EXISTS enroll;
DROP TABLE IF EXISTS fee;
DROP TABLE IF EXISTS RefreshToken;
DROP TABLE IF EXISTS teacher;
DROP TABLE IF EXISTS institute;

-- 학원
CREATE TABLE institute (
    pk BINARY(16) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    failedCount TINYINT NOT NULL DEFAULT 0,
    accountNonLocked BOOLEAN NOT NULL DEFAULT TRUE,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    authorities VARCHAR(255) NOT NULL,
    institute_name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone_num VARCHAR(255) NOT NULL
);

-- 선생님
CREATE TABLE teacher (
    pk BINARY(16) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    failedCount TINYINT NOT NULL DEFAULT 0,
    accountNonLocked BOOLEAN NOT NULL DEFAULT TRUE,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    authorities VARCHAR(255) NOT NULL,
    teacher_name VARCHAR(255) NOT NULL,
    age TINYINT,
    birth DATE,
    phoneNum VARCHAR(20),
    gender CHAR(1),
    bank_account VARCHAR(255),
    salary BIGINT,
    nickname VARCHAR(255) NOT NULL DEFAULT '닉네임',
    provider VARCHAR(255),
    institute_id BINARY(16),
    FOREIGN KEY (institute_id) REFERENCES institute(pk) ON DELETE CASCADE
);

-- 학생
CREATE TABLE student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    grade INT NOT NULL,
    phoneNumber VARCHAR(20) NOT NULL,
    parentName VARCHAR(255) NOT NULL,
    parentNumber VARCHAR(20) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    level VARCHAR(50) NOT NULL,
    institute_id BINARY(16) NOT NULL,  -- 수정: BIGINT -> BINARY(16)
    FOREIGN KEY (institute_id) REFERENCES institute(pk) ON DELETE CASCADE
);

-- 강의
CREATE TABLE lecture (
    lecture_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    grade VARCHAR(50) NOT NULL,
    fee INT NOT NULL,
    time VARCHAR(50) NOT NULL,
    institute_id BINARY(16) NOT NULL,  -- 수정: BIGINT -> BINARY(16)
    teacher_id BINARY(16) DEFAULT NULL, -- 수정: BIGINT -> BINARY(16)
    FOREIGN KEY (institute_id) REFERENCES institute(pk) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES teacher(pk) ON DELETE SET DEFAULT
);




-- 일정
CREATE TABLE schedule (
    schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    memo VARCHAR(255),
    institute_id BINARY(16) NOT NULL,
    lecture_id BIGINT DEFAULT NULL,
    FOREIGN KEY (institute_id) REFERENCES institute(pk) ON DELETE CASCADE,
    FOREIGN KEY (lecture_id) REFERENCES lecture(lecture_id) ON DELETE CASCADE
);

-- 등록
CREATE TABLE enroll (
    enroll_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    "year" SMALLINT NOT NULL,
    "month" SMALLINT NOT NULL,
    payed_fee INT NOT NULL DEFAULT 0,
    fullPayment BOOLEAN NOT NULL DEFAULT FALSE,
    lecture_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    FOREIGN KEY (lecture_id) REFERENCES lecture(lecture_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);

-- 출석
CREATE TABLE attendance (
    attend_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    attendance TINYINT NOT NULL DEFAULT 0,
    memo VARCHAR(255),
    student_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES schedule(schedule_id) ON DELETE CASCADE
);

-- 수강료
CREATE TABLE fee (
    fee_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    totalMonthFee BIGINT NOT NULL DEFAULT 0,
    payedMonthFee BIGINT NOT NULL DEFAULT 0,
    "year" SMALLINT NOT NULL,
    "month" SMALLINT NOT NULL,
    institute_id BINARY(16) NOT NULL,
    FOREIGN KEY (institute_id) REFERENCES institute(pk) ON DELETE CASCADE
);


-- 리프레쉬 토큰
CREATE TABLE RefreshToken (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BINARY(16) NOT NULL UNIQUE,
    refresh_token VARCHAR(1000) NOT NULL,
    expired_time TIMESTAMP NOT NULL
);
