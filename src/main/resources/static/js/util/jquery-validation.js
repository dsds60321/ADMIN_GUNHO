const jqueryValidOptions = {
    // AUTH
    sign : {
        in: function(submitHandler) {
            return {
                rules: {
                    _csrf: {
                        required: true
                    },
                    userId: {
                        required: true, // 필수 입력값
                        minlength: 4 // 최소 4자
                    },
                    password: {
                        required: true, // 필수 입력값
                        minlength: 6 // 최소 6자
                    }
                },
                messages: {
                    _csrf : {
                      required : '보안위반 사항이 존재합니다. 다시 시도 해주세요.'
                    },
                    userId: {
                        required: "ID를 입력하세요",
                        minlength: "ID는 최소 4자 이상이어야 합니다"
                    },
                    password: {
                        required: "비밀번호를 입력하세요",
                        minlength: "비밀번호는 최소 6자 이상이어야 합니다"
                    }
                },
                errorElement: "span", // 오류 메시지 요소 (기본: <label>)
                errorClass: "error-message", // CSS 클래스
                highlight: function(element) {
                    if (element.name === "_csrf") {
                        alert("보안위반 사항이 존재합니다. 다시 시도 해주세요.");
                        return;
                    }

                    // 유효성 오류 발생 시 input 클래스를 추가
                    $(element).addClass("input-error");
                },
                unhighlight: function(element) {
                    if (element.name === "_csrf") {
                        return;
                    }

                    // 유효성 오류 해결 시 클래스 제거
                    $(element).removeClass("input-error");
                },
                errorPlacement: function(error, element) {
                    // 에러 메시지 위치를 필드셋 내부에 배치
                    if (element.closest("fieldset").length) {
                        element.closest("fieldset").append(error);
                    } else {
                        error.insertAfter(element); // 기본 위치
                    }
                },
                submitHandler: submitHandler // 외부에서 주입받은 함수 사용
            };
        },
        up: function(submitHandler) {
            return {
                rules: {
                    _csrf : {
                        required : true
                    },
                    email: {
                        required: true, // 필수 입력값
                        email: true // 유효한 이메일 형식
                    },
                    userId: {
                        required: true, // 필수 입력값
                        minlength: 4 // 최소 4자
                    },
                    password: {
                        required: true, // 필수 입력값
                        minlength: 6 // 최소 6자
                    },
                    confirmPassword: {
                        required: true, // 필수 입력값
                        equalTo: '[name="password"]' // 비밀번호와 동일한 값
                    },
                    emailAuth: {
                      required: true
                    },
                    signed: {
                        required: true // 개인정보 동의 체크박스 필수
                    }
                },
                messages: {
                    _csrf : {
                        required : '보안위반 사항이 존재합니다. 다시 시도 해주세요.'
                    },
                    nick: {
                        required: "닉네임을 입력하세요",
                        minlength: "닉네임은 최소 2자 이상이어야 합니다"
                    },
                    email: {
                        required: "이메일을 입력하세요",
                        email: "유효한 이메일 주소를 입력하세요"
                    },
                    userId: {
                        required: "ID를 입력하세요",
                        minlength: "ID는 최소 4자 이상이어야 합니다"
                    },
                    password: {
                        required: "비밀번호를 입력하세요",
                        minlength: "비밀번호는 최소 6자 이상이어야 합니다"
                    },
                    confirmPassword: {
                        required: "비밀번호 확인을 입력하세요",
                        equalTo: "비밀번호가 일치하지 않습니다"
                    },
                    signed: {
                        required: "개인정보 처리방침에 동의해야 합니다"
                    }
                },
                errorElement: "span", // 오류 메시지 요소 (기본: <label>)
                errorClass: "error-message", // CSS 클래스
                highlight: function(element) {
                    if (element.name === "_csrf") {
                        alert("보안위반 사항이 존재합니다. 다시 시도 해주세요.");
                        return;
                    }

                    // 유효성 오류 발생 시 input 클래스를 추가
                    $(element).addClass("input-error");
                },
                unhighlight: function(element) {
                    if (element.name === "_csrf") {
                        return; // _csrf는 unhighlight 동작 없음
                    }


                    // 유효성 오류 해결 시 클래스 제거
                    $(element).removeClass("input-error");
                },
                errorPlacement: function(error, element) {
                    // 에러 메시지 위치를 필드셋 내부에 배치
                    if (element.closest("fieldset").length) {
                        element.closest("fieldset").append(error);
                    } else {
                        error.insertAfter(element); // 기본 위치
                    }
                },
                submitHandler: submitHandler // 외부에서 전달받은 함수 사용
            };
        }
    }
}