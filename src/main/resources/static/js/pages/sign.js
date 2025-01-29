const signIn = {
    init: function() {
        this.validateForm(); // 유효성 검사 초기화
    },
    validateForm: function() {
        const frm = $('#signInFrm'); // 폼 요소를 선택
        console.log(frm);
        frm.validate(jqueryValidOptions.sign.in(async (frm) => {
            const { data } = await Post(frm.action, frm);
            if (data.result) {
                location.href = '/';
            } else {
                alert(data.message);
            }
        }));
    }
};

const signUp = {
    init: function() {
        this.validateForm(); // 유효성 검사 초기화
        this.emailAuth();
    },
    validateForm: function() {
        const frm = $('#signUpFrm'); // 폼 요소를 선택
        frm.validate(jqueryValidOptions.sign.up(async (frm) => {
            const { data } = await Post(frm.action, frm);
            if (data.result) {
                alert(data.message);
                location.href = '/auth/sign-in';
            } else {
                alert(data.message);
            }
        }));
    },
    emailAuth: function() {

        document.getElementById('email-btn').addEventListener('click', async () => {
            const { _csrf, email } = document.signUpFrm;

            if (!_csrf.value || !email.value) {
                alert('이메일 입력이 되어있지않습니다.')
                return;
            }
            const { data } = await Post('/auth/verify/email', { csrf: _csrf.value, email: email.value });
            if (data.result) {
                alert(data.message);
            } else {
                alert(data.message);
            }
        })

    }
};


$(document).ready(function() {
    if (document.getElementById('signInFrm')) {
        signIn.init();
    }

    if (document.getElementById('signUpFrm')) {
        signUp.init();
    }

});