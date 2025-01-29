insert into HOMEPAGE.template ( from_email, subject, to_email, bcc, content, id)
values  ( 'noreply@gunho.dev', '[GUNHO.DEV] 회원가입 인증', null, null, '<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>이메일 인증</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: ''Apple SD Gothic Neo'', ''Malgun Gothic'', ''맑은 고딕'', sans-serif;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 40px 20px;
            background-color: #fff;
        }
        .logo {
            text-align: center;
            margin-bottom: 40px;
        }
        .content {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .title {
            color: #333;
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 20px;
            text-align: center;
        }
        .verification-code {
            background-color: #f8f9fa;
            padding: 20px;
            text-align: center;
            border-radius: 8px;
            margin: 30px 0;
        }
        .code {
            color: #FF7433;
            font-size: 32px;
            font-weight: bold;
            letter-spacing: 4px;
            margin: 0;
        }
        .description {
            color: #666;
            line-height: 1.6;
            margin-bottom: 30px;
        }
        .footer {
            color: #999;
            font-size: 14px;
            text-align: center;
            margin-top: 40px;
        }
        .button {
            display: inline-block;
            background-color: #FF7433;
            color: #fff;
            padding: 15px 30px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            margin: 20px 0;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="logo">
        <!-- 로고 이미지를 넣을 수 있습니다 -->
        <img src="[로고URL]" alt="로고" style="max-width: 150px;">
    </div>
    <div class="content">
        <h1 class="title">이메일 인증번호</h1>
        <p class="description">
            안녕하세요.<br>
            회원가입을 위한 인증번호를 안내해 드립니다.<br>
            아래 인증번호를 회원가입 페이지에 입력해 주세요.
        </p>
        <div class="verification-code">
            <p class="code">%s</p>
        </div>
        <p class="description">
            인증번호는 발급 후 30분 동안만 유효합니다.<br>
            본 메일은 발신전용이므로 회신이 되지 않습니다.
        </p>
    </div>
    <div class="footer">
        © 2025 [gunho.dev]. All rights reserved.
    </div>
</div>
</body>
</html>', 'EMAIL_VERIFY');


insert into HOMEPAGE.template ( from_email, subject, to_email, bcc, content, id)
values  ( 'noreply@gunho.dev', '[GUNHO.DEV] 오류 알림', 'dsds60321@gmail.com', null, '<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bug Report</title>
    <style>
        body {
            font-family: ''Helvetica'', Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            color: #333;
        }
        .container {
            width: 100%;
            max-width: 700px;
            margin: 20px auto;
            background-color: #ffffff;
            border: 1px solid #e3e3e3;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        .header {
            background-color: #4a90e2;
            color: #ffffff;
            padding: 10px;
            text-align: center;
            font-size: 20px;
            font-weight: bold;
        }
        .header h1 {
            margin: 0;
        }
        .content {
            padding: 20px 30px;
        }
        .content p {
            font-size: 16px;
            margin-bottom: 15px;
        }
        .error-title {
            color: #4a90e2;
            margin-top: 10px;
            margin-bottom: 20px;
            font-size: 20px;
            font-weight: bold;
            border-bottom: 2px solid #eeeeee;
            padding-bottom: 10px;
        }
        .exception-content {
            background-color: #f8f8f8;
            color: #5c5c5c;
            padding: 15px;
            border: 1px solid #dddddd;
            border-radius: 6px;
            margin: 20px 0;
            font-family: monospace;
            white-space: pre-wrap;
        }
        .exception-content strong {
            color: #d9534f;
        }
        .button-container {
            margin-top: 20px;
            text-align: center;
        }
        .button {
            text-decoration: none;
            font-size: 14px;
            padding: 10px 20px;
            color: #ffffff;
            background-color: #4a90e2;
            border-radius: 6px;
            transition: background-color 0.3s ease;
            font-weight: bold;
        }
        .button:hover {
            background-color: #356bb3;
        }
        .footer {
            background-color: #f9f9f9;
            color: #6b6b6b;
            font-size: 12px;
            padding: 10px 15px;
            text-align: center;
            border-top: 1px solid #e3e3e3;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h2> 오류 보고</h2>
    </div>
    <div class="content">
        <p>안녕하세요 개발자님,</p>
        <p>다음과 같은 오류가 감지되었습니다:</p>

        <div class="error-title">
            {title}
        </div>

        <div class="exception-content">
            {content}
        </div>

        <p>위 내용을 확인하시고, 빠른 조치를 부탁드립니다. 감사합니다.</p>

        <div class="button-container">
            <a href="http://localhost:8080" class="button">시스템 확인</a>
        </div>
    </div>
    <div class="footer">
        ⓒ 2025 GUNHO.DEV. All rights reserved. | 문의: dsds60321@gmail.com
    </div>
</div>
</body>
</html>
', 'ERROR');