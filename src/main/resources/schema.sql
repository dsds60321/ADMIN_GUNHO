CREATE SCHEMA IF NOT EXISTS HOMEPAGE;

CREATE TABLE IF NOT EXISTS HOMEPAGE.template (
     idx        bigint auto_increment primary key,
     id VARCHAR(50) PRIMARY KEY,         -- 템플릿 ID
    from_email VARCHAR(100),            -- 발신 이메일
    subject VARCHAR(200),               -- 이메일 제목
    to_email VARCHAR(100) NULL,         -- 수신 이메일 (옵션)
    bcc VARCHAR(100) NULL,              -- 숨은 참조 (옵션)
    content TEXT NOT NULL               -- 이메일 내용 (HTML)
    );
